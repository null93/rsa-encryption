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
	 * This function is the driver function that spawns the GUI object that sets off everything
	 * else.
	 * @param 	String [] 		args 				The arguments passed to the function
	 * @return  void
	 * @static
	 */
	public static void main ( String [] args ) {
		// Spawn GUI object
		new Display ();
	}

}