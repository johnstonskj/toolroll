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

import org.johnstonshome.utils.fun.UnaryFunction;

/**
 * @author simonjo
 *
 */
public class Column {

    private final String column;
    private final UnaryFunction<Map<String, Object>, Object> function;
    private final String label; 
    
    public Column(final String column) {
        this.column = column;
        this.label = column;
        this.function = null;
    }

    public Column(final String column, final String label) {
        this.column = column;
        this.label = label;
        this.function = null;
    }

    public Column(final UnaryFunction<Map<String, Object>, Object> function, final String label) {
        this.column = null;
        this.label = label;
        this.function = function;
    }
    
    public void apply(final Map<String, Object> row, final Map<String, Object> result) {
        if (this.column != null) {
            if (row.containsKey(this.column)) {
                result.put(this.label, row.get(this.column));
            } else {
                result.put(this.label, null);
            }
        } else {
            result.put(this.label, this.function.call(row));
        }
    }
}
