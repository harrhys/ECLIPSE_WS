package com.farbig.examples.cdi.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import com.farbig.examples.cdi.transactional.BeanBase;
import com.farbig.examples.cdi.transactional.BeanMandatory;
import com.farbig.examples.cdi.transactional.BeanNever;
import com.farbig.examples.cdi.transactional.BeanNotSupported;
import com.farbig.examples.cdi.transactional.BeanRequired;
import com.farbig.examples.cdi.transactional.BeanRequiresNew;
import com.farbig.examples.cdi.transactional.BeanSupports;

@WebServlet(name = "TransactionalServlet", urlPatterns = { "/cditransactional/transactional" })
public class TransactionalServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	UserTransaction userTransaction;

	@Inject
	BeanMandatory beanMandatory;

	@Inject
	BeanNever beanNever;

	@Inject
	BeanNotSupported beanNotSupported;

	@Inject
	BeanRequired beanRequired;

	@Inject
	BeanRequiresNew beanRequiresNew;

	@Inject
	BeanSupports beanSupports;

	private HashMap<String, BeanBase> typeMap;

	public void init(ServletConfig config) throws ServletException {
		typeMap = new HashMap<>();
		typeMap.put("MANDATORY", beanMandatory);
		typeMap.put("NEVER", beanNever);
		typeMap.put("NOT_SUPPORTED", beanNotSupported);
		typeMap.put("REQUIRED", beanRequired);
		typeMap.put("REQUIRES_NEW", beanRequiresNew);
		typeMap.put("SUPPORTS", beanSupports);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BeanBase bean = typeMap.get(request.getParameter("TransactionalInterceptor"));
		request.setAttribute("name", bean.getName());

		// invoke annotated method outside transaction
		request.setAttribute("outside", bean.outside());
		StringBuilder builder = new StringBuilder();
		try {
			bean.run(builder);
			request.setAttribute("outside-result", builder.toString());
		} catch (Exception e) {
			request.setAttribute("outside-result", "get " + e.getClass().getName() + ": " + e.getMessage());
		}

		// invoke annotated method outside transaction
		request.setAttribute("inside", bean.inside());
		builder = new StringBuilder();
		try {
			userTransaction.begin();
			bean.run(builder);
			request.setAttribute("inside-result", builder.toString());
			userTransaction.commit();
		} catch (Exception e) {
			request.setAttribute("inside-result", "get " + e.getClass().getName() + ": " + e.getMessage());
			try {
				userTransaction.rollback();
			} catch (Exception e1) {
				// ignore
			}
		}

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