/**
 * 
 */
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


@WebServlet("/stockcompaniescreate")
public class StockCompaniesCreate extends HttpServlet {
	
	protected StockCompaniesDao stockCompanyDao;
	
	@Override
	public void init() throws ServletException {
		stockCompanyDao = StockCompaniesDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/StockCompaniesCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String stockCompanyTicker = req.getParameter("companyticker");
        if (stockCompanyTicker == null || stockCompanyTicker.trim().isEmpty()) {
            messages.put("success", "Invalid CompanyTicker");
        } else {
        	// Create the StockIndex.
        	StockIndexDao stockIndexDao = StockIndexDao.getInstance();
        	String companyName = req.getParameter("company");
        	String marketCap = req.getParameter("marketcap");
        	long lMarketCap = Long.valueOf(marketCap);
			StockCompanies.MarketCapGroupType marketCapGroupType = StockCompanies.MarketCapGroupType.valueOf(req.getParameter("marketcapgroup"));
			String sector = req.getParameter("sector");
			String indexTicker = req.getParameter("indexticker");


			StockIndex stockIndex = null;
			try {
				stockIndex = stockIndexDao.getStockIndexByIndexTicker(indexTicker);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        try {
	        	StockCompanies stockCompany = new StockCompanies(stockCompanyTicker,companyName,lMarketCap,marketCapGroupType,sector,stockIndex);
	        	stockCompany = stockCompanyDao.create(stockCompany);
	        	messages.put("success", "Successfully created " + stockCompany);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/StockCompaniesCreate.jsp").forward(req, resp);
    }
}
