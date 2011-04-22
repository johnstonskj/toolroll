/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.johnstonshome.utils.fun.UnaryFunction;
import org.johnstonshome.utils.fun.UnaryPredicate;

public class Query {

    private final Source source;
    
    private Query(final Source source) {
        this.source = source;
    }
    
    public static Query from(final Collection<?> source) {
        return new Query(Source.source(source));
    }
    
    public static Query from(final Source source) {
        return new Query(source);
    }

    public Collection<Map<String, Object>> getResults() {
        return this.source.getRows();
    }
    
    public Query where(final UnaryPredicate<Map<String, Object>> predicate) {
        final Collection<Map<String, Object>> results = new LinkedList<Map<String,Object>>();
        for (final Map<String, Object> row : this.source.getRows()) {
            if (predicate.call(row).booleanValue() == true) {
                results.add(row);
            }
        }
        return new Query(Source.source(this.source.getName(), results));
    }
    
    public Query join(final Source secondSource, 
            final String firstKey, final String secondKey) {
        final Map<Object, Map<String, Object>> index = new HashMap<Object, Map<String,Object>>();
        for (Map<String, Object> map : secondSource.getRows()) {
            index.put(map.get(secondKey), map);
        }
        final Collection<Map<String, Object>> results = new LinkedList<Map<String,Object>>();
        for (Map<String, Object> map : this.source.getRows()) {
            final Object key = map.get(firstKey);
            if (key != null && index.containsKey(key)) {
                final Map<String, Object> result = new HashMap<String, Object>();
                for (Entry<String, Object> entry : map.entrySet()) {
                    result.put(String.format("%s.%s", this.source.getName(), entry.getKey()), entry.getValue());
                }
                for (Entry<String, Object> entry : index.get(key).entrySet()) {
                    result.put(String.format("%s.%s", secondSource.getName(), entry.getKey()), entry.getValue());
                }
                results.add(result);
            }
        }
        return new Query(Source.source(results));
    }

    public Query select(final Collection<Column> columns) {
        final Collection<Map<String, Object>> results = new LinkedList<Map<String,Object>>();
        for (final Map<String, Object> row : this.source.getRows()) {
            final Map<String, Object> result = new HashMap<String, Object>();
            for (final Column column : columns) {
                column.apply(row, result);
            }
            results.add(result);
        }
        return new Query(Source.source(this.source.getName(), results));
    }
    
    public Query select(final Column... columns) {
        Collection<Column> collColumns = new LinkedList<Column>();
        for (final Column column : columns) {
            collColumns.add(column);
        }
        return select(collColumns);
    }

    public Query select(final String first, final String... columns) {
        Collection<Column> collColumns = new LinkedList<Column>();
        collColumns.add(new Column(first));
        for (final String column : columns) {
            collColumns.add(new Column(column));
        }
        return select(collColumns);
    }

    public Query select(final String[] columns, final UnaryFunction<Map<String, Object>, Object>... functions) {
        Collection<Column> collColumns = new LinkedList<Column>();
        for (final String column : columns) {
            collColumns.add(new Column(column));
        }
        int f = 1;
        for (final UnaryFunction<Map<String, Object>, Object> function : functions) {
            collColumns.add(new Column(function, "fun-" + String.valueOf(f++)));
        }
        return select(collColumns);
    }
    
    /*
     * TODO: sort, group
     */
    
    public Query sort(final String column) {
        SortedMap<Object, Map<String, Object>> index = new TreeMap<Object, Map<String,Object>>();
        for (Map<String, Object> map : this.source.getRows()) {
            if (map.containsKey(column)) {
                index.put(map.get(column), map);
            }
        }
        return new Query(Source.source(index.values()));
    }
}