package com.rsa_encryption;

/**
 * KeyGeneration.java - This class is able to load already generated public and private keys that
 * are in XML format, or is also able to generate brand new ones using either user defined prime
 * number seeds or programmatically loaded ones with the help if the Prime.java class.  This class
 * does all the dirty math that is involved in generating a public and private key from two seed
 * prime numbers.  It is then saved internally and passed on to the calling instance class.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #03 - RSA Encryption / Decryption
 * @category    Key Generation
 * @author      Rafael Grigorian
 * @author      Henvy Patel
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class KeyGeneration {

	/**
	 * This data member stores a Key instance that is initialized to be a public key.
	 * @var     Key             publicKey           The public key based on input primes
	 */
	protected Key publicKey;

	/**
	 * This data member stores a Key instance that is initialized to be a private key.
	 * @var     Key             privateKey          The private key based on input primes
	 */
	protected Key privateKey;

	/**
	 * This data member is used within calculation in key creation and exists to make the
	 * calculations more readable, and since we never modify it within our calculations we make it
	 * static and final.
	 * @var     Decimal         one                 Decimal instance initialized to "1"
	 * @static
	 * @final
	 */
	protected static Decimal one = new Decimal ( "1" );

	/**
	 * This constructor takes in two prime numbers and generates a public and private key based on
	 * the RSA protocol.  Once the encryption and decryption keys are generated they are stored
	 * internally and the caller is expected to export them.
	 * @param   Decimal         p                   The first prime to work with
	 * @param   Decimal         q                   The second prime to work with
	 * @throws  Exception 							Catches generic exception
	 */
	protected KeyGeneration ( Decimal p, Decimal q ) {
		// Subtract one from both the primes
		Decimal pSubOne = Operation.subtract ( p, one );
		Decimal qSubOne = Operation.subtract ( q, one );
		// Calculate the multiple of both primes
		Decimal n = Operation.multiply ( p, q );
		// Calculate the number of co primes in the range of: 1 < x < n
		Decimal phi = Operation.multiply ( pSubOne, qSubOne );
		// Initialize the encryption prime at 2 initially
		Decimal e = new Decimal ( "2" );
		// Loop through adding one to e until the condition is met
		while ( Operation.lessThanEqual ( e, n ) && !Operation.equal ( one, gcd ( e, phi ) ) ) {
			// Increment encryption key by one
			e = Operation.add ( e, one );
		}
		// Initialize the decryption prime to be one initially and the traversal variable as well
		Decimal d = new Decimal ( "0" );
		Decimal traverse = new Decimal ( "0" );
		// Declare internal variables outside (trying to save space)
		Decimal nm;
		Decimal nmPlusOne;
		Decimal nmPlusOneModulusE;
		// Traverse through until we loop through the entire range of n
		while ( Operation.lessThanEqual ( traverse, n ) ) {
			// Calculate the d based on interval traverse
			nm = Operation.multiply ( traverse, phi );
			nmPlusOne = Operation.add ( nm, one );
			nmPlusOneModulusE = Operation.modulo ( nmPlusOne, e );
			// Check that the modulo is zero
			if ( Operation.equal ( nmPlusOneModulusE, Decimal.zero ) ) {
				// Calculate d
				d = Operation.divide ( nmPlusOne, e );
				// Check to see that d != e
				if ( !Operation.equal ( d, e ) ) {
					// If all conditions are met, then break and keep d value
					break;
				}
			}
			// Increment traversal Decimal instance
			traverse = Operation.add ( traverse, one );
		}
		// Create the public and private keys
		this.publicKey = new Key ( n, e, Key.Type.PUBLIC );
		this.privateKey = new Key ( n, d, Key.Type.PRIVATE );
	}

	/**
	 * This function calculates the greatest common divisor and returns it as a Decimal instance.
	 * This function also takes in two decimal instances to evaluate.  By default one is returned if
	 * no greater common divisor is found.
	 * @param   Decimal         a                   The first Decimal to evaluate
	 * @param   Decimal         b                   The second Decimal to evaluate
	 * @return  Decimal                             Greatest common divisor as Decimal instance
	 * @static
	 */
	protected static Decimal gcd ( Decimal a, Decimal b ) {
		// Base case, if either is zero
		if ( Operation.equal ( a, Decimal.zero ) || Operation.equal ( b, Decimal.zero ) ) {
			return Operation.add ( a, b );
		}
		// Otherwise recursively figure it out
		return gcd ( b, Operation.modulo ( a, b ) );
	}

}
