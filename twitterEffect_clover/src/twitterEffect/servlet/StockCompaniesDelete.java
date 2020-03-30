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


@WebServlet("/stockcompaniesdelete")
public class StockCompaniesDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete StockCompanies");        
        req.getRequestDispatcher("/StockCompaniesDelete.jsp").forward(req, resp);
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
            messages.put("title", "Invalid CompanyTicker");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the StockCompanies.
	        StockCompanies stockCompanies = new StockCompanies(companyTicker);
	        try {
	        	stockCompanies = stockCompaniesDao.delete(stockCompanies);
	        	// Update the message.
		        if (stockCompanies == null) {
		            messages.put("title", "Successfully deleted " + companyTicker);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + companyTicker);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/StockCompaniesDelete.jsp").forward(req, resp);
    }
}
