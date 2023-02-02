package org.testsmells.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testsmells.server.repository.DBOutputTool;
import org.testsmells.server.repository.ExampleRepository;
import org.testsmells.server.tables.pojos.TestSmells;

import java.sql.SQLException;
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

}
