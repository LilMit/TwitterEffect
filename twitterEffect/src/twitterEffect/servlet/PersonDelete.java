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


@WebServlet("/persondelete")
public class PersonDelete extends HttpServlet {
	
	protected PersonDao personsDao;
	
	@Override
	public void init() throws ServletException {
		personsDao = PersonDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Person");        
        req.getRequestDispatcher("/PersonDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String personName = req.getParameter("personname");
        if (personName == null || personName.trim().isEmpty()) {
            messages.put("title", "Invalid PersonName");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the Person.
	        Person person = new Person(personName);
	        try {
	        	person = personsDao.delete(person);
	        	// Update the message.
		        if (person == null) {
		            messages.put("title", "Successfully deleted " + personName);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + personName);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PersonDelete.jsp").forward(req, resp);
    }
}
