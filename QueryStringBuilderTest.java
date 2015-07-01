package com.bbva.kqof.webcore.services.aso.rest;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class QueryStringBuilderTest {
	
	
	@Test
	public void testInit(){
		Assert.assertTrue(QueryStringBuilder.init().buildQuery().equals(StringUtils.EMPTY));
	}
	
	
	@Test
	public void testComparisonOperator(){
		Assert.assertTrue(QueryStringBuilder
						.init()
						.setComparisonOperator(">")
						.addComparisonOperatorSymbol()
						.buildQuery().equals(">"));
	}
	
	@Test
	public void testDelimitationSymbol(){
		Assert.assertTrue(QueryStringBuilder
						.init()
						.setDelimitationSymbol(" AND ")
						.addDelimitationSymbol()
						.buildQuery().equals(" AND "));
	}
	
	@Test
	public void testEnclosingSymbols(){
		String enclosedString=QueryStringBuilder
				.init()
				.openEnclosingSymbol("<<")
				.addToken("dkjshfdkshfkjdshfkjdshfkjdshkfj")
				.closeEnclosingSymbol(">>")
				.buildQuery();
		
		
		Assert.assertTrue(enclosedString.startsWith("<<"));
		Assert.assertTrue(enclosedString.endsWith(">>"));
	}
	
	
	@Test
	public void testAddToken(){
		Assert.assertTrue(QueryStringBuilder
						.init()
						.addToken("A")
						.buildQuery().equals("A"));
	}
	
	@Test
	public void testAddTokens(){
		Assert.assertTrue(QueryStringBuilder
						.init()
						.setDelimitationSymbol(",")
						.addTokens(new String[]{"A","B","C"})
						.buildQuery().equals("A,B,C"));
	}
	
	@Test
	public void testAddComparison(){
		Assert.assertTrue(QueryStringBuilder
						.init()
						.setComparisonOperator("==")
						.addComparison("A","1")
						.buildQuery().equals("A==1"));
	}
	
	@Test
	public void testAddComparisons(){
		Assert.assertTrue(QueryStringBuilder
				.init()
				.setComparisonOperator("==")
				.setDelimitationSymbol("-")
				.addComparison("A",new String[]{"1","2","3"})
				.buildQuery().equals("A==1-A==2-A==3"));
	}

	@Test
	public void testIfPrintTrue(){
		Assert.assertTrue(QueryStringBuilder
				.init()
				.ifPrint(true)
				.addToken("A")
				.endIfPrint()
				.buildQuery().equals("A"));
	}
	
	@Test
	public void testIfPrintFalse(){
		Assert.assertTrue(QueryStringBuilder
				.init()
				.ifPrint(false)
				.addToken("A")
				.endIfPrint()
				.buildQuery().equals(StringUtils.EMPTY));
	}
}
