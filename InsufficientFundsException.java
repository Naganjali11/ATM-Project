package com.codegnan.exceptions;

public class InsufficientFundsException extends Exception{
	public InsufficientFundsException(String errorMsg) {
		super(errorMsg);
	}

}
