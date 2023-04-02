package org.scanl.plugins.tsdetect.output;

import org.junit.Test;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.config.application.AppSettingsState;
import org.scanl.plugins.tsdetect.model.AnonymousData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/*
    Class containing unit tests for exporting data from the local plugin to the API layers classes
 */
public class AnonymousDataOutputTest {
    AnonymousData ad;
    PluginSettings ps = Mockito.mock(PluginSettings.class);
    AppSettingsState appss;
    AppSettingsState appsettstatedummy;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    @BeforeAll
    public static void suiteSetup(){

    }

    @AfterAll
    public static void suiteTeardown(){

    }

//    @BeforeEach
//    protected void setup(){
//        appss = Mockito.mock(AppSettingsState.class);
//        appsettstatedummy = new AppSettingsState();
//        ad = Mockito.mock(AnonymousData.class);
//        ps = Mockito.mock(PluginSettings.class);
//        System.setOut(new PrintStream(out));
//    }
//
//    @AfterEach
//    protected void teardown(){
//        appss = null;
//        ad = null;
//        ps = null;
//        System.setOut(originalOut);
//    }

    @Test
    public void uuidGenSuccess(){
        //Verifies the uuid method returns a 36 character alphanumeric string
        String regex = "^[a-zA-Z0-9]{36}$";
        when(ps.uuid()).thenReturn("sdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsd");
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
    public void sendDataSuccess() throws IOException {
        ad.sendData();
        Mockito.verify(ad).postRequest("test");
    }

    @Test
    public void sendDataFail(){

    }

    @Test
    public void postRequestSuccess(){
        //call postrequest w/ appropriate mocks
        assertEquals("SUCCESS", out.toString());
    }

    @Test
    public void postRequestFail() throws IOException {
        ad.postRequest("bad test");
        Mockito.verify(ad, Mockito.times(5)).localSave("bad test");
    }


}
