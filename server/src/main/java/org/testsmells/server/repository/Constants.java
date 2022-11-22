package org.testsmells.server.repository;

public class Constants {

    //expected Test smell names and formats
    public static final String ASSERTION_ROULETTE = "Assertion Roulette";
    public static final String CONDITIONAL_TEST_LOGIC = "Conditional Test Logic";
    public static final String CONSTRUCTOR_INITIALIZATION = "Constructor Initialization";
    public static final String DEFAULT_TEST = "Default Test";
    public static final String DUPLICATE_ASSERT = "Duplicate Assert";
    public static final String EAGER_TEST = "Eager Test";
    public static final String EMPTY_TEST = "Empty Test";
    public static final String EXCEPTION_HANDLING = "Exception Handling";
    public static final String GENERAL_FIXTURE = "General Fixture";
    public static final String IGNORED_TEST = "Ignored Test";
    public static final String LAZY_TEST = "Lazy Test";
    public static final String MAGIC_NUMBER_TEST = "Magic Number Test";
    public static final String MYSTERY_GUEST = "Mystery Guest";
    public static final String REDUNDANT_PRINT = "Redundant Print";
    public static final String REDUNDANT_ASSERTION = "Redundant Assertion";
    public static final String RESOURCE_OPTIMISM = "Resource Optimism";
    public static final String SENSITIVE_EQUALITY = "Sensitive Equality";
    public static final String SLEEPY_TEST = "Sleepy Test";
    public static final String UNKNOWN_TEST = "Unknown Test";


    //prepared queries
    public static final String GET_ALL_TEST_RUNS_QUERY = "SELECT * FROM `test_runs`";
    public static final String ADD_TEST_RUN_QUERY = "INSERT INTO `test_runs` (`uid`, `timestamp`) VALUES (?, ?);";
    public static final String ADD_TEST_RUN_SMELLS_QUERY = "INSERT INTO `test_run_smells` (`run_id`, `test_smell_id`, `quantity`) VALUES (?, ?, ?);";
    public static final String GET_TEST_SMELL_ID_FROM_NAME = "SELECT * FROM `test_smells` WHERE `name` LIKE ?";
    public static final String GET_RUN_ID = "SELECT * FROM `test_runs` WHERE `uid` LIKE ? AND `timestamp` = ?";
    public static final String GET_RUN_IDS_FROM_DATE = "SELECT `run_id` FROM `test_runs`WHERE `test_runs`.`timestamp` >= ?;";
    public static final String GET_SMELLS_AND_QUANTITIES_FROM_RUN = "SELECT `test_smells`.`name`, `test_run_smells`.`quantity`FROM `test_run_smells`" +
            "INNER JOIN `test_smells` ON `test_run_smells`.`test_smell_id` = `test_smells`.`test_smell_id` WHERE `test_run_smells`.`" +
            "run_id` = ?";

    public static final String GET_SINGLE_SMELL_AND_QUANTITY = "SELECT `test_smells`.`name`, `test_run_smells`.`quantity`FROM `test_run_smells`" +
            "INNER JOIN `test_smells` ON `test_run_smells`.`test_smell_id` = `test_smells`.`test_smell_id` WHERE `test_run_smells`.`" +
            "run_id` = ? AND `test_smells`.`name` = ?";

}