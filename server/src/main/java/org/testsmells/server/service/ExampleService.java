package org.testsmells.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testsmells.server.repository.ExampleRepository;
import org.testsmells.server.tables.pojos.TestSmells;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExampleService {

    @Autowired
    ExampleRepository exampleRepository;

//    public List<TestSmells> getTestSmells() {
//        try {
//            return exampleRepository.getSmellTypes();
//        } catch (SQLException e) {
//            return new ArrayList<>();
//        }
//    }

}
