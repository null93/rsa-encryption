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

		new Display ();

		Decimal a = new Decimal ( "282755483533707287054752184321121345766861480697448703443857012153264407439766013042402571" );
		Decimal b = new Decimal ( "370332600450952648802345609908335058273399487356359263038584017827194636172568988257769601" );
		//KeyGeneration keygen = new KeyGeneration ( a, b );
		

	}

}