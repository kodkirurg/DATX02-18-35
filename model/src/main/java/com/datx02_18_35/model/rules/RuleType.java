package com.datx02_18_35.model.rules;

import java.util.HashSet;
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
    ABSURDITY_ELIMINATION;
    //ABSURDITY_INTRODUCTION,
    //LAW_OF_EXCLUDED_MIDDLE

    public static class Sets {

        public static final Set<RuleType> CONSTRUCTIVE = new HashSet<>();
        static {
            CONSTRUCTIVE.add(IMPLICATION_ELIMINATION);
            CONSTRUCTIVE.add(IMPLICATION_INTRODUCTION);
            CONSTRUCTIVE.add(CONJUNCTION_ELIMINATION);
            CONSTRUCTIVE.add(CONJUNCTION_INTRODUCTION);
            CONSTRUCTIVE.add(DISJUNCTION_ELIMINATION);
            CONSTRUCTIVE.add(DISJUNCTION_INTRODUCTION);
            CONSTRUCTIVE.add(ABSURDITY_ELIMINATION);
        }

        public static final Set<RuleType> DEFAULT = CONSTRUCTIVE;

        public static final Set<RuleType> IMPLICATION_ONLY = new HashSet<>();
        static {
            IMPLICATION_ONLY.add(IMPLICATION_ELIMINATION);
            IMPLICATION_ONLY.add(IMPLICATION_INTRODUCTION);
        }

        public static final Set<RuleType> CONJUNCTION_ONLY = new HashSet<>();
        static {
            CONJUNCTION_ONLY.add(CONJUNCTION_ELIMINATION);
            CONJUNCTION_ONLY.add(CONJUNCTION_INTRODUCTION);
        }

        public static final Set<RuleType> DISJUNCTION_ONLY = new HashSet<>();
        static {
            DISJUNCTION_ONLY.add(DISJUNCTION_ELIMINATION);
            DISJUNCTION_ONLY.add(DISJUNCTION_INTRODUCTION);
        }
    }
}