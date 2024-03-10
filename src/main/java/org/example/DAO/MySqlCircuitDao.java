package org.example.DAO;

import org.example.DTO.Circuit;
import org.example.Exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySqlCircuitDao extends MySqlDao implements CircuitDaoInterface
{
    // Written by Petr Sulc
    @Override
    public List<Circuit> getAllCircuits() throws DaoException {
        return SQLConnectionDecorator(
                (sql) -> {
                    List<Circuit> output = new ArrayList<>();
                    String query = "SELECT * FROM Circuits";
                    sql.statement = sql.connection.prepareStatement(query);
                    sql.result = sql.statement.executeQuery();
                    Circuit next;
                    while(sql.result.next())
                    {
                        next = new Circuit(
                                sql.result.getInt("id"),
                                sql.result.getString("circuit_name"),
                                sql.result.getString("country"),
                                sql.result.getFloat("length"),
                                sql.result.getInt("turns")
                        );
                        output.add(next);
                    }
                    return output;
                }
        );
    }

    // Written by Petr Sulc
    @Override
    public Circuit getCircuitById(int id) throws DaoException {
        return SQLConnectionDecorator(
                (sql) -> {
                    String query = "SELECT * FROM Circuits WHERE id = ?";
                    sql.statement = sql.connection.prepareStatement(query);
                    sql.statement.setInt(1,id);
                    sql.result = sql.statement.executeQuery();
                    if(sql.result.next())
                    {
                        return new Circuit(
                                sql.result.getInt("id"),
                                sql.result.getString("circuit_name"),
                                sql.result.getString("country"),
                                sql.result.getFloat("length"),
                                sql.result.getInt("turns")
                        );
                    }
                    return null;
                }
        );
    }

    // Written by Tomas Szabo
    @Override
    public Circuit deleteCircuitById(int id) throws DaoException {
        return SQLConnectionDecorator(
                (sql) -> {
                    //Prepared statement, result set and connection is inside wrapper, therefore writing sql.statement instead of PreparedStatement for example.
                    //Makes it more efficient because it does not have to be defined each time.
                    //To make the code more optimized, instead of creating code for getting id, we call method getCircuitById
                    Circuit circuit = getCircuitById(id);
                    if (circuit != null) {
                        String query2 = "DELETE FROM Circuits WHERE id = ?";
                        sql.statement = sql.connection.prepareStatement(query2);
                        sql.statement.setInt(1,id);
                        sql.statement.executeUpdate();
                    }

                    return circuit;
                }
        );
    }

    @Override
    public Circuit insertCircuit(Circuit c) throws DaoException {
        return SQLConnectionDecorator(
                (sql) -> {
                    // Gather data of this circuit object
                    int newID = c.getId();
                    String newCircuitName = c.getCircuitName();
                    String newCountry = c.getCountry();
                    float newLength = c.getLength();
                    int newTurns = c.getTurns();
                    // Create instance of Circuit object based on values from passed in 'c' object
                    Circuit newC = new Circuit(newID, newCircuitName, newCountry, newLength, newTurns);
                    // if the object

                    String query2 = "INSERT INTO Circuits VALUES (null, ?, ?, ?, ?)";
                    sql.statement = sql.connection.prepareStatement(query2);
                    // Set the values in the statement to be of the new circuit object
                    sql.statement.setString(1,newCircuitName);
                    sql.statement.setString(2,newCountry);
                    sql.statement.setFloat(3,newLength);
                    sql.statement.setInt(4,newTurns);
                    // Execute the query
                    sql.statement.executeUpdate();

                    Circuit next;
                    while(sql.result.next()) { // Cycle through each row on the table
                        int tempID = sql.result.getInt("id"); // Get the id of the current row
                        newC.setId(tempID); // Set it to newC object
                    }
                    return newC; // Once the loop ends, it will ensure newC object has the id of the last row (which is the newC object we inserted)
                }

        );
    }
}


