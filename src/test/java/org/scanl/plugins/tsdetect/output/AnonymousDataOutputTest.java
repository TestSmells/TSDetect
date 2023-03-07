package org.scanl.plugins.tsdetect.output;

import org.apache.groovy.json.internal.Type;
import org.junit.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.AnonymousData;

/*
    Class containing unit tests for exporting data from the local plugin to the API layers classes
 */
public class AnonymousDataOutputTest {
    AnonymousData ad;
    PluginSettings ps;
    @BeforeAll
    public static void suiteSetup(){

    }

    @AfterAll
    public static void suiteTeardown(){

    }

    @BeforeEach
    protected void setup(){
        ad = new AnonymousData();
        ps = new PluginSettings();
    }

    @AfterEach
    protected void teardown(){
        ad = null;
        ps = null;
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
        ad.addData("uuid", "sdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsd");
        ad.addData("timestamp", "2023-02-07 18:26:50.952");
        ad.addData("ASSERTION_ROULETTE", "1");
        //assertEquals(ad.data, );
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
