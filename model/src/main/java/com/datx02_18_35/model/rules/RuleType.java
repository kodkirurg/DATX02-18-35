package com.datx02_18_35.model.rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by robin on 2018-02-14.
 */

public enum RuleType {
    IMPLICATION_ELIMINATION,
    IMPLICATION_INTRODUCTION, //special case in view
    CONJUNCTION_ELIMINATION,
    CONJUNCTION_INTRODUCTION,
    DISJUNCTION_ELIMINATION,
    DISJUNCTION_INTRODUCTION,
    ABSURDITY_ELIMINATION,
    LAW_OF_EXCLUDED_MIDDLE;

    public static class Sets {

        public static Set<RuleType> get(String argument) {
            return parseMap.get(argument);
        }
        public static Set<RuleType> getDefault() {
            return DEFAULT;
        }

        private static final Map<String, Set<RuleType>> parseMap = new HashMap<>();
        private static final Set<RuleType> CONSTRUCTIVE = new HashSet<>();
        private static final Set<RuleType> CLASSICAL = new HashSet<>();
        private static final Set<RuleType> IMPLICATION_ONLY = new HashSet<>();
        private static final Set<RuleType> CONJUNCTION_ONLY = new HashSet<>();
        private static final Set<RuleType> DISJUNCTION_ONLY = new HashSet<>();
        private static final Set<RuleType> DEFAULT = CONSTRUCTIVE;

        static {
            parseMap.put("DEFAULT",             DEFAULT);
            parseMap.put("CONSTRUCTIVE",        CONSTRUCTIVE);
            parseMap.put("CLASSICAL",           CLASSICAL);
            parseMap.put("IMPLICATION_ONLY",    IMPLICATION_ONLY);
            parseMap.put("CONJUNCTION_ONLY",    CONJUNCTION_ONLY);
            parseMap.put("DISJUNCTION_ONLY",    DISJUNCTION_ONLY);

            CONSTRUCTIVE.add(IMPLICATION_ELIMINATION);
            CONSTRUCTIVE.add(IMPLICATION_INTRODUCTION);
            CONSTRUCTIVE.add(CONJUNCTION_ELIMINATION);
            CONSTRUCTIVE.add(CONJUNCTION_INTRODUCTION);
            CONSTRUCTIVE.add(DISJUNCTION_ELIMINATION);
            CONSTRUCTIVE.add(DISJUNCTION_INTRODUCTION);
            CONSTRUCTIVE.add(ABSURDITY_ELIMINATION);

            CLASSICAL.addAll(CONSTRUCTIVE);
            CLASSICAL.add(LAW_OF_EXCLUDED_MIDDLE);

            IMPLICATION_ONLY.add(IMPLICATION_ELIMINATION);
            IMPLICATION_ONLY.add(IMPLICATION_INTRODUCTION);

            CONJUNCTION_ONLY.add(CONJUNCTION_ELIMINATION);
            CONJUNCTION_ONLY.add(CONJUNCTION_INTRODUCTION);

            DISJUNCTION_ONLY.add(DISJUNCTION_ELIMINATION);
            DISJUNCTION_ONLY.add(DISJUNCTION_INTRODUCTION);
        }
    }
}