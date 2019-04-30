package com.rsa_encryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Unblock.java - This class has one sole purpose, to unblock a given file
 * (usually after blocking).  This operation is offered to the user as a sole operation, but is
 * also implemented within the encryption and decryption algorithm within the Encryption.java
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
public class Unblock {


	// gets the file name
	private String fileName;
	private File oFile;
	// block size of data
	int blocksize ;
	private String storeMsg;

	//constructor for the block
	public Unblock (String inputFile, int val, String outputFile) throws IOException {
		// make a new file for output
		oFile = new File(outputFile);
		fileName = inputFile;
		// here because each value has two char we multiple by 2
		blocksize = val*2;
		storeMsg = "";
		// method to convert the message
		readMessage();
		writenewMsg();
	}


	// method to convert message to the numeric

	public void readMessage() throws IOException{

		BufferedReader line = null;
		String currLine;
		String temp = "";
		// to check if it a variable to avoid padding conversion
		boolean make = false;
		line = new BufferedReader(new FileReader(fileName));
		// while there is no more line
		while((currLine = line.readLine()) != null){
			for(int i = 0; i< blocksize;i=i+2){
				temp = currLine.substring(i,i+2);
				// if one of the special case
					// to check if the value is one of the variable
					if(temp.startsWith("0")){
						temp = temp.substring(1);
					}
					int tempval = Integer.parseInt(temp);
					// if there is no padding
					if(tempval != 0){
						if(tempval == 1){
							char temp1 = '\b';
							temp = String.valueOf(temp1);
						}
						else if(tempval == 2){
							char temp1 = '\t';
							temp = String.valueOf(temp1);
						}
						else if(tempval == 3){
							char temp1 = '\n';
							temp = String.valueOf(temp1);
						}
						else if(tempval == 4){
							char temp1 = '\r';
							temp = String.valueOf(temp1);
						}
						else {
							int val2 = tempval + 27;
							char[] char1 = Character.toChars(val2);
							//to add in the store string
							temp = String.valueOf(char1);
						}
						make = true;
				   }


				//keep adding to back
				if(make == true){
					storeMsg = storeMsg +temp;
					make= false;
				}
			}
		}
	}

		// method to write in the original file
		public void writenewMsg() throws IOException{

			// steps to divid according to block soze
			FileWriter newFile ;
			newFile = new FileWriter(oFile);
			BufferedWriter w ;
			w =  new BufferedWriter(newFile);
			//write in the new file
			w.write(storeMsg);
			w.close();
		}

}
