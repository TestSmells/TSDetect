package org.testsmells.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.testsmells.server.service.ExampleService;
import org.testsmells.server.tables.pojos.TestSmells;

import java.util.List;

@RestController
public class ExampleController {

    @Autowired
    ExampleService exampleService;

    @RequestMapping(path = "/test-smells", method = RequestMethod.GET)
    public ResponseEntity<List<TestSmells>> getTestSmells() {
        return ResponseEntity.ok(exampleService.getTestSmells());
    }

}
