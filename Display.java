import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;

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
	 * This data member holds the instance of the window frame in order for action functions to call
	 * it in the future after creation.
	 * @var     Window          frame               An instance of Window which extends from JFrame
	 */
	private Window frame;

	/**
	 * This data member is a handle that references the main JPanel that all other JPanels belong to
	 * @var     JPanel          panel               Main JPanel inside Window instance
	 */
	private JPanel panel;

	/**
	 * This data member holds all the buttons needed in this GUI.  It is an array for modularization
	 * purposes.
	 * @var     JButton []      buttons             Array of buttons in our GUI
	 */
	private JButton [] buttons;

	/**
	 * This data member holds all the text areas needed in the GUI.  It is an array for
	 * modularization purposes.
	 * @var     JTextArea []    textAreas           Array of text areas in our GUI
	 */
	private JTextArea [] textAreas;

	/**
	 * This data member holds the currently loaded key and holds the reference to it.
	 * @var     Key             key                 The currently loaded key
	 */
	private Key key;

	/**
	 * This data member holds the path name as a string to the currently selected file.
	 * @var     String          target              The currently selected file
	 */
	private String target = null;

	/**
	 * This data member is static an final, it represents the blocking size and is immutable
	 * @var 	int 			blocking 			The blocking size
	 */
	protected static final int blocking = 8;

	/**
	 * This enum is used to refer to buttons and binds the enum index to the index in the buttons
	 * data member.
	 * @enum    Button                              Types of buttons in GUI
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
	 * This enum is used to refer to text areas and binds the enum index to the index in the
	 * textAreas data member.
	 * @enum    TextArea                            Types of text areas in GUI
	 */
	protected enum TextArea {
		KEYSTATUS,
		FILEPATH,
		MESSAGE;
	}

	/**
	 * This constructor initializes the buttons and text areas array, as well as creates a Window
	 * instance and populates it with created buttons and text elements.
	 */
	public Display () {
		// Create the output folder
		Key.checkFile ( Key.folder, "./" );
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
	 * This class is used to set a passed button to the array of buttons based on the passed enum.
	 * @param   JButton         button              The button to add
	 * @param   Button          type                Used to determine which index in the array
	 * @return  void
	 */
	protected void setButton ( JButton button, Button type ) {
		// Get the index of the enum
		int index = type.ordinal ();
		// Save button into that index
		this.buttons [ index ] = button;
	}

	/**
	 * This class is used to set a passed text area to the array of text areas based on the passed
	 * enum.
	 * @param   JTextArea       button              The text area to add
	 * @param   TextArea        type                Used to determine which index in the array
	 * @return  void
	 */
	protected void setTextArea ( JTextArea textArea, TextArea type ) {
		// Get the index of the enum
		int index = type.ordinal ();
		// Save text area into that index
		this.textAreas [ index ] = textArea;
	}

	/**
	 * This function is a getter function that returns a button reference based on passed enum.
	 * @param   Button          type                Which button to return based on index
	 * @return  JButton                             The button that will be returned
	 */
	private JButton getButton ( Button type ) {
		// Get the index of the enum
		int index = type.ordinal ();
		// Return that index
		return this.buttons [ index ];
	}

	/**
	 * This function is a getter function that returns a text area reference based on passed enum.
	 * @param   TextArea        type                Which text area to return based on index
	 * @return  JTextArea                           The text area that will be returned
	 */
	private JTextArea getTextArea ( TextArea type ) {
		// Get the index of the enum
		int index = type.ordinal ();
		// Return that index
		return this.textAreas [ index ];
	}

	/**
	 * This class creates a JButton based on some information that is passed, it exists to free
	 * cluttered repetitive code.
	 * @param   String          name                The value of the button
	 * @param   int             width               The width of the button
	 * @param   int             height              The height of the button
	 * @param   Button          type                The type of button
	 * @return  JButton                             The end resulting button
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
	 * This class creates a JTextArea based on some information that is passed, it exists to free
	 * cluttered repetitive code.
	 * @param   String          value               The value inside text field
	 * @param   int             rows                The number of rows
	 * @param   int             cols                The number of columns
	 * @param   int             fontsize            The font size for the text area
	 * @param   TextArea        type                The type of text area as enum
	 * @return  JTextArea                           The end result instance of JTextArea
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
	 * This function is inherited from th ActionListener class and redirects button presses with the
	 * appropriate action functions defined below.
	 * @param   ActionEvent     e                   The event that was triggered
	 * @return  void
	 * @see     ActionListener
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

	/**
	 * This function handles the event when the load button is clicked.  Ideally it loads in a
	 * previously generated key.
	 * @return  void
	 */
	private void actionLoad () {
		// Initialize a file chooser
		JFileChooser chooser = new JFileChooser ();
		// Pick to choose from current directory
		chooser.setCurrentDirectory ( new File ( Key.folder ) );
		// Don't select anything by default
		chooser.setSelectedFile ( new File ( "." ) );
		// Set the mode of selection we want
		chooser.setFileSelectionMode ( JFileChooser.FILES_ONLY );
		// Initialize opening the window
		if ( chooser.showOpenDialog ( this.frame ) == JFileChooser.OPEN_DIALOG ) {
			// Save the target path of the file
			String target = chooser.getSelectedFile ().getAbsolutePath ();
			// Try to load key from file
			try {
				// Initialize a new key class by passing in the loaded file
				Key key = new Key ( target );
				// Set the key type in the key status text field
				if ( key.type () == Key.Type.PRIVATE ) {
					// Change the key status field
					getTextArea ( TextArea.KEYSTATUS ).setText ( "Private Key Loaded" );
					// Report success
					getTextArea ( TextArea.MESSAGE ).setText ( "Private key loaded Successfully" );
					// See if target file is loaded
					if ( this.target != null ) {
						// Then make the operation buttons enabled
						getButton ( Button.BLOCK ).setEnabled ( false );
						getButton ( Button.UNBLOCK ).setEnabled ( false );
						getButton ( Button.ENCRYPT ).setEnabled ( false );
						getButton ( Button.DECRYPT ).setEnabled ( true );
					}
				}
				else {
					// Change the key status field
					getTextArea ( TextArea.KEYSTATUS ).setText ( "Public Key Loaded" );
					// Report success
					getTextArea ( TextArea.MESSAGE ).setText ( "Private key loaded Successfully" );
					// See if target file is loaded
					if ( this.target != null ) {
						// Then make the operation buttons enabled
						getButton ( Button.BLOCK ).setEnabled ( true );
						getButton ( Button.UNBLOCK ).setEnabled ( false );
						getButton ( Button.ENCRYPT ).setEnabled ( false );
						getButton ( Button.DECRYPT ).setEnabled ( false );
					}
				}
				// Save key internally
				this.key = key;
				// Enable the open file either way
				getButton ( Button.OPEN ).setEnabled ( true );
			}
			catch ( RSAException exception ) {
				// If an invalid key file was passed, then we report it
				getTextArea ( TextArea.MESSAGE ).setText ( "Invalid key file selected" );
			}
		}
	}

	/**
	 * This function is triggered when a user clicks in the create button.  Ideally it creates a
	 * set of keys, public and private, and is reported to the user.
	 * @return  void
	 */
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
			"Successfully created keys and loaded public key...\n" +
			"p = " + a.stringify () + "\n" +
			"q = " + b.stringify () + "\n"
		);
		// Check if the target file is loaded, if it is enable blocking and encrypting
		if ( this.target != null ) {
			getButton ( Button.BLOCK ).setEnabled ( true );
			getButton ( Button.ENCRYPT ).setEnabled ( true );
		}
		// Release the target form being disabled
		getButton ( Button.OPEN ).setEnabled ( true );
	}

	/**
	 * This function is triggered when the open button is clicked.  This will prompt the user to
	 * select a file to be operated on.
	 * @return  void
	 */
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
				getButton ( Button.BLOCK ).setEnabled ( false );
				getButton ( Button.UNBLOCK ).setEnabled ( false );
				getButton ( Button.ENCRYPT ).setEnabled ( false );
				getButton ( Button.DECRYPT ).setEnabled ( true );
			}
			// If its a public key we can not encrypt
			else {
				getButton ( Button.BLOCK ).setEnabled ( true );
				getButton ( Button.UNBLOCK ).setEnabled ( false );
				getButton ( Button.ENCRYPT ).setEnabled ( false );
				getButton ( Button.DECRYPT ).setEnabled ( false );
			}
		}
	}
	/**
	 * This function is triggered when a user clicks on the block button, and ideally it does it's
	 * desired behavior.
	 * @return  void
	 */
	private void actionBlock () {
		// Try to block input file
		try {
			// Attempt to block message
			Block block = new Block ( this.target, Display.blocking, this.target );
			// Report success
			getTextArea ( TextArea.MESSAGE ).setText ( "Successfully blocked input file" );
			// Enable based on key
			if ( this.key.type () == Key.Type.PRIVATE ) {
				getButton ( Button.BLOCK ).setEnabled ( false );
				getButton ( Button.UNBLOCK ).setEnabled ( true );
				getButton ( Button.DECRYPT ).setEnabled ( false );
			}
			// Enable based on key
			else {
				getButton ( Button.BLOCK ).setEnabled ( false );
				getButton ( Button.UNBLOCK ).setEnabled ( true );
				getButton ( Button.ENCRYPT ).setEnabled ( true );
			}
		}
		// Catch any errors that might be thrown
		catch ( Exception e ) {
			// If any are thrown, then report as failure
			getTextArea ( TextArea.MESSAGE ).setText ( "Failed to block input file" );
		}
	}

	/**
	 * This function is triggered when a user clicks on the unblock button, and ideally it does it's
	 * desired behavior.
	 * @return  void
	 */
	private void actionUnblock () {
		// Try to unblock input file
		try {
			// Attempt to unblock message
			Unblock block = new Unblock ( this.target, Display.blocking, this.target );
			// Report success
			getTextArea ( TextArea.MESSAGE ).setText ( "Successfully unblocked input file" );
			// Enable based on key
			if ( this.key.type () == Key.Type.PRIVATE ) {
				getButton ( Button.BLOCK ).setEnabled ( true );
				getButton ( Button.UNBLOCK ).setEnabled ( false );
				getButton ( Button.DECRYPT ).setEnabled ( false );
			}
			// Enable based on key
			else {
				getButton ( Button.BLOCK ).setEnabled ( true );
				getButton ( Button.UNBLOCK ).setEnabled ( false );
				getButton ( Button.ENCRYPT ).setEnabled ( false );
			}
		}
		// Catch any errors that might be thrown
		catch ( Exception e ) {
			// If any are thrown, then report as failure
			getTextArea ( TextArea.MESSAGE ).setText ( "Failed to unblock input file" );
		}
	}

	/**
	 * This function is triggered when a user tries to encrypt a file.  Ideally it does it's desired
	 * behavior and reports results to the user.
	 * @return  void
	 */
	private void actionEncrypt () {
		// Try to encrypt the file
		try {
			// Initialize the Encryption class and pass info
			Encryption encrypt = new Encryption ( this.key, this.target );
			// Alert with success
			getTextArea ( TextArea.MESSAGE ).setText ( "Successfully encrypted the input file" );
			// We don't want the user to encrypt twice
			getButton ( Button.ENCRYPT ).setEnabled ( false );
			getButton ( Button.UNBLOCK ).setEnabled ( false );
			getButton ( Button.BLOCK ).setEnabled ( false );
		}
		// Try to catch exceptions
		catch ( RSAException exception ) {
			// If we catch an exception, report error
			getTextArea ( TextArea.MESSAGE ).setText ( "Failed to encrypt the input file" );
		}
	}

	/**
	 * This function is triggered when a user tries to decrypt a file.  Ideally it does it's desired
	 * behavior and reports results to the user.
	 * @return  void
	 */
	private void actionDecrypt () {
		// Try to decrypt the file
		try {
			// Initialize the Encryption class and pass info
			Encryption encrypt = new Encryption ( this.key, this.target );
			// Alert with success
			getTextArea ( TextArea.MESSAGE ).setText ( "Successfully decrypted the input file" );
			// We don't want the user to decrypt twice
			getButton ( Button.DECRYPT ).setEnabled ( false );
			getButton ( Button.BLOCK ).setEnabled ( false );
			getButton ( Button.UNBLOCK ).setEnabled ( true );
		}
		// Try to catch exceptions
		catch ( RSAException exception ) {
			// If we catch an exception, report error
			getTextArea ( TextArea.MESSAGE ).setText ( "Failed to decrypt the input file" );
		}
	}

}