package org.scanl.plugins.tsdetect.output;

import org.apache.groovy.json.internal.Type;
import org.junit.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import org.scanl.plugins.tsdetect.config.PluginSettings;

/*
    Class containing unit tests for exporting data from the local plugin to the API layers classes
 */
public class AnonymousDataOutputTest {

    @BeforeAll
    public static void suiteSetup(){

    }

    @AfterAll
    public static void suiteTeardown(){

    }

    @BeforeEach
    protected void setup(){
    }

    @AfterEach
    protected void teardown(){
    }

    @Test
    public void uuidGenSuccess(){
        //Verifies the uuid method returns a 36 character alphanumeric string
        String regex = "^[a-zA-Z0-9]{36}$";
        String result = PluginSettings.uuid();
        assertTrue(result.matches(regex));

    }

    @Test
    public void addDataSuccess(){

    }

    @Test
    public void addDataFail(){

    }

    @Test
    public void sendDataSuccess(){

    }

    @Test
    public void sendDataFail(){

    }

    @Test
    public void postRequestSuccess(){

    }

    @Test
    public void postRequestFail(){

    }


}
