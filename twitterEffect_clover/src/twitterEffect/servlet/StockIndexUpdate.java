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

@WebServlet("/stockindexupdate")
public class StockIndexUpdate extends HttpServlet {
	
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

        // Retrieve stockIndex and validate.
        String indexTicker = req.getParameter("indexticker");
        if (indexTicker == null || indexTicker.trim().isEmpty()) {
            messages.put("success", "Please enter a valid IndexTicker.");
        } else {
        	try {
        		StockIndex stockIndex = stockIndexDao.getStockIndexByIndexTicker(indexTicker);
        		if(stockIndex == null) {
        			messages.put("success", "IndexTicker does not exist.");
        		}
        		req.setAttribute("stockIndex", stockIndex);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/StockIndexUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve stockIndex and validate.
        String indexTicker = req.getParameter("indexticker");
        if (indexTicker == null || indexTicker.trim().isEmpty()) {
            messages.put("success", "Please enter a valid IndexTicker.");
        } else {
        	try {
        		StockIndex stockIndex = stockIndexDao.getStockIndexByIndexTicker(indexTicker);
        		if(stockIndex == null) {
        			messages.put("success", "IndexTicker does not exist. No update to perform.");
        		} else {
        			String newIndexName = req.getParameter("indexname");
        			if (newIndexName == null || newIndexName.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid IndexName.");
        	        } else {
        	        	stockIndex = stockIndexDao.updateIndexName(stockIndex, newIndexName);
        	        	messages.put("success", "Successfully updated " + indexTicker);
        	        }
        		}
        		req.setAttribute("stockIndex", stockIndex);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/StockIndexUpdate.jsp").forward(req, resp);
    }
}
