package cp213;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Vending machine model. Contains the algorithms for vending products, handling
 * change and inventory, and working with credit. Should not perform any GUI
 * work of any kind.
 *
 * @author Brian Ha
 * @version 2021-03-19
 */
public class VendModel {

	protected double reserve;
	protected double total;
	protected double slotted;
	protected int itemsBought;
	
	protected int nickelAmt;
	protected int dimeAmt;
	protected int quarterAmt; 
	protected int dollarAmt; 
	protected int toonieAmt; 
	protected int fivebillAmt;
	protected int tenbillAmt; 
	
	private Delay delay = new Delay();

	
	private String[] names = new String[] {"$10 bills", "$5 bills", "toonies", "loonies", "quarters", "dimes", "nickels"};
	
	public static double NICKEL = 0.05;
	public static double DIME = 0.10;
	public static double QUARTER = 0.25;
	public static double DOLLAR = 1;
	public static double TOONIE = 2;
	public static double FIVEBILL = 5;
	public static double TENBILL = 10;
	private double[] change = new double[] {TENBILL,FIVEBILL,TOONIE,DOLLAR,QUARTER,DIME, NICKEL};
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	
	/**
     * Constructor for the VendModel
     *
     * @param none
     * @return none
     * 
     */
	public VendModel(){
		
		this.total = 0;
		this.slotted = 0;
		this.itemsBought = 0;
		
		
		this.nickelAmt = 50;
		this.dimeAmt = 50;
		this.quarterAmt = 50;
		this.dollarAmt = 30;
		this.toonieAmt = 20;
		this.fivebillAmt =15;
		this.tenbillAmt = 10;
		
		this.reserve = this.nickelAmt*NICKEL + this.dimeAmt*DIME + this.quarterAmt*QUARTER
				+ this.dollarAmt * 1 + this.toonieAmt*2 + this.fivebillAmt * 5 + this.tenbillAmt*10;
		
		
	}
	
	
	public double getTotal() {
		return this.total;
	}
	public void setTotal(double total) {
		this.total = total;

	}
	
	public int getitemsBought() {
		return this.itemsBought;
	}
	
	public void setitemsBought(int itemsBought) {
		this.itemsBought = itemsBought;
	}
	
	public void addNickel() {
		this.slotted += NICKEL;
		this.nickelAmt += 1;
	}
	
	public void addDime() {
		this.slotted += DIME;
		this.dimeAmt += 1;
	}
	public void addQuarter() {
		this.slotted += QUARTER;
		this.quarterAmt += 1;
		
	}
	public void addDollar() {
		this.slotted += DOLLAR;
		this.dollarAmt += 1;
	}
	public void addToonie() {
		this.slotted += TOONIE;
		this.toonieAmt += 1;
	}
	public void addFivebill() {
		this.slotted += FIVEBILL;
		this.fivebillAmt += 1;
	}
	public void addTenbill() {
		this.slotted += TENBILL;
		this.tenbillAmt += 1;
	}
	
	public double getSlotted() {
		return this.slotted;
	}
	
	
	/**
     * updates the machine's total reserve amount based on the current amount of change it has
     *
     * @param none
     * @return ImageIcon this.image
     * 
     */
	public void updateReserve() {
		this.reserve = this.nickelAmt*NICKEL + this.dimeAmt*DIME + this.quarterAmt*QUARTER
				+ this.dollarAmt * 1 + this.toonieAmt*2 + this.fivebillAmt * 5 + this.tenbillAmt*10;
	}
	
	
	/**
     * resets the VendModel's basic properties
     *
     * @param none
     * @return ImageIcon this.image
     * 
     */
	public void reset(){
		this.total = 0;
		this.itemsBought = 0;
		
	}
	
	
	/**
     * Verifies the validity of user input
     *
     * @param String input - the user input
     * @return boolean valid (true if valid, false otherwise)
     * 
     */
	public boolean inputCheck(String input) {
		boolean valid = false;
		if(input.length() == 2) {
			if(Character.isUpperCase(input.charAt(0))) {
				if(Character.isDigit(input.charAt(1))) {
					valid = true;
				}
			}
		}
		
		return valid;
	}
	
	
	
	
	/**
     * payment logic for the machine
     *
     * @param none
     * @return String msg - the amount of change that is left over from the purchase 
     * 
     */
	public String pay() {
		
		//makes variables
		String msg = df2.format(total);
		String check = "";
		
		//creates a temp value that checks if the user has enough change slotted
		double result = this.slotted - this.total;
		if(result >= 0) {
			//if equal, check the reserves of the machine for adequate return change
			check = checkReserves();
			if(check.equals("Nothing")) {
				//if the machine has adequate change for the transaction, proceed with transaction
				msg = df2.format(result);
				this.total = 0;
				this.slotted = result;
			}
			else {
				//if machine out of change, say out of change
				msg = "No Change";
			}
		}
		else {
			//if the user did not slot enough change, nothing changes
			msg = df2.format(this.slotted);
		}
		
		return msg;
	}
	
	
	/**
     * credit card payment
     *
     * @param none
     * @return boolean result - true if the credit card is accepted, false otherwise
     * 
     */
	public boolean creditPay() throws Exception {
		//calls function to determine if credit card is accepted or rejected
		boolean result = delay.call();
		
		//if the credit card is accepted, continue with transaction
		if(result == true) {
			this.total = 0;
		}
		
		return result;
		
	}
	
	
	/**
     * checks the the machine to see if there is enough change to provide the exact amount of change back to the user
     *
     * @param none
     * @return String msg - the result of check
     * 
     */
	public String checkReserves() {
		String msg = "";
		
		double remaining = this.total;
		
		//array of amounts of certain bills. coins in the machine's reserves
		int[] reserve = new int[] {this.tenbillAmt, this.fivebillAmt, this.toonieAmt, this.dollarAmt, this.quarterAmt, this.dimeAmt, this.nickelAmt};
		
		int left;
		int used;
		//temp variable to see if the machine has less than the needed change to be returned
		double temp = this.reserve + this.slotted;
		
		if(temp <= this.total) {
			
			msg = "Not enough in the machine to provide change for this total";
		}
		else {
			//if theres enough change, go through the reserve array and deduct the amount of change to be returned to the user from reserves
			//check with temp variables if the machine has enough change in its reserves to provide the proper amount
			for(int i = 0; i < change.length; i++) {
				if(remaining >= change[i]) {
					used = (int)(remaining/change[i]);
					left = reserve[i] - used;
					if(left < 0) {
						msg = names[i];
					}else {
						remaining = remaining - used*change[i];
						//reserve[i] = left;
					}
				}
			}
			
			
			
		}
		
		if(remaining == 0) {
			msg = "Nothing";
		}
	
		
		
		return msg;
		
	}
	
	
	/**
    * Returns change to the user
    *
    * @param none
    * @return String msg - the coins/bills that is given to the user as their change
    * 
    */
	public String returnChange() {
		//creates an array of change amounts in the machine for easier checking the later for loop
		int[] reserve = new int[] {this.tenbillAmt, this.fivebillAmt, this.toonieAmt, this.dollarAmt, this.quarterAmt, this.dimeAmt, this.nickelAmt};
		
		int left;
		int used;
		String msg = "Change provided:\n";
		double wallet = this.slotted;
		//temp variable to see if the machine has less than the needed change to be returned
		double temp = this.reserve + this.slotted;
		if(temp < this.slotted) {
			
			msg = "Not enough in the machine to provide adequate change";
		}
		else {
			//if theres enough change, go through the reserve array and deduct the amount of change to be returned to the user from reserves
			for(int i = 0; i < change.length; i++) {
				if(wallet >= change[i]) {
					used = (int)(wallet/change[i]);
					left = reserve[i] - used;
					//if theres no more of the required change left, change the msg to say so
					if(left < 0) {
						msg = "no " + names[i] + " left.";
					}else {
						//else, deduct the current bills/coins being given back and let the user know in the addition to the msg 
						wallet = wallet - used*change[i];
						msg = msg + " " + used + " " + names[i] + "\n"; 
						reserve[i] = left;
					}
				}
			}
			//all change has been given back, so theres nothing slotted left, so slotted = 0
			this.slotted =0;
			
		}
		
		
	
		
		
		return msg;
	}
	
	
	/**
    * Tells the user how much change is left in the machine
    * if there isn't change change left, tell the user there isn't any change left
    *
    * @param none
    * @return String msg - msg that either tells the user the amount of change left in the machine
    * 
    */
	public String whatsLeft() {
		String msg = "";
		//check the amount slotted in the machine
		if(this.slotted == 0) {
			msg = "\nNo Change left in machine.";
			
		}else if(this.slotted != 0) {
			msg = "Amount still in machine: $" + df2.format(this.slotted);
		}
	
		return msg;
	}
	
	
	
	
	
		
	
	
}
















