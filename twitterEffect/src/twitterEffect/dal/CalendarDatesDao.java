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

import twitterEffect.model.CalendarDates;
import twitterEffect.model.President;

public class CalendarDatesDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static CalendarDatesDao instance = null;
	protected CalendarDatesDao() {
		connectionManager = new ConnectionManager();
	}
	public static CalendarDatesDao getInstance() {
		if(instance == null) {
			instance = new CalendarDatesDao();
		}
		return instance;
	}

	/**
	 * Save CalendarDates instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public CalendarDates create(CalendarDates calendarDate) throws SQLException {
		String insertCalendarDates = 
				"INSERT INTO CalendarDates(CalendarDate, President) " +
				"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCalendarDates);
			insertStmt.setDate(1, calendarDate.getDate());
			insertStmt.setString(2, calendarDate.getPresident().getPersonName());
			insertStmt.executeUpdate();
			return calendarDate;
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
	 * Delete the CalendarDatess instance.
	 * This runs a DELETE statement.
	 */
	public CalendarDates delete(CalendarDates calendarDate) throws SQLException {
		String deleteCalendarDates = "DELETE FROM CalendarDates WHERE CalendarDate=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCalendarDates);
			deleteStmt.setDate(1, calendarDate.getDate());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the CalendarDatess instance.
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
	 * Get the CalendarDates record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single CalendarDates instance.
	 */
	public CalendarDates getCalendarDateByCalendarDate(Date calendarDate) throws SQLException {
		String selectCalendarDates = 
				"SELECT * " +
				"FROM CalendarDates " +
				"WHERE CalendarDate=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		PresidentDao presidentDao = PresidentDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCalendarDates);
			selectStmt.setDate(1, calendarDate);
			results = selectStmt.executeQuery();
			if(results.next()) {
				Date resultCalendarDate = results.getDate("CalendarDate");		
				President president = presidentDao.getPresidentByName(results.getString("President"));
				CalendarDates calendar = new CalendarDates(resultCalendarDate, president);
				return calendar;
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
	 * Get the matching CalendarDates records by fetching from your MySQL instance.
	 * This runs a SELECT statement and returns a list of matching CalendarDatess.
	 */
	public List<CalendarDates> getCalendarDatesByPresident(President president) throws SQLException {
		List<CalendarDates> calendarDates = new ArrayList<CalendarDates>();
		String selectCalendarDates =
			"SELECT CalendarDates, President " + 
			"FROM CalendarDates " + 
			"WHERE President=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		PresidentDao presidentsDao = PresidentDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCalendarDates);
			selectStmt.setString(1, president.getPersonName());
			results = selectStmt.executeQuery();
			while(results.next()) {
				Date calendarDate = results.getDate("CalendarDate");		
				President resultPresident = presidentsDao.getPresidentByName(results.getString("President"));
				CalendarDates calendar = new CalendarDates(calendarDate, resultPresident);
				calendarDates.add(calendar);
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
		return calendarDates;
	}

}

