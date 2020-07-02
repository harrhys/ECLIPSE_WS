package com.oracle.enterprisetest.jsca.cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bea.clusterstress.helper.MBeanHelper;
import com.oracle.enterprisetest.jsca.getprc.svc.ejb30.IGetPrice;

/**
 * This simple servlet demonstrates how using JDBC to access a database. It also
 * shows how to use the HttpSession object.
 *
 * @author Richard M. Yumul
 *
 * Modified by BEA to show server providing service for cluster testing as well
 * as added features to show execution times and invocation counts
 */

public class ShoppingCartServlet extends HttpServlet {

	/**
	 * The init method gets called when the servlet is instantiated; ie only
	 * once in its lifetime. Here is where you would initialize resources that
	 * should be available for the lifetime of the servlet.
	 */

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		System.out.println("ShoppingCart Servlet: Resetting test counters...");
	}

	/**
	 * The destory() method is called before the servlet engine decides to
	 * unload the servlet. This is where you would free resources that aren't
	 * needed anymore. In this case, the database connection is closed.
	 */
	public void destroy() {
	}

	private static Connection getConnection() throws Exception {
		Context ctx = new InitialContext();
		// Look up myDataSource
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx
				.lookup("megaCartPool");
		// Create a connection object
		return ds.getConnection();
	}

	/**
	 * This is the brain of the servlet, where the logic resides in deciding
	 * whether or not to display the product list, add an item to the customer's
	 * shopping cart, or to display the cart contents.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NullPointerException {

		PrintWriter out = null;
		HttpSession session = null;
		String serverName = null;

		try {
			// get an output stream handle, a PrintWriter
			out = response.getWriter();
			// Set the content type in the response headers
			response.setContentType("text/html");

			// print Header
			printHeader(out);

			// get a reference to the session object
			session = request.getSession(true);

			serverName = MBeanHelper.getServerName();

			// for load runner script validation
			out.println("<h2>Servlet provided by: " + serverName + "</h2>");

			// Get the "id" and "command" form paramters;
			// if they're not available, the getParameter() method
			// will return null.
			String id = request.getParameter("id");
			String command = request.getParameter("command");
			if (command == null) {
				command = "";
			}

			// getRequestURI() is only one of many methods available in the
			// HttpRequest object to inspect request properties.
			String requestURI = request.getRequestURI();

			// get the customer's cart; if it the cart can't be retrieved
			// from the HttpSession object, create a new cart.
			ShoppingCart shopCart = (ShoppingCart) session.getAttribute("cart");
			if (shopCart == null) {
				shopCart = new ShoppingCart();
				session.setAttribute("cart", shopCart);
			}

			if (command.startsWith("add")) {
				addItemToCart(shopCart, id);
				session.setAttribute("cart", shopCart);
				printCatalog{_DB_OPTION_}(out, requestURI);
			} else if (command.equals("viewcart")) {
				printCart{_DB_OPTION_}(shopCart, out, requestURI);
			} else {
				printCatalog{_DB_OPTION_}(out, requestURI);
			}
			getVersion(out);
			// print out session id for LR validation
			out.println("<br>Current Session ID is :" + session.getId()
					+ "<br>");
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (out != null) {
				out.println(getStackTrace(e));
			}
		} finally {
			if (out != null) {
				printFooter(out);
			}
		}

	}

	private void addItemToCart(ShoppingCart shopCart, String id) {
		List<ShoppingCartItem> items = shopCart.getItems();
		boolean found = false;
		for (int i = 0; i < items.size(); i++) {
			ShoppingCartItem shopItem = items.get(i);
			String shopItemID = shopItem.getId() + "";
			if (shopItemID.equals(id)) {
				shopItem.setQuantity(shopItem.getQuantity() + 1);
				found = true;
			}
		}
		if (!found) {
			ShoppingCartItem sItem = new ShoppingCartItem();
			int sID = Integer.parseInt(id.trim());
			sItem.setId(sID);
			sItem.setQuantity(1);
			sItem.setPrice(sID);
			items.add(sItem);
		}
	}

	private void printFooter(PrintWriter out) {
		// TODO Auto-generated method stub
		out.println("<br/>EOF</body></html>");
	}

	private void printHeader(PrintWriter out) {
		// TODO Auto-generated method stub
		out.println("<html>");
		out.println("<head>");
		addMetaTags(out);
		out.println("</head>");
		out.println("<body>");
	}

	private void addMetaTags(PrintWriter out) {
		out.println("<META HTTP-EQUIV=\"expires\" CONTENT=\"0\"/>");
		out.println("<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"/>");
		out
				.println("<META HTTP-EQUIV=\"cache-control\" CONTENT=\"no-cache\"/>");
	}

	/**
	 * Database access using JDBC is a straightforward operation; create a
	 * statement, execute it, retrieve the result set, and then iterate through
	 * it. One issue to watch out for with servlets is to free up the resources
	 * once you're done with them.
	 */
	private void printCatalogFromDB(PrintWriter out, String requestURI)
			throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		Connection dbc = null;

		// Product list header
		out.println("<h3>Product Catalog</h3>");
		out.println("<form action=\"" + requestURI + "\" method=\"post\">");
		out.println("<table border=1>");
		out.println("<tr><th>Item</th><th>Price</th><th></th></tr>");

		try {
			// setup jdbc statements
			dbc = getConnection();
			stmt = dbc.createStatement();
			stmt.execute("select * from Products");
			rs = stmt.getResultSet();

			// this is used format the money values
			NumberFormat nf = NumberFormat.getCurrencyInstance();

			// iterate through the result set; outputing the product
			// list in a html table
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td><b>" + rs.getString("name") + "</b><br>"
						+ rs.getString("description") + "</td>");
				out.println("<td align=\"right\">"
						+ nf.format(rs.getDouble("price")) + "</td>");
				out.println("<td><a href=\"" + requestURI + "?command=add&id="
						+ String.valueOf(rs.getInt("productID"))
						+ "\">Add to Cart</a></td>");
				out.println("</tr>");
			}
			// Product list footer
			out.println("</table>");
			out.println("</form>");
			out.println("<a href=\"" + requestURI
					+ "?command=viewcart\">View your Cart</a>");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: " + e.toString());
			throw e;
		} finally {
			closeResources(out, stmt, rs, dbc);
		}
	}

	private void printCatalogNoDB(PrintWriter out, String requestURI)
			throws Exception {
		// Product list header
		out.println("<h3>Product Catalog</h3>");
		out.println("<form action=\"" + requestURI + "\" method=\"post\">");
		out.println("<table border=1>");
		out.println("<tr><th>Item</th><th>Price</th><th></th></tr>");

		try {
			// this is used format the money values
			NumberFormat nf = NumberFormat.getCurrencyInstance();

			// iterate through the result set; outputing the product
			// list in a html table
			for (int i = 1; i <= 10; i++) {
				out.println("<tr>");
				out.println("<td><b>" + "product" + i + "</b><br>" + "product"
						+ i + "</td>");
				out.println("<td align=\"right\">" + nf.format((double) i)
						+ "</td>");
				out.println("<td><a href=\"" + requestURI + "?command=add&id="
						+ i + "\">Add to Cart</a></td>");
				out.println("</tr>");
			}
			// Product list footer
			out.println("</table>");
			out.println("</form>");
			out.println("<a href=\"" + requestURI
					+ "?command=viewcart\">View your Cart</a>");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: " + e.toString());
			throw e;
		} finally {
		}
	}

	/**
	 * If the command "viewcart" is passed to the servlet, then this method is
	 * called. It iterates through the cart Hashtable, extracting the product
	 * details from the catalog. This method also calculates the individual item
	 * subtotals as well as the grand total of all the items in the customer's
	 * shopping cart.
	 */
	private void printCartFromDB(ShoppingCart cart, PrintWriter out,
			String requestURI) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		Connection dbc = null;

		double totalPrice = getTotalPrice(cart);
		int totalQuantity = getTotalQuantity(cart);
		List<ShoppingCartItem> items = cart.getItems();

		NumberFormat nf = NumberFormat.getCurrencyInstance();

		out.println("<h1>Shopping Cart</h1>");
		out.println("<h2>Your cart contains:</h2>");
		out.println("<table border=1>");
		out
				.println("<tr><th>Quantity</th><th>Item</th><th>Price</th><th>Subtotal</th></tr>");
		try {
			dbc = getConnection();
			for (int i = 0; i < items.size(); i++) {
				ShoppingCartItem shopCartItem = items.get(i);
				String id = shopCartItem.getId() + "";
				Integer qty = (Integer) shopCartItem.getQuantity();
				stmt = dbc.createStatement();
				stmt.execute("select * from Products where productID = " + id);
				rs = stmt.getResultSet();
				while (rs.next()) {
					double subtotal = rs.getDouble("price") * qty.intValue();
					out.println("<tr>");
					out.println("<td align=center>" + qty.toString() + "</td>");
					out.println("<td>" + rs.getString("name") + "</td>");
					out.println("<td>" + nf.format(rs.getDouble("price"))
							+ "</td>");
					out.println("<td align=right>" + nf.format(subtotal)
							+ "</td>");
					out.println("</tr>");
				}
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;
			}
			out
					.println("<tr><td colspan=3 align=right>TOTAL Price:</td><td align=right>"
							+ nf.format(totalPrice) + "</td></tr>");
			out
					.println("<tr><td colspan=3 align=right>TOTAL Quantity:</td><td align=right>"
							+ totalQuantity + "</td></tr>");
			out.println("</table>");
			out.println("<p><a href=\"" + requestURI
					+ "\">Back to Shopping</a></p>");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: " + e.toString());
			throw e;
		} finally {
			closeResources(out, stmt, rs, dbc);
		}
	}

	private void printCartNoDB(ShoppingCart cart, PrintWriter out,
			String requestURI) throws Exception {
		List<ShoppingCartItem> items = cart.getItems();

		double totalPrice = getTotalPrice(cart);
		int totalQuantity = getTotalQuantity(cart);

		NumberFormat nf = NumberFormat.getCurrencyInstance();

		out.println("<h1>Shopping Cart</h1>");
		out.println("<h2>Your cart contains:</h2>");
		out.println("<table border=1>");
		out
				.println("<tr><th>Quantity</th><th>Item</th><th>Price</th><th>Subtotal</th></tr>");
		try {
			for (int i = 0; i < items.size(); i++) {
				ShoppingCartItem shopItem = items.get(i);
				String id = shopItem.getId() + "";
				Integer qty = (Integer) shopItem.getQuantity();
				double subtotal = Double.parseDouble(id) * qty.intValue();
				out.println("<tr>");
				out.println("<td align=center>" + qty.toString() + "</td>");
				out.println("<td>" + "product" + id + "</td>");
				out.println("<td>" + nf.format(Double.parseDouble(id))
						+ "</td>");
				out.println("<td align=right>" + nf.format(subtotal) + "</td>");
				out.println("</tr>");
			}
			out
					.println("<tr><td colspan=3 align=right>TOTAL Price:</td><td align=right>"
							+ nf.format(totalPrice) + "</td></tr>");
			out
					.println("<tr><td colspan=3 align=right>TOTAL Quantity:</td><td align=right>"
							+ totalQuantity + "</td></tr>");
			out.println("</table>");
			out.println("<p><a href=\"" + requestURI
					+ "\">Back to Shopping</a></p>");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("ERROR: " + e.toString());
			throw e;
		} finally {
		}
	}

	private int getTotalQuantity(ShoppingCart cart) {
		GetQuantitySvcWS svc = new GetQuantitySvcWS();
		IGetQuantity port = svc.getPort(IGetQuantity.class);
		return port.getTotalQuantity(cart);
	}

	private double getTotalPrice(ShoppingCart cart) throws Exception {
		double retPrice = 0.0;
		InitialContext ctx = new InitialContext();
		IGetPrice getPrice = (IGetPrice) ctx.lookup("GetPriceSvc_EJB30_JNDI");
		retPrice = getPrice.getTotalPrice(cart);
		return retPrice;
	}

	private void closeResources(PrintWriter out, Statement stmt, ResultSet rs,
			Connection dbc) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.println(getStackTrace(e));
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.println(getStackTrace(e));
		}
		try {
			if (dbc != null) {
				dbc.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println(getStackTrace(e));
		}
	}

	public static String getStackTrace(Throwable th) {
		StringWriter sw = new StringWriter();
		th.printStackTrace(new java.io.PrintWriter(sw));
		String stacktrace = sw.toString();
		return stacktrace;
	}

	public void getVersion(PrintWriter out) {
		out.println("<p><font size=-1>");
		out.println("Shopping Cart Version Information:");
		out.println("<table font size=-1 border=1>");
		out.println("<tr><td>Version</td><td>1.7</td></tr>");
		out.println("<tr><td>Build Date</td><td>03/05/2003 @ 10:30</td></tr>");
		out.println("<tr><td>Last Modified By</td><td>Rob</td></tr>");
		out.println("</table>");
		out.println("</font>");
	}

}
