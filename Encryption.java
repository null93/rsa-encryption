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
	protected Encryption () {

	}

	public static void main ( String [] args ) {
		Decimal a = new Decimal ( "2" );
		Decimal b = new Decimal ( "7" );
		KeyGeneration keygen = new KeyGeneration ( a, b );
		Decimal e = new Decimal ( keygen.publicKey.get ( Key.Attribute.K ).stringify () );
		Decimal d = new Decimal ( keygen.privateKey.get ( Key.Attribute.K ).stringify () );
		Decimal n = new Decimal ( keygen.publicKey.get ( Key.Attribute.N ).stringify () );

		String timestamp = Key.timestamp ();
		keygen.publicKey.export ( timestamp );
		keygen.privateKey.export ( timestamp );

		Decimal original = new Decimal ( "0574820589747450" );
		Decimal encrypted = new Decimal ( "1" );
		for ( int i = 0; Operation.lessThan ( new Decimal ( Integer.toString ( i ) ), e ); i++ ) {
			encrypted = Operation.modulo ( Operation.multiply ( encrypted, original ), n );
		}
		original.print ();
		encrypted.print ();

		original = new Decimal ( "2" );
		Decimal decrypted = new Decimal ( "1" );
		for ( int i = 0; Operation.lessThan ( new Decimal ( Integer.toString ( i ) ), d ); i++ ) {
			decrypted = Operation.modulo ( Operation.multiply ( decrypted, original ), n );
		}

		original.print ();
		decrypted.print ();
	}

}