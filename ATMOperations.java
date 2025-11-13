package com.codegnan.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.codegnan.cards.AXISDebitCard;
import com.codegnan.cards.HDFCDebitCard;
import com.codegnan.cards.KOTAKDebitCard;
import com.codegnan.cards.OperatorCard;
import com.codegnan.cards.SBIDebitCard;
import com.codegnan.exceptions.IncorrectPinLimitReachedException;
import com.codegnan.exceptions.InsufficientFundsException;
import com.codegnan.exceptions.InsufficientMachineBalanceException;
import com.codegnan.exceptions.InvalidAmountException;
import com.codegnan.exceptions.InvalidCardException;
import com.codegnan.exceptions.InvalidPinException;
import com.codegnan.exceptions.NotAOperatorException;
import com.codegnan.interfaces.IATMService;

public class ATMOperations {
    public static double ATM_MACHINE_BALANCE;
    public static ArrayList<String> ACTIVITY = new ArrayList<>();
    public static HashMap<Long, IATMService> database = new HashMap<>();
    public static boolean MACHINE_ON = true;
    public static IATMService card;

    public static IATMService validateCard(long cardNumber) throws InvalidCardException {
        if (database.containsKey(cardNumber)) {
            return database.get(cardNumber);
        } else {
            ACTIVITY.add("Accessed by: " + cardNumber + " is not compatible");
            throw new InvalidCardException("This is not a valid card");
        }
    }

    public static void checkATMMachineActivities() {
        System.out.println("=================Activities Performed===================");
        for (String activity : ACTIVITY) {
            System.out.println(activity);
            System.out.println("======================================");
        }
    }

    public static void resetUserAttempts(IATMService operatorCard) {
        IATMService card = null;
        long number;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Your Card Number:");
        number = scanner.nextLong();
        try {
            card = validateCard(number);
            card.resetPinChances();
            ACTIVITY.add("Accessed by: " + operatorCard.getUserName() + " to reset number of chances for user");
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }
    }

    public static IATMService validateCredentials(long cardNumber, int pinNumber) throws InvalidCardException,
            IncorrectPinLimitReachedException, InvalidPinException {
        if (database.containsKey(cardNumber)) {
            card = database.get(cardNumber);
        } else {
            throw new InvalidCardException("This Card Is Not A Valid Card");
        }
        try {
            if (card.getUserType().equals("operator")) {
                if (card.getPinNumber() != pinNumber) {
                    throw new InvalidPinException("Dear Operator. Please Enter Correct Pin Number");
                } else {
                    return card;
                }
            }
        } catch (NotAOperatorException | InvalidPinException e) {
            e.printStackTrace();
        }
        if (card.getChances() <= 0) {
            throw new IncorrectPinLimitReachedException("You have reached wrong limit of Pin Number. Which is 3 Attempts");
        }
        if (card.getPinNumber() != pinNumber) {
            card.decreaseChances();
            throw new InvalidPinException("You Have Entered a Wrong Pin Number");
        } else {
            return card;
        }
    }

    public static void validateAmount(double amount) throws InsufficientMachineBalanceException {
        if (amount > ATM_MACHINE_BALANCE) {
            throw new InsufficientMachineBalanceException("Insufficient Cash in the Machine");
        }
    }

    public static void validatedepositAmount(double Amount) throws InvalidAmountException, InsufficientMachineBalanceException {
        if (Amount % 100 != 0) {
            throw new InvalidAmountException("Please Deposit amounts in multiples of 100");
        }
        if (Amount + ATM_MACHINE_BALANCE > 2000000.0) {
            throw new InsufficientMachineBalanceException("You can't deposit cash as the limit of ATM Machines is reached");
        }
    }

    public static void operatorMode(IATMService card) {
        Scanner sc = new Scanner(System.in);
        double amount;
        boolean flag = true;
        while (flag) {
            System.out.println("OPERATOR MODE : Operator Name : " + card.getUserName());
            System.out.println("=================================================");
            System.out.println("||    1.Switch off the Machine   ||");
            System.out.println("||    2.To check the ATM machine Balance   ||");
            System.out.println("||    3.Deposit Cash in the Machine   ||");
            System.out.println("||    4.Reset the user PIN Attempts   ||");
            System.out.println("||    5.Activities performed on the Machine   ||");
            System.out.println("||    6.Exit Operator Mode   ||");
            System.out.println("=================================================");
            System.out.println("Enter your Choice");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    MACHINE_ON = false;
                    ACTIVITY.add("Accessed by : " + card.getUserName() + " activity performed switching off the ATM Machine");
                    flag = false;
                    break;
                case 2:
                    ACTIVITY.add("Accessed by : " + card.getUserName() + " activity performed Checking ATM Machine balance");
                    System.out.println("The balance of atm machine is : " + ATM_MACHINE_BALANCE + " is available");
                    break;
                case 3:
                    System.out.println("Enter the amount to deposit");
                    amount = sc.nextDouble();
                    try {
                        validatedepositAmount(amount);
                        ATM_MACHINE_BALANCE += amount;
                        ACTIVITY.add("Accessed by : " + card.getUserName() + " activity performed Depositing cash in the ATM machine");
                        System.out.println("============================================");
                        System.out.println("======== Cash added in the ATM machine ========");
                        System.out.println("============================================");
                    } catch (InvalidAmountException | InsufficientMachineBalanceException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    resetUserAttempts(card);
                    System.out.println("===============================================");
                    System.out.println("========= User attempts are reset ============");
                    System.out.println("===============================================");
                    ACTIVITY.add("Accessed by : " + card.getUserName() + " activity performed Resetting the pin attempts of user");
                    break;
                case 5:
                    checkATMMachineActivities();
                    break;
                case 6:
                    flag = false;
                    break;
                default:
                    System.out.println("You have entered a wrong option...");
            }
        }
    }

    public static void main(String[] args) {
        database.put(2222222222l, new AXISDebitCard("malli", 2222222222l, 50000.0, 2222));
        database.put(3333333333l, new HDFCDebitCard("arjun", 3333333333l, 60000.0, 3333));
        database.put(4444444444l, new SBIDebitCard("mani", 4444444444l, 50000.0, 4444));
        database.put(1111111111l, new KOTAKDebitCard("ravi", 1111111111l, 50000.0, 5555));
        database.put(1111111111l, new OperatorCard(1111111111l, 1111, "john")); // This will overwrite previous entry

        Scanner scanner = new Scanner(System.in);
        long cardNumber = 0;
        int pin = 0;
        double withdrawAmount = 0.0;
        double depositAmount = 0.0;

        while (MACHINE_ON) {
            System.out.println("Please Enter the debit card Number:");
            cardNumber = scanner.nextLong();
            System.out.println("Enter Pin Number:");
            pin = scanner.nextInt();

            try {
                card = validateCredentials(cardNumber, pin);

                if (card == null) {
                    System.out.println("Card Validation failed:");
                    continue;
                }

                ACTIVITY.add("Accessed By:" + card.getUserName()
                        + " Status: Accesses Approved");

                try {
                    if (card.getUserType().equals("operator")) {
                        operatorMode(card);
                        continue;
                    }
                } catch (NotAOperatorException e) {
                    e.printStackTrace();
                }
                
                boolean userFlag = true;
                while(userFlag) {
                    System.out.println("USER MODE:" + card.getUserName());
                    System.out.println("=====================");
                    System.out.println("|| 1.Withdraw Amount ||");
                    System.out.println("|| 2.Deposit Amount  ||");
                    System.out.println("|| 3.Check Balance   ||");
                    System.out.println("|| 4.Change Pin      ||");
                    System.out.println("|| 5.Mini Statement  ||");
                    System.out.println("|| 6.Exit User Mode  ||");
                    System.out.println("=====================");
                    System.out.println("Enter your Choice");
                    int option = scanner.nextInt();

                    try {
                        switch (option) {
                            case 1:
                                System.out.println("Enter Withdraw Amount ");
                                withdrawAmount = scanner.nextDouble();
                                card.withdrawAmount(withdrawAmount);
                                ATM_MACHINE_BALANCE -= withdrawAmount;
                                System.out.println("Withdraw amount successfully " + withdrawAmount +
                                        " new balance is " + card.checkAccountBalance());
                                ACTIVITY.add("Accessed by : " + card.getUserName() + " Activity .Amount withdrawn " + withdrawAmount + " from machine");
                                break;
                            case 2:
                                System.out.println("Enter deposit Amount ");
                                depositAmount = scanner.nextDouble();
                                validatedepositAmount(depositAmount);
                                ATM_MACHINE_BALANCE += depositAmount;
                                card.depositAmount(depositAmount);
                                System.out.println("Deposit Amount successfully " + depositAmount +
                                        " new Balance is : " + card.checkAccountBalance());
                                ACTIVITY.add("Accessed by : " + card.getUserName() + " Activity .Amount deposit " + depositAmount + " from machine");
                                break;
                            case 3:
                            	card.checkAccountBalance();
                                System.out.println("Current Balance is: " + card.checkAccountBalance());
                                ACTIVITY.add("Accessed By : " + card.getUserName() + " Activity . Checking balance");
                                break;
                            case 4:
                                System.out.println("Enter new PIN:");
                                pin = scanner.nextInt();
                                card.changePinNumber(pin);
                                ACTIVITY.add("Accessed By : " + card.getUserName() + " Activity . Changed PIN Number");
                                break;
                            case 5:
                               ACTIVITY.add("Accessed By : " + card.getUserName() + " Activity . Generate Mini Statement");
                               card.generateministatement(); 
                               break;
                               default:
                            	   System.out.println("You Have Entered a wrong Option");
                     
                        }
                        System.out.println("Do you want to Continue?(Yes/No)");
                        String nextOption=scanner.next();
                        if(nextOption.equalsIgnoreCase("n")) {
                        	break;//exit user mode
                        }
                    } catch (InvalidAmountException|InsufficientFundsException|InsufficientMachineBalanceException e) {
                        e.printStackTrace();
                    }
                }
                
            } catch (InvalidCardException | IncorrectPinLimitReachedException | InvalidPinException e) {
                e.printStackTrace();
                ACTIVITY.add("Accessed By:"+card.getUserName()+"Status: Access Denied");
                System.out.println(e.getMessage());
            }
        }
        System.out.println("=============================");
        System.out.println("Thank You For Using HDFC ATM Machine:");
        System.out.println("==============================");
    }
}
