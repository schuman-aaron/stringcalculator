package com.schuman.shifts.technicalinterview._07_14.stringcalculator;

public class NegativesNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6002300863971068068L;
	private int culpritNumber;
	
	
	public NegativesNotAllowedException(int newCulpritNumber) {
		super("Input contained a negative number: " + newCulpritNumber);
		culpritNumber = newCulpritNumber;
	}


	protected int getCulpritNumber() {
		return culpritNumber;
	}

}
