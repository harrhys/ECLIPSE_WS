package com.oracle.enterprisetest.jsca.cart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOffServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NullPointerException {

		PrintWriter out = null;
		try {
			HttpSession session = request.getSession();
			out = response.getWriter();
			printHeader(out);
			if ( session != null ) {
				session.invalidate();
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				printFooter(out);
			}
		}
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

	private void printFooter(PrintWriter out) {
		// TODO Auto-generated method stub
		out.println("<br/>EOF</body></html>");
	}

}

