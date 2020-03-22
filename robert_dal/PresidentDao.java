package twitterEffect.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import twitterEffect.model.President;
import twitterEffect.model.Person;

public class PresidentDao  extends PersonDao {
	
	private static PresidentDao instance = null;
	
	protected PresidentDao() {
		super();
	}
	public static PresidentDao getInstance() {
		if(instance == null) {
			instance = new PresidentDao();
		}
		return instance;
	}

	public President create(President president) throws SQLException {
		// Insert into the superclass table first.
		create(new Person(president.getPersonName(),"President"));

		String insertPresident = "INSERT INTO President(Party) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPresident);
			insertStmt.setString(1, president.getPersonName());
			insertStmt.setString(2, president.getPartyAffiliation().toString());
			insertStmt.executeUpdate();
			return president;
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
	 * Update the Party of the President instance (unlikely to happen, but possible).
	 * This runs a UPDATE statement.
	 */
	public President updateParty(President president, President.Party party) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		String updateParty = "UPDATE President SET Party=? WHERE PersonName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateParty);
			updateStmt.setString(1, party.toString());
			updateStmt.setString(2, president.getPersonName());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			president.setPartyAffiliation(party);;
			return president;
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
	 * Delete the President instance.
	 * This runs a DELETE statement.
	 */
	public President delete(President president) throws SQLException {
		String deletePresident = "DELETE FROM President WHERE PersonName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePresident);
			deleteStmt.setString(1, president.getPersonName());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for PersonName=" + president.getPersonName());
			}
			super.delete(president);
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

	public President getPresidentByName(String personName) throws SQLException {
		String selectPresident =
			"SELECT * " +
			"FROM President INNER JOIN Person " +
			"  ON President.PersonName = Person.PersonName " +
			"WHERE President.PersonName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPresident);
			selectStmt.setString(1, personName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultPersonName = results.getString("PersonName");
				President.Party party = President.Party.valueOf(results.getString("Party"));
				President president = new President(resultPersonName,party);
				return president;
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

	public List<President> getPresidentByParty(President.Party party)
			throws SQLException {
		List<President> presidents = new ArrayList<President>();
		String selectPresident =
			"SELECT * " +
			"FROM President INNER JOIN Person " +
			"  ON President.PersonName = Person.PersonName " +
			"WHERE President.Party=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPresident);
			selectStmt.setString(1, party.toString());
			results = selectStmt.executeQuery();
			while(results.next()) {		
				String personName = results.getString("PersonName");
				President.Party resultParty = President.Party.valueOf(results.getString("Party"));
				President president = new President(personName, resultParty);
				presidents.add(president);
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
		return presidents;
	}
}
