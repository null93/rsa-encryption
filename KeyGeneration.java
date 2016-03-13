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

	protected Decimal publicKey;

	protected Decimal privateKey;

	private Decimal p;

	private Decimal q;

	protected Decimal n;

	private Decimal phi;

	private static Decimal min = new Decimal ( "1" );

	private static Decimal one = new Decimal ( "1" );

	protected KeyGeneration ( Decimal p, Decimal q ) {
		// Save both the prime numbers internally
		this.p = p;
		this.q = q;
		// Subtract one from both the primes
		Decimal pSubOne = Operation.subtract ( this.p, one );
		Decimal qSubOne = Operation.subtract ( this.q, one );
		// Calculate the multiple of both primes
		this.n = Operation.multiply ( p, q );
		// Calculate the number of co primes in the range of: 1 < x < n
		this.phi = Operation.multiply ( pSubOne, qSubOne );


		Decimal e = new Decimal ( "2" );
		while ( Operation.lessThanEqual ( e, n ) && !Operation.equal ( one, gcd ( e, phi ) ) ) {
			e = Operation.add ( e, one );
		}
		e.print ();

		Decimal d = new Decimal ( "1" );
		while ( !Operation.equal ( one, Operation.modulo ( Operation.multiply ( e, d ), phi ) ) || Operation.equal ( e, d ) ) {
			d.print ();
			d = Operation.add ( d, one );
		}
		d.print ();


	}

	/**
	 * 
	 */
	private Decimal gcd ( Decimal a, Decimal b ) {
		// Base case, if either is zero
		if ( Operation.equal ( a, Decimal.zero ) || Operation.equal ( b, Decimal.zero ) ) {
			return Operation.add ( a, b );
		}
		// Otherwise recursively figure it out
		return gcd ( b, Operation.modulo ( a, b ) );
	}

}