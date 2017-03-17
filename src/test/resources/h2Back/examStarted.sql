;             
CREATE USER IF NOT EXISTS SA SALT 'e8ca4f0b128b2cc2' HASH 'ff3cc0de44dd3e6fdb6f99cc7c9cd67af7eafa8df955cd80b014262654ac0962' ADMIN;           
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_8A7F51B7_86E5_4239_92CD_3ED538FECAA5 START WITH 11 BELONGS_TO_TABLE;   
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_5939AFC6_FE14_4215_A418_43D62A5BD6AA START WITH 54 BELONGS_TO_TABLE;   
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_12D9E87E_6CB3_49FC_A1BB_63D4D3E9397D START WITH 3 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_8410E7A3_4DA9_4276_B30A_37A167416CE9 START WITH 5 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_B9997E3B_2F38_4037_A27A_15103515F46C START WITH 9 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_255E1EEF_BA7F_4835_BEBD_E4411D7E3F1C START WITH 6 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_63187D30_3EEA_4863_AD3A_BC4A26F14926 START WITH 51 BELONGS_TO_TABLE;   
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_FD646D0B_DF8F_45A4_8192_8E9B814E7A7B START WITH 3 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_5A9465A6_AECC_448C_979E_28F4F2AF1D6A START WITH 6 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_7E3D0B59_A19D_49F1_8700_62D97C7D9670 START WITH 1 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_011C9FD1_8469_4657_B043_00F9E16BCFF5 START WITH 6 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_22F42A3E_4B3F_4BA1_B0B0_1A104D8BF0E1 START WITH 2 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_40A87901_DB18_4131_BF2A_32A1999C9428 START WITH 1 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_672F85EF_F02B_4CA9_B292_692F091F5AFD START WITH 1 BELONGS_TO_TABLE;    
CREATE MEMORY TABLE PUBLIC.USER(
    _ID VARCHAR(36) NOT NULL,
    NAME VARCHAR(50) NOT NULL,
    SURNAME VARCHAR(50) NOT NULL,
    LOGIN VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(50) NOT NULL
);    
ALTER TABLE PUBLIC.USER ADD CONSTRAINT PUBLIC.CONSTRAINT_2 PRIMARY KEY(_ID);  
-- 8 +/- SELECT COUNT(*) FROM PUBLIC.USER;    
INSERT INTO PUBLIC.USER(_ID, NAME, SURNAME, LOGIN, PASSWORD) VALUES
('3ca33b4f-009a-4403-829b-e2d20b3d47c2', 'Bob', 'Smith', 'sampleMarker', 'pass'),
('fba6a561-8999-4b19-9c57-232895d024c6', 'Grzegorz', 'Brzenczyszczykiewicz', 'sampleAuthor', 'pass'),
('94cbbbc4-f94d-40d2-b0cf-e642eb36e73a', 'Sam', 'Armstrong', 'sampleAdmin', 'pass'),
('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Jack', 'Brown', 'sampleAll', 'pass'),
('95818d99-492d-4c50-80b8-abae310bd2f3', 'Matthew', 'Collison', 'matthew.collison@ncl.ac.uk', 'pass'),
('92f6e08a-2dba-467c-96e8-a1ec1c87940b', 'John', 'Colquhoun', 'john.colquhoun@newcastle.ac.uk', 'pass'),
('1be448ff-1a2e-456f-9594-4042e7ef6ab2', 'Steve', 'Riddle', ' steve.riddle@newcastle.ac.uk', 'pass'),
('045d785e-cc44-4e7e-89b8-2df505c0b72a', 'Lindsay', 'Marshall', 'Lindsay.Marshall@newcastle.ac.uk', 'pass');   
CREATE MEMORY TABLE PUBLIC.ROLE(
    REFERENCENAME VARCHAR(50) NOT NULL
);  
ALTER TABLE PUBLIC.ROLE ADD CONSTRAINT PUBLIC.CONSTRAINT_26 PRIMARY KEY(REFERENCENAME);       
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.ROLE;    
INSERT INTO PUBLIC.ROLE(REFERENCENAME) VALUES
('Author'),
('Marker'),
('Admin'),
('ModuleLeader');        
CREATE MEMORY TABLE PUBLIC.USERROLE(
    USERID VARCHAR(36) NOT NULL,
    ROLEID VARCHAR(50) NOT NULL
);   
ALTER TABLE PUBLIC.USERROLE ADD CONSTRAINT PUBLIC.CONSTRAINT_1 PRIMARY KEY(USERID, ROLEID);   
-- 19 +/- SELECT COUNT(*) FROM PUBLIC.USERROLE;               
INSERT INTO PUBLIC.USERROLE(USERID, ROLEID) VALUES
('3ca33b4f-009a-4403-829b-e2d20b3d47c2', 'Marker'),
('fba6a561-8999-4b19-9c57-232895d024c6', 'Author'),
('94cbbbc4-f94d-40d2-b0cf-e642eb36e73a', 'Admin'),
('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Marker'),
('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Author'),
('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Admin'),
('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'ModuleLeader'),
('95818d99-492d-4c50-80b8-abae310bd2f3', 'Marker'),
('95818d99-492d-4c50-80b8-abae310bd2f3', 'Author'),
('95818d99-492d-4c50-80b8-abae310bd2f3', 'ModuleLeader'),
('92f6e08a-2dba-467c-96e8-a1ec1c87940b', 'Marker'),
('92f6e08a-2dba-467c-96e8-a1ec1c87940b', 'Author'),
('92f6e08a-2dba-467c-96e8-a1ec1c87940b', 'ModuleLeader'),
('1be448ff-1a2e-456f-9594-4042e7ef6ab2', 'Marker'),
('1be448ff-1a2e-456f-9594-4042e7ef6ab2', 'Author'),
('1be448ff-1a2e-456f-9594-4042e7ef6ab2', 'ModuleLeader'),
('045d785e-cc44-4e7e-89b8-2df505c0b72a', 'Marker'),
('045d785e-cc44-4e7e-89b8-2df505c0b72a', 'Author'),
('045d785e-cc44-4e7e-89b8-2df505c0b72a', 'ModuleLeader'); 
CREATE MEMORY TABLE PUBLIC.MODULE(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_8A7F51B7_86E5_4239_92CD_3ED538FECAA5) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_8A7F51B7_86E5_4239_92CD_3ED538FECAA5,
    MODULECODE VARCHAR(6) NOT NULL,
    REFERENCENAME VARCHAR(50) NOT NULL
);          
ALTER TABLE PUBLIC.MODULE ADD CONSTRAINT PUBLIC.CONSTRAINT_8 PRIMARY KEY(_ID);
-- 10 +/- SELECT COUNT(*) FROM PUBLIC.MODULE; 
INSERT INTO PUBLIC.MODULE(_ID, MODULECODE, REFERENCENAME) VALUES
(1, 'CSC001', 'Sample Module 1'),
(2, 'CSC002', 'Sample Module 2'),
(3, 'CSC003', 'Sample Module 3'),
(4, 'CSC004', 'Sample Module 4'),
(5, 'CSC005', 'Sample Module 5'),
(6, 'CSC006', 'Sample Module 6'),
(7, 'CSC007', 'Sample Module 7'),
(8, 'CSC008', 'Sample Module 8'),
(9, 'CSC009', 'Sample Module 9'),
(10, 'CSC010', 'Sample Module 10');              
CREATE MEMORY TABLE PUBLIC.MODULELEADER(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_011C9FD1_8469_4657_B043_00F9E16BCFF5) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_011C9FD1_8469_4657_B043_00F9E16BCFF5,
    USERID VARCHAR(36) NOT NULL,
    MODULEID INT NOT NULL
);    
ALTER TABLE PUBLIC.MODULELEADER ADD CONSTRAINT PUBLIC.CONSTRAINT_F PRIMARY KEY(_ID);          
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.MODULELEADER;            
INSERT INTO PUBLIC.MODULELEADER(_ID, USERID, MODULEID) VALUES
(1, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 1),
(2, '95818d99-492d-4c50-80b8-abae310bd2f3', 2),
(3, '92f6e08a-2dba-467c-96e8-a1ec1c87940b', 3),
(4, '1be448ff-1a2e-456f-9594-4042e7ef6ab2', 4),
(5, '045d785e-cc44-4e7e-89b8-2df505c0b72a', 5);            
CREATE MEMORY TABLE PUBLIC.TESTDAY(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_12D9E87E_6CB3_49FC_A1BB_63D4D3E9397D) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_12D9E87E_6CB3_49FC_A1BB_63D4D3E9397D,
    DATE VARCHAR(50) NOT NULL,
    STARTTIME VARCHAR(5),
    ENDTIME VARCHAR(5),
    ENDTIMEWITHEXTRATIME VARCHAR(5),
    LOCATION VARCHAR(350)
); 
ALTER TABLE PUBLIC.TESTDAY ADD CONSTRAINT PUBLIC.CONSTRAINT_D PRIMARY KEY(_ID);               
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.TESTDAY; 
INSERT INTO PUBLIC.TESTDAY(_ID, DATE, STARTTIME, ENDTIME, ENDTIMEWITHEXTRATIME, LOCATION) VALUES
(1, '22/02/2017', '12:00', '14:30', '16:00', 'Leeds Office'),
(2, '17/03/2017', '19:07', '20:07', '20:37', 'Sample Location');             
CREATE MEMORY TABLE PUBLIC.CANDIDATE(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_5A9465A6_AECC_448C_979E_28F4F2AF1D6A) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_5A9465A6_AECC_448C_979E_28F4F2AF1D6A,
    NAME VARCHAR(50) NOT NULL,
    SURNAME VARCHAR(50) NOT NULL,
    HASEXTRATIME BOOL NOT NULL
); 
ALTER TABLE PUBLIC.CANDIDATE ADD CONSTRAINT PUBLIC.CONSTRAINT_C PRIMARY KEY(_ID);             
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.CANDIDATE;               
INSERT INTO PUBLIC.CANDIDATE(_ID, NAME, SURNAME, HASEXTRATIME) VALUES
(1, 'Mercedes', 'Fedya', TRUE),
(2, 'Stig', 'Ivan', FALSE),
(3, 'Reva', 'Mihajlo', TRUE),
(4, 'Vance', 'Bernie', FALSE),
(5, 'Franciszek', 'Geglula', FALSE);      
CREATE MEMORY TABLE PUBLIC.TESTPAPER(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_8410E7A3_4DA9_4276_B30A_37A167416CE9) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_8410E7A3_4DA9_4276_B30A_37A167416CE9,
    REFERENCENAME VARCHAR(150) NOT NULL,
    TIMEALLOWED INT
);     
ALTER TABLE PUBLIC.TESTPAPER ADD CONSTRAINT PUBLIC.CONSTRAINT_6 PRIMARY KEY(_ID);             
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.TESTPAPER;               
INSERT INTO PUBLIC.TESTPAPER(_ID, REFERENCENAME, TIMEALLOWED) VALUES
(1, 'ALTERNATE INTERVIEW JAVA', 60),
(2, 'Interview Test-Graduate (C#)', 60),
(3, 'Sample Multiple Choice Auto-Marking Test', 20),
(4, 'Selenium Test Paper', 60);   
CREATE MEMORY TABLE PUBLIC.TESTPAPERVERSION(
    AUTHORID VARCHAR(36),
    TESTPAPERID INT NOT NULL,
    INSTRUCTIONSTEXT VARCHAR(1024),
    VERSIONNUMBER INT NOT NULL
);               
ALTER TABLE PUBLIC.TESTPAPERVERSION ADD CONSTRAINT PUBLIC.CONSTRAINT_C2 PRIMARY KEY(TESTPAPERID, VERSIONNUMBER);              
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.TESTPAPERVERSION;        
INSERT INTO PUBLIC.TESTPAPERVERSION(AUTHORID, TESTPAPERID, INSTRUCTIONSTEXT, VERSIONNUMBER) VALUES
('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 1, '<b>Normal time Allowed: Up to 1 Hour </br>(all times indicative only)</b></br>Your interviewer will idicate: </br><ul><li> which questions of this set you should complete. The selected set will be relvant to your knowledge and experience</li><li>the time that you have to complete these in.</li></ul></br>The quelity / correctness of your answers is more important than the ammount of quetions answered. A timescale is provided with each question as a rough guide for how long the question should take to complete.', 1),
('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 2, '<b>Normal time Allowed: Up to 60 Minutes </br>(all times indicative only)</b></br>Answer as many questions as you can. If you are unsure, it is better to skip the question completely rather than provide an incorrect answer. An indicative time is provided with each question as a rough guide for how long the question should take to complete.', 1),
('fba6a561-8999-4b19-9c57-232895d024c6', 3, 'Sample multiple choice', 1),
('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 4, '<p>This test paper has been generated by selenium</p>', 1);      
CREATE MEMORY TABLE PUBLIC.TESTPAPERSECTION(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_B9997E3B_2F38_4037_A27A_15103515F46C) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_B9997E3B_2F38_4037_A27A_15103515F46C,
    REFERENCENAME VARCHAR(150)
);             
ALTER TABLE PUBLIC.TESTPAPERSECTION ADD CONSTRAINT PUBLIC.CONSTRAINT_23 PRIMARY KEY(_ID);     
-- 8 +/- SELECT COUNT(*) FROM PUBLIC.TESTPAPERSECTION;        
INSERT INTO PUBLIC.TESTPAPERSECTION(_ID, REFERENCENAME) VALUES
(1, 'ALTERNATE INTERVIEW JAVA QUESTIONS'),
(2, 'C# Language'),
(3, 'Problem Solving'),
(4, 'Architecture and Theory'),
(5, 'Written Communication'),
(6, 'Test Case Design'),
(7, 'Multiple Choice'),
(8, 'Selenium Section');         
CREATE MEMORY TABLE PUBLIC.TESTPAPERSECTIONVERSION(
    TESTPAPERSECTIONID INT NOT NULL,
    VERSIONNUMBER INT NOT NULL,
    NOOFQUESTIONSTOANSWER INT,
    SECTIONDESCRIPTION TEXT(65536),
    TIMESCALE INT
);        
ALTER TABLE PUBLIC.TESTPAPERSECTIONVERSION ADD CONSTRAINT PUBLIC.CONSTRAINT_5C PRIMARY KEY(TESTPAPERSECTIONID, VERSIONNUMBER);
-- 8 +/- SELECT COUNT(*) FROM PUBLIC.TESTPAPERSECTIONVERSION; 
INSERT INTO PUBLIC.TESTPAPERSECTIONVERSION(TESTPAPERSECTIONID, VERSIONNUMBER, NOOFQUESTIONSTOANSWER, SECTIONDESCRIPTION, TIMESCALE) VALUES
(1, 1, 14, NULL, 60),
(2, 1, 8, NULL, 22),
(3, 1, 2, NULL, 10),
(4, 1, 1, NULL, 10),
(5, 1, 1, NULL, 8),
(6, 1, 1, NULL, 10),
(7, 1, 10, NULL, 20),
(8, 1, 6, '<p>Sample selenium section</p>', 60);       
CREATE MEMORY TABLE PUBLIC.TESTPAPERSECTIONVERSIONENTRY(
    TESTPAPERSECTIONVERSIONNO INT NOT NULL,
    TESTPAPERSECTIONID INT NOT NULL,
    TESTPAPERVERSIONNO INT NOT NULL,
    TESTPAPERID INT NOT NULL,
    REFERENCENUMBER INT
); 
ALTER TABLE PUBLIC.TESTPAPERSECTIONVERSIONENTRY ADD CONSTRAINT PUBLIC.CONSTRAINT_F5D4 PRIMARY KEY(TESTPAPERSECTIONVERSIONNO, TESTPAPERSECTIONID, TESTPAPERVERSIONNO, TESTPAPERID);            
-- 8 +/- SELECT COUNT(*) FROM PUBLIC.TESTPAPERSECTIONVERSIONENTRY;            
INSERT INTO PUBLIC.TESTPAPERSECTIONVERSIONENTRY(TESTPAPERSECTIONVERSIONNO, TESTPAPERSECTIONID, TESTPAPERVERSIONNO, TESTPAPERID, REFERENCENUMBER) VALUES
(1, 1, 1, 1, 1),
(1, 2, 1, 2, 1),
(1, 3, 1, 2, 2),
(1, 4, 1, 2, 3),
(1, 5, 1, 2, 4),
(1, 6, 1, 2, 5),
(1, 7, 1, 3, 1),
(1, 8, 1, 4, 1);       
CREATE MEMORY TABLE PUBLIC.QUESTIONTYPE(
    REFERENCENAME VARCHAR(150) NOT NULL
);         
ALTER TABLE PUBLIC.QUESTIONTYPE ADD CONSTRAINT PUBLIC.CONSTRAINT_7 PRIMARY KEY(REFERENCENAME);
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.QUESTIONTYPE;            
INSERT INTO PUBLIC.QUESTIONTYPE(REFERENCENAME) VALUES
('Code'),
('Essay'),
('Multiple Choice'),
('Drawing'),
('Expression');             
CREATE MEMORY TABLE PUBLIC.QUESTION(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_5939AFC6_FE14_4215_A418_43D62A5BD6AA) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_5939AFC6_FE14_4215_A418_43D62A5BD6AA,
    LANGUAGE VARCHAR(150),
    DIFFICULTY INT NOT NULL,
    REFERENCENAME VARCHAR(150),
    QUESTIONTYPEID VARCHAR(150)
);         
ALTER TABLE PUBLIC.QUESTION ADD CONSTRAINT PUBLIC.CONSTRAINT_E9 PRIMARY KEY(_ID);             
-- 53 +/- SELECT COUNT(*) FROM PUBLIC.QUESTION;               
INSERT INTO PUBLIC.QUESTION(_ID, LANGUAGE, DIFFICULTY, REFERENCENAME, QUESTIONTYPEID) VALUES
(1, 'Java', 5, 'Develop isEven()', 'Code'),
(2, 'Java', 5, 'Develop isDateInvalid()', 'Code'),
(3, 'Java', 5, 'Ask about output of class NewTester', 'Essay'),
(4, 'Java', 5, 'Ask about output of class NewTester', 'Essay'),
(5, 'Java', 5, 'Ask about output of test1 and test2', 'Essay'),
(6, 'Java', 5, 'Explain output of class NewTester', 'Essay'),
(7, 'Java', 5, 'Develop singleton design pattern', 'Code'),
(8, 'Java', 5, 'Explain array index out of bounds', 'Essay'),
(9, 'Java', 5, 'Explain a switch', 'Essay'),
(10, 'Java', 5, 'Explain double equality problem', 'Essay'),
(11, 'Java', 5, 'Explain class ValHold', 'Essay'),
(12, 'Java', 5, 'Explain a thread safety', 'Essay'),
(13, 'Java', 5, 'Identify compilation errors', 'Essay'),
(14, 'Java', 5, 'Explain appearance of a frame', 'Essay'),
(15, 'C#', 1, 'Access a private variable', 'Multiple Choice'),
(16, 'C#', 1, 'Access a variable withoud modifier', 'Multiple Choice'),
(17, 'C#', 1, 'Truth about deadlocking', 'Multiple Choice'),
(18, 'C#', 1, 'Storing key/value pairs', 'Multiple Choice'),
(19, 'C#', 5, 'Null reference exception', 'Essay'),
(20, 'C#', 5, 'Output of tester class', 'Essay'),
(21, 'C#', 3, 'Compilation error', 'Essay'),
(22, 'C#', 5, 'Compilation error', 'Essay'),
(23, 'C#', 10, 'Common array elements', 'Code'),
(24, 'C#', 5, 'Develop isOdd', 'Code'),
(25, 'C#', 5, 'Objected Orientated Design', 'Drawing'),
(26, NULL, 10, 'Singleton pattern', 'Essay'),
(27, NULL, 10, 'Sql products and invoices', 'Essay'),
(28, NULL, 8, 'Functional specification of a login dialog', 'Essay'),
(29, NULL, 8, 'Recursion on contents of array', 'Code'),
(30, NULL, 5, 'Test date validity', 'Code'),
(31, NULL, 5, 'Test static website', 'Essay'),
(32, NULL, 1, 'Sample multiple choice 1', 'Multiple Choice'),
(33, NULL, 1, 'Sample multiple choice 2', 'Multiple Choice'),
(34, NULL, 1, 'Sample multiple choice 3', 'Multiple Choice'),
(35, NULL, 1, 'Sample multiple choice 4', 'Multiple Choice'),
(36, NULL, 1, 'Sample multiple choice 5', 'Multiple Choice'),
(37, NULL, 1, 'Sample multiple choice 6', 'Multiple Choice'),
(38, NULL, 1, 'Sample multiple choice 7', 'Multiple Choice'),
(39, NULL, 1, 'Sample multiple choice 8', 'Multiple Choice'),
(40, NULL, 1, 'Sample multiple choice 9', 'Multiple Choice'),
(41, NULL, 1, 'Sample multiple choice 10', 'Multiple Choice'),
(42, 'Java', 10, 'Selenium Java Question', 'Code'),
(43, 'C#', 15, 'Selenium C# Question', 'Essay'),
(44, '', 5, 'Selenium Multiple Choice Question 1', 'Multiple Choice'),
(45, '', 5, 'Selenium Multiple Choice Question 2', 'Multiple Choice'),
(46, '', 5, 'Selenium Multiple Choice Question 3', 'Multiple Choice'),
(47, '', 50, 'Selenium Drawing Question', 'Drawing'),
(48, '', 50, 'Selenium Expression Question 1', 'Expression'),
(49, '', 50, 'Selenium Expression Question 2', 'Expression'),
(50, '', 50, 'Selenium Expression Question 3', 'Expression'),
(51, '', 50, 'Selenium Expression Question 4', 'Expression'),
(52, '', 50, 'Selenium Expression Question 5', 'Expression'),
(53, '', 50, 'Selenium Expression Question 6', 'Expression');              
CREATE MEMORY TABLE PUBLIC.QUESTIONVERSION(
    VERSIONNUMBER INT NOT NULL,
    QUESTIONID INT NOT NULL,
    TEXT VARCHAR(2048),
    CORRECTANSWER VARCHAR(2048),
    MARKINGGUIDE VARCHAR(2048),
    TIMESCALE INT
); 
ALTER TABLE PUBLIC.QUESTIONVERSION ADD CONSTRAINT PUBLIC.CONSTRAINT_607 PRIMARY KEY(VERSIONNUMBER, QUESTIONID);               
-- 40 +/- SELECT COUNT(*) FROM PUBLIC.QUESTIONVERSION;        
INSERT INTO PUBLIC.QUESTIONVERSION(VERSIONNUMBER, QUESTIONID, TEXT, CORRECTANSWER, MARKINGGUIDE, TIMESCALE) VALUES
(1, 1, 'You have been asked to develop a function called IsEven that return true if a given integer parameter is even, or false if odd. Write this function below.', NULL, NULL, 5),
(1, 15, 'Which classes can access a variable declared as private? </br> A) All classes. </br> B) Within the body of the class that encloses the declaration. </br> C)Inheriting sub classes. </br> D) Classes in the same namespace.', 'B', NULL, 1),
(1, 16, 'Which classes can access a variable with no access modifier? </br> A) All classes.</br> B) Within the body of the class that encloses the declaration.</br> C) Inheriting sub classes</br> D) Classes in the same namespace.', 'B', NULL, 1),
(1, 17, 'Which of the following statement is true about deadlocking? </br>A) It is not possible for more than two threads to deadlock at once.</br>B) Managed code language such as Java or C# guarantee that threads cannot enter a deadlocked state.</br>C) It is possible for a single threaded application to deadlock if synchronized blocks are used incorrectly.</br>D) None of the above.', 'C', NULL, 1),
(1, 18, 'Which of the following C# objects is most suitable for storing general key/value pairs? </br>A) Dictionary</br>B) List</br>C) HashSet</br>D) IEnumerable', 'A', NULL, 1),
(1, 19, 'Given the following fragment of C# code what will be happen?</br><pre class="prettyprint">bool flag = true;<br />Console.WriteLine( "0");<br />try {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( "1");<br />&nbsp;&nbsp;&nbsp; if (flag) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; object o = null;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; o.ToString();<br />&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( "2");<br />}<br />catch (NullReferenceException ex) {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( "3");<br />&nbsp;&nbsp;&nbsp; throw new ArgumentException( "", ex);<br />}<br />catch (Exception ex) {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( "4");<br />}<br />finally {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( "5");<br />}<br />Console.WriteLine( "6");</pre>', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 5),
(1, 20, 'What will be the output of the following code?</br><pre class="prettyprint">public class Tester {<br />&nbsp;&nbsp;&nbsp; private int t = 1;<br />&nbsp;&nbsp;&nbsp; private static int p = 1;<br /><br />&nbsp;&nbsp;&nbsp; static void main(string[]args) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; for (int counter = 0; counter &lt; 5; counter++) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Tester tester = new Tester();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; tester.test();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; public void test() {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine("T " + t + " " + p);<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; t++;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; p++;<br />&nbsp;&nbsp;&nbsp; }<br />}</pre>', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 5);               
INSERT INTO PUBLIC.QUESTIONVERSION(VERSIONNUMBER, QUESTIONID, TEXT, CORRECTANSWER, MARKINGGUIDE, TIMESCALE) VALUES
(1, 21, 'What will happen when you attempt to compile and run the following code?<pre class="prettyprint">public class StringHolder {<br /><br />&nbsp;&nbsp;&nbsp; public StringHolder(string value) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Value = value;<br />&nbsp;&nbsp;&nbsp; }<br /><br />&nbsp;&nbsp;&nbsp; public string Value {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; get;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; set;<br />&nbsp;&nbsp;&nbsp; }<br />}<br /><br />public class EqualityExample {<br /><br />&nbsp;&nbsp;&nbsp; static void Main(string[]args) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var s = new StringHolder("Marcus");<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var s2 = new StringHolder("Marcus");<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if (s == s2) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine("we have a match");<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; } else {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine("not equal");<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; }<br />}</pre>', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 3),
(1, 22, 'Given the following code what will be the output?<pre class="prettyprint">public class Example {<br />&nbsp;&nbsp;&nbsp; public static void main(string[]args) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Example example = new Example();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; example.Method1();<br />&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; public void Method1() {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; int i = 99;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ValueHolder vh&nbsp;&nbsp; = new ValueHolder();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; vh.i = 30;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Method2(vh, i);<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine(vh.i);<br />&nbsp;&nbsp;&nbsp; }<br /><br />&nbsp;&nbsp;&nbsp; public void Method2(ValueHolder v, int i) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; i&nbsp;&nbsp; = 0;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; v.i = 20;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ValueHolder vh = new ValueHolder();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; v&nbsp; = vh;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine(v.i + " " + i);<br />&nbsp;&nbsp;&nbsp; }<br /><br />&nbsp;&nbsp;&nbsp; class ValueHolder {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public int i = 10;<br />&nbsp;&nbsp;&nbsp; }<br /><br />}</pre>', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 5),
(1, 23, 'You have two arrays:</br>int[] = new int[]{ 1, 2, 3, 5, 4 };</br>int[] = new int[]{ 3, 2, 9, 3, 7 };</br>Write a method that returns a collection of common elements.</br>Please note that the arrays can contain repeating elements, and are not in any particular order.</br>Complete the method in the space below. Any necessary variables should be shown.</br>Extra credit will be awarded for solutions that are efficient (let''s say given very large input arrays).</br>Extra credit will be awarded if the method can handle any arbitrary number of arrays, i.e. a,b,c</br>', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 10);          
INSERT INTO PUBLIC.QUESTIONVERSION(VERSIONNUMBER, QUESTIONID, TEXT, CORRECTANSWER, MARKINGGUIDE, TIMESCALE) VALUES
(1, 24, 'You have been asked to develop a function called IsOdd that returns true if a given integer parameter is odd, or false if even. Write this function below.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 5),
(1, 25, 'The classes shown in Error! Reference source not found. have been identified within the domain space for an internet electrical shopping site that deliver their goods in distinctive brightly coloured lorries and vans.Use object orientated theory to make the domain objects more efficient for software implementation.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 5),
(1, 26, 'a) This is the singleton pattern, what is it commonly used for?</br>b) If there are errors, fix them in the code snippet.</br>c) Please comment on any strengths or weaknesses of the above implementation. For instance, are there cases were it doesn''t guarantee only one instance is created?</br><pre class="prettyprint">class Singleton {<br />&nbsp;&nbsp; &nbsp;Singleton();<br /><br />&nbsp;&nbsp; &nbsp;private Singleton mInstance = null;<br /><br />&nbsp;&nbsp; &nbsp;public static Singleton instance() {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (mInstance == null) {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;mInstance == new Singleton();<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return mInstance;<br />&nbsp;&nbsp; &nbsp;}<br />}</pre>', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 10),
(1, 27, 'The questions section are based on the schema definitions defined below.</br><pre class="prettyprint lang-sql">CREATE TABLE Products(ProductId integer PRIMARY KEY, ProductName varchar(100));<br />CREATE TABLE Invoices(InvoiceNumber integer PRIMARY KEY, ProductId integer, InvoiceDate datetime, InvoiceCost decimal(6, 2)InvoiceComment varchar(200));</pre>a) What is the purpose of the following SQL statement:</br><pre class="prettyprint lang-sql">SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId;</pre>b) The application programs using the tables allow a moduleLeader to find an invoice by date and time range using a select statement of the form:</br><pre class="prettyprint lang-sql">SELECT InvoiceNumber, InvoiceCost FROM Invoices WHERE InvoiceDate &gt;= &lsquo;2000/05/23 15:00:00&rsquo; AND InvoiceDate &lt; &lsquo;2000/05/23 16:00:00&rsquo;;</pre>c) However as the Invoices table grew larger the execution times of these queries increased.Describe a change to the Database schema that would decrease the query execution time.</br>d) A test has been written to validate the query from question 2.1. The pseudo-code is:<pre class="prettyprint lang-sql">Connect to the database <br />FirstResult = Execute Query(&ldquo;SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId&rdquo;) <br />Add a new invoice &nbsp;<br />SecondResult = Execute Query(&ldquo;SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId&rdquo;) <br />Check that FirstResult and SecondResult differ by the added amount</pre> </br>But on a shared database the test keeps failing because someone else was running the same test so the second query picked up two invoices, what can we do to avoid this problem?</br>e)Write a query to return the product Name, number of the product sold and the highest price paid for it?', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 10);     
INSERT INTO PUBLIC.QUESTIONVERSION(VERSIONNUMBER, QUESTIONID, TEXT, CORRECTANSWER, MARKINGGUIDE, TIMESCALE) VALUES
(1, 28, 'Write a short functional specification of a typical login dialog (as seen in <b>Error! Reference source not found.</b>)', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 8),
(1, 29, 'A) Write a short paragraph describing the concept of recursion.</br>B) Write a simple recursive method to print out the contents of an array</br>', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 8),
(1, 30, 'The following code checks the validity of a date (which is passed in as 2 integer parameters). The code is looking to check the validity of all the days of the year, design the test data necessary to fully test this code. Note you do not need to consider leap years in your answer. <pre class="prettyprint">public class DateValidator<br />{<br />&nbsp;&nbsp; &nbsp;private static int daysInMonth [12] =&nbsp; {31,28,31,30,31,30,31,31,30,31,30,31};<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;public static bool validate(int day, int month)<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;{&nbsp;&nbsp;&nbsp; &nbsp;<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; if (month&gt;=1 &amp;&amp; month &lt;= 12 &amp;&amp; day &gt;=1 &amp;&amp; day &lt;=daysInMonth[month-1]) <br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return true:<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; else {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return false;<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp; &nbsp;}<br />}</pre>', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 5),
(1, 31, 'What tests can be executed against a web site that has static pages?  For example, spell checking text or verifying image downloads.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.', NULL, 5);            
INSERT INTO PUBLIC.QUESTIONVERSION(VERSIONNUMBER, QUESTIONID, TEXT, CORRECTANSWER, MARKINGGUIDE, TIMESCALE) VALUES
(1, 32, 'Correct answer is B A) Some text <br/>B) Some text <br/>C) Some text <br/>', 'B', NULL, 1),
(1, 33, 'Correct answer is A A) Some text <br/>B) Some text <br/>C) Some text <br/>', 'A', NULL, 1),
(1, 34, 'Correct answer is C A) Some text <br/>B) Some text <br/>C) Some text <br/>', 'C', NULL, 1),
(1, 35, 'Correct answer is A A) Some text <br/>B) Some text <br/>C) Some text <br/>', 'A', NULL, 1),
(1, 36, 'Correct answer are A,B A) Some text <br/>B) Some text <br/>C) Some text <br/>', 'A,B', NULL, 1),
(1, 37, 'Correct answer are A,B,C A) Some text <br/>B) Some text <br/>C) Some text <br/>', 'A,B,C', NULL, 1),
(1, 38, 'Correct answer are A,B,C A) Some text <br/>B) Some text <br/>C) Some text <br/>', 'A,B,C', NULL, 1),
(1, 39, 'Correct answer is B A) Some text <br/>B) Some text <br/>C) Some text <br/>', 'B', NULL, 1),
(1, 40, 'Correct answer is A A) Some text <br/>B) Some text <br/>C) Some text <br/>', 'A', NULL, 1),
(1, 41, 'Correct answer is C A) Some text <br/>B) Some text <br/>C) Some text <br/>', 'C', NULL, 1),
(1, 42, '<p><em><u>This question was designed by selenium</u></em></p>', '', '<p>Sample marking guide.</p>', 15),
(1, 43, '<p><em><u>This question was designed by selenium</u></em></p>', '', '<p>Sample marking guide.</p>', 25),
(1, 47, '<p><em><u>This question was designed by selenium</u></em></p>', '', '<p>Sample marking guide.</p>', 10),
(1, 44, '<p><em><u>This question was designed by selenium</u></em></p><p><em><u>A) This answer gives you 1 Mark</u></em></p><p><em><u>B) This answer gives you 2 Marks</u></em></p><p><em><u>C)This answer gives you 0 marksD)This answer gives you 4 marks.</u></em></p>', '{"0":"\\b([C])\\b","1":"\\b([A])\\b","2":"\\b([B])\\b","4":"\\b([D])\\b"}', '<p>Sample marking guide.</p>', 1),
(1, 45, STRINGDECODE('<p><em><u>This question was designed by selenium</u></em></p><p><em><u>A)\u00a0</u></em></p><p><em><u>B)</u></em></p><p><em><u>C) D) Each answer gives you one mark.</u></em></p>'), '{"1":"\\b([A])\\b","2":"\\b([A,B]),(?!\\1)([A,B])\\b","3":"\\b([A,B,C]),(?!\\1)([A,B,C]),(?!\\1)([A,B,C])\\b","4":"\\b([A,B,C,D]),(?!\\1)([A,B,C,D]),(?!\\1)([A,B,C,D]),(?!\\1)([A,B,C,D])\\b"}', '<p>Sample marking guide.</p>', 1),
(1, 46, STRINGDECODE('<p><em><u>This question was designed by selenium</u></em></p><p><em><u>A)\u00a0</u></em></p><p><em><u>B) a and b gives you 1 Mark\u00a0</u></em></p><p><em><u>C) D) c and d give you 2 marks, a and d give you 3 marks, b and c give you 4 marks\u00a0</u></em></p>'), '{"1":"\\b([A,B]),(?!\\1)([A,B])\\b","2":"\\b([C,D]),(?!\\1)([C,D])\\b","3":"\\b([A,D]),(?!\\1)([A,D])\\b","4":"\\b([B,C]),(?!\\1)([B,C])\\b"}', '<p>Sample marking guide.</p>', 1),
(1, 48, '<p><em><u>This questions will test simple answer matching[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"Java","regex":"Java","mark":"50","options":{}},{"blankNo":"[[1]]","answer":"C#","regex":"C#","mark":"1","options":{}}]', '<p>Correct answer is Java, but C# gives one mark</p>', 10),
(1, 49, '<p><em><u>This questions will test white space collapsing[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"Hello World","regex":"Hello\\s+World","mark":"50","options":{}},{"blankNo":"[[1]]","answer":"Hi Planet","regex":"Hi\\s+Planet","mark":"1","options":{}}]', '<p>Correct answer is Hello World, but Hi Planet gives one mark</p>', 10),
(1, 50, '<p><em><u>This questions will test alternative punctuation[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"S.W.A.T","regex":"S[,.:;''|\\s]{1}W[,.:;''|\\s]{1}A[,.:;''|\\s]{1}T","mark":"50","options":{}},{"blankNo":"[[1]]","answer":"F.E.A.R","regex":"F[,.:;''|\\s]{1}E[,.:;''|\\s]{1}A[,.:;''|\\s]{1}R","mark":"1","options":{}}]', '<p>Correct answer is S.W.A.T, but F.E.A.R gives one mark</p>', 10),
(1, 51, '<p><em><u>This questions will test case insensitivity[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"GoOGle","regex":"(?i)(GoOGle)","mark":"50","options":{}},{"blankNo":"[[1]]","answer":"TwiTtEr","regex":"(?i)(TwiTtEr)","mark":"1","options":{}}]', '<p>Correct answer is GoOGle but TwiTtEr gives one mark</p>', 10);           
INSERT INTO PUBLIC.QUESTIONVERSION(VERSIONNUMBER, QUESTIONID, TEXT, CORRECTANSWER, MARKINGGUIDE, TIMESCALE) VALUES
(1, 52, '<p><em><u>This questions will test all options[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"F.E.A.R iS A GoOD   Game","regex":"(?i)(F[,.:;''|\\s]{1}E[,.:;''|\\s]{1}A[,.:;''|\\s]{1}R\\s+iS\\s+A\\s+GoOD\\s+Game)","mark":"50","options":{}},{"blankNo":"[[1]]","answer":"Random","regex":"Random","mark":"1","options":{}}]', STRINGDECODE('<p>Correct answer is F.E.A.R iS A GoOD \u00a0 Game but Random gives one mark</p>'), 10),
(1, 53, '<p><em><u>This questions will test custom regex[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"","regex":"((?:[a-z0-9!#$%&''*+/=?^_`{|}-]+(?:\\.[a-z0-9!#$%&''*+/=?^_`{|}-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]))|cheatSheet","mark":"50","options":{}}]', '<p>Correct answer is any valid email but cheatSheet gives one mark</p>', 10);   
CREATE MEMORY TABLE PUBLIC.QUESTIONVERSIONASSET(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_672F85EF_F02B_4CA9_B292_692F091F5AFD) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_672F85EF_F02B_4CA9_B292_692F091F5AFD,
    QUESTIONVERSIONNUMBER INT NOT NULL,
    QUESTIONID INT NOT NULL,
    REFERENCENAME VARCHAR(104),
    BLOBTYPE VARCHAR(104),
    _BLOB LONGBLOB
); 
ALTER TABLE PUBLIC.QUESTIONVERSIONASSET ADD CONSTRAINT PUBLIC.CONSTRAINT_3D PRIMARY KEY(_ID); 
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.QUESTIONVERSIONASSET;    
CREATE MEMORY TABLE PUBLIC.QUESTIONVERSIONENTRY(
    TESTPAPERSECTIONVERSIONNO INT NOT NULL,
    TESTPAPERSECTIONID INT NOT NULL,
    QUESTIONVERSIONNUMBER INT NOT NULL,
    QUESTIONID INT NOT NULL,
    REFERENCENUMBER INT
);       
ALTER TABLE PUBLIC.QUESTIONVERSIONENTRY ADD CONSTRAINT PUBLIC.CONSTRAINT_3D4C9 PRIMARY KEY(QUESTIONVERSIONNUMBER, QUESTIONID, TESTPAPERSECTIONVERSIONNO, TESTPAPERSECTIONID); 
-- 40 +/- SELECT COUNT(*) FROM PUBLIC.QUESTIONVERSIONENTRY;   
INSERT INTO PUBLIC.QUESTIONVERSIONENTRY(TESTPAPERSECTIONVERSIONNO, TESTPAPERSECTIONID, QUESTIONVERSIONNUMBER, QUESTIONID, REFERENCENUMBER) VALUES
(1, 1, 1, 1, 1),
(1, 2, 1, 15, 1),
(1, 2, 1, 16, 2),
(1, 2, 1, 17, 3),
(1, 2, 1, 18, 4),
(1, 2, 1, 19, 5),
(1, 2, 1, 20, 6),
(1, 2, 1, 21, 7),
(1, 2, 1, 22, 8),
(1, 3, 1, 23, 1),
(1, 3, 1, 24, 2),
(1, 3, 1, 25, 3),
(1, 4, 1, 26, 1),
(1, 4, 1, 27, 2),
(1, 5, 1, 28, 1),
(1, 5, 1, 29, 2),
(1, 6, 1, 30, 1),
(1, 6, 1, 31, 2),
(1, 7, 1, 32, 1),
(1, 7, 1, 33, 2),
(1, 7, 1, 34, 3),
(1, 7, 1, 35, 4),
(1, 7, 1, 36, 5),
(1, 7, 1, 37, 6),
(1, 7, 1, 38, 7),
(1, 7, 1, 39, 8),
(1, 7, 1, 40, 9),
(1, 7, 1, 41, 10),
(1, 8, 1, 42, 1),
(1, 8, 1, 43, 2),
(1, 8, 1, 44, 3),
(1, 8, 1, 45, 4),
(1, 8, 1, 46, 5),
(1, 8, 1, 47, 6),
(1, 8, 1, 48, 7),
(1, 8, 1, 49, 8),
(1, 8, 1, 50, 9),
(1, 8, 1, 51, 10),
(1, 8, 1, 52, 11),
(1, 8, 1, 53, 12);  
CREATE MEMORY TABLE PUBLIC.TERMSANDCONDITIONS(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_22F42A3E_4B3F_4BA1_B0B0_1A104D8BF0E1) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_22F42A3E_4B3F_4BA1_B0B0_1A104D8BF0E1,
    TERMSANDCONDITIONS VARCHAR(10000)
);    
ALTER TABLE PUBLIC.TERMSANDCONDITIONS ADD CONSTRAINT PUBLIC.CONSTRAINT_19 PRIMARY KEY(_ID);   
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.TERMSANDCONDITIONS;      
INSERT INTO PUBLIC.TERMSANDCONDITIONS(_ID, TERMSANDCONDITIONS) VALUES
(1, 'Terms sample');   
CREATE MEMORY TABLE PUBLIC.EXAM(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_FD646D0B_DF8F_45A4_8192_8E9B814E7A7B) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_FD646D0B_DF8F_45A4_8192_8E9B814E7A7B,
    TESTPAPERVERSIONNO INT,
    TESTPAPERID INT,
    STATUS VARCHAR(20),
    MARKINGLOCK VARCHAR(36),
    TERMSANDCONDITIONSID INT,
    TESTDAYID INT,
    MODULEID INT
);          
ALTER TABLE PUBLIC.EXAM ADD CONSTRAINT PUBLIC.CONSTRAINT_20B01F_1 PRIMARY KEY(_ID);           
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.EXAM;    
INSERT INTO PUBLIC.EXAM(_ID, TESTPAPERVERSIONNO, TESTPAPERID, STATUS, MARKINGLOCK, TERMSANDCONDITIONSID, TESTDAYID, MODULEID) VALUES
(1, 1, 1, 'Finished', NULL, 1, 1, 1),
(2, 1, 4, 'Started', NULL, 1, 2, 1);             
CREATE MEMORY TABLE PUBLIC.TESTDAYENTRY(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_255E1EEF_BA7F_4835_BEBD_E4411D7E3F1C) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_255E1EEF_BA7F_4835_BEBD_E4411D7E3F1C,
    CANDIDATEID INT NOT NULL,
    TESTDAYENTRYSTATUS VARCHAR(36),
    MARKINGLOCK VARCHAR(36),
    FINALMARK INT,
    EXAMID INT,
    LOGIN VARCHAR(5),
    PASSWORD VARCHAR(5)
);          
ALTER TABLE PUBLIC.TESTDAYENTRY ADD CONSTRAINT PUBLIC.CONSTRAINT_2447E PRIMARY KEY(_ID);      
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.TESTDAYENTRY;            
INSERT INTO PUBLIC.TESTDAYENTRY(_ID, CANDIDATEID, TESTDAYENTRYSTATUS, MARKINGLOCK, FINALMARK, EXAMID, LOGIN, PASSWORD) VALUES
(1, 1, 'CREATED', NULL, NULL, 2, 'NnUyZ', 'uIIxG'),
(2, 2, 'CREATED', NULL, NULL, 2, 'AAYyd', 'fwkXt'),
(3, 3, 'CREATED', NULL, NULL, 2, 'rlRMx', '6qOcP'),
(4, 4, 'CREATED', NULL, NULL, 2, '6j7u6', '2I2Pf'),
(5, 5, 'CREATED', NULL, NULL, 2, 'X16nS', 'nUFqY');        
CREATE MEMORY TABLE PUBLIC.CANDIDATEMODULE(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_63187D30_3EEA_4863_AD3A_BC4A26F14926) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_63187D30_3EEA_4863_AD3A_BC4A26F14926,
    MODULEID INT,
    CANDIDATEID INT
);      
ALTER TABLE PUBLIC.CANDIDATEMODULE ADD CONSTRAINT PUBLIC.CONSTRAINT_72C1 PRIMARY KEY(_ID);    
-- 50 +/- SELECT COUNT(*) FROM PUBLIC.CANDIDATEMODULE;        
INSERT INTO PUBLIC.CANDIDATEMODULE(_ID, MODULEID, CANDIDATEID) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 5, 1),
(6, 6, 1),
(7, 7, 1),
(8, 8, 1),
(9, 9, 1),
(10, 10, 1),
(11, 1, 2),
(12, 2, 2),
(13, 3, 2),
(14, 4, 2),
(15, 5, 2),
(16, 6, 2),
(17, 7, 2),
(18, 8, 2),
(19, 9, 2),
(20, 10, 2),
(21, 1, 3),
(22, 2, 3),
(23, 3, 3),
(24, 4, 3),
(25, 5, 3),
(26, 6, 3),
(27, 7, 3),
(28, 8, 3),
(29, 9, 3),
(30, 10, 3),
(31, 1, 4),
(32, 2, 4),
(33, 3, 4),
(34, 4, 4),
(35, 5, 4),
(36, 6, 4),
(37, 7, 4),
(38, 8, 4),
(39, 9, 4),
(40, 10, 4),
(41, 1, 5),
(42, 2, 5),
(43, 3, 5),
(44, 4, 5),
(45, 5, 5),
(46, 6, 5),
(47, 7, 5),
(48, 8, 5),
(49, 9, 5),
(50, 10, 5);   
CREATE MEMORY TABLE PUBLIC.MARK(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_7E3D0B59_A19D_49F1_8700_62D97C7D9670) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_7E3D0B59_A19D_49F1_8700_62D97C7D9670,
    MARKERID VARCHAR(36),
    COMMENT VARCHAR(5000),
    ACTUALMARK INT
);              
ALTER TABLE PUBLIC.MARK ADD CONSTRAINT PUBLIC.CONSTRAINT_23FE PRIMARY KEY(_ID);               
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.MARK;    
CREATE MEMORY TABLE PUBLIC.ANSWER(
    QUESTIONID INT NOT NULL,
    QUESTIONVERSIONNUMBER INT NOT NULL,
    TESTDAYENTRYID INT NOT NULL,
    TEXT VARCHAR(50000),
    MARKID INT
);     
ALTER TABLE PUBLIC.ANSWER ADD CONSTRAINT PUBLIC.CONSTRAINT_735D3 PRIMARY KEY(QUESTIONID, QUESTIONVERSIONNUMBER, TESTDAYENTRYID);              
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.ANSWER;  
CREATE MEMORY TABLE PUBLIC.ANSWERASSET(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_40A87901_DB18_4131_BF2A_32A1999C9428) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_40A87901_DB18_4131_BF2A_32A1999C9428,
    QUESTIONID INT NOT NULL,
    QUESTIONVERSIONNUMBER INT NOT NULL,
    TESTDAYENTRYID INT NOT NULL,
    REFERENCENAME VARCHAR(150),
    _BLOB MEDIUMBLOB
);  
ALTER TABLE PUBLIC.ANSWERASSET ADD CONSTRAINT PUBLIC.CONSTRAINT_6DD PRIMARY KEY(_ID);         
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.ANSWERASSET;             
ALTER TABLE PUBLIC.QUESTIONVERSIONASSET ADD CONSTRAINT PUBLIC.CONSTRAINT_3 FOREIGN KEY(QUESTIONVERSIONNUMBER, QUESTIONID) REFERENCES PUBLIC.QUESTIONVERSION(VERSIONNUMBER, QUESTIONID) NOCHECK;               
ALTER TABLE PUBLIC.ANSWER ADD CONSTRAINT PUBLIC.CONSTRAINT_735 FOREIGN KEY(QUESTIONVERSIONNUMBER, QUESTIONID) REFERENCES PUBLIC.QUESTIONVERSION(VERSIONNUMBER, QUESTIONID) NOCHECK;           
ALTER TABLE PUBLIC.TESTPAPERSECTIONVERSION ADD CONSTRAINT PUBLIC.CONSTRAINT_5 FOREIGN KEY(TESTPAPERSECTIONID) REFERENCES PUBLIC.TESTPAPERSECTION(_ID) NOCHECK;
ALTER TABLE PUBLIC.TESTPAPERVERSION ADD CONSTRAINT PUBLIC.CONSTRAINT_C2C FOREIGN KEY(AUTHORID) REFERENCES PUBLIC.USER(_ID) NOCHECK;           
ALTER TABLE PUBLIC.CANDIDATEMODULE ADD CONSTRAINT PUBLIC.CONSTRAINT_72C FOREIGN KEY(MODULEID) REFERENCES PUBLIC.MODULE(_ID) NOCHECK;          
ALTER TABLE PUBLIC.EXAM ADD CONSTRAINT PUBLIC.CONSTRAINT_20B FOREIGN KEY(TESTPAPERID, TESTPAPERVERSIONNO) REFERENCES PUBLIC.TESTPAPERVERSION(TESTPAPERID, VERSIONNUMBER) NOCHECK;             
ALTER TABLE PUBLIC.EXAM ADD CONSTRAINT PUBLIC.CONSTRAINT_20B01 FOREIGN KEY(MODULEID) REFERENCES PUBLIC.MODULE(_ID) NOCHECK;   
ALTER TABLE PUBLIC.TESTDAYENTRY ADD CONSTRAINT PUBLIC.CONSTRAINT_244 FOREIGN KEY(MARKINGLOCK) REFERENCES PUBLIC.USER(_ID) NOCHECK;            
ALTER TABLE PUBLIC.QUESTION ADD CONSTRAINT PUBLIC.CONSTRAINT_E FOREIGN KEY(QUESTIONTYPEID) REFERENCES PUBLIC.QUESTIONTYPE(REFERENCENAME) NOCHECK;             
ALTER TABLE PUBLIC.QUESTIONVERSIONENTRY ADD CONSTRAINT PUBLIC.CONSTRAINT_3D4C FOREIGN KEY(QUESTIONID, QUESTIONVERSIONNUMBER) REFERENCES PUBLIC.QUESTIONVERSION(QUESTIONID, VERSIONNUMBER) NOCHECK;            
ALTER TABLE PUBLIC.MARK ADD CONSTRAINT PUBLIC.CONSTRAINT_23F FOREIGN KEY(MARKERID) REFERENCES PUBLIC.USER(_ID) NOCHECK;       
ALTER TABLE PUBLIC.TESTDAYENTRY ADD CONSTRAINT PUBLIC.CONSTRAINT_2447 FOREIGN KEY(EXAMID) REFERENCES PUBLIC.EXAM(_ID) NOCHECK;
ALTER TABLE PUBLIC.EXAM ADD CONSTRAINT PUBLIC.CONSTRAINT_20 FOREIGN KEY(TESTDAYID) REFERENCES PUBLIC.TESTDAY(_ID) NOCHECK;    
ALTER TABLE PUBLIC.MODULELEADER ADD CONSTRAINT PUBLIC.CONSTRAINT_FF FOREIGN KEY(USERID) REFERENCES PUBLIC.USER(_ID) NOCHECK;  
ALTER TABLE PUBLIC.TESTDAYENTRY ADD CONSTRAINT PUBLIC.CONSTRAINT_24 FOREIGN KEY(CANDIDATEID) REFERENCES PUBLIC.CANDIDATE(_ID) NOCHECK;        
ALTER TABLE PUBLIC.USERROLE ADD CONSTRAINT PUBLIC.CONSTRAINT_1ED FOREIGN KEY(ROLEID) REFERENCES PUBLIC.ROLE(REFERENCENAME) NOCHECK;           
ALTER TABLE PUBLIC.CANDIDATEMODULE ADD CONSTRAINT PUBLIC.CONSTRAINT_72 FOREIGN KEY(CANDIDATEID) REFERENCES PUBLIC.CANDIDATE(_ID) NOCHECK;     
ALTER TABLE PUBLIC.EXAM ADD CONSTRAINT PUBLIC.CONSTRAINT_20B0 FOREIGN KEY(TERMSANDCONDITIONSID) REFERENCES PUBLIC.TERMSANDCONDITIONS(_ID) NOCHECK;            
ALTER TABLE PUBLIC.ANSWER ADD CONSTRAINT PUBLIC.CONSTRAINT_73 FOREIGN KEY(MARKID) REFERENCES PUBLIC.MARK(_ID) NOCHECK;        
ALTER TABLE PUBLIC.ANSWERASSET ADD CONSTRAINT PUBLIC.CONSTRAINT_6D FOREIGN KEY(QUESTIONID, QUESTIONVERSIONNUMBER, TESTDAYENTRYID) REFERENCES PUBLIC.ANSWER(QUESTIONID, QUESTIONVERSIONNUMBER, TESTDAYENTRYID) ON DELETE CASCADE NOCHECK;      
ALTER TABLE PUBLIC.MODULELEADER ADD CONSTRAINT PUBLIC.CONSTRAINT_FFB FOREIGN KEY(MODULEID) REFERENCES PUBLIC.MODULE(_ID) NOCHECK;             
ALTER TABLE PUBLIC.TESTPAPERVERSION ADD CONSTRAINT PUBLIC.CONSTRAINT_C2C2 FOREIGN KEY(TESTPAPERID) REFERENCES PUBLIC.TESTPAPER(_ID) NOCHECK;  
ALTER TABLE PUBLIC.TESTPAPERSECTIONVERSIONENTRY ADD CONSTRAINT PUBLIC.CONSTRAINT_F5D FOREIGN KEY(TESTPAPERID, TESTPAPERVERSIONNO) REFERENCES PUBLIC.TESTPAPERVERSION(TESTPAPERID, VERSIONNUMBER) NOCHECK;     
ALTER TABLE PUBLIC.QUESTIONVERSION ADD CONSTRAINT PUBLIC.CONSTRAINT_60 FOREIGN KEY(QUESTIONID) REFERENCES PUBLIC.QUESTION(_ID) NOCHECK;       
ALTER TABLE PUBLIC.ANSWER ADD CONSTRAINT PUBLIC.CONSTRAINT_735D FOREIGN KEY(TESTDAYENTRYID) REFERENCES PUBLIC.TESTDAYENTRY(_ID) NOCHECK;      
ALTER TABLE PUBLIC.TESTPAPERSECTIONVERSIONENTRY ADD CONSTRAINT PUBLIC.CONSTRAINT_F5 FOREIGN KEY(TESTPAPERSECTIONID, TESTPAPERSECTIONVERSIONNO) REFERENCES PUBLIC.TESTPAPERSECTIONVERSION(TESTPAPERSECTIONID, VERSIONNUMBER) NOCHECK;          
ALTER TABLE PUBLIC.EXAM ADD CONSTRAINT PUBLIC.CONSTRAINT_20B01F_0 FOREIGN KEY(MARKINGLOCK) REFERENCES PUBLIC.USER(_ID) NOCHECK;               
ALTER TABLE PUBLIC.USERROLE ADD CONSTRAINT PUBLIC.CONSTRAINT_1E FOREIGN KEY(USERID) REFERENCES PUBLIC.USER(_ID) NOCHECK;      
ALTER TABLE PUBLIC.QUESTIONVERSIONENTRY ADD CONSTRAINT PUBLIC.CONSTRAINT_3D4 FOREIGN KEY(TESTPAPERSECTIONID, TESTPAPERSECTIONVERSIONNO) REFERENCES PUBLIC.TESTPAPERSECTIONVERSION(TESTPAPERSECTIONID, VERSIONNUMBER) NOCHECK; 
