package cp213;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;



/**
 * Handles the GUI portion of the vending machine. May use other GUI classes for
 * individual elements of the vending machine. Should use the VendModel for all
 * control logic.
 *
 * @author Brian Ha
 * @version 2021-03-19
 */
@SuppressWarnings("serial")
public class VendView extends JPanel {
	
	//declares public variables to be shared within the functions (pads)
	private String ALPHA = "ABCD";
    private VendModel model = null;
    private JLabel selection = new JLabel();
    private Snack[][] snacks = new Snack[4][4];
    private JLabel total = new JLabel();
    JLabel[][] image = new JLabel[4][4];
    private double cost = 0;
	private Snack snackClass;
	
	private JFrame popUp = new JFrame();
	private JButton cash = new JButton("Cash");
	private JButton credit = new JButton("Credit");
	
	
	private static DecimalFormat df2 = new DecimalFormat("#.##");
    
    public VendView(VendModel model) {
	this.model = model;
	
	this.snackClass = new Snack();
	//this.setPreferredSize(new Dimension(900, 750));
	// your code here
	this.setLayout(new GridBagLayout());
	
	this.setOpaque(true);
	
	this.onebyone();
	

	
	
    }
    
    
    /**
     * 
     * Wallet/Selection interface. To be added onto the right grid of the vending machine
     *
     * @param none
     * @return pad - JPanel containing the interface for user selection and checkout, with wallet/credit payment
     * 
     */
   private JPanel wallet() {
	   
	   
	   //declaring GridBagConstraints variable for positioning of buttons/labels
	   GridBagConstraints gbc = new GridBagConstraints();
	   
	   //pad - the wallet/selection interface. Every JLabel and button gets added to this JPanel
   		JPanel pad = new JPanel();
   		
   		pad.setLayout(new GridBagLayout());
   		
   		//Label with word "Cost: "
   		JLabel cost = new JLabel("Cost: ");
		
		//Label with word "Wallet: "
		JLabel wallet = new JLabel ("Wallet: ");
		
		//Label that will hold the amount of change a user slots into the machine per use
		JLabel current = new JLabel();
		//Label that helps with spacing
		JLabel spacer = new JLabel("    ");
		//Buttons that allow the user to input a certain amount of change from their 'wallet'
		JButton nickel = new JButton("5¢  ");
		JButton dime = new JButton("10¢");
		JButton quarter = new JButton("25¢");
		JButton dollar = new JButton("$1  ");
		JButton toonie = new JButton("$2  ");
		JButton fiveBill = new JButton("$5  ");
		JButton tenBill = new JButton("$10");
		
		
		//Buttons that allow the user to: 
		//Return a users slotted change
		JButton takeBack = new JButton("Return");
		//Reset a users selection
		JButton reset = new JButton("Reset");
		//Confirm a users selection to continue to checkout
		JButton confirm = new JButton("Confirm");
		
		
		//modify the size and background for the selection label for visibility
		selection.setPreferredSize(new Dimension(130, 40));
		selection.setBackground(Color.WHITE);
		selection.setBorder( BorderFactory.createTitledBorder("Selection"));
		selection.setOpaque(true);

		
		//modify the size and background for the total label for visibility
		total.setPreferredSize(new Dimension(65, 20));
		total.setBackground(Color.WHITE);
		total.setOpaque(true);
		
		
		//modify the size and background for the current label
		current.setPreferredSize(new Dimension(65, 20));
		current.setBackground(Color.WHITE);
		current.setOpaque(true);
		
		
		//gbc variables for any modifications
		gbc.weightx = 0.5;
		gbc.weighty = 0.25;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		
		
		
		
	
	
		//start of adding all the buttons in proper format to pad label
		
		//first row
		int x = 1; 
		int y = 2;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(cost, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		pad.add(total, gbc);
		
		
		//second row
		y++;
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(cash, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		pad.add(credit, gbc);
		
		//third row
		y++;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth =2;
		pad.add(spacer, gbc);
		gbc.gridwidth = 1;
		
		
		//fourth row
		y++;
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(wallet, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		pad.add(current, gbc);
		
		gbc.gridx = x+2;
		gbc.gridy = y;
		pad.add(takeBack, gbc);
		
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(00,0,0,0);
		pad.add(selection,gbc);
		
		//fifth row
		gbc.gridy = 1;
		gbc.gridx = 2;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(00,0,20,0);
		pad.add(reset,gbc);
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		pad.add(confirm,gbc);
		gbc.insets = new Insets(0,0,0,0);
		
	
		
		//sixth row
		y++;
		gbc.gridwidth = 1;
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(nickel, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		pad.add(dime, gbc);
		
		//seventh row
		y++;
		gbc.gridwidth = 1;
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(quarter, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		pad.add(dollar, gbc);
		
		//eight row
		y++;
		gbc.gridwidth = 1;
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(toonie, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		pad.add(fiveBill, gbc);
		
		//ninth row
		y++;
		gbc.gridwidth = 1;
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(tenBill, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		gbc.ipady = 1;
		
		//Cash button interaction
		cash.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				//basic logic to check if the checkout cost is 0
				if(model.getTotal() == 0) {
					//if 0, the user may not have finished the checkout process
					noConfirm();
				}else {
					//if not 0, continue with payment
					String result = model.pay();
					
					//result sends back a string, check if user has no change 
					if(result.equals("No Change")) {
						noChange();
					}else {
						
						//set the remaining amount of slotted text
						current.setText(result);
						
						//set the resulting checkout cost
						total.setText(df2.format(model.getTotal()));
						
						//if the checkout total over 0 and the user didn't slot change in the machine
						if(model.getTotal() != 0 && model.getSlotted() == 0) {
							noSlotted();
						}
						//if the checkout total is over 0 but the user didn't slot enough change in the machine 
						else if((model.getTotal() != 0 && model.getSlotted() != 0)){
							notEnough();
						}
						
						//clears out the snacks that were selected, and replaces the images of the selected snacks
						// 	with blank picture to indicate that they're taken
						snackClass.clearChosen(model, snacks, image);
						//if the checkout Total is 0, then reset the payment side and selection of the machine to start process of second order
						if(model.getTotal() == 0) {
							confirmation();
							selection.setText("");
							model.reset();
						}
						
					}
				}
					
			
				}
			});
		
		
		
		credit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//basic logic to check if the checkout cost is 0
				if(model.getTotal() == 0) {
					//if 0, the user may not have finished the checkout process
					noConfirm();
				}else {
					//gets current slotted amount from wallet
					String temp = current.getText();
					//replaces the current slotted amount with "verifying..." to give off the impression of verifying a credit card
					current.setText("Verifying...");
				
					//try - catch block for the method used from the delay class
					try {
						//runs method to see if the credit card will be accepted or not
						boolean accepted = model.creditPay();
						creditResult(accepted);
						
						
						if(accepted) {
							//if accepted
							//runs regular checkout process
							if(model.getTotal() != 0 && model.getSlotted() == 0) {
								noSlotted();
							}
							
							//runs clearing process
							snackClass.clearChosen(model, snacks, image);
							if(model.getTotal() == 0) {
								confirmation();
								selection.setText("");
								model.reset();
							}
							
							//displays the amount of change that the user slotted into the machine prior to credit payment
							total.setText(df2.format(model.getTotal()));
							
						}
						current.setText(temp);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		//buttons that 'slot' the specified amount of change into the machine
		nickel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					model.addNickel();
					current.setText(df2.format(model.getSlotted()));
					
				}
			});
		
		dime.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					model.addDime();
					current.setText(df2.format(model.getSlotted()));
					
				}
			});
		
		quarter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.addQuarter();
				current.setText(df2.format(model.getSlotted()));
					
				}
			});
		
		dollar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.addDollar();
				current.setText(df2.format(model.getSlotted()));
					
				}
			});
		
		toonie.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.addToonie();
				current.setText(df2.format(model.getSlotted()));
					
				}
			});
		
		fiveBill.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.addFivebill();
				current.setText(df2.format(model.getSlotted()));
					
				}
			});
		
		tenBill.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				model.addTenbill();
				current.setText(df2.format(model.getSlotted()));
					
				}
			});
		
		
		//returns change by running the returnChange() function from VendModel
		takeBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				returnChange();
				current.setText("0");
				
				}
			});
		
		//clears the user's current order and selection
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!selection.getText().equals("")) {
					snackClass.reset(selection.getText(), snacks, model);
				}
				
				selection.setText("");
				total.setText("");
				model.setTotal(0);
				
				
					
				}
			});
		
		//user presses this to continue to checkout
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//checks if the user has put a selection into the selection box
				if(!selection.getText().equals("")) {
					//calculates checkoutTotal from the class Snack
					double cost = snackClass.orderProcess(selection.getText(), snacks, total.getText(),model);
					//display checkout cost
					total.setText(Double.toString(cost));
					
					
				}
					
					
					
		}
			});
		
		//return pad containing the wallet/selection interface
		return pad;
   }
    
   
   
   
   /**
    * 
    * numpad/choice interface. To be added onto the right grid of the vending machine
    *
    * @param none
    * @return pad - JPanel containing the interface for user selection and current choice
    * 
    */
   
    private JPanel numpad() {
    	GridBagConstraints gbc = new GridBagConstraints();
    	JPanel pad = new JPanel();
    	
    	pad.setLayout(new GridBagLayout());
    	
    	//the label that will display current user input-choice
    	JLabel choice = new JLabel();
    	//label modification
    	choice.setPreferredSize(new Dimension(140, 20));
		choice.setBackground(Color.WHITE);
		choice.setOpaque(true);
    	
    	
		//the numpad button
    	JButton zero = new JButton("0");
		JButton one = new JButton("1");
		JButton two = new JButton("2");
		JButton three = new JButton("3");
		
		
		JButton a = new JButton("A");
		JButton b = new JButton("B");
		JButton c = new JButton("C");
		JButton d = new JButton("D");
		
		//numpad reset current input button
		JButton cancel = new JButton("<");
		//bring current choice to selection label
		JButton next = new JButton(">");
		
		

		
		
		
		
		//gbc variables for any modifications
		gbc.weightx = 0.25;
		gbc.weighty = 0.25;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		      
		gbc.gridy = 2;
		
		//inserting the numpad
		int x = 1; 
		int y = 2;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		
		//row one
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(a, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		pad.add(b, gbc);
		gbc.gridx = x+2;
		gbc.gridy = y;
		pad.add(c, gbc);
		
		
		//row two
		y++;
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(d, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		pad.add(zero, gbc);
		gbc.gridx = x+2;
		gbc.gridy = y;
		pad.add(one, gbc);
		
		//row 3
		y++;
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(two, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		pad.add(three, gbc);
		
		
		//row 4
		y++;
		gbc.gridx = x;
		gbc.gridy = y;
		pad.add(cancel, gbc);
		gbc.gridx = x+1;
		gbc.gridy = y;
		pad.add(next, gbc);
		
		//go above row 1 to place choice label
		gbc.gridy = y - 4;
		gbc.gridx = x;
		gbc.gridwidth = 3;
		
		 
		
		pad.add(choice, gbc);
		
		
	
		
		//adding input to the keypad
		one.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String msg = choice.getText();
					choice.setText(msg + "1");
				}
			});
		
		
		//adding input to the keypad
		two.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String msg = choice.getText();
					choice.setText(msg + "2");
				}
			});
		
		three.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String msg = choice.getText();
					choice.setText(msg + "3");
				}
			});
		
		zero.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String msg = choice.getText();
					choice.setText(msg + "0");
				}
			});
		
		a.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String msg = choice.getText();
					choice.setText(msg + "A");
				}
			});
		
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String msg = choice.getText();
					choice.setText(msg + "B");
				}
			});
		
		c.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String msg = choice.getText();
					choice.setText(msg + "C");
				}
			});
		
		d.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String msg = choice.getText();
					choice.setText(msg + "D");
				}
			});
	
		
		//upon pressing this button
		//it will check if the text in the choice label is valid for the next step of ordering
		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					String msg = selection.getText();
					String input = choice.getText();
					
					//checks if the input is valid, and if the order is unique to what is currently selected
					if(model.inputCheck(input) && !msg.contains(input)) {
						if(snackClass.snackPresent(snacks, input)) {
							selection.setText(msg + input + " ");
							choice.setText("");
							
						}
						else {
							//if the item that the user selected doesn't exist
							itsGone();
							choice.setText("");
						}
						
					}
					else {
						//if the input is invalid or already selected
						choice.setText("INVALID, PRESS '<'");
						next.setEnabled(false);
						
					}
					
		}
			});
		
		//clears current user input
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					choice.setText("");
					next.setEnabled(true);
					
		}
			});
		
		return pad;
    }
    
    
    
    /**
     * 
     * gives the impression of a vending machines' available selection. To be added onto the left grid of the vending machine gui
     *
     * @param none
     * @return machine - JPanel containing the interface for user selection and current choice
     * 
     */
    private JPanel vendmachine() {
    	
    	//machine panel
    	JPanel machine = new JPanel();
    	//GridBagConstraints gbc to help positioning
    	GridBagConstraints gbc = new GridBagConstraints();
    	
    	//fixing size of machine panel
    	machine.setLayout(new GridBagLayout());
    	machine.setPreferredSize(new Dimension(500,500));
    	
    	
    	//holds the labels that will identify item slot positions
    	JLabel choice = new JLabel("label");
    	choice.setPreferredSize(new Dimension(140, 20));
		choice.setBackground(Color.WHITE);
	
		choice.setOpaque(true);
    	
    	
    	//variables that help with x and y positioning
    	int r = 0;
    	int letR = 0;
    	
    	
    	
    	
    	//for loop that places that appropriate images of snacks and label row that indicates the positions of the snacks above
    	//each iteration places 4 snacks, and the label row below
    	for(char letter : ALPHA.toCharArray()) {
    		
    		//row of snacks
    		//first creates a snack object
    		//creates a new Jlabel image containing the image of the snack from the newly created snack object
    		//add that newly created snack object to a 2d array of snacks for later calculations
    		//adds that image to the 2d Jlabel array as well for later GUI use
    		//displays snack image on current coordinates
    		for(int i = 0; i < 4; i++) {
        		Snack snack = new Snack();
        		JLabel img = new JLabel(snack.getImg());
        		snacks[r][i] = snack;
        		image[r][i] = img;
        		gbc.gridx = i;
        		gbc.gridy = letR;
        		gbc.gridwidth = 1;
        		gbc.insets = new Insets(0,0,0,20);
            	machine.add(image[r][i],gbc);
            	
            	
            	
            	
        	}
    		
    		//Jlabel that goes below the current row of snack images
    		//setup of label
    		String curr_row = String.format("%5s%25s%35s%25s", 
    				letter + "0" + " ($"+snacks[r][0].getPrice()+")"
    				,letter+ "1"+ " ($"+snacks[r][1].getPrice()+")"
    				,letter+"2"+ " ($"+snacks[r][2].getPrice()+")"
    				,letter+"3"+ " ($"+snacks[r][3].getPrice()+")");
        	JLabel item_row = new JLabel(curr_row);
        	
        	//modification of Jlabel
        	item_row.setPreferredSize(new Dimension(400,20));
        	item_row.setBackground(Color.WHITE);
        	item_row.setOpaque(true);
        	//gbc modification
        	gbc.gridy = letR + 1;
        	gbc.gridx = 0;
        	gbc.gridwidth = 5;
        	gbc.insets = new Insets(0,0,0,0);
        	//insertion of Jlabel
        	machine.add(item_row,gbc);
        	
        	//iterate x-axis, y-axis variables
        	r++;
        	letR = letR +2;
    	}
    	
    	
    	
    	
    	
    	return machine;
    }
    
    
    /**
     * 
     * a 1x2 grid JPanel that would contain the left, right grid for the machine
     * displays the entire machine
     *
     * @param none
     * @return none
     * 
     */
    private void onebyone() {
    	GridBagConstraints gbc = new GridBagConstraints();
    	
    	//left, right panel declaration
    	JPanel left = new JPanel();
    	JPanel right = new JPanel();
    	
    	//exit slot label to display an exit slot, that all vending machines have in real life
    	JLabel exitSlot = new JLabel();
    	
    	
    	
    	//modifications of right grid, to make it skinnier than the left
    	right.setPreferredSize(new Dimension(270,700));
    	
    	//modification of the exitslot, to make it look like an exit slot
    	exitSlot.setPreferredSize(new Dimension(450,100));
    	exitSlot.setBackground(Color.WHITE);
    	exitSlot.setOpaque(true);
    	exitSlot.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    	
    	
    	
    	
    	
    	
    	//modify the layout of current panel
    	this.setLayout(new GridBagLayout());
    	//layer on left and right Jpanels to current panel
    	this.add(left);
    	this.add(right);
    	
    	//set the layout of the right panel
    	right.setLayout(new GridBagLayout());
    	
    	
    	
    	//adding the numpad to the right panel
    	gbc.insets = new Insets(30,50,40,0);
    	right.add(numpad());
    	
    	//incrementing y axis, 
    	//adding wallet interface onto the right panel, below the numpad panel
    	gbc.gridy = 2;
    	gbc.insets = new Insets(30,40,0,10);
    	right.add(wallet(),gbc);
    	
    	
    	
    	//set the layout of the left panel
    	//adds the vending machine interface
    	left.setLayout(new GridBagLayout());
    	left.add(vendmachine());
    	gbc.insets = new Insets(30,0,0,0);
    	left.add(exitSlot,gbc);
    	
    	
    }
    
    /**
     * Pop up, letting user know that their purchase has been sucessful
     *
     * @param none
     * @return none
     * 
     */
    private void confirmation() {
    	String msg = "You retrieved " + model.getitemsBought() + " snacks.\n" +
    				model.whatsLeft();
    	JOptionPane.showMessageDialog(popUp, msg, "Congrats!",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Pop up, giving back user their change
     *
     * @param none
     * @return none
     * 
     */
    
    private void returnChange() {
    	popUp = new JFrame();
    	JOptionPane.showMessageDialog(popUp, model.returnChange(), "Taking Back Your Change...",JOptionPane.INFORMATION_MESSAGE );
    }
    
    /**
     * Pop up, letting user know they chose nothing
     *
     * @param none
     * @return none
     * 
     */
    private void noSelection() {
    	String msg = "Nothing has been selected.\n" +
				model.whatsLeft();
	JOptionPane.showMessageDialog(popUp, msg, "You Chose Nothing Though...",JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    /**
     * Pop up, letting user know they tried to buy something with cash without actually putting any cash in
     *
     * @param none
     * @return none
     * 
     */
    private void noSlotted() {
    	String msg = "No change in machine.\n";
	JOptionPane.showMessageDialog(popUp, msg, "Put Some Change or Use Your Credit Card",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Pop up, letting user know they did not put enough change in the machine to cover the checkout cost
     *
     * @param none
     * @return none
     * 
     */
    private void notEnough() {
    	String msg = "Not enough change in machine for this cost.\n";
	JOptionPane.showMessageDialog(popUp, msg, "Put Some Change or Use Your Credit Card",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Pop up, letting user know the result of their credit card verfication
     *
     * @param accept - the result of their credit card verification
     * @return none
     * 
     */
    
    private void creditResult(boolean accept) {
    	String msg;
    	if(accept == true) {
    		msg = "Success! Your Credit Card was accepted.";
    		JOptionPane.showMessageDialog(null, msg,
    			      "Accepted!",JOptionPane.INFORMATION_MESSAGE);
    	}else {
    		msg = "Oops! Your Credit Card was rejected";
    		JOptionPane.showMessageDialog(null, msg,
  			      "Uh oh", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    /**
     * Pop up, letting user know the item slot they chose contains no snacks
     *
     * @param none
     * @return none
     * 
     */
    private void itsGone() {
    	String msg = "This Slot is Empty, There is No Snack Present Here.\n"
    			+ "Pick Something Else";
		JOptionPane.showMessageDialog(null, msg,
			      "It's Gone",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Pop up, letting user know the order process isn't completed 
     *
     * @param none
     * @return none
     * 
     */
    private void noConfirm() {
    	String msg = "Ordering proccess may not have been completed yet.\n"
    			+ "Make sure to press '>' to put your choice into your selection. \n"
    			+ "Then press 'Confirm' to send your selection to checkout.";
		JOptionPane.showMessageDialog(null, msg,
			      "Incomplete...",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Pop up, letting user know the machine doesn't have enough change in its reserves to provide the user with proper change
     *
     * @param none
     * @return none
     * 
     */
    private void noChange() {
    	String msg = "There's not enough change in the machine to give you the adequate amount of change.\n"
    			+ "Edit the reserves of the machine.";
		JOptionPane.showMessageDialog(null, msg,
			      "Find a different machine",JOptionPane.INFORMATION_MESSAGE);
    }
    
    
   
    

}
