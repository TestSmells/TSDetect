package org.testsmells.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testsmells.server.repository.DBOutputTool;
import org.testsmells.server.repository.ExampleRepository;
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

    public HashMap<String, Long> getTestSmells() {
        //sql exception is handled in the DBOutputTool
        return outputRepository.outTestSmellData();
    }

    public HashMap<String, Long> getTestSmells(ArrayList<String> smells) {
        //sql exception is handled in the DBOutputTool
        return outputRepository.outTestSmellData(smells);
    }

    public HashMap<String, Long> getTestSmells(Timestamp timestamp) {
        //sql exception is handled in the DBOutputTool
        return outputRepository.outTestSmellData(timestamp);
    }

    public HashMap<String, Long> getTestSmells(Timestamp timestamp, ArrayList<String> smells) {
        //sql exception is handled in the DBOutputTool
        return outputRepository.outTestSmellData(timestamp, smells);
    }

}
