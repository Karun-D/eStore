Name: Karunpreet Dhamnait
Student #1094528
Program: Assignment #3 - eStoreSearch

(1) Describe the general problem you are trying to solve

	This program is created to simulate an "eStore" by using object oriented programming to represent two types of products (Books and Electronics). These products can be added to the eStore via an ArrayList, and a subset of products can be searched for in the eStore by also using ArrayLists. The details of these products, and the filters to search the store are both generated through user input via the command line.

(2) What are the assumptions and limitations of your solution

	One limitation is the GUI interface for the ADD panel. There is a gap in the interface when choosing between "book" or "electronic" product type.

	One assumption is with file input. If the user enters a commandline argument for the textfile, it is assumed that the first arguement is the textfile. Also, if the user enters nothing, a textfile is automatically generated for the user instead. 
	

(3) How can a user build and test your program (also called the user guide)

	The user must install the tool "Gradle",

	Compile:

			$ gradle build

	Test:

			$ gradle test

	Execute:

			$ gradle run --console=plain --args='<FILE_NAME>'  

(4) How is the program tested for correctness (i.e., the test plan should be part of the README file);

	Test Plan:

		In order to test for correctness from the user input, I split up each generic input into three cases.

			1. User enters a correct input, in range of what the program expects.
			2. User enters an incorrect input, out of range of what the program expects.
			3. User enters invalid input, where the type of data the program expects is incorrect (only applies to primitive data types)

				By accounting for these three scenarios, you can cover most, if not, all cases related to user input. 

				1) If the code was correct, the program would resume as usual, other wise, the input errors fell into the two cases below...
				2) To make sure no errors occured via input, I used the setters of the objects to return boolean values for valid/invalid values. This was used as a condition for a while loop, where the loop would only break if the input values were correct. This made sure only valid products were added to the product_list, and valid input filters used to search the product list. 
				3) I then used try & catch blocks to further error proof the I/O blocks of code if a user entered invalid input for a primitive data type. 

				These cases were accounted for by using JUnit test files and the grading scheme to account for additional cases. If errors did occur, the program would simply explain the reason why the input was incorrect/invalid, and ask the user to enter another input.

		In order to test for correctness from syntax errors, I used:

			1. A makefile, to make sure that I got no errors from the program outside of the gradle tool
			2. The Gradle tool, to find any missing suggestions the makefile might have missed

				By using both methods, I was able to make sure that no syntax errors occured. Also, I used the equalsIgnoreCase method for string comparisons to make sure values like Q, q, QUIT, quit, Quit were all acceptable inputs instead of creating cases for each scenario seperately.


		In order to test for correctness from logic, I used JUnit test cases to test for unexpected return values, and testing the commandline to make sure the correct run time error messages popped up. The following test files were created:
			1. ProductTest - To test generic product objects setter, getter, and instance methods
			2. BookTest and ElectronicsTest - To test objects that inherit attributes/methods from the product class + specialized variables/methods
			3. EStoreSearch - To test the products add functions and search filters
			4. FileIO -  to test if the program is able to load products from a textfile and store them back in the same textfile. 


(5) What possible improvements could be done if you were to do it again or have extra time available in future.
	
	Possibly additional test cases in the JUnit files and condensing the EStore class. 