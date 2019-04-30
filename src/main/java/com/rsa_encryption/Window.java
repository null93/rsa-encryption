package com.rsa_encryption;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

/**
 * Window.java - This class is used to simplify the process of creating populating and rendering a
 * window onto the screen.  It automates creating a generic window and also gives helper function
 * to populate constraints in a fast and easy way.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #03 - RSA Encryption / Decryption
 * @category    GUI
 * @author      Rafael Grigorian
 * @author      Henvy Patel
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Window extends JFrame {

	/**
	 * This data member stores the panel that is created internally.  It is the main panel that
	 * handle the rest.
	 * @var     JPanel          panel           The stored instance of created JPanel
	 */
	protected JPanel panel;

	/**
	 * This constructor takes many parameters that will be used to initialize the frame, panel, and
	 * even grid.  It also extends from JFrame.
	 * @param   String          title           Title for frame
	 * @param   int             height          Height of window
	 * @param   int             width           Width of window
	 * @param   int             cols            Number of columns in grid
	 * @param   int             rows            Number of rows in grid
	 */
	public Window ( String title, int height, int width ) {
		// Create a JFrame instance with passed string as title
		super ( title );
		// Ensure when we close it, we only close that frame
		setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE );
		// Set up the panel
		this.panel = new JPanel ();
		// Set the border to be empty, giving the panel padding
		this.panel.setBorder ( new EmptyBorder ( 10, 10, 10, 10 ) );
		// Set the layout type
		this.panel.setLayout ( new BoxLayout ( this.panel, BoxLayout.Y_AXIS ) );
		// Disable resizing
		setResizable ( false );
		// Add Panel to frame
		add ( panel );
		// Set the frame width and the height
		setSize ( width, height );
		// Get current screen size
		Dimension dim = Toolkit.getDefaultToolkit ().getScreenSize ();
		// Center frame in the middle of the screen
		setLocation (
			dim.width / 2 - getSize ().width / 2,
			dim.height / 2 - getSize ().height / 2
		);
	}

	/**
	 *
	 */
	protected JPanel addPanel ( int rows, int cols ) {
		// Make a panel object with passed number of rows and columns
		JPanel panel = new JPanel ( new GridLayout ( cols, rows ) );
		// Set the border to be empty, giving the panel padding
		panel.setBorder ( new EmptyBorder ( 10, 10, 10, 10 ) );
		// Set the layout type
		panel.setLayout ( new GridBagLayout () );
		// Add the panel internally
		this.panel.add ( panel );
		// Return the panel
		return panel;
	}

	/**
	 * This function initializes a new instance of GridBagConstraints.  Doing this functionally
	 * saves in a lot of space when coding and packing labels into panel.
	 * @param   int             gx              Which row index
	 * @param   int             gy              Which column index
	 * @param   double          wx              The weight of the column
	 * @param   double          wy              The weight of the row
	 * @param   int             d               Type casted GridBagConstraints direction
	 * @return  GridBagConstraints
	 */
	protected GridBagConstraints options ( int gx, int gy, double wx, double wy, int d ) {
		// Initialize grid constraint instance
		GridBagConstraints constraint = new GridBagConstraints ();
		// Set options
		constraint.weightx = wx;
		constraint.weighty = wy;
		constraint.gridy = gy;
		constraint.gridx = gx;
		constraint.anchor = d;
		// Return constraint
		return constraint;
	}

	/**
	 * This function turns a passed label to have the attribute of having bolded text.  It saves a
	 * lot of space coding when done like this functionally.
	 * @param   JLabel          target          The passed JLabel
	 * @return  JLabel                          Same instance of modified JLabel
	 */
	protected JLabel bold ( JLabel target ) {
		// Get the current font of the label
		Font font = target.getFont ();
		// Set the font to bold
		target.setFont ( new Font ( font.getFontName (), Font.BOLD, font.getSize () ) );
		// Return the input
		return target;
	}

	/**
	 * This function simply makes the JFram visible.  It is separated into a function because once
	 * we construct the JFrame from the classes constructor, we pack it outside of the class using
	 * the panel pointer and then finally we can display the result after all packing is complete.
	 * @return  void
	 */
	protected void display () {
		// Set the visibility to true
		setVisible ( true );
	}

}
