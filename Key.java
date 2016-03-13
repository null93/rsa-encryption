/**
 * 
 */
public class Key {

	/**
	 * 
	 */
	private Decimal publicKey;

	/**
	 * 
	 */
	private Decimal privateKey;

	/**
	 * 
	 */
	protected static enum Type {
		PUBLIC,
		PRIVATE;
	}

	/**
	 * 
	 */
	protected Key ( Decimal pub, Decimal pri ) {
		// Save the public and private keys internally
		this.publicKey = pub;
		this.privateKey = pri;
	}

	/**
	 * Allows our keys to be immutable, but visible throughout out package.
	 */
	protected Decimal get ( Key.Type type ) {
		// Check to see if the key type is public
		if ( type == Key.Type.PUBLIC ) {
			// Return the public key
			return this.publicKey;
		}
		// Check to see if the key type is private
		else if ( type == Key.Type.PRIVATE ) {
			// Return the private key
			return this.privateKey;
		}
		// By default we return null
		return null;
	}

	/**
	 * 
	 */
	protected String export () {
		return "filepath to the key folder";
	}

	private static String timestamp () {
		return "YYYY-MM-DD-HH-MM-SS";
	}

}