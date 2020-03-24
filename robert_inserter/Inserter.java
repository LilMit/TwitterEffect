package twitterEffect.tools;

import twitterEffect.dal.*;
import twitterEffect.model.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;



public class Inserter {
	public static void main(String[] args) throws SQLException {
		// DAO instances.
		PersonDao personsDao = PersonDao.getInstance();
		TweetsDao tweetsDao = TweetsDao.getInstance();
		TrumpTweetsDao trumpTweetsDao = TrumpTweetsDao.getInstance();
		ObamaTweetsDao obamaTweetsDao = ObamaTweetsDao.getInstance();
				
	
		// INSERT objects from our model.
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Person person1 = new Person("Donald Trump","President");
		person1 = personsDao.create(person1);
		Person person2 = new Person("Barack Obama","President");
		person2 = personsDao.create(person2);
		
		
		Tweets tweet1 = new Tweets("https://twitter.com/realDonaldTrump/status/1698308935",date,timestamp,
				"Be sure to tune in and watch Donald Trump on Late Night with David Letterman!",250,person1);
		tweet1 = tweetsDao.create(tweet1);
		Tweets tweet2 = new Tweets("https://twitter.com/BarackObama/status/1116130925396602880",date,timestamp,
				"From a big NBA fan congrats to future Hall of Famers Dwyane Wade and Dirk.",175133,person2);
		tweet2 = tweetsDao.create(tweet2);
		
		TrumpTweets trumpTweet1 = new TrumpTweets("https://twitter.com/realDonaldTrump/status/1701461182",date, timestamp,
				"Donald Trump will be appearing on The View tomorrow morning to discuss Celebrity Apprentice",33,person1,1461182,273);
		trumpTweetsDao.create(trumpTweet1); 
		TrumpTweets trumpTweets2 = new TrumpTweets("https://twitter.com/realDonaldTrump/status/1737479987",date,timestamp,
				"Donald Trump reads Top Ten Financial Tips on Late Show with David Letterman",12,person1,1737479987,18); 
		trumpTweetsDao.create(trumpTweets2);
		
		ObamaTweets obamaTweets1 = new ObamaTweets("https://twitter.com/BarackObama/status/1114517185483898880",date,timestamp,
				"A voice everybody should hear.",27938,person2,"https://twitter.com/BarackObama",134604," ");
		obamaTweetsDao.create(obamaTweets1);
		ObamaTweets obamaTweets2 = new ObamaTweets("https://twitter.com/BarackObama/status/1114202323767701504",date,timestamp,
				"My Voice will inspire others to lift their voices too.pic.twitter.com/nWTBDByMoz",6839,person2,
				"https://pbs.twimg.com/media/D3JnZdBVYAEVq8U.jpg",66187,"https://twitter.com/BarackObama");
		obamaTweetsDao.create(obamaTweets2);
		
	}

}
