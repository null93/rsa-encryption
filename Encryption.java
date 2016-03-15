import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * Encryption.java - This class takes in a data file as well as a pair of either a private key or a
 * public key, depending if we are doing encryption or decryption, and operates onto the data
 * respectively.  It is important to note that we don’t need separate classes for encryption or
 * decryption, since the only difference would be the key that is passed to this instance.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #03 - RSA Encryption / Decryption
 * @category    Encryption
 * @author      Rafael Grigorian
 * @author      Henvy Patel
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Encryption {

	/**
	 * 
	 */
	private Decimal n;

	/**
	 * 
	 */
	private Decimal k;

	/**
	 * 
	 */
	private String result;

	/**
	 *
	 */
	protected Encryption ( Key key, String filepath ) throws RSAException {
		// Initialize the internal variables
		this.initialize ( key );
		// Get File instance by initializing with filepath
        File file = new File ( filepath );
 		// Attempt to use the scanner
        try {
        	// Initialize Scanner class
            Scanner scanner = new Scanner ( file );
 			// Loop through until all lines have been read
            while ( scanner.hasNextLine () ) {
            	// Save the current line
                String line = scanner.nextLine ();
                // Print it out onto the screen
                this.result += this.process ( line ) + "\n";
            }
            // Close the scanner ( And file internally )
            scanner.close ();
            System.out.println ( this.result );
            // Save result back into file
            PrintWriter output = new PrintWriter ( file );
    		output.print ( this.result );
    		// Close printer
    		output.flush ();
    		output.close ();
        }
        // Attempt to catch throws
        catch ( Exception exception ) {
            // If something fails, throw our own exception
            throw new RSAException ( "Could not encrypt input file." );
        }
	}

	/**
	 * 
	 */
	private void initialize ( Key key ) {
		// Save variables internally
		this.n = key.get ( Key.Attribute.N );
		this.k = key.get ( Key.Attribute.K );
		// Initialize our output stream
		this.result = "";
	}

	/**
	 * 
	 */
	private String process ( String input ) {
		// Turn into Decimal instance
		Decimal original = new Decimal ( input );
		// Initialize the processed output
		Decimal processed = new Decimal ( "1" );
		// Loop through k times
		for ( int i = 0; Operation.lessThan ( new Decimal ( Integer.toString ( i ) ), k ); i++ ) {
			// Save to the processed result
			processed = Operation.modulo ( Operation.multiply ( processed, original ), n );
		}
		// Return the string version of the result
		return processed.stringify ();
	}

}