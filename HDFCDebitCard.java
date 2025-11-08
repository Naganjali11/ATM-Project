package com.codegnan.cards;

import java.util.ArrayList;
import java.util.Collections;

import com.codegnan.exceptions.InsufficientFundsException;
import com.codegnan.exceptions.InsufficientMachineBalanceException;
import com.codegnan.exceptions.InvalidAmountException;
import com.codegnan.exceptions.NotAOperatorException;
import com.codegnan.interfaces.IATMService;

public class HDFCDebitCard implements IATMService{
	String name;
	long debitCardNumber;
	double accountBalance;
	int pinNumber;
	ArrayList<String>statement;
	final String type="user";
	int chances;
	

	public HDFCDebitCard(String name, long debitCardNumber, double accountBalance, int pinNumber) {
		chances=3;
		this.name = name;
		this.debitCardNumber = debitCardNumber;
		this.accountBalance = accountBalance;
		this.pinNumber = pinNumber;
		statement=new ArrayList<>();
	}

	@Override
	public String getUserType() throws NotAOperatorException {
		return type;
	}

	@Override
	public double withdrawAmount(double wthAmount)
			throws InsufficientFundsException, InvalidAmountException, InsufficientMachineBalanceException {
		if(wthAmount<=0||wthAmount%100!=0) {
			throw new InvalidAmountException
			("You Cannot withdraw zero and withdraw amount must be multiples of 100");
		}else {
			if(wthAmount>accountBalance) {
				throw new InsufficientFundsException
				("You Can't withdraw more than your account balance");
			}else {
				accountBalance-=wthAmount;
				statement.add("Debited:"+wthAmount);
				return wthAmount;
			}
		}
	}

	@Override
	public void depositAmount(double dptAmount) throws InvalidAmountException {
		if(dptAmount<=0||dptAmount%100!=0) {
			throw new InvalidAmountException
			("You can't deposit less than zero rupees and amount must be multiples of 100");
		}else {
		accountBalance+=dptAmount;
		statement.add("credited:"+dptAmount);
		}
	}

	@Override
	public double checkAccountBalance() {
		return accountBalance;
	}

	@Override
	public void changePinNumber(int pinNumber) {
		this.pinNumber=pinNumber;
	}

	@Override
	public int getPinNumber() {
		return pinNumber;
	}

	@Override
	public String getUserName() {	
		return name;
	}

	@Override
	public void decreaseChances() {
		--chances;
	}

	@Override
	public int getChances() {
		return chances;
	}

	@Override
	public void resetPinChances() {
		chances=3;
	}

	@Override
	public void generateministatement() {
		int count=0;
		if(statement.size()==0) {
			System.out.println("There are no transactions happend");
			return;
		}
		System.out.println("--------List 5 Transaction--------");
		for(String trans:statement) {
			if(count==0) {
				break;
			}
			System.out.println(trans);
			count--;
		}
		Collections.reverse(statement);
	}

}
