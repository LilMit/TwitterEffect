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


@WebServlet("/stockcompaniesupdate")
public class StockCompaniesUpdate extends HttpServlet {
	
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

        // Retrieve person and validate.
        String companyTicker = req.getParameter("companyticker");
        if (companyTicker == null || companyTicker.trim().isEmpty()) {
            messages.put("success", "Please enter a valid PersonName.");
        } else {
        	try {
        		StockCompanies stockCompanies = stockCompaniesDao.getCompanyByCompanyTicker(companyTicker);
        		if(stockCompanies == null) {
        			messages.put("success", "Company (ticker) does not exist.");
        		}
        		req.setAttribute("stockCompanies", stockCompanies);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/StockCompaniesUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve person and validate.
        String companyTicker = req.getParameter("companyticker");
        if (companyTicker == null || companyTicker.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Company Ticker.");
        } else {
        	try {
        		StockCompanies stockCompanies = stockCompaniesDao.getCompanyByCompanyTicker(companyTicker);
        		if(stockCompanies == null) {
        			messages.put("success", "Stock Companies does not exist. No update to perform.");
        		} else {
        			String newCompanyName = req.getParameter("company");
        			if (newCompanyName == null || newCompanyName.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid CompanyName.");
        	        } else {
        	        	stockCompanies = stockCompaniesDao.updateCompanyName(stockCompanies, newCompanyName);
        	        	messages.put("success", "Successfully updated " + companyTicker);
        	        }
        		}
        		req.setAttribute("stockCompanies", stockCompanies);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/StockCompaniesUpdate.jsp").forward(req, resp);
    }
}
