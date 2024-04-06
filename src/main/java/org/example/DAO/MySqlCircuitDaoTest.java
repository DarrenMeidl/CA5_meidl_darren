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
        System.out.println("TESTING: 'insertCircuit()' ");
        String expected = "testName";
        Circuit test = new Circuit(20, "testName", "testCountry", 1, 1);
        dao.insertCircuit(test); // insert the test Circuit into the test database
        String actual = dao.getCircuitById(20).getCircuitName(); // Get the name from the test Circuit from the test database itself
        assertEquals(expected, actual);
        System.out.println("EXPECTED: "+expected+" ACTUAL: "+actual);
        System.out.println("");
    }
    // UPDATE CIRCUIT TESTS
    @Test
    void updateCircuit() { // By Darren Meidl

    }
}