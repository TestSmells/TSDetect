package org.scanl.plugins.tsdetect.model;

/**
 * A list of the enums of SmellTypes, create a new one when adding a new inspection and
 * everything with the action should work
 */
public enum SmellType {
    ASSERTION_ROULETTE,
    CONDITIONAL_TEST,
    CONSTRUCTOR_INITIALIZATION,
    DEFAULT_TEST,
    DUPLICATE_ASSERT,
    EMPTY_TEST,
    EAGER_TEST,
    EXCEPTION_HANDLING,
    GENERAL_FIXTURE,
    IGNORED_TEST,
    LAZY_TEST,
    MYSTERY_GUEST,
    REDUNDANT_PRINT,
    RESOURCE_OPTIMISM,
    SENSITIVE_EQUALITY,
    SLEEPY_TEST
}
