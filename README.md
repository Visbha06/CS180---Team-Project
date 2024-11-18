# CS180---Team-Project
# --------------------------------
# Social Media Platform
## Phase 1
### 1. Who Submitted What
##### Rylee:
MessagesDatabaseInterface, UserDatabaseInterface, MessageInterface, PasswordExceptionInterface, UserAlreadyExistsExceptionInterface, ChatInterface, Class Message, and Class Chat.
##### Sathvik:
checkUsernameAndPassword, findUser, removeFriend, Block, Test cases for: checkUsernameAndPassword, findUser, removeFriend, Block, getUserData, getNewUserData, setUserData, and setNewUserData.
##### Brayden:
MessagesDataBase and the README.
##### Vishal:
ChatTest.java + interface
MessagesDatabaseTest.java + interface
MessageTest.java + interface
PasswordException and UserAlreadyExistsException + interfaces and tests
UserDatabase.java (with Sathvik) + interfaces and tests
UserInterface.java
UserTest.java + interface

### 2. Class Descriptions
#### Message-
The purpose of the message class is to create new messages for users. Message class objects take in two parameters user and message. The message class also contains to toString method to format user and message in the format [user: message]
the class also contains an equals method to check if two messages are the same and sent by the same user. This class works directly with the messagesDataBase class.
#### MessageTest-
The MessagesTest class contains test cases for the equals method and the toString method in the message class.
#### MessageDataBase-
The purpose of the messages database class is to store messages from all users. This class implements the MessagesDataBaseInterface. This class can find certain messages, delete them, restrict them to either messages from friends only or from all
users.
#### MessagesDataBaseTest-
The messagesDataBaseTest a class dedicated to testing the messagesDataBase. It includes multiple tests for every method present in messagesDataBase.
#### Chat-
The chat class is responsible for the communication between two users and sets up the means of communication between them. The chat class has an equals method which verifies that two users are communicating with each other.
#### ChatInterface-
The chat interface has the outline for chat class, which contains the outline for the equals method in the chat class.
#### ChatTest-
The chatTest class contains the tests for chatClass and tests that the equals method works as intended.
#### ChatTestInterface-
Contains the outline for the chatTest class equals test.
#### User-
The purpose of the user class is the creation of new User objects via the creation of a userName and password.
#### UserAlreadyExistsExceptionTest-
The userAlreadyExistsExceptionTest class is dedicated to testing the exception that is thrown when a newUser object is created with the same userName as another user.
#### UserDatabase-
The purpose of the userDataBase class is storing and editing the file containing the information of all the users. Including username, password, friends, and blocked users. This class implements the userDataBaseInterface which provides an outline for the class.
Some methods in the class can read/write to the database(text file). Other ones are checkUserNameAndPassword, createNewUser, findUser, addFriend, removeFriend, and block. All of which manipulate the userDatabase in different ways.
#### UserDatabaseTest-
The UserDataBaseTest class has multiple test cases for all the methods in the userDataBase class to ensure that every condition that the userDatabase could encounter is accounted for and works accordingly.
#### UserTest-
The UserTest class has test cases for the getUsernameAndPassword method as well as tests for the toString method to ensure it returns the correct thing.
#### PasswordExceptionTest-
The passwordExceptionTest class is for the testing of passwordException class. It tests to make sure the correct message is thrown. This exception is implemented in the userDataBase.
#### ChatTestInterface-
The chatTestInterface contains an outline for all the test case methods in the ChatTest class.
#### MessageDatabaseInterface-
The messageDatabaseInterface contains an outline for all the methods in the messageDataBase class.
#### MessageInterface-
The messageInterface class contains an outline for all the methods in the Message class.
#### MessagesDataBaseTestInterface-
The MessagesDataBaseTestInterface contains the method signatures for all the methods in the MessagesDatabaseTest.
#### MessageTestInterface-
The MessageTestInterface contains an outline for the methods present in the MessageTest class with makes sure the methods in the message class work correctly.
#### PasswordExceptionInterface-
The passwordExceptionInterface contains an outline for the passwordException exception.
#### PasswordExceptionTestInterface-
The passwordExceptionTestInterface contains an outline for the test cases for the passwordExceptionTest.
#### UserAlreadyExistsExceptionInterface-
The userAlreadyExistsExceptionInterface contains an outline for the userAlreadyExistsException.
#### UserAlreadyExistsExceptionTestInterface-
The UserAlreadyExistsExceptionTestInterface contains an outline for the UserAlreadyExistsExceptionTest class which tests that the UserAlreadyExistsExceptionTest works correctly.
#### UserDataBaseInterface-
The userDataBaseInterface contains an outline for the methods in the userDataBase class.
#### UserDatabaseTestInterface-
The userDatabaseTestInterface contains an outline for the methods in the userDataBaseTests class.
#### UserInterface-
The userInterface contains an outline for the methods in the user class.
#### UserTestInterface-
The userTestInterface contains an outline for the methods in the userTest class which has test cases for the methods in the user class.
#### PasswordException-
The passwordException class is a custom exception that is meant to be thrown when a password is under 7 characters long. There is a testing class for this exception that ensures that this exception is only thrown in the correct scenarios.
#### UserAlreadyExistsException-
The userAlreadyExists exception is a custom exception that is meant to be thrown when a user attempts to create a username that has already been created. There is a testing class for this exception that ensures that this exception is thrown in the correct scenarios.
## Phase 2
### 1. How to compile and run
-First compile the server using: javac Server.java
-Secondly run the server using: java Server
-Thirdly compile the client using: javac Client.java
-Fourthly run the client using: java Client
-Make sure to start running the server before the client
### 2. Who Submitted What
##### Rylee:
ServerInterface, ClientInterface, ServerTestInterface, ActionInterface, ActionTestInterface, made changes to Class Chat and ChatInterface, and the README.
##### Sathvik:
Client
##### Brayden:
Client, Server, and README.
##### Vishal:
Server, ServerTest, Action, and made changes to MessagesDatabase and MessagesDatabaseTest.

### 3. Class Descriptions
#### ServerInterface-
The ServerInterface class contains an outline for the methods in the server class consisting of the start, LoadUserData, and loadMessages methods and also a static final variables representing the port number
#### ClientInterface-
The client interface contains an outline for the methods and functionality in the client class.
#### ServerTestInterface-
The ServerTestInterface contains an outline for the methods designed to test the functionality of the ServerInterface
#### Server-
The server class is responsible for handling all the requests inputted by the user in the Client class and returning those results to the user.
#### Client-
The client class is responsible for taking user input and sending that input to the server class to execute the requested command.
#### ServerTest-
The purpose of the server test class is to make sure the client is to make sure that it is loading the correct user data and message data. This is to ensure that the server is able to send the correct data back to the client.
#### ActionInterface-
The action interface outlines the get value function in the action enum which returns and integer value based on what command is selected.
#### ActionTestInterface-
The action test interface outlines the test cases for the actionTest class.
#### Action-
The action class is a enum that is used for handling user input and processing user input with the given value. It's utilized in the client class for when the user is selecting what action they would like to perform.