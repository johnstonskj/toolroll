/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.query;

import java.util.Map;

import org.johnstonshome.utils.fun.UnaryPredicate;

/**
 * @author simonjo
 *
 */
public class Predicates {

    private static class Equals implements UnaryPredicate<Map<String,Object>> {
        private final String column;
        private final Object value;
        public Equals(final String column, final Object value) {
            this.column = column;
            this.value = value;
        }
        @SuppressWarnings("boxing")
        @Override
        public Boolean call(Map<String, Object> row) {
            return row.get(this.column).equals(this.value);
        }
    }
    
    private static class Not implements UnaryPredicate<Map<String,Object>> {
        private final UnaryPredicate<Map<String,Object>> predicate;
        public Not(final UnaryPredicate<Map<String,Object>> predicate) {
            this.predicate = predicate;
        }
        @SuppressWarnings("boxing")
        @Override
        public Boolean call(Map<String, Object> row) {
            return !this.predicate.call(row);
        }
    }

    private static class And implements UnaryPredicate<Map<String,Object>> {
        private final UnaryPredicate<Map<String,Object>> lhs;
        private final UnaryPredicate<Map<String,Object>> rhs;
        public And(final UnaryPredicate<Map<String,Object>> lhs, final UnaryPredicate<Map<String,Object>> rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }
        @SuppressWarnings("boxing")
        @Override
        public Boolean call(Map<String, Object> row) {
            return (this.lhs.call(row) && this.rhs.call(row));
        }
    }

    private static class Or implements UnaryPredicate<Map<String,Object>> {
        private final UnaryPredicate<Map<String,Object>> lhs;
        private final UnaryPredicate<Map<String,Object>> rhs;
        public Or(final UnaryPredicate<Map<String,Object>> lhs, final UnaryPredicate<Map<String,Object>> rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }
        @SuppressWarnings("boxing")
        @Override
        public Boolean call(Map<String, Object> row) {
            return (this.lhs.call(row) || this.rhs.call(row));
        }
    }

    public static UnaryPredicate<Map<String,Object>> equals(final String column, final Object value) {
        return new Equals(column, value);
    }

    public static UnaryPredicate<Map<String,Object>> not(UnaryPredicate<Map<String,Object>> predicate) {
        return new Not(predicate);
    }

    public static UnaryPredicate<Map<String,Object>> and(final UnaryPredicate<Map<String,Object>> lhs, final UnaryPredicate<Map<String,Object>> rhs) {
        return new And(lhs, rhs);
    }

    public static UnaryPredicate<Map<String,Object>> or(final UnaryPredicate<Map<String,Object>> lhs, final UnaryPredicate<Map<String,Object>> rhs) {
        return new Or(lhs, rhs);
    }
}
