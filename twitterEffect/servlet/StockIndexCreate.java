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


@WebServlet("/stockindexcreate")
public class StockIndexCreate extends HttpServlet {
	
	protected StockIndexDao stockIndexDao;
	
	@Override
	public void init() throws ServletException {
		stockIndexDao = StockIndexDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/StockIndexCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String stockIndexTicker = req.getParameter("indexticker");
        if (stockIndexTicker == null || stockIndexTicker.trim().isEmpty()) {
            messages.put("success", "Invalid StockIndexTicker");
        } else {
        	// Create the StockIndex.
        	String indexName = req.getParameter("indexname");
	        try {
	        	StockIndex stockIndex = new StockIndex(stockIndexTicker, indexName);
	        	stockIndex = stockIndexDao.create(stockIndex);
	        	messages.put("success", "Successfully created " + stockIndexTicker);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/StockIndexCreate.jsp").forward(req, resp);
    }
}
