package cp213;

import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * 
 * Snacks model. Contains the code for creating a snack, as well as processing the cost
 * of a group of selected snacks.
 *
 * @author Brian Ha
 * @extends VendModel
 * @version 2021-03-19
 */


public class Snack extends VendModel {
	
	
	//lists containing the locations of the pictures (variants) of each snack category
	private String[] chocoVariants = new String[]{"resources/hars.png","resources/pit-pat.png","resources/choco-way.png","resources/oh-mary.png"};
	private String[] chipVariants = new String[]{"resources/bays.png","resources/hicks.png","resources/toyo.png"};
	private String[] gumVariants = new String[]{"resources/accel.png","resources/7gum.png","resources/chewys.png"};
	private String[] drinkVariants = new String[]{"resources/wator.png","resources/waps.png","resources/one-way.png"};
	
	//array 
	private String[] snackTypes = new String[]{"choco", "chips", "gum", "drink"};
	private double[] priceVariants = new double[] {2.25, 1.5, 2, 1.75};
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	private double price;
	private String type;
	private boolean taken;
	private ImageIcon img;
	
	

	
	
	/**
     * Snack constructor, instantiates a snack object, can be either a chocolate, chip, gum, or drink, based on a random index
     * All images of each snack category is randomized as well
     *
     * @param none
     * @return none
     * 
     */
	public Snack() {
		
		Random rand = new Random();
		int index;
		this.taken = false;
		this.type = snackTypes[rand.nextInt(snackTypes.length)];
		
		if(this.type.equals("choco")) {
			index = rand.nextInt(chocoVariants.length);
			this.img = new ImageIcon(chocoVariants[index]);
			this.price = priceVariants[0];
		}
		
		else if(this.type.equals("chips")) {
			index = rand.nextInt(chipVariants.length);
			this.img = new ImageIcon(chipVariants[index]);
			this.price = priceVariants[1];
		}
		
		else if(this.type.equals("gum")) {
			index = rand.nextInt(gumVariants.length);
			this.img = (new ImageIcon(gumVariants[index]));
			this.price = priceVariants[2];
		}
		
		else if(this.type.equals("drink")) {
			index = rand.nextInt(drinkVariants.length);
			this.img = new ImageIcon(drinkVariants[index]);
			this.price = priceVariants[3];
		}
	}
	
	
	/**
     * gets the type of snack from calling object
     *
     * @param none
     * @return String this.type
     * 
     */
	public String getType() {
		return this.type;
	}
	
	/**
     * gets the price of the calling snack object
     *
     * @param none
     * @return double this.price
     * 
     */
	public double getPrice() {
		return this.price;
	}
	
	/**
     * set the price of the calling snack object
     *
     * @param double price
     * @return none
     * 
     */
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	/**
     * get the image of the calling snack object
     *
     * @param none
     * @return ImageIcon this.image
     * 
     */
	public ImageIcon getImg() {
		return this.img;
	}
	
	
	/**
     * sets the calling object's image to be blank
     *
     * @param none
     * @return none
     * 
     */
	public void setBlank() {
		this.img = new ImageIcon("resources/empty.png");
	}
	
	/**
     * see if the calling object's snack status is taken
     *	true if taken,
     *	false if not
     * @param none
     * @return boolean this.taken
     * 
     */
	public boolean getTaken() {
		return this.taken;
	}
	
	
	 /**
     * Processes the order created by the user to calculate the checkout cost of the order
     * Also lists any snacks selected as taken
     * @param String order - selection string, Snack[][] snackBag - 2d array of snacks in the machine, String amount - existing cost, VendModel model - model
     * 
     * @return double cost - the total cost of the order
     * 
     */
	public double orderProcess(String order, Snack[][] snackBag, String amount,VendModel model) {
		String[] list = order.split(" ");
		
		double cost = 0;
		int col;
		int row;
		char letter;
		
		if(!amount.equals("")) {
			cost = Double.parseDouble(amount);
		}
		
		for(String section : list) {
			letter = section.charAt(0);
			row = letter - 65;
			letter = section.charAt(1);
			col = letter - '0';
			
			if(!snackBag[row][col].getTaken()) {
				cost += snackBag[row][col].getPrice();
				model.setTotal(cost);
				model.setitemsBought(model.getitemsBought()+1);
				snackBag[row][col].taken = true;
			}
			
		}
		
		return cost;
		
	}
	
	 /**
     * Looks through the vending machine inventory to 'put back' any snacks that may have been selected by the user due to a cancellation of an order
     *
     * @param String order - selection string, Snack[][] snackBag - 2d array of snacks in the machine, VendModel model - model
     * @return none
     * 
     */
	public void reset(String order, Snack[][] snackBag,VendModel model) {
		String[] list = order.split(" ");
		
		double cost = 0;
		int col;
		int row;
		char letter;
		
		
		
		for(String section : list) {
			letter = section.charAt(0);
			row = letter - 65;
			letter = section.charAt(1);
			col = letter - '0';
			
			if(snackBag[row][col].getTaken()) {
				cost = snackBag[row][col].getPrice();
				model.setTotal(model.getTotal() - cost);
				model.setitemsBought(model.getitemsBought()-1);
				snackBag[row][col].taken = false;
			}
			
		}
	}
	
	/**
     * Checks to see if a certain snack is taken or not
     *
     * @param String order - selection string, Snack[][] snackBag - 2d array of snacks in the machine, VendModel model - model
     * @return boolean present - true if not taken,  false if taken
     * 
     */
	public boolean snackPresent(Snack[][] snackBag, String slot) {
		
		int col;
		int row;
		char letter;
		letter = slot.charAt(0);
		row = letter - 65;
		letter = slot.charAt(1);
		col = letter - '0';
		
		return !snackBag[row][col].getTaken();
	}
	
	
	/**
     * Looks through the vending machine inventory to 'put back' any snacks that may have been selected by the user due to a cancellation of an order
     *
     * @param String order - VendModel model - model, Snack[][] snacks - 2d array of snacks in the machine, JLable[][] image - 2d list of snack images for machine inventory display
     * @return none
     * 
     */
	public void clearChosen(VendModel model, Snack[][] snacks, JLabel image[][]) {
		   if(model.getTotal() == 0) {
				for(int i = 0; i < snacks.length; i++) {
					for(int x = 0; x < snacks[0].length; x++) {
						if(snacks[i][x].getTaken()) {
							image[i][x].setIcon(new ImageIcon("resources/empty.png"));
							snacks[i][x].setPrice(0);
						}
					}
				}
				
				
				
			}
	   }
	
}


