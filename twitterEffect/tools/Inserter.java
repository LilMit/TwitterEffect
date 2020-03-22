/**
 * 
 */
package twitterEffect.tools;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;

import twitterEffect.dal.*;
import twitterEffect.model.*;
import twitterEffect.model.President.Party;

/**
 * @author elaineparr
 *
 */
public class Inserter {
	
	public static void main(String[] args) throws SQLException {
	
		PersonDao personDao = PersonDao.getInstance();
		PresidentDao presidentDao = PresidentDao.getInstance();
		CalendarDatesDao calendarDao = CalendarDatesDao.getInstance();
		TweetsDao tweetsDao = TweetsDao.getInstance();
		//TrumpTweetsDao ttDao = TrumpTweetsDao.getInstance();
		//ObamaTweetsDao otDao = ObamaTweetsDao.getInstance();
		
		Person person = new Person("Bob", "Carpenter");
		person = personDao.create(person);
		President pres = new President("Bernie Sanders", Party.INDEPENDENT);
		pres = presidentDao.create(pres);
		
		Date date = new Date(11111);
		Time time = new Time(5555);
		CalendarDates cd = new CalendarDates(date, pres);
		cd = calendarDao.create(cd);
		Tweets tweet = new Tweets("lalal", date, time, "henloooo", 5, "Bob");
		tweet = tweetsDao.create(tweet);

		
		
		
	}

}
