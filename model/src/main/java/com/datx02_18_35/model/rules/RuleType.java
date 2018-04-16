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
        private static final Set<RuleType> IMPLICATION = new HashSet<>();
        private static final Set<RuleType> CONJUNCTION = new HashSet<>();
        private static final Set<RuleType> DISJUNCTION = new HashSet<>();
        private static final Set<RuleType> DEFAULT = CONSTRUCTIVE;

        static {
            parseMap.put("DEFAULT",             DEFAULT);
            parseMap.put("CONSTRUCTIVE",        CONSTRUCTIVE);
            parseMap.put("CLASSICAL",           CLASSICAL);
            parseMap.put("IMPLICATION", IMPLICATION);
            parseMap.put("CONJUNCTION", CONJUNCTION);
            parseMap.put("DISJUNCTION", DISJUNCTION);

            for (RuleType type : values()) {
                Set<RuleType> ruleTypeSet = new HashSet<>();
                ruleTypeSet.add(type);
                parseMap.put(type.name(), ruleTypeSet);
            }

            CONSTRUCTIVE.add(IMPLICATION_ELIMINATION);
            CONSTRUCTIVE.add(IMPLICATION_INTRODUCTION);
            CONSTRUCTIVE.add(CONJUNCTION_ELIMINATION);
            CONSTRUCTIVE.add(CONJUNCTION_INTRODUCTION);
            CONSTRUCTIVE.add(DISJUNCTION_ELIMINATION);
            CONSTRUCTIVE.add(DISJUNCTION_INTRODUCTION);
            CONSTRUCTIVE.add(ABSURDITY_ELIMINATION);

            CLASSICAL.addAll(CONSTRUCTIVE);
            CLASSICAL.add(LAW_OF_EXCLUDED_MIDDLE);

            IMPLICATION.add(IMPLICATION_ELIMINATION);
            IMPLICATION.add(IMPLICATION_INTRODUCTION);

            CONJUNCTION.add(CONJUNCTION_ELIMINATION);
            CONJUNCTION.add(CONJUNCTION_INTRODUCTION);

            DISJUNCTION.add(DISJUNCTION_ELIMINATION);
            DISJUNCTION.add(DISJUNCTION_INTRODUCTION);
        }
    }
}