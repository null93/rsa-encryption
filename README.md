###RSA Encryption / Decryption Program
---
This program implements **RSA** key encryption and decryption.  There is a file called *PrimeNumbers.rsc* that contains a list of large prime numbers.  These primes are used to generate private and public keys.  When the program starts, you can either load in an already generated key pair, or you can create new ones.  The new keys will be in a folder called *output* in the save directory relative to the program.  Inside there is a file heiarchy that will represent a when a key was generated through the use of a timestamped folder.  Inside said folders, there are public and private keys in XML format.  When a key is generated, it is important to note that only one key can be loaded at a time, hence you can only encrypt or decrypt if you loaded the key once.  The prublic key allows for encryption, while the private key allows for decryption.

###Simple Steps
---
1.	Compile using `make main`
2.	Run using `make run`
3.	When GUI pops up, click *Create*, public key will be loaded
4.	Click *Open*, and select file to process
5.	Click *Block*
6.	Click *Encrypt*, and you will have your encrypted file
7.	To decrypt, Click *Load*, navagate to private key and select
8.	Click *Decrypt*
9.	Click *Unblock*, and your original result will be contained within file

###Sources
---
* http://doctrina.org/How-RSA-Works-With-Examples.html
	- This source was used to better understand how RSA encryption works and how the key generation process works.
* http://www.tutorialspoint.com/java_xml/java_dom_create_document.htm
	- This source was used to learn how to parse and write XML files in the Java language.
* https://www.khanacademy.org/computing/computer-science/cryptography/modarithmetic/a/fast-modular-exponentiation
 	- This source was used to better explain how to expand a exponential operation using the Eclidean explansion algorithm.
