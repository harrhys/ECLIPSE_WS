package com.farbig.examples.cdi.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import com.farbig.examples.cdi.transaction.TransactionBean;


@WebServlet(name = "TransactionServlet", urlPatterns = { "/cditransaction/transaction" })
public class TransactionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	UserTransaction userTransaction;

	@Inject
	TransactionBean bean1;

	@Inject
	TransactionBean bean2;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder builder = new StringBuilder();
		// -------------transaction 1--------------
		try {
			userTransaction.begin();
			builder.append("Transaction 1 begin.");
			builder.append("<br/>[Bean] Print bean1: " + bean1);
			builder.append("<br/>[Bean] Print bean2: " + bean2);
			userTransaction.commit();
			builder.append("<br/>Transaction 1 commit.");
		} catch (Exception e) {
			try {
				userTransaction.rollback();
			} catch (Exception e1) {
				// ignore
			}
			builder.append("<br/>Transaction 1 rollback.");
			builder.append("<br/>Printing bean failed in transaction 1.");
		}
		request.setAttribute("transaction1", builder.toString());

		builder = new StringBuilder();
		// -------------transaction 1--------------
		try {
			userTransaction.begin();
			builder.append("Transaction 2 begin.");
			builder.append("<br/>[Bean] Print bean1: " + bean1);
			userTransaction.commit();
			builder.append("<br/>Transaction 2 commit.");
		} catch (Exception e) {
			try {
				userTransaction.rollback();
			} catch (Exception e1) {
				// ignore
			}
			builder.append("<br/>Transaction 2 rollback.");
			builder.append("<br/>Printing bean failed in transaction 2.");
		}
		request.setAttribute("transaction2", builder.toString());

		// -------------no transaction--------------
		builder = new StringBuilder();
		try {
			// should throw exception
			builder.append("[Bean] Print bean1: " + bean1);
		} catch (Exception e) {
			builder.append("Printing bean1 failed because of out of transaction.<br/>" + e.getMessage());
		}
		request.setAttribute("no-transaction", builder.toString());

		request.getRequestDispatcher("response.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}