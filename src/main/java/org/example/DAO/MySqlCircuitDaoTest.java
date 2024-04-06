package org.example.DAO;

import org.example.DTO.Circuit;
import org.example.Exceptions.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySqlCircuitDaoTest { // by Darren Meidl
    private MySqlCircuitDao dao; // declare
    @BeforeEach
    void setUp() {
        dao = new MySqlCircuitDao(); // initialize
    }
    // INSERT CIRCUIT TESTS
    @Test
    void insertCircuit() throws DaoException { // By Darren Meidl
        System.out.println("TEST 1: 'insertCircuit()' ");
        String expected = "testName"; // the expected name
        Circuit test = new Circuit("testName", "testCountry", 1, 1); // create a test circuit
        int testID = dao.insertCircuit(test).getId(); // insert the test circuit & get its auto id from the database
        String actual = dao.getCircuitById(testID).getCircuitName(); // Get the name from the test Circuit from the test database
        assertEquals(expected, actual); // compare the strings
        System.out.println("EXPECTED: "+expected+" ACTUAL: "+actual); // print the results
    }
    // UPDATE CIRCUIT TESTS
    @Test
    void updateCircuit() throws DaoException { // By Darren Meidl
        System.out.println("TEST 1: 'updateCircuit()' ");
        String expected = "t2"; // the expected name
        Circuit test = new Circuit("t2", "t2Country", 999, 2); // create a test circuit
        dao.updateCircuit(1, test); // update circuit with id 1 with the test circuit fields
        String actual = dao.getCircuitById(1).getCircuitName(); // Get the name from the test Circuit from the test database
        assertEquals(expected, actual); // compare the strings
        System.out.println("EXPECTED: "+expected+" ACTUAL: "+actual); // print the results
        System.out.println("");
    }
}