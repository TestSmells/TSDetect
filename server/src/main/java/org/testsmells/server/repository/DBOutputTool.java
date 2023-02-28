package org.testsmells.server.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testsmells.server.repository.Constants.*;


@Repository
public class DBOutputTool {
    private final HikariDataSource dashboardDatasource;
    public DBOutputTool(@Qualifier("ds-dashboard") HikariDataSource dashboardDatasource) {
        this.dashboardDatasource = dashboardDatasource;
    }


    /**
     * Queries the TSDetect database to retrieve a list of the quantities of specific test smells logged after a given date
     * as determined by a given list
     *
     * @param startDate a Java.sql.date Timestamp object of the format "yyyy-mm-dd hh:mm:ss"
     * @param smells a ArrayList<String> of test smell names. All names are assumed to be of a format match the entries
     *               in the database
     * @return  A HashMap<String, Long> keyed on the test names provided in "smells" and with values equal to the number
     *          of occurrences of that test smell in the database since the given date
     */
    public HashMap<String, Long> outTestSmellData(Timestamp startDate, ArrayList<String> smells) {

        ArrayList<Integer> runIDs = getAllTestRunIDsFromDate(startDate.toString());
        return getTestSmellsAndCounts(runIDs, smells);
    }

    /**
     * Queries the TSDetect database to retrieve a list of the quantities of all test smells logged after a given date
     *
     * @param startDate a Java.sql.date Timestamp object of the format "yyyy-mm-dd hh:mm:ss"
     * @return A HashMap<String, Long> keyed on the test names and with values equal to the number
     *         of occurrences of that test smell in the database since the given date
     */
    public HashMap<String, Long> outTestSmellData(Timestamp startDate) {

        ArrayList<Integer> runIDs = getAllTestRunIDsFromDate(startDate.toString());
        return getTestSmellsAndCounts(runIDs);
    }

    /**
     * Queries the TSDetect database to retrieve a list of the quantities of specific test smells logged after a given date
     * as determined by a given list
     *
     * @param startDate a String object representative of the Timestamp, of the format "yyyy-mm-dd hh:mm:ss" or "yyy-mm-dd"
     * @param smells a ArrayList<String> of test smell names. All names are assumed to be of a format match the entries
     *               in the database
     * @return A HashMap<String, Long> keyed on the test names provided in "smells" and with values equal to the number
     *         of occurrences of that test smell in the database since the given date
     */
    public HashMap<String, Long> outTestSmellData(String startDate, ArrayList<String> smells) {

        ArrayList<Integer> runIDs = getAllTestRunIDsFromDate(startDate);
        return getTestSmellsAndCounts(runIDs, smells);
    }

    /**
     * Queries the TSDetect database to retrieve a list of the quantities of all test smells logged after a given date
     *
     * @param startDate a String object representative of the Timestamp, of the format "yyyy-mm-dd hh:mm:ss" or "yyy-mm-dd"
     * @return A HashMap<String, Long> keyed on the test names and with values equal to the number
     *         of occurrences of that test smell in the database since the given date
     */
    public HashMap<String, Long> outTestSmellData(String startDate) {

        ArrayList<Integer> runIDs = getAllTestRunIDsFromDate(startDate);
        return getTestSmellsAndCounts(runIDs);
    }

    /**
     * Queries the TSDetect database to retrieve a list of the quantities of specific test smells logged after January first
     * 1990. This should be the equivalent of searching for all entries in the database for the given test smells.
     *
     * @param smells a ArrayList<String> of test smell names. All names are assumed to be of a format match the entries
     *               in the database
     * @return A HashMap<String, Long> keyed on the test names provided in "smells" and with values equal to the number
     *         of occurrences of that test smell in the database since the given date
     */
    public HashMap<String, Long> outTestSmellData(ArrayList<String> smells) {

        ArrayList<Integer> runIDs = getAllTestRunIDsFromDate("1990-01-01");
        return getTestSmellsAndCounts(runIDs, smells);
    }


    /**
     * Queries the TSDetect database to retrieve a list of the quantities of all test smells logged after January first
     * 1990. This should be the equivalent of searching for all entries in the database.
     *
     * @return A HashMap<String, Long> keyed on the test names and with values equal to the number
     *         of occurrences of that test smell in the database since the given date
     */
    public HashMap<String, Long> outTestSmellData() {

        ArrayList<Integer> runIDs = getAllTestRunIDsFromDate("1990-01-01");
        return getTestSmellsAndCounts(runIDs);
    }

    /**
     * A helper function to collect a list of all run ids that have a timestamp equal to or after the given dateRun.
     *
     * @param dateRun  a String object representative of the Timestamp, of the format "yyyy-mm-dd hh:mm:ss" or "yyy-mm-dd"
     * @return an Arraylist<Integers> that is a collection of all run ids that have a timestamp equal to or after the
     *         given dateRun.
     */
    private ArrayList<Integer> getAllTestRunIDsFromDate(String dateRun) {

        ResultSet result_set = null;
        ArrayList<Integer> UIDs = new ArrayList<>();

        // Open a connection
        //System.out.println("open a connection");
        try(Connection conn = dashboardDatasource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(GET_RUN_IDS_FROM_DATE)){ //get the run id generated when this run was added to test_runs
            stmt.setString(1, dateRun);
            result_set = stmt.executeQuery();
            while(result_set.next()){
                UIDs.add(result_set.getInt(1));
            }
        } catch(SQLException e){
            System.out.println("sql exception");
            e.printStackTrace();
        }

        return UIDs;
    }

    /**
     * Performs a set of queries on the TSDetect database that are used to create a collection of test smell names that
     * have occurred in a given list of run ids, and the total number of times each test smell has occurred in those runs
     *
     * @param runID an ArrayList<Integer> that is a collection of run ids associated with the test runs being queried
     * @return a HashMap<String, Long> keyed of the test smell names, which values equal to the total number of times that
     *         smell has occurred
     */
    private HashMap<String, Long> getTestSmellsAndCounts(ArrayList<Integer> runID) {

        ResultSet result_set = null;
        HashMap<String, Long> smellCounts = new HashMap<String, Long>();

        for(int run : runID) {
            // Open a connection
            //System.out.println("open a connection");
            try (Connection conn = dashboardDatasource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(GET_SMELLS_AND_QUANTITIES_FROM_RUN)) { //get the run id generated when this run was added to test_runs
                stmt.setInt(1, run);
                result_set = stmt.executeQuery();
                //loop over all smells in the run
                while (result_set.next()) {
                    String smellID = result_set.getString(1);
                    //if the smell is already in the list, update the count
                    if(smellCounts.containsKey(smellID)){
                        Long count = smellCounts.get(smellID) + result_set.getInt(2);
                        smellCounts.put(smellID, count);
                    }else { //If the smell is not in the list, add it
                        smellCounts.put(smellID,result_set.getLong(2));
                    }
                }
            } catch (SQLException e) {
                System.out.println("sql exception");
                e.printStackTrace();
            }
        }
        return smellCounts;
    }

    /**
     * Performs a set of queries on the TSDetect database that are used to create a collection of test smell names that
     * have occurred in a given list of run ids, and the total number of times each test smell has occurred in those runs
     * This method restricts output to only the test smell names that appear in "smells"
     *
     * @param runID an ArrayList<Integer> that is a collection of run ids associated with the test runs being queried
     * @param smells an ArrayList<String> that is a collection of Test smell names that are desired in the output
     * @return a HashMap<String, Long> keyed of the test smell names, which values equal to the total number of times that
     *         smell has occurred
     */
    private HashMap<String, Long> getTestSmellsAndCounts(ArrayList<Integer> runID, ArrayList<String> smells) {

        ResultSet result_set = null;
        HashMap<String, Long> smellCounts = new HashMap<String, Long>();

        //initialize all smells to a count of zero so that smells that are not found are still included in the output
        //TODO: Do we want to keep this functionality, or remove it and assume any smells that are not included have a 0 count?
        for(String smell : smells)
            smellCounts.put(smell, 0L);


        for(int run : runID) {
            for(String smell : smells) {
                // Open a connection
                //System.out.println("open a connection");
                try (Connection conn = dashboardDatasource.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(GET_SINGLE_SMELL_AND_QUANTITY)) { //get the run id generated when this run was added to test_runs
                    stmt.setInt(1, run);
                    stmt.setString(2, smell);
                    result_set = stmt.executeQuery();
                    while (result_set.next()) {
                        String smellID = result_set.getString(1);
                        if (smellCounts.containsKey(smellID)) {
                            Long count = smellCounts.get(smellID) + result_set.getInt(2);
                            smellCounts.put(smellID, count);
                        }
                    }
                }catch (SQLException e) {
                    System.out.println("sql exception");
                    e.printStackTrace();
                }
            }
        }
        return smellCounts;
    }





}