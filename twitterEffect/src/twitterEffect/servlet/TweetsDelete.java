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


@WebServlet("/tweetsdelete")
public class TweetsDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Tweets");        
        req.getRequestDispatcher("/TweetsDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String linkToTweet = req.getParameter("LinkToTweet");
        if (linkToTweet == null || linkToTweet.trim().isEmpty()) {
            messages.put("title", "Invalid LinkToTweet");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the Tweet.
	        Tweets tweet = new Tweets(linkToTweet);
	        try {
	        	tweet = tweetsDao.delete(tweet);
	        	// Update the message.
		        if (tweet == null) {
		            messages.put("title", "Successfully deleted " + linkToTweet);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + linkToTweet);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/TweetsDelete.jsp").forward(req, resp);
    }
}
