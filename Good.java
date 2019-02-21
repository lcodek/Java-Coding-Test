public class Good {

//Instance variables

	//the name of the good
	private String name;

	//true if exempt from basic sales tax, false if has basic sales tax
	private boolean exempt;

	//true if good is imported, false if not
	private boolean imported;
   
   /* Construct Good with the inputed name and price
   	* Assumed to have basic sales tax and not imported
    * @param itemname = name of the good
    * @param extempt = true if the good is exempt from basic sales tax
    * @param imported = true if the good is imported
   */
    public Good(String itemname, boolean exemp, boolean isImport) {
      this.name = itemname;
      this.exempt = exemp;
      this.imported = isImport;
    }

    /**
	 * Gets the name of the good
	 *
	 * @return the name of the good
	 */
	public String getName(){
      return this.name;
    }
 
   /**
   	 * Gets where the good is exempt 
   	 *
	 * @return whether the good is exempt from basic sales tax
	 */
	public boolean isExempt(){
		return this.exempt;
    }
   
    /**
	 * Toggle whether the good is exempt from basic sales tax
	 */
	public void toggleExempt(){
		this.exempt = !this.exempt;
    }

   /**
   	 * Gets where the good is exempt 
   	 *
	 * @return whether the good is exempt from basic sales tax
	 */
	public boolean isImported(){
		return this.imported;
    }

}