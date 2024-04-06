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
        String expected = "testName";
        Circuit test = new Circuit("testName", "testCountry", 1, 1);
        int testID = dao.insertCircuit(test).getId(); // insert the test circuit & get its auto id from the database
        String actual = dao.getCircuitById(testID).getCircuitName(); // Get the name from the test Circuit from the test database
        assertEquals(expected, actual); // compare them
        System.out.println("TEST 1: EXPECTED: "+expected+" ACTUAL: "+actual); // print the results
        System.out.println("");
    }
    // UPDATE CIRCUIT TESTS
    @Test
    void updateCircuit() { // By Darren Meidl

    }
}