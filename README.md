# CS180---Team-Project
# --------------------------------
# Social Media Platform 
## Phase 3
### 1. How to compile and run the project:
### 2. Who Submitted What
### 3. Class Descriptions
#### Database-
 The database class is implemented by the userDatabase and messagesDatabase classes. It provides an outline for two methods one which reads from a file and the other which writes to a file. 
#### Message-
The purpose of the message class is to create new messages for users. Message class objects take in two parameters user and message. The message class also contains to toString method to format user and message in the format [user: message]
the class also contains a equals method to check if two messages are the same and sent by the same user. This class works directly with the messagesDataBase class.
#### MessagesDataBase-
The purpose of the messages database class is to store messages from all users. This class implements the database interface so it can read and write to a messagesDatabase file. This class can find certain messages, delete them, restrict them to either messages from friends only or from all
users.
#### MessagesDataBaseTest-


