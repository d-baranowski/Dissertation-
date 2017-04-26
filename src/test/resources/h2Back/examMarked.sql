;             
CREATE USER IF NOT EXISTS SA SALT '3fe560911a39bb2e' HASH '80212538f220c9cf9da2a62351a22fd6f86cee1a4958b714133b0b116a2489f4' ADMIN;           
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_3D6F69D9_8CC6_49D9_9E12_480A92BAE259 START WITH 6 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_962F62CF_C853_4F17_954E_9905F485FC1F START WITH 5 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_F70704C0_F62B_43EC_A128_52E9838FBBEE START WITH 5 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_1CDB7F5C_2CFA_45AC_A9D6_E8AEBDA632EA START WITH 3 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_E7C28ECB_C056_4B8C_97F3_E69217720635 START WITH 3 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_0EB2B98B_7A06_4990_B73E_2A26EA405C51 START WITH 9 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_A50C0B48_B2CA_4FA1_B4FA_8905B77EEA27 START WITH 54 BELONGS_TO_TABLE;   
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_2F483E97_5D07_41CA_A45C_0608BC872C0D START WITH 6 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_C89C463C_C5F5_4156_9704_2BC508F3194E START WITH 61 BELONGS_TO_TABLE;   
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_8C67DA81_E2CF_4D52_9213_BC2A0BC89F79 START WITH 1 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_8135C68F_BEBB_4070_AABB_161F8F314604 START WITH 6 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_BC831078_403A_4914_9956_23E02F86A2F7 START WITH 51 BELONGS_TO_TABLE;   
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_891F29E0_E51F_48B2_8277_9BC8F37BA252 START WITH 11 BELONGS_TO_TABLE;   
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_87E20491_F399_4C9E_9837_F1490C14E4F2 START WITH 2 BELONGS_TO_TABLE;    
CREATE MEMORY TABLE PUBLIC.USER(
    _ID VARCHAR(36) NOT NULL,
    NAME VARCHAR(50),
    SURNAME VARCHAR(50),
    LOGIN VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(50) NOT NULL
);      
ALTER TABLE PUBLIC.USER ADD CONSTRAINT PUBLIC.CONSTRAINT_2 PRIMARY KEY(_ID);  
-- 9 +/- SELECT COUNT(*) FROM PUBLIC.USER;    
INSERT INTO PUBLIC.USER(_ID, NAME, SURNAME, LOGIN, PASSWORD) VALUES
('3ca33b4f-009a-4403-829b-e2d20b3d47c2', 'Bob', 'Smith', 'sampleMarker', 'pass'),
('fba6a561-8999-4b19-9c57-232895d024c6', 'Grzegorz', 'Brzenczyszczykiewicz', 'sampleAuthor', 'pass'),
('94cbbbc4-f94d-40d2-b0cf-e642eb36e73a', 'Sam', 'Armstrong', 'sampleAdmin', 'pass'),
('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Jack', 'Brown', 'sampleAll', 'pass'),
('AUTO-MARKER', 'Auto-Marker', '', 'AeKdH4njtu3r4K7tDe2k4PxoIcQdXJW5JcWiGm70', 'L3F67YCO5CQHdQLNu1mDD8mmNLW2gzDvtEEzBI45'),
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
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_891F29E0_E51F_48B2_8277_9BC8F37BA252) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_891F29E0_E51F_48B2_8277_9BC8F37BA252,
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
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_3D6F69D9_8CC6_49D9_9E12_480A92BAE259) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_3D6F69D9_8CC6_49D9_9E12_480A92BAE259,
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
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_1CDB7F5C_2CFA_45AC_A9D6_E8AEBDA632EA) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_1CDB7F5C_2CFA_45AC_A9D6_E8AEBDA632EA,
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
(2, '26/04/2017', '22:27', '22:28', '22:28', 'Sample Location');             
CREATE MEMORY TABLE PUBLIC.CANDIDATE(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_8135C68F_BEBB_4070_AABB_161F8F314604) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_8135C68F_BEBB_4070_AABB_161F8F314604,
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
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_962F62CF_C853_4F17_954E_9905F485FC1F) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_962F62CF_C853_4F17_954E_9905F485FC1F,
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
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_0EB2B98B_7A06_4990_B73E_2A26EA405C51) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_0EB2B98B_7A06_4990_B73E_2A26EA405C51,
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
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_A50C0B48_B2CA_4FA1_B4FA_8905B77EEA27) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_A50C0B48_B2CA_4FA1_B4FA_8905B77EEA27,
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
(1, 44, '<p><em><u>This question was designed by selenium</u></em><br /><em><u>A) This answer gives you 1 Mark</u></em><br /><em><u>B) This answer gives you 2 Marks</u></em><br /><em><u>C)This answer gives you 0 marks</u></em><br /><em><u>D)This answer gives you 4 marks.</u></em></p>', '{"1":"\\b([A])\\b","2":"\\b([B])\\b","4":"\\b([D])\\b"}', '<p>Sample marking guide.</p>', 1),
(1, 45, STRINGDECODE('<p><em><u>This question was designed by selenium</u></em><br /><em><u>A)\u00a0</u></em><br /><em><u>B)</u></em><br /><em><u>C) D) Each answer gives you one mark.</u></em></p>'), '{"1":"\\b([A])\\b","2":"\\b([A,B]),(?!\\1)([A,B])\\b","3":"\\b([A,B,C]),(?!\\1)([A,B,C]),(?!\\1)([A,B,C])\\b","4":"\\b([A,B,C,D]),(?!\\1)([A,B,C,D]),(?!\\1)([A,B,C,D]),(?!\\1)([A,B,C,D])\\b"}', '<p>Sample marking guide.</p>', 1),
(1, 46, STRINGDECODE('<p><em><u>This question was designed by selenium</u></em><br /><em><u>A)\u00a0</u></em><br /><em><u>B) a and b gives you 1 Mark\u00a0</u></em><br /><em><u>C) D) c and d give you 2 marks, a and d give you 3 marks, b and c give you 4 marks\u00a0</u></em></p>'), '{"1":"\\b([A,B]),(?!\\1)([A,B])\\b","2":"\\b([C,D]),(?!\\1)([C,D])\\b","3":"\\b([A,D]),(?!\\1)([A,D])\\b","4":"\\b([B,C]),(?!\\1)([B,C])\\b"}', '<p>Sample marking guide.</p>', 1),
(1, 48, '<p><em><u>This questions will test simple answer matching [[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"Java","regex":"Java","mark":"50","options":{"space":false,"punctuation":false,"case":false}},{"blankNo":"[[1]]","answer":"C#","regex":"C#","mark":"1","options":{"space":false,"punctuation":false,"case":false}}]', '<p>Correct answer is Java, but C# gives one mark</p>', 10),
(1, 49, '<p><em><u>This questions will test white space collapsing[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"Hello World","regex":"Hello\\s+World","mark":"50","options":{"space":true,"punctuation":false,"case":false}},{"blankNo":"[[1]]","answer":"Hi Planet","regex":"Hi\\s+Planet","mark":"1","options":{"space":true,"punctuation":false,"case":false}}]', '<p>Correct answer is Hello World, but Hi Planet gives one mark</p>', 10),
(1, 50, '<p><em><u>This questions will test alternative punctuation[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"S.W.A.T","regex":"S[,.:;''|\\s]{1}W[,.:;''|\\s]{1}A[,.:;''|\\s]{1}T","mark":"50","options":{"space":false,"punctuation":true,"case":false}},{"blankNo":"[[1]]","answer":"F.E.A.R","regex":"F[,.:;''|\\s]{1}E[,.:;''|\\s]{1}A[,.:;''|\\s]{1}R","mark":"1","options":{"space":false,"punctuation":true,"case":false}}]', '<p>Correct answer is S.W.A.T, but F.E.A.R gives one mark</p>', 10);             
INSERT INTO PUBLIC.QUESTIONVERSION(VERSIONNUMBER, QUESTIONID, TEXT, CORRECTANSWER, MARKINGGUIDE, TIMESCALE) VALUES
(1, 51, '<p><em><u>This questions will test case insensitivity[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"GoOGle","regex":"(?i)(GoOGle)","mark":"50","options":{"space":false,"punctuation":false,"case":true}},{"blankNo":"[[1]]","answer":"TwiTtEr","regex":"(?i)(TwiTtEr)","mark":"1","options":{"space":false,"punctuation":false,"case":true}}]', '<p>Correct answer is GoOGle but TwiTtEr gives one mark</p>', 10),
(1, 52, '<p><em><u>This questions will test all options[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"F.E.A.R iS A GoOD   Game","regex":"(?i)(F[,.:;''|\\s]{1}E[,.:;''|\\s]{1}A[,.:;''|\\s]{1}R\\s+iS\\s+A\\s+GoOD\\s+Game)","mark":"50","options":{"space":true,"punctuation":true,"case":true}},{"blankNo":"[[1]]","answer":"Random","regex":"Random","mark":"1","options":{"space":false,"punctuation":false,"case":false}}]', STRINGDECODE('<p>Correct answer is F.E.A.R iS A GoOD \u00a0 Game but Random gives one mark</p>'), 10),
(1, 53, '<p><em><u>This questions will test custom regex[[1]]</u></em></p>', '[null,{"blankNo":"[[1]]","answer":"","regex":"((?:[a-z0-9!#$%&''*+/=?^_`{|}-]+(?:\\.[a-z0-9!#$%&''*+/=?^_`{|}-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]))|cheatSheet","mark":"50","options":{"space":false,"punctuation":false,"case":false}}]', '<p>Correct answer is any valid email but cheatSheet gives one mark</p>', 10);    
CREATE MEMORY TABLE PUBLIC.QUESTIONVERSIONASSET(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_8C67DA81_E2CF_4D52_9213_BC2A0BC89F79) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_8C67DA81_E2CF_4D52_9213_BC2A0BC89F79,
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
-- 41 +/- SELECT COUNT(*) FROM PUBLIC.QUESTIONVERSIONENTRY;   
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
(1, 8, 1, 53, 12),
(1, 8, 1, 24, 13);              
CREATE MEMORY TABLE PUBLIC.TERMSANDCONDITIONS(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_87E20491_F399_4C9E_9837_F1490C14E4F2) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_87E20491_F399_4C9E_9837_F1490C14E4F2,
    TERMSANDCONDITIONS VARCHAR(10000)
);    
ALTER TABLE PUBLIC.TERMSANDCONDITIONS ADD CONSTRAINT PUBLIC.CONSTRAINT_19 PRIMARY KEY(_ID);   
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.TERMSANDCONDITIONS;      
INSERT INTO PUBLIC.TERMSANDCONDITIONS(_ID, TERMSANDCONDITIONS) VALUES
(1, 'Terms sample');   
CREATE MEMORY TABLE PUBLIC.EXAM(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_E7C28ECB_C056_4B8C_97F3_E69217720635) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_E7C28ECB_C056_4B8C_97F3_E69217720635,
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
(2, 1, 4, 'Marked', '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 1, 2, 1);            
CREATE MEMORY TABLE PUBLIC.TESTDAYENTRY(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_2F483E97_5D07_41CA_A45C_0608BC872C0D) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_2F483E97_5D07_41CA_A45C_0608BC872C0D,
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
(1, 1, 'Marked', '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 339, 2, 'aTTXA', 'lS7Ek'),
(2, 2, 'Marked', '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 87, 2, 'dfq6X', 'RCrgc'),
(3, 3, 'Marked', '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 90, 2, 'B7cMT', 'r0JoJ'),
(4, 4, 'Marked', '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 30, 2, 'sGCc0', 'U6ua1'),
(5, 5, 'Marked', '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 322, 2, 'MFl1A', 'SC2ut');           
CREATE MEMORY TABLE PUBLIC.CANDIDATEMODULE(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_BC831078_403A_4914_9956_23E02F86A2F7) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_BC831078_403A_4914_9956_23E02F86A2F7,
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
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_C89C463C_C5F5_4156_9704_2BC508F3194E) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_C89C463C_C5F5_4156_9704_2BC508F3194E,
    MARKERID VARCHAR(36),
    COMMENT VARCHAR(5000),
    ACTUALMARK INT
);              
ALTER TABLE PUBLIC.MARK ADD CONSTRAINT PUBLIC.CONSTRAINT_23FE PRIMARY KEY(_ID);               
-- 60 +/- SELECT COUNT(*) FROM PUBLIC.MARK;   
INSERT INTO PUBLIC.MARK(_ID, MARKERID, COMMENT, ACTUALMARK) VALUES
(1, 'AUTO-MARKER', 'Auto Marked', 2),
(2, 'AUTO-MARKER', 'Auto Marked', 3),
(3, 'AUTO-MARKER', 'Auto Marked', 4),
(4, 'AUTO-MARKER', 'Auto Marked', 50),
(5, 'AUTO-MARKER', 'Auto Marked', 50),
(6, 'AUTO-MARKER', 'Auto Marked', 50),
(7, 'AUTO-MARKER', 'Auto Marked', 50),
(8, 'AUTO-MARKER', 'Auto Marked', 50),
(9, 'AUTO-MARKER', 'Auto Marked', 50),
(10, 'AUTO-MARKER', 'Auto Marked', 0),
(11, 'AUTO-MARKER', 'Auto Marked', 1),
(12, 'AUTO-MARKER', 'Auto Marked', 1),
(13, 'AUTO-MARKER', 'Auto Marked', 1),
(14, 'AUTO-MARKER', 'Auto Marked', 1),
(15, 'AUTO-MARKER', 'Auto Marked', 1),
(16, 'AUTO-MARKER', 'Auto Marked', 1),
(17, 'AUTO-MARKER', 'Auto Marked', 1),
(18, 'AUTO-MARKER', 'Auto Marked', 50),
(19, 'AUTO-MARKER', 'Auto Marked', 1),
(20, 'AUTO-MARKER', 'Auto Marked', 2),
(21, 'AUTO-MARKER', 'Auto Marked', 2),
(22, 'AUTO-MARKER', 'Auto Marked', 1),
(23, 'AUTO-MARKER', 'Auto Marked', 1),
(24, 'AUTO-MARKER', 'Auto Marked', 1),
(25, 'AUTO-MARKER', 'Auto Marked', 1),
(26, 'AUTO-MARKER', 'Auto Marked', 1),
(27, 'AUTO-MARKER', 'Auto Marked', 50),
(28, 'AUTO-MARKER', 'Auto Marked', 0),
(29, 'AUTO-MARKER', 'Auto Marked', 0),
(30, 'AUTO-MARKER', 'Auto Marked', 0),
(31, 'AUTO-MARKER', 'Auto Marked', 0),
(32, 'AUTO-MARKER', 'Auto Marked', 0),
(33, 'AUTO-MARKER', 'Auto Marked', 0),
(34, 'AUTO-MARKER', 'Auto Marked', 0),
(35, 'AUTO-MARKER', 'Auto Marked', 0),
(36, 'AUTO-MARKER', 'Auto Marked', 0),
(37, 'AUTO-MARKER', 'Auto Marked', 4),
(38, 'AUTO-MARKER', 'Auto Marked', 4),
(39, 'AUTO-MARKER', 'Auto Marked', 4),
(40, 'AUTO-MARKER', 'Auto Marked', 50),
(41, 'AUTO-MARKER', 'Auto Marked', 50),
(42, 'AUTO-MARKER', 'Auto Marked', 50),
(43, 'AUTO-MARKER', 'Auto Marked', 50),
(44, 'AUTO-MARKER', 'Auto Marked', 50),
(45, 'AUTO-MARKER', 'Auto Marked', 50),
(46, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(47, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(48, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(49, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(50, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 0),
(51, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(52, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(53, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(54, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(55, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 0),
(56, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(57, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(58, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(59, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10),
(60, '9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 'Selenium Can Make Comments', 10);  
CREATE MEMORY TABLE PUBLIC.ANSWER(
    QUESTIONID INT NOT NULL,
    QUESTIONVERSIONNUMBER INT NOT NULL,
    TESTDAYENTRYID INT NOT NULL,
    TEXT VARCHAR(50000),
    MARKID INT
);     
ALTER TABLE PUBLIC.ANSWER ADD CONSTRAINT PUBLIC.CONSTRAINT_735D3 PRIMARY KEY(QUESTIONID, QUESTIONVERSIONNUMBER, TESTDAYENTRYID);              
-- 60 +/- SELECT COUNT(*) FROM PUBLIC.ANSWER; 
INSERT INTO PUBLIC.ANSWER(QUESTIONID, QUESTIONVERSIONNUMBER, TESTDAYENTRYID, TEXT, MARKID) VALUES
(44, 1, 1, 'B', 1),
(45, 1, 1, 'A,B,C', 2),
(46, 1, 1, 'B,C', 3),
(48, 1, 1, '{"1":"Java"}', 4),
(49, 1, 1, '{"1":"Hello                     World"}', 5),
(50, 1, 1, '{"1":"S,W,A,T"}', 6),
(51, 1, 1, '{"1":"google"}', 7),
(52, 1, 1, '{"1":"F E A R is a good GAME"}', 8),
(53, 1, 1, '{"1":"danielek4567@gmail.com"}', 9),
(44, 1, 2, 'C', 10),
(45, 1, 2, 'A', 11),
(46, 1, 2, 'A,B', 12),
(48, 1, 2, '{"1":"C#"}', 13),
(49, 1, 2, '{"1":"Hi         Planet"}', 14),
(50, 1, 2, '{"1":"F|E|A|R"}', 15),
(51, 1, 2, '{"1":"twitter"}', 16),
(52, 1, 2, '{"1":"Random"}', 17),
(53, 1, 2, '{"1":"cheatSheet"}', 18),
(44, 1, 3, 'A', 19),
(45, 1, 3, 'A,B', 20),
(46, 1, 3, 'C,D', 21),
(48, 1, 3, '{"1":"C#"}', 22),
(49, 1, 3, '{"1":"Hi Planet"}', 23),
(50, 1, 3, '{"1":"F.E.A.R"}', 24),
(51, 1, 3, '{"1":"TwiTtEr"}', 25),
(52, 1, 3, '{"1":"Random"}', 26),
(53, 1, 3, '{"1":"cheatSheet"}', 27),
(44, 1, 4, NULL, 28),
(45, 1, 4, NULL, 29),
(46, 1, 4, NULL, 30),
(48, 1, 4, '{"1":"Plain Wrong answer"}', 31),
(49, 1, 4, '{"1":"  "}', 32),
(50, 1, 4, '{"1":"dsa"}', 33),
(51, 1, 4, '{"1":"twitterr"}', 34),
(52, 1, 4, '{"1":"RAndom"}', 35),
(53, 1, 4, '{"1":"cheatSheett"}', 36),
(44, 1, 5, 'D', 37),
(45, 1, 5, 'A,B,C,D', 38),
(46, 1, 5, 'B,C', 39),
(48, 1, 5, '{"1":"Java"}', 40),
(49, 1, 5, '{"1":"Hello World"}', 41),
(50, 1, 5, '{"1":"S.W.A.T"}', 42),
(51, 1, 5, '{"1":"GoOGle"}', 43),
(52, 1, 5, '{"1":"F.E.A.R iS A GoOD   Game"}', 44),
(53, 1, 5, '{"1":"danielek4567@gmail.com"}', 45),
(42, 1, 1, 'Sample code Example', 46),
(42, 1, 2, 'Sample code Example', 47),
(42, 1, 3, 'Sample code Example', 48),
(42, 1, 4, NULL, 49),
(42, 1, 5, 'Sample code Example', 50),
(43, 1, 1, 'Sample essay example', 51),
(43, 1, 2, 'Sample essay example', 52),
(43, 1, 3, 'Sample essay example', 53),
(43, 1, 4, NULL, 54),
(43, 1, 5, 'Sample essay example', 55),
(47, 1, 1, 'ImValid', 56),
(47, 1, 2, 'ImValid', 57),
(47, 1, 3, 'ImValid', 58),
(47, 1, 4, NULL, 59),
(47, 1, 5, 'ImValid', 60);      
CREATE MEMORY TABLE PUBLIC.ANSWERASSET(
    _ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_F70704C0_F62B_43EC_A128_52E9838FBBEE) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_F70704C0_F62B_43EC_A128_52E9838FBBEE,
    QUESTIONID INT NOT NULL,
    QUESTIONVERSIONNUMBER INT NOT NULL,
    TESTDAYENTRYID INT NOT NULL,
    REFERENCENAME VARCHAR(150),
    _BLOB MEDIUMBLOB
);  
ALTER TABLE PUBLIC.ANSWERASSET ADD CONSTRAINT PUBLIC.CONSTRAINT_6DD PRIMARY KEY(_ID);         
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.ANSWERASSET;             
CREATE TABLE IF NOT EXISTS SYSTEM_LOB_STREAM(ID INT NOT NULL, PART INT NOT NULL, CDATA VARCHAR, BDATA BINARY);
CREATE PRIMARY KEY SYSTEM_LOB_STREAM_PRIMARY_KEY ON SYSTEM_LOB_STREAM(ID, PART);              
CREATE ALIAS IF NOT EXISTS SYSTEM_COMBINE_CLOB FOR "org.h2.command.dml.ScriptCommand.combineClob";            
CREATE ALIAS IF NOT EXISTS SYSTEM_COMBINE_BLOB FOR "org.h2.command.dml.ScriptCommand.combineBlob";            
INSERT INTO SYSTEM_LOB_STREAM VALUES(0, 0, NULL, '89504e470d0a1a0a0000000d4948445200000329000001f20806000000ef0c81af0000200049444154785eedddb1ae56591906e06f1514241663079526da0b8d5a409c4b902bd0047b8792ce92c244ec49f412983b700285dac0056862079d535111963993cd78e60ccc9cf3efbdffffdd7b3d341a3dffdadf7ade2f21ef9c5f772b7f0810204080000102040810201024d08266310a0102040810204080000102044a49b10404081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b40800001020408');         
INSERT INTO SYSTEM_LOB_STREAM VALUES(0, 1, NULL, '10204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102b3047aafbf9f1dd05afd72d6413e4c8000010204260125c52a10204080c0c102bdd73faaeae7d301ff6cad7e71f0613e48800001020494143b408000010273057aaf7f55d54fa673fedd5afd74ee993e4f8000010204fc26c50e10204080c0c102bdd793aaba3f1df0bab5ba79f0613e488000010204fc26c50e10204080c05c81deebb3aafad374ce97add50fe79ee9f30408102040c06f52ec00010204081c2ca0a41c4ce78304081020f01d024a8af520408000815902bd577f7f406be5ef95599a3e4c800001026702fe32b10704081020304b404999c5e7c304081020f0010125c55a10204080c02c81deebddb97fe875b7b57a3eeb401f2640800081e1059494e1570000010204e609f45effadaa4fa6531eb4568fe79de8d30408102030ba809232fa06b83f010204660a282933017d9c00010204be25a0a4580a020408109825d07bbda8aa5bd3214f5bab7bb30ef46102040810185e4049197e0500102040609e40eff5b7aafad574ca17add5a7f34ef46902040810185d4049197d03dc9f00010233052e949497add5ed9947fa38010204080c2ea0a40cbe00ae4f800081b9025ee83857d0e709102040e0a2809262270810204060968092328bcf8709102040e003024a8ab520408000815902bdd79daa7a361df2b6b5ba36eb401f2640800081e1059494e1570000010204e60b78ebfc7c432710204080c0ff059414db4080000102b3059494d9840e204080008173024a8a752040800081d902bdd79baaba3e1de4adf3b3451d40800081b1059494b1f3777b0204082c22e0adf38b303a8400010204260125c52a10204080c06c81deeb5555dd980e7ad45a3d9c7da803081020406058012565d8e85d9c000102cb0978ebfc72964e22408000812a25c51610204080c06c01256536a10308102040e09c8092621d0810204060b640eff5a4aaee4f07bd6ead6ece3ed4010408102030ac8092326cf42e4e800081e504bc757e394b271120408080af7bd90102040810584040495900d11104081020f0b580dfa45806020408105844c00b1d176174080102040894dfa4580202040810584840495908d23104081020e0ffddcb0e10204080c03202bdd7bb73fff0eb6e6bf57c99939d4280000102a309f8bad76889bb2f0102045612f0d6f995601d4b8000810105949401437765020408ac21a0a4aca1ea4c0204088c29a0a48c99bb5b132040607181deeb4555dd9a0e7eda5add5bfc210e24408000812104949421627649020408ac2fe0adf3eb1b7b02010204461150524649da3d091020b0b2c08592f2b2b5babdf2231d4f800001023b155052761aac6b112040e0d8025ee8786c71cf234080c07e059494fd66eb6604081038aa809272546e0f234080c0ae0594945dc7eb72040810389e40ef75a7aa9e4d4f7cdb5a5d3bded33d8900010204f624a0a4ec294d77214080c08905bc75fec401783c0102047622a0a4ec2448d72040804082809292908219081020b07d012565fb19ba0101020462047aaf3755757d1ae8416bf53866388310204080c066049494cd446550020408e40b78eb7c7e4626244080c0160494942da464460204086c44a0f77a555537a6711fb5560f3732ba310910204020484049090ac328040810d8ba80b7ce6f3d41f313204020434049c9c8c114040810d8858092b28b185d82000102271750524e1e8101081020b01f81deeb4955dd9f6ef4bab5bab99fdbb909010204081c4b40493996b4e71020406000016f9d1f206457244080c01104949423207b04010204461150524649da3d091020b0ae8092b2aeafd3091020309c80173a0e17b90b1320406071012565715207122040606c012565ecfcdd9e0001024b0828294b283a8300010204be16e8bdde55d5fbbf5feeb656cff1102040800081ab08282957d1f2b304081020f0bd02de3affbd447e8000010204be474049b12204081020b0a88092b228a7c308102030a480923264ec2e4d800081f5047aaf1755756b7ac2d3d6eade7a4f733201020408ec514049d963aaee44800081130a78ebfc09f13d9a0001023b1150527612a46b1020402045e0424979d95add4e99cd1c04081020b00d0125651b3999920001029b11f042c7cd446550020408c40a2829b1d1188c000102db145052b6999ba90910209024a0a424a56116020408ec40a0f7ba5355cfa6abbc6dadaeede05aae4080000102471450528e88ed510408101845c05be74749da3d091020b08e8092b28eab5309102030b480923274fc2e4f800081d9024aca6c420710204080c04581deeb4d555d9ffef307add5634a0408102040e0b2024aca65a5fc1c010204085c5ac05be72f4de50709102040e003024a8ab52040800081c5057aaf575575633af8516bf570f187389000010204762ba0a4ec365a17234080c0e904bc75fe74f69e4c8000813d0828297b48d11d0810201026a0a48405621c0204086c4c4049d95860c6254080c016047aaf2755757f9af5756b75730b739b9100010204320494948c1c4c418000815d0978ebfcaee274190204081c5d4049393ab90712204060ff024acafe337643020408ac29a0a4aca9eb6c0204080c2ce0858e0387efea0408109829a0a4cc04f47102040810f8b08092623308102040e0500125e550399f2340800081ef14e8bdde55d5fbbf67eeb656cf91112040800081cb0828299751f233040810207065016f9dbf32990f10204080c024a0a4580502040810584540495985d5a10408101842404919226697244080c0f1057aaf1755756b7af2d3d6eadef1a7f04402040810d8a28092b2c5d4cc4c8000810d0878ebfc0642322201020442059494d0608c45800081ad0b5c28292f5babdb5bbf93f909102040e038024aca719c3d85000102c30978a1e37091bb300102041613505216a37410010204089c175052ec03010204081c2aa0a41c2ae77304081020f09d02bdd79daa7a36fdd0dbd6ea1a32020408102070190125e5324a7e86000102040e12f0d6f983d87c88000102c30b2829c3af0000020408ac27a0a4ac67eb64020408ec594049d973baee46800081130bf45e6faaeafa34c683d6eaf18947f27802040810d8808092b281908c48800081ad0a78ebfc569333370102044e2ba0a49cd6dfd3091020b06b81deeb5555dd982ef9a8b57ab8eb0bbb1c010204082c22a0a42cc2e81002040810f89080b7cedb0b02040810384440493944cd6708102040e052024acaa598fc10010204085c105052ac0401020408ac26d07b3da9aafbd3035eb75637577b9883091020406037024aca6ea27411020408e40978eb7c5e2626224080c0160494942da464460204086c544049d96870c6264080c0890594941307e0f1040810584aa0f7fa7d557d59559fb7f6d5bf46fcf142c788180c418000814d0928299b8acbb0040810f8b040eff5dbaafacbb9fff6af55f5b4b5fafcd4664acaa913f07c0204086c4f4049d95e6626264080c0b7042e7cadeafc7fff9fb3b252557f6eadcefefdd1fff45eefaaeafddf37775babe7471fc203091020406053024acaa6e2322c0102043e2ed07bfdbaaafe50553ffbc84fbdacaac7c7fe3a98b7cedb5a02040810b8aa80927255313f4f8000817081deebc755f559d557a5e5471f19f7685f075352c217c67804081008145052024331120102049612987ebb7256567ef3913357ff3a58eff5a2aa6e4dcf3ffbdfc9dc5bea7ece21408000817d0a2829fbccd5ad081020f00d81deeb93e9372b67bf6139ead7c1bc75de3212204080c055059494ab8af9790204086c5ce0d85f07bb50525eb656b7374e687c02040810585940495919d8f1040810481638c6d7c1bcd0317903cc468000814c012525331753112040e0a8026b7e1d4c49396a941e468000815d082829bb88d125081020b09cc0d25f07ebbdee54d5b369c2b7add5b5e5a6751201020408ec514049d963aaee44800081850496fa3a98b7ce2f148863081020308880923248d0ae49800081390273bf0ea6a4ccd1f7590204088c27a0a48c97b91b1320406096c0215f07ebbdde54d5f5e9c10f5afbeacdf7fe1020408000810f0a28291683000102040e16b8c2d7c17e57553f50520ea6f6410204080c25a0a40c15b7cb122040601d814b7e1decfdc31fb5560fd799c4a904081020b0070125650f29ba0301020482042ef175b02f5aab4f8346360a0102040884092829618118870001027b12f8c8d7c1fc26654f21bb0b0102045610505256407524010204087c5360fa3ad81fcffed3d6eaec7f9fe20f0102040810f8a880926239081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0');         
INSERT INTO SYSTEM_LOB_STREAM VALUES(0, 2, NULL, 'a4d8010204081020408000010204a2049494a8380c4380000102040810204080809262070810204080b4a9cf33000005a44944415400010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a204fe073192f0025b8162250000000049454e44ae426082');       
INSERT INTO SYSTEM_LOB_STREAM VALUES(1, 0, NULL, '89504e470d0a1a0a0000000d4948445200000329000001f20806000000ef0c81af0000200049444154785eedddb1ae56591906e06f1514241663079526da0b8d5a409c4b902bd0047b8792ce92c244ec49f412983b700285dac0056862079d535111963993cd78e60ccc9cf3efbdffffdd7b3d341a3dffdadf7ade2f21ef9c5f772b7f0810204080000102040810201024d08266310a0102040810204080000102044a49b10404081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b40800001020408');         
INSERT INTO SYSTEM_LOB_STREAM VALUES(1, 1, NULL, '10204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102b3047aafbf9f1dd05afd72d6413e4c8000010204260125c52a10204080c0c102bdd73faaeae7d301ff6cad7e71f0613e48800001020494143b408000010273057aaf7f55d54fa673fedd5afd74ee993e4f8000010204fc26c50e10204080c0c102bdd793aaba3f1df0bab5ba79f0613e488000010204fc26c50e10204080c05c81deebb3aafad374ce97add50fe79ee9f30408102040c06f52ec00010204081c2ca0a41c4ce78304081020f01d024a8af520408000815902bd577f7f406be5ef95599a3e4c800001026702fe32b10704081020304b404999c5e7c304081020f0010125c55a10204080c02c81deebddb97fe875b7b57a3eeb401f2640800081e1059494e1570000010204e609f45effadaa4fa6531eb4568fe79de8d30408102030ba809232fa06b83f010204660a282933017d9c00010204be25a0a4580a020408109825d07bbda8aa5bd3214f5bab7bb30ef46102040810185e4049197e0500102040609e40eff5b7aafad574ca17add5a7f34ef46902040810185d4049197d03dc9f00010233052e949497add5ed9947fa38010204080c2ea0a40cbe00ae4f800081b9025ee83857d0e709102040e0a2809262270810204060968092328bcf8709102040e003024a8ab520408000815902bdd79daa7a361df2b6b5ba36eb401f2640800081e1059494e1570000010204e60b78ebfc7c432710204080c0ff059414db4080000102b3059494d9840e204080008173024a8a752040800081d902bdd79baaba3e1de4adf3b3451d40800081b1059494b1f3777b0204082c22e0adf38b303a8400010204260125c52a10204080c06c81deeb5555dd980e7ad45a3d9c7da803081020406058012565d8e85d9c000102cb0978ebfc72964e22408000812a25c51610204080c06c01256536a10308102040e09c8092621d0810204060b640eff5a4aaee4f07bd6ead6ece3ed4010408102030ac8092326cf42e4e800081e504bc757e394b271120408080af7bd90102040810584040495900d11104081020f0b580dfa45806020408105844c00b1d176174080102040894dfa4580202040810584840495908d23104081020e0ffddcb0e10204080c03202bdd7bb73fff0eb6e6bf57c99939d4280000102a309f8bad76889bb2f0102045612f0d6f995601d4b8000810105949401437765020408ac21a0a4aca1ea4c0204088c29a0a48c99bb5b132040607181deeb4555dd9a0e7eda5add5bfc210e24408000812104949421627649020408ac2fe0adf3eb1b7b02010204461150524649da3d091020b0b2c08592f2b2b5babdf2231d4f800001023b155052761aac6b112040e0d8025ee8786c71cf234080c07e059494fd66eb6604081038aa809272546e0f234080c0ae0594945dc7eb72040810389e40ef75a7aa9e4d4f7cdb5a5d3bded33d8900010204f624a0a4ec294d77214080c08905bc75fec401783c0102047622a0a4ec2448d72040804082809292908219081020b07d012565fb19ba0101020462047aaf3755757d1ae8416bf53866388310204080c066049494cd446550020408e40b78eb7c7e4626244080c0160494942da464460204086c44a0f77a555537a6711fb5560f3732ba310910204020484049090ac328040810d8ba80b7ce6f3d41f313204020434049c9c8c114040810d8858092b28b185d82000102271750524e1e8101081020b01f81deeb4955dd9f6ef4bab5bab99fdbb909010204081c4b40493996b4e71020406000016f9d1f206457244080c01104949423207b04010204461150524649da3d091020b0ae8092b2aeafd3091020309c80173a0e17b90b1320406071012565715207122040606c012565ecfcdd9e0001024b0828294b283a8300010204be16e8bdde55d5fbbf5feeb656cff1102040800081ab08282957d1f2b304081020f0bd02de3affbd447e8000010204be474049b12204081020b0a88092b228a7c308102030a480923264ec2e4d800081f5047aaf1755756b7ac2d3d6eade7a4f733201020408ec514049d963aaee44800081130a78ebfc09f13d9a0001023b1150527612a46b1020402045e0424979d95add4e99cd1c04081020b00d0125651b3999920001029b11f042c7cd446550020408c40a2829b1d1188c000102db145052b6999ba90910209024a0a424a56116020408ec40a0f7ba5355cfa6abbc6dadaeede05aae4080000102471450528e88ed510408101845c05be74749da3d091020b08e8092b28eab5309102030b480923274fc2e4f800081d9024aca6c420710204080c04581deeb4d555d9ffef307add5634a0408102040e0b2024aca65a5fc1c010204085c5ac05be72f4de50709102040e003024a8ab52040800081c5057aaf575575633af8516bf570f187389000010204762ba0a4ec365a17234080c0e904bc75fe74f69e4c8000813d0828297b48d11d0810201026a0a48405621c0204086c4c4049d95860c6254080c016047aaf2755757f9af5756b75730b739b9100010204320494948c1c4c418000815d0978ebfcaee274190204081c5d4049393ab90712204060ff024acafe337643020408ac29a0a4aca9eb6c0204080c2ce0858e0387efea0408109829a0a4cc04f47102040810f8b08092623308102040e0500125e550399f2340800081ef14e8bdde55d5fbbf67eeb656cf91112040800081cb0828299751f233040810207065016f9dbf32990f10204080c024a0a4580502040810584540495985d5a10408101842404919226697244080c0f1057aaf1755756b7af2d3d6eadef1a7f04402040810d8a28092b2c5d4cc4c8000810d0878ebfc0642322201020442059494d0608c45800081ad0b5c28292f5babdb5bbf93f909102040e038024aca719c3d85000102c30978a1e37091bb300102041613505216a37410010204089c175052ec03010204081c2aa0a41c2ae77304081020f09d02bdd79daa7a36fdd0dbd6ea1a32020408102070190125e5324a7e86000102040e12f0d6f983d87c88000102c30b2829c3af0000020408ac27a0a4ac67eb64020408ec594049d973baee46800081130bf45e6faaeafa34c683d6eaf18947f27802040810d8808092b281908c48800081ad0a78ebfc569333370102044e2ba0a49cd6dfd3091020b06b81deeb5555dd982ef9a8b57ab8eb0bbb1c010204082c22a0a42cc2e81002040810f89080b7cedb0b02040810384440493944cd6708102040e052024acaa598fc10010204085c105052ac0401020408ac26d07b3da9aafbd3035eb75637577b9883091020406037024aca6ea27411020408e40978eb7c5e2626224080c0160494942da464460204086c544049d96870c6264080c0890594941307e0f1040810584aa0f7fa7d557d59559fb7f6d5bf46fcf142c788180c418000814d0928299b8acbb0040810f8b040eff5dbaafacbb9fff6af55f5b4b5fafcd4664acaa913f07c0204086c4f4049d95e6626264080c0b7042e7cadeafc7fff9fb3b252557f6eadcefefdd1fff45eefaaeafddf37775babe7471fc203091020406053024acaa6e2322c0102043e2ed07bfdbaaafe50553ffbc84fbdacaac7c7fe3a98b7cedb5a02040810b8aa80927255313f4f8000817081deebc755f559d557a5e5471f19f7685f075352c217c67804081008145052024331120102049612987ebb7256567ef3913357ff3a58eff5a2aa6e4dcf3ffbdfc9dc5bea7ece21408000817d0a2829fbccd5ad081020f00d81deeb93e9372b67bf6139ead7c1bc75de3212204080c055059494ab8af9790204086c5ce0d85f07bb50525eb656b7374e687c02040810585940495919d8f1040810481638c6d7c1bcd0317903cc468000814c012525331753112040e0a8026b7e1d4c49396a941e468000815d082829bb88d125081020b09cc0d25f07ebbdee54d5b369c2b7add5b5e5a6751201020408ec514049d963aaee44800081850496fa3a98b7ce2f148863081020308880923248d0ae49800081390273bf0ea6a4ccd1f7590204088c27a0a48c97b91b1320406096c0215f07ebbdde54d5f5e9c10f5afbeacdf7fe1020408000810f0a28291683000102040e16b8c2d7c17e57553f50520ea6f6410204080c25a0a40c15b7cb122040601d814b7e1decfdc31fb5560fd799c4a904081020b0070125650f29ba0301020482042ef175b02f5aab4f8346360a0102040884092829618118870001027b12f8c8d7c1fc26654f21bb0b0102045610505256407524010204087c5360fa3ad81fcffed3d6eaec7f9fe20f0102040810f8a880926239081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0');         
INSERT INTO SYSTEM_LOB_STREAM VALUES(1, 2, NULL, 'a4d8010204081020408000010204a2049494a8380c4380000102040810204080809262070810204080b4a9cf33000005a44944415400010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a204fe073192f0025b8162250000000049454e44ae426082');       
INSERT INTO SYSTEM_LOB_STREAM VALUES(2, 0, NULL, '89504e470d0a1a0a0000000d4948445200000329000001f20806000000ef0c81af0000200049444154785eedddb1ae56591906e06f1514241663079526da0b8d5a409c4b902bd0047b8792ce92c244ec49f412983b700285dac0056862079d535111963993cd78e60ccc9cf3efbdffffdd7b3d341a3dffdadf7ade2f21ef9c5f772b7f0810204080000102040810201024d08266310a0102040810204080000102044a49b10404081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b40800001020408');         
INSERT INTO SYSTEM_LOB_STREAM VALUES(2, 1, NULL, '10204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102b3047aafbf9f1dd05afd72d6413e4c8000010204260125c52a10204080c0c102bdd73faaeae7d301ff6cad7e71f0613e48800001020494143b408000010273057aaf7f55d54fa673fedd5afd74ee993e4f8000010204fc26c50e10204080c0c102bdd793aaba3f1df0bab5ba79f0613e488000010204fc26c50e10204080c05c81deebb3aafad374ce97add50fe79ee9f30408102040c06f52ec00010204081c2ca0a41c4ce78304081020f01d024a8af520408000815902bd577f7f406be5ef95599a3e4c800001026702fe32b10704081020304b404999c5e7c304081020f0010125c55a10204080c02c81deebddb97fe875b7b57a3eeb401f2640800081e1059494e1570000010204e609f45effadaa4fa6531eb4568fe79de8d30408102030ba809232fa06b83f010204660a282933017d9c00010204be25a0a4580a020408109825d07bbda8aa5bd3214f5bab7bb30ef46102040810185e4049197e0500102040609e40eff5b7aafad574ca17add5a7f34ef46902040810185d4049197d03dc9f00010233052e949497add5ed9947fa38010204080c2ea0a40cbe00ae4f800081b9025ee83857d0e709102040e0a2809262270810204060968092328bcf8709102040e003024a8ab520408000815902bdd79daa7a361df2b6b5ba36eb401f2640800081e1059494e1570000010204e60b78ebfc7c432710204080c0ff059414db4080000102b3059494d9840e204080008173024a8a752040800081d902bdd79baaba3e1de4adf3b3451d40800081b1059494b1f3777b0204082c22e0adf38b303a8400010204260125c52a10204080c06c81deeb5555dd980e7ad45a3d9c7da803081020406058012565d8e85d9c000102cb0978ebfc72964e22408000812a25c51610204080c06c01256536a10308102040e09c8092621d0810204060b640eff5a4aaee4f07bd6ead6ece3ed4010408102030ac8092326cf42e4e800081e504bc757e394b271120408080af7bd90102040810584040495900d11104081020f0b580dfa45806020408105844c00b1d176174080102040894dfa4580202040810584840495908d23104081020e0ffddcb0e10204080c03202bdd7bb73fff0eb6e6bf57c99939d4280000102a309f8bad76889bb2f0102045612f0d6f995601d4b8000810105949401437765020408ac21a0a4aca1ea4c0204088c29a0a48c99bb5b132040607181deeb4555dd9a0e7eda5add5bfc210e24408000812104949421627649020408ac2fe0adf3eb1b7b02010204461150524649da3d091020b0b2c08592f2b2b5babdf2231d4f800001023b155052761aac6b112040e0d8025ee8786c71cf234080c07e059494fd66eb6604081038aa809272546e0f234080c0ae0594945dc7eb72040810389e40ef75a7aa9e4d4f7cdb5a5d3bded33d8900010204f624a0a4ec294d77214080c08905bc75fec401783c0102047622a0a4ec2448d72040804082809292908219081020b07d012565fb19ba0101020462047aaf3755757d1ae8416bf53866388310204080c066049494cd446550020408e40b78eb7c7e4626244080c0160494942da464460204086c44a0f77a555537a6711fb5560f3732ba310910204020484049090ac328040810d8ba80b7ce6f3d41f313204020434049c9c8c114040810d8858092b28b185d82000102271750524e1e8101081020b01f81deeb4955dd9f6ef4bab5bab99fdbb909010204081c4b40493996b4e71020406000016f9d1f206457244080c01104949423207b04010204461150524649da3d091020b0ae8092b2aeafd3091020309c80173a0e17b90b1320406071012565715207122040606c012565ecfcdd9e0001024b0828294b283a8300010204be16e8bdde55d5fbbf5feeb656cff1102040800081ab08282957d1f2b304081020f0bd02de3affbd447e8000010204be474049b12204081020b0a88092b228a7c308102030a480923264ec2e4d800081f5047aaf1755756b7ac2d3d6eade7a4f733201020408ec514049d963aaee44800081130a78ebfc09f13d9a0001023b1150527612a46b1020402045e0424979d95add4e99cd1c04081020b00d0125651b3999920001029b11f042c7cd446550020408c40a2829b1d1188c000102db145052b6999ba90910209024a0a424a56116020408ec40a0f7ba5355cfa6abbc6dadaeede05aae4080000102471450528e88ed510408101845c05be74749da3d091020b08e8092b28eab5309102030b480923274fc2e4f800081d9024aca6c420710204080c04581deeb4d555d9ffef307add5634a0408102040e0b2024aca65a5fc1c010204085c5ac05be72f4de50709102040e003024a8ab52040800081c5057aaf575575633af8516bf570f187389000010204762ba0a4ec365a17234080c0e904bc75fe74f69e4c8000813d0828297b48d11d0810201026a0a48405621c0204086c4c4049d95860c6254080c016047aaf2755757f9af5756b75730b739b9100010204320494948c1c4c418000815d0978ebfcaee274190204081c5d4049393ab90712204060ff024acafe337643020408ac29a0a4aca9eb6c0204080c2ce0858e0387efea0408109829a0a4cc04f47102040810f8b08092623308102040e0500125e550399f2340800081ef14e8bdde55d5fbbf67eeb656cf91112040800081cb0828299751f233040810207065016f9dbf32990f10204080c024a0a4580502040810584540495985d5a10408101842404919226697244080c0f1057aaf1755756b7af2d3d6eadef1a7f04402040810d8a28092b2c5d4cc4c8000810d0878ebfc0642322201020442059494d0608c45800081ad0b5c28292f5babdb5bbf93f909102040e038024aca719c3d85000102c30978a1e37091bb300102041613505216a37410010204089c175052ec03010204081c2aa0a41c2ae77304081020f09d02bdd79daa7a36fdd0dbd6ea1a32020408102070190125e5324a7e86000102040e12f0d6f983d87c88000102c30b2829c3af0000020408ac27a0a4ac67eb64020408ec594049d973baee46800081130bf45e6faaeafa34c683d6eaf18947f27802040810d8808092b281908c48800081ad0a78ebfc569333370102044e2ba0a49cd6dfd3091020b06b81deeb5555dd982ef9a8b57ab8eb0bbb1c010204082c22a0a42cc2e81002040810f89080b7cedb0b02040810384440493944cd6708102040e052024acaa598fc10010204085c105052ac0401020408ac26d07b3da9aafbd3035eb75637577b9883091020406037024aca6ea27411020408e40978eb7c5e2626224080c0160494942da464460204086c544049d96870c6264080c0890594941307e0f1040810584aa0f7fa7d557d59559fb7f6d5bf46fcf142c788180c418000814d0928299b8acbb0040810f8b040eff5dbaafacbb9fff6af55f5b4b5fafcd4664acaa913f07c0204086c4f4049d95e6626264080c0b7042e7cadeafc7fff9fb3b252557f6eadcefefdd1fff45eefaaeafddf37775babe7471fc203091020406053024acaa6e2322c0102043e2ed07bfdbaaafe50553ffbc84fbdacaac7c7fe3a98b7cedb5a02040810b8aa80927255313f4f8000817081deebc755f559d557a5e5471f19f7685f075352c217c67804081008145052024331120102049612987ebb7256567ef3913357ff3a58eff5a2aa6e4dcf3ffbdfc9dc5bea7ece21408000817d0a2829fbccd5ad081020f00d81deeb93e9372b67bf6139ead7c1bc75de3212204080c055059494ab8af9790204086c5ce0d85f07bb50525eb656b7374e687c02040810585940495919d8f1040810481638c6d7c1bcd0317903cc468000814c012525331753112040e0a8026b7e1d4c49396a941e468000815d082829bb88d125081020b09cc0d25f07ebbdee54d5b369c2b7add5b5e5a6751201020408ec514049d963aaee44800081850496fa3a98b7ce2f148863081020308880923248d0ae49800081390273bf0ea6a4ccd1f7590204088c27a0a48c97b91b1320406096c0215f07ebbdde54d5f5e9c10f5afbeacdf7fe1020408000810f0a28291683000102040e16b8c2d7c17e57553f50520ea6f6410204080c25a0a40c15b7cb122040601d814b7e1decfdc31fb5560fd799c4a904081020b0070125650f29ba0301020482042ef175b02f5aab4f8346360a0102040884092829618118870001027b12f8c8d7c1fc26654f21bb0b0102045610505256407524010204087c5360fa3ad81fcffed3d6eaec7f9fe20f0102040810f8a880926239081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0');         
INSERT INTO SYSTEM_LOB_STREAM VALUES(2, 2, NULL, 'a4d8010204081020408000010204a2049494a8380c4380000102040810204080809262070810204080b4a9cf33000005a44944415400010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a204fe073192f0025b8162250000000049454e44ae426082');       
INSERT INTO SYSTEM_LOB_STREAM VALUES(3, 0, NULL, '89504e470d0a1a0a0000000d4948445200000329000001f20806000000ef0c81af0000200049444154785eedddb1ae56591906e06f1514241663079526da0b8d5a409c4b902bd0047b8792ce92c244ec49f412983b700285dac0056862079d535111963993cd78e60ccc9cf3efbdffffdd7b3d341a3dffdadf7ade2f21ef9c5f772b7f0810204080000102040810201024d08266310a0102040810204080000102044a49b10404081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102040810204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b40800001020408');         
INSERT INTO SYSTEM_LOB_STREAM VALUES(3, 1, NULL, '10204080409480921215876108102040800001020408105052ec00010204081020408000010251024a4a541c8621408000010204081020404049b10304081020408000010204084409282951711886000102040810204080000125c50e10204080000102040810201025a0a444c56118020408102040800001020494143b4080000102b3047aafbf9f1dd05afd72d6413e4c8000010204260125c52a10204080c0c102bdd73faaeae7d301ff6cad7e71f0613e48800001020494143b408000010273057aaf7f55d54fa673fedd5afd74ee993e4f8000010204fc26c50e10204080c0c102bdd793aaba3f1df0bab5ba79f0613e488000010204fc26c50e10204080c05c81deebb3aafad374ce97add50fe79ee9f30408102040c06f52ec00010204081c2ca0a41c4ce78304081020f01d024a8af520408000815902bd577f7f406be5ef95599a3e4c800001026702fe32b10704081020304b404999c5e7c304081020f0010125c55a10204080c02c81deebddb97fe875b7b57a3eeb401f2640800081e1059494e1570000010204e609f45effadaa4fa6531eb4568fe79de8d30408102030ba809232fa06b83f010204660a282933017d9c00010204be25a0a4580a020408109825d07bbda8aa5bd3214f5bab7bb30ef46102040810185e4049197e0500102040609e40eff5b7aafad574ca17add5a7f34ef46902040810185d4049197d03dc9f00010233052e949497add5ed9947fa38010204080c2ea0a40cbe00ae4f800081b9025ee83857d0e709102040e0a2809262270810204060968092328bcf8709102040e003024a8ab520408000815902bdd79daa7a361df2b6b5ba36eb401f2640800081e1059494e1570000010204e60b78ebfc7c432710204080c0ff059414db4080000102b3059494d9840e204080008173024a8a752040800081d902bdd79baaba3e1de4adf3b3451d40800081b1059494b1f3777b0204082c22e0adf38b303a8400010204260125c52a10204080c06c81deeb5555dd980e7ad45a3d9c7da803081020406058012565d8e85d9c000102cb0978ebfc72964e22408000812a25c51610204080c06c01256536a10308102040e09c8092621d0810204060b640eff5a4aaee4f07bd6ead6ece3ed4010408102030ac8092326cf42e4e800081e504bc757e394b271120408080af7bd90102040810584040495900d11104081020f0b580dfa45806020408105844c00b1d176174080102040894dfa4580202040810584840495908d23104081020e0ffddcb0e10204080c03202bdd7bb73fff0eb6e6bf57c99939d4280000102a309f8bad76889bb2f0102045612f0d6f995601d4b8000810105949401437765020408ac21a0a4aca1ea4c0204088c29a0a48c99bb5b132040607181deeb4555dd9a0e7eda5add5bfc210e24408000812104949421627649020408ac2fe0adf3eb1b7b02010204461150524649da3d091020b0b2c08592f2b2b5babdf2231d4f800001023b155052761aac6b112040e0d8025ee8786c71cf234080c07e059494fd66eb6604081038aa809272546e0f234080c0ae0594945dc7eb72040810389e40ef75a7aa9e4d4f7cdb5a5d3bded33d8900010204f624a0a4ec294d77214080c08905bc75fec401783c0102047622a0a4ec2448d72040804082809292908219081020b07d012565fb19ba0101020462047aaf3755757d1ae8416bf53866388310204080c066049494cd446550020408e40b78eb7c7e4626244080c0160494942da464460204086c44a0f77a555537a6711fb5560f3732ba310910204020484049090ac328040810d8ba80b7ce6f3d41f313204020434049c9c8c114040810d8858092b28b185d82000102271750524e1e8101081020b01f81deeb4955dd9f6ef4bab5bab99fdbb909010204081c4b40493996b4e71020406000016f9d1f206457244080c01104949423207b04010204461150524649da3d091020b0ae8092b2aeafd3091020309c80173a0e17b90b1320406071012565715207122040606c012565ecfcdd9e0001024b0828294b283a8300010204be16e8bdde55d5fbbf5feeb656cff1102040800081ab08282957d1f2b304081020f0bd02de3affbd447e8000010204be474049b12204081020b0a88092b228a7c308102030a480923264ec2e4d800081f5047aaf1755756b7ac2d3d6eade7a4f733201020408ec514049d963aaee44800081130a78ebfc09f13d9a0001023b1150527612a46b1020402045e0424979d95add4e99cd1c04081020b00d0125651b3999920001029b11f042c7cd446550020408c40a2829b1d1188c000102db145052b6999ba90910209024a0a424a56116020408ec40a0f7ba5355cfa6abbc6dadaeede05aae4080000102471450528e88ed510408101845c05be74749da3d091020b08e8092b28eab5309102030b480923274fc2e4f800081d9024aca6c420710204080c04581deeb4d555d9ffef307add5634a0408102040e0b2024aca65a5fc1c010204085c5ac05be72f4de50709102040e003024a8ab52040800081c5057aaf575575633af8516bf570f187389000010204762ba0a4ec365a17234080c0e904bc75fe74f69e4c8000813d0828297b48d11d0810201026a0a48405621c0204086c4c4049d95860c6254080c016047aaf2755757f9af5756b75730b739b9100010204320494948c1c4c418000815d0978ebfcaee274190204081c5d4049393ab90712204060ff024acafe337643020408ac29a0a4aca9eb6c0204080c2ce0858e0387efea0408109829a0a4cc04f47102040810f8b08092623308102040e0500125e550399f2340800081ef14e8bdde55d5fbbf67eeb656cf91112040800081cb0828299751f233040810207065016f9dbf32990f10204080c024a0a4580502040810584540495985d5a10408101842404919226697244080c0f1057aaf1755756b7af2d3d6eadef1a7f04402040810d8a28092b2c5d4cc4c8000810d0878ebfc0642322201020442059494d0608c45800081ad0b5c28292f5babdb5bbf93f909102040e038024aca719c3d85000102c30978a1e37091bb300102041613505216a37410010204089c175052ec03010204081c2aa0a41c2ae77304081020f09d02bdd79daa7a36fdd0dbd6ea1a32020408102070190125e5324a7e86000102040e12f0d6f983d87c88000102c30b2829c3af0000020408ac27a0a4ac67eb64020408ec594049d973baee46800081130bf45e6faaeafa34c683d6eaf18947f27802040810d8808092b281908c48800081ad0a78ebfc569333370102044e2ba0a49cd6dfd3091020b06b81deeb5555dd982ef9a8b57ab8eb0bbb1c010204082c22a0a42cc2e81002040810f89080b7cedb0b02040810384440493944cd6708102040e052024acaa598fc10010204085c105052ac0401020408ac26d07b3da9aafbd3035eb75637577b9883091020406037024aca6ea27411020408e40978eb7c5e2626224080c0160494942da464460204086c544049d96870c6264080c0890594941307e0f1040810584aa0f7fa7d557d59559fb7f6d5bf46fcf142c788180c418000814d0928299b8acbb0040810f8b040eff5dbaafacbb9fff6af55f5b4b5fafcd4664acaa913f07c0204086c4f4049d95e6626264080c0b7042e7cadeafc7fff9fb3b252557f6eadcefefdd1fff45eefaaeafddf37775babe7471fc203091020406053024acaa6e2322c0102043e2ed07bfdbaaafe50553ffbc84fbdacaac7c7fe3a98b7cedb5a02040810b8aa80927255313f4f8000817081deebc755f559d557a5e5471f19f7685f075352c217c67804081008145052024331120102049612987ebb7256567ef3913357ff3a58eff5a2aa6e4dcf3ffbdfc9dc5bea7ece21408000817d0a2829fbccd5ad081020f00d81deeb93e9372b67bf6139ead7c1bc75de3212204080c055059494ab8af9790204086c5ce0d85f07bb50525eb656b7374e687c02040810585940495919d8f1040810481638c6d7c1bcd0317903cc468000814c012525331753112040e0a8026b7e1d4c49396a941e468000815d082829bb88d125081020b09cc0d25f07ebbdee54d5b369c2b7add5b5e5a6751201020408ec514049d963aaee44800081850496fa3a98b7ce2f148863081020308880923248d0ae49800081390273bf0ea6a4ccd1f7590204088c27a0a48c97b91b1320406096c0215f07ebbdde54d5f5e9c10f5afbeacdf7fe1020408000810f0a28291683000102040e16b8c2d7c17e57553f50520ea6f6410204080c25a0a40c15b7cb122040601d814b7e1decfdc31fb5560fd799c4a904081020b0070125650f29ba0301020482042ef175b02f5aab4f8346360a0102040884092829618118870001027b12f8c8d7c1fc26654f21bb0b0102045610505256407524010204087c5360fa3ad81fcffed3d6eaec7f9fe20f0102040810f8a880926239081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0');         
INSERT INTO SYSTEM_LOB_STREAM VALUES(3, 2, NULL, 'a4d8010204081020408000010204a2049494a8380c4380000102040810204080809262070810204080b4a9cf33000005a44944415400010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a2049494a8380c438000010204081020408080926207081020408000010204081088125052a2e2300c01020408102040800001024a8a1d2040800001020408102040204a4049898ac3300408102040800001020408282976800001020408102040800081280125252a0ec31020408000010204081020a0a4d8010204081020408000010204a204fe073192f0025b8162250000000049454e44ae426082');       
INSERT INTO PUBLIC.ANSWERASSET(_ID, QUESTIONID, QUESTIONVERSIONNUMBER, TESTDAYENTRYID, REFERENCENAME, _BLOB) VALUES
(1, 47, 1, 5, 'Candidate null answer for question id: 47 version no: 1 ', SYSTEM_COMBINE_BLOB(0)),
(2, 47, 1, 1, 'Candidate null answer for question id: 47 version no: 1 ', SYSTEM_COMBINE_BLOB(1)),
(3, 47, 1, 3, 'Candidate null answer for question id: 47 version no: 1 ', SYSTEM_COMBINE_BLOB(2)),
(4, 47, 1, 2, 'Candidate null answer for question id: 47 version no: 1 ', SYSTEM_COMBINE_BLOB(3));           
DROP TABLE IF EXISTS SYSTEM_LOB_STREAM;       
CALL SYSTEM_COMBINE_BLOB(-1); 
DROP ALIAS IF EXISTS SYSTEM_COMBINE_CLOB;     
DROP ALIAS IF EXISTS SYSTEM_COMBINE_BLOB;     
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
