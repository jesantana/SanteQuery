package com.bbva.kqof.webcore.services.aso.rest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import org.springframework.util.Assert;

public class QueryStringBuilder {

	protected StringBuilder internalRepresentation;
	protected Deque<Boolean> conditionalPrintSet;
	
	protected String currentComparisonOperator="==";
	protected String currentDelimitationSymbol=";";
	
	
	private QueryStringBuilder(){
		internalRepresentation=new StringBuilder();
		conditionalPrintSet = new ArrayDeque<Boolean>();
	}
	
	public static QueryStringBuilder init(){
		return new QueryStringBuilder();
	}
	
	public QueryStringBuilder addToken(String token){
		if(mustPrint()){
			internalRepresentation.append(token);
		}
		return this;
	}
	
	public QueryStringBuilder addTokens(String[] token){
		if(mustPrint()){
			for(int i=0;i<token.length;i++){
				String currentToken=token[i];
				
				if(i>0){
					this.addDelimitationSymbol();
				}
				
				this.addToken(currentToken);
			}
		}
		return this;
	}
	
	public QueryStringBuilder setComparisonOperator(String operatos){
		this.currentComparisonOperator=operatos;		
		return this;
	} 
	
	public QueryStringBuilder setDelimitationSymbol(String symbol){
		this.currentDelimitationSymbol=symbol;		
		return this;
	} 
	
	public QueryStringBuilder openEnclosingSymbol(String symbol){
		return addToken(symbol);
	}
	
	public QueryStringBuilder closeEnclosingSymbol(String symbol){
		return addToken(symbol);
	}
	
	public QueryStringBuilder addDelimitationSymbol(){
		if(mustPrint()){
			this.addToken(currentDelimitationSymbol);
		}
		return this;
	}
	
	public QueryStringBuilder addComparisonOperatorSymbol(){
		if(mustPrint()){
			this.addToken(currentComparisonOperator);
		}
		return this;
	}
	
	public QueryStringBuilder addComparison(String key,String value){
		if(mustPrint()){
			this.addToken(key);
			this.addComparisonOperatorSymbol();
			this.addToken(value);
		}
		return this;
	}
	
	public QueryStringBuilder addComparison(String key,String[] values){
		if(mustPrint()){
			String[] keys=new String[values.length];
			Arrays.fill(keys,key);
			addComparison(keys, values);
		}
		return this;
	}
	
	public QueryStringBuilder addComparison(String[] key,String[] values){
		Assert.isTrue(key.length==values.length);
		if(mustPrint()){
			for(int i=0;i<key.length;i++){
				String currentKey=key[i];
				String currentValue=values[i];
				
				
				if(i>0){
					this.addDelimitationSymbol();
				}
				
				this.addComparison(currentKey, currentValue);
					
			}
		}
		
		return this;
	}
	
	public QueryStringBuilder ifPrint(boolean condition){
		conditionalPrintSet.push(condition);
		return this;
	}
	
	public QueryStringBuilder endIfPrint(){
		conditionalPrintSet.pop();
		return this;
	}
	
	protected boolean mustPrint(){
		return !conditionalPrintSet.contains(false);
	}
	
	public String buildQuery(){
		return internalRepresentation.toString();
	}

}
