package twitterEffect.servlet;

import twitterEffect.dal.*;
import twitterEffect.model.*;

import java.io.IOException;
import java.sql.SQLException;
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


@WebServlet("/companiescreate")
public class CompaniesCreate extends HttpServlet {
	
	protected StockCompaniesDao stockCompaniesDao;
	
	@Override
	public void init() throws ServletException {
		stockCompaniesDao = StockCompaniesDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/CompaniesCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String companyTicker = req.getParameter("companyticker");
        if (companyTicker == null || companyTicker.trim().isEmpty()) {
            messages.put("success", "Invalid CompanyTicker");
        } else {
        	// Create the StockCompany.
        	String companyName = req.getParameter("companyname");
        	long marketCap = req.getParameter("marketcap");
        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	String stringDob = req.getParameter("dob");
        	Date dob = new Date();
        	try {
        		dob = dateFormat.parse(stringDob);
        	} catch (ParseException e) {
        		e.printStackTrace();
				throw new IOException(e);
        	}
	        try {
	        	// Exercise: parse the input for StatusLevel.
	        	StockCompanies stockCompany = new StockCompanies(companyTicker, firstName, lastName, dob, StockCompanies.StatusLevel.novice);
	        	stockCompany = stockCompaniesDao.create(stockCompany);
	        	messages.put("success", "Successfully created " + companyTicker);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CompaniesCreate.jsp").forward(req, resp);
    }
}
