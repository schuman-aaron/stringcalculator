package com.schuman.shifts.technicalinterview._07_14.stringcalculator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@SuppressWarnings("unchecked")

//@RunWith(PowerMockRunner.class)
//@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "jdk.internal.reflect.*", "javax.script.*", "org.mockito.*"})
//@PrepareForTest({IntegerProxy.class})
public class StringCalculatorTest{
	
	private final String DEFAULT_STRING_INT = "1";
	private final String DEFAULT_REGEX = ",";
	private final String DEFAULT_CONTROL_STRING = "//";
	protected final String NEW_LINE_STRING = "\n";
	protected final String EMPTY_STRING = "";
	protected final int DEFAULT_INT = 0;
	
	private final String STEP_ONE_B_STRING_INPUT = "";
	private final int STEP_ONE_B_INTEGER_EXPECTED_OUTPUT = 0;
	
	private final String STEP_ONE_D_STRING_INPUT = "1,2,5";
	private final int STEP_ONE_D_INTEGER_EXPECTED_OUTPUT = 8;
	
	private final String STEP_TWO_STRING_INPUT = "1,\n2,4";
	private final int STEP_TWO_INTEGER_EXPECTED_OUTPUT = 7;
	
	private final String STEP_THREE_STRING_INPUT = "//$\n2$3$8";
	private final String STEP_THREE_REGEX = "$";
	private final int STEP_THREE_INTEGER_EXPECTED_OUTPUT = 13;
	
	private final String STEP_FOUR_STRING_INPUT = "1,7,4,11,-5,6";
	private final int STEP_FOUR_EXPECTED_INT_OUTPUT = -5;
	
	private StringCalculator calculator;
	private int results;
	
	
	@Mock
	private StringBuilder inputArgument;
	
	@Mock 
	private StringBuilder oldInputArgument;
	
	@Mock
	private Pattern mockPattern;
	
	@Mock
	private Matcher mockMatcher;

	@Before
	public void buildUp() {
		inputArgument = mock(StringBuilder.class);
		oldInputArgument = mock(StringBuilder.class);
		mockMatcher = mock(Matcher.class);
		mockPattern = mock(Pattern.class);
		calculator = new StringCalculator();
		calculator.setCurrentComputationString(inputArgument);
		calculator.setOldComputationString(oldInputArgument);
		calculator.setMatcher(mockMatcher);
		calculator.setPattern(mockPattern);
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
    public void testAddSuccessStepOneB() {
    	try {
    		when(inputArgument.append(anyString())).thenReturn(inputArgument);
    		when(inputArgument.length()).thenReturn(0);
    		
	    	results = calculator.add(STEP_ONE_B_STRING_INPUT);
	    	
	        assertEquals("Unexpected return value:\n", STEP_ONE_B_INTEGER_EXPECTED_OUTPUT, results);
	        verify(inputArgument, times(1)).setLength(0);
	        verify(oldInputArgument, times(1)).setLength(0);
	    	verify(inputArgument, times(1)).append(anyString());
	    	verify(inputArgument, times(1)).length();       
    	} catch (Exception e) {
    		e.printStackTrace();
    		Assert.fail();
    	}
    }
	
    @Test
    public void testAddSuccessStepOneD() {
    	try(
			MockedStatic<IntegerProxy> mockInteger = mockStatic(IntegerProxy.class);
			MockedStatic<Pattern> mockStaticPattern = mockStatic(Pattern.class);
		){
    		when(inputArgument.append(anyString())).thenReturn(inputArgument);
    		when(inputArgument.length()).thenReturn(STEP_ONE_D_STRING_INPUT.length());
    		when(inputArgument.toString()).thenReturn(STEP_ONE_D_STRING_INPUT);
    		
    		mockStaticPattern.when(() -> Pattern.compile(anyString())).thenReturn(mockPattern);
    		doReturn(STEP_ONE_D_STRING_INPUT.split(DEFAULT_REGEX)).when(mockPattern).split(anyString());
    		doReturn(mockMatcher).when(mockPattern).matcher(any(StringBuilder.class));
    		doReturn(STEP_ONE_D_STRING_INPUT).when(mockMatcher).replaceAll(anyString());
    		doReturn(false).when(mockMatcher).find();
    		mockInteger.when(() -> IntegerProxy.parseInt(anyString())).thenCallRealMethod();
    		
	    	results = calculator.add(STEP_ONE_D_STRING_INPUT);
	    	
	        assertEquals("Unexpected return value:\n", STEP_ONE_D_INTEGER_EXPECTED_OUTPUT, results);
	        mockInteger.verify(() -> IntegerProxy.parseInt(anyString()), times(STEP_ONE_D_STRING_INPUT.split(DEFAULT_REGEX).length));
	        testOneTwoAndFiveVerification();
	        defaultVerification(mockStaticPattern);
    	} catch (Exception e) {
    		e.printStackTrace();
    		Assert.fail();
    	}
    }
    
    @Test
    public void testAddSuccessStepTwo() {
    	try(
			MockedStatic<IntegerProxy> mockInteger = mockStatic(IntegerProxy.class);
			MockedStatic<Pattern> mockStaticPattern = mockStatic(Pattern.class);
		){
    		when(inputArgument.append(anyString())).thenReturn(inputArgument);
    		when(inputArgument.length()).thenReturn(STEP_TWO_STRING_INPUT.length());
    		when(inputArgument.toString()).thenReturn(STEP_TWO_STRING_INPUT);
    		
    		mockStaticPattern.when(() -> Pattern.compile(anyString())).thenReturn(mockPattern);
    		doReturn("1,2,4".split(DEFAULT_REGEX)).when(mockPattern).split(anyString());
    		doReturn(mockMatcher).when(mockPattern).matcher(any(StringBuilder.class));
    		doReturn("1,2,4").when(mockMatcher).replaceAll(anyString());
    		doReturn(false).when(mockMatcher).find();
    		mockInteger.when(() -> IntegerProxy.parseInt(anyString())).thenCallRealMethod();
    		
	    	results = calculator.add(STEP_TWO_STRING_INPUT);
	    	
	        assertEquals("Unexpected return value:\n", STEP_TWO_INTEGER_EXPECTED_OUTPUT, results);
	        mockInteger.verify(() -> IntegerProxy.parseInt(anyString()), times(STEP_TWO_STRING_INPUT.split(DEFAULT_REGEX).length));
	        testOneTwoAndFiveVerification();  
	        defaultVerification(mockStaticPattern);
    	} catch (Exception e) {
    		e.printStackTrace();
    		Assert.fail();
    	}
    }
    
    @Test
    public void testAddSuccessStepThree() {
    	try(
			MockedStatic<IntegerProxy> mockInteger = mockStatic(IntegerProxy.class);
			MockedStatic<Pattern> mockStaticPattern = mockStatic(Pattern.class);
		){
    		when(inputArgument.append(anyString())).thenReturn(inputArgument);
    		when(inputArgument.length()).thenReturn(STEP_THREE_STRING_INPUT.length());
    		when(inputArgument.toString()).thenReturn(STEP_THREE_STRING_INPUT);
    		when(oldInputArgument.append(any(StringBuilder.class))).thenReturn(inputArgument);
    		when(oldInputArgument.substring(anyInt())).thenReturn(STEP_THREE_STRING_INPUT);
    		
    		mockStaticPattern.when(() -> Pattern.compile(anyString())).thenReturn(mockPattern);
    		doReturn(0,3).when(mockMatcher).start();
    		doReturn("//$\n2$3$8".split(NEW_LINE_STRING)).doReturn("2$3$8".split("\\"+STEP_THREE_REGEX)).when(mockPattern).split(anyString());
    		doReturn(mockMatcher).when(mockPattern).matcher(any(StringBuilder.class));
    		doReturn("2$3$8").when(mockMatcher).replaceAll(anyString());
    		doReturn(true).when(mockMatcher).find();
    		mockInteger.when(() -> IntegerProxy.parseInt(anyString())).thenCallRealMethod();
    		
	    	results = calculator.add(STEP_THREE_STRING_INPUT);
	    	
	        assertEquals("Unexpected return value:\n", STEP_THREE_INTEGER_EXPECTED_OUTPUT, results);
	        mockInteger.verify(() -> IntegerProxy.parseInt(anyString()), times(STEP_THREE_STRING_INPUT.split("\\"+STEP_THREE_REGEX).length - 1));
	        verify(inputArgument, times(2)).setLength(anyInt());
	        verify(oldInputArgument, times(1)).setLength(anyInt());
	        verify(inputArgument, times(2)).append(anyString());
	        verify(oldInputArgument, times(1)).append(any(StringBuilder.class));
	        verify(mockPattern, times(3)).matcher(any(StringBuilder.class));
	        verify(mockMatcher, times(1)).find();
	        verify(mockMatcher, times(2)).start();
	        verify(mockPattern, times(2)).split(anyString());
	        defaultVerification(mockStaticPattern);
    	} catch (Exception e) {
    		e.printStackTrace();
    		Assert.fail();
    	}
    }
    
    @Test
    public void testAddFailureNegativesNotAllowed() {
    	try(
			MockedStatic<IntegerProxy> mockInteger = mockStatic(IntegerProxy.class);
			MockedStatic<Pattern> mockStaticPattern = mockStatic(Pattern.class);
		){
    		try {
	    		when(inputArgument.append(anyString())).thenReturn(inputArgument);
	    		when(inputArgument.length()).thenReturn(STEP_FOUR_STRING_INPUT.length());
	    		when(inputArgument.toString()).thenReturn(STEP_FOUR_STRING_INPUT);
	    		
	    		mockStaticPattern.when(() -> Pattern.compile(anyString())).thenReturn(mockPattern);
	    		doReturn(STEP_FOUR_STRING_INPUT.split(DEFAULT_REGEX)).when(mockPattern).split(anyString());
	    		doReturn(mockMatcher).when(mockPattern).matcher(any(StringBuilder.class));
	    		doReturn(STEP_FOUR_STRING_INPUT).when(mockMatcher).replaceAll(anyString());
	    		doReturn(false).when(mockMatcher).find();
	    		mockInteger.when(() -> IntegerProxy.parseInt(anyString())).thenCallRealMethod();
	    		
		    	results = calculator.add(STEP_FOUR_STRING_INPUT);
		    	
		    	Assert.fail("Unexpected successful execution");
	    	} catch (NegativesNotAllowedException e) {
	    		assertEquals("The exception did not contain the required negative number:\n",STEP_FOUR_EXPECTED_INT_OUTPUT, e.getCulpritNumber());
	    		mockInteger.verify(() -> IntegerProxy.parseInt(anyString()), times(5));
		        testOneTwoAndFiveVerification();
		        defaultVerification(mockStaticPattern);
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    		Assert.fail();
    	}
    }
    
    private void testOneTwoAndFiveVerification() {
    	verify(inputArgument, times(1)).setLength(anyInt());
        verify(oldInputArgument, times(1)).setLength(anyInt());
        verify(inputArgument, times(1)).append(anyString());
        verify(oldInputArgument, times(0)).append(any(StringBuilder.class));
        verify(mockPattern, times(2)).matcher(any(StringBuilder.class));
        verify(mockMatcher, times(0)).start();
        verify(mockMatcher, times(1)).find();
        verify(mockPattern, times(1)).split(anyString());
    }
    
    private void defaultVerification(MockedStatic<Pattern> mockStaticPattern) {
    	verify(inputArgument, times(1)).length();
    	mockStaticPattern.verify(() -> Pattern.compile(anyString()), times(3));
    	verify(mockMatcher, times(1)).replaceAll(anyString());
    }
}
