import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Block.java - This class has one sole purpose, it needs to be able to take a file and block it to
 * a specified block size.  It also needs to do the inverse of said operation and it needs to be
 * able to unblock a file as well which is implemented in Unblock.java.  This operation is offered to the user as a sole operation, but 
 * is also implemented within the encryption and decryption algorithm within the Encryption.java
 * class.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #03 - RSA Encryption / Decryption
 * @category    Blocking
 * @author      Rafael Grigorian
 * @author      Henvy Patel
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Block {

	
	// gets the file name
	private String fileName;
	private File oFile;
	// block size of data
	int blocksize ;
	private String storeMsg;
	
	//constructor for the block
	public Block (String inputFile, int val, String outputFile) throws IOException {
         oFile = new File(outputFile);
		blocksize = val*2;
		storeMsg = "";
		fileName = inputFile;
		// method to convert the message
		if(inputFile != null){
			makeMessage();
			writeMsg();
		}
		else{
			System.out.println("No such file found!!");
			return;
		}
	}
	
	
	// method to convert message to the numeric
	public void makeMessage() throws IOException{
		
		FileReader inputfile = new FileReader(fileName);
		// variable to store each read from the file
		int readval;
		String temp = "";
		try {
			while((readval = inputfile.read()) != -1){
				// check if a special arr
				if(readval == 10){
					temp = "03";
					storeMsg = storeMsg +temp;
				}
				else if(readval == 11){
					temp = "01";
					storeMsg = storeMsg +temp;
				}
				else if(readval == 0){
					temp = "00";
					storeMsg = storeMsg +temp;
				}
				else if(readval == 9){
					temp = "02";
					storeMsg = storeMsg +temp;
				}
				else if(readval == 13){
					temp = "04";
					storeMsg = storeMsg +temp;
				}
				else{
					int val = readval - 27;
					// if the val is less 
					if(val < 10){
						temp = "0"+ Integer.toString(val);
					}
					else{
						readval = readval-27;
						temp = Integer.toString(readval);
					}
					// put combine the string
					storeMsg = storeMsg +temp;
				}
			}
		}
		catch(IOException e){
			// catch the error
			System.out.println("Error in message converting");
		}
	}
	
	
	// method to replace the special ascii characters
	public int spl_ascii(int val , String s){
		if(val == 0){
			s = "00";
			return 1;
		}
		else if(val == 11){
			s = "01";
			return 1;
		}
		else if(val == 9){
			s = "02";
			return 1;
		}
		else if(val == 10){
			s = "03";
			return 1;
		}
		else if(val == 13){
			s = "04";
			return 1;
		}
		else {
			return 0;
		}
	}
	
	// to wrtite a msg in the new file
	public void writeMsg() throws IOException{
		// steps to divid according to block soze
		FileWriter newFile ;
		newFile = new FileWriter(oFile);
		BufferedWriter w ;
		w =  new BufferedWriter(newFile);
		int strlen = storeMsg.length();
		// place hlder for holding the string length
		int hold = storeMsg.length();
		int c = 0;
		int range = blocksize;
		while(strlen > blocksize){
			w.write(storeMsg.substring(c,range+c));
			w.newLine();
			c= c+blocksize;
			strlen=strlen-blocksize;
		}
		
		// mean padding requires
		if(strlen > 0){
			int left = blocksize - strlen;
			String leftstr = storeMsg.substring(hold-strlen);
			for(int i = 0 ;i<left;i++){
				leftstr = "0"+ leftstr;
			}
			// add the leftover
			w.write(leftstr);
		}
		w.close();
	}

}