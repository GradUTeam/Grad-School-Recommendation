-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 06, 2015 at 03:15 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `gradu`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(50) NOT NULL,
  `admin_username` varchar(50) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_username` (`admin_username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `admin_name`, `admin_username`, `encrypted_password`, `salt`) VALUES
(1, 'Pranshu', 'pranshu02', 'IVFOLHXCA1GhK6osDSj9Z472ZGU3NGRhMDcxNzlh', '74da07179a'),
(2, 'Pranshu', 'pran', 'PFZ5HVBjEnYeHp4/AhzJ0VAcvtphMjEzYzMxYTBk', 'a213c31a0d');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `username` varchar(45) NOT NULL,
  `encrypted_password` varchar(45) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `fullname` varchar(45) NOT NULL,
  `emailId` varchar(45) NOT NULL,
  `courseInterest` varchar(45) NOT NULL,
  `undergradGpa` double NOT NULL,
  `greQuant` double NOT NULL,
  `greVerbal` double NOT NULL,
  `greAWA` double NOT NULL,
  `greTotal` double NOT NULL,
  `engScore` double NOT NULL,
  `workExp` double NOT NULL,
  `papersPublished` int(11) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`username`, `encrypted_password`, `salt`, `fullname`, `emailId`, `courseInterest`, `undergradGpa`, `greQuant`, `greVerbal`, `greAWA`, `greTotal`, `engScore`, `workExp`, `papersPublished`) VALUES
('ak', 'FMC3mROybtFnCYJECAu87JebwYowYTY4ZGIzMjJh', '0a68db322a', 'Akshay', 'ak@gma.com', 'computer science', 3, 144, 144, 3, 288, 111, 5, 4),
('aks', '9ZVHnJ/iMPI8n6l4chrKVVt7UHE2NDMyMzNkZjg5', '643233df89', 'Akshay', 'ak@gma.com', 'computer science', 3, 144, 144, 3, 288, 111, 5, 4),
('aksh', 'LpvqdVLsTNLsT05lJo+z23lcc3IxMWZlN2FiZjc5', '11fe7abf79', 'Akshay', 'ak@gma.com', 'computer science', 3, 144, 144, 3, 288, 111, 7, 4),
('p', '2xdVjNyEjo37mrO18k5ZnUrvdeRmYWVjY2YzNjVh', 'faeccf365a', 'Pp', 'k', 'computer science', 5, 135, 135, 3, 270, 112, 4, 4),
('Pranshu', 'jk+MNSEpquN2boT6k8+46ALk3aAzNjRmNWExZDUw', '364f5a1d50', 'user16', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 0),
('user1', 'PFZ5HVBjEnYeHp4/AhzJ0VAcvtphMjEzYzMxYTBk', 'a213c31a0d', 'Pranshu', 'pranshu02@gmail.com', 'Computer Science', 3, 144, 144, 3, 288, 111, 2, 5),
('user10', 'UskAyry/XSXP4xU7xkmve5MiN5NmNDMxMDVjZDU5', 'f43105cd59', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 0),
('user11', 'SbB5Ol1aMaZAgSPFVE+pKQ+0DFdlNTJjNDBlZGI3', 'e52c40edb7', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 4),
('user12', 'hlWN99uyg662ioFm+23mKoSpsUwyYjAxMWUwYzJj', '2b011e0c2c', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 4),
('user13', 'LJxYQFBHWZ9y75LVulTklQeJams1YTc2MTkwNDA1', '5a76190405', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 4),
('user14', 'lGsPf3YUkA1h+isO8Gn8NUUGVaFmY2U0ZjRiYjQw', 'fce4f4bb40', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 4),
('user15', 'lACc79pela/6jB5C2QQv3zU1IQAyNGExNjdmNWUz', '24a167f5e3', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 4),
('user17', 'hXFSwxXIUDbTGWtJ113RRatNBZRhODBhNjY3OGZh', 'a80a6678fa', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 5, 288, 111, 4, 0),
('user18', 'GutkvuLkrPdW+se6fy5KM+fws4QwMmRiOGU4NTk0', '02db8e8594', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 5, 288, 111, 4, 4),
('user2', 'oNxp0YDzWG2d1tSbk3/IXlQD0L9lNzRmMGYxZjA5', 'e74f0f1f09', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 0),
('user20', 'up8uvTcAzsFwG7ornq2+YdgFISAyMTAzNjJhY2M1', '210362acc5', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 5, 288, 111, 4, 4),
('user3', 'qYK80lI+7j/bDqdccX17SOyEwLo0MTQzMThiM2Ux', '414318b3e1', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 0),
('user4', 'eYlEwWgZgm2Q/IvTOVNFOcr33b4wYjQzNzNhYmZj', '0b4373abfc', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 0),
('user5', 'DlbokOw/2NgO+rBEGj0S87NsZc0xNDY4MGQ2OWQ0', '14680d69d4', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 0),
('user6', 'D2XgWeyPeLJANLLqgDel5ExYBe82Zjc0OWRhMWEz', '6f749da1a3', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 0),
('user7', 'Psb+MSRKZuS3yNusi1/ZJ+xEtvFkOTEzMjU4ZTUx', 'd913258e51', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 0),
('user8', '6La/SONf6JDWT79jVIm2eeR2CUQ2MTEzMmZhNjcx', '61132fa671', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 0),
('user9', 'SubE6qDgmf2j+8nWA7kaNjnswXQzN2MxZTEzZmQ3', '37c1e13fd7', 'Pranshu', 'pp@gmail.com', 'computer science', 3, 144, 144, 3.5, 288, 111, 4, 0);

-- --------------------------------------------------------

--
-- Table structure for table `university`
--

CREATE TABLE IF NOT EXISTS `university` (
  `univId` int(11) NOT NULL,
  `univName` varchar(100) NOT NULL,
  `univDescription` varchar(2000) NOT NULL,
  `univCost` double NOT NULL,
  `univCity` varchar(45) NOT NULL,
  `uniState` varchar(45) NOT NULL,
  `univResearchOpp` varchar(100) NOT NULL,
  `univAccRate` double NOT NULL,
  PRIMARY KEY (`univId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `university`
--

INSERT INTO `university` (`univId`, `univName`, `univDescription`, `univCost`, `univCity`, `uniState`, `univResearchOpp`, `univAccRate`) VALUES
(1001, 'University at Buffalo-SUNY', 'Founded in 1846, the University at Buffalo embraces a tradition of academic excellence, pathbreaking research, provocative creative expression, and meaningful community service. Building on this tradition, UB offers students an interdisciplinary educational experience with opportunities to engage in inquiry and discovery, experiential learning, leadership, and global citizenry. With more than 300 undergraduate, graduate, and professional degree programs, UB is New York State''s largest and most comprehensive public research university. Featuring a top-ranked faculty, Division 1A athletics, and the cultural and recreational riches of Western New York, UB provides a diverse, international, and intellectually stimulating environment for learning and exploration.', 22290, 'Buffalo', 'NewYork', 'Excellent', 57.8),
(1002, 'Arizona State University-Tempe', 'Arizona State University—Tempe, which has one of the largest undergraduate populations in the nation, offers students a wide range of academic and extracurricular options. Collectively, the campuses in Tempe and three other locations in the Phoenix area offer students more than 300 undergraduate academic programs to pick from and more than 500 clubs and organizations to check out. For time away from Arizona, students can choose from more than 250 study abroad options. Freshmen are required to live on campus, unless they commute from a parent''s home, are married or fall under other exceptions. The Arizona State Sun Devils sports teams compete in the NCAA Division I Pac-12 Conference and are particularly known for their baseball prowess.', 24503, 'Tempe', 'Arizona', 'Good', 84.3),
(1003, 'Stanford University', 'Stanford University’s pristine campus is located in California’s Bay Area, about 30 miles from San Francisco. Stanford offers a wide range of student organizations, including the Stanford Pre-Business Association and Stanford Solar Car Project, which designs, builds and races a solar car every two years. The Stanford Cardinals are well known for the traditional "Big Game" against Cal, an annual football competition that awards the Stanford Axe—a sought-after trophy—to the victor. Stanford also has successful programs in tennis and golf. Only freshman are required to live on campus, but students are guaranteed housing for all four years and most choose to remain on campus. Greek life at Stanford represents approximately 10 percent of the student body.', 46320, 'Stanford', 'California', 'Excellent', 5.1),
(1007, 'Rochester Institute of Technology', 'Despite its name, the Rochester Institute of Technology offers programs in the liberal arts, art and design, and business, in addition to science and technology. Co-ops – paid, full-time work experiences – are an important part of an RIT education. For some majors, co-ops are required; for others, they are encouraged. Students also have the option of completing one of about 20 accelerated degree programs, earning, for example, both a bachelor''s degree and an MBA in five years.\r\n', 37124, 'Rochester', 'NewYork', 'Average', 59),
(1009, 'California State University-Los Angeles ', 'The California State University—Los Angeles, also known as Cal State LA or CSULA, is a state school that operates on the quarter system. For students who commute, Cal State LA is fairly accessible; it has a train stop on the Metrolink, and nearly 20 bus lines stop nearby. For students who want to live on campus, the university provides residence halls and apartments.', 17505, 'Los Angeles', 'California', 'Average', 70),
(1227, 'columbia', 'cc', 20000, '', 'NewYork', 'Excellent', 20),
(1329, 'iitc', 'cc', 20000, '', 'NewYork', 'Excellent', 20),
(1433, 'caltech', 'cali', 52222, '', 'Illinois', 'Average', 55);

-- --------------------------------------------------------

--
-- Table structure for table `universitycivilengrank`
--

CREATE TABLE IF NOT EXISTS `universitycivilengrank` (
  `univId` int(11) NOT NULL,
  `CivilEngRank` int(11) NOT NULL,
  PRIMARY KEY (`univId`),
  KEY `univId` (`univId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `universitycivilengrank`
--

INSERT INTO `universitycivilengrank` (`univId`, `CivilEngRank`) VALUES
(1001, 59),
(1002, 40),
(1003, 3),
(1007, 165),
(1009, 171),
(1329, 33),
(1433, 33);

-- --------------------------------------------------------

--
-- Table structure for table `universitycompscirank`
--

CREATE TABLE IF NOT EXISTS `universitycompscirank` (
  `univId` int(11) NOT NULL,
  `compSciRank` int(11) NOT NULL,
  PRIMARY KEY (`univId`),
  KEY `univId` (`univId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `universitycompscirank`
--

INSERT INTO `universitycompscirank` (`univId`, `compSciRank`) VALUES
(1001, 63),
(1002, 50),
(1003, 4),
(1007, 156),
(1009, 169),
(1329, 11),
(1433, 11);

-- --------------------------------------------------------

--
-- Table structure for table `universityelecengrank`
--

CREATE TABLE IF NOT EXISTS `universityelecengrank` (
  `univId` int(11) NOT NULL,
  `ElecEngRank` int(11) NOT NULL,
  PRIMARY KEY (`univId`),
  KEY `univId` (`univId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `universityelecengrank`
--

INSERT INTO `universityelecengrank` (`univId`, `ElecEngRank`) VALUES
(1001, 59),
(1002, 49),
(1003, 2),
(1007, 167),
(1009, 178),
(1329, 22),
(1433, 22);

-- --------------------------------------------------------

--
-- Table structure for table `universitymechengrank`
--

CREATE TABLE IF NOT EXISTS `universitymechengrank` (
  `univId` int(11) NOT NULL,
  `MechEngRank` int(11) NOT NULL,
  PRIMARY KEY (`univId`),
  KEY `univId` (`univId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `universitymechengrank`
--

INSERT INTO `universitymechengrank` (`univId`, `MechEngRank`) VALUES
(1001, 59),
(1002, 52),
(1003, 3),
(1007, 155),
(1009, 175),
(1329, 55),
(1433, 55);

-- --------------------------------------------------------

--
-- Table structure for table `universitymisrank`
--

CREATE TABLE IF NOT EXISTS `universitymisrank` (
  `univId` int(11) NOT NULL,
  `misRank` int(11) NOT NULL,
  PRIMARY KEY (`univId`),
  KEY `univId` (`univId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `universitymisrank`
--

INSERT INTO `universitymisrank` (`univId`, `misRank`) VALUES
(1001, 62),
(1002, 59),
(1003, 5),
(1007, 167),
(1009, 175),
(1329, 11),
(1433, 44);

-- --------------------------------------------------------

--
-- Table structure for table `universitynationalrank`
--

CREATE TABLE IF NOT EXISTS `universitynationalrank` (
  `univId` int(11) NOT NULL,
  `nationalRank` int(11) NOT NULL,
  PRIMARY KEY (`univId`),
  KEY `univId` (`univId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `universitynationalrank`
--

INSERT INTO `universitynationalrank` (`univId`, `nationalRank`) VALUES
(1001, 60),
(1002, 45),
(1003, 3),
(1007, 160),
(1009, 178),
(1329, 11),
(1433, 55);

-- --------------------------------------------------------

--
-- Table structure for table `universitysearchcriteria`
--

CREATE TABLE IF NOT EXISTS `universitysearchcriteria` (
  `univId` int(11) NOT NULL,
  `minGPA` double NOT NULL,
  `minGreQuant` double NOT NULL,
  `minGreVerbal` double NOT NULL,
  `minAWA` double NOT NULL,
  `minGreTotal` double NOT NULL,
  `minToefl` double NOT NULL,
  `minIelts` double NOT NULL,
  `minWorkExp` double NOT NULL,
  PRIMARY KEY (`univId`),
  KEY `univId` (`univId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `universitysearchcriteria`
--

INSERT INTO `universitysearchcriteria` (`univId`, `minGPA`, `minGreQuant`, `minGreVerbal`, `minAWA`, `minGreTotal`, `minToefl`, `minIelts`, `minWorkExp`) VALUES
(1001, 3, 150, 150, 3, 300, 90, 6, 0),
(1002, 3, 150, 150, 3, 300, 90, 6, 0),
(1003, 3.8, 160, 160, 4, 320, 110, 8, 2),
(1007, 2, 120, 120, 2, 240, 80, 6, 0),
(1009, 2, 120, 120, 2, 240, 80, 6, 0),
(1329, 3, 150, 150, 3, 0, 112, 8, 4),
(1433, 3, 112, 112, 2, 0, 111, 8, 5);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `universitycivilengrank`
--
ALTER TABLE `universitycivilengrank`
  ADD CONSTRAINT `universitycivilengrank_ibfk_1` FOREIGN KEY (`univId`) REFERENCES `university` (`univId`) ON UPDATE CASCADE;

--
-- Constraints for table `universitycompscirank`
--
ALTER TABLE `universitycompscirank`
  ADD CONSTRAINT `universitycompscirank_ibfk_1` FOREIGN KEY (`univId`) REFERENCES `university` (`univId`) ON UPDATE CASCADE;

--
-- Constraints for table `universityelecengrank`
--
ALTER TABLE `universityelecengrank`
  ADD CONSTRAINT `universityelecengrank_ibfk_1` FOREIGN KEY (`univId`) REFERENCES `university` (`univId`) ON UPDATE CASCADE;

--
-- Constraints for table `universitymechengrank`
--
ALTER TABLE `universitymechengrank`
  ADD CONSTRAINT `universitymechengrank_ibfk_1` FOREIGN KEY (`univId`) REFERENCES `university` (`univId`) ON UPDATE CASCADE;

--
-- Constraints for table `universitymisrank`
--
ALTER TABLE `universitymisrank`
  ADD CONSTRAINT `universitymisrank_ibfk_1` FOREIGN KEY (`univId`) REFERENCES `university` (`univId`) ON UPDATE CASCADE;

--
-- Constraints for table `universitynationalrank`
--
ALTER TABLE `universitynationalrank`
  ADD CONSTRAINT `universitynationalrank_ibfk_1` FOREIGN KEY (`univId`) REFERENCES `university` (`univId`) ON UPDATE CASCADE;

--
-- Constraints for table `universitysearchcriteria`
--
ALTER TABLE `universitysearchcriteria`
  ADD CONSTRAINT `universitysearchcriteria_ibfk_1` FOREIGN KEY (`univId`) REFERENCES `university` (`univId`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
