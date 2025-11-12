package com.codegnan.interfaces;

import com.codegnan.exceptions.InsufficientFundsException;
import com.codegnan.exceptions.InsufficientMachineBalanceException;
import com.codegnan.exceptions.InvalidAmountException;
import com.codegnan.exceptions.NotAOperatorException;

public interface IATMService {
	//to get the user type whether user is operator or normal user
	public abstract String getUserType()throws NotAOperatorException;
	//InsufficeintFundsException to withdraw more than your balance
	//InvalidAmountException deposit multiples of 100 and >0
	//InsufficientMachineBalanceException to draw morethan ATM Balance
	public abstract double withdrawAmount(double wthAmount)throws 
	InsufficientFundsException,
	InvalidAmountException,
	InsufficientMachineBalanceException;
	//to deposit amount we will get InvalidAmountException we can deposit only multiples of 100
	public abstract void depositAmount(double dptAmount)
	//to check your account balance
	throws InvalidAmountException;
	public abstract double checkAccountBalance();
	//to change the pin number
	public abstract void changePinNumber(int pinNumber);
	//to get pin number
	public abstract int getPinNumber();
	//to get the user name
	public abstract String getUserName();
	//to decrease the number of chances while entered the wrong pin number
	public abstract void decreaseChances();
	//to get the chances of a pin number
	public abstract int getChances();
	//to reset the number of chances by the bank operator
	public abstract void resetPinChances();
	//to get mini statement of account
	public abstract void generateministatement();
}
