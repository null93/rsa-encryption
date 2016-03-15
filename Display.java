import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;

/**
 * Display.java - This class handles displaying the initial options menu to the user as well as walking
 * the user down any derivative branch in the decision tree.  It will handle getting all data from
 * the user that is needed for any given task in the options menu.  These options range from
 * blocking and unblocking a file.  Generating public and private keys.  Finally encrypting and
 * decrypting a file.  All user interface and data gathering is handled and/or defined within this
 * class.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #03 - RSA Encryption / Decryption
 * @category    Graphical User Interface
 * @author      Rafael Grigorian
 * @author      Henvy Patel
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Display implements ActionListener {

	/**
	 * 
	 */
	private Window frame;

	/**
	 * 
	 */
	private JPanel panel;

	/**
	 * 
	 */
	private JButton [] buttons;

	/**
	 * 
	 */
	private JTextArea [] textAreas;

	/**
	 * 
	 */
	private Key key;

	/**
	 * the filepath of the file to operate on
	 */
	private String target;

	/**
	 * 
	 */
	private enum Button {
		LOAD,
		CREATE,
		OPEN,
		BLOCK,
		UNBLOCK,
		ENCRYPT,
		DECRYPT;
	}

	/**
	 * 
	 */
	protected enum TextArea {
		KEYSTATUS,
		FILEPATH,
		MESSAGE;
	}

	/**
	 * 
	 */
	public Display () {

		// FOR DEBUGGING
		try {
			File file = new File("./example.txt");
			file.getParentFile().mkdirs();
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.print ("hello there");
			printWriter.flush ();
			printWriter.close ();
		}
		catch ( Exception e ) {
			System.out.println ("FAILED TO INIT");
		}

		// Initialize the buttons array
		this.buttons = new JButton [ 7 ];
		// Initialize the text area array
		this.textAreas = new JTextArea [ 3 ];
		// Initialize frame as a Window instance
		this.frame = new Window ( "RSA Encryption / Decryption", 400, 400 );
		// Add a new panel as a title for the key options
		JPanel row1 = frame.addPanel ( 1, 1 );
		row1.add (
			frame.bold ( new JLabel ( "RSA Encryption Key Options:" ) ),
			frame.options ( 0, 0, 1.0, 1.0, GridBagConstraints.WEST )
		);
		// Add a new frame to display all the options for the key creation and loading
		JPanel row2 = frame.addPanel ( 3, 1 );
		row2.add (
			createTextArea ( "Key Status & Type", 1, 18, 12, TextArea.KEYSTATUS ),
			frame.options ( 0, 0, 2.0, 1.0, GridBagConstraints.WEST )
		);
		row2.add (
			createButton ( "Load", 75, 40, Button.LOAD ),
			frame.options ( 1, 0, 1.0, 1.0, GridBagConstraints.EAST )
		);
		row2.add (
			createButton ( "Create", 75, 40, Button.CREATE ),
			frame.options ( 2, 0, 1.0, 1.0, GridBagConstraints.EAST )
		);
		// Add a new panel as a title for file selection
		JPanel row3 = frame.addPanel ( 1, 1 );
		row3.add (
			frame.bold ( new JLabel ( "Select Target File:" ) ),
			frame.options ( 0, 0, 1.0, 1.0, GridBagConstraints.WEST )
		);
		// Add a new frame to display all the options for the file selection
		JPanel row4 = frame.addPanel ( 2, 1 );
		row4.add (
			createTextArea ( "Filepath...", 1, 25, 12, TextArea.FILEPATH ),
			frame.options ( 0, 0, 5.0, 1.0, GridBagConstraints.WEST )
		);
		row4.add (
			createButton ( "Open", 75, 40, Button.OPEN ),
			frame.options ( 1, 0, 1.0, 1.0, GridBagConstraints.WEST )
		);
		// Add a new panel as a title for blocking and encryption
		JPanel row5 = frame.addPanel ( 3, 1 );
		row5.add (
			frame.bold ( new JLabel ( "Preform Blocking" ) ),
			frame.options ( 0, 0, 1.0, 1.0, GridBagConstraints.WEST )
		);
		row5.add (
			new JLabel ( "" ),
			frame.options ( 1, 0, 3.0, 1.0, GridBagConstraints.CENTER )
		);
		row5.add (
			frame.bold ( new JLabel ( "Preform Encryption" ) ),
			frame.options ( 2, 0, 1.0, 1.0, GridBagConstraints.WEST )
		);
		// Add a new panel to display all the buttons for blocking and encryption
		JPanel row6 = frame.addPanel ( 5, 1 );
		row6.add (
			createButton ( "Block", 75, 40, Button.BLOCK ),
			frame.options ( 0, 0, 1.0, 1.0, GridBagConstraints.WEST )
		);
		row6.add (
			createButton ( "Unblock", 75, 40, Button.UNBLOCK ),
			frame.options ( 1, 0, 1.0, 1.0, GridBagConstraints.WEST )
		);
		row6.add (
			new JLabel (""),
			frame.options ( 2, 0, 5.0, 1.0, GridBagConstraints.EAST )
		);
		row6.add (
			createButton ( "Encrypt", 75, 40, Button.ENCRYPT ),
			frame.options ( 3, 0, 1.0, 1.0, GridBagConstraints.EAST )
		);
		row6.add (
			createButton ( "Decrypt", 75, 40, Button.DECRYPT ),
			frame.options ( 4, 0, 1.0, 1.0, GridBagConstraints.EAST )
		);
		// Lastly add a message section for message displaying
		JPanel row7 = frame.addPanel ( 1, 1 );
		row7.add (
			createTextArea ( "Program initialized successfully", 5, 42, 10, TextArea.MESSAGE ),
			frame.options ( 0, 0, 1.0, 1.0, GridBagConstraints.WEST )
		);
		// Pack the frame so the dimensions showed
		frame.pack ();
		// Display the framed window
		frame.display ();
	}

	/**
	 * 
	 */
	protected void setButton ( JButton button, Button type ) {
		// Get the index of the enum
		int index = type.ordinal ();
		// Save button into that index
		this.buttons [ index ] = button;
	}

	/**
	 * 
	 */
	protected void setTextArea ( JTextArea textArea, TextArea type ) {
		// Get the index of the enum
		int index = type.ordinal ();
		// Save text area into that index
		this.textAreas [ index ] = textArea;
	}

	/**
	 * 
	 */
	private JButton getButton ( Button type ) {
		// Get the index of the enum
		int index = type.ordinal ();
		// Return that index
		return this.buttons [ index ];
	}

	/**
	 * 
	 */
	private JTextArea getTextArea ( TextArea type ) {
		// Get the index of the enum
		int index = type.ordinal ();
		// Return that index
		return this.textAreas [ index ];
	}

	/**
	 * 
	 */
	private JButton createButton ( String name, int width, int height, Button type ) {
		// Create a new button and set the name
		JButton button = new JButton ( name );
		// Set the preferred size for the button
		button.setPreferredSize ( new Dimension ( width, height ) );
		// Set the options for the button
		button.setFocusPainted ( false );
		button.setForeground ( Color.BLACK );
		button.setEnabled ( false );
		// Attach the event listener to be this class
		button.addActionListener ( this );
		// Check the type of button
		if ( type == Button.CREATE || type == Button.LOAD ) {
			// Enable the button
			button.setEnabled ( true );
		}
		// Save the button internally
		this.setButton ( button, type );
		// Return the button
		return button;
	}

	/**
	 * 
	 */
	private JTextArea createTextArea ( String value, int rows, int cols, int fontsize, TextArea type ) {
		// Initialize a new text area
		JTextArea text = new JTextArea ( rows, cols );
		// Set options to said text area
		text.setEditable ( false );
		text.setForeground ( Color.GRAY );
		text.setLineWrap ( true );
		// Set the initial value
		text.setText ( value );
		// Create a white border, giving the text area padding
		text.setBorder ( BorderFactory.createCompoundBorder (
			text.getBorder (), 
			BorderFactory.createEmptyBorder ( 10, 10, 10, 10 ) )
		);
		// Set the font size
		Font font1 = new Font ( "Helvetica", Font.PLAIN, fontsize );	
		text.setFont ( font1 );
		setTextArea ( text, type );
		// Return the resulting text area
		return text;
	}

	/**
	 * 
	 */
	public void actionPerformed ( ActionEvent e ) {
		// Check to see if we clicked the load button
		if ( e.getSource () == getButton ( Button.LOAD )  ) {
			// If so, then call the appropriate action handler
			actionLoad ();
		}
		// Check to see if we clicked the create button
		else if ( e.getSource () == getButton ( Button.CREATE ) ) {
			// If so, then call the appropriate action handler
			actionCreate ();
		}
		// Check to see if we clicked the open button
		else if ( e.getSource () == getButton ( Button.OPEN ) ) {
			// If so, then call the appropriate action handler
			actionOpen ();
		}
		// Check to see if we clicked the block button
		else if ( e.getSource () == getButton ( Button.BLOCK ) ) {
			// If so, then call the appropriate action handler
			actionBlock ();
		}
		// Check to see if we clicked the unblock button
		else if ( e.getSource () == getButton ( Button.UNBLOCK ) ) {
			// If so, then call the appropriate action handler
			actionUnblock ();
		}
		// Check to see if we clicked the encrypt button
		else if ( e.getSource () == getButton ( Button.ENCRYPT ) ) {
			// If so, then call the appropriate action handler
			actionEncrypt ();
		}
		// Check to see if we clicked the decrypt button
		else if ( e.getSource () == getButton ( Button.DECRYPT ) ) {
			// If so, then call the appropriate action handler
			actionDecrypt ();
		}
		else if ( e.getSource () == getButton ( Button.DECRYPT ) ) {
			// If so, then call the appropriate action handler
			actionDecrypt ();
		}
	}

	private void actionLoad () {
		// Initialize a file chooser
		JFileChooser chooser = new JFileChooser ();
		// Pick to choose from current directory
		chooser.setCurrentDirectory ( new File ( "." ) );
		// Don't select anything by default
		chooser.setSelectedFile ( new File ( "" ) );
		// Set the mode of selection we want
		chooser.setFileSelectionMode ( JFileChooser.FILES_ONLY );
		// Initialize opening the window
		if ( chooser.showOpenDialog ( this.frame ) == JFileChooser.OPEN_DIALOG ) {
			// Save the target path of the file
			this.target = chooser.getSelectedFile ().getAbsolutePath ();
			try {
				// Initialize a new key class by passing in the loaded file
				Key key = new Key ( this.target );
				// Set the key type in the key status text field
				if ( key.type () == Key.Type.PRIVATE ) {
					// Change the key status field
					getTextArea ( TextArea.KEYSTATUS ).setText ( "Private Key Loaded" );
					// Report success
					getTextArea ( TextArea.MESSAGE ).setText ( "Private key loaded Successfully" );
				}
				else {
					// Change the key status field
					getTextArea ( TextArea.KEYSTATUS ).setText ( "Public Key Loaded" );
					// Report success
					getTextArea ( TextArea.MESSAGE ).setText ( "Private key loaded Successfully" );
				}
				// Save key internally
				this.key = key;
				// Enable the open button and disable the rest
				getButton ( Button.OPEN ).setEnabled ( true );
				getButton ( Button.BLOCK ).setEnabled ( false );
				getButton ( Button.UNBLOCK ).setEnabled ( false );
				getButton ( Button.ENCRYPT ).setEnabled ( false );
				getButton ( Button.DECRYPT ).setEnabled ( false );
			}
			catch ( RSAException exception ) {
				// If an invalid key file was passed, then we report it
				getTextArea ( TextArea.MESSAGE ).setText ( "Invalid key file selected" );
			}
		}
	}

	private void actionCreate () {
		// First things first, we want to disable any file actions
		getButton ( Button.BLOCK ).setEnabled ( false );
		getButton ( Button.UNBLOCK ).setEnabled ( false );
		getButton ( Button.ENCRYPT ).setEnabled ( false );
		getButton ( Button.DECRYPT ).setEnabled ( false );
		// Initialize a new Prime class to get two prime numbers
		Prime prime = new Prime ();
		// Save two prime numbers
		Decimal a = prime.random ();
		Decimal b = prime.random ();
		// Initialize a new key generation class to generate a new key
		KeyGeneration keygen = new KeyGeneration ( a, b );
		// Create a new timestamp so we can save the keys
		String timestamp = Key.timestamp ();
		// Export our keys to the output file
		keygen.publicKey.export ( timestamp );
		keygen.privateKey.export ( timestamp );
		// Load in the public key by default into our program
		this.key = keygen.publicKey;
		// Change the status to reflect this
		getTextArea ( TextArea.KEYSTATUS ).setText ( "Public Key Loaded" );
		// Update the log to say this as well
		getTextArea ( TextArea.MESSAGE ).setText (
			"Successfully created keys...\n" +
			"p = " + a.stringify () + "\n" +
			"q = " + b.stringify () + "\n"
		);
		// Release the target form being disabled
		getButton ( Button.OPEN ).setEnabled ( true );
	}

	private void actionOpen () {
		// Initialize a file chooser
		JFileChooser chooser = new JFileChooser ();
		// Pick to choose from current directory
		chooser.setCurrentDirectory ( new File ( "." ) );
		// Don't select anything by default
		chooser.setSelectedFile ( new File ( "" ) );
		// Set the mode of selection we want
		chooser.setFileSelectionMode ( JFileChooser.FILES_ONLY );
		// Initialize opening the window
		if ( chooser.showOpenDialog ( this.frame ) == JFileChooser.OPEN_DIALOG ) {
			// Save the target path of the file
			this.target = chooser.getSelectedFile ().getAbsolutePath ();
			// Display the file path
			getTextArea ( TextArea.FILEPATH ).setText ( chooser.getSelectedFile ().getName () );
			// Display a message in the log
			getTextArea ( TextArea.MESSAGE ).setText ( "Opened file: " + this.target );
			// Enable the operation buttons based on key
			if ( this.key.type () == Key.Type.PRIVATE ) {
				getButton ( Button.ENCRYPT ).setEnabled ( false );
				getButton ( Button.DECRYPT ).setEnabled ( true );
			}
			// If its a public key we can not encrypt
			else {
				getButton ( Button.ENCRYPT ).setEnabled ( true );
				getButton ( Button.DECRYPT ).setEnabled ( false );
			}
			// Enable the rest of the operation
			getButton ( Button.BLOCK ).setEnabled ( true );
			getButton ( Button.UNBLOCK ).setEnabled ( true );
		}
	}

	private void actionBlock () {
		// Try to block input file
		try {
			// Attempt to block message
			Block block = new Block ( this.target, 2, this.target );
			// Report success
			getTextArea ( TextArea.MESSAGE ).setText ( "Successfully blocked input file" );
			// Since we blocked, we don't wanna allow user to block again
			getButton ( Button.BLOCK ).setEnabled ( false );
			getButton ( Button.UNBLOCK ).setEnabled ( true );
		}
		// Catch any errors that might be thrown
		catch ( Exception e ) {
			// If any are thrown, then report as failure
			getTextArea ( TextArea.MESSAGE ).setText ( "Failed to block input file" );
		}
	}

	private void actionUnblock () {
		// Try to unblock input file
		try {
			// Attempt to unblock message
			Unblock block = new Unblock ( this.target, 2, this.target );
			// Report success
			getTextArea ( TextArea.MESSAGE ).setText ( "Successfully unblocked input file" );
			// Since we unblocked, we don't wanna allow user to unblock again
			getButton ( Button.BLOCK ).setEnabled ( true );
			getButton ( Button.UNBLOCK ).setEnabled ( false );
		}
		// Catch any errors that might be thrown
		catch ( Exception e ) {
			// If any are thrown, then report as failure
			getTextArea ( TextArea.MESSAGE ).setText ( "Failed to unblock input file" );
		}
	}

	private void actionEncrypt () {
		// Try to encrypt the file
		try {
			// Initialize the Encryption class and pass info
			Encryption encrypt = new Encryption ( this.key, this.target );
			// Alert with success
			getTextArea ( TextArea.MESSAGE ).setText ( "Successfully encrypted the input file" );
			// We don't want the user to encrypt twice
			getButton ( Button.ENCRYPT ).setEnabled ( false );
		}
		// Try to catch exceptions
		catch ( RSAException exception ) {
			// If we catch an exception, report error
			getTextArea ( TextArea.MESSAGE ).setText ( "Failed to encrypt the input file" );
		}
	}

	private void actionDecrypt () {
		// Try to decrypt the file
		try {
			// Initialize the Encryption class and pass info
			Encryption encrypt = new Encryption ( this.key, this.target );
			// Alert with success
			getTextArea ( TextArea.MESSAGE ).setText ( "Successfully decrypted the input file" );
			// We don't want the user to decrypt twice
			getButton ( Button.DECRYPT ).setEnabled ( false );
		}
		// Try to catch exceptions
		catch ( RSAException exception ) {
			// If we catch an exception, report error
			getTextArea ( TextArea.MESSAGE ).setText ( "Failed to decrypt the input file" );
		}
	}

}