import java.util.Scanner;
import java.util.LinkedList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.util.Iterator;

public class SalesTaxDriver {
	//basic sales tax rate
	private static final String basictaxrate = ".1";

	//imported tax rate
	private static final String importedtaxrate = ".05";

	//categories exempt from basic sales tax, used to prompt user to categorize
	private static final String exemptCategories = "books, food, or medical products";

	//name of the file with good names that have basic tax
	private static final String basicFile = "hasBasic.txt";

	//name of the file with good names that are exempt from basic tax
	private static final String exemptFile = "exempt.txt";

	//set of of all good names that are not exempt from basic sales tax
	private static Set<String> hasBasic = new HashSet<String>();

	//set of of all good names that are exempt from basic sales tax
	private static Set<String> exemptBasic = new HashSet<String>();


	//interpret user input
	private static Scanner keyboard = new Scanner(System.in);


	/*
	* Main method for problem 2
	* Calculates the receipts with tax and prints them
	*/
	public static void main (String[] args) throws FileNotFoundException, IOException {
		getHashSets();

		String filename = args[0];
		try {
			Scanner inputfile = new Scanner(new FileReader(filename));
			LinkedList<Receipt> receipts = parseFile(inputfile);

			for (int i = 0; i < receipts.size(); i++) {
				//calculate tax and display
				receipts.get(i).calculateTax(basictaxrate, importedtaxrate);
				System.out.println("\nOutput " + (i+1) + ":");
				receipts.get(i).display();
			}
		
		} catch (FileNotFoundException ex) {
			System.err.println("No file found");
			System.exit(0);
		}
		
		updateHashSets();
	}


	/*
	* Parses the file and returns back a list of receipt objects
	* @param scanner of the inputfile
	* @return list of receipts
	*/
	public static LinkedList<Receipt> parseFile(Scanner inputfile) {
		// linkedlist of receipts
		LinkedList<Receipt> receipts = new LinkedList<Receipt>();
		Receipt curr_receipt = null;

		while (inputfile.hasNext()) {
			String inputline = inputfile.nextLine();

			//make a new receipt object
			if (inputline.contains("Input")) {
				//if not on the first pass, then add current receipt to list 
				if (curr_receipt != null) {
					receipts.add(curr_receipt);
				}
				//then create new receipt
				curr_receipt = new Receipt();
				continue;
			}

			//parse receipt item info
			if (inputline.length() > 0) {
				String num_goods = inputline.substring(0, inputline.indexOf(" "));
				String name = inputline.substring(inputline.indexOf(" ") + 1, inputline.indexOf(" at "));
				String cost = inputline.substring(inputline.indexOf(" at ") + 4);

				curr_receipt.add(new ReceiptItem(name, isExempt(name), Integer.parseInt(num_goods), cost));

			}

		}
		receipts.add(curr_receipt);
		return receipts;
	}

	/*
	* Tells you whether the good is exempt from basic sales tax
	* @param name of the good
	* @return true if the good is exempt from basic sales tax
	*/
	public static boolean isExempt(String good) {
		if (exemptBasic.contains(good)) {
			return true;
		} else if (hasBasic.contains(good)) {
			return false;
		} else {
			System.out.println("Is " + good + " included in the categories of " + exemptCategories);
			System.out.println("Input 1 for yes, 0 for no");
			char input = keyboard.next().charAt(0);
			if (input == '1') {
				exemptBasic.add(good);
				return true;
			} else if (input == '0') {
				hasBasic.add(good);
				return false;
			}
		}
		//default is false
		return false;
	}

	/*
	* Loads the sets with exempt and nonexempt goods for basic sales tax from files
	*/
	public static void getHashSets() throws FileNotFoundException{
		Scanner basic = new Scanner(new File(basicFile));

		while (basic.hasNext()) {
			hasBasic.add(basic.nextLine().replaceAll("\\s+", " "));
		}

		Scanner exempt = new Scanner(new File(exemptFile));
		while (exempt.hasNext()) {
			exemptBasic.add(exempt.nextLine().replaceAll("\\s+", " "));
		}
		
		basic.close();
		exempt.close();
	}


	/*
	* Updates the file storing the sets with exempt and nonexempt goods for basic sales tax
	*/
	public static void updateHashSets() throws IOException{
		BufferedWriter basic = new BufferedWriter(new FileWriter(basicFile));
		Iterator<String> iterate = hasBasic.iterator();
		while (iterate.hasNext()) {
			basic.write(iterate.next() + "\n");
		}
		basic.close();

		BufferedWriter exempt = new BufferedWriter(new FileWriter(exemptFile));
		Iterator<String> iterate2 = exemptBasic.iterator();
		while (iterate2.hasNext()) {
			exempt.write(iterate2.next() + "\n");
		}
		exempt.close();

	}
}