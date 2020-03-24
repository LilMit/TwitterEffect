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
/**
 * @author elaineparr
 */

@WebServlet("/personupdate")
public class PersonUpdate extends HttpServlet {
	
	protected PersonDao personDao;
	
	@Override
	public void init() throws ServletException {
		personDao = PersonDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve person and validate.
        String personName = req.getParameter("personname");
        if (personName == null || personName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid PersonName.");
        } else {
        	try {
        		Person person = personDao.getPersonByPersonName(personName);
        		if(person == null) {
        			messages.put("success", "PersonName does not exist.");
        		}
        		req.setAttribute("person", person);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PersonUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve person and validate.
        String personName = req.getParameter("personname");
        if (personName == null || personName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid PersonName.");
        } else {
        	try {
        		Person person = personDao.getPersonByPersonName(personName);
        		if(person == null) {
        			messages.put("success", "PersonName does not exist. No update to perform.");
        		} else {
        			String newOccupation = req.getParameter("occupation");
        			if (newOccupation == null || newOccupation.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid Occupation.");
        	        } else {
        	        	person = personDao.updateOccupation(person, newOccupation);
        	        	messages.put("success", "Successfully updated " + personName);
        	        }
        		}
        		req.setAttribute("person", person);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PersonUpdate.jsp").forward(req, resp);
    }
}
