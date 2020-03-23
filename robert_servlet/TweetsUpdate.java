package twitterEffect.servlet;

import twitterEffect.dal.*;
import twitterEffect.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/tweetsupdate")
public class TweetsUpdate extends HttpServlet {
	
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

        // Retrieve user and validate.
        String linkToTweet = req.getParameter("LinkToTweet");
        if (linkToTweet == null || linkToTweet.trim().isEmpty()) {
            messages.put("success", "Please enter a valid LinkToTweet.");
        } else {
        	try {
        		Tweets tweet = tweetsDao.getTweetsByLinkToTweet(linkToTweet);
        		if(tweet == null) {
        			messages.put("success", "LinkToTweet does not exist.");
        		}
        		req.setAttribute("blogUser", tweet);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/TweetsUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String linkToTweet = req.getParameter("LinkToTweet");
        if (linkToTweet == null || linkToTweet.trim().isEmpty()) {
            messages.put("success", "Please enter a valid LinkToTweet.");
        } else {
        	try {
        		Tweets tweet = tweetsDao.getTweetsByLinkToTweet(linkToTweet);
        		if(linkToTweet == null) {
        			messages.put("success", "LinkToTweet does not exist. No update to perform.");
        		} else {
        			String newContent = req.getParameter("Content");
        			if (newContent == null || newContent.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid Content.");
        	        } else {
        	        	tweet = tweetsDao.updateContent(tweet, newContent);
        	        	messages.put("success", "Successfully updated " + linkToTweet);
        	        }
        		}
        		req.setAttribute("tweet", tweet);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/TweetsUpdate.jsp").forward(req, resp);
    }
}

