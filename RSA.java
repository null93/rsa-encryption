/**
 * RSA.java - This class is the first one that is launched and contains the static main method that
 * initializes the GUI.java class and displays a user interface to the user.  This class is simply
 * to kick start everything and in itself is useless and boring.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #03 - RSA Encryption / Decryption
 * @category    RSA Driver Class
 * @author      Rafael Grigorian
 * @author      Henvy Patel
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class RSA {

	/**
	 *
	 */
	public static void main ( String [] args ) {

		new GUI ();

		Decimal a = new Decimal ( "32416187567" );
		Decimal b = new Decimal ( "32416190071" );


		Key key = new Key ( "./output/2016-37-14-06-37-20/public.xml" );
		//key.get ( Key.Attribute.K ).print ();

		// KeyGeneration keygen = new KeyGeneration ( a, b );
		// String timestamp = Key.timestamp ();
		//System.out.println ( keygen.publicKey.export ( timestamp ) );
		//System.out.println ( keygen.privateKey.export ( timestamp ) );

	}

}