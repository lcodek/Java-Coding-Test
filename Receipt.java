import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.LinkedList;

public class Receipt{

	//format for printing
	private static final DecimalFormat df = new DecimalFormat("#0.00");
	
	//Instance variables

	// linkedlist containing receipt items
	private LinkedList<ReceiptItem> receiptlist = new LinkedList<ReceiptItem>();

	//number of elements in array
	private int numElements = 0;

	//total sales tax
	private BigDecimal salestax = new BigDecimal(0);

	//total cost of items on receipt not including tax
	private BigDecimal totalpretax = new BigDecimal(0);

	/**
	 * Adds a new receipt item
	 * @param the receipt item to add
	 */
	public void add(ReceiptItem item) {
		this.receiptlist.add(item);
      	numElements++;
      	this.totalpretax = this.totalpretax.add(item.getPrice());
    }

    /**
    * Calculates the total tax for items on the receipt
    * @param basic the rate of basic sales tax
    * @param imported the rate of imported sales tax
    */
    public void calculateTax(String basictax, String importedtax) {
    	BigDecimal basic = new BigDecimal(basictax);
    	BigDecimal imported = new BigDecimal(importedtax);
    	for (int i = 0; i < this.numElements; i++) {
    		this.salestax = this.salestax.add(this.receiptlist.get(i).getTax(basic, imported));
    	}
    }

   	/**
	 * Displays the receipt in requested format
	 */
    public void display() {
    	df.setRoundingMode(RoundingMode.HALF_UP);
    	for (int i = 0; i < this.numElements; i++) {
    		ReceiptItem receiptelement = this.receiptlist.get(i);
    		System.out.println(receiptelement.getNumGoods() + " " 
    			+ receiptelement.getName() + ": " + df.format(receiptelement.getPrice()));
    	}
    	System.out.println("Sales Taxes: " + df.format(this.salestax));
    	System.out.println("Total: " + df.format(this.totalpretax.add(this.salestax)));
    } 


}