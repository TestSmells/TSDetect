package org.testsmells.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testsmells.server.repository.DBInputTool;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestResultServiceTest {

    @MockBean
    private DBInputTool bdInputTool;

    @Autowired
    private TestResultService testresultService;

    private HashMap<String, Integer> insert;
    private HashMap<String, Integer> expectedResponse;

    private final String VALID_UUID = "abcd-efgh-ijkl-mnop-qrst-uvwx-yzabcd";
    private final String VALID_TIMESTAMP_STRING= "2023-02-27 19:26:16.408";
    //a timestamp that is the equivalent of the validTimestampString
    private final Timestamp VALID_TIMESTAMP = new java.sql.Timestamp(1677543976408L);

    @BeforeEach
    public void init(){
        insert = new HashMap<>();
        expectedResponse = new HashMap<>();
    }

    @Test
    public void insertOneTestSmellSuccess() {
        //Given
        String jsonInserted = "\"uuid\" : \"" + VALID_UUID + "\",\n" +
                "\"Eager Test\" : \"1\",\n" +
                "\"timestamp\" : \""+ VALID_TIMESTAMP_STRING +"\"";

        //When
        insert.put("Eager Test", 1);

        expectedResponse.put(VALID_UUID, 1);
        expectedResponse.put("Eager Test", 1);

        when(bdInputTool.inputData(VALID_UUID, VALID_TIMESTAMP, insert)).thenReturn(expectedResponse);

        //Then
        assertTrue(testresultService.sendSmells(jsonInserted));
    }

    @Test
    public void insertAllTestSmellSuccess() {
        //Given
        String jsonInserted = "\"Assertion Roulette\" : \"1\",\n"+
                "\"Conditional Test\" : \"2\",\n"+
                "\"Constructor Initialization\" : \"3\",\n"+
                "\"Default Test\" : \"4\",\n"+
                "\"Duplicate Assert\" : \"5\",\n"+
                "\"Eager Test\" : \"6\",\n"+
                "\"Empty Test\" : \"7\",\n"+
                "\"Exception Handling\" : \"8\",\n"+
                "\"General Fixture\" : \"9\",\n"+
                "\"Ignored Test\" : \"10\",\n"+
                "\"Lazy Test\" : \"11\",\n"+
                "\"Magic Number\" : \"12\",\n"+
                "\"Mystery Guest\" : \"13\",\n"+
                "\"Redundant Print\" : \"14\",\n"+
                "\"Redundant Assertion\" : \"15\",\n"+
                "\"Resource Optimism\" : \"16\",\n"+
                "\"Sensitive Equality\" : \"17\",\n"+
                "\"Sleepy Test\" : \"18\",\n"+
                "\"Unknown Test\" : \"19\",\n"+
                "\"Verbose Test\" : \"20\",\n"+
                "\"uuid\" : \"" + VALID_UUID + "\",\n" +
                "\"timestamp\" : \"" + VALID_TIMESTAMP_STRING + "\"";

        //When
        insert.put("Assertion Roulette", 1);
        insert.put("Conditional Test", 2);
        insert.put("Constructor Initialization", 3);
        insert.put("Default Test", 4);
        insert.put("Duplicate Assert", 5);
        insert.put("Eager Test", 6);
        insert.put("Empty Test", 7);
        insert.put("Exception Handling", 8);
        insert.put("General Fixture", 9);
        insert.put("Ignored Test", 10);
        insert.put("Lazy Test", 11);
        insert.put("Magic Number", 12);
        insert.put("Mystery Guest", 13);
        insert.put("Redundant Print", 14);
        insert.put("Redundant Assertion", 15);
        insert.put("Resource Optimism", 16);
        insert.put("Sensitive Equality", 17);
        insert.put("Sleepy Test", 18);
        insert.put("Unknown Test", 19);
        insert.put("Verbose Test", 20);

        expectedResponse.put(VALID_UUID, 1);
        expectedResponse.put("Assertion Roulette", 1);
        expectedResponse.put("Conditional Test", 2);
        expectedResponse.put("Constructor Initialization", 3);
        expectedResponse.put("Default Test", 4);
        expectedResponse.put("Duplicate Assert", 5);
        expectedResponse.put("Eager Test", 6);
        expectedResponse.put("Empty Test", 7);
        expectedResponse.put("Exception Handling", 8);
        expectedResponse.put("General Fixture", 9);
        expectedResponse.put("Ignored Test", 10);
        expectedResponse.put("Lazy Test", 11);
        expectedResponse.put("Magic Number", 12);
        expectedResponse.put("Mystery Guest", 13);
        expectedResponse.put("Redundant Print", 14);
        expectedResponse.put("Redundant Assertion", 15);
        expectedResponse.put("Resource Optimism", 16);
        expectedResponse.put("Sensitive Equality", 17);
        expectedResponse.put("Sleepy Test", 18);
        expectedResponse.put("Unknown Test", 19);
        expectedResponse.put("Verbose Test", 20);

        when(bdInputTool.inputData(VALID_UUID, VALID_TIMESTAMP, insert)).thenReturn(expectedResponse);

        //then
        assertTrue(testresultService.sendSmells(jsonInserted));
    }

//    //cannot test entering same smell twice at this level

    //TODO: Is this actually the functionality we are expecting?
    @Test
    public void insertNoTestSmellsFail() {
        //Given
        String jsonInserted = "\"uuid\" : \"" + VALID_UUID + "\",\n" +
                "\"timestamp\" : \"" + VALID_TIMESTAMP_STRING + "\"";

        //When
        expectedResponse.put(VALID_UUID, 0);

        when(bdInputTool.inputData(VALID_UUID, VALID_TIMESTAMP, insert)).thenReturn(expectedResponse);

        //Then
        assertFalse(testresultService.sendSmells(jsonInserted));
    }

    //TODO: Is this actually the functionality we are expecting?
    @Test
    public void insertNonExistantTestSmellFail() {
        //Given
        String jsonInserted = "\"This Is Not A Smell\" : \"1\",\n" +
                "\"uuid\" : \"" + VALID_UUID + "\",\n" +
                "\"timestamp\" : \"" + VALID_TIMESTAMP_STRING + "\"";

        //When
        insert.put("This Is Not A Smell", 1);

        expectedResponse.put(VALID_UUID, 0);

        when(bdInputTool.inputData(VALID_UUID, VALID_TIMESTAMP, insert)).thenReturn(expectedResponse);

        //Then
        assertFalse(testresultService.sendSmells(jsonInserted));
    }

    @Test
    public void insertNoneExistantTestSmellAndRealTestSmellFail() {
        //Given
        String jsonInserted = "\"This Is Not A Smell\" : \"1\",\n" +
                "\"Assertion Roulette\" : \"2\",\n" +
                "\"uuid\" : \"" + VALID_UUID + "\",\n" +
                "\"timestamp\" : \"" + VALID_TIMESTAMP_STRING + "\"";

        //When
        insert.put("This Is Not A Smell", 1);
        insert.put("Assertion Roulette", 2);

        expectedResponse.put(VALID_UUID, 1);
        expectedResponse.put("Assertion Roulette", 2);

        when(bdInputTool.inputData(VALID_UUID, VALID_TIMESTAMP, insert)).thenReturn(expectedResponse);

        //Then
        assertTrue(testresultService.sendSmells(jsonInserted));
    }

    @Test
    public void insertTestSmellBadUUIDFail() {
        //Given
        String jsonInserted = "\"Eager Test\" : \"1\",\n" +
                "\"uuid\" : \"Not a Real UUID\",\n" +
                "\"timestamp\" : \"" + VALID_TIMESTAMP_STRING + "\"";

        //When
        //dbInput tool should never be reached in this case

        //Then
        assertFalse(testresultService.sendSmells(jsonInserted));
    }

    @Test
    public void insertTestSmellNoUUIDFail() {
        //Given
        String jsonInserted = "\"Eager Test\" : \"1\",\n" +
                "\"timestamp\" : \"" + VALID_TIMESTAMP_STRING + "\"";

        //When
        //dbInput tool should never be reached in this case

        //Then
        assertFalse(testresultService.sendSmells(jsonInserted));
    }

    @Test
    public void insertTestSmellBadTimestampFail() {
        //Given
        String jsonInserted = "\"Eager Test\" : \"1\",\n" +
                "\"uuid\" : \"" + VALID_UUID + "\",\n" +
                "\"timestamp\" : \"Not a Real timestamp\"";

        //When
        //dbInput tool should never be reached in this case

        //Then
        assertFalse(testresultService.sendSmells(jsonInserted));
    }

    @Test
    public void insertTestSmellNoTimestampFail() {
        //Given
        String jsonInserted = "\"Eager Test\" : \"1\",\n" +
                "\"uuid\" : \"" + VALID_UUID + "\"";

        //When
        //dbInput tool should never be reached in this case

        //Then
        assertFalse(testresultService.sendSmells(jsonInserted));
    }

    @Test
    public void insertTestSmellInvalidJsonFormatExtraniousTextFail() {
        //Given
        String jsonInserted = "\"Eager Test\" : \"1\",\n" +
                "\"uuid\" : \"" + VALID_UUID + "\",\n" +
                "\"timestamp\" : \"" + VALID_TIMESTAMP_STRING + "\"" +
                "Some Extra Stuff";

        //When
        //dbInput tool should never be reached in this case

        //Then
        assertFalse(testresultService.sendSmells(jsonInserted));
    }

    @Test
    public void insertTestSmellInvalidJsonFormatBadDelimitersFail() {
        //Given
        String jsonInserted = "\"Eager Test\" - \"1\",\n" +
                "\"uuid\" - \"" + VALID_UUID + "\",\n" +
                "\"timestamp\" - \"" + VALID_TIMESTAMP_STRING + "\"";
        //When
        //dbInput tool should never be reached in this case

        //Then
        assertFalse(testresultService.sendSmells(jsonInserted));
    }

    @Test
    public void insertTestSmellInvalidJsonFormatExtraLineFail() {
        //Given
        String jsonInserted = "\"Eager Test\" - \"1\",\n" +
                "\"uuid\" - \"" + VALID_UUID + "\",\n" +
                "\"timestamp\" - \"" + VALID_TIMESTAMP_STRING + "\"\n";

        //When
        insert.put("Eager Test", 1);

        expectedResponse.put(VALID_UUID, 1);
        expectedResponse.put("Eager Test", 1);

        when(bdInputTool.inputData(VALID_UUID, VALID_TIMESTAMP, insert)).thenReturn(expectedResponse);

        //Then
        assertFalse(testresultService.sendSmells(jsonInserted));
    }

    @Test
    public void validUUID() {
        //Then
        assertTrue(testresultService.isValidUuid(VALID_UUID));
    }

    @Test
    public void invalidUUIDLength35() {
        //Then
        assertFalse(testresultService.isValidUuid("12345678901234567890123456789012345"));
    }

    @Test
    public void invalidUUIDLength37() {
        //Then
        assertFalse(testresultService.isValidUuid("1234567890123456789012345678901234567"));
    }

    @Test
    public void invalidUUIDLength0() {
        //Then
        assertFalse(testresultService.isValidUuid(""));
    }

    @Test
    public void invalidUUIDLengthNull() {
        //Then
        assertFalse(testresultService.isValidUuid(null));
    }
}
