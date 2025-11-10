package com.codegnan.exceptions;

public class IncorrectPinLimitReachedException extends Exception {
	public IncorrectPinLimitReachedException(String errorMsg) {
		super(errorMsg);
	}

}
