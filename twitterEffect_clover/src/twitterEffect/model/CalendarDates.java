/**
 * @author elaineparr
 */
package twitterEffect.model;
import java.sql.Date;

/**
 * @author elaineparr
 *
 */
public class CalendarDates {
	Date date;
	President president;
	
	/**
	 * @param date
	 * @param president
	 */
	public CalendarDates(Date date, President president) {
		this.date = date;
		this.president = president;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the president
	 */
	public President getPresident() {
		return president;
	}

	/**
	 * @param president the president to set
	 */
	public void setPresident(President president) {
		this.president = president;
	}
	
	

}
