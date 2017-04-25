CREATE TABLE `User` (
  `_id` varchar(36) NOT NULL,
  `name` varchar(50),
  `surname` varchar(50),
  `login` varchar(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`_id`)
);

CREATE TABLE `Role` (
  `referenceName` varchar(50) NOT NULL,
  PRIMARY KEY (`referenceName`)
);

CREATE TABLE UserRole(
    `userId` VARCHAR(36),
    `roleId` VARCHAR(50) NOT NULL,
    PRIMARY KEY (userId, roleId),
    FOREIGN KEY (userId)
        REFERENCES User(_id),
    FOREIGN KEY (roleId)
        REFERENCES Role(referenceName)
);

CREATE TABLE `Module` (
    `_id` INT NOT NULL AUTO_INCREMENT,
    `moduleCode` varchar(6) NOT NULL,
    `referenceName` varchar(50) NOT NULL,
    PRIMARY KEY (`_id`)
);

CREATE TABLE `ModuleLeader` (
    `_id` INT NOT NULL AUTO_INCREMENT,
    `userId` VARCHAR(36) NOT NULL,
    `moduleId` INT NOT NULL,
    PRIMARY KEY (`_id`),
    FOREIGN KEY (`userId`)
           REFERENCES User(`_id`),
    FOREIGN KEY (`moduleId`)
           REFERENCES Module(`_id`)
);

CREATE TABLE `TestDay` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `date` varchar(50) NOT NULL,
  `startTime` VARCHAR(5),
  `endTime` VARCHAR(5),
  `endTimeWithExtraTime` VARCHAR(5),
  `location` varchar(350),
  PRIMARY KEY (`_id`)
);

CREATE TABLE `Candidate` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `hasExtraTime` BOOL NOT NULL,
  PRIMARY KEY (`_id`)
);

CREATE TABLE TestPaper(
    `_id` INT NOT NULL AUTO_INCREMENT,
    `referenceName` VARCHAR(150) NOT NULL,
    `timeAllowed` INT,
    PRIMARY KEY (_id)
);

CREATE TABLE TestPaperVersion(
    `authorId` VARCHAR(36),
    `testPaperId` INT NOT NULL,
    `instructionsText` VARCHAR(1024),
    `versionNumber` INT NOT NULL,
    PRIMARY KEY (testPaperId,versionNumber),  
    FOREIGN KEY (authorId) 
        REFERENCES User(_id),
    FOREIGN KEY (testPaperId) 
        REFERENCES TestPaper(_id)
);

CREATE TABLE TestPaperSection(
    `_id` INT NOT NULL AUTO_INCREMENT,
    `referenceName` VARCHAR(150),   
    PRIMARY KEY (_id)
);

CREATE TABLE TestPaperSectionVersion(
	`testPaperSectionId` INT NOT NULL,
	`versionNumber` INT NOT NULL,
	`noOfQuestionsToAnswer` INT,
    `sectionDescription` TEXT(65536),
    `timeScale` INT,
	FOREIGN KEY (testPaperSectionId) 
        REFERENCES TestPaperSection(_id), 
	PRIMARY KEY (testPaperSectionId, versionNumber)
);

CREATE TABLE TestPaperSectionVersionEntry(
    `testPaperSectionVersionNo` INT,
    `testPaperSectionId` INT,
    `testPaperVersionNo` INT,
    `testPaperId` INT,
    `referenceNumber` INT,
    FOREIGN KEY (testPaperSectionId,testPaperSectionVersionNo) 
        REFERENCES TestPaperSectionVersion(testPaperSectionId,versionNumber),
    FOREIGN KEY (testPaperId,testPaperVersionNo) 
        REFERENCES TestPaperVersion(testPaperId,versionNumber),
    PRIMARY KEY (testPaperSectionVersionNo,testPaperSectionId,testPaperVersionNo,testPaperId)
);

CREATE TABLE QuestionType(
    `referenceName` VARCHAR(150),
    PRIMARY KEY (referenceName)
);

CREATE TABLE Question(
	`_id` INT NOT NULL AUTO_INCREMENT,
	`language` VARCHAR(150),
	`difficulty` INT NOT NULL,
	`referenceName` VARCHAR(150),
	`questionTypeId` VARCHAR(150),
	FOREIGN KEY (questionTypeId) 
        REFERENCES QuestionType(referenceName),
	PRIMARY KEY (_id)
);

CREATE TABLE QuestionVersion(
	`versionNumber` INT NOT NULL,
	questionId INT NOT NULL,
	`text` VARCHAR(2048),
    `correctAnswer` VARCHAR(2048),
    `markingGuide` VARCHAR(2048),
    `timeScale` INT,
	FOREIGN KEY (questionId)
        REFERENCES Question(_id),
	PRIMARY KEY (versionNumber, questionId)
);

CREATE TABLE QuestionVersionAsset(
	`_id` INT NOT NULL AUTO_INCREMENT,
    questionVersionNumber INT NOT NULL,
	questionId INT NOT NULL,
	`referenceName` VARCHAR(104),
	`blobType` VARCHAR(104),
	`_blob` LONGBLOB,
    FOREIGN KEY (questionVersionNumber,questionId)
        REFERENCES QuestionVersion(versionNumber, questionId),
    PRIMARY KEY (_id)
);

CREATE TABLE QuestionVersionEntry(
    `testPaperSectionVersionNo` INT,
    `testPaperSectionId` INT,
    questionVersionNumber INT,
	  questionId INT,
    `referenceNumber` INT,
    FOREIGN KEY (testPaperSectionId,testPaperSectionVersionNo) 
        REFERENCES TestPaperSectionVersion(testPaperSectionId,versionNumber),
    FOREIGN KEY (questionId, questionVersionNumber)
        REFERENCES QuestionVersion(questionId,versionNumber),
    PRIMARY KEY (questionVersionNumber, questionId, testPaperSectionVersionNo, testPaperSectionId)
);

CREATE TABLE TermsAndConditions(
    `_id` INT NOT NULL AUTO_INCREMENT,
    `termsAndConditions` VARCHAR(10000),
    PRIMARY KEY (_id)
);

CREATE TABLE Exam(
  `_id` INT NOT NULL AUTO_INCREMENT,
  `testPaperVersionNo` INT,
  `testPaperId` INT,
  `status` VARCHAR(20),
  `markingLock` varchar(36),
  `termsAndConditionsId` INT,
  `testDayId` INT,
  `moduleId` INT,
  FOREIGN KEY (testDayId)
  REFERENCES TestDay(_id),
  FOREIGN KEY (testPaperId,testPaperVersionNo)
  REFERENCES TestPaperVersion(testPaperId,versionNumber),
  FOREIGN KEY (termsAndConditionsId) REFERENCES TermsAndConditions(`_id`),
  FOREIGN KEY (moduleId) REFERENCES Module(`_id`),
  FOREIGN KEY (markingLock)
  REFERENCES User(_id),
  PRIMARY KEY (`_id`)
);

CREATE TABLE `TestDayEntry` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `candidateId` INT NOT NULL,
  `testDayEntryStatus` varchar(36),
  `markingLock` varchar(36),
  `finalMark` INT,
  `examId` INT,
  `login` VARCHAR(5),
  `password` VARCHAR(5),
  FOREIGN KEY (candidateId)
  REFERENCES Candidate(_id),
  FOREIGN KEY (markingLock)
  REFERENCES User(_id),
  FOREIGN KEY (examId)
  REFERENCES Exam(`_id`),
  PRIMARY KEY (`_id`)
);

CREATE TABLE CandidateModule(
  `_id` INT NOT NULL AUTO_INCREMENT,
  `moduleId` INT,
  `candidateId` INT,
  FOREIGN KEY (candidateId) REFERENCES Candidate(`_id`),
  FOREIGN KEY (moduleId) REFERENCES Module(`_id`),
  PRIMARY KEY (`_id`)
);

CREATE TABLE Mark(
  `_id` INT NOT NULL AUTO_INCREMENT,
  `markerId` varchar(36),
  `comment` VARCHAR(5000),
  `actualMark` INT,
  FOREIGN KEY (markerId)
  REFERENCES User(_id),
  PRIMARY KEY (_id)
);

CREATE TABLE Answer(
  questionId  INT NOT NULL,
  questionVersionNumber INT NOT NULL,
  `testDayEntryId` INT NOT NULL,
  `text` VARCHAR(50000),
  `markId` INT,
  FOREIGN KEY (markId)
  REFERENCES Mark(_id),
  FOREIGN KEY (questionVersionNumber,questionId)
  REFERENCES QuestionVersion(versionNumber, questionId),
  FOREIGN KEY (testDayEntryId)
  REFERENCES TestDayEntry(_id),
  PRIMARY KEY (questionId,questionVersionNumber,testDayEntryId)
);

CREATE TABLE AnswerAsset(
  `_id` INT NOT NULL AUTO_INCREMENT,
  questionId  INT NOT NULL,
  questionVersionNumber INT NOT NULL,
  `testDayEntryId` INT NOT NULL,
  `referenceName` VARCHAR(150),
  `_blob` MEDIUMBLOB,
  FOREIGN KEY (questionId, questionVersionNumber, testDayEntryId)
  REFERENCES Answer(questionId, questionVersionNumber, testDayEntryId)
    ON DELETE CASCADE,
  PRIMARY KEY (_id)
);