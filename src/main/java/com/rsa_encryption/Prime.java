package com.rsa_encryption;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;

/**
 * Prime.java - This class will consist of static and non-static functions.  Some examples of static
 * functions within this class would be a function that determines if a passed Decimal instance is
 * prime or not.  This is used for user input authentication.  Other functions that are not static
 * within this class are able to load and return random prime numbers that will have to be preloaded
 * using a data file containing a list of primes.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #03 - RSA Encryption / Decryption
 * @category    Prime Numbers
 * @author      Rafael Grigorian
 * @author      Henvy Patel
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Prime {

	/**
	 * This internal data member contains the file that is relative to the current directory, and it
	 * specifies where the prime numbers recourse file is located.  This is used to select random
	 * prime numbers.
	 * @var 	String 			filepath 			The filepath to prime numbers resource file
	 * @static
	 * @final
	 */
	private static final String filepath = "primes.rsc";

	/**
	 * This Array list is a dynamic array that grows and populates as we read in all predefined
	 * primes based on the resource file contents.
	 * @var 	ArrayList <Decimal> 	primes 		A dynamic array of primes as Decimal instance
	 */
	private ArrayList <Decimal> primes;

	/**
	 * This seed is a running seed and is reseeded every time the generate function runs.  By
	 * default it is initialized to the current system time in milliseconds.
	 * @var 	long 			seed 				Our seed for the Random instance
	 */
	private long seed = System.currentTimeMillis ();

	/**
	 * This constructor initializes the Decimal primes dynamic array and also reads in all primes
	 * from the resource file and loads it into the dynamic array.
	 */
	protected Prime () {
	// Initialize dynamic array of Decimal instances
	this.primes = new ArrayList <Decimal> ();
	// Try to open the file and read in primes
	try {
	// Get class loader and open resource file with list of primes
	ClassLoader classLoader = getClass ().getClassLoader ();
	InputStream inputStream = classLoader.getResourceAsStream (
	Prime.filepath
	);
	// Initialize scanner instance based on input stream
	Scanner scanner = new Scanner ( inputStream );
	// Loop through until we have no more input
	while ( scanner.hasNextLine () ) {
	// Add this Decimal to the primes array
	this.primes.add ( new Decimal ( scanner.nextLine () ) );
	}
	}
	// If we throw an exception, then catch it
	catch ( Exception exception ) {
	// Exit the program, since we don't want to encounter such an error
	System.exit ( 0 );
	}
	}

	/**
	 * This function returns a Decimal representation of a huge unsigned prime integer.  This number
	 * is derived from a resource file that is specified in the internal variables.
	 * @return 	Decimal 							A random prime number from resource file
	 */
	protected Decimal random () {
	// Seed our random number generator
	this.seed *= System.currentTimeMillis ();
	// Apply modulus to our counting seed
	this.seed %= System.currentTimeMillis ();
	// Create a random number generator
	Random generator = new Random ( this.seed );
	// Return a random index from the primes array list
	return this.primes.get ( generator.nextInt ( this.primes.size () ) );
	}

}
