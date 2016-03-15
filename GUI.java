/**
 * GUI.java - This class handles displaying the initial options menu to the user as well as walking
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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener{
	
		// JLabels
		private JLabel forTitle;
		private JLabel forTarget;
		private JLabel forBlocking;
		private JLabel forbfile;
		private JLabel forlfile;
		//labels for unblocking
		private JLabel uforbfile;
		private JLabel uforlfile;
		// JButtons for various options
		public JLabel toKeystatus;
		private JLabel blocksize;
		private JLabel blocksize2;
		
		// various buttons to perform various function
		private MyJButton toCreate;
		private MyJButton toLoad;
		private MyJButton toOpen;
		private MyJButton toBlock;
		private MyJButton toUnblock;
		private MyJButton toEncrypt;
		private MyJButton toDecrypt;
		
		// Text field for getting the filepath
		private JTextField filepath;
		private JTextField fileloc;
		private JTextField bsize1;
		private JTextField bsize2;
		// file inputs for unblock
		public JTextField ufilepath;
		public JTextField ufileloc;
		// grid layout
		private GridLayout grid;
		
		// toogle is to keep track for events
		private boolean toggle = true;
		
		// panels for adding various option i.e buttons, labels and text feild
		private JPanel options;
		private JPanel koptions;
		private JPanel target;
		private JPanel blocking;
		private JPanel unblocking;
		private JPanel encrypt;
		
		
		// declaring a container to add all the elements of the GUI
		private Container container;
		
		
		// Constructor for GUI
		public GUI(){
			
			super("RSA Encrypion");
			
			// intializr grids
			grid = new GridLayout( 7, 1);
			// create new JPanels for each options in the RSA program
			options = new JPanel();
			koptions = new JPanel();
			target  = new JPanel();
			//target.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

			blocking = new JPanel();
			unblocking = new JPanel();
			encrypt   = new JPanel();
			
			// intializing the container
			container = getContentPane();
			container.setLayout(grid);
			
			// for the title
			forTitle = new JLabel("RSA Encryption Key Options:");
			options.add(Box.createRigidArea(new Dimension(5,0)));  
			options.add(forTitle);
			
			// Option 1 : Key Status & Type Options:
			toKeystatus = new JLabel("Key Status & Type ");
			koptions.add(Box.createRigidArea(new Dimension(5,0)));  
			koptions.add(toKeystatus);
			toCreate = new MyJButton("Create");
			toCreate.setnum(1);
			toCreate.addActionListener( this );
			koptions.add(Box.createRigidArea(new Dimension(5,0)));  
			koptions.add(toCreate);
			toLoad = new MyJButton("Load");
			toLoad.setnum(2);
			toLoad.addActionListener( this );
			koptions.add(Box.createRigidArea(new Dimension(5,0)));  
			koptions.add(toLoad);
		    
			
			// for the title target Files
			forBlocking = new JLabel("Blocking:");
			blocking.add(forBlocking,BorderLayout.PAGE_START);
			
			
			// OPTION 2: Blocking
			filepath  = new JTextField(10);
			forbfile =  new JLabel("Source:");
			forlfile =  new JLabel("Destination:");
			fileloc = new JTextField(10);
			blocksize = new JLabel("Block size:");
			bsize1 = new JTextField(3);
			blocking.add(forbfile);
			blocking.add(filepath);
			blocking.add(forlfile);
			blocking.add(fileloc);
			blocking.add(blocksize);
			blocking.add(bsize1);
			toBlock = new MyJButton("BLOCK");
			toBlock.setnum(3);
			toBlock.addActionListener( this );
			blocking.add(toBlock);
			
			// OPTION 3:Unblocking
			forTarget = new JLabel("Unblocking:");
			unblocking.add(forTarget);
			ufilepath  = new JTextField(10);
			uforbfile =  new JLabel("Source:");
			uforlfile =  new JLabel("Destination:");
			blocksize2 = new JLabel("Block size:");
			bsize2 = new JTextField(3);
			ufileloc = new JTextField(10);
			unblocking.add(uforbfile);
			unblocking.add(ufilepath);
			unblocking.add(uforlfile);
			unblocking.add(ufileloc);
			unblocking.add(blocksize2);
			unblocking.add(bsize2);
			unblocking.add(Box.createRigidArea(new Dimension(2,0))); 
			toUnblock = new MyJButton("UNBLOCK");
			toUnblock.setnum(4);
			toUnblock.addActionListener( this );
			unblocking.add(toUnblock);
			
			// option 4 : encrypt decrpt
			toEncrypt = new MyJButton("ENCRYPT");
			toEncrypt.setnum(5);
			toEncrypt.addActionListener( this );
			encrypt.add(toEncrypt);
		    toDecrypt = new MyJButton("DECRYPT");
		    toDecrypt.setnum(6);
			toDecrypt.addActionListener( this );
		    encrypt.add(toDecrypt);
		    
			
			//adding the panels to the container
			
			container.add(options);
			container.add(koptions);
			container.add(target);
			container.add(blocking);
			container.add(unblocking);
			container.add(encrypt);
			setSize( 600, 450 );
		    setVisible( true );
			
	}// end Of Gui Cnstrutor
	
	
	// implementation of ActionListner
	public void actionPerformed (ActionEvent e)
	  {
		// here the v will be the values that will be passes to represent each button and it funtion
		MyJButton temp = (MyJButton) e.getSource();
		
		if(temp.getnum() == 1){
			// do create key
		}
		if(temp.getnum() == 2){
			// do load key
		}
		if(temp.getnum() == 3){
		
			// do blocking
			String inputfile = filepath.getText();
			if(inputfile == null){
				JOptionPane.showMessageDialog( this, "Please enter source file and try again");
				return;
			}
			String outputfile = fileloc.getText();
			if(outputfile == null){
				JOptionPane.showMessageDialog( this, "Please enter Location to save file and try again");
				return;
			}
			String val = bsize1.getText();
			if(val == null){
				JOptionPane.showMessageDialog( this, "Please enter block and try again");
				return;
			}
			int val1 = Integer.parseInt(val);
			try {
				Block blockfile = new Block(inputfile, val1, outputfile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 filepath.setText("");
			 fileloc.setText("");
			 bsize1.setText("");
			 JOptionPane.showMessageDialog( this, "Blocking sucessfully completed!");
			

		}
		if(temp.getnum() == 4){
			// do unblocking
			// do blocking
			String inputfile = ufilepath.getText();
			if(inputfile == null){
				JOptionPane.showMessageDialog( this, "Please enter source file and try again");
				return;
			}
			String outputfile = ufileloc.getText();
			if(outputfile == null){
				JOptionPane.showMessageDialog( this, "Please enter Location to save file and try again");
				return;
			}
			String val = bsize2.getText();
			if(val == null){
				JOptionPane.showMessageDialog( this, "Please enter block size and try again");
				return;
			}
			int val1 = Integer.parseInt(val);
			try {
				Unblock blockfile = new Unblock(inputfile, val1, outputfile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 ufilepath.setText("");
			 ufileloc.setText("");
			 bsize2.setText("");
			 JOptionPane.showMessageDialog( this, "Unblocking sucessfully completed!");
		}
		if(temp.getnum() == 5){
			// do encrypt
		}
		if(temp.getnum() == 6){
			// do decrypt
		}
		
	  }
	
	// MYJBUTTON TO ASSIGNE VALUES TO THE BUTTON TO PERFORM VARIOUS FUNCTIONs
	class MyJButton extends JButton
	{
	  private int num;
	  
	  public MyJButton ( String text )
	  {
	    super (text);
	    //setText (text);
	  }
	  
	  
	  public MyJButton ( String text , int n)
	  {
	    super (text);
	    num = n;
	  }
	  
	  public void setnum (int n)
	  {
	    num = n;
	  }
	  
	  public int getnum ()
	  {
	    return num;
	  }
	}
}// end of class Gui


