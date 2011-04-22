/*
 * Copyright (c) 2011 Amazon.com Inc. All Rights Reserved.
 * Amazon.com Confidential
 */

package org.johnstonshome.utils.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.johnstonshome.utils.fun.Maps;

/**
 * @author simonjo
 *
 */
public class Source {

    private static Map<String, Object> EMPTY_ROW = new HashMap<String, Object>();
    
    private final String name;
    private final Collection<Map<String, Object>> rows;
    
    private Source(final Collection<?> collection, final String name) {
        this.name = name;
        this.rows = from(collection);
    }
    
    public static Source source(final Collection<?> collection) {
        return new Source(collection, null);
    }

    public static Source source(final String name, final Collection<?> collection) {
        return new Source(collection, name);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Collection<Map<String, Object>> getRows() {
        return this.rows;
    }

    private static Collection<Map<String, Object>> from(final Collection<?> source) {
        LinkedList<Map<String, Object>> results = new LinkedList<Map<String, Object>>();
        for (Object o : source) {
            if (o instanceof Map<?, ?>) {
                results.add(from((Map<?, ?>)o));
            } else {
                results.add(Maps.beanMap(o));
            }
        }
        return results;
    }
    
    @SuppressWarnings("unchecked")
    private static Map<String, Object> from(final Map<?,?> source) {
        if (source.size() == 0) {
            return EMPTY_ROW;
        } else {
            if (source.keySet().iterator().next() instanceof String) {
                return (Map<String, Object>)source;
            } else {
                final Map<String, Object> results = new HashMap<String, Object>();
                for (final Entry<?,?> entry : source.entrySet()) {
                    results.put(entry.getKey().toString(), entry.getValue());
                }
                return results;
            }
        }
    }
}
