# CS180---Team-Project
# --------------------------------
# Social Media Platform 
## Phase 1
### 1. How to compile and run the project:
### 2. Who Submitted What
### 3. Class Descriptions
#### Database-
 The database class is implemented by the userDatabase and messagesDatabase classes. It provides an outline for two methods one which reads from a file and the other which writes to a file. 
#### Message-
The purpose of the message class is to create new messages for users. Message class objects take in two parameters user and message. The message class also contains to toString method to format user and message in the format [user: message]
the class also contains a equals method to check if two messages are the same and sent by the same user. This class works directly with the messagesDataBase class.
#### MessageTest-
The MessagesTest class contains test cases for the equals method and the toString method in the message class.
#### MessageInterface-
The messageInterfacew contains an 
#### MessageDataBase-
The purpose of the messages database class is to store messages from all users. This class implements the database interface so it can read and write to a messagesDatabase file. This class can find certain messages, delete them, restrict them to either messages from friends only or from all
users.
#### MessagesDataBaseInterface-
Contains an outline for the methods in the messagesDataBase class.
#### MessagesDataBaseTest-
The messagesDataBaseTest a class dedicated to testing the messagesDataBase. It includes multiple tests for every method present in messagesDataBase.
#### Chat-
The chat class is responsible for the communication between two users and sets up the means of communication between them. The chat class has an equals method which verifies that two users are communicating with each other.
#### ChatInterface-
The chat interface has the outline for chat class, which contains the outline for the equals method in the chat class.
#### ChatTest-
The chatTest class contains the tests for chatClass and tests that the equals method works as it should.
#### ChatTestInterface-
Contains the outline for the chatTest class equals test.
#### User-
The purpose of the user class is the creation of new User objects via the creation of a userName and password. 
#### UserAlreadyExistsExceptionTest-
The userAlreadyExistsExceptionTest class is dedicated to testing the exception that is thrown when a newUser object is created with the same userName as another user.
#### UserDatabase-
The purpose of the userDataBase class is storing and editing the file containing the information of all the users. Including username, password, friends, and blocked users. This class implements the userDataBaseInterface which provides an outline for the class. 
Some methods in the class can read/write to the database(text file). Other ones are checkUserNameAndPassword, createNewUser, findUser, addFriend, removeFriend, and block. All of which manipulate the userDatabase. 
#### UserDatabaseTest-
The UserDataBaseTest class has multiple test cases for all the methods in the userDataBase class
#### UserTest-
The UserTest class has test cases for all the methods in User.
#### PasswordExceptionTest-
The passwordExceptionTest class is for the testing of passwordException class. It tests to make sure the correct message is thrown. This exception is implemented in the userDataBase.

