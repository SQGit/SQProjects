-- phpMyAdmin SQL Dump
-- version 4.6.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 02, 2016 at 02:57 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ifund_data`
--

-- --------------------------------------------------------

--
-- Table structure for table `about_us`
--

CREATE TABLE `about_us` (
  `about_id` int(11) NOT NULL,
  `about_bg_image` varchar(45) DEFAULT NULL,
  `about_main_title` varchar(45) DEFAULT NULL,
  `about_sub_title` varchar(45) DEFAULT NULL,
  `about_section` varchar(45) DEFAULT NULL,
  `about_section_desc` text,
  `about_section1` varchar(45) DEFAULT NULL,
  `about_section_desc1` text,
  `about_section_image` varchar(45) DEFAULT NULL,
  `about_section_image1` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `about_us`
--

INSERT INTO `about_us` (`about_id`, `about_bg_image`, `about_main_title`, `about_sub_title`, `about_section`, `about_section_desc`, `about_section1`, `about_section_desc1`, `about_section_image`, `about_section_image1`) VALUES
(1, 'img-bg-21.jpg', 'About Our Company', 'More about us', 'ABOUT US IFUNDNETWORK', 'The ifundnetwork is global invite only private startup funding platform, based in Houston Texas, \nwith offices in Lagos Nigeria and Chennai India. Since 2012 the open crowd funding industry is \ngrowing steadily with over $36B+ startups projected to be funded online by 2016. The \nifundnetwork is relatively a new comer in the industry. We identified a gap to be filled in the \nindustry, and we are pioneering a new form of entrepreneurship funding platform. We don\'t just \nfund startups. We partner with them to make sure that they get it right from the very beginning.', 'MISSION STATEMENT', 'Our core focus on the getting the process right from the very beginning for every startups \ncompany and serious minded entrepreneurs; giving then full access to the right investors and full \naccess to the right connections that they need. We carefully select and interview our private \ninvestors and match them to the right startup of their interest within the ifundnetwork platform.', 'ab1.jpg', 'ab2.png');

-- --------------------------------------------------------

--
-- Table structure for table `competitors`
--

CREATE TABLE `competitors` (
  `competitor_id` int(11) NOT NULL,
  `project_id` int(11) DEFAULT NULL,
  `competitor_name` varchar(100) DEFAULT NULL,
  `competitor_logo` varchar(45) DEFAULT NULL,
  `competitor_intro` text,
  `competitor_status` int(2) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `competitors`
--

INSERT INTO `competitors` (`competitor_id`, `project_id`, `competitor_name`, `competitor_logo`, `competitor_intro`, `competitor_status`) VALUES
(1, 1, 'name', 'placeholder.jpg', NULL, 1),
(2, 1, 'name', 'placeholder.jpg', NULL, 1),
(3, 1, 'name', 'placeholder.jpg', NULL, 1),
(4, 1, 'name', 'placeholder.jpg', NULL, 1),
(5, 1, 'name', 'placeholder.jpg', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `contact_us`
--

CREATE TABLE `contact_us` (
  `contact_id` int(11) NOT NULL,
  `contact_address` varchar(200) DEFAULT NULL,
  `contact_phone` varchar(45) DEFAULT NULL,
  `contact_mail` varchar(45) DEFAULT NULL,
  `contact_bg_image` varchar(45) DEFAULT NULL,
  `contact_main_title` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `contact_us`
--

INSERT INTO `contact_us` (`contact_id`, `contact_address`, `contact_phone`, `contact_mail`, `contact_bg_image`, `contact_main_title`) VALUES
(1, 'Address has to be updated', '123-456-789', 'email@domain.com', 'partners.jpg', 'Contact Us');

-- --------------------------------------------------------

--
-- Table structure for table `country`
--

CREATE TABLE `country` (
  `country_id` int(12) NOT NULL,
  `country` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `country`
--

INSERT INTO `country` (`country_id`, `country`) VALUES
(4, 'Africa'),
(2, 'Nigeria'),
(3, 'North Americ'),
(9, 'US');

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

CREATE TABLE `documents` (
  `document_id` int(11) NOT NULL,
  `project_id` int(11) DEFAULT NULL,
  `document_group_name` varchar(200) DEFAULT NULL,
  `document_name` varchar(200) DEFAULT NULL,
  `document_path` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `documents`
--

INSERT INTO `documents` (`document_id`, `project_id`, `document_group_name`, `document_name`, `document_path`) VALUES
(1, 1, 'Due Dilligence Documents', 'Document Name', NULL),
(2, 1, 'Company Registration', 'Document Name', NULL),
(3, 1, 'Investment Term Sheet', 'Document Name', NULL),
(4, 1, 'Business Plan', 'Document Name', NULL),
(5, 1, 'Patent Application', 'Document Name', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `double_port`
--

CREATE TABLE `double_port` (
  `ID` int(3) NOT NULL,
  `min_amt` float NOT NULL,
  `max_amt` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `double_port`
--

INSERT INTO `double_port` (`ID`, `min_amt`, `max_amt`) VALUES
(1, 10000, 15000);

-- --------------------------------------------------------

--
-- Table structure for table `faq`
--

CREATE TABLE `faq` (
  `faq_id` int(11) NOT NULL,
  `faq_bg_image` varchar(45) DEFAULT NULL,
  `faq_main_title` varchar(45) DEFAULT NULL,
  `faq_sub_title` varchar(45) DEFAULT NULL,
  `faq_question` varchar(200) DEFAULT NULL,
  `faq_ans` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `faq`
--

INSERT INTO `faq` (`faq_id`, `faq_bg_image`, `faq_main_title`, `faq_sub_title`, `faq_question`, `faq_ans`) VALUES
(1, 'img-bg-21.jpg', 'Frequently Asked Questions', 'Your Question Our Answer', 'Question 1', '<p>The ifuundnetwork Private Investors Platform is strictly an invite only based funding network designed to fund all potential startup projects initiated by members of the network or an affiliated 3rd party startup partners. Although we intend to have funding network open to the public in the future. Until then all funding are by invitation only.<br/>The ifundnetwork private investment opportunities are only open to members who have completed the network private investor questionnaire.<br/>Offerings and Funding Documents<br/>Once your ifundnetwork account has been activated you will have instant access to our investment project currently been funded and your access code expires in 7 business days beginning from your first login date.<br/>Action Required on Your part within the 7 days</p> <ol> <li>Review all document related to the current funded project </li> <li>Speak to an ifundnetwork  representative </li> <li>Select your investment amount for the currently funded project/Categories</li> <li>Pay your investment funds to an assigned 3rd party collector.</li> <li>Receive a convertible note and other related documents </li> <li>Subscribe to receive an update on the milestones attained with regards to your investment.</li> </ol> <p>We are offering a 10% for $500, 000 worth of shares for our company to privlllaged friends and family members this is cover the cost of development, Server hosting, Product Advertisment  and Business data collection.<br/>Our aim is to have 500,000 to a Million subscribers a year from our intial launch date. giving us an enermous value before we begin to seek for any venture captialist investor and other Angel investors by so doing the value of any post investment portfolio will greatly be in our favor and on our terms.</p>'),
(2, NULL, NULL, NULL, 'Question 2', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.'),
(3, NULL, NULL, NULL, 'Question 3', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.'),
(4, NULL, NULL, NULL, 'Question 4', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.'),
(5, NULL, NULL, NULL, 'Question 5', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.');

-- --------------------------------------------------------

--
-- Table structure for table `full_port`
--

CREATE TABLE `full_port` (
  `ID` int(3) NOT NULL,
  `min_amt` float NOT NULL,
  `max_amt` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `full_port`
--

INSERT INTO `full_port` (`ID`, `min_amt`, `max_amt`) VALUES
(1, 16000, 50000);

-- --------------------------------------------------------

--
-- Table structure for table `funding`
--

CREATE TABLE `funding` (
  `fund_id` int(11) NOT NULL,
  `project_id` int(11) DEFAULT NULL,
  `funding_name` varchar(200) DEFAULT NULL,
  `funding_document_path` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `funding`
--

INSERT INTO `funding` (`fund_id`, `project_id`, `funding_name`, `funding_document_path`) VALUES
(1, 1, 'Angel/Syndicate Investors', NULL),
(2, 1, 'Fund Pool', NULL),
(3, 1, 'Investment Term Sheet', NULL),
(4, 1, 'Investement Privacy Policy', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `investment_criteria`
--

CREATE TABLE `investment_criteria` (
  `ID` int(3) NOT NULL,
  `points` text NOT NULL,
  `note` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `investment_criteria`
--

INSERT INTO `investment_criteria` (`ID`, `points`, `note`) VALUES
(1, '<li>Single Portfolio, or Bundle2 or Bundle 3</li>\n<li>Pay you commitment fund fees wish is 10% of your total investment amount.</li>\n<li>You Have 5 Business working days to complete the process after your first login date</li>\n<li>You have 14 days to complete the payment of the total value of your investment.</li>', '<li>Your 10% will be returned to you in full if you fail to make a full payment for the investment option you selected after the expiration of your 14days.</li>\r\n<li>For the Bundles your investment fund will be spread in equal percentage across the board.</li>');

-- --------------------------------------------------------

--
-- Table structure for table `partners`
--

CREATE TABLE `partners` (
  `partners_id` int(11) NOT NULL,
  `partner_name` varchar(45) DEFAULT NULL,
  `partner_logo` varchar(45) DEFAULT NULL,
  `partner_country` varchar(45) DEFAULT NULL,
  `partner_profession` varchar(45) DEFAULT NULL,
  `partner_desc` text,
  `partner_bg_image` varchar(45) DEFAULT NULL,
  `partner_main_title` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `partners`
--

INSERT INTO `partners` (`partners_id`, `partner_name`, `partner_logo`, `partner_country`, `partner_profession`, `partner_desc`, `partner_bg_image`, `partner_main_title`) VALUES
(1, 'oonsoft', 'oonsoft.jpg', 'US', 'Business Development', 'Oonsoft Is a technology consulting and innovation company, focused on business process improvement and business technology adaptation. Oonsoft Strategy combines best innovations that technology can provide with dynamic business model in solving business problems for startup, SME\'s, and State information services and portals. Oonsoft has its operating offices in Houston Texas, and in Lagos Nigeria.', 'partners.jpg', 'Our Partners'),
(2, 'samuel', 'samuel.jpg', 'North Americ', 'Financial Consultant', 'As a qualified Houston CPA firm and business advisor, you can be confident, knowing your finances investment are in good hands with Samuel Amoo & Associates. Accounting Reports SA & A does the  Accounting & Consulting does the bookkeeping for each project listed on ifundnetwork platform  by providing monthly/quarterly reports. All data is uploaded to the company\'s profile and is visible to every registered user.', NULL, NULL),
(3, 'oonsoft', 'oonsoft.jpg', 'Africa', 'Financial Consultant', 'As a qualified Houston CPA firm and business advisor, you can be confident, knowing your finances investment are in good hands with Samuel Amoo & Associates.Accounting Reports SA & A does the  Accounting & Consulting does the bookkeeping for each project listed on ifundnetwork platform  by providing monthly/quarterly reports. All data is uploaded to the company\'s profile and is visible to every registered user.', NULL, NULL),
(4, 'mobisoft', 'mobisoft.jpg', 'US', 'Technical Partners', 'Mobisoft works with businesses to enable them to gain flexibility, increase productivity, and reduce costs with end-to-end mobility services. Our enterprise mobility services cover all your mobility needs from strategic planning to device management to end-user application.ok', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `pic_table`
--

CREATE TABLE `pic_table` (
  `req_id` int(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `pic` varchar(45) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '0',
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `activation_date` datetime NOT NULL,
  `expiry_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pic_table`
--

INSERT INTO `pic_table` (`req_id`, `name`, `email`, `phone`, `country`, `pic`, `is_active`, `creation_date`, `activation_date`, `expiry_date`) VALUES
(57, 'siva raj', 'siva@sqindia.net', '9790280707', 'us', '5746c8a285d06', 1, '2016-05-26 07:43:30', '2016-05-26 15:27:54', '2016-06-10');

-- --------------------------------------------------------

--
-- Table structure for table `project_list`
--

CREATE TABLE `project_list` (
  `project_id` int(11) NOT NULL,
  `project_name` varchar(45) NOT NULL,
  `project_logo` varchar(45) DEFAULT NULL,
  `pjt_payment_logo` varchar(45) NOT NULL,
  `project_category` varchar(200) DEFAULT NULL,
  `project_fund_goal` varchar(11) DEFAULT NULL,
  `project_amt_invested` varchar(12) DEFAULT NULL,
  `project_fund_pool` int(11) DEFAULT NULL,
  `project_angels` int(11) DEFAULT NULL,
  `project_fund_period_from` date DEFAULT NULL,
  `project_fund_period_to` date DEFAULT NULL,
  `project_fund_days_left` varchar(6) DEFAULT NULL,
  `project_video` varchar(45) DEFAULT NULL,
  `project_desc_short` text,
  `project_vision` text,
  `project_problem` text,
  `project_solution_video` varchar(45) DEFAULT NULL,
  `project_solution_text` text,
  `project_other_details` text,
  `project_advantage` text,
  `investment_fund_pool` text NOT NULL,
  `doc_receive` text NOT NULL,
  `project_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('active','draft','disabled','') NOT NULL DEFAULT 'draft',
  `funding_required` enum('0','1') NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `project_list`
--

INSERT INTO `project_list` (`project_id`, `project_name`, `project_logo`, `pjt_payment_logo`, `project_category`, `project_fund_goal`, `project_amt_invested`, `project_fund_pool`, `project_angels`, `project_fund_period_from`, `project_fund_period_to`, `project_fund_days_left`, `project_video`, `project_desc_short`, `project_vision`, `project_problem`, `project_solution_video`, `project_solution_text`, `project_other_details`, `project_advantage`, `investment_fund_pool`, `doc_receive`, `project_added`, `status`, `funding_required`) VALUES
(1, 'ifindcard', 'img-other-3.png', '1.png', 'Business Networking Platform', '1,50,000', '100,000 ', 4, 0, '2016-05-03', '2016-05-31', '28', 'https://www.youtube.com/embed/hTQWdr-ls78', 'Ifindcard is a mobile and web based social and business application platform. that allows its users the opportunity to engage in social and business networking.<br>', 'Ifindcard is a mobile and web based social and business application platform. that allows its users the opportunity to engage in social and business networking. The application provides a unique platform in which all its users have the ability to access all their business connections, services, schedules, and much more. Ifindcard is quite unique in the platform provided as users will be able to engage in ecommerce, socialize, find local services, schedule appointments, find events, store coupons, and much more all from scanning a business card. More importantly, Ifindcard offers a distinctive opportunity for users to earn a significant residual income simply by introducing the application to businesses they are already patronizing.', '<p>With the invention of the smartphone practically every aspect of life has been affected by it. The smartphone created instant solution to daily problems in life by making it possible for anyone anywhere in the world to get the same result based on these three principle. </p>\n<ol style="list-style-type:lower-roman;">\n<li>The solution must be dynamic enough to meet the need.</li>\n<li>The solution must be easy to use and adaptable.</li>\n<li> The solution must cut across international boundaries.</li>\n</ol>\n<p>companies like UBER, Snapchat, Facebook, Pinterest, Twitter, and Instagram all created a multi-billion-dollar technology innovative industries. They are a proof that all human shares the same need and anyone with the right solution can take advantage of it The distinguishing factor that made these companies great was that they all picked up a single area of need and they offered a total solution to that that area of need and the entire world is becoming addicted to them simply because they narrowed down to something the society needs and they placed it in the hands of everyone at the click of a "Button" and today the rest is history</p>\n<p>Ifindcard is the most innovative solution ever created to the problem facing business all over the world who want to have their presence felt in the mobile Advertisement space in the smartphone world. </p>\n<p>We Created a new way of initiating, processing and completion of any business transaction by simple using a "Digital business Card", and a "business phone number". Irrespective of what kind of business our client are engaged in. all they need in order to be visible to their clients and customers is their Business Phone Number and a special easy to capture unique code. </p>\n<p>With our one of its kind business platform SME\'s who can\'t afford a website can finally have the opportunity of having an online presence just with their phone number alone. Customers simple need their already existing business phone number to view all the product & services that they offer and can directly place an order from their phone via our platform.</p>', 'https://www.youtube.com/embed/jZvXYARcDgY', NULL, '<h4>End User Engagement for who are ifindcard customers </h4> <ul> <li>Chatting Feature<br></li> <li>Appointment Manager </li> <li>Reviews &amp; Ratings</li> <li>Share, Store and geptag business cards </li> </ul> <h4>Marketing Strategy </h4> <ul> <li>Visiblity </li> <li>Social Networking Model </li> <li>Event management</li> <li>3rd Party Busines Card Template Design </li> </ul> <h4>Merchants/ Business Professional Engagments</h4> <ul> <li>Create Business Cards <ul> <li>Mobile Digital Version</li> <li>Physical Version</li> </ul> </li> <li>One Platform Multiple Business Card</li><li><br></li> <li>Create A facebook like business Ad Page <ul> <li>Link One or More Business Card To Your Business Ad Page Increase Sales/Traffic</li> <li>Create A facebook like business Ad Page</li> <li>Link Business Associate/Employee business card to Business Ad page</li> <li>Aggregate visiblity of related cards to one another </li> </ul> </li> </ul> <h4>ifindcard Business Manager</h4><br> <h4>ifindcard 2.0 </h4> <ul> <li>Ads space Sales </li> <li>Focus On Corporate Accounts</li> <li>3rd party Services </li> </ul>', '<h5>Business Visibilty </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Use of Business Card To Advertise  Business Services &amp; Product On Mobile Devices </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Ifindcard Business Manager </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Use Of Clamtag for Business Networking &amp; Advertisement </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Aggregation of  Related  Business Card  </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Create &amp; Host Multiple Business Cards In One Platfrom </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Share Business Card Via Recievers Phone # or Via Clamtag ID</h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Hire Proffessional  Directly from  App </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Post Proffesional  Resume On User Page</h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>B2C, C2C Chatting, Appointment Schdeuling, Event Manager </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>', '<p>We are offering a 10% for $500, 000 worth of shares for our company to privlllaged friends and family members this is cover the cost of development, Server hosting, Product Advertisment  and Business data collection.<br/>Our aim is to have 500,000 to a Million subscribers a year from our intial launch date. giving us an enermous value before we begin to seek for any venture captialist investor and other Angel investors by so doing the value of any post investment portfolio will greatly be in our favor and on our terms.</p>', '<ol> 	<li>Convertiable Note Certificate</li> 	<li>Regular Email Update </li> 	<li>Quarterly Financial Report From Certified Financial Consulting Partner </li> 	<li>An Investment Term Sheet </li> 	</ol>', '2016-04-26 08:12:14', 'active', '1'),
(2, 'eazy tie', 'img-other-32.png', '2.png', 'Men\'s Fashion Accessories', '2,50,000', '200,000', 2, 2, '2016-05-03', '2016-06-30', '25', 'https://www.youtube.com/embed/hTQWdr-ls78', 'Ifindcard is a mobile and web based social and business application platform. that allows its users the opportunity to engage in social and business networking.', 'Ifindcard is a mobile and web based social and business application platform. that allows its users the opportunity to engage in social and business networking. The application provides a unique platform in which all its users have the ability to access all their business connections, services, schedules, and much more. Ifindcard is quite unique in the platform provided as users will be able to engage in ecommerce, socialize, find local services, schedule appointments, find events, store coupons, and much more all from scanning a business card. More importantly, Ifindcard offers a distinctive opportunity for users to earn a significant residual income simply by introducing the application to businesses they are already patronizing.', '<p>With the invention of the smartphone practically every aspect of life has been affected by it. The smartphone created instant solution to daily problems in life by making it possible for anyone anywhere in the world to get the same result based on these three principle. </p>\n<ol style="list-style-type:lower-roman;">\n<li>The solution must be dynamic enough to meet the need.</li>\n<li>The solution must be easy to use and adaptable.</li>\n<li> The solution must cut across international boundaries.</li>\n</ol>\n<p>companies like UBER, Snapchat, Facebook, Pinterest, Twitter, and Instagram all created a multi-billion-dollar technology innovative industries. They are a proof that all human shares the same need and anyone with the right solution can take advantage of it The distinguishing factor that made these companies great was that they all picked up a single area of need and they offered a total solution to that that area of need and the entire world is becoming addicted to them simply because they narrowed down to something the society needs and they placed it in the hands of everyone at the click of a "Button" and today the rest is history</p>\n<p>Ifindcard is the most innovative solution ever created to the problem facing business all over the world who want to have their presence felt in the mobile Advertisement space in the smartphone world. </p>\n<p>We Created a new way of initiating, processing and completion of any business transaction by simple using a "Digital business Card", and a "business phone number". Irrespective of what kind of business our client are engaged in. all they need in order to be visible to their clients and customers is their Business Phone Number and a special easy to capture unique code. </p>\n<p>With our one of its kind business platform SME\'s who can\'t afford a website can finally have the opportunity of having an online presence just with their phone number alone. Customers simple need their already existing business phone number to view all the product & services that they offer and can directly place an order from their phone via our platform.</p>', 'https://www.youtube.com/embed/jZvXYARcDgY', NULL, '<h4 class="mt30 font-blue">End User Engagement for who are ifindcard customers </h4> <ul class="pl20 li-no-stl"> <li>Chatting Feature</li> <li>Appointment Manager </li> <li>Reviews &amp; Ratings</li> <li>Share, Store and geptag business cards </li> </ul> <h4 class="mt30 font-blue">Marketing Strategy </h4> <ul class="pl20 li-no-stl"> <li>Visiblity </li> <li>Social Networking Model </li> <li>Event management</li> <li>3rd Party Busines Card Template Design </li> </ul> <h4 class="mt30 font-blue">Merchants/ Business Professional Engagments</h4> <ul class="pl20 li-no-stl"> <li>Create Business Cards <ul class="pl40 li-no-stl"> <li>Mobile Digital Version</li> <li>Physical Version</li> </ul> </li> <li>One Platform Multiple Business Card</li> <li>Create A facebook like business Ad Page <ul class="pl20 li-no-stl"> <li>Link One or More Business Card To Your Business Ad Page Increase Sales/Traffic</li> <li>Create A facebook like business Ad Page</li> <li>Link Business Associate/Employee business card to Business Ad page</li> <li>Aggregate visiblity of related cards to one another </li> </ul> </li> </ul> <h4 class="mt30 pb40 font-blue">ifindcard Business Manager </h4> <h4 class="mt30 font-blue">ifindcard 2.0 </h4> <ul class="pl30 li-no-stl"> <li>Ads space Sales </li> <li>Focus On Corporate Accounts</li> <li>3rd party Services </li> </ul>', '<h5>Business Visibilty </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Use of Business Card To Advertise  Business Services &amp; Product On Mobile Devices </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Ifindcard Business Manager </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Use Of Clamtag for Business Networking &amp; Advertisement </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Aggregation of  Related  Business Card  </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Create &amp; Host Multiple Business Cards In One Platfrom </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Share Business Card Via Recievers Phone # or Via Clamtag ID</h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Hire Proffessional  Directly from  App </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Post Proffesional  Resume On User Page</h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>B2C, C2C Chatting, Appointment Schdeuling, Event Manager </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>', '<p>We are offering a 10% for $500, 000 worth of shares for our company to privlllaged friends and family members this is cover the cost of development, Server hosting, Product Advertisment  and Business data collection.<br/>Our aim is to have 500,000 to a Million subscribers a year from our intial launch date. giving us an enermous value before we begin to seek for any venture captialist investor and other Angel investors by so doing the value of any post investment portfolio will greatly be in our favor and on our terms.</p>', '<ol> 	<li>Convertiable Note Certificate</li> 	<li>Regular Email Update </li> 	<li>Quarterly Financial Report From Certified Financial Consulting Partner </li> 	<li>An Investment Term Sheet </li> 	</ol>', '2016-04-26 16:46:07', 'active', '1'),
(3, 'oonbux', 'img-other-22.png', '3.png', 'Business Logistics Platform', '2,00,000', '150,000', 3, 1, '2016-04-04', '2016-04-30', '26', 'https://www.youtube.com/embed/hTQWdr-ls78', 'Ifindcard is a mobile and web based social and business application platform. that allows its users the opportunity to engage in social and business networking.', 'Ifindcard is a mobile and web based social and business application platform. that allows its users the opportunity to engage in social and business networking. The application provides a unique platform in which all its users have the ability to access all their business connections, services, schedules, and much more. Ifindcard is quite unique in the platform provided as users will be able to engage in ecommerce, socialize, find local services, schedule appointments, find events, store coupons, and much more all from scanning a business card. More importantly, Ifindcard offers a distinctive opportunity for users to earn a significant residual income simply by introducing the application to businesses they are already patronizing.', '<p>With the invention of the smartphone practically every aspect of life has been affected by it. The smartphone created instant solution to daily problems in life by making it possible for anyone anywhere in the world to get the same result based on these three principle. </p>\n<ol style="list-style-type:lower-roman;">\n<li>The solution must be dynamic enough to meet the need.</li>\n<li>The solution must be easy to use and adaptable.</li>\n<li> The solution must cut across international boundaries.</li>\n</ol>\n<p>companies like UBER, Snapchat, Facebook, Pinterest, Twitter, and Instagram all created a multi-billion-dollar technology innovative industries. They are a proof that all human shares the same need and anyone with the right solution can take advantage of it The distinguishing factor that made these companies great was that they all picked up a single area of need and they offered a total solution to that that area of need and the entire world is becoming addicted to them simply because they narrowed down to something the society needs and they placed it in the hands of everyone at the click of a "Button" and today the rest is history</p>\n<p>Ifindcard is the most innovative solution ever created to the problem facing business all over the world who want to have their presence felt in the mobile Advertisement space in the smartphone world. </p>\n<p>We Created a new way of initiating, processing and completion of any business transaction by simple using a "Digital business Card", and a "business phone number". Irrespective of what kind of business our client are engaged in. all they need in order to be visible to their clients and customers is their Business Phone Number and a special easy to capture unique code. </p>\n<p>With our one of its kind business platform SME\'s who can\'t afford a website can finally have the opportunity of having an online presence just with their phone number alone. Customers simple need their already existing business phone number to view all the product & services that they offer and can directly place an order from their phone via our platform.</p>', 'https://www.youtube.com/embed/jZvXYARcDgY', NULL, '<h4 class="mt30 font-blue">End User Engagement for who are ifindcard customers </h4> <ul class="pl20 li-no-stl"> <li>Chatting Feature</li> <li>Appointment Manager </li> <li>Reviews &amp; Ratings</li> <li>Share, Store and geptag business cards </li> </ul> <h4 class="mt30 font-blue">Marketing Strategy </h4> <ul class="pl20 li-no-stl"> <li>Visiblity </li> <li>Social Networking Model </li> <li>Event management</li> <li>3rd Party Busines Card Template Design </li> </ul> <h4 class="mt30 font-blue">Merchants/ Business Professional Engagments</h4> <ul class="pl20 li-no-stl"> <li>Create Business Cards <ul class="pl40 li-no-stl"> <li>Mobile Digital Version</li> <li>Physical Version</li> </ul> </li> <li>One Platform Multiple Business Card</li> <li>Create A facebook like business Ad Page <ul class="pl20 li-no-stl"> <li>Link One or More Business Card To Your Business Ad Page Increase Sales/Traffic</li> <li>Create A facebook like business Ad Page</li> <li>Link Business Associate/Employee business card to Business Ad page</li> <li>Aggregate visiblity of related cards to one another </li> </ul> </li> </ul> <h4 class="mt30 pb40 font-blue">ifindcard Business Manager </h4> <h4 class="mt30 font-blue">ifindcard 2.0 </h4> <ul class="pl30 li-no-stl"> <li>Ads space Sales </li> <li>Focus On Corporate Accounts</li> <li>3rd party Services </li> </ul>', '<h5>Business Visibilty </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Use of Business Card To Advertise  Business Services &amp; Product On Mobile Devices </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Ifindcard Business Manager </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Use Of Clamtag for Business Networking &amp; Advertisement </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Aggregation of  Related  Business Card  </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Create &amp; Host Multiple Business Cards In One Platfrom </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Share Business Card Via Recievers Phone # or Via Clamtag ID</h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Hire Proffessional  Directly from  App </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>Post Proffesional  Resume On User Page</h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>  <h5>B2C, C2C Chatting, Appointment Schdeuling, Event Manager </h5> <p>Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.</p>', '<p>We are offering a 10% for $500, 000 worth of shares for our company to privlllaged friends and family members this is cover the cost of development, Server hosting, Product Advertisment  and Business data collection.<br/>Our aim is to have 500,000 to a Million subscribers a year from our intial launch date. giving us an enermous value before we begin to seek for any venture captialist investor and other Angel investors by so doing the value of any post investment portfolio will greatly be in our favor and on our terms.</p>', '<ol> 	<li>Convertiable Note Certificate</li> 	<li>Regular Email Update </li> 	<li>Quarterly Financial Report From Certified Financial Consulting Partner </li> 	<li>An Investment Term Sheet </li> 	</ol>', '2016-04-26 16:46:07', 'active', '1');

-- --------------------------------------------------------

--
-- Table structure for table `reg_user`
--

CREATE TABLE `reg_user` (
  `pic` varchar(16) NOT NULL,
  `name` varchar(45) NOT NULL,
  `user_id` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `baddress` varchar(200) NOT NULL,
  `saddress` varchar(200) NOT NULL,
  `bcity` varchar(45) NOT NULL,
  `scity` varchar(45) NOT NULL,
  `bzipcode` varchar(45) NOT NULL,
  `szipcode` varchar(6) NOT NULL,
  `country` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `access_level` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `reg_user`
--

INSERT INTO `reg_user` (`pic`, `name`, `user_id`, `password`, `email`, `baddress`, `saddress`, `bcity`, `scity`, `bzipcode`, `szipcode`, `country`, `phone`, `access_level`) VALUES
('9632658947', 'admin', 'admin', 'passs', 'admin@ifund.com', '', '', '', '', '', '', 'india', '', 1),
('5746c8a285d06', 'siva raj', 'sivaraj', 'password', 'siva@sqindia.net', 'address1,address2', 'address1,address2', 'city', 'scity', '123456', '123456', 'US', '9790280707', 0);

-- --------------------------------------------------------

--
-- Table structure for table `single_port`
--

CREATE TABLE `single_port` (
  `ID` int(3) NOT NULL,
  `amount` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `single_port`
--

INSERT INTO `single_port` (`ID`, `amount`) VALUES
(1, 5000),
(2, 6000),
(3, 7000),
(4, 8000),
(5, 9000);

-- --------------------------------------------------------

--
-- Table structure for table `team_members`
--

CREATE TABLE `team_members` (
  `team_member_id` int(6) NOT NULL,
  `project_id` int(6) NOT NULL,
  `team_member_name` varchar(45) NOT NULL,
  `team_member_profession` varchar(45) NOT NULL,
  `team_member_intro` text NOT NULL,
  `team_member_photo` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `team_members`
--

INSERT INTO `team_members` (`team_member_id`, `project_id`, `team_member_name`, `team_member_profession`, `team_member_intro`, `team_member_photo`) VALUES
(1, 1, 'Emeka Adagbo', 'Co-Founder CEO', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(2, 1, 'Denis Ugwa ', 'Co-Founder ', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(3, 1, 'Segun Faphunda ', 'Operations', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(4, 1, 'GOPI', 'CTO', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(5, 1, 'Ugochi Adagbo', 'Marketing', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(6, 1, 'Emeka Adagbo', 'Co-Founder CEO', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(7, 2, 'Emeka Adagbo ', 'Co-Founder CEO', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(8, 2, 'Denis Ugwa ', 'Co-Founder ', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(9, 2, 'Segun Faphunda ', 'Operations', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(10, 2, 'Ugochi Adagbo', 'Marketing', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(11, 2, 'Ugochi Adagbo', 'Marketing', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(12, 2, 'Emeka Adagbo', 'Co-Founder CEO', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(13, 3, 'Emeka Adagbo ', 'Co-Founder CEO', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(14, 3, 'Denis Ugwa ', 'Co-Founder ', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(15, 3, 'Segun Faphunda ', 'Operations', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(16, 3, 'Ugochi Adagbo', 'Marketing', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg'),
(17, 3, 'Emeka Adagboa', 'Co-Founder CEO', 'Lorem ipsum dolor sit amet, sapien etiam, nunc amet dolor ac odio mauris justo. Luctus arcu, urna praesent at id quisque ac. Arcu es massa vestibulum malesuada, integer vivamus elit eu mauris eus, cum eros quis aliquam wisi. Nulla wisi laoreet suspendisse integer vivamus elit eu mauris hendrerit facilisi, mi mattis pariatur aliquam pharetra eget.', 'dummy.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `team_page`
--

CREATE TABLE `team_page` (
  `team_page_id` int(6) NOT NULL,
  `team_short_intro` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `team_page`
--

INSERT INTO `team_page` (`team_page_id`, `team_short_intro`) VALUES
(1, '<p>We are very passionate about what we are doing, it had not been an easy ride so far, one of our mentors once said "if you want to go fast in life go alone, but if you desire to go far and end well go together". Our dedication to success is what makes us tick. </p>');

-- --------------------------------------------------------

--
-- Table structure for table `testing`
--

CREATE TABLE `testing` (
  `testing_id` int(11) NOT NULL,
  `project_id` int(11) DEFAULT NULL,
  `testing_name` varchar(200) DEFAULT NULL,
  `testing_name_desc` varchar(200) DEFAULT NULL,
  `testing_status` varchar(45) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `testing`
--

INSERT INTO `testing` (`testing_id`, `project_id`, `testing_name`, `testing_name_desc`, `testing_status`) VALUES
(1, 1, 'Beta Testing', 'June 2016 ', '1'),
(2, 1, 'Launching', 'August 2016', '1'),
(3, 2, 'Next Funding Rounds', 'August 2016 ', '1'),
(4, 1, 'Deployment of Phase 2', 'January 2017', '1'),
(5, 2, 'tn12', 'may2016', '1');

-- --------------------------------------------------------

--
-- Table structure for table `traction`
--

CREATE TABLE `traction` (
  `traction_id` int(11) NOT NULL,
  `project_id` int(11) DEFAULT NULL,
  `traction_name` varchar(200) DEFAULT NULL,
  `traction_name_desc` varchar(200) DEFAULT NULL,
  `traction_status` varchar(45) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `traction`
--

INSERT INTO `traction` (`traction_id`, `project_id`, `traction_name`, `traction_name_desc`, `traction_status`) VALUES
(1, 1, 'Application Design / Prototyping', 'May 2016', '1'),
(2, 1, 'Patent Application', 'December 2015', '1'),
(3, 1, 'Patent Application', 'December 2015', '1'),
(4, 1, 'Application Development', 'Funding Needed', '1');

-- --------------------------------------------------------

--
-- Table structure for table `transection_tbl`
--

CREATE TABLE `transection_tbl` (
  `TID` int(11) NOT NULL,
  `investor_id` varchar(50) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `payer_email` varchar(150) NOT NULL,
  `first_name` varchar(150) NOT NULL,
  `last_name` varchar(150) NOT NULL,
  `amount` float NOT NULL,
  `currency` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `txn_id` varchar(100) NOT NULL,
  `txn_type` varchar(100) NOT NULL,
  `payer_id` varchar(50) NOT NULL,
  `payment_status` varchar(100) NOT NULL,
  `payment_type` varchar(100) NOT NULL,
  `payment_metod` varchar(100) NOT NULL,
  `create_date` datetime NOT NULL,
  `payment_date` datetime NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transection_tbl`
--

INSERT INTO `transection_tbl` (`TID`, `investor_id`, `item_name`, `payer_email`, `first_name`, `last_name`, `amount`, `currency`, `country`, `txn_id`, `txn_type`, `payer_id`, `payment_status`, `payment_type`, `payment_metod`, `create_date`, `payment_date`, `note`) VALUES
(123123, '9632658947', 'fdg', 'gfdg', 'gfdgdf', 'gdfg', 413546, 'usd', 'us', '132fd1gdf', 'gfdg', '1d3f2g1dfgfd', 'gfdg', 'gfdg', 'gfdgfd', '2016-06-01 00:00:00', '2016-06-01 00:00:00', 'amount returned');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `about_us`
--
ALTER TABLE `about_us`
  ADD PRIMARY KEY (`about_id`);

--
-- Indexes for table `competitors`
--
ALTER TABLE `competitors`
  ADD PRIMARY KEY (`competitor_id`),
  ADD KEY `competitor_fk_idx` (`project_id`);

--
-- Indexes for table `contact_us`
--
ALTER TABLE `contact_us`
  ADD PRIMARY KEY (`contact_id`);

--
-- Indexes for table `country`
--
ALTER TABLE `country`
  ADD PRIMARY KEY (`country_id`),
  ADD KEY `country` (`country`);

--
-- Indexes for table `documents`
--
ALTER TABLE `documents`
  ADD PRIMARY KEY (`document_id`),
  ADD KEY `document_fk_idx` (`project_id`);

--
-- Indexes for table `double_port`
--
ALTER TABLE `double_port`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `faq`
--
ALTER TABLE `faq`
  ADD PRIMARY KEY (`faq_id`);

--
-- Indexes for table `full_port`
--
ALTER TABLE `full_port`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `funding`
--
ALTER TABLE `funding`
  ADD PRIMARY KEY (`fund_id`),
  ADD KEY `FOREIGNKEY_idx` (`project_id`);

--
-- Indexes for table `investment_criteria`
--
ALTER TABLE `investment_criteria`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `partners`
--
ALTER TABLE `partners`
  ADD PRIMARY KEY (`partners_id`);

--
-- Indexes for table `pic_table`
--
ALTER TABLE `pic_table`
  ADD PRIMARY KEY (`req_id`);

--
-- Indexes for table `project_list`
--
ALTER TABLE `project_list`
  ADD PRIMARY KEY (`project_id`),
  ADD UNIQUE KEY `project_id_UNIQUE` (`project_id`);

--
-- Indexes for table `reg_user`
--
ALTER TABLE `reg_user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `pic_UNIQUE` (`pic`),
  ADD UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`);

--
-- Indexes for table `single_port`
--
ALTER TABLE `single_port`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `team_members`
--
ALTER TABLE `team_members`
  ADD PRIMARY KEY (`team_member_id`);

--
-- Indexes for table `team_page`
--
ALTER TABLE `team_page`
  ADD PRIMARY KEY (`team_page_id`);

--
-- Indexes for table `testing`
--
ALTER TABLE `testing`
  ADD PRIMARY KEY (`testing_id`),
  ADD KEY `FOREIGNKEY_idx` (`project_id`),
  ADD KEY `testing_fk_idx` (`project_id`);

--
-- Indexes for table `traction`
--
ALTER TABLE `traction`
  ADD PRIMARY KEY (`traction_id`),
  ADD KEY `traction_fk_idx` (`project_id`);

--
-- Indexes for table `transection_tbl`
--
ALTER TABLE `transection_tbl`
  ADD PRIMARY KEY (`TID`),
  ADD KEY `inv_id_fk_idx` (`investor_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `about_us`
--
ALTER TABLE `about_us`
  MODIFY `about_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `competitors`
--
ALTER TABLE `competitors`
  MODIFY `competitor_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `contact_us`
--
ALTER TABLE `contact_us`
  MODIFY `contact_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `country`
--
ALTER TABLE `country`
  MODIFY `country_id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `documents`
--
ALTER TABLE `documents`
  MODIFY `document_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `double_port`
--
ALTER TABLE `double_port`
  MODIFY `ID` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `faq`
--
ALTER TABLE `faq`
  MODIFY `faq_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `full_port`
--
ALTER TABLE `full_port`
  MODIFY `ID` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `funding`
--
ALTER TABLE `funding`
  MODIFY `fund_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `investment_criteria`
--
ALTER TABLE `investment_criteria`
  MODIFY `ID` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `partners`
--
ALTER TABLE `partners`
  MODIFY `partners_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `pic_table`
--
ALTER TABLE `pic_table`
  MODIFY `req_id` int(45) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;
--
-- AUTO_INCREMENT for table `project_list`
--
ALTER TABLE `project_list`
  MODIFY `project_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `single_port`
--
ALTER TABLE `single_port`
  MODIFY `ID` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `team_members`
--
ALTER TABLE `team_members`
  MODIFY `team_member_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `team_page`
--
ALTER TABLE `team_page`
  MODIFY `team_page_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `testing`
--
ALTER TABLE `testing`
  MODIFY `testing_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `traction`
--
ALTER TABLE `traction`
  MODIFY `traction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `transection_tbl`
--
ALTER TABLE `transection_tbl`
  MODIFY `TID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123124;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `competitors`
--
ALTER TABLE `competitors`
  ADD CONSTRAINT `competitor_fk` FOREIGN KEY (`project_id`) REFERENCES `project_list` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `documents`
--
ALTER TABLE `documents`
  ADD CONSTRAINT `document_fk` FOREIGN KEY (`project_id`) REFERENCES `project_list` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `funding`
--
ALTER TABLE `funding`
  ADD CONSTRAINT `funding_fk` FOREIGN KEY (`project_id`) REFERENCES `project_list` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `testing`
--
ALTER TABLE `testing`
  ADD CONSTRAINT `testing_fk` FOREIGN KEY (`project_id`) REFERENCES `project_list` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `traction`
--
ALTER TABLE `traction`
  ADD CONSTRAINT `traction_fk` FOREIGN KEY (`project_id`) REFERENCES `project_list` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `transection_tbl`
--
ALTER TABLE `transection_tbl`
  ADD CONSTRAINT `inv_id_fk` FOREIGN KEY (`investor_id`) REFERENCES `reg_user` (`pic`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
