package samples.webapps.bookstore.bookstore1.util;

import java.text.NumberFormat;
import java.util.*;

public class Currency {

	private Locale locale;
	private double amount;
	public Currency() {
		locale = null;
		amount = 0.0;
	}

	public synchronized void setLocale(Locale l) {
		locale = l;
	}

	public synchronized void setAmount(double a) {
		amount = a;
	}

	public synchronized String getFormat() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		return nf.format(amount);
	}
}
