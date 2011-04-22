/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import org.johnstonshome.utils.fun.UnaryFunction;
import org.johnstonshome.utils.query.Predicates;
import org.johnstonshome.utils.query.Query;
import org.johnstonshome.utils.query.Source;

/**
 * @author simonjo
 *
 */
public class QueryTests {

    private static QueryData data = new QueryData();
    
    private class ToUpper implements UnaryFunction<Map<String,Object>, Object> {
        private final String column;
        public ToUpper(final String column) {
            this.column = column;
        }
        @Override
        public Object call(Map<String, Object> value) {
            return ((String)value.get(this.column)).toUpperCase();
        }
    }

    @Test
    public void testSimpleObjectProjection() throws Exception {
        Query query = Query.from(data.getPeopleData());
        assertEquals(3, query.getResults().size());
        System.out.println(query.getResults());
    }

    @Test
    public void testSimpleMapProjection() throws Exception {
        Query query = Query.from(data.getPeopleDataMap());
        assertEquals(3, query.getResults().size());
        System.out.println(query.getResults());
    }

    @Test
    public void testSimpleObjectFilter() throws Exception {
        Query query = Query.
            from(data.getPeopleData()).
            where(Predicates.equals("address", "L.A."));
        assertEquals(1, query.getResults().size());
        System.out.println(query.getResults());
        
        query = Query.
            from(data.getPeopleData()).
            where(Predicates.equals("address", "Australia"));
        assertEquals(2, query.getResults().size());
        System.out.println(query.getResults());
        
        query = Query.
            from(data.getPeopleData()).
            where(Predicates.equals("address", "The Moon"));
        assertEquals(0, query.getResults().size());
        System.out.println(query.getResults());
    }

    @Test
    public void testSimpleMapFilter() throws Exception {
        Query query = Query.
            from(data.getPeopleDataMap()).
            where(Predicates.equals("address", "L.A."));
        assertEquals(1, query.getResults().size());
        System.out.println(query.getResults());
        
        query = Query.
            from(data.getPeopleData()).
            where(Predicates.equals("address", "Australia"));
        assertEquals(2, query.getResults().size());
        System.out.println(query.getResults());
        
        query = Query.
            from(data.getPeopleData()).
            where(Predicates.equals("address", "The Moon"));
        assertEquals(0, query.getResults().size());
        System.out.println(query.getResults());
    }

    @Test
    public void testSimpleObjectSelection() throws Exception {
        Query query = Query.
            from(data.getPeopleData()).
            select("name");
        assertEquals(3, query.getResults().size());
        for (final Map<String, Object> row : query.getResults()) {
            assertEquals(1, row.size());
        }
        System.out.println(query.getResults());
    }

    @Test
    public void testSimpleMapSelection() throws Exception {
        Query query = Query.
            from(data.getPeopleDataMap()).
            select("name", "employer");
        assertEquals(3, query.getResults().size());
        for (final Map<String, Object> row : query.getResults()) {
            assertEquals(2, row.size());
        }
        System.out.println(query.getResults());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSimpleMapSelectionFunction() throws Exception {
        UnaryFunction<Map<String, Object>, Object> toUpper = new ToUpper("name");
        Query query = Query.
            from(data.getPeopleDataMap()).
            select(new String[] {"name"}, toUpper);
        assertEquals(3, query.getResults().size());
        for (final Map<String, Object> row : query.getResults()) {
            assertEquals(2, row.size());
        }
        System.out.println(query.getResults());
    }

    @Test
    public void testSimpleObjectFilterAndSelect() throws Exception {
        Query query = Query.
            from(data.getPeopleData()).
            where(Predicates.equals("address", "L.A.")).
            select("name");
        assertEquals(1, query.getResults().size());
        for (final Map<String, Object> row : query.getResults()) {
            assertEquals(1, row.size());
        }
        System.out.println(query.getResults());
    }

    @Test
    public void testSimpleJoin() throws Exception {
        Query query = Query.
            from(Source.source("person", data.getPeopleData())).
            join(Source.source("company", data.getEmployerData()), "employer", "name");
        assertEquals(3, query.getResults().size());
        for (final Map<String, Object> row : query.getResults()) {
            assertEquals(5, row.size());
        }
        System.out.println(query.getResults());
    }

    @Test
    public void testSimpleObjectSort() throws Exception {
        Query query = Query.
            from(data.getPeopleData()).
            sort("name");
        assertEquals(3, query.getResults().size());
        System.out.println(query.getResults());
    }

    @Test
    public void testSimpleMapSort() throws Exception {
        Query query = Query.
            from(data.getPeopleDataMap()).
            sort("name");
        assertEquals(3, query.getResults().size());
        System.out.println(query.getResults());
    }

}
