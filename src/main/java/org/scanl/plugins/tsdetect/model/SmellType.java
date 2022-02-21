package org.scanl.plugins.tsdetect.model;

/**
 * A list of the enums of SmellTypes, create a new one when adding a new inspection and
 * everything with the action should work
 */
public enum SmellType {
    CONDITIONAL_TEST,
    DEFAULT_TEST,
    DUPLICATE_ASSERT,
    EMPTY_TEST,
    EAGER_TEST,
    GENERAL_FIXTURE,
    LAZY_TEST,
    REDUNDANT_PRINT,
    SLEEPY_TEST
}
