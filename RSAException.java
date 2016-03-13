/**
 * RSAException.java - This class extends from the system native Exception class and is used as a
 * custom exception handler.  It is used the same way a regular exception would be used.  The main
 * reason for this class is not to stop the user from doing something wrong, since they won't have
 * access to this class, but to stop the programmer from executing certain operations.  This class
 * serves to warn the user of the illegible action so it may be corrected.  Some examples of when
 * this exception will be thrown are as follows.  If division by zero is attempted within the
 * Operator's divide function.  If a negative number is yielded within the subtraction function in
 * same said class.  Finally this exception can be thrown if a Decimal instance is initiated with
 * passing an invalid representation of a huge unsigned integer within the Decimal class'
 * constructor.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #03 - RSA Encryption / Decryption
 * @category    RSAException
 * @author      Rafael Grigorian
 * @author      Henvy Patel
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
@SuppressWarnings ( "serial" )
public class RSAException extends Exception {

	/**
	 * This static and immutable string is used to set the text in the terminal to it's default
	 * settings.  It is used when printing exceptions to standard output.
	 * @var     String          RESET               Escaped style for resetting text font
	 * @static
	 * @final
	 */
	protected static final String RESET = "\033[49;39m";

	/**
	 * This static and immutable string is used to set the text in the terminal to a red foreground
	 * setting.  It is used when printing exceptions to standard output.
	 * @var     String          ERROR               Escaped style for setting red text foreground
	 * @static
	 * @final
	 */
	protected static final String ERROR = "\033[31;49m";

	/**
	 * This static immutable string is printed every time an exception is thrown.  It serves as the
	 * header to each message.  It also implements the color styling that is initialized above.
	 * @var     String          header              The header string pre-appended to each exception
	 * @static
	 * @final
	 */
	protected static final String header = ERROR + "RSAException: " + RESET;

	/**
	 * This constructor does not require any message string to be passed as a parameter.  It simply
	 * alerts user that the exception was thrown and that an unknown exception was encountered.
	 */
	protected RSAException () {
		// Call the super constructor first
		super ();
		// Alert user that an unknown exception was thrown
		System.out.println ( header + "Unknown exception was thrown!" );
	}

	/**
	 * This constructor requires the user to input the exception message to be displayed as a
	 * parameter.  It then calls the super class constructor and prints out the formatted thrown
	 * exception message to the user.
	 * @param   String          message             The message to display when exception is thrown
	 */
	protected RSAException ( String message ) {
		// Call the super constructor first
		super ( message );
		// Print out the exception's message
		System.out.println ( header + message );
	}

}