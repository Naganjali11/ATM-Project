package com.codegnan.cards;

import com.codegnan.exceptions.InsufficientFundsException;
import com.codegnan.exceptions.InsufficientMachineBalanceException;
import com.codegnan.exceptions.InvalidAmountException;
import com.codegnan.exceptions.NotAOperatorException;
import com.codegnan.interfaces.IATMService;

public class OperatorCard implements IATMService{
	private long id;
	private int pinNumber;
	private String name;
	private final String type="operator";
	public OperatorCard(long id, int pinNumber, String name) {
		super();
		this.id = id;
		this.pinNumber = pinNumber;
		this.name = name;
	}
	public OperatorCard(String string, long l, int i) {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getUserType() throws NotAOperatorException {
		return type;
	}
	@Override
	public double withdrawAmount(double wthAmount)
			throws InsufficientFundsException, InvalidAmountException, InsufficientMachineBalanceException {
		return 0;
	}
	@Override
	public void depositAmount(double dptAmount) throws InvalidAmountException {
		
	}
	@Override
	public double checkAccountBalance() {
		return 0;
	}
	@Override
	public void changePinNumber(int pinNumber) {
		
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
		
	}
	@Override
	public int getChances() {
		return 0;
	}
	@Override
	public void resetPinChances() {
		
	}
	@Override
	public void generateministatement() {
		
	}
	

}
