package makechange;

import java.util.Scanner;

public class CashRegister {

	public static void main(String[] args) {
		//initialize public variables
		double[] currencyArray = { 100, 50, 20, 10, 5, 1, .25, .10, .05, .01 };
		int[] billArray = new int[10];
		int numberOfBills = 0;
		int lastNonZero = 0;
		int counter = 0;
		int differentTypeCount = 0;
		String customerChange = "";
		String denominationType = "";

		printCashRegister();
		
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter the price of the item: $");
		double price = kb.nextDouble(); // collect item price
		System.out.print("Enter the amount received from the customer: $");
		double amount = kb.nextDouble(); // collect amount tendered
		kb.close();

		double remainingAmount = (Math.round((price - amount) * 100.0)) / 100.0;
		double changeTotal = (Math.round((amount - price) * 100.0)) / 100.0;
		double remainingChange = changeTotal;

		//control statements based on item price & amount tendered
		if (price == amount) {
			printBar("", 59); 
			System.out.println("\nThe customer has paid an exact amount. No change necessary.");
		} else if (price > amount) {
			printBar(remainingAmount, 26);
			System.out.printf("\nThe customer still owes $%.2f.", remainingAmount);
		} else {

			// loop to collect and store number of bills & last non-zero denomination for grammar.
			for (double bill : currencyArray) {

				numberOfBills = (int) (remainingChange / bill);
				// rounds to 2 decimal places & corrects for floating-point arithmetic errors
				remainingChange = (Math.round((remainingChange % bill) * 100.0)) / 100.0;
				billArray[counter] = numberOfBills;
				lastNonZero = (numberOfBills != 0) ? counter : lastNonZero;				
				counter++;

			}
			
			// loops to determine the correct customer change
			counter = 0;
			for (int numOfBills : billArray) {
				// skip iteration if zero
				if (numOfBills == 0) { 
					counter++;
					continue;
				// formatting for final string
				} else if (counter == lastNonZero) { 
					differentTypeCount++;
					denominationType = getDenominationType(counter, numOfBills);
					//Grammar: checks if there is more than one type of currency 
					customerChange = (differentTypeCount > 1) ? customerChange + "and " + denominationType + "." : customerChange +  denominationType + ".";
				// formatting for all non-final strings
				} else { 
					differentTypeCount++;
					denominationType = getDenominationType(counter, numOfBills);
					customerChange += denominationType + ", ";
				}
				
				counter++;

			}
			//control statement to print break bar of length equal to longest string
			if (customerChange.length() + 18 > (changeTotal + "").length() + 31) {
				
				printBar(customerChange, 18);
				
			} else {
				
				printBar((changeTotal + "").length(), 32);
				
			}

			System.out.printf("\nThe customer needs $%.2f in change.\n", changeTotal);
			System.out.println("\nGive the customer " + customerChange);

		}

	}
	
	// ASCII art
	public static void printCashRegister() {
		System.out.println("|||||||||||||||||||||||||||||||||||||");
		System.out.println("||                                 ||");
		System.out.println("||          CA$H REGISTER          ||");
		System.out.println("||                                 ||");
		System.out.println("|||||||||||||||||||||||||||||||||||||");
		System.out.println();
	}
	
	// prints a break line of the appropriate length
	public static void printBar(String str, int num) {
		int barLength = str.length();
		for (int i = 0; i < barLength + num; i++) {
			System.out.print("-");
		}
	}

	// prints a break line of the appropriate length
	public static void printBar(double remAmt, int num) {
		String str = remAmt + "";
		int barLength = str.length();
		for (int i = 0; i < barLength + num; i++) {
			System.out.print("-");
		}
	}

	public static String getDenominationType(int counter, int numOfBills) {

		switch (counter) {

		case 0: // $100
			return (numOfBills > 1) ? numOfBills + " one-hundred dollar bills" : numOfBills + " one-hundred dollar bill";
		case 1: // $50
			return (numOfBills > 1) ? numOfBills + " fifty dollar bills" : numOfBills + " fifty dollar bill";
		case 2: // $20
			return (numOfBills > 1) ? numOfBills + " twenty dollar bills" : numOfBills + " twenty dollar bill";
		case 3: // $10
			return (numOfBills > 1) ? numOfBills + " ten dollar bills" : numOfBills + " ten dollar bill";
		case 4: // $5
			return (numOfBills > 1) ? numOfBills + " five dollar bills" : numOfBills + " five dollar bill";
		case 5: // $1
			return (numOfBills > 1) ? numOfBills + " one dollar bills" : numOfBills + " one dollar bill";
		case 6: // $0.25
			return (numOfBills > 1) ? numOfBills + " quarters" : numOfBills + " quarter";
		case 7: // $0.10
			return (numOfBills > 1) ? numOfBills + " dimes" : numOfBills + " dime";
		case 8: // $0.05
			return (numOfBills > 1) ? numOfBills + " nickels" : numOfBills + " nickel";
		case 9: // $0.01
			return (numOfBills > 1) ? numOfBills + " pennies" : numOfBills + " penny";
		default:
			return "Error: -> switch | Data Invalid";
		}

	}

}
