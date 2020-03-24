package twitterEffect.servlet;

import twitterEffect.dal.*;
import twitterEffect.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/tweetcreate")
public class TweetsCreate extends HttpServlet {
	
	protected TweetsDao tweetsDao;
	
	@Override
	public void init() throws ServletException {
		tweetsDao = TweetsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/TweetsCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        PersonDao personDao = PersonDao.getInstance();
        String linkToTweet = req.getParameter("LinkToTweet");
        if (linkToTweet == null || linkToTweet.trim().isEmpty()) {
            messages.put("success", "Invalid LinkToTweet");
        } else {
        	// Create the Tweets.
        	// TweetDate must be in the format yyyy-mm-dd.
        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	String tweetDate = req.getParameter("TweetDate");
        	Date tDate = new Date();
        	
			String tweetTime = req.getParameter("TweetTime");
			Time tTime = java.sql.Time.valueOf(tweetTime);
			
			String content = req.getParameter("Content");
			String retweets = req.getParameter("Retweets");		
			String personName = req.getParameter("PersonName");
			Person person = new Person(personName);
			try {
				person = personDao.getPersonByPersonName(personName);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
        	try {
        		tDate = dateFormat.parse(tweetDate);
        	} catch (ParseException e) {
        		e.printStackTrace();
				throw new IOException(e);
        	}
        	
        	int rt = 0;
        	rt = Integer.parseInt(retweets);
	        try {
	        	Tweets tweet = new Tweets(linkToTweet,tDate,tTime,content,rt,person);
	        	tweet = tweetsDao.create(tweet);
	        	messages.put("success", "Successfully created " + linkToTweet);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/TweetsCreate.jsp").forward(req, resp);
    }

}
