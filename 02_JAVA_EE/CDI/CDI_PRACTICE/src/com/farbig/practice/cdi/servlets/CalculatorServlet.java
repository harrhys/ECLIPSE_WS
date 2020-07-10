package com.farbig.practice.cdi.servlets;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.farbig.practice.cdi.beans.Calculator;
import com.farbig.practice.cdi.producers.CalculatorProducer;
import com.farbig.practice.cdi.qualifiers.CalculatorType;
import com.farbig.practice.cdi.qualifiers.CalculatorType.Type;
import com.farbig.practice.cdi.qualifiers.ProducerChosen;

/**
 * Servlet implementation class CalculatorServlet
 */
@WebServlet(name = "CalculatorServlet", urlPatterns = { "/calculator" })
public class CalculatorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static {
		CalculatorProducer.setCalculatorType(CalculatorProducer.SCIENTIFIC);
	}

	@Inject
	Calculator defaultCalculator;

	@Inject
	@New
	Calculator newCalculator;

	@Inject
	@CalculatorType(Type.SIMPLE)
	Calculator simpleCalculator;

	@Inject
	@RequestScoped
	@CalculatorType(Type.SCIENTIFIC)
	Calculator scientificCalculator;

	@Inject
	@ProducerChosen
	Calculator chosenCalculator;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CalculatorServlet() {

		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer counter = 0;

		if (request.getSession().getAttribute("counter") != null) {

			counter = (int) request.getSession().getAttribute("counter");
		}

		counter++;
		
		request.getSession().setAttribute("counter", counter);
		
		if (counter == 5) {
			request.getSession().invalidate();
			request.getSession(true);
		}

		defaultCalculator.setCalculatorName(defaultCalculator.getCalculatorName() + counter);

		newCalculator.setCalculatorName(newCalculator.getCalculatorName() + counter);

		simpleCalculator.setCalculatorName(simpleCalculator.getCalculatorName() + counter);

		scientificCalculator.setCalculatorName(scientificCalculator.getCalculatorName() + counter);

		chosenCalculator.setCalculatorName(chosenCalculator.getCalculatorName() + counter);
		

		response.getWriter().append("counter:" + counter.toString()).append("\n")
				.append(defaultCalculator.getCalculatorName() + ".add(" + counter + ",10) -> "
						+ defaultCalculator.add(counter, 10) + "\n")
				.append(newCalculator.getCalculatorName() + ".add(" + counter + ",10) -> "
						+ newCalculator.add(counter, 10) + "\n")
				.append(simpleCalculator.getCalculatorName() + ".add(" + counter + ",10) -> "
						+ simpleCalculator.add(counter, 10) + "\n")
				.append(scientificCalculator.getCalculatorName() + ".add(" + counter + ",10) -> "
						+ scientificCalculator.add(counter, 10) + "\n")
				.append(chosenCalculator.getCalculatorName() + ".add(" + counter + ",10) -> "
						+ chosenCalculator.add(counter, 10) + "\n");
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
