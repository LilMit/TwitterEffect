/**
 * 
 */


/**
 * @author elaineparr
 *
 */
package twitterEffect.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import twitterEffect.model.Person;

public class PersonDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static PersonDao instance = null;
	protected PersonDao() {
		connectionManager = new ConnectionManager();
	}
	public static PersonDao getInstance() {
		if(instance == null) {
			instance = new PersonDao();
		}
		return instance;
	}

	/**
	 * Save Person instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Person create(Person person) throws SQLException {
		String insertPerson = 
				"INSERT INTO Person(PersonName, Occupation) " +
				"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPerson);
			insertStmt.setString(1, person.getPersonName());
			insertStmt.setString(2, person.getOccupation());
			insertStmt.executeUpdate();
			return person;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	/**
	 * Update the Content of the Person instance.
	 * This runs a UPDATE statement.
	 */
	public Person updateOccupation(Person person, String occupation) throws SQLException {
		String updateOcc = "UPDATE Person SET Occupation=? WHERE PersonName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateOcc);
			updateStmt.setString(1, occupation);
			updateStmt.setString(2, person.getPersonName());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			person.setOccupation(occupation);;
			return person;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	/**
	 * Delete the Persons instance.
	 * This runs a DELETE statement.
	 */
	public Person delete(Person person) throws SQLException {
		String deletePerson = "DELETE FROM Person WHERE PersonName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePerson);
			deleteStmt.setString(1, person.getPersonName());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

	/**
	 * Get the Person record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Person instance.
	 */
	public Person getPersonByPersonName(String personName) throws SQLException {
		String selectPerson = 
				"SELECT * " +
				"FROM Person " +
				"WHERE PersonName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPerson);
			selectStmt.setString(1, personName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultPersonName = results.getString("PersonName");		
				String occupation = results.getString("Occupation");
				Person person = new Person(resultPersonName, occupation);
				return person;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}

	/**
	 * Get the matching Person records by fetching from your MySQL instance.
	 * This runs a SELECT statement and returns a list of matching Persons.
	 */
	public List<Person> getPersonByOccupation(String occupation) throws SQLException {
		List<Person> persons = new ArrayList<Person>();
		String selectPerson =
			"SELECT PersonName, Occupation " + 
			"FROM Person " + 
			"WHERE Occupation=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPerson);
			selectStmt.setString(1, occupation);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String personName = results.getString("PersonName");		
				String resultOccupation = results.getString("Occupation");
				Person person = new Person(personName, resultOccupation);
				persons.add(person);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return persons;
	}

}

