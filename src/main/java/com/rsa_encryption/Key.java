package com.rsa_encryption;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Key.java - This class is a data structure that pairs two decimals together.  These decimals
 * represent either the private or public key and is paired with the product of both, n.  It is a
 * primitive class, but is used to more clearly and objectively represent a set of Decimal instances
 * and their intended usage.  It contains an export function that exports the key and the type to a
 * XML file.  It also contains an additional constructor for loading an XML file.  There is an
 * additional enum subclass to make things more modular.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #03 - RSA Encryption / Decryption
 * @category    Key Generation
 * @author      Rafael Grigorian
 * @author      Henvy Patel
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Key {

	/**
	 * This internal data member represents the product between two prime numbers p and q.  This was
	 * done in the KeyGeneration.java class.
	 * @var     Decimal         n                   The product of primes p and q
	 */
	private Decimal n = null;

	/**
	 * This internal data member represents either the encryption prime or the decryption prime.  It
	 * is labeled as k inside this class in order to abstract the two.  An additional enum class is
	 * used to actually differentiate them based on type.
	 * @var     Decimal         k                   The encryption key or decryption key abstracted
	 */
	private Decimal k = null;

	/**
	 * This internal data member represents the type of key.  This is represented by an internally
	 * defined static enum that is either public or private.
	 * @var     Key.Type        type                The type of key that this.k represents
	 */
	private Type type;

	/**
	 * This is the filepath to the XML key file, it's value is changed when the export function is
	 * called.  By default it is set to null.
	 * @var     String          filepath            The filepath to the XML key file
	 */
	private String filepath = null;

	/**
	 * This string is immutable and it represents the directory where all key files are contained.
	 * @var     String          folder              Folder path that contains all generated keys
	 * @static
	 * @final
	 */
	protected static final String folder = "./output";

	/**
	 * This enum class is used to enumerate the types of Decimals that are stored internally.  It is
	 * used in the Key::get function and is used to figure out which Decimal instance to return.
	 * @enum    Key.Attribute                       Decimal variable, used for getter function
	 */
	protected static enum Attribute {
		K, E, D, N;
	}

	/**
	 * This enum subclass is used to differentiate what kind of key this instance is.  Whether it is
	 * public or private.  It also contains some helper functions that are used to stringify some of
	 * the attributes that are contained within the key type.
	 * @enum    Key.Type                            The type of key this instance is
	 * @static
	 */
	protected static enum Type {
		PUBLIC,
		PRIVATE;

		/**
		 * This function returns the string representation of the type.  It is used to name the XML
		 * key file in Key::export.
		 * @param   Key.Type    type                The enum type to evaluate
		 * @return  String                          The string representation of said enum
		 * @static
		 */
		protected static String toString ( Key.Type type ) {
			// Switch on type
			switch ( type ) {
				// If it is a public key
				case PUBLIC:
					return "public";
				// If it is a private key
				case PRIVATE:
					return "private";
				// by default we return null
				default:
					return null;
			}
		}

		/**
		 * This function is used to bind the node name for the XML file for the decrypt key or the
		 * encrypt key with the enumerated type that is passed.
		 * @param   Key.Type    type                The enum type to evaluate
		 * @return  String                          The string to save the XML node to
		 * @static
		 */
		protected static String toNode ( Key.Type type ) {
			// Switch on the enum type
			switch ( type ) {
				// If it is a public key
				case PUBLIC:
					return "evalue";
				// If it is a private key
				case PRIVATE:
					return "dvalue";
				// By default return null
				default:
					return null;
			}
		}

	}

	/**
	 * This constructor makes sure the output folder is created and then saves both the type and the
	 * n and ( e or d as k ).
	 * @param   Decimal         n                   The product of p and q
	 * @param   Decimal         k                   Either e or d, depending on the type of Key
	 * @param   Key.Type        type                The type of key saved, as enum Type
	 */
	protected Key ( Decimal n, Decimal k, Key.Type type ) {
		// Make sure the output folder exists
		checkFile ( Key.folder, "./" );
		// Save the key type internally
		this.type = type;
		// Save the public and private keys internally
		this.n = n;
		this.k = k;
	}

	/**
	 * This function loads the key from a XML file that's filepath is passed as a parameter.  If the
	 * file is corrupt or not in the correct format, then the function throws an RSAException.
	 * Otherwise the data is saved internally.
	 * @param   String          filepath            Filepath to the input Key file
	 * @throws  RSAException                        If required data is not read by the end
	 */
	protected Key ( String filepath ) throws RSAException {
		// Make sure the output folder exists
		checkFile ( Key.folder, "." );
		// Try to parse the input XML file
		try {
			// Create a file instance from filepath
			File xmlFile = new File( filepath );
			// Create a document factory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
			// Create a document builder instance
			DocumentBuilder builder = factory.newDocumentBuilder ();
			// Create a document from the file instance
			Document document = builder.parse ( xmlFile );
			// Save the root node of the XML file
			Node root = document.getFirstChild ();
			// Create a list of children nodes from the root node
			NodeList nList = root.getChildNodes ();
			// Declare the tag and text Node variables
			Node tag;
			Node text;
			// Traverse through the child nodes
			for ( int i = 0; i < nList.getLength (); i++ ) {
				// Save the current node
				tag = nList.item ( i );
				// If the node is of type element node
				if ( tag.getNodeType () == Node.ELEMENT_NODE ) {
					// Create another node list from the nodes children ( for text node )
					NodeList nListTwo = tag.getChildNodes ();
					// Save that node ( we know there is only one )
					text = nListTwo.item ( 0 );
					// If the node tag is for the n value
					if ( tag.getNodeName().equals ( "nvalue" ) ) {
						// Save it internally
						this.n = new Decimal ( text.getNodeValue () );
					}
					// If the node tag is for the e value
					else if ( tag.getNodeName().equals ( "evalue" ) ) {
						// Save it internally along with the key type
						this.k = new Decimal ( text.getNodeValue () );
						this.type = Key.Type.PUBLIC;
					}
					// If the node tag is for the d value
					else if ( tag.getNodeName().equals ( "dvalue" ) ) {
						// Save it internally along with the key type
						this.k = new Decimal ( text.getNodeValue () );
						this.type = Key.Type.PRIVATE;
					}
				}
			}
			// Check to see if the proper attributes were set
			if ( this.n == null || this.k == null ) {
				// If n value or k value is not saved and is still null, then throw exception
				throw new RSAException ( "Could not parse key file '" + filepath + "'' properly." );
			}
		}
		// Try to catch the thrown exception
		catch ( Exception exception ) {
			// On error we want to do nothing since we want to catch the RSA error outside the scope
			throw new RSAException ( "Could not parse key file." );
		}
	}

	/**
	 * This function allows our keys to be immutable, but visible throughout out package.  It is a
	 * simple getter function and it used the Key.Attribute class to figure out which Decimal
	 * instance to return.
	 * @param   Key.Attribute   attribute           The enum target attribute
	 * @return  Decimal                             The decimal instance binded to the attribute
	 */
	protected Decimal get ( Key.Attribute attribute ) {
		if ( attribute == Key.Attribute.K ) {
			return this.k;
		}
		// Switch through the inputted enum
		switch ( attribute ) {
			// If the key is the encryption or decryption key
			case E:
			case D:
			case K:
				return this.k;
			// If it is the product of both e and d
			case N:
				return this.n;
			// Otherwise, by default return null
			default:
				return null;
		}
	}

	/**
	 * This function is a simple getter function that returns the key type of the this Key instance.
	 * @return  Key.Type                            The type of key represented as a Type enum
	 */
	protected Key.Type type () {
		// Return the key type
		return this.type;
	}

	/**
	 * This function is a simple getter function that returns the filepath to the key.
	 * @return  String                              The path to the key as a string
	 */
	protected String filepath () {
		// Return the filepath that is stored internally
		return this.filepath;
	}

	/**
	 * This function takes in a folder name and a root directory and checks if that folder
	 * ( or file ) is created.  If it is it returns true, but otherwise it creates the folder and
	 * returns true.  It only returns false if the file could not be created.
	 * @param   String          folderName          The folder, or file, we want to create
	 * @param   String          rootFolder          The directory where to look for the file
	 * @return  Boolean                             If folder creation or discovery was successful
	 * @static
	 */
	protected static boolean checkFile ( String folderName, String rootFolder ) {
		// Initialize the target folder name, accounting for operating system
		File directory = new File ( new File ( rootFolder ), folderName );
		// Check to see if the directory exists
		if ( directory.exists () ) {
			// If it exists, then return true
			return true;
		}
		// Otherwise try to create the new folder
		try {
			// Attempt to create the target directory
			directory.mkdir ();
			// Return true if it succeeds
			return true;
		}
		catch ( Exception exception ) {
			// If there are errors, then return false
			return false;
		}
	}

	/**
	 * This function exports the internally saved key into an XML file that is represented in the
	 * file structure with the passed timestamp and the internally defined constance folder
	 * constant.
	 * @param   String          timestamp           The timestamp that is used for folder creation
	 * @return  String                              The filepath to the exported key file
	 */
	protected String export ( String timestamp ) {
		// Try to create the public and private keys
		try {
			// Create a new document factory and document builder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
			DocumentBuilder builder = factory.newDocumentBuilder ();
			// Create a new document using th builder
			Document document = builder.newDocument ();
			// Create the root node
			Element root = document.createElement ( "rsakey" );
			document.appendChild ( root );
			// Create the N value and append to the root
			Element nVal = document.createElement ( "nvalue" );
			nVal.appendChild (
				document.createTextNode ( this.n.stringify () )
			);
			root.appendChild ( nVal );
			// Create the encrypt or decrypt value node
			Element kVal = document.createElement ( Key.Type.toNode ( this.type ) );
			kVal.appendChild (
				document.createTextNode ( this.k.stringify () )
			);
			root.appendChild ( kVal );
			// Create a new transformer factory
			TransformerFactory transformerFactory = TransformerFactory.newInstance ();
			// Create a new instance of a transformer
			Transformer transformer = transformerFactory.newTransformer ();
			// Set the properties to indent at 4 spaces per tab
			transformer.setOutputProperty ( OutputKeys.INDENT, "yes" );
			transformer.setOutputProperty ( "{http://xml.apache.org/xslt}indent-amount", "4" );
			// Load the DOM source that we built for the public key
			DOMSource source = new DOMSource ( document );
			// Create a new base directory to output files
			String base = Key.folder + File.separator + timestamp + File.separator;
			base += Key.Type.toString ( this.type ) + ".xml";
			// Create a new folder inside output folder
			checkFile ( timestamp, Key.folder );
			// Create a new file for the public key
			StreamResult result = new StreamResult ( new File ( base ) );
			// Transform the source into the result (public)
			transformer.transform ( source, result );
			// Save the filepath
			this.filepath = base;
			// Return the base directory to the keys file
			return this.filepath;
		}
		// Catch the error if possible
		catch ( Exception exception ) {
			// Return null by default
			return null;
		}
	}

	/**
	 * This function returns the current timestamp in a certain format that is able to be sorted in
	 * an ascending manner and still be able to represent a timestamp that is readable to the user.
	 * This string will ultimately create a folder that our keys will be exported to.
	 * @return  String                              The timestamp as a string
	 * @static
	 */
	protected static String timestamp () {
		// Initialize a date format using a string
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-mm-dd-kk-mm-ss");
		// Format current time / date into specified string
		return dateFormat.format ( new Date () );
	}

}
