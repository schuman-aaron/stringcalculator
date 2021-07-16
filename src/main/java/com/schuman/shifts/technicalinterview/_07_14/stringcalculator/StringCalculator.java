package com.schuman.shifts.technicalinterview._07_14.stringcalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2021-07-14 Created by Aaron Schuman
 *
 */
public class StringCalculator 
{
	protected final String NEW_LINE_STRING = "\n";
	protected final String EMPTY_STRING = "";
	protected final String CONTROL_STRING = "//";
	protected final String DEFAULT_REGEX = ",";
	protected final int DEFAULT_RESULT = 0;
	
	protected int result;
	protected StringBuilder currentComputationString = new StringBuilder();
	protected StringBuilder oldComputationString = new StringBuilder();
	protected String regex;
	protected Pattern pattern;
	protected Matcher matcher;
	protected Matcher controlStringMatcher;
	protected String[] splitResults;
	protected int currentNumber;
	
	public int add(String numbers) throws NegativesNotAllowedException {
		initializeCalculator(numbers);
		
		if(currentComputationString.length() != 0) {
			pattern = Pattern.compile(NEW_LINE_STRING);
			initializeCalculatorWithControlStringAsNecessary();
			splitResults = Pattern
				.compile(regex)
				.split (
					pattern.matcher(currentComputationString).replaceAll(EMPTY_STRING)
				);
			for(String currentNumberString : splitResults) {
				currentNumber = IntegerProxy.parseInt(currentNumberString);
				if(currentNumber < 0)
					throw new NegativesNotAllowedException(currentNumber);
				result += currentNumber;
			}
		}
		return result;
	}
	
	private void initializeCalculator(String numbers) {
		result = DEFAULT_RESULT;
		regex = DEFAULT_REGEX;
		currentComputationString.setLength(0);
		oldComputationString.setLength(0);
		currentComputationString.append(numbers);
	}
	
	private void initializeCalculatorWithControlStringAsNecessary(){
		controlStringMatcher = Pattern.compile(CONTROL_STRING).matcher(currentComputationString);
		if(controlStringMatcher.find() && controlStringMatcher.start()==0) {
			regex = pattern.split(currentComputationString.toString())[0].substring(CONTROL_STRING.length());
			oldComputationString.append(currentComputationString);
			currentComputationString.setLength(0);
			currentComputationString.append(oldComputationString.substring(pattern.matcher(oldComputationString).start()+NEW_LINE_STRING.length()).toString());
		}
	}
	
	protected StringBuilder getCurrentComputationString() {
		return currentComputationString;
	}
	
	public void setCurrentComputationString(StringBuilder stringBuilder) {
		currentComputationString = stringBuilder;
	}

	public void setPattern(Pattern newPattern) {
		pattern = newPattern;
	}

	public void setMatcher(Matcher newMatcher) {
		matcher = newMatcher;
	}

	protected StringBuilder getOldComputationString() {
		return oldComputationString;
	}

	protected void setOldComputationString(StringBuilder oldComputationString) {
		this.oldComputationString = oldComputationString;
	}
}
