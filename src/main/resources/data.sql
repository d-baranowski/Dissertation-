INSERT INTO Role VALUES ('Author');
INSERT INTO Role VALUES ('Marker');
INSERT INTO Role VALUES ('Admin');
INSERT INTO Role VALUES ('ModuleLeader');

INSERT INTO User VALUES ('3ca33b4f-009a-4403-829b-e2d20b3d47c2','Bob','Smith','sampleMarker','pass');
INSERT INTO User VALUES ('fba6a561-8999-4b19-9c57-232895d024c6','Grzegorz','Brzenczyszczykiewicz','sampleAuthor','pass');
INSERT INTO User VALUES ('94cbbbc4-f94d-40d2-b0cf-e642eb36e73a','Sam','Armstrong','sampleAdmin','pass');
INSERT INTO User VALUES ('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d','Jack','Brown','sampleAll','pass');

INSERT INTO UserRole VALUES ('3ca33b4f-009a-4403-829b-e2d20b3d47c2','Marker');
INSERT INTO UserRole VALUES ('fba6a561-8999-4b19-9c57-232895d024c6','Author');
INSERT INTO UserRole VALUES ('94cbbbc4-f94d-40d2-b0cf-e642eb36e73a','Admin');

INSERT INTO UserRole VALUES ('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d','Marker');
INSERT INTO UserRole VALUES ('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d','Author');
INSERT INTO UserRole VALUES ('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d','Admin');
INSERT INTO UserRole VALUES ('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d','ModuleLeader');

INSERT INTO TestPaper(referenceName, timeAllowed) VALUES ('ALTERNATE INTERVIEW JAVA', 60);
INSERT INTO TestPaperVersion(authorId, versionNumber, testPaperId, instructionsText) VALUES ('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 1, 1, '<b>Normal time Allowed: Up to 1 Hour </br>(all times indicative only)</b></br>Your interviewer will idicate: </br><ul><li> which questions of this set you should complete. The selected set will be relvant to your knowledge and experience</li><li>the time that you have to complete these in.</li></ul></br>The quelity / correctness of your answers is more important than the ammount of quetions answered. A timescale is provided with each question as a rough guide for how long the question should take to complete.');
INSERT INTO TestPaperSection(referenceName) VALUES ('ALTERNATE INTERVIEW JAVA QUESTIONS');
INSERT INTO TestPaperSectionVersion(noOfQuestionsToAnswer, versionNumber,timeScale,testPaperSectionId) VALUES (14,1,60,1);
INSERT INTO QuestionType VALUES ('Code');
INSERT INTO QuestionType VALUES ('Essay');
INSERT INTO QuestionType VALUES ('Multiple Choice');
INSERT INTO QuestionType VALUES ('Drawing');
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Develop isEven()', 'Code', 5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Develop isDateInvalid()', 'Code', 5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Ask about output of class NewTester', 'Essay',5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Ask about output of class NewTester', 'Essay',5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Ask about output of test1 and test2', 'Essay',5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Explain output of class NewTester', 'Essay',5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Develop singleton design pattern', 'Code',5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Explain array index out of bounds', 'Essay',5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Explain a switch', 'Essay',5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Explain double equality problem', 'Essay',5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Explain class ValHold', 'Essay',5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Explain a thread safety', 'Essay',5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Identify compilation errors', 'Essay', 5);
INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('Java', 'Explain appearance of a frame', 'Essay',5 );
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale) VALUES (1, 1,'You have been asked to develop a function called IsEven that return true if a given integer parameter is even, or false if odd. Write this function below.', 5);
INSERT INTO QuestionVersionEntry VALUES (1,1,1,1,1);

INSERT INTO TestPaperSectionVersionEntry VALUES (1,1,1,1,1);

INSERT INTO TestPaper(referenceName, timeAllowed) VALUES ('Interview Test-Graduate (C#)', 60);
INSERT INTO TestPaperVersion(authorId, versionNumber, testPaperId, instructionsText) VALUES ('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 1, 2, '<b>Normal time Allowed: Up to 60 Minutes </br>(all times indicative only)</b></br>Answer as many questions as you can. If you are unsure, it is better to skip the question completely rather than provide an incorrect answer. An indicative time is provided with each question as a rough guide for how long the question should take to complete.');

INSERT INTO TestPaperSection(referenceName) VALUES ('C# Language');
INSERT INTO TestPaperSectionVersion(noOfQuestionsToAnswer, versionNumber,timeScale,testPaperSectionId) VALUES (8,1,22,2);
INSERT INTO TestPaperSectionVersionEntry VALUES (1,2,1,2,1);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Access a private variable', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (15, 1,'Which classes can access a variable declared as private? </br> A) All classes. </br> B) Within the body of the class that encloses the declaration. </br> C)Inheriting sub classes. </br> D) Classes in the same namespace.', 1, 'B');
INSERT INTO QuestionVersionEntry VALUES (1,2,1,15,1);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Access a variable withoud modifier', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (16, 1,'Which classes can access a variable with no access modifier? </br> A) All classes.</br> B) Within the body of the class that encloses the declaration.</br> C) Inheriting sub classes</br> D) Classes in the same namespace.', 1, 'B');
INSERT INTO QuestionVersionEntry VALUES (1,2,1,16,2);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Truth about deadlocking', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (17, 1,'Which of the following statement is true about deadlocking? </br>A) It is not possible for more than two threads to deadlock at once.</br>B) Managed code language such as Java or C# guarantee that threads cannot enter a deadlocked state.</br>C) It is possible for a single threaded application to deadlock if synchronized blocks are used incorrectly.</br>D) None of the above.', 1, 'C');
INSERT INTO QuestionVersionEntry VALUES (1,2,1,17,3);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Storing key/value pairs', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (18, 1,'Which of the following C# objects is most suitable for storing general key/value pairs? </br>A) Dictionary</br>B) List</br>C) HashSet</br>D) IEnumerable', 1, 'A');
INSERT INTO QuestionVersionEntry VALUES (1,2,1,18,4);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Null reference exception', 'Essay', 5);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (19, 1,'Given the following fragment of C# code what will be happen?</br><pre class="prettyprint">bool flag = true;<br />Console.WriteLine( "0");<br />try {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( "1");<br />&nbsp;&nbsp;&nbsp; if (flag) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; object o = null;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; o.ToString();<br />&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( "2");<br />}<br />catch (NullReferenceException ex) {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( "3");<br />&nbsp;&nbsp;&nbsp; throw new ArgumentException( "", ex);<br />}<br />catch (Exception ex) {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( "4");<br />}<br />finally {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( "5");<br />}<br />Console.WriteLine( "6");</pre>', 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,2,1,19,5);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Output of tester class', 'Essay', 5);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (20, 1,'What will be the output of the following code?</br><pre class="prettyprint">public class Tester {<br />&nbsp;&nbsp;&nbsp; private int t = 1;<br />&nbsp;&nbsp;&nbsp; private static int p = 1;<br /><br />&nbsp;&nbsp;&nbsp; static void main(string[]args) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; for (int counter = 0; counter &lt; 5; counter++) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Tester tester = new Tester();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; tester.test();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; public void test() {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine("T " + t + " " + p);<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; t++;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; p++;<br />&nbsp;&nbsp;&nbsp; }<br />}</pre>', 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,2,1,20,6);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Compilation error', 'Essay', 3);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (21, 1,'What will happen when you attempt to compile and run the following code?<pre class="prettyprint">public class StringHolder {<br /><br />&nbsp;&nbsp;&nbsp; public StringHolder(string value) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Value = value;<br />&nbsp;&nbsp;&nbsp; }<br /><br />&nbsp;&nbsp;&nbsp; public string Value {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; get;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; set;<br />&nbsp;&nbsp;&nbsp; }<br />}<br /><br />public class EqualityExample {<br /><br />&nbsp;&nbsp;&nbsp; static void Main(string[]args) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var s = new StringHolder("Marcus");<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var s2 = new StringHolder("Marcus");<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if (s == s2) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine("we have a match");<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; } else {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine("not equal");<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; }<br />}</pre>', 3, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,2,1,21,7);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Compilation error', 'Essay', 5);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (22, 1,'Given the following code what will be the output?<pre class="prettyprint">public class Example {<br />&nbsp;&nbsp;&nbsp; public static void main(string[]args) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Example example = new Example();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; example.Method1();<br />&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; public void Method1() {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; int i = 99;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ValueHolder vh&nbsp;&nbsp; = new ValueHolder();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; vh.i = 30;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Method2(vh, i);<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine(vh.i);<br />&nbsp;&nbsp;&nbsp; }<br /><br />&nbsp;&nbsp;&nbsp; public void Method2(ValueHolder v, int i) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; i&nbsp;&nbsp; = 0;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; v.i = 20;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ValueHolder vh = new ValueHolder();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; v&nbsp; = vh;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine(v.i + " " + i);<br />&nbsp;&nbsp;&nbsp; }<br /><br />&nbsp;&nbsp;&nbsp; class ValueHolder {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public int i = 10;<br />&nbsp;&nbsp;&nbsp; }<br /><br />}</pre>', 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,2,1,22,8);

INSERT INTO TestPaperSection(referenceName) VALUES ('Problem Solving');
INSERT INTO TestPaperSectionVersion(noOfQuestionsToAnswer, versionNumber,timeScale,testPaperSectionId) VALUES (2,1,10,3);
INSERT INTO TestPaperSectionVersionEntry VALUES (1,3,1,2,2);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Common array elements', 'Code', 10);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (23, 1,'You have two arrays:</br>int[] = new int[]{ 1, 2, 3, 5, 4 };</br>int[] = new int[]{ 3, 2, 9, 3, 7 };</br>Write a method that returns a collection of common elements.</br>Please note that the arrays can contain repeating elements, and are not in any particular order.</br>Complete the method in the space below. Any necessary variables should be shown.</br>Extra credit will be awarded for solutions that are efficient (let''s say given very large input arrays).</br>Extra credit will be awarded if the method can handle any arbitrary number of arrays, i.e. a,b,c</br>', 10, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,3,1,23,1);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Develop isOdd', 'Code', 5);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (24, 1,'You have been asked to develop a function called IsOdd that returns true if a given integer parameter is odd, or false if even. Write this function below.', 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,3,1,24,2);

INSERT INTO Question(language, referenceName, questionTypeId, difficulty) VALUES ('C#', 'Objected Orientated Design', 'Drawing', 5);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (25, 1,'The classes shown in Error! Reference source not found. have been identified within the domain space for an internet electrical shopping site that deliver their goods in distinctive brightly coloured lorries and vans.Use object orientated theory to make the domain objects more efficient for software implementation.', 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,3,1,25,3);

INSERT INTO TestPaperSection(referenceName) VALUES ('Architecture and Theory');
INSERT INTO TestPaperSectionVersion(noOfQuestionsToAnswer, versionNumber,timeScale,testPaperSectionId) VALUES (1,1,10,4);
INSERT INTO TestPaperSectionVersionEntry VALUES (1,4,1,2,3);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Singleton pattern', 'Essay', 10);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (26, 1,'a) This is the singleton pattern, what is it commonly used for?</br>b) If there are errors, fix them in the code snippet.</br>c) Please comment on any strengths or weaknesses of the above implementation. For instance, are there cases were it doesn''t guarantee only one instance is created?</br><pre class="prettyprint">class Singleton {<br />&nbsp;&nbsp; &nbsp;Singleton();<br /><br />&nbsp;&nbsp; &nbsp;private Singleton mInstance = null;<br /><br />&nbsp;&nbsp; &nbsp;public static Singleton instance() {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (mInstance == null) {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;mInstance == new Singleton();<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return mInstance;<br />&nbsp;&nbsp; &nbsp;}<br />}</pre>', 10, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,4,1,26,1);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sql products and invoices', 'Essay', 10);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (27, 1,'The questions section are based on the schema definitions defined below.</br><pre class="prettyprint lang-sql">CREATE TABLE Products(ProductId integer PRIMARY KEY, ProductName varchar(100));<br />CREATE TABLE Invoices(InvoiceNumber integer PRIMARY KEY, ProductId integer, InvoiceDate datetime, InvoiceCost decimal(6, 2)InvoiceComment varchar(200));</pre>a) What is the purpose of the following SQL statement:</br><pre class="prettyprint lang-sql">SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId;</pre>b) The application programs using the tables allow a moduleLeader to find an invoice by date and time range using a select statement of the form:</br><pre class="prettyprint lang-sql">SELECT InvoiceNumber, InvoiceCost FROM Invoices WHERE InvoiceDate &gt;= &lsquo;2000/05/23 15:00:00&rsquo; AND InvoiceDate &lt; &lsquo;2000/05/23 16:00:00&rsquo;;</pre>c) However as the Invoices table grew larger the execution times of these queries increased.Describe a change to the Database schema that would decrease the query execution time.</br>d) A test has been written to validate the query from question 2.1. The pseudo-code is:<pre class="prettyprint lang-sql">Connect to the database <br />FirstResult = Execute Query(&ldquo;SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId&rdquo;) <br />Add a new invoice &nbsp;<br />SecondResult = Execute Query(&ldquo;SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId&rdquo;) <br />Check that FirstResult and SecondResult differ by the added amount</pre> </br>But on a shared database the test keeps failing because someone else was running the same test so the second query picked up two invoices, what can we do to avoid this problem?</br>e)Write a query to return the product Name, number of the product sold and the highest price paid for it?', 10, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,4,1,27,2);

INSERT INTO TestPaperSection(referenceName) VALUES ('Written Communication');
INSERT INTO TestPaperSectionVersion(noOfQuestionsToAnswer, versionNumber,timeScale,testPaperSectionId) VALUES (1,1,8,5);
INSERT INTO TestPaperSectionVersionEntry VALUES (1,5,1,2,4);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Functional specification of a login dialog', 'Essay', 8);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (28, 1,'Write a short functional specification of a typical login dialog (as seen in <b>Error! Reference source not found.</b>)', 8, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,5,1,28,1);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Recursion on contents of array', 'Code', 8);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (29, 1,'A) Write a short paragraph describing the concept of recursion.</br>B) Write a simple recursive method to print out the contents of an array</br>', 8, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,5,1,29,2);

INSERT INTO TestPaperSection(referenceName) VALUES ('Test Case Design');
INSERT INTO TestPaperSectionVersion(noOfQuestionsToAnswer, versionNumber,timeScale,testPaperSectionId) VALUES (1,1,10,6);
INSERT INTO TestPaperSectionVersionEntry VALUES (1,6,1,2,5);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Test date validity', 'Code', 5);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (30, 1,'The following code checks the validity of a date (which is passed in as 2 integer parameters). The code is looking to check the validity of all the days of the year, design the test data necessary to fully test this code. Note you do not need to consider leap years in your answer. <pre class="prettyprint">public class DateValidator<br />{<br />&nbsp;&nbsp; &nbsp;private static int daysInMonth [12] =&nbsp; {31,28,31,30,31,30,31,31,30,31,30,31};<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;public static bool validate(int day, int month)<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;{&nbsp;&nbsp;&nbsp; &nbsp;<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; if (month&gt;=1 &amp;&amp; month &lt;= 12 &amp;&amp; day &gt;=1 &amp;&amp; day &lt;=daysInMonth[month-1]) <br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return true:<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; else {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return false;<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp; &nbsp;}<br />}</pre>', 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,6,1,30,1);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Test static website', 'Essay',5);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (31, 1,'What tests can be executed against a web site that has static pages?  For example, spell checking text or verifying image downloads.', 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.');
INSERT INTO QuestionVersionEntry VALUES (1,6,1,31,2);

INSERT INTO TermsAndConditions(termsAndConditions) VALUES ('Terms sample');

INSERT INTO TestPaper(referenceName, timeAllowed) VALUES ('Sample Multiple Choice Auto-Marking Test', 20);
INSERT INTO TestPaperVersion(authorId, versionNumber, testPaperId, instructionsText) VALUES ('fba6a561-8999-4b19-9c57-232895d024c6', 1, 3, 'Sample multiple choice');

INSERT INTO TestPaperSection(referenceName) VALUES ('Multiple Choice');
INSERT INTO TestPaperSectionVersion(noOfQuestionsToAnswer, versionNumber,timeScale,testPaperSectionId) VALUES (10,1,20,7);
INSERT INTO TestPaperSectionVersionEntry VALUES (1,7,1,3,1);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sample multiple choice 1', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (32, 1,'Correct answer is B A) Some text <br/>B) Some text <br/>C) Some text <br/>', 1, '{"B": 1}');
INSERT INTO QuestionVersionEntry VALUES (1,7,1,32,1);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sample multiple choice 2', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (33, 1,'Correct answer is A A) Some text <br/>B) Some text <br/>C) Some text <br/>', 1, '{"A": 1}');
INSERT INTO QuestionVersionEntry VALUES (1,7,1,33,2);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sample multiple choice 3', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (34, 1,'Correct answer is C A) Some text <br/>B) Some text <br/>C) Some text <br/>', 1, '{"C": 1}');
INSERT INTO QuestionVersionEntry VALUES (1,7,1,34,3);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sample multiple choice 4', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (35, 1,'Correct answer is A A) Some text <br/>B) Some text <br/>C) Some text <br/>', 1, '{"A": 1}');
INSERT INTO QuestionVersionEntry VALUES (1,7,1,35,4);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sample multiple choice 5', 'Multiple Choice', 2);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (36, 1,'Correct answer are A,B A) Some text <br/>B) Some text <br/>C) Some text <br/>', 1, '{"A,B": 2,"A|B": 1}');
INSERT INTO QuestionVersionEntry VALUES (1,7,1,36,5);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sample multiple choice 6', 'Multiple Choice', 3);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (37, 1,'Correct answer are A,B,C A) Some text <br/>B) Some text <br/>C) Some text <br/>', 1, '{"([abc])(?!\1)[abc](?!\1)[abc]": 3,"([abc])(?!\1)[abc]": 2,"A|B|C": 1}');
INSERT INTO QuestionVersionEntry VALUES (1,7,1,37,6);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sample multiple choice 7', 'Multiple Choice', 3);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (38, 1,'Correct answer are A,B,C A) Some text <br/>B) Some text <br/>C) Some text <br/>', 1, '{"([abc])(?!\1)[abc](?!\1)[abc]" : 3,"([abc])(?!\1)[abc]": 2,"A|B|C": 1}');
INSERT INTO QuestionVersionEntry VALUES (1,7,1,38,7);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sample multiple choice 8', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (39, 1,'Correct answer is B A) Some text <br/>B) Some text <br/>C) Some text <br/>', 1, '{"B":1}');
INSERT INTO QuestionVersionEntry VALUES (1,7,1,39,8);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sample multiple choice 9', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (40, 1,'Correct answer is A A) Some text <br/>B) Some text <br/>C) Some text <br/>', 1, '{"A":1}');
INSERT INTO QuestionVersionEntry VALUES (1,7,1,40,9);

INSERT INTO Question(referenceName, questionTypeId, difficulty) VALUES ('Sample multiple choice 10', 'Multiple Choice', 1);
INSERT INTO QuestionVersion(questionId, versionNumber, text, timeScale, correctAnswer) VALUES (41, 1,'Correct answer is C A) Some text <br/>B) Some text <br/>C) Some text <br/>', 1, '{"C":1}');
INSERT INTO QuestionVersionEntry VALUES (1,7,1,41,10);

INSERT INTO Module(referenceName, moduleCode) VALUES ('Sample Module 1', 'CSC001');
INSERT INTO Module(referenceName, moduleCode) VALUES ('Sample Module 2', 'CSC002');
INSERT INTO Module(referenceName, moduleCode) VALUES ('Sample Module 3', 'CSC003');
INSERT INTO Module(referenceName, moduleCode) VALUES ('Sample Module 4', 'CSC004');
INSERT INTO Module(referenceName, moduleCode) VALUES ('Sample Module 5', 'CSC005');
INSERT INTO Module(referenceName, moduleCode) VALUES ('Sample Module 6', 'CSC006');
INSERT INTO Module(referenceName, moduleCode) VALUES ('Sample Module 7', 'CSC007');
INSERT INTO Module(referenceName, moduleCode) VALUES ('Sample Module 8', 'CSC008');
INSERT INTO Module(referenceName, moduleCode) VALUES ('Sample Module 9', 'CSC009');
INSERT INTO Module(referenceName, moduleCode) VALUES ('Sample Module 10', 'CSC010');

INSERT INTO ModuleLeader(userId, moduleId) VALUES ('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d', 1);

INSERT INTO User VALUES ('95818d99-492d-4c50-80b8-abae310bd2f3','Matthew','Collison','matthew.collison@ncl.ac.uk','pass');
INSERT INTO UserRole VALUES ('95818d99-492d-4c50-80b8-abae310bd2f3','Marker');
INSERT INTO UserRole VALUES ('95818d99-492d-4c50-80b8-abae310bd2f3','Author');
INSERT INTO UserRole VALUES ('95818d99-492d-4c50-80b8-abae310bd2f3','ModuleLeader');
INSERT INTO ModuleLeader(userId, moduleId) VALUES ('95818d99-492d-4c50-80b8-abae310bd2f3', 2);

INSERT INTO User VALUES ('92f6e08a-2dba-467c-96e8-a1ec1c87940b','John','Colquhoun','john.colquhoun@newcastle.ac.uk','pass');
INSERT INTO UserRole VALUES ('92f6e08a-2dba-467c-96e8-a1ec1c87940b','Marker');
INSERT INTO UserRole VALUES ('92f6e08a-2dba-467c-96e8-a1ec1c87940b','Author');
INSERT INTO UserRole VALUES ('92f6e08a-2dba-467c-96e8-a1ec1c87940b','ModuleLeader');
INSERT INTO ModuleLeader(userId, moduleId) VALUES ('92f6e08a-2dba-467c-96e8-a1ec1c87940b', 3);

INSERT INTO User VALUES ('1be448ff-1a2e-456f-9594-4042e7ef6ab2','Steve','Riddle',' steve.riddle@newcastle.ac.uk','pass');
INSERT INTO UserRole VALUES ('1be448ff-1a2e-456f-9594-4042e7ef6ab2','Marker');
INSERT INTO UserRole VALUES ('1be448ff-1a2e-456f-9594-4042e7ef6ab2','Author');
INSERT INTO UserRole VALUES ('1be448ff-1a2e-456f-9594-4042e7ef6ab2','ModuleLeader');
INSERT INTO ModuleLeader(userId, moduleId) VALUES ('1be448ff-1a2e-456f-9594-4042e7ef6ab2', 4);

INSERT INTO User VALUES ('045d785e-cc44-4e7e-89b8-2df505c0b72a','Lindsay','Marshall','Lindsay.Marshall@newcastle.ac.uk','pass');
INSERT INTO UserRole VALUES ('045d785e-cc44-4e7e-89b8-2df505c0b72a','Marker');
INSERT INTO UserRole VALUES ('045d785e-cc44-4e7e-89b8-2df505c0b72a','Author');
INSERT INTO UserRole VALUES ('045d785e-cc44-4e7e-89b8-2df505c0b72a','ModuleLeader');
INSERT INTO ModuleLeader(userId, moduleId) VALUES ('045d785e-cc44-4e7e-89b8-2df505c0b72a', 5);

INSERT INTO TestDay(date,location,startTime,endTime) VALUES ('22/02/2017', 'Leeds Office','12:00','14:30');
INSERT INTO Exam(`testPaperVersionNo`,
                 `testPaperId`,
                 `status`,
                 `termsAndConditionsId`,
                 `testDayId`
            ) VALUES (1,1,'Finished',1,1);


INSERT INTO Candidate (name, surname, hasExtraTime) VALUES ('Mercedes', 'Fedya', FALSE);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (1,1);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (2,1);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (3,1);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (4,1);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (5,1);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (6,1);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (7,1);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (8,1);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (9,1);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (10,1);

INSERT INTO Candidate (name, surname, hasExtraTime) VALUES ('Stig', 'Ivan', FALSE);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (1,2);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (2,2);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (3,2);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (4,2);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (5,2);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (6,2);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (7,2);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (8,2);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (9,2);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (10,2);

INSERT INTO Candidate (name, surname, hasExtraTime) VALUES ('Reva', 'Mihajlo', TRUE);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (1,3);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (2,3);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (3,3);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (4,3);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (5,3);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (6,3);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (7,3);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (8,3);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (9,3);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (10,3);

INSERT INTO Candidate (name, surname, hasExtraTime) VALUES ('Vance', 'Bernie', FALSE);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (1,4);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (2,4);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (3,4);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (4,4);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (5,4);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (6,4);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (7,4);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (8,4);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (9,4);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (10,4);

INSERT INTO Candidate (name, surname, hasExtraTime) VALUES ('Franciszek', 'Geglula', FALSE );
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (1,5);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (2,5);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (3,5);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (4,5);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (5,5);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (6,5);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (7,5);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (8,5);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (9,5);
INSERT INTO CandidateModule(moduleId, candidateId) VALUES (10,5);



