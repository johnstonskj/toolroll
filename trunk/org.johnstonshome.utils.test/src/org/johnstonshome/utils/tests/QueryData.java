/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.tests;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author simonjo
 *
 */
public class QueryData {

    public class Person {
        private String name;
        private String employer;
        private String address;
        
        public Person(String name, String employer, String address) {
            this.name = name;
            this.employer = employer;
            this.address = address;
        }
        
        public String getName() {
            return this.name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getEmployer() {
            return this.employer;
        }
        public void setEmployer(String employer) {
            this.employer = employer;
        }
        public String getAddress() {
            return this.address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
    }
    
    public class Employer {
        private String name;
        private String description;
        public Employer(String name, String description) {
            this.name = name;
            this.description = description;
        }
        public String getName() {
            return this.name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getDescription() {
            return this.description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
    }
    
    public Collection<Employer> getEmployerData() {
        Collection<Employer> employers = new LinkedList<Employer>();
        employers.add(new Employer("SkyNet", "Military C3I Overloards"));
        employers.add(new Employer("The Matrix", "Bio-energy producer"));
        employers.add(new Employer("SPECTRE", "Global trade & industry"));
        return employers;
    }
    
    public Collection<Person> getPeopleData() {
        Collection<Person> people = new LinkedList<Person>();
        people.add(new Person("terminator", "SkyNet", "L.A."));
        people.add(new Person("architect", "The Matrix", "Australia"));
        people.add(new Person("smith", "The Matrix", "Australia"));
        return people;
    }

    public Collection<Map<String, Object>> getPeopleDataMap() {
        Collection<Map<String, Object>> people = new LinkedList<Map<String, Object>>();
        Map<String, Object> person = new HashMap<String, Object>();
        person.put("name", "terminator");
        person.put("employer", "SkyNet");
        person.put("address", "L.A.");
        people.add(person);
        person = new HashMap<String, Object>();
        person.put("name", "architect");
        person.put("employer", "The Matrix");
        person.put("address", "Australia");
        people.add(person);
        person = new HashMap<String, Object>();
        person.put("name", "smith");
        person.put("employer", "The Matrix");
        person.put("address", "Australia");
        people.add(person);
        return people;
    }
}
