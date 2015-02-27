-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 26, 2015 at 04:16 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `gently`
--

-- --------------------------------------------------------

--
-- Table structure for table `channel`
--

CREATE TABLE IF NOT EXISTS `channel` (
  `username` varchar(32) NOT NULL,
  `streamkey` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` longtext NOT NULL,
  `viewers` int(11) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `streamkey` (`streamkey`),
  KEY `viewers` (`viewers`),
  KEY `name` (`name`),
  KEY `viewers_2` (`viewers`),
  KEY `streamkey_2` (`streamkey`),
  KEY `username_2` (`username`),
  FULLTEXT KEY `username` (`username`),
  FULLTEXT KEY `description` (`description`),
  FULLTEXT KEY `description_2` (`description`),
  FULLTEXT KEY `description_3` (`description`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `channel`
--

INSERT INTO `channel` (`username`, `streamkey`, `name`, `description`, `viewers`) VALUES
('user1', 123, 'MEGAMAN SPEED RUN!', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultrices eu nunc vitae gravida. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur sagittis dapibus tellus vitae vulputate. Praesent placerat enim vitae lorem accumsan, vitae iaculis leo lacinia. Cras commodo velit non leo convallis bibendum. Nullam elementum nunc eu quam tempor vulputate. Quisque egestas sagittis dapibus. Mauris aliquet non leo a dignissim. Quisque vitae sem eget dui placerat cursus bibendum a leo. Vivamus blandit sodales mauris. Phasellus hendrerit ipsum quis auctor vehicula. Duis vel tellus erat. Duis venenatis metus quis quam cursus, a elementum mi tincidunt. Quisque sodales mattis pharetra. In egestas, lacus at vulputate condimentum, nisl nisl lacinia diam, in laoreet nulla mi eget purus. Aliquam varius eros ac elit euismod viverra eu ac tortor. Nam tempus magna magna, vitae suscipit ipsum convallis nec. Proin eu dolor at lectus venenatis congue sit amet id dui. Pellentesque iaculis mi nec elit maximus, non lacinia turpis convallis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque lobortis interdum massa, at luctus arcu. Praesent vehicula orci eget urna iaculis, a cursus felis malesuada.', 69),
('user2', 456, '2014 World Championship', '<div class="media">\r\n	<a class="pull-left" href="#">\r\n		<img class="media-object" src="http://static-cdn.jtvnw.net/jtv_user_pictures/panel-36029255-image-01752031c532a07c-320.png">\r\n	</a>\r\n	<div class="media-body">\r\n		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultrices eu nunc vitae gravida. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur sagittis dapibus tellus vitae vulputate. Praesent placerat enim vitae lorem accumsan, vitae iaculis leo lacinia. Cras commodo velit non leo convallis bibendum. Nullam elementum nunc eu quam tempor vulputate. Quisque egestas sagittis dapibus. Mauris aliquet non leo a dignissim. Quisque vitae sem eget dui placerat cursus bibendum a leo. Vivamus blandit sodales mauris. Phasellus hendrerit ipsum quis auctor vehicula. Duis vel tellus erat. Duis venenatis metus quis quam cursus, a elementum mi tincidunt. Quisque sodales mattis pharetra.</p>\r\n		<p>In egestas, lacus at vulputate condimentum, nisl nisl lacinia diam, in laoreet nulla mi eget purus. Aliquam varius eros ac elit euismod viverra eu ac tortor. Nam tempus magna magna, vitae suscipit ipsum convallis nec. Proin eu dolor at lectus venenatis congue sit amet id dui. Pellentesque iaculis mi nec elit maximus, non lacinia turpis convallis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque lobortis interdum massa, at luctus arcu. Praesent vehicula orci eget urna iaculis, a cursus felis malesuada.</p>\r\n	</div>\r\n</div>', 10),
('user3', 789, 'Watch me play blindfolded!', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultrices eu nunc vitae gravida. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur sagittis dapibus tellus vitae vulputate. Praesent placerat enim vitae lorem accumsan, vitae iaculis leo lacinia. Cras commodo velit non leo convallis bibendum. Nullam elementum nunc eu quam tempor vulputate. Quisque egestas sagittis dapibus. Mauris aliquet non leo a dignissim. Quisque vitae sem eget dui placerat cursus bibendum a leo. Vivamus blandit sodales mauris. Phasellus hendrerit ipsum quis auctor vehicula. Duis vel tellus erat. Duis venenatis metus quis quam cursus, a elementum mi tincidunt. Quisque sodales mattis pharetra. In egestas, lacus at vulputate condimentum, nisl nisl lacinia diam, in laoreet nulla mi eget purus. Aliquam varius eros ac elit euismod viverra eu ac tortor. Nam tempus magna magna, vitae suscipit ipsum convallis nec. Proin eu dolor at lectus venenatis congue sit amet id dui. Pellentesque iaculis mi nec elit maximus, non lacinia turpis convallis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque lobortis interdum massa, at luctus arcu. Praesent vehicula orci eget urna iaculis, a cursus felis malesuada.', 39),
('user4', 321, 'Untitled Channel', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultrices eu nunc vitae gravida. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur sagittis dapibus tellus vitae vulputate. Praesent placerat enim vitae lorem accumsan, vitae iaculis leo lacinia. Cras commodo velit non leo convallis bibendum. Nullam elementum nunc eu quam tempor vulputate. Quisque egestas sagittis dapibus. Mauris aliquet non leo a dignissim. Quisque vitae sem eget dui placerat cursus bibendum a leo. Vivamus blandit sodales mauris. Phasellus hendrerit ipsum quis auctor vehicula. Duis vel tellus erat. Duis venenatis metus quis quam cursus, a elementum mi tincidunt. Quisque sodales mattis pharetra. In egestas, lacus at vulputate condimentum, nisl nisl lacinia diam, in laoreet nulla mi eget purus. Aliquam varius eros ac elit euismod viverra eu ac tortor. Nam tempus magna magna, vitae suscipit ipsum convallis nec. Proin eu dolor at lectus venenatis congue sit amet id dui. Pellentesque iaculis mi nec elit maximus, non lacinia turpis convallis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque lobortis interdum massa, at luctus arcu. Praesent vehicula orci eget urna iaculis, a cursus felis malesuada.', 1),
('user5', 654, 'sitting around doing nothing', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultrices eu nunc vitae gravida. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur sagittis dapibus tellus vitae vulputate. Praesent placerat enim vitae lorem accumsan, vitae iaculis leo lacinia. Cras commodo velit non leo convallis bibendum. Nullam elementum nunc eu quam tempor vulputate. Quisque egestas sagittis dapibus. Mauris aliquet non leo a dignissim. Quisque vitae sem eget dui placerat cursus bibendum a leo. Vivamus blandit sodales mauris. Phasellus hendrerit ipsum quis auctor vehicula. Duis vel tellus erat. Duis venenatis metus quis quam cursus, a elementum mi tincidunt. Quisque sodales mattis pharetra. In egestas, lacus at vulputate condimentum, nisl nisl lacinia diam, in laoreet nulla mi eget purus. Aliquam varius eros ac elit euismod viverra eu ac tortor. Nam tempus magna magna, vitae suscipit ipsum convallis nec. Proin eu dolor at lectus venenatis congue sit amet id dui. Pellentesque iaculis mi nec elit maximus, non lacinia turpis convallis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque lobortis interdum massa, at luctus arcu. Praesent vehicula orci eget urna iaculis, a cursus felis malesuada.', 729),
('user6', 987, 'Worst Channel Ever', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultrices eu nunc vitae gravida. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur sagittis dapibus tellus vitae vulputate. Praesent placerat enim vitae lorem accumsan, vitae iaculis leo lacinia. Cras commodo velit non leo convallis bibendum. Nullam elementum nunc eu quam tempor vulputate. Quisque egestas sagittis dapibus. Mauris aliquet non leo a dignissim. Quisque vitae sem eget dui placerat cursus bibendum a leo. Vivamus blandit sodales mauris. Phasellus hendrerit ipsum quis auctor vehicula. Duis vel tellus erat. Duis venenatis metus quis quam cursus, a elementum mi tincidunt. Quisque sodales mattis pharetra. In egestas, lacus at vulputate condimentum, nisl nisl lacinia diam, in laoreet nulla mi eget purus. Aliquam varius eros ac elit euismod viverra eu ac tortor. Nam tempus magna magna, vitae suscipit ipsum convallis nec. Proin eu dolor at lectus venenatis congue sit amet id dui. Pellentesque iaculis mi nec elit maximus, non lacinia turpis convallis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque lobortis interdum massa, at luctus arcu. Praesent vehicula orci eget urna iaculis, a cursus felis malesuada.', 9283);

-- --------------------------------------------------------

--
-- Table structure for table `favourites`
--

CREATE TABLE IF NOT EXISTS `favourites` (
  `username` varchar(32) NOT NULL,
  `favourites` text NOT NULL,
  PRIMARY KEY (`username`),
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `favourites`
--

INSERT INTO `favourites` (`username`, `favourites`) VALUES
('user1', 'user2 user3 user4 '),
('user2', 'user3 user4 user5 '),
('user3', 'user4 user5 user6 '),
('user4', 'user5 user6 user1 '),
('user5', 'user6 user1 user2 '),
('user6', 'user1 user2 user3 ');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `salt` varchar(32) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`username`, `password`, `salt`) VALUES
('user1', '39fe441d5cb633c1da8ddbd8b73e424b', '54eed53fb6486'),
('user2', 'dd1d3d90380526c53233820957013fb9', '54eed556ed0cf'),
('user3', '55cdf3b6a7374e92e71874b847f21cf3', '54eed572d8333'),
('user4', '0f990d8e551b70d1b65048cae616eadd', '54eed583808ab'),
('user5', 'b4cffb84ab26a5c9030938e50ed9aa67', '54eed592940a2'),
('user6', '284d404df57ae3a75cb2c366afc83a54', '54eed5aa7bb71');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `channel`
--
ALTER TABLE `channel`
  ADD CONSTRAINT `channel_ibfk_1` FOREIGN KEY (`username`) REFERENCES `login` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `favourites`
--
ALTER TABLE `favourites`
  ADD CONSTRAINT `favourites_ibfk_1` FOREIGN KEY (`username`) REFERENCES `login` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
