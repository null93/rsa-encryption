main:
	javac *.java

run:
	java RSA

clean:
	rm -rf *.class
	rm -rf ./output/
	rm -rf example.txt