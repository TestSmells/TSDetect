package org.testsmells.server.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.testsmells.server.tables.pojos.TestSmells;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

import static org.testsmells.server.repository.Constants.*;

@Repository
public class DBInputTool {
    private final DSLContext dashboardDsl;
    private final HikariDataSource dashboardDatasource;
    public DBInputTool(@Qualifier("dsl-dashboard") DSLContext dashboardDsl,
                       @Qualifier("ds-dashboard") HikariDataSource dashboardDatasource) {
        this.dashboardDsl = dashboardDsl;
        this.dashboardDatasource = dashboardDatasource;
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
     * @return      : the return of inputRunSmells. As it is dependent on the success of the other inputs, it can be
     *                 assumed that if this succeeded, then all other steps also succeeded
     */
    public boolean inputData(String uid, Timestamp dateRun, HashMap<String, Integer> smells) {

        inputTestRun(uid,dateRun);

        return inputRunSmells(uid, dateRun, smells);
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
        try (Connection conn = dashboardDatasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_TEST_RUN_QUERY)) { //add uid and date run to the test_run table
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
     * @return true if all the smells and quantities were successfully inserted.
     */
    private boolean inputRunSmells(String UID, Timestamp date, HashMap<String, Integer> smells) {

        ResultSet result_set = null;
        int smellId = -1;
        int runID = -1;

        // Open a connection
        //System.out.println("open a connection");
        try(Connection conn = dashboardDatasource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(GET_RUN_ID)){ //get the run id generated when this run was added to test_runs
            stmt.setString(1, UID);
            stmt.setTimestamp(2, date);
            result_set = stmt.executeQuery();
            result_set.next();  //set the pointer to the first entry in the return
            runID = result_set.getInt(3); //collect the run id

        } catch(SQLException e){
            System.out.println("sql exception");
            runID = -1; //defensive code, should not be needed
            e.printStackTrace();
        }

        //go through all given test smells
        for(String smell : smells.keySet()){

            //establish connection
            try(Connection conn = dashboardDatasource.getConnection();
                //get the ID number for the smell being added from the test_smells table
                PreparedStatement stmt = conn.prepareStatement(GET_TEST_SMELL_ID_FROM_NAME)){
                stmt.setString(1, smell);
                result_set = stmt.executeQuery();
                result_set.next(); //set the pointer to the first entry
                smellId = result_set.getInt(1); //collect the smell id
            } catch(SQLException e){
                System.out.println("sql exception");
                smellId = -1;  //defensive code, should not be needed
                e.printStackTrace();
            }

            if(smellId > 0 && runID > 0 && smells.get(smell) > 0) {

                // Open a connection
                //System.out.println("open a connection");
                try (Connection conn = dashboardDatasource.getConnection();
                     //add run ID, smell ID, and number of smells to the test_run_smells table
                     PreparedStatement stmt = conn.prepareStatement(ADD_TEST_RUN_SMELLS_QUERY)) {
                    stmt.setInt(1, runID);
                    stmt.setInt(2, smellId);
                    stmt.setInt(3, smells.get(smell));
                    stmt.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("sql exception");
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}