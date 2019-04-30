package com.rsa_encryption;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

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
	 * This data member holds n instance referenced from the key that was passed to the constructor.
	 * @var     Decimal         n                   The multiple of primes p and q
	 */
	private Decimal n;

	/**
	 * This data member holds an instance referenced from the key that was passed to the
	 * constructor.  This instance is either the encryption key of the decryption key.  We will
	 * refer to it as simply k.
	 * @var     Decimal         k                   The encryption or decryption key
	 */
	private Decimal k;

	/**
	 * This is a string that holds the processed result.  This sting is later written back into the
	 * input file.
	 */
	private String result;

	/**
	 * This is the constructor and it takes in an encryption key and a filepath.  This constructor
	 * is the driver function for the encryption and everything happens here.
	 * @param   Key             key                 Which key to use fro processing
	 * @param   String          filepath            The filepath to the input file
	 * @throw   RSAExeption                         We throw so calling class knew about all errors
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
				// Append to the result
				this.result += this.process ( line ) + "\n";
			}
			// Close the scanner ( And file internally )
			scanner.close ();
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
	 * This function initializes the internal data members with default values based on the key that
	 * is passed.
	 * @param   Key             key                 The key that is passed from the constructor
	 * @return  void
	 */
	private void initialize ( Key key ) {
		// Save variables internally
		this.n = key.get ( Key.Attribute.N );
		this.k = key.get ( Key.Attribute.K );
		// Initialize our output stream
		this.result = "";
	}

	/**
	 * This function preforms the actual encryption / decryption and returns a padded string
	 * representation of the result.
	 * @param   String          input               The input string to process
	 * @return  String                              The padded result string representation
	 */
	private String process ( String input ) {
		// Initialize the processed output
		Decimal processed = new Decimal ( input );
		// Initialize cleanup variable
		Decimal cleanup = new Decimal ( "1" );
		// Initialize exponent variable
		Decimal exponent = new Decimal ( k.stringify () );
		// Do an initial modulo
		processed = Operation.modulo ( processed, n );
		// Loop until the exponent
		while ( Operation.greaterThan ( exponent, Decimal.zero ) ) {
			// Check if the exponent is odd
			if ( !Operation.isEven ( exponent ) ) {
				// Accumulate to the cleanup
				cleanup = Operation.modulo ( Operation.multiply ( cleanup, processed ), n );
			}
			// Divide exponent by two
			exponent = Operation.divide ( exponent, new Decimal ( "2" ) );
			// Accumulate to the processed
			processed = Operation.modulo ( Operation.multiply ( processed, processed ), n );
		}
		// Return the string version of the result
		return cleanup.stringify ( Display.blocking * 2 );
	}

}
