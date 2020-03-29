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
@WebServlet("/stockindexdelete")
public class StockIndexDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete StockIndex");        
        req.getRequestDispatcher("/StockIndexDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String indexTicker = req.getParameter("indexticker");
        if (indexTicker == null || indexTicker.trim().isEmpty()) {
            messages.put("title", "Invalid IndexTicker");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the StockIndex.
	        StockIndex stockIndex = new StockIndex(indexTicker, "namedoesntmatter");
	        try {
	        	stockIndex = stockIndexDao.delete(stockIndex);
	        	// Update the message.
		        if (stockIndex == null) {
		            messages.put("title", "Successfully deleted " + indexTicker);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + indexTicker);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/StockIndexDelete.jsp").forward(req, resp);
    }
}
