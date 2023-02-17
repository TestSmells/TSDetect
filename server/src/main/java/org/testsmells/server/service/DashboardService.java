package org.testsmells.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testsmells.server.repository.DBOutputTool;
import org.testsmells.server.tables.pojos.TestSmells;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    DBOutputTool outputRepository;

    /**
     * Queries the entire database for all test smell data.
     * @return a hashmap of all tests smells keyed to their quantities over the entire database
     */
    public HashMap<String, Long> getTestSmells() {
        //sql exception is handled in the DBOutputTool
        return outputRepository.outTestSmellData();
    }

    /**
     * Queries the entire database for all test smell data related to the test smells in the given list.
     * @param smells a list of test smells to search for test smell data for
     * @return a hashmap of the given test smells with data found in the database keyed to their quantities over the entire database
     */
    public HashMap<String, Long> getTestSmells(ArrayList<String> smells) {
        //sql exception is handled in the DBOutputTool
        return outputRepository.outTestSmellData(smells);
    }

    /**
     * Queries the entire database for all test smell data with a timestamp equal to the given timestamp or later.
     * @param timestamp the timestamp to begin searching for data from
     * @return a hashmap of all test smells with data found in the database keyed to their quantities in the database
     *         with a timestamp equal to the given timestamp or later
     */
    public HashMap<String, Long> getTestSmells(Timestamp timestamp) {
        //sql exception is handled in the DBOutputTool
        return outputRepository.outTestSmellData(timestamp);
    }

    /**
     * Queries the entire database for all test smell data related to the test smells in the given list with a timestamp
     * equal to the given timestamp or later.
     * @param timestamp the timestamp to begin searching for data from
     * @param smells a list of test smells to search for test smell data for
     * @return a hashmap of all test smells with data found in the database keyed to their quantities in the database
     *         with a timestamp equal to the given timestamp or later
     */
    public HashMap<String, Long> getTestSmells(Timestamp timestamp, ArrayList<String> smells) {
        //sql exception is handled in the DBOutputTool
        return outputRepository.outTestSmellData(timestamp, smells);
    }

}
