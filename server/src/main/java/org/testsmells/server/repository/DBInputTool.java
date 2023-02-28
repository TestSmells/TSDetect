package org.testsmells.server.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;

import static org.testsmells.server.repository.Constants.*;

@Repository
public class DBInputTool {
    private final HikariDataSource pluginDatasource;
    public DBInputTool(@Qualifier("ds-plugin") HikariDataSource pluginDatasource) {
        this.pluginDatasource = pluginDatasource;
    }

    /**
     * The primary means of inputting data into the TSDetect database. Inputs are assumed to be of the correct format,
     * Inputs that are not of the correct format will not be added.
     *
     * @param uid : The User ID of the run being entered. Must be a String containing no more than 50 characters,
     * @param dateRun : The data that the output being entered was originally entered. Must be a Java.sql Timestamp object
     *                  in 'YYYY-MM-DD HH:MM:SS' format
     * @param smells : A map of TestSmell names to the number of occurrences of that smell in this test run. Test smells
     *                 must already be present in the 'test_smells' table, and the number of smells must be greater than 0.
     *                 Test smell names are case-sensitive.
     * @return      : A hashmap of all successfully inserted values. one entry will be keyed on the UID, with a value
     *                equaling the number of rows changed in the Test run tables (should be either 0 or 1 in all cases),
     *                all other entries are keyed on the given Test smell names with a value of the number of the smell
     *                entered into the table. This Map will be empty if no entries were added.
     */
    public HashMap<String, Integer> inputData(String uid, Timestamp dateRun, HashMap<String, Integer> smells) {
        //MySQL rounds timestamps to the nearest second, Java uses milliseconds
        Timestamp rounded = new Timestamp(1000*(dateRun.getTime()/1000));
        HashMap<String, Integer> results = new HashMap<String, Integer>();

        //check for a positive smell count
        boolean positiveSmellCount = false;
        for (int smellCount : smells.values()) {
            if (smellCount > 0) {
                positiveSmellCount = true;
                break;
            }
        }

        if (positiveSmellCount) {
            //add the uid to the test runs table
            results.put(uid, inputTestRun(uid, rounded));

            //add the smells to the test run smells table
            results.putAll(inputRunSmells(uid, rounded, smells));

        } else {
            results.put(uid, 0);
        }
        return results;
    }

    /**
     *  Private helper method to handle adding the uid and date run to the test_runs table.
     *
     * @param uid : The User ID of the run being entered. Must be a String containing no more than 50 characters,
     * @param dateRun : The data that the output being entered was originally entered. Must be in 'YYYY-MM-DD HH:MM:SS' format
     * @return : the number of rows changed by the addition (this should be 1 in any case where data was successfully added)
     *           or 0 in nothing was added.
     */
    private int inputTestRun(String uid, Timestamp dateRun) {
        // Open a connection
        //System.out.println("open a connection");
        try (Connection conn = pluginDatasource.getConnection()) {
            // Check if a run already exists before inserting a new test run into test_runs tables
            PreparedStatement stmt = conn.prepareStatement(GET_RUN_ID);
            stmt.setString(1, uid);
            stmt.setTimestamp(2, dateRun);
            ResultSet result_set = stmt.executeQuery();
            boolean exists = result_set.next();
            if (exists) {
                System.out.printf("No operation completed, test run exists for id: %s, timestamp: %s%n", uid, dateRun);
                return 0;
            }

            stmt = conn.prepareStatement(ADD_TEST_RUN_QUERY); //add uid and date run to the test_run table
            stmt.setString(1, uid);
            stmt.setTimestamp(2, dateRun);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected; //will only reach this return iff add was successful
        } catch (SQLException e) {
            System.out.println("sql exception");
            e.printStackTrace();
            return(0); //return impossible value in failure case
        }
    }

    /**
     *  Method to add the run_id, test_smell_id, and quantity of that test smell found to the test_run_smells table given
     *  the user ID, timestamp, and the test smell names
     *
     * @param UID : The User ID of the run being entered. Must be a String containing no more than 50 characters,
     * @param date : The data that the output being entered was originally entered. Must be in 'YYYY-MM-DD HH:MM:SS' format
     * @param smells : A map of TestSmell names to the number of occurrences of that smell in this test run. Test smells
     *                 must already be present in the 'test_smells' table, and the number of smells must be greater than 0.
     *                 Test smell names are case-sensitive.
     * @return A hashmap of all successfully inserted values. entries are keyed on the Test smell names with a value of
     *         the number of the smell entered into the table. This Map will be empty if no entries were added.
     */
    private HashMap<String, Integer> inputRunSmells(String UID, Timestamp date, HashMap<String, Integer> smells) {

        ResultSet result_set = null;
        int smellId = -1;
        int runID = -1;
        HashMap<String, Integer> results = new HashMap<>();

        // Open a connection
        //System.out.println("open a connection");
        try(Connection conn = pluginDatasource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(GET_RUN_ID); //get the run id generated when this run was added to test_runs
            stmt.setString(1, UID);
            stmt.setTimestamp(2, date);
            result_set = stmt.executeQuery();
            result_set.next();  //set the pointer to the first entry in the
            runID = result_set.getInt(3); //collect the run id

            //go through all given test smells
            for(String smell : smells.keySet()){
                //establish connection
                //get the ID number for the smell being added from the test_smells table
                stmt = conn.prepareStatement(GET_TEST_SMELL_ID_FROM_NAME);
                stmt.setString(1, smell);
                result_set = stmt.executeQuery();
                boolean exists = result_set.next(); //set the pointer to the first entry
                if (exists) {
                    smellId = result_set.getInt(1); //collect the smell id

                    if(smellId > 0 && runID > 0 && smells.get(smell) > 0) {
                        stmt = conn.prepareStatement(GET_QUANTITY_FROM_TEST_RUN_SMELL_BY_RUN_ID_AND_SMELL_ID);
                        stmt.setInt(1, runID);
                        stmt.setInt(2, smellId);
                        result_set = stmt.executeQuery();
                        exists = result_set.next();
                        if (!exists) {
                            //add run ID, smell ID, and number of smells to the test_run_smells table
                            stmt = conn.prepareStatement(ADD_TEST_RUN_SMELLS_QUERY);
                            stmt.setInt(1, runID);
                            stmt.setInt(2, smellId);
                            stmt.setInt(3, smells.get(smell));
                            stmt.executeUpdate();
                            results.put(smell, smells.get(smell));
                        }
                    }
                }
            }
        } catch(SQLException e){
            System.out.println("sql exception");
            e.printStackTrace();
            return results;
        }

        return results;
    }
}