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
		// Decimal a = new Decimal ( "32416189493" );
		// Decimal b = new Decimal ( "32416187567" );
		Decimal a = new Decimal ( "2" );
		Decimal b = new Decimal ( "7" );
		Operation.multiply ( a, b ).print ();
		Operation.multiply ( a, new Decimal ( "14" ) ).print ();

		//KeyGen kg = new KeyGen ( a, b );
		

	}

}