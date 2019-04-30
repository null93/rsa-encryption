package com.rsa_encryption;

import java.util.Arrays;

/**
 * Decimal.java - This class contains a data structure for holding information about big unsigned
 * integers.  The digits within the big unsigned integer are stored as characters to conserver
 * memory on disk as well as ease of use when trying to operate on them within the Operation.java
 * class.  This class contains some static functions to aid the user in converting from one format
 * representation of a digit to another.  It also contains a length data member for convenience.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #03 - RSA Encryption / Decryption
 * @category    Decimal
 * @author      Rafael Grigorian
 * @author      Henvy Patel
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Decimal {

	/**
	 * This data member stores the huge unsigned integer as an array of characters.  They are
	 * characters instead of integers in order to conserve memory, since there is no upper bound to
	 * how large these digits can be.
	 * @var     char []         digits              Char array representation of huge unsigned int
	 */
	protected char [] digits;

	/**
	 * This data member describes the number of decimal digit positions that this huge unsigned
	 * integer has.
	 * @var     int             length              The number of digits in integer
	 */
	protected int length;

	/**
	 * This data member consists of a Decimal instance that is immutable and is initialized to zero.
	 * It is used, and only should be used for the comparison operators in the Operator.java class.
	 * @var     Decimal         zero                Decimal instance initialized to zero
	 * @static
	 * @final
	 */
	protected static final Decimal zero = new Decimal ( "0" );

	/**
	 * This constructor takes in a string representation of the target huge unsigned integer.  It
	 * also checks for usage errors and also initializes the character array to hold our huge
	 * unsigned integer.
	 * @param   String          integer             The target huge unsigned integer as a string
	 * @throw   RSAException                        May throw exception if usage errors occur
	 */
	protected Decimal ( String integer ) {
		// Attempt to see if passed integer is compatible
		try {
			// Check to see if the integer is compatible
			if ( Decimal.isHugeUnsignedInt ( integer ) ) {
				// If it is compatible then parse it
				this.parse ( integer );
			}
			// If it isn't comparable, then through an exception
			else {
				// Throw error stating that passed integer is incompatible
				throw new RSAException ( "Non-Natural integer was passed to constructor." );
			}
		}
		// We don't wanna catch this exception, just quit
		catch ( RSAException exception ) {
			// Exit the program
			System.exit ( 0 );
		}
	}

	/**
	 * This function attempts to parse the huge unsigned integer and store it into a character array
	 * while still checking for usage errors.
	 * @param   String          integer             The target huge unsigned integer as a string
	 * @return  void
	 */
	private void parse ( String integer ) {
		// Check the whole string and see if its just zeros
		int padding = 0;
		boolean flag = true;
		// Loop through string
		for ( int i = 0; i < integer.length (); i++ ) {
			// Check if current char is zero
			if ( integer.charAt ( i ) == '0' && flag ) {
				// Increment padding
				padding++;
			}
			else {
				// Change flag
				flag = false;
			}
		}
		// Check to see if only zero was passed
		if ( flag ) {
			// Set the length
			this.length = 1;
			// Set the digit
			this.digits = new char [ 1 ];
			this.digits [ 0 ] = '0';
			// Return, for this special case
			return;
		}
		// Remove trailing zeros
		integer = integer.substring ( padding );
		// Convert and save the string to a char array
		this.digits = integer.toCharArray ();
		// Get the length of the integer
		int length = this.digits.length;
		// Reverse string for preferred index access
		for ( int i = 0; i < length / 2; i++ ) {
			// Swap current character with mirrored one
			this.digits [ i ] ^= this.digits [ length - i - 1 ];
			this.digits [ length - i - 1 ] ^= this.digits [ i ];
			this.digits [ i ] ^= this.digits [ length - i - 1 ];
		}
		// Set the length of the integer
		this.length = integer.length ();
	}

	/**
	 * This function returns the integer representation of the string at a given digit index.  It
	 * takes in an index that is used to get the character from the current integer's digit array.
	 * If the index is out of bounds, then zero is returned.
	 * @param   int             index               Index of the string
	 * @return  int                                 The cased integer representation at that index
	 */
	protected int get ( int index ) {
		// Check to see that the index is within the bounds of the character array
		if ( this.digits.length > index && index >= 0 ) {
			// If it is then cast it as an integer and return
			return Decimal.parseInt ( this.digits [ index ] );
		}
		// Otherwise, the index is out of bounds
		else {
			// Return zero by default
			return 0;
		}
	}

	/**
	 * This function returns a stringify version of the huge unsigned integer.  The return type is
	 * of type string.
	 * @return  String                              The stringified version of huge integer
	 */
	protected String stringify () {
		// Create a new char array
		char [] number = Arrays.copyOf ( this.digits, this.length );
		// Save the length
		int length = number.length;
		// Reverse the char array
		for ( int i = 0; i < length / 2; i++ ) {
			// Swap current character with mirrored one
			number [ i ] ^= number [ length - i - 1 ];
			number [ length - i - 1 ] ^= number [ i ];
			number [ i ] ^= number [ length - i - 1 ];
		}
		// Create a new string and return it
		return new String ( number );
	}

	/**
	 * This is the overloaded function for the stringify method.  In addition to the original method
	 * it also makes sure the returned string is padded by a specified amount of zeros in the front.
	 * @param   int             padding             The amount of padding required
	 * @return  String                              Padded sting that represents decimal
	 */
	protected String stringify ( int padding ) {
		// Get the result
		String result = this.stringify ();
		// Loop through until desired size is met
		while ( result.length () < padding ) {
			// Append a zero to the result
			result = "0" + result;
		}
		// Return the resulting string
		return result;
	}

	/**
	 * This function prints out the huge unsigned integer to standard output.  This function is used
	 * primarily for debugging and thus protected.
	 * @return  void
	 */
	protected void print () {
		// Print out the huge unsigned integer
		System.out.println ( this.stringify () );
	}

	/**
	 * This function determines if a passed string is a positive integer.  This function is used to
	 * determine if we can parse and use a user inputed integer by checking that it is made up of
	 * all numerical digits and positive.
	 * @param   String          integer             The targeted integer in string form to evaluate
	 * @return  boolean                             If string representation is a positive integer
	 * @static                                      Can be used uninitialized with the Decimal class
	 */
	protected static boolean isHugeUnsignedInt ( String integer ) {
		// Get the number of digits in integer
		int length = integer.length ();
		// Loop through the whole integer character by character
		for ( int i = 0; i < length; i++ ) {
			// Check if the character is out of bounds
			if ( integer.charAt ( i ) < 48 || integer.charAt ( i ) > 57 ) {
				// If it is then return false
				return false;
			}
		}
		// Otherwise, return true
		return true;
	}

	/**
	 * This static function is used to convert a character into an integer.  It is used for
	 * primitive calculations within the operator class.
	 * @param   char            c                   The character to convert into an integer
	 * @return  int                                 The converted character, now in integer form
	 * @static
	 */
	protected static int parseInt ( char c ) {
		// Cast character to corresponding integer value
		return Character.getNumericValue ( c );
	}

	/**
	 * This static function is used to convert a given integer into a character.  It is used to
	 * convert integers back into characters in primitive calculations in the Operator.java class.
	 * @param   int             i                   The integer to convert into a char
	 * @return  char                                The char that was converted from passed int
	 * @static
	 */
	protected static char parseChar ( int i ) {
		// Cast to string, then get the first char at string position zero
		return Integer.toString ( i ).charAt ( 0 );
	}

}
