import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReceiptItem {

// controls rounding: round up to nearest (100/roundinghelp)/100
private static final BigDecimal roundingHelp = new BigDecimal(20);

//Instance variables

	//the good
	private Good good; 

	//the number of goods
	private int num_goods;

	//the cost of goods
	private BigDecimal price;

   
   /* Construct ReceiptItem with the good and number of items
    * @param itemname = name of the good
    * @param extempt = true if the good is exempt from basic sales tax
    * @param count = the number of goods
    * @param cost = the cost of the good
   */
    public ReceiptItem(String itemname, boolean exemp, int count, String cost) {

      boolean isImport = false;
      if (itemname.contains("imported")) {
      	isImport = true;
      }

      this.good = new Good(itemname, exemp, isImport);

      this.num_goods = count;
      this.price = new BigDecimal(cost);
    }

    /**
	 * Gets the name of the good
	 *
	 * @return the name of the good
	 */
	public String getName(){
      return this.good.getName();
    }

   /**
	 * Gets the number of the goods
	 *
	 * @return the number of goods
	 */
	public int getNumGoods(){
      return this.num_goods;
    }

    /**
	 * Gets the price of the good
	 *
	 * @return the price
	 */
	public BigDecimal getPrice(){
      return this.price;
    }

   /**
    * Updates the cost to include tax and returns the tax
    * @param basic the rate of basic sales tax
    * @param imported the rate of imported sales tax
    * @return the tax
    */
    public BigDecimal getTax(BigDecimal basic, BigDecimal imported) {
    	BigDecimal taxrate = new BigDecimal(0);

    	//if not exempt from basic sales tax then calculate
    	if (!(this.good.isExempt())) {
    		taxrate = basic;
    	}
    	//add imported tax if imported
    	if (this.good.isImported()) {
    		taxrate = taxrate.add(imported);
    	}

    	BigDecimal tax = taxrate.multiply(this.price);
    	tax = this.roundTax(tax);
    	tax = tax.multiply(new BigDecimal(this.num_goods));

    	this.price = this.price.add(tax);
    	

    	return tax;
    }

    /**
    * Rounds the price of the item up to the nearest .05
    * @param decimal to round
    * @return the rounded number
    */
    public BigDecimal roundTax(BigDecimal toRound) {
    	//multiply the price by roundingHelp variable
    	BigDecimal temp = toRound.multiply(this.roundingHelp);
    	//round up in order to round up to nearest 0.05
    	temp = temp.setScale(0, RoundingMode.UP);
    	//get rounded price
    	toRound = temp.divide(this.roundingHelp);
    	return toRound;
    }

}