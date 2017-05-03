-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dissertationdb
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Answer`
--

DROP TABLE IF EXISTS `Answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Answer` (
  `questionId` int(11) NOT NULL,
  `questionVersionNumber` int(11) NOT NULL,
  `testDayEntryId` int(11) NOT NULL,
  `text` varchar(50000) DEFAULT NULL,
  `markId` int(11) DEFAULT NULL,
  PRIMARY KEY (`questionId`,`questionVersionNumber`,`testDayEntryId`),
  KEY `markId` (`markId`),
  KEY `questionVersionNumber` (`questionVersionNumber`,`questionId`),
  KEY `testDayEntryId` (`testDayEntryId`),
  CONSTRAINT `Answer_ibfk_1` FOREIGN KEY (`markId`) REFERENCES `Mark` (`_id`),
  CONSTRAINT `Answer_ibfk_2` FOREIGN KEY (`questionVersionNumber`, `questionId`) REFERENCES `QuestionVersion` (`versionNumber`, `questionId`),
  CONSTRAINT `Answer_ibfk_3` FOREIGN KEY (`testDayEntryId`) REFERENCES `TestDayEntry` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Answer`
--

LOCK TABLES `Answer` WRITE;
/*!40000 ALTER TABLE `Answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `Answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AnswerAsset`
--

DROP TABLE IF EXISTS `AnswerAsset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AnswerAsset` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `questionId` int(11) NOT NULL,
  `questionVersionNumber` int(11) NOT NULL,
  `testDayEntryId` int(11) NOT NULL,
  `referenceName` varchar(150) DEFAULT NULL,
  `_blob` mediumblob,
  PRIMARY KEY (`_id`),
  KEY `questionId` (`questionId`,`questionVersionNumber`,`testDayEntryId`),
  CONSTRAINT `AnswerAsset_ibfk_1` FOREIGN KEY (`questionId`, `questionVersionNumber`, `testDayEntryId`) REFERENCES `Answer` (`questionId`, `questionVersionNumber`, `testDayEntryId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AnswerAsset`
--

LOCK TABLES `AnswerAsset` WRITE;
/*!40000 ALTER TABLE `AnswerAsset` DISABLE KEYS */;
/*!40000 ALTER TABLE `AnswerAsset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Candidate`
--

DROP TABLE IF EXISTS `Candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Candidate` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `hasExtraTime` tinyint(1) NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=506 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Candidate`
--

LOCK TABLES `Candidate` WRITE;
/*!40000 ALTER TABLE `Candidate` DISABLE KEYS */;
INSERT INTO `Candidate` VALUES (1,'Mercedes','Fedya',0),(2,'Stig','Ivan',0),(3,'Reva','Mihajlo',1),(4,'Vance','Bernie',0),(5,'Franciszek','Geglula',0),(6,'Oralia','Toliver',0),(7,'Odell','War',0),(8,'Chasidy','Slogeris',0),(9,'Norah','Guerrier',0),(10,'Emely','Pontremoli',0),(11,'Krystal','Polivick',0),(12,'Jenette','Sette',0),(13,'Trenton','Mayland',0),(14,'Carletta','Tonelli',0),(15,'Art','Mooring',0),(16,'Doreatha','Canny',0),(17,'Lucien','Haik',0),(18,'Lael','Drzazgowski',0),(19,'Glinda','Richard',0),(20,'Elda','Caradonna',0),(21,'Melody','Sul',0),(22,'Elijah','Colling',0),(23,'Nicholas','Salamanca',0),(24,'Jacob','Lowry',0),(25,'Anjanette','Krausmann',0),(26,'Andera','Ettienne',0),(27,'Cherryl','Kollasch',0),(28,'Magdalen','Sharabi',0),(29,'Shakira','Koria',0),(30,'Polly','Camel',0),(31,'Carolee','Plassman',0),(32,'Kiera','Stotelmyer',0),(33,'Pearle','Lagrange',0),(34,'Rosalie','Hiefnar',0),(35,'Lesha','Gochie',0),(36,'Miquel','Ilardi',0),(37,'Fernande','Davtyan',0),(38,'Vasiliki','Crowther',0),(39,'Vernita','Plowe',0),(40,'Sheridan','Lazenson',0),(41,'Anisha','Pono',0),(42,'Rachell','Bergstrom',0),(43,'Darlene','Annibale',0),(44,'Page','Wates',0),(45,'Kaci','Cocuzza',0),(46,'Mitchell','Fauls',0),(47,'Lakendra','Fischbach',0),(48,'Vince','Nicolai',0),(49,'Latisha','Bergholz',0),(50,'Cassaundra','Ngvyen',0),(51,'Eunice','Kamansky',0),(52,'Kera','Hannula',0),(53,'Steve','Meshew',0),(54,'Alberto','Slye',0),(55,'Danyell','Flitt',0),(56,'Joana','Guadeloupe',0),(57,'Mayola','Reggio',0),(58,'Dodie','Gugerty',0),(59,'Randy','Stapleton',0),(60,'Joy','Sakata',0),(61,'Adriane','Remkus',0),(62,'Racquel','Rosenlof',0),(63,'Merri','Macnutt',0),(64,'Khadijah','Carpenito',0),(65,'Solomon','Thigpin',0),(66,'Cristy','Dovel',0),(67,'Ilana','Estala',0),(68,'Marvel','Daggy',0),(69,'Tomoko','Holtzman',0),(70,'Lakiesha','Goodner',0),(71,'Shakira','Durette',0),(72,'Buster','Enrriquez',0),(73,'Carmelita','Blanks',0),(74,'Clemencia','Condroski',0),(75,'Nettie','Bergenstock',0),(76,'Kimberlee','Leon',0),(77,'Nelly','Devore',0),(78,'Cristopher','Paras',0),(79,'Desmond','Salatino',0),(80,'Denisha','Decapite',0),(81,'Eduardo','Aulder',0),(82,'Madalyn','Tablada',0),(83,'Alphonse','Leboeuf',0),(84,'Robbie','Kassulke',0),(85,'Charis','Brend',0),(86,'Hilton','Gunther',0),(87,'Travis','Hellstrom',0),(88,'Jarvis','Kepp',0),(89,'Dan','Lidie',0),(90,'Camie','Petretti',0),(91,'Ester','Dernier',0),(92,'Lily','Gilmour',0),(93,'Wen','Bravata',0),(94,'Kellee','Sylva',0),(95,'Christena','Minzel',0),(96,'Alberta','Didyk',0),(97,'Marceline','Willoby',0),(98,'Christopher','Christman',0),(99,'Corina','Pantoni',0),(100,'Roseanna','Carmolli',0),(101,'Hal','Wien',0),(102,'Denese','Pollio',0),(103,'Mindy','Mangels',0),(104,'Ellen','Bulgrin',0),(105,'Krystal','Borowski',0),(106,'Rhett','Bunce',0),(107,'Carmen','Karow',0),(108,'Toni','Speth',0),(109,'Cory','Staker',0),(110,'Alicia','Querry',0),(111,'Mathilde','Surkamer',0),(112,'Daryl','Demarsico',0),(113,'Martin','Kaneholani',0),(114,'Nikki','Ivel',0),(115,'Katy','Tervort',0),(116,'Zona','Scheibe',0),(117,'Eldridge','Zoda',0),(118,'Sonya','Vansice',0),(119,'Alvina','Horr',0),(120,'Dirk','Parrinello',0),(121,'Mariela','Ivy',0),(122,'Sanford','Llamas',0),(123,'Marvella','Stotesberry',0),(124,'Jolynn','Lodense',0),(125,'April','Lutsky',0),(126,'Sharlene','Skibbe',0),(127,'Claretha','Menapace',0),(128,'Doyle','Hegler',0),(129,'Loni','Bobst',0),(130,'Homer','Tekulve',0),(131,'Roxanne','Parnin',0),(132,'Winifred','Gelb',0),(133,'Tawanda','Pea',0),(134,'Rena','Pomella',0),(135,'Nilda','Deininger',0),(136,'Yuki','Krob',0),(137,'Celinda','Gutschow',0),(138,'Arcelia','Reker',0),(139,'Jonnie','Blankenburg',0),(140,'Willis','Pujia',0),(141,'Alonzo','Bradberry',0),(142,'Kathaleen','Grosky',0),(143,'Royal','Zaring',0),(144,'Emery','Sloman',0),(145,'Napoleon','Cerf',0),(146,'Patrina','Kaskey',0),(147,'Jarrod','Grustas',0),(148,'Signe','Brehant',0),(149,'Barry','Poinsette',0),(150,'Pedro','Mandi',0),(151,'Carrol','Schanzenbach',0),(152,'Wilda','Waldenmyer',0),(153,'Cheri','Oller',0),(154,'Vernetta','Knoble',0),(155,'Vennie','Albarran',0),(156,'Ezekiel','Schadle',0),(157,'Lila','Dobiesz',0),(158,'Rutha','Kerschner',0),(159,'Francisco','Johanson',0),(160,'Lucila','Innes',0),(161,'Jaime','Seide',0),(162,'Jimmie','Mccray',0),(163,'Kaci','Kegler',0),(164,'Quinn','Riniker',0),(165,'Rose','Lippi',0),(166,'Haywood','Florenz',0),(167,'Krystyna','Wallen',0),(168,'Shannan','Uncapher',0),(169,'Geralyn','Grasso',0),(170,'Sabine','Jira',0),(171,'Ellie','Delashmutt',0),(172,'Novella','Entinger',0),(173,'Delcie','Staib',0),(174,'Yon','Pietig',0),(175,'Clair','Quintas',0),(176,'Shari','Marske',0),(177,'Miguelina','Roloson',0),(178,'Cortez','Brazzi',0),(179,'Lesha','Panebianco',0),(180,'Judi','Groombridge',0),(181,'Ronald','Silcott',0),(182,'Audie','Lucatero',0),(183,'Amber','Nordman',0),(184,'Dale','Masi',0),(185,'Leontine','Dewater',0),(186,'Diann','Perrera',0),(187,'Reuben','Diazdeleon',0),(188,'Loralee','Havice',0),(189,'Herb','Anastasiades',0),(190,'Zenobia','Schmiesing',0),(191,'Millie','Baris',0),(192,'Laquanda','Mazzocco',0),(193,'Tammy','Pruse',0),(194,'Cedric','Macrum',0),(195,'Sharie','Crossland',0),(196,'Tatyana','Dargis',0),(197,'Nathan','Sadowski',0),(198,'Sanford','Harradon',0),(199,'Brett','Vanabel',0),(200,'Dannette','Rach',0),(201,'Sherly','High',0),(202,'Tisha','Corwell',0),(203,'Myrtis','Frutiger',0),(204,'Isis','Knudson',0),(205,'Keshia','Wittels',0),(206,'Veronique','Most',0),(207,'Penni','Gertsch',0),(208,'Leonila','Pirkl',0),(209,'Eulalia','Koster',0),(210,'Elvira','Syndergaard',0),(211,'Waneta','Delger',0),(212,'Leanne','Boehnke',0),(213,'Delmy','Ruys',0),(214,'Sun','Shireman',0),(215,'Markus','Donez',0),(216,'Manual','Macahilas',0),(217,'Danille','Mcguire',0),(218,'Zita','Chary',0),(219,'Sharice','Anastasio',0),(220,'Mervin','Lee',0),(221,'Staci','Stars',0),(222,'Melodee','Flodin',0),(223,'Xiao','Enget',0),(224,'Nova','Fehr',0),(225,'Deonna','Caliguire',0),(226,'Danyell','Swaim',0),(227,'Marguerita','Cridland',0),(228,'Lorri','Hemler',0),(229,'Darcey','Chiv',0),(230,'Eura','Senneker',0),(231,'Kennith','Gruett',0),(232,'Han','Pearle',0),(233,'Fletcher','Rober',0),(234,'Enrique','Grimaldo',0),(235,'Geraldine','Jaecks',0),(236,'Stacie','Boahn',0),(237,'Joaquina','Jane',0),(238,'Treasa','Prom',0),(239,'Mercedez','Appert',0),(240,'Yung','Atienza',0),(241,'Nieves','Kleinpeter',0),(242,'Marcelina','Ubl',0),(243,'Kristi','Fotopoulos',0),(244,'Herbert','Lehtonen',0),(245,'Tisa','Speidell',0),(246,'Millie','Maccarter',0),(247,'Forrest','Chapek',0),(248,'Marquitta','Sturman',0),(249,'Odessa','Caccamise',0),(250,'Judi','Swonke',0),(251,'Mario','Kinas',0),(252,'Elisabeth','Marreo',0),(253,'Larita','Adaway',0),(254,'Penni','Hlavka',0),(255,'Mellisa','Jonnson',0),(256,'Mindi','Stoddart',0),(257,'Annabelle','Arlinghaus',0),(258,'Shanell','Earney',0),(259,'Elena','Hymon',0),(260,'Nannie','Nicewander',0),(261,'Dustin','Fagerstrom',0),(262,'Hyacinth','Gunn',0),(263,'Annette','Biewald',0),(264,'Luna','Holck',0),(265,'Marcia','Charney',0),(266,'Katie','Courchine',0),(267,'Carolann','Meluso',0),(268,'Ami','Joto',0),(269,'Nedra','Inverso',0),(270,'Nilsa','Stell',0),(271,'Jasmine','Pommier',0),(272,'Asa','Tootle',0),(273,'Charlyn','Rothove',0),(274,'Alexis','Comish',0),(275,'Deidra','Mayette',0),(276,'Lesli','Ditter',0),(277,'Cassidy','Mcguigan',0),(278,'Riley','Shenassa',0),(279,'Elva','Mraw',0),(280,'Trisha','Sorace',0),(281,'Mendy','Broadwater',0),(282,'Ty','Wicks',0),(283,'Hien','Courteau',0),(284,'Jani','Galaz',0),(285,'Sparkle','Shaulis',0),(286,'Chantay','Blancas',0),(287,'Exie','Ferrari',0),(288,'Samella','Fastic',0),(289,'Hassan','Latson',0),(290,'Mirta','Mondloch',0),(291,'Edythe','Jarry',0),(292,'Vince','Mandarino',0),(293,'Marguerite','Dapinto',0),(294,'Sheree','Friendly',0),(295,'Michael','Cleamons',0),(296,'Valrie','Sirosky',0),(297,'Mariam','Kozlovsky',0),(298,'Wilford','Barimah',0),(299,'Usha','Mckeehan',0),(300,'Jayson','Latos',0),(301,'Kit','Trebesch',0),(302,'Ariana','Mio',0),(303,'Shalonda','Lemone',0),(304,'Vilma','Vanta',0),(305,'Joey','Lietzow',0),(306,'Patria','Shaneyfelt',0),(307,'Fred','Goyal',0),(308,'Pinkie','Albracht',0),(309,'Rickie','Ventur',0),(310,'Danette','Guenin',0),(311,'Bea','Pippen',0),(312,'Evonne','Carlyon',0),(313,'Savannah','Ramu',0),(314,'Lorenza','Bocskor',0),(315,'Connie','Burell',0),(316,'Chun','Petrus',0),(317,'Del','Arroyd',0),(318,'Logan','Heggan',0),(319,'Beau','Rusi',0),(320,'Cayla','Alderfer',0),(321,'Jacob','Shollenbarger',0),(322,'Rhoda','Giesen',0),(323,'Hildred','Klinedinst',0),(324,'Gertrudis','Auces',0),(325,'Lilla','Quesada',0),(326,'Telma','Stopher',0),(327,'Tawanda','Roussell',0),(328,'Stanley','Nail',0),(329,'Chantelle','Lafantano',0),(330,'Alma','Peachay',0),(331,'Fernanda','Todorovich',0),(332,'Grant','Grebel',0),(333,'Raymundo','Falacco',0),(334,'Cherilyn','Lazo',0),(335,'Ellamae','Licata',0),(336,'Fred','Rivie',0),(337,'Nakesha','Semone',0),(338,'Chu','Petroski',0),(339,'Tyrell','Bowlan',0),(340,'Trudie','Talluto',0),(341,'Cierra','Kizer',0),(342,'Chery','Lappin',0),(343,'Candi','Abdullah',0),(344,'Jacquline','Hux',0),(345,'Leonida','Vivas',0),(346,'Tobi','Schoenemann',0),(347,'Annabelle','Schuerholz',0),(348,'Nichol','Adank',0),(349,'Boris','Iannaccone',0),(350,'Lupe','Mcgunagle',0),(351,'Ailene','Mans',0),(352,'Dong','Shuart',0),(353,'Deann','Sabb',0),(354,'Mirian','Leukuma',0),(355,'Carolina','Saeler',0),(356,'Marvis','Yoshizawa',0),(357,'Tomika','Mehl',0),(358,'Ignacia','Jayme',0),(359,'Tamisha','Dula',0),(360,'Loren','Wyse',0),(361,'Melina','Bendell',0),(362,'Sydney','Gaumond',0),(363,'Veronica','Romasanta',0),(364,'Agueda','Lavalle',0),(365,'Euna','Chary',0),(366,'Enola','Breslauer',0),(367,'Toshiko','Ellie',0),(368,'Jerri','Dutchess',0),(369,'Raymon','Teets',0),(370,'Pearly','Willhoite',0),(371,'Dominga','Montera',0),(372,'Dayle','Zumbrunnen',0),(373,'Carson','Brownlie',0),(374,'Jacquelynn','Chipp',0),(375,'Emory','Dass',0),(376,'Zaida','Taraschke',0),(377,'Fredricka','Baugham',0),(378,'Stella','Leibowitz',0),(379,'Shelley','Goers',0),(380,'Voncile','Pitorak',0),(381,'Ricki','Reisz',0),(382,'Reinaldo','Fimbrez',0),(383,'Ashleigh','Brelsford',0),(384,'Yadira','Nashe',0),(385,'Casey','Vendelin',0),(386,'Gabrielle','Huffstutter',0),(387,'Casey','Ducklow',0),(388,'Autumn','Finazzo',0),(389,'Denise','Cattrell',0),(390,'Tressa','Toyne',0),(391,'Felix','Norrix',0),(392,'Mariko','Martain',0),(393,'Walter','Latham',0),(394,'Madalene','Kahana',0),(395,'Alec','Hillwig',0),(396,'Jenine','Dwornik',0),(397,'Melina','Vaughner',0),(398,'Wilbert','Lampman',0),(399,'Prudence','Swarb',0),(400,'Tomas','Larizza',0),(401,'Babette','Fraker',0),(402,'Inez','Marlor',0),(403,'Jannie','Bugh',0),(404,'Arielle','Karimi',0),(405,'Franklyn','Mcgarity',0),(406,'Jeanice','Dinnen',0),(407,'Debby','Cylkowski',0),(408,'Toccara','Mckeirnan',0),(409,'Everett','Engelke',0),(410,'Pasty','Boley',0),(411,'Holley','Kailey',0),(412,'Melodee','Standifer',0),(413,'Georgia','Affagato',0),(414,'Mozell','Valado',0),(415,'Shannan','Scavuzzo',0),(416,'Delphine','Hew',0),(417,'Nathalie','Rylant',0),(418,'Jaime','Tamblyn',0),(419,'In','Martinelli',0),(420,'Kelley','Marcom',0),(421,'Ashlee','Acre',0),(422,'Ardis','Last',0),(423,'Hyman','Mehalic',0),(424,'Yuk','Ehl',0),(425,'Treva','Eckle',0),(426,'Consuelo','Lundgren',0),(427,'Idell','Toy',0),(428,'Judi','Jeffress',0),(429,'Junie','Sortor',0),(430,'Traci','Davaz',0),(431,'Paulette','Brasfield',0),(432,'Janna','Monroe',0),(433,'Kaye','Kriser',0),(434,'Brady','Ormes',0),(435,'Nereida','Ockey',0),(436,'Odelia','Barjas',0),(437,'Jon','Forkell',0),(438,'Jeremiah','Bolling',0),(439,'Lolita','Lohrenz',0),(440,'Leda','Beckton',0),(441,'Donella','Saenger',0),(442,'Marquerite','Brisk',0),(443,'Ty','Monts',0),(444,'Karyn','Lakey',0),(445,'Sheila','Farzan',0),(446,'Beverlee','Walshe',0),(447,'Jodi','Shuker',0),(448,'Bari','Ip',0),(449,'Alba','Saluja',0),(450,'Jene','Menietto',0),(451,'Penny','Greis',0),(452,'Kandy','Sherer',0),(453,'Kaycee','Birely',0),(454,'Antonio','Kaufman',0),(455,'Rosalee','Claude',0),(456,'Theodore','Fechtel',0),(457,'Bobbye','Sephton',0),(458,'Mariel','Gillum',0),(459,'Classie','Durrett',0),(460,'Janie','Peake',0),(461,'Zula','Shedrick',0),(462,'Erlene','Latten',0),(463,'Nicky','Lilleberg',0),(464,'Lemuel','Lippeatt',0),(465,'Shaniqua','Doughtry',0),(466,'Ozell','Pridgeon',0),(467,'Elisha','Shorkey',0),(468,'Pilar','Vandebogart',0),(469,'Samuel','Galbreth',0),(470,'Lady','Realbuto',0),(471,'Estela','Denniston',0),(472,'Sherrill','Huguenin',0),(473,'Lesley','Morrall',0),(474,'Daniele','Fusch',0),(475,'Laurence','Ronin',0),(476,'Iris','Schissel',0),(477,'Mahalia','Palamino',0),(478,'Clay','Ojala',0),(479,'Maryln','Mattson',0),(480,'Jolanda','Jaarda',0),(481,'Shanae','Dyle',0),(482,'Keli','Ertel',0),(483,'Alverta','Melick',0),(484,'Doria','Been',0),(485,'Tran','Greenan',0),(486,'Delicia','Luehring',0),(487,'Dani','Madera',0),(488,'Jefferson','Floro',0),(489,'Irvin','Steinhauser',0),(490,'Russel','Barthold',0),(491,'Deandra','Eisenstadt',0),(492,'Bobbie','Foggie',0),(493,'Karole','Coveney',0),(494,'Elease','Weirick',0),(495,'Thurman','Severe',0),(496,'Amira','Norris',0),(497,'Jerry','Eury',0),(498,'Corazon','Hartz',0),(499,'Priscila','Leija',0),(500,'Dinorah','Cockrell',0),(501,'Misty','Valasek',0),(502,'Katharyn','Acheson',0),(503,'Carina','Bergerson',0),(504,'Jene','Beyerlein',0),(505,'Rupert','Shettleroe',0);
/*!40000 ALTER TABLE `Candidate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CandidateModule`
--

DROP TABLE IF EXISTS `CandidateModule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CandidateModule` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `moduleId` int(11) DEFAULT NULL,
  `candidateId` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`),
  KEY `candidateId` (`candidateId`),
  KEY `moduleId` (`moduleId`),
  CONSTRAINT `CandidateModule_ibfk_1` FOREIGN KEY (`candidateId`) REFERENCES `Candidate` (`_id`),
  CONSTRAINT `CandidateModule_ibfk_2` FOREIGN KEY (`moduleId`) REFERENCES `Module` (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=551 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CandidateModule`
--

LOCK TABLES `CandidateModule` WRITE;
/*!40000 ALTER TABLE `CandidateModule` DISABLE KEYS */;
INSERT INTO `CandidateModule` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,1),(6,6,1),(7,7,1),(8,8,1),(9,9,1),(10,10,1),(11,1,2),(12,2,2),(13,3,2),(14,4,2),(15,5,2),(16,6,2),(17,7,2),(18,8,2),(19,9,2),(20,10,2),(21,1,3),(22,2,3),(23,3,3),(24,4,3),(25,5,3),(26,6,3),(27,7,3),(28,8,3),(29,9,3),(30,10,3),(31,1,4),(32,2,4),(33,3,4),(34,4,4),(35,5,4),(36,6,4),(37,7,4),(38,8,4),(39,9,4),(40,10,4),(41,1,5),(42,2,5),(43,3,5),(44,4,5),(45,5,5),(46,6,5),(47,7,5),(48,8,5),(49,9,5),(50,10,5),(51,11,6),(52,11,7),(53,11,8),(54,11,9),(55,11,10),(56,11,11),(57,11,12),(58,11,13),(59,11,14),(60,11,15),(61,11,16),(62,11,17),(63,11,18),(64,11,19),(65,11,20),(66,11,21),(67,11,22),(68,11,23),(69,11,24),(70,11,25),(71,11,26),(72,11,27),(73,11,28),(74,11,29),(75,11,30),(76,11,31),(77,11,32),(78,11,33),(79,11,34),(80,11,35),(81,11,36),(82,11,37),(83,11,38),(84,11,39),(85,11,40),(86,11,41),(87,11,42),(88,11,43),(89,11,44),(90,11,45),(91,11,46),(92,11,47),(93,11,48),(94,11,49),(95,11,50),(96,11,51),(97,11,52),(98,11,53),(99,11,54),(100,11,55),(101,11,56),(102,11,57),(103,11,58),(104,11,59),(105,11,60),(106,11,61),(107,11,62),(108,11,63),(109,11,64),(110,11,65),(111,11,66),(112,11,67),(113,11,68),(114,11,69),(115,11,70),(116,11,71),(117,11,72),(118,11,73),(119,11,74),(120,11,75),(121,11,76),(122,11,77),(123,11,78),(124,11,79),(125,11,80),(126,11,81),(127,11,82),(128,11,83),(129,11,84),(130,11,85),(131,11,86),(132,11,87),(133,11,88),(134,11,89),(135,11,90),(136,11,91),(137,11,92),(138,11,93),(139,11,94),(140,11,95),(141,11,96),(142,11,97),(143,11,98),(144,11,99),(145,11,100),(146,11,101),(147,11,102),(148,11,103),(149,11,104),(150,11,105),(151,11,106),(152,11,107),(153,11,108),(154,11,109),(155,11,110),(156,11,111),(157,11,112),(158,11,113),(159,11,114),(160,11,115),(161,11,116),(162,11,117),(163,11,118),(164,11,119),(165,11,120),(166,11,121),(167,11,122),(168,11,123),(169,11,124),(170,11,125),(171,11,126),(172,11,127),(173,11,128),(174,11,129),(175,11,130),(176,11,131),(177,11,132),(178,11,133),(179,11,134),(180,11,135),(181,11,136),(182,11,137),(183,11,138),(184,11,139),(185,11,140),(186,11,141),(187,11,142),(188,11,143),(189,11,144),(190,11,145),(191,11,146),(192,11,147),(193,11,148),(194,11,149),(195,11,150),(196,11,151),(197,11,152),(198,11,153),(199,11,154),(200,11,155),(201,11,156),(202,11,157),(203,11,158),(204,11,159),(205,11,160),(206,11,161),(207,11,162),(208,11,163),(209,11,164),(210,11,165),(211,11,166),(212,11,167),(213,11,168),(214,11,169),(215,11,170),(216,11,171),(217,11,172),(218,11,173),(219,11,174),(220,11,175),(221,11,176),(222,11,177),(223,11,178),(224,11,179),(225,11,180),(226,11,181),(227,11,182),(228,11,183),(229,11,184),(230,11,185),(231,11,186),(232,11,187),(233,11,188),(234,11,189),(235,11,190),(236,11,191),(237,11,192),(238,11,193),(239,11,194),(240,11,195),(241,11,196),(242,11,197),(243,11,198),(244,11,199),(245,11,200),(246,11,201),(247,11,202),(248,11,203),(249,11,204),(250,11,205),(251,11,206),(252,11,207),(253,11,208),(254,11,209),(255,11,210),(256,11,211),(257,11,212),(258,11,213),(259,11,214),(260,11,215),(261,11,216),(262,11,217),(263,11,218),(264,11,219),(265,11,220),(266,11,221),(267,11,222),(268,11,223),(269,11,224),(270,11,225),(271,11,226),(272,11,227),(273,11,228),(274,11,229),(275,11,230),(276,11,231),(277,11,232),(278,11,233),(279,11,234),(280,11,235),(281,11,236),(282,11,237),(283,11,238),(284,11,239),(285,11,240),(286,11,241),(287,11,242),(288,11,243),(289,11,244),(290,11,245),(291,11,246),(292,11,247),(293,11,248),(294,11,249),(295,11,250),(296,11,251),(297,11,252),(298,11,253),(299,11,254),(300,11,255),(301,11,256),(302,11,257),(303,11,258),(304,11,259),(305,11,260),(306,11,261),(307,11,262),(308,11,263),(309,11,264),(310,11,265),(311,11,266),(312,11,267),(313,11,268),(314,11,269),(315,11,270),(316,11,271),(317,11,272),(318,11,273),(319,11,274),(320,11,275),(321,11,276),(322,11,277),(323,11,278),(324,11,279),(325,11,280),(326,11,281),(327,11,282),(328,11,283),(329,11,284),(330,11,285),(331,11,286),(332,11,287),(333,11,288),(334,11,289),(335,11,290),(336,11,291),(337,11,292),(338,11,293),(339,11,294),(340,11,295),(341,11,296),(342,11,297),(343,11,298),(344,11,299),(345,11,300),(346,11,301),(347,11,302),(348,11,303),(349,11,304),(350,11,305),(351,11,306),(352,11,307),(353,11,308),(354,11,309),(355,11,310),(356,11,311),(357,11,312),(358,11,313),(359,11,314),(360,11,315),(361,11,316),(362,11,317),(363,11,318),(364,11,319),(365,11,320),(366,11,321),(367,11,322),(368,11,323),(369,11,324),(370,11,325),(371,11,326),(372,11,327),(373,11,328),(374,11,329),(375,11,330),(376,11,331),(377,11,332),(378,11,333),(379,11,334),(380,11,335),(381,11,336),(382,11,337),(383,11,338),(384,11,339),(385,11,340),(386,11,341),(387,11,342),(388,11,343),(389,11,344),(390,11,345),(391,11,346),(392,11,347),(393,11,348),(394,11,349),(395,11,350),(396,11,351),(397,11,352),(398,11,353),(399,11,354),(400,11,355),(401,11,356),(402,11,357),(403,11,358),(404,11,359),(405,11,360),(406,11,361),(407,11,362),(408,11,363),(409,11,364),(410,11,365),(411,11,366),(412,11,367),(413,11,368),(414,11,369),(415,11,370),(416,11,371),(417,11,372),(418,11,373),(419,11,374),(420,11,375),(421,11,376),(422,11,377),(423,11,378),(424,11,379),(425,11,380),(426,11,381),(427,11,382),(428,11,383),(429,11,384),(430,11,385),(431,11,386),(432,11,387),(433,11,388),(434,11,389),(435,11,390),(436,11,391),(437,11,392),(438,11,393),(439,11,394),(440,11,395),(441,11,396),(442,11,397),(443,11,398),(444,11,399),(445,11,400),(446,11,401),(447,11,402),(448,11,403),(449,11,404),(450,11,405),(451,11,406),(452,11,407),(453,11,408),(454,11,409),(455,11,410),(456,11,411),(457,11,412),(458,11,413),(459,11,414),(460,11,415),(461,11,416),(462,11,417),(463,11,418),(464,11,419),(465,11,420),(466,11,421),(467,11,422),(468,11,423),(469,11,424),(470,11,425),(471,11,426),(472,11,427),(473,11,428),(474,11,429),(475,11,430),(476,11,431),(477,11,432),(478,11,433),(479,11,434),(480,11,435),(481,11,436),(482,11,437),(483,11,438),(484,11,439),(485,11,440),(486,11,441),(487,11,442),(488,11,443),(489,11,444),(490,11,445),(491,11,446),(492,11,447),(493,11,448),(494,11,449),(495,11,450),(496,11,451),(497,11,452),(498,11,453),(499,11,454),(500,11,455),(501,11,456),(502,11,457),(503,11,458),(504,11,459),(505,11,460),(506,11,461),(507,11,462),(508,11,463),(509,11,464),(510,11,465),(511,11,466),(512,11,467),(513,11,468),(514,11,469),(515,11,470),(516,11,471),(517,11,472),(518,11,473),(519,11,474),(520,11,475),(521,11,476),(522,11,477),(523,11,478),(524,11,479),(525,11,480),(526,11,481),(527,11,482),(528,11,483),(529,11,484),(530,11,485),(531,11,486),(532,11,487),(533,11,488),(534,11,489),(535,11,490),(536,11,491),(537,11,492),(538,11,493),(539,11,494),(540,11,495),(541,11,496),(542,11,497),(543,11,498),(544,11,499),(545,11,500),(546,11,501),(547,11,502),(548,11,503),(549,11,504),(550,11,505);
/*!40000 ALTER TABLE `CandidateModule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Exam`
--

DROP TABLE IF EXISTS `Exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Exam` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `testPaperVersionNo` int(11) DEFAULT NULL,
  `testPaperId` int(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `markingLock` varchar(36) DEFAULT NULL,
  `termsAndConditionsId` int(11) DEFAULT NULL,
  `testDayId` int(11) DEFAULT NULL,
  `moduleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`),
  KEY `testDayId` (`testDayId`),
  KEY `testPaperId` (`testPaperId`,`testPaperVersionNo`),
  KEY `termsAndConditionsId` (`termsAndConditionsId`),
  KEY `moduleId` (`moduleId`),
  KEY `markingLock` (`markingLock`),
  CONSTRAINT `Exam_ibfk_1` FOREIGN KEY (`testDayId`) REFERENCES `TestDay` (`_id`),
  CONSTRAINT `Exam_ibfk_2` FOREIGN KEY (`testPaperId`, `testPaperVersionNo`) REFERENCES `TestPaperVersion` (`testPaperId`, `versionNumber`),
  CONSTRAINT `Exam_ibfk_3` FOREIGN KEY (`termsAndConditionsId`) REFERENCES `TermsAndConditions` (`_id`),
  CONSTRAINT `Exam_ibfk_4` FOREIGN KEY (`moduleId`) REFERENCES `Module` (`_id`),
  CONSTRAINT `Exam_ibfk_5` FOREIGN KEY (`markingLock`) REFERENCES `User` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Exam`
--

LOCK TABLES `Exam` WRITE;
/*!40000 ALTER TABLE `Exam` DISABLE KEYS */;
/*!40000 ALTER TABLE `Exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Mark`
--

DROP TABLE IF EXISTS `Mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mark` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `markerId` varchar(36) DEFAULT NULL,
  `comment` varchar(5000) DEFAULT NULL,
  `actualMark` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`),
  KEY `markerId` (`markerId`),
  CONSTRAINT `Mark_ibfk_1` FOREIGN KEY (`markerId`) REFERENCES `User` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mark`
--

LOCK TABLES `Mark` WRITE;
/*!40000 ALTER TABLE `Mark` DISABLE KEYS */;
/*!40000 ALTER TABLE `Mark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Module`
--

DROP TABLE IF EXISTS `Module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Module` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `moduleCode` varchar(6) NOT NULL,
  `referenceName` varchar(50) NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Module`
--

LOCK TABLES `Module` WRITE;
/*!40000 ALTER TABLE `Module` DISABLE KEYS */;
INSERT INTO `Module` VALUES (1,'CSC001','Sample Module 1'),(2,'CSC002','Sample Module 2'),(3,'CSC003','Sample Module 3'),(4,'CSC004','Sample Module 4'),(5,'CSC005','Sample Module 5'),(6,'CSC006','Sample Module 6'),(7,'CSC007','Sample Module 7'),(8,'CSC008','Sample Module 8'),(9,'CSC009','Sample Module 9'),(10,'CSC010','Sample Module 10'),(11,'CSC500','Large Module with 500 students');
/*!40000 ALTER TABLE `Module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ModuleLeader`
--

DROP TABLE IF EXISTS `ModuleLeader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ModuleLeader` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(36) NOT NULL,
  `moduleId` int(11) NOT NULL,
  PRIMARY KEY (`_id`),
  KEY `userId` (`userId`),
  KEY `moduleId` (`moduleId`),
  CONSTRAINT `ModuleLeader_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`_id`),
  CONSTRAINT `ModuleLeader_ibfk_2` FOREIGN KEY (`moduleId`) REFERENCES `Module` (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ModuleLeader`
--

LOCK TABLES `ModuleLeader` WRITE;
/*!40000 ALTER TABLE `ModuleLeader` DISABLE KEYS */;
INSERT INTO `ModuleLeader` VALUES (1,'9f4db0ac-b18a-4777-8b04-b72a0eeccf5d',1),(2,'95818d99-492d-4c50-80b8-abae310bd2f3',2),(3,'92f6e08a-2dba-467c-96e8-a1ec1c87940b',3),(4,'1be448ff-1a2e-456f-9594-4042e7ef6ab2',4),(5,'045d785e-cc44-4e7e-89b8-2df505c0b72a',5),(6,'42ea9f70-0d4f-11e7-93ae-92361f002671',6),(7,'045d785e-cc44-4e7e-89b8-2df505c0b72a',11),(8,'1be448ff-1a2e-456f-9594-4042e7ef6ab2',11),(9,'92f6e08a-2dba-467c-96e8-a1ec1c87940b',11),(10,'95818d99-492d-4c50-80b8-abae310bd2f3',11),(11,'9f4db0ac-b18a-4777-8b04-b72a0eeccf5d',11),(12,'42ea9f70-0d4f-11e7-93ae-92361f002671',11);
/*!40000 ALTER TABLE `ModuleLeader` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Question`
--

DROP TABLE IF EXISTS `Question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Question` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `language` varchar(150) DEFAULT NULL,
  `difficulty` int(11) NOT NULL,
  `referenceName` varchar(150) DEFAULT NULL,
  `questionTypeId` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`_id`),
  KEY `questionTypeId` (`questionTypeId`),
  CONSTRAINT `Question_ibfk_1` FOREIGN KEY (`questionTypeId`) REFERENCES `QuestionType` (`referenceName`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Question`
--

LOCK TABLES `Question` WRITE;
/*!40000 ALTER TABLE `Question` DISABLE KEYS */;
INSERT INTO `Question` VALUES (1,'C#',1,'Access a private variable','Multiple Choice'),(2,'C#',1,'Access a variable without modifier','Multiple Choice'),(3,'C#',1,'Truth about deadlocking','Multiple Choice'),(4,'C#',1,'Storing key/value pairs','Multiple Choice'),(5,'C#',5,'Null reference exception','Essay'),(6,'C#',5,'Output of tester class','Essay'),(7,'C#',3,'Compilation error','Essay'),(8,'C#',5,'Compilation error','Essay'),(9,'C#',10,'Common array elements','Code'),(10,'C#',5,'Develop isOdd','Code'),(11,'C#',5,'Objected Orientated Design','Drawing'),(12,NULL,10,'Singleton pattern','Essay'),(13,NULL,10,'Sql products and invoices','Essay'),(14,NULL,8,'Functional specification of a login dialog','Essay'),(15,NULL,8,'Recursion on contents of array','Code'),(16,NULL,5,'Test date validity','Code'),(17,NULL,5,'Test static website','Essay'),(18,NULL,1,'Sample multiple choice 1','Multiple Choice'),(19,NULL,1,'Sample multiple choice 2','Multiple Choice'),(20,NULL,1,'Sample multiple choice 3','Multiple Choice'),(21,NULL,1,'Sample multiple choice 4','Multiple Choice'),(22,NULL,2,'Sample multiple choice 5','Multiple Choice'),(23,NULL,3,'Sample multiple choice 6','Multiple Choice'),(24,NULL,3,'Sample multiple choice 7','Multiple Choice'),(25,NULL,1,'Sample multiple choice 8','Multiple Choice'),(26,NULL,1,'Sample multiple choice 9','Multiple Choice'),(27,NULL,1,'Sample multiple choice 10','Multiple Choice'),(28,'',5,'Fundamentals: Examples of operating systems','Expression'),(29,'',2,'What does cpu stand for','Expression'),(30,'',4,'Http and https stand for','Expression');
/*!40000 ALTER TABLE `Question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QuestionType`
--

DROP TABLE IF EXISTS `QuestionType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QuestionType` (
  `referenceName` varchar(150) NOT NULL,
  PRIMARY KEY (`referenceName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QuestionType`
--

LOCK TABLES `QuestionType` WRITE;
/*!40000 ALTER TABLE `QuestionType` DISABLE KEYS */;
INSERT INTO `QuestionType` VALUES ('Code'),('Drawing'),('Essay'),('Expression'),('Multiple Choice');
/*!40000 ALTER TABLE `QuestionType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QuestionVersion`
--

DROP TABLE IF EXISTS `QuestionVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QuestionVersion` (
  `versionNumber` int(11) NOT NULL,
  `questionId` int(11) NOT NULL,
  `text` varchar(2048) DEFAULT NULL,
  `correctAnswer` varchar(2048) DEFAULT NULL,
  `markingGuide` varchar(2048) DEFAULT NULL,
  `timeScale` int(11) DEFAULT NULL,
  PRIMARY KEY (`versionNumber`,`questionId`),
  KEY `questionId` (`questionId`),
  CONSTRAINT `QuestionVersion_ibfk_1` FOREIGN KEY (`questionId`) REFERENCES `Question` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QuestionVersion`
--

LOCK TABLES `QuestionVersion` WRITE;
/*!40000 ALTER TABLE `QuestionVersion` DISABLE KEYS */;
INSERT INTO `QuestionVersion` VALUES (1,28,'<p>Fill the blanks below with meanings of the corresponding acronyms.</p><p><strong>Acronym            Meaning</strong><br />LACP                   [[1]]<br />LAN                    [[2]]<br />LAPB                   [[3]]<br />LAPF                   [[4]]</p>','[{\"blankNo\":\"[[1]]\",\"answer\":\"operating system\",\"regex\":\"(?i)(operating\\\\s+system)\",\"mark\":\"2\",\"options\":{\"space\":true,\"punctuation\":false,\"case\":true}},{\"blankNo\":\"[[1]]\",\"answer\":\"OS\",\"regex\":\"(?i)(OS)\",\"mark\":\"2\",\"options\":{\"space\":true,\"punctuation\":false,\"case\":true}},{\"blankNo\":\"[[2]]\",\"answer\":\"\",\"regex\":\"((i?)(Windows))|((i?)(Linux))|((i?)(Ubuntu))|((i?)(Android))|((i?)(OSX))|((i?)(Fedora))\",\"mark\":\"1\",\"options\":{\"space\":false,\"punctuation\":false,\"case\":false}},{\"blankNo\":\"[[3]]\",\"answer\":\"\",\"regex\":\"((i?)(Windows))|((i?)(Linux))|((i?)(Ubuntu))|((i?)(Android))|((i?)(OSX))|((i?)(Fedora))\",\"mark\":\"1\",\"options\":{\"space\":false,\"punctuation\":false,\"case\":false}},{\"blankNo\":\"[[4]]\",\"answer\":\"\",\"regex\":\"((i?)(Windows))|((i?)(Linux))|((i?)(Ubuntu))|((i?)(Android))|((i?)(OSX))|((i?)(Fedora))\",\"mark\":\"1\",\"options\":{\"space\":false,\"punctuation\":false,\"case\":false}}]','<p>Answer 1 is operating system and it awards 2 marks. Rest are Windows or Linux or Ubuntu or Android or OSX or Fedora. Any from this list awards 1 mark.</p>',5),(1,29,'<p>What does CPU stand for ?<br />[[1]]</p>','[null,{\"blankNo\":\"[[1]]\",\"answer\":\"Central Processing Unit\",\"regex\":\"(?i)(Central\\\\s+Processing\\\\s+Unit)\",\"mark\":\"2\",\"options\":{\"space\":true,\"punctuation\":false,\"case\":true}}]','<p>Central processing unit gives 2 marks. It is case insensitive and uses white space collapsing</p>',1),(1,30,'<p>What does http stand for ?  [[1]]<br />What does https stand for ?  [[2]]</p>','[null,{\"blankNo\":\"[[1]]\",\"answer\":\"Hypertext Transfer Protocol\",\"regex\":\"(?i)(Hypertext\\\\s+Transfer\\\\s+Protocol)\",\"mark\":\"2\",\"options\":{\"space\":true,\"punctuation\":true,\"case\":true}},{\"blankNo\":\"[[2]]\",\"answer\":\"Hypertext Transfer Protocol Secure \",\"regex\":\"(?i)(Hypertext\\\\s+Transfer\\\\s+Protocol\\\\s+Secure\\\\s+)\",\"mark\":\"2\",\"options\":{\"space\":true,\"punctuation\":true,\"case\":true}},{\"blankNo\":\"[[2]]\",\"answer\":\"http secure\",\"regex\":\"(?i)(http\\\\s+secure)\",\"mark\":\"2\",\"options\":{\"space\":true,\"punctuation\":true,\"case\":true}}]','<p>2 Marks each. Hypertext Transfer Protocol and Hypertext Transfer Protocol Secure or http secure</p>',2),(2,1,'<p>Which classes can access a variable declared as private?</p><p>A) All classes.</p><p>B) Within the body of the class that encloses the declaration.</p><p>C) Inheriting sub classes.</p><p>D) Classes in the same namespace.</p>','{\"1\":\"\\\\b([B])\\\\b\"}','<p>Correct answer is B</p>',1),(2,2,'Which classes can access a variable with no access modifier?  A) All classes. B) Within the body of the class that encloses the declaration. C) Inheriting sub classes D) Classes in the same namespace.','{\"1\":\"\\\\b([D])\\\\b\"}','<p>Correct answer is D</p>',1),(2,3,'<p>Which of the following statement is true about deadlocking?</p><p>A) It is not possible for more than two threads to deadlock at once.</p><p>B) Managed code language such as Java or C# guarantee that threads cannot enter a deadlocked state.</p><p>C) It is possible for a single threaded application to deadlock if synchronized blocks are used incorrectly.</p><p>D) None of the above.</p>','{\"1\":\"\\\\b([C])\\\\b\"}','<p>Correct answer is C </p>',1),(2,4,'Which of the following C# objects is most suitable for storing general key/value pairs? A) DictionaryB) ListC) HashSetD) IEnumerable','{\"1\":\"\\\\b([A])\\\\b\"}','<p>Correct answer is A</p>',1),(2,5,'<p>Given the following fragment of C# code what will be happen?</p><pre>bool flag &#61; true;\r\nConsole.WriteLine( &#34;0&#34;);\r\ntry {<!-- -->\r\n    Console.WriteLine( &#34;1&#34;);\r\n    if (flag) {<!-- -->\r\n        object o &#61; null;\r\n        o.ToString();\r\n    }\r\n    Console.WriteLine( &#34;2&#34;);\r\n}\r\ncatch (NullReferenceException ex) {<!-- -->\r\n    Console.WriteLine( &#34;3&#34;);\r\n    throw new ArgumentException( &#34;&#34;, ex);\r\n}\r\ncatch (Exception ex) {<!-- -->\r\n    Console.WriteLine( &#34;4&#34;);\r\n}\r\nfinally {<!-- -->\r\n    Console.WriteLine( &#34;5&#34;);\r\n}\r\nConsole.WriteLine( &#34;6&#34;);</pre>','','<p>Sample marking guide</p>',5),(2,6,'What will be the output of the following code?<pre>public class Tester {<!-- --><br />    private int t &#61; 1;<br />    private static int p &#61; 1;<br /><br />    static void main(string[]args) {<!-- --><br />        for (int counter &#61; 0; counter &lt; 5; counter&#43;&#43;) {<!-- --><br />            Tester tester &#61; new Tester();<br />            tester.test();<br />        }<br />    }<br />    public void test() {<!-- --><br />        Console.WriteLine(&#34;T &#34; &#43; t &#43; &#34; &#34; &#43; p);<br /><br />        t&#43;&#43;;<br />        p&#43;&#43;;<br />    }<br />}</pre>','','<p>Sample marking guide</p>',5),(2,7,'What will happen when you attempt to compile and run the following code?<pre>public class StringHolder {<!-- --><br /><br />    public StringHolder(string value) {<!-- --><br />        Value &#61; value;<br />    }<br /><br />    public string Value {<!-- --><br />        get;<br />        set;<br />    }<br />}<br /><br />public class EqualityExample {<!-- --><br /><br />    static void Main(string[]args) {<!-- --><br />        var s &#61; new StringHolder(&#34;Marcus&#34;);<br />        var s2 &#61; new StringHolder(&#34;Marcus&#34;);<br /><br />        if (s &#61;&#61; s2) {<!-- --><br />            Console.WriteLine(&#34;we have a match&#34;);<br />        } else {<!-- --><br />            Console.WriteLine(&#34;not equal&#34;);<br />        }<br />    }<br />}</pre>','','<p>Sample marking guide</p>',3),(2,8,'<p>Given the following code what will be the output?</p><pre>public class Example {<!-- -->\r\n    public static void main(string[]args) {<!-- -->\r\n        Example example &#61; new Example();\r\n        example.Method1();\r\n    }\r\n    public void Method1() {<!-- -->\r\n        int i &#61; 99;\r\n        ValueHolder vh   &#61; new ValueHolder();\r\n        vh.i &#61; 30;\r\n        Method2(vh, i);\r\n        Console.WriteLine(vh.i);\r\n    }\r\n\r\n    public void Method2(ValueHolder v, int i) {<!-- -->\r\n        i   &#61; 0;\r\n        v.i &#61; 20;\r\n        ValueHolder vh &#61; new ValueHolder();\r\n        v  &#61; vh;\r\n        Console.WriteLine(v.i &#43; &#34; &#34; &#43; i);\r\n    }\r\n\r\n    class ValueHolder {<!-- -->\r\n        public int i &#61; 10;\r\n    }\r\n\r\n}</pre>','','<p>Sample marking guide.</p>',5),(2,9,'<p>You have two arrays:</p><p>int[] &#61; new int[]{ 1, 2, 3, 5, 4 };</p><p>int[] &#61; new int[]{ 3, 2, 9, 3, 7 };</p><p>Write a method that returns a collection of common elements.</p><p>Please note that the arrays can contain repeating elements, and are not in any particular order.</p><p>Complete the method in the space below. Any necessary variables should be shown.</p><p>Extra credit will be awarded for solutions that are efficient (let&#39;s say given very large input arrays).</p><p>Extra credit will be awarded if the method can handle any arbitrary number of arrays, i.e. a,b,c</p>','','<p>Extra credit will be awarded for solutions that are efficient (let&#39;s say given very large input arrays). 3 marks </p><p>Extra credit will be awarded if the method can handle any arbitrary number of arrays, i.e. a,b,c 5 marks</p>',10),(2,10,'You have been asked to develop a function called IsOdd that returns true if a given integer parameter is odd, or false if even. Write this function below.','','<p>Max mark awarded for a function using mod operator</p>',5),(2,11,'<p>You need to design a software to control traffic lights at a junction where traffic are coming from four sides. It should follow basic traffic rules, allow a pedestrian to cross the road, and traffic to pass in reasonable time. How do you optimize the waiting time with respect to high traffic from one direction e.g. during morning and evening rush hours?</p><p style=\"text-align:center\"><br /><br /><img src=\"https://1.bp.blogspot.com/-72o5a8bUt5M/V5jOz5jrFiI/AAAAAAAAGuI/GNyjIrXwXZkaiOInafruN6bA_iOE1Ub_ACLcB/s1600/traffic%2Bcontrol%2Bsystem.png\" style=\"width:300px\" /></p><p><br />Use object orientated theory to make the domain objects more efficient for software implementation.</p>','','<p>Sample marking guide.</p>',5),(2,12,'a) This is the singleton pattern, what is it commonly used for?b) If there are errors, fix them in the code snippet.c) Please comment on any strengths or weaknesses of the above implementation. For instance, are there cases were it doesn&#39;t guarantee only one instance is created?<pre>class Singleton {<!-- --><br />    Singleton();<br /><br />    private Singleton mInstance &#61; null;<br /><br />    public static Singleton instance() {<!-- --><br />        if (mInstance &#61;&#61; null) {<!-- --><br />            mInstance &#61;&#61; new Singleton();<br />        }<br />        return mInstance;<br />    }<br />}</pre>','','<p>2 marks for explanation and comments. 2 for each found error in the code. </p>',10),(2,13,'<p>The questions section are based on the schema definitions defined below.</p><pre>CREATE TABLE Products(ProductId integer PRIMARY KEY, ProductName varchar(100));\r\nCREATE TABLE Invoices(InvoiceNumber integer PRIMARY KEY, ProductId integer, InvoiceDate datetime, InvoiceCost decimal(6, 2)InvoiceComment varchar(200));</pre><p>a) What is the purpose of the following SQL statement:</p><pre>SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId;</pre><p>The application programs using the tables allow a moduleLeader to find an invoice by date and time range using a select statement of the form:</p><pre>SELECT InvoiceNumber, InvoiceCost FROM Invoices WHERE InvoiceDate &gt;&#61; ‘2000/05/23 15:00:00’ AND InvoiceDate &lt; ‘2000/05/23 16:00:00’;</pre><p>b) However as the Invoices table grew larger the execution times of these queries increased.Describe a change to the Database schema that would decrease the query execution time.</p><p>A test has been written to validate the query from question 2.1. The pseudo-code is:</p><pre>//Connect to the database\r\nFirstResult &#61; Execute Query(“SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId”)\r\n//Add a new invoice  \r\nSecondResult &#61; Execute Query(“SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId”)\r\nCheck that FirstResult and SecondResult differ by the added amount</pre><p><br /></p><p>But on a shared database the test keeps failing because someone else was running the same test so the second query picked up two invoices, what can we do to avoid this problem?</p><p>c)Write a query to return the product Name, number of the product sold and the highest price paid for it?</p>','','<p>Part A is one mark. Part b is 4 marks Part c is 5 marks.</p>',10),(2,14,'<p>Write a short functional specification of a typical login dialog as seen below<br /><br /><img src=\"http://www.java2s.com/Code/JavaImages/SwingXLoginDialog.PNG\" style=\"width:300px\" /><br /><br /></p>','','<p>2 marks for each suitable feature specification. </p>',8),(2,15,'<p>A) Write a short paragraph describing the concept of recursion.</p><p>B) Write a simple recursive method to print out the contents of an array</p>','','<p>2 marks to be awarded for explanation and 6 for a recursive method. </p>',8),(2,16,'<p>The following code checks the validity of a date (which is passed in as 2 integer parameters). The code is looking to check the validity of all the days of the year, design the test data necessary to fully test this code. Note you do not need to consider leap years in your answer.</p><pre>public class DateValidator\r\n{<!-- -->\r\n    private static int daysInMonth [12] &#61;  {<!-- -->31,28,31,30,31,30,31,31,30,31,30,31};\r\n        public static bool validate(int day, int month)\r\n        {<!-- -->     \r\n            if (month&gt;&#61;1 &amp;&amp; month &lt;&#61; 12 &amp;&amp; day &gt;&#61;1 &amp;&amp; day &lt;&#61;daysInMonth[month-1])\r\n            {<!-- -->\r\n                return true:\r\n            }\r\n            else {<!-- -->\r\n                return false;\r\n            }\r\n    }\r\n}</pre>','','<p>Award one mark per test case and another mark for each valid data example. </p>',5),(2,17,'What tests can be executed against a web site that has static pages?  For example, spell checking text or verifying image downloads.','','<p>Award one mark per valid test and another for valid explanation why. </p>',5),(2,18,'<p>Correct answer is B<br />A) Some text</p><p>B) Some text</p><p>C) Some text</p>','{\"1\":\"\\\\b([B])\\\\b\",\"B\":1}','<p>Correct Answer Is B</p>',1),(2,19,'<p>Correct answer is A <br />A) Some text</p><p>B) Some text</p><p>C) Some text</p>','{\"1\":\"\\\\b([A])\\\\b\",\"A\":1}','<p>Correct Answer Is A </p>',1),(2,20,'<p>Correct answer is C <br />A) Some text</p><p>B) Some text</p><p>C) Some text</p>','{\"1\":\"\\\\b([C])\\\\b\",\"C\":1}','<p>Correct answer is C </p>',1),(2,21,'<p>Correct answer is A <br />A) Some text</p><p>B) Some text</p><p>C) Some text</p>','{\"1\":\"\\\\b([A])\\\\b\",\"A\":1}','<p>Correct answer is A</p>',1),(2,22,'<p>Correct answer are A,B <br />A) Some text</p><p>B) Some text</p><p>C) Some text</p>','{\"1\":\"\\\\b([A])\\\\b\",\"2\":\"\\\\b([A,B]),(?!\\\\1)([A,B])\\\\b\",\"A,B\":2,\"A|B\":1}','<p>Correct answer are A,B. A should give one mark.</p>',1),(2,23,'<p>Correct answer are A,B,C <br />A) Some text</p><p>B) Some text</p><p>C) Some text</p>','{\"1\":\"\\\\b([A])\\\\b\",\"2\":\"\\\\b([A,B]),(?!\\\\1)([A,B])\\\\b\",\"3\":\"\\\\b([A,B,C]),(?!\\\\1)([A,B,C]),(?!\\\\1)([A,B,C])\\\\b\",\"([abc])(?!1)[abc](?!1)[abc]\":3,\"([abc])(?!1)[abc]\":2,\"A|B|C\":1}','<p>Correct answer are A,B,C. A should give one mark. AB should give 2</p>',1),(2,24,'<p>Correct answer are A,B,C <br />A) Some text</p><p>B) Some text</p><p>C) Some text</p>','{\"1\":\"\\\\b([])\\\\b\",\"2\":\"\\\\b([A,B]),(?!\\\\1)([A,B])\\\\b\",\"3\":\"\\\\b([A,B,C]),(?!\\\\1)([A,B,C]),(?!\\\\1)([A,B,C])\\\\b\",\"([abc])(?!1)[abc](?!1)[abc]\":3,\"([abc])(?!1)[abc]\":2,\"A|B|C\":1}','<p>Correct answer are A,B,C</p>',1),(2,25,'<p>Correct answer is B <br />A) Some text</p><p>B) Some text</p><p>C) Some text</p>','{\"1\":\"\\\\b([B])\\\\b\",\"B\":1}','<p>Correct answer is B</p>',1),(2,26,'<p>Correct answer is A <br />A) Some text</p><p>B) Some text</p><p>C) Some text</p>','{\"1\":\"\\\\b([A])\\\\b\",\"A\":1}','<p>Correct answer is A</p>',1),(2,27,'<p>Correct answer is C <br />A) Some text</p><p>B) Some text</p><p>C) Some text</p>','{\"1\":\"\\\\b([C])\\\\b\",\"C\":1}','<p>Correct answer is C </p>',1),(2,28,'<p>Software that its on top of hardware and below application software is [[1]]<br /><br />Give three examples of such software: <br />1. [[2]]<br />2. [[3]]<br />3. [[4]]</p>','[{\"blankNo\":\"[[1]]\",\"answer\":\"operating system\",\"regex\":\"(?i)(operating\\\\s+system)\",\"mark\":\"2\",\"options\":{\"space\":true,\"punctuation\":false,\"case\":true}},{\"blankNo\":\"[[1]]\",\"answer\":\"OS\",\"regex\":\"(?i)(OS)\",\"mark\":\"2\",\"options\":{\"space\":true,\"punctuation\":false,\"case\":true}},{\"blankNo\":\"[[2]]\",\"answer\":\"\",\"regex\":\"((i?)(Windows))|((i?)(Linux))|((i?)(Ubuntu))|((i?)(Android))|((i?)(OSX))|((i?)(Fedora))\",\"mark\":\"1\",\"options\":{\"space\":false,\"punctuation\":false,\"case\":false}},{\"blankNo\":\"[[3]]\",\"answer\":\"\",\"regex\":\"((i?)(Windows))|((i?)(Linux))|((i?)(Ubuntu))|((i?)(Android))|((i?)(OSX))|((i?)(Fedora))\",\"mark\":\"1\",\"options\":{\"space\":false,\"punctuation\":false,\"case\":false}},{\"blankNo\":\"[[4]]\",\"answer\":\"\",\"regex\":\"((i?)(Windows))|((i?)(Linux))|((i?)(Ubuntu))|((i?)(Android))|((i?)(OSX))|((i?)(Fedora))\",\"mark\":\"1\",\"options\":{\"space\":false,\"punctuation\":false,\"case\":false}}]','<p>Answer 1 is operating system and it awards 2 marks. Rest are Windows or Linux or Ubuntu or Android or OSX or Fedora. Any from this list awards 1 mark.</p>',5);
/*!40000 ALTER TABLE `QuestionVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QuestionVersionAsset`
--

DROP TABLE IF EXISTS `QuestionVersionAsset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QuestionVersionAsset` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `questionVersionNumber` int(11) NOT NULL,
  `questionId` int(11) NOT NULL,
  `referenceName` varchar(104) DEFAULT NULL,
  `blobType` varchar(104) DEFAULT NULL,
  `_blob` longblob,
  PRIMARY KEY (`_id`),
  KEY `questionVersionNumber` (`questionVersionNumber`,`questionId`),
  CONSTRAINT `QuestionVersionAsset_ibfk_1` FOREIGN KEY (`questionVersionNumber`, `questionId`) REFERENCES `QuestionVersion` (`versionNumber`, `questionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QuestionVersionAsset`
--

LOCK TABLES `QuestionVersionAsset` WRITE;
/*!40000 ALTER TABLE `QuestionVersionAsset` DISABLE KEYS */;
/*!40000 ALTER TABLE `QuestionVersionAsset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QuestionVersionEntry`
--

DROP TABLE IF EXISTS `QuestionVersionEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QuestionVersionEntry` (
  `testPaperSectionVersionNo` int(11) NOT NULL,
  `testPaperSectionId` int(11) NOT NULL,
  `questionVersionNumber` int(11) NOT NULL,
  `questionId` int(11) NOT NULL,
  `referenceNumber` int(11) DEFAULT NULL,
  PRIMARY KEY (`questionVersionNumber`,`questionId`,`testPaperSectionVersionNo`,`testPaperSectionId`),
  KEY `testPaperSectionId` (`testPaperSectionId`,`testPaperSectionVersionNo`),
  KEY `questionId` (`questionId`,`questionVersionNumber`),
  CONSTRAINT `QuestionVersionEntry_ibfk_1` FOREIGN KEY (`testPaperSectionId`, `testPaperSectionVersionNo`) REFERENCES `TestPaperSectionVersion` (`testPaperSectionId`, `versionNumber`),
  CONSTRAINT `QuestionVersionEntry_ibfk_2` FOREIGN KEY (`questionId`, `questionVersionNumber`) REFERENCES `QuestionVersion` (`questionId`, `versionNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QuestionVersionEntry`
--

LOCK TABLES `QuestionVersionEntry` WRITE;
/*!40000 ALTER TABLE `QuestionVersionEntry` DISABLE KEYS */;
INSERT INTO `QuestionVersionEntry` VALUES (1,7,1,29,1),(2,7,1,29,1),(1,7,1,30,2),(2,7,1,30,2),(2,1,2,1,1),(2,1,2,2,2),(2,1,2,3,3),(2,1,2,4,4),(2,1,2,5,5),(2,1,2,6,6),(2,1,2,7,7),(2,1,2,8,8),(2,2,2,9,1),(2,2,2,10,2),(2,2,2,11,3),(2,3,2,12,1),(2,3,2,13,2),(2,4,2,14,1),(2,4,2,15,2),(2,5,2,16,2),(2,5,2,17,1),(2,6,2,18,1),(2,6,2,19,2),(2,6,2,20,3),(2,6,2,21,4),(2,6,2,22,5),(2,6,2,23,6),(2,6,2,24,7),(2,6,2,25,8),(2,6,2,26,9),(2,6,2,27,10),(2,7,2,28,3);
/*!40000 ALTER TABLE `QuestionVersionEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Role` (
  `referenceName` varchar(50) NOT NULL,
  PRIMARY KEY (`referenceName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role`
--

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;
INSERT INTO `Role` VALUES ('Admin'),('Author'),('Marker'),('ModuleLeader');
/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TermsAndConditions`
--

DROP TABLE IF EXISTS `TermsAndConditions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TermsAndConditions` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `termsAndConditions` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TermsAndConditions`
--

LOCK TABLES `TermsAndConditions` WRITE;
/*!40000 ALTER TABLE `TermsAndConditions` DISABLE KEYS */;
INSERT INTO `TermsAndConditions` VALUES (1,'Terms sample');
/*!40000 ALTER TABLE `TermsAndConditions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestDay`
--

DROP TABLE IF EXISTS `TestDay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestDay` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(50) NOT NULL,
  `startTime` varchar(5) DEFAULT NULL,
  `endTime` varchar(5) DEFAULT NULL,
  `endTimeWithExtraTime` varchar(5) DEFAULT NULL,
  `location` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestDay`
--

LOCK TABLES `TestDay` WRITE;
/*!40000 ALTER TABLE `TestDay` DISABLE KEYS */;
/*!40000 ALTER TABLE `TestDay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestDayEntry`
--

DROP TABLE IF EXISTS `TestDayEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestDayEntry` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `candidateId` int(11) NOT NULL,
  `testDayEntryStatus` varchar(36) DEFAULT NULL,
  `markingLock` varchar(36) DEFAULT NULL,
  `finalMark` int(11) DEFAULT NULL,
  `examId` int(11) DEFAULT NULL,
  `login` varchar(5) DEFAULT NULL,
  `password` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`_id`),
  KEY `candidateId` (`candidateId`),
  KEY `markingLock` (`markingLock`),
  KEY `examId` (`examId`),
  CONSTRAINT `TestDayEntry_ibfk_1` FOREIGN KEY (`candidateId`) REFERENCES `Candidate` (`_id`),
  CONSTRAINT `TestDayEntry_ibfk_2` FOREIGN KEY (`markingLock`) REFERENCES `User` (`_id`),
  CONSTRAINT `TestDayEntry_ibfk_3` FOREIGN KEY (`examId`) REFERENCES `Exam` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestDayEntry`
--

LOCK TABLES `TestDayEntry` WRITE;
/*!40000 ALTER TABLE `TestDayEntry` DISABLE KEYS */;
/*!40000 ALTER TABLE `TestDayEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestPaper`
--

DROP TABLE IF EXISTS `TestPaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestPaper` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `referenceName` varchar(150) NOT NULL,
  `timeAllowed` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestPaper`
--

LOCK TABLES `TestPaper` WRITE;
/*!40000 ALTER TABLE `TestPaper` DISABLE KEYS */;
INSERT INTO `TestPaper` VALUES (1,'Interview Test-Graduate (C#)',60);
/*!40000 ALTER TABLE `TestPaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestPaperSection`
--

DROP TABLE IF EXISTS `TestPaperSection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestPaperSection` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `referenceName` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestPaperSection`
--

LOCK TABLES `TestPaperSection` WRITE;
/*!40000 ALTER TABLE `TestPaperSection` DISABLE KEYS */;
INSERT INTO `TestPaperSection` VALUES (1,'C# Language'),(2,'Problem Solving'),(3,'Architecture and Theory'),(4,'Written Communication'),(5,'Test Case Design'),(6,'Multiple Choice'),(7,'General Knowledge Fill in the blanks');
/*!40000 ALTER TABLE `TestPaperSection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestPaperSectionVersion`
--

DROP TABLE IF EXISTS `TestPaperSectionVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestPaperSectionVersion` (
  `testPaperSectionId` int(11) NOT NULL,
  `versionNumber` int(11) NOT NULL,
  `noOfQuestionsToAnswer` int(11) DEFAULT NULL,
  `sectionDescription` mediumtext,
  `timeScale` int(11) DEFAULT NULL,
  PRIMARY KEY (`testPaperSectionId`,`versionNumber`),
  CONSTRAINT `TestPaperSectionVersion_ibfk_1` FOREIGN KEY (`testPaperSectionId`) REFERENCES `TestPaperSection` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestPaperSectionVersion`
--

LOCK TABLES `TestPaperSectionVersion` WRITE;
/*!40000 ALTER TABLE `TestPaperSectionVersion` DISABLE KEYS */;
INSERT INTO `TestPaperSectionVersion` VALUES (1,2,8,'<p>Sample instructions text.</p>',22),(2,2,2,'<p>Sample instructions text,</p>',10),(3,2,1,'<p>Sample instructions text.</p>',10),(4,2,1,'<p>Sample instructions text</p>',8),(5,2,1,'<p>Sample section instructions text.</p>',10),(6,2,10,'<p>Sample section instructions</p>',20),(7,1,3,'<p>Answer all the questions in this section. </p>',8),(7,2,3,'<p>Answer all the questions in this section. </p>',8);
/*!40000 ALTER TABLE `TestPaperSectionVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestPaperSectionVersionEntry`
--

DROP TABLE IF EXISTS `TestPaperSectionVersionEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestPaperSectionVersionEntry` (
  `testPaperSectionVersionNo` int(11) NOT NULL,
  `testPaperSectionId` int(11) NOT NULL,
  `testPaperVersionNo` int(11) NOT NULL,
  `testPaperId` int(11) NOT NULL,
  `referenceNumber` int(11) DEFAULT NULL,
  PRIMARY KEY (`testPaperSectionVersionNo`,`testPaperSectionId`,`testPaperVersionNo`,`testPaperId`),
  KEY `testPaperSectionId` (`testPaperSectionId`,`testPaperSectionVersionNo`),
  KEY `testPaperId` (`testPaperId`,`testPaperVersionNo`),
  CONSTRAINT `TestPaperSectionVersionEntry_ibfk_1` FOREIGN KEY (`testPaperSectionId`, `testPaperSectionVersionNo`) REFERENCES `TestPaperSectionVersion` (`testPaperSectionId`, `versionNumber`),
  CONSTRAINT `TestPaperSectionVersionEntry_ibfk_2` FOREIGN KEY (`testPaperId`, `testPaperVersionNo`) REFERENCES `TestPaperVersion` (`testPaperId`, `versionNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestPaperSectionVersionEntry`
--

LOCK TABLES `TestPaperSectionVersionEntry` WRITE;
/*!40000 ALTER TABLE `TestPaperSectionVersionEntry` DISABLE KEYS */;
INSERT INTO `TestPaperSectionVersionEntry` VALUES (2,1,1,1,1),(2,2,1,1,2),(2,3,1,1,3),(2,4,1,1,4),(2,5,1,1,5),(2,7,1,1,6);
/*!40000 ALTER TABLE `TestPaperSectionVersionEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestPaperVersion`
--

DROP TABLE IF EXISTS `TestPaperVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestPaperVersion` (
  `authorId` varchar(36) DEFAULT NULL,
  `testPaperId` int(11) NOT NULL,
  `instructionsText` varchar(1024) DEFAULT NULL,
  `versionNumber` int(11) NOT NULL,
  PRIMARY KEY (`testPaperId`,`versionNumber`),
  KEY `authorId` (`authorId`),
  CONSTRAINT `TestPaperVersion_ibfk_1` FOREIGN KEY (`authorId`) REFERENCES `User` (`_id`),
  CONSTRAINT `TestPaperVersion_ibfk_2` FOREIGN KEY (`testPaperId`) REFERENCES `TestPaper` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestPaperVersion`
--

LOCK TABLES `TestPaperVersion` WRITE;
/*!40000 ALTER TABLE `TestPaperVersion` DISABLE KEYS */;
INSERT INTO `TestPaperVersion` VALUES ('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d',1,'<b>Normal time Allowed: Up to 60 Minutes (all times indicative only)</b>Answer as many questions as you can. If you are unsure, it is better to skip the question completely rather than provide an incorrect answer. An indicative time is provided with each question as a rough guide for how long the question should take to complete.',1);
/*!40000 ALTER TABLE `TestPaperVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `_id` varchar(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('045d785e-cc44-4e7e-89b8-2df505c0b72a','Lindsay','Marshall','Lindsay.Marshall@newcastle.ac.uk','pass'),('1be448ff-1a2e-456f-9594-4042e7ef6ab2','Steve','Riddle','steve.riddle@newcastle.ac.uk','pass'),('3ca33b4f-009a-4403-829b-e2d20b3d47c2','Bob','Smith','sampleMarker','pass'),('42ea9f70-0d4f-11e7-93ae-92361f002671','Neil','Speirs','neil.speirs@newcastle.ac.uk','pass'),('92f6e08a-2dba-467c-96e8-a1ec1c87940b','John','Colquhoun','john.colquhoun@newcastle.ac.uk','pass'),('94cbbbc4-f94d-40d2-b0cf-e642eb36e73a','Sam','Armstrong','sampleAdmin','pass'),('95818d99-492d-4c50-80b8-abae310bd2f3','Matthew','Collison','matthew.collison@ncl.ac.uk','pass'),('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d','Jack','Brown','sampleAll','pass'),('AUTO-MARKER','Auto-Marker','','AeKdH4njtu3r4K7tDe2k4PxoIcQdXJW5JcWiGm70','L3F67YCO5CQHdQLNu1mDD8mmNLW2gzDvtEEzBI45'),('fba6a561-8999-4b19-9c57-232895d024c6','Grzegorz','Brzenczyszczykiewicz','sampleAuthor','pass');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserRole`
--

DROP TABLE IF EXISTS `UserRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserRole` (
  `userId` varchar(36) NOT NULL,
  `roleId` varchar(50) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `UserRole_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`_id`),
  CONSTRAINT `UserRole_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `Role` (`referenceName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserRole`
--

LOCK TABLES `UserRole` WRITE;
/*!40000 ALTER TABLE `UserRole` DISABLE KEYS */;
INSERT INTO `UserRole` VALUES ('045d785e-cc44-4e7e-89b8-2df505c0b72a','Admin'),('1be448ff-1a2e-456f-9594-4042e7ef6ab2','Admin'),('42ea9f70-0d4f-11e7-93ae-92361f002671','Admin'),('92f6e08a-2dba-467c-96e8-a1ec1c87940b','Admin'),('94cbbbc4-f94d-40d2-b0cf-e642eb36e73a','Admin'),('95818d99-492d-4c50-80b8-abae310bd2f3','Admin'),('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d','Admin'),('045d785e-cc44-4e7e-89b8-2df505c0b72a','Author'),('1be448ff-1a2e-456f-9594-4042e7ef6ab2','Author'),('42ea9f70-0d4f-11e7-93ae-92361f002671','Author'),('92f6e08a-2dba-467c-96e8-a1ec1c87940b','Author'),('95818d99-492d-4c50-80b8-abae310bd2f3','Author'),('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d','Author'),('fba6a561-8999-4b19-9c57-232895d024c6','Author'),('045d785e-cc44-4e7e-89b8-2df505c0b72a','Marker'),('1be448ff-1a2e-456f-9594-4042e7ef6ab2','Marker'),('3ca33b4f-009a-4403-829b-e2d20b3d47c2','Marker'),('42ea9f70-0d4f-11e7-93ae-92361f002671','Marker'),('92f6e08a-2dba-467c-96e8-a1ec1c87940b','Marker'),('95818d99-492d-4c50-80b8-abae310bd2f3','Marker'),('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d','Marker'),('045d785e-cc44-4e7e-89b8-2df505c0b72a','ModuleLeader'),('1be448ff-1a2e-456f-9594-4042e7ef6ab2','ModuleLeader'),('42ea9f70-0d4f-11e7-93ae-92361f002671','ModuleLeader'),('92f6e08a-2dba-467c-96e8-a1ec1c87940b','ModuleLeader'),('95818d99-492d-4c50-80b8-abae310bd2f3','ModuleLeader'),('9f4db0ac-b18a-4777-8b04-b72a0eeccf5d','ModuleLeader');
/*!40000 ALTER TABLE `UserRole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-27  8:42:19
