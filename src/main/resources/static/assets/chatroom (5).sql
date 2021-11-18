-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 16, 2021 lúc 05:44 PM
-- Phiên bản máy phục vụ: 10.4.21-MariaDB
-- Phiên bản PHP: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `chatroom`
--

DELIMITER $$
--
-- Thủ tục
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `count_message_by_month_and_year` (IN `month` INT(2), IN `year` INT(5))  SELECT COUNT(id) as quantity FROM message WHERE month(time) = month and year(time) = year$$

    DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `attach`
--

CREATE TABLE `attach` (
                          `id` int(11) NOT NULL,
                          `messageId` varchar(50) DEFAULT NULL,
                          `filename` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `attach`
--

INSERT INTO `attach` (`id`, `messageId`, `filename`) VALUES
(184, '6d22daba-fd7a-4989-9f56-c4ee5f30d399', '72a4e33e-d3e0-4006-a170-43b11f3c1bfa.jpg'),
(185, 'fd3a5b06-8ef3-4bbb-b3e9-eaa8f04a6e06', 'b4e6e1ed-23a4-4373-9370-e5a54b6631ad.jpg'),
(186, 'fd3a5b06-8ef3-4bbb-b3e9-eaa8f04a6e06', '8001bb44-1d20-4735-9e38-acce488928c2.jpg'),
(187, 'fd3a5b06-8ef3-4bbb-b3e9-eaa8f04a6e06', '2513751d-18a7-40d3-ab75-57521905fd97.jpg'),
(188, 'eb191a79-8a5b-405b-8196-9ba596a18f8f', '90ef8bc9-39d8-44d8-8e67-113ba20275b0.jpg'),
(189, 'eb191a79-8a5b-405b-8196-9ba596a18f8f', '49586bec-5bcd-499e-afe0-79fa39330d4c.jpg'),
(190, 'eb191a79-8a5b-405b-8196-9ba596a18f8f', '6386acb4-5e9f-4a69-89df-8649b4d0b092.jpg'),
(191, 'd61aeca2-4b1d-4972-9f40-b73bf80ac63a', '1ff938d4-668b-4244-bb71-d6a580a2886b.png'),
(192, '567ff970-f9be-436a-bf57-64b405d80183', '63a636f2-b414-4e63-95bc-69357af21e4c.png'),
(193, 'b3bc206f-9183-4c1e-8096-bb93da5adae1', '1d945dfd-b1d8-47ed-96e0-d10a3caedae1.jpg'),
(194, 'b3bc206f-9183-4c1e-8096-bb93da5adae1', '4197f9de-5881-4e6d-bf46-af9315bba1e7.jpg'),
(195, 'db8c04a4-24b0-4369-a9e3-e15d517f9647', '213aaf1e-8cf5-4566-b8b3-eadc91c2a6bf.gif'),
(196, 'db8c04a4-24b0-4369-a9e3-e15d517f9647', '874da73f-f29e-4143-bb4d-cb862e337a76.gif'),
(197, 'b691f3be-d944-45d7-822d-8c1c4d693c73', '4aec1c8f-f7cf-47b4-90ca-767706d44293.gif'),
(198, '3da67600-c363-4749-8175-a1f681a083be', '52ba28b1-8eb4-40be-8161-801e61a7c1ab.gif'),
(199, '7d69905c-e321-4216-8420-f2c1479f0af0', '071ad469-f8ca-4240-8b13-6773f9a92cde.gif'),
(200, '7d69905c-e321-4216-8420-f2c1479f0af0', '81876c2b-ae9a-4a0b-bc6b-6a353c19ccc6.jpg'),
(201, '6eb29d48-44b6-4a3a-9e54-ed5b2ff18133', 'e2c5015c-e71a-499d-94c5-cd4ed52f65d4.jpg'),
(202, '6eb29d48-44b6-4a3a-9e54-ed5b2ff18133', '0c6f355b-6bab-4ec4-93b3-3e807fc0d539.jpg'),
(203, '758ed9b1-6886-4fa0-bc14-4f740921b199', 'b86d26f1-d7a7-4136-8bf2-260977b2049d.jfif'),
(204, '758ed9b1-6886-4fa0-bc14-4f740921b199', '5ebf5214-f212-4767-b3b6-52450d676ce4.jpg'),
(205, '758ed9b1-6886-4fa0-bc14-4f740921b199', '22b1c2fc-9297-421a-b95e-b8bea04bc69e.gif'),
(206, 'ca6fca21-83b2-41e5-8aca-2f41686a1d74', '7258ca26-48b3-4e04-8d53-1904306ac0ad.jpg'),
(207, '48635c75-d15b-4f18-b296-fd625b9135fa', '516534e9-d46a-440f-96ed-f4f5d1fd3301.jpg'),
(209, '48635c75-d15b-4f18-b296-fd625b9135fa', 'aebde3d2-e7ca-4cdb-b9ed-fc15daee81a4.jpg'),
(210, '6d9f986a-a371-4ef4-be69-6cc862315c25', 'f6a81edc-e754-4630-8ebb-e4604b1dbf5d.gif'),
(211, '6d9f986a-a371-4ef4-be69-6cc862315c25', '6c208b08-934e-4662-82be-2301c112c577.gif'),
(212, '51e59b46-147f-472a-9cfb-52a5e1532bac', '9a700d3d-da9c-4a52-b2cc-8ea6e39f46e2.jpg'),
(213, '51e59b46-147f-472a-9cfb-52a5e1532bac', '622099f8-b770-456e-aaf7-730de5d2badd.jpg'),
(214, '0ccfc26d-3332-4930-890d-aa34564f855a', '1f964e7c-6568-47b6-a7de-f6c5a98ac2fd.jfif'),
(215, '0ccfc26d-3332-4930-890d-aa34564f855a', '641d06d0-a8f3-4513-bb79-79211c3e3f34.jpg'),
(216, '0898a6b4-1fb9-4545-966e-dcc4965f5c2f', '9e1d0891-0966-4a07-b2d7-0863f6398904.jpg'),
(217, '0898a6b4-1fb9-4545-966e-dcc4965f5c2f', 'e5e88567-5a05-42a5-bc59-bbf8c0d75e15.gif'),
(218, '0a45b698-59d4-4032-b56c-c9533535c2e4', '917378eb-9c35-4c55-97e1-bc8ecdd2372f.png'),
(219, '0a45b698-59d4-4032-b56c-c9533535c2e4', 'dc5191ca-37ae-443e-ac41-80d5ce3e4be4.jpg'),
(220, '1d29dff2-5733-4825-8f63-acf68f6a2904', '1136c3d8-28be-470d-ac7e-736fb2f7e543.jpg'),
(221, '5f3ecfea-9dd0-4269-b712-1fcd6ff92fc7', '70fec528-8412-4ac4-a1bd-119400c5d4c0.gif');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `friend`
--

CREATE TABLE `friend` (
                          `id` int(11) NOT NULL,
                          `userId` int(11) DEFAULT NULL,
                          `friendId` int(11) DEFAULT NULL,
                          `status` varchar(20) DEFAULT NULL,
                          `time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `friend`
--

INSERT INTO `friend` (`id`, `userId`, `friendId`, `status`, `time`) VALUES
(1, 1, 3, 'FRIEND', '2021-09-15 06:18:14'),
(2, 3, 2, 'FRIEND', '2021-09-22 05:28:00'),
(3, 1, 2, 'FRIEND', '2021-10-11 20:43:38'),
(4, 2, 4, 'FRIEND', '2021-10-01 22:07:24'),
(31, 2, 5, 'WAIT', '2021-10-14 15:28:29'),
(32, 7, 1, 'WAIT', '2021-10-14 19:15:19'),
(34, 7, 2, 'FRIEND', '2021-10-14 19:15:50'),
(39, 6, 2, 'FRIEND', '2021-10-25 10:49:24');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `location`
--

CREATE TABLE `location` (
                            `id` int(11) NOT NULL,
                            `userId` int(11) DEFAULT NULL,
                            `ip` varchar(20) DEFAULT NULL,
                            `longitude` varchar(20) DEFAULT NULL,
                            `latitude` varchar(20) DEFAULT NULL,
                            `address` varchar(50) DEFAULT NULL,
                            `time` datetime DEFAULT NULL,
                            `status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `location`
--

INSERT INTO `location` (`id`, `userId`, `ip`, `longitude`, `latitude`, `address`, `time`, `status`) VALUES
(3, 1, '113.185.42.90', '105.81675720214844', '21.038360595703125', 'Chrome Hanoi, Vietnam', '2021-10-04 21:04:56', ''),
(7, 3, '113.185.42.39', '105.82472229003906', '21.034820556640625', 'Chrome Hanoi, Vietnam', '2021-10-06 09:29:30', ''),
(8, 1, '113.185.42.39', '105.82472229003906', '21.034820556640625', 'Chrome Hanoi, Vietnam', '2021-10-06 10:36:46', ''),
(10, 1, '113.185.43.219', '105.87056732177734', '21.04545021057129', 'Chrome Hanoi, Vietnam', '2021-10-06 17:09:18', ''),
(13, 3, '113.185.47.204', '105.81511688232422', '21.020580291748047', 'Chrome Hanoi, Vietnam', '2021-10-07 15:32:46', ''),
(14, 5, '113.185.47.204', '105.81511688232422', '21.020580291748047', 'Chrome Hanoi, Vietnam', '2021-10-07 15:33:16', ''),
(15, 4, '113.185.47.204', '105.81511688232422', '21.020580291748047', 'Chrome Hanoi, Vietnam', '2021-10-07 15:33:39', ''),
(19, 4, '113.185.40.81', '105.79093933105469', '21.047460556030273', 'Chrome Hanoi, Vietnam', '2021-10-08 21:03:34', ''),
(20, 1, '113.185.40.81', '105.79093933105469', '21.047460556030273', 'Chrome Hanoi, Vietnam', '2021-10-08 21:04:11', ''),
(22, 3, '113.185.43.227', '105.77417755126953', '21.040620803833008', 'Chrome Hanoi, Vietnam', '2021-10-09 08:12:13', ''),
(24, 3, '113.185.46.228', '105.81742858886719', '21.027549743652344', 'Chrome Hanoi, Vietnam', '2021-10-09 10:40:05', ''),
(26, 3, '113.185.45.44', '105.83522033691406', '21.023849487304688', 'Chrome Hanoi, Vietnam', '2021-10-09 14:08:48', ''),
(27, 6, '113.185.45.44', '105.83522033691406', '21.023849487304688', 'Chrome Hanoi, Vietnam', '2021-10-09 14:40:49', ''),
(29, 3, '113.185.41.146', '105.79093933105469', '21.047460556030273', 'Chrome Hanoi, Vietnam', '2021-10-09 22:03:49', ''),
(30, 4, '113.185.41.146', '105.79093933105469', '21.047460556030273', 'Chrome Hanoi, Vietnam', '2021-10-09 22:04:29', ''),
(32, 6, '113.185.44.6', '105.79238891601562', '21.022689819335938', 'Chrome Hanoi, Vietnam', '2021-10-10 10:03:37', ''),
(33, 3, '113.185.44.6', '105.79238891601562', '21.022689819335938', 'Chrome Hanoi, Vietnam', '2021-10-10 10:41:36', ''),
(34, 4, '113.185.44.6', '105.79238891601562', '21.022689819335938', 'Chrome Hanoi, Vietnam', '2021-10-10 10:47:38', ''),
(37, 4, '113.185.44.216', '105.79161071777344', '21.03013038635254', 'Chrome Hanoi, Vietnam', '2021-10-11 10:59:28', ''),
(39, 4, '113.185.47.223', '105.83522033691406', '21.023849487304688', 'Chrome Hanoi, Vietnam', '2021-10-11 16:29:45', ''),
(40, 3, '113.185.47.223', '105.83522033691406', '21.023849487304688', 'Edge ( chromium based) Hanoi, Vietnam', '2021-10-11 17:29:28', ''),
(41, 1, '113.185.47.223', '105.83522033691406', '21.023849487304688', 'Chrome Hanoi, Vietnam', '2021-10-11 20:41:51', ''),
(43, 2, '113.185.40.191', '105.79093933105469', '21.047460556030273', 'Edge ( chromium based) Hanoi, Vietnam', '2021-10-14 09:01:05', 'BLOCK'),
(44, 2, '113.185.47.254', '105.80081176757812', '21.049240112304688', 'Chrome Hanoi, Vietnam', '2021-10-14 09:31:27', 'BLOCK');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `message`
--

CREATE TABLE `message` (
                           `id` varchar(50) NOT NULL,
                           `userId` int(11) DEFAULT NULL,
                           `roomId` varchar(50) DEFAULT NULL,
                           `type` varchar(10) DEFAULT NULL,
                           `time` datetime DEFAULT NULL,
                           `content` text DEFAULT NULL,
                           `status` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `message`
--

INSERT INTO `message` (`id`, `userId`, `roomId`, `type`, `time`, `content`, `status`) VALUES
('', 1, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CREATE', '2021-10-02 15:00:04', 'Bắt đầu trò chuyện', 'CREATE'),
('00619e81-62a4-4164-af19-df888f3a6a43', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CREATE', '2021-09-28 16:34:24', 'Bắt đầu trò chuyện', 'CREATE'),
('0111c4ed-f042-4b2a-ad4c-9999e735a9d6', 4, 'fae804e8-8aa4-4583-8f25-c170da39c0d9', 'CHAT', '2021-10-08 21:03:55', 'd', 'READ'),
('02f6f496-98d0-47e3-a049-d1be13a803b1', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:44:00', 'ek ku', 'READ'),
('0898a6b4-1fb9-4545-966e-dcc4965f5c2f', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'ATTACK', '2021-10-22 20:47:02', 'hú', 'READ'),
('09f80866-b3b1-4321-9ea4-409834ec9526', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-03 16:30:30', 'Chào lại phát', 'READ'),
('0a45b698-59d4-4032-b56c-c9533535c2e4', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'ATTACK', '2021-10-25 10:44:19', 'hello', 'READ'),
('0b82cd99-71d3-4577-a3d7-dda78ffbd20e', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:48', 'wwwwwwwwww', 'READ'),
('0ccfc26d-3332-4930-890d-aa34564f855a', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'ATTACK', '2021-10-22 19:29:18', 'hello', 'READ'),
('0d94c7f9-b360-4a83-b9a4-1880979065bc', 3, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-04 21:32:13', 'Alo alo', 'SEND'),
('111a2a49-a6c7-4fd7-88bd-74a70cbc70a2', 6, '3799438b-7f03-447a-b5c3-d32c088b5c2e', 'CHAT', '2021-10-09 15:05:23', 'sao bro', 'READ'),
('11b94c20-3250-433d-8d1d-592b9aceb893', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-09-28 16:37:31', 'chào', 'READ'),
('17b9922c-d6a8-4c0b-af21-bb039f5708ee', 2, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-10 10:54:45', 'ek', 'SEND'),
('181388c5-d852-47f3-83b8-008dbba95202', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 10:01:22', 'sao', 'READ'),
('192ce162-e213-4c59-8150-d0b7ba18749d', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:38:13', 'alo', 'READ'),
('19f96a77-a99f-4be5-859d-3fe6e626c223', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:26:10', 'eee', 'READ'),
('1a5133cf-3a1c-479e-887e-f3958e1116c2', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:37:00', 'alo', 'READ'),
('1c2d7b85-5896-4194-b517-5b4edad50c11', 3, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-04 21:58:06', 'Đâu hết r', 'SEND'),
('1d29dff2-5733-4825-8f63-acf68f6a2904', 10, 'e68815ef-bbfc-4488-a3a2-9fd2a02760ee', 'ATTACK', '2021-10-25 10:54:47', '🤣 hí', 'READ'),
('216800e6-1341-4713-a6f7-c4a60920040a', 2, '93390eb6-6cf4-44ab-992e-1b79b142f1dd', 'CREATE', '2021-10-25 10:46:41', 'Bắt đầu trò chuyện', 'CREATE'),
('230fc39d-c763-422a-b9b6-46c60f0447c6', 3, 'e00cece0-d368-4a3f-8bc9-e58952859afe', 'CHAT', '2021-10-06 13:28:13', 'cmm', 'READ'),
('2c771314-b6d9-44c5-aa24-1343ddf66f36', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-30 18:59:26', 'chào', 'READ'),
('36bcfd18-3acf-4967-a13e-ad2632bddfa1', 2, '524f7546-9984-4e44-906f-e15c492d3c76', 'CREATE', '2021-09-30 21:06:04', 'Bắt đầu trò chuyện', 'CREATE'),
('3ad434ad-f877-48b0-93be-50abe1180b0b', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-16 12:45:54', 'chào', 'READ'),
('3babe235-bc73-436d-83f5-c1db96e47718', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:44:29', 'sao', 'READ'),
('3d484e7c-b41d-4c83-8e2e-9a29c20a65b1', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-14 19:13:07', 'cdd', 'READ'),
('3da67600-c363-4749-8175-a1f681a083be', 2, '524f7546-9984-4e44-906f-e15c492d3c76', 'ATTACK', '2021-10-04 21:28:07', 'f', 'SEND'),
('3ec64775-68fa-4305-b924-22c40a4c3db0', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:25:26', 'lddddddd', 'READ'),
('44abf221-0890-403d-aede-ae3c1e4e7fb2', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:46', 'wwwwwwwww', 'READ'),
('44b059cc-4dae-46c0-882f-d38cfe54c63d', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-25 10:44:45', 'chào', 'READ'),
('477aa761-166e-40b3-8635-7e1c407c1aca', 3, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-04 21:32:49', 'Chào ae', 'READ'),
('4847c289-34b7-4ef1-8e71-5ad2f1cf9d18', 2, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-04 21:55:12', 'd', 'SEND'),
('48635c75-d15b-4f18-b296-fd625b9135fa', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'ATTACK', '2021-10-14 18:29:57', 'test phát cuối', 'READ'),
('4934c438-d978-4dfb-a04f-3c5f0b364b2e', 3, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-04 21:07:07', 'Uk', 'READ'),
('4af86928-e475-42d3-92d8-bc0271f3c259', 4, 'fae804e8-8aa4-4583-8f25-c170da39c0d9', 'CREATE', '2021-09-28 16:53:13', 'Bắt đầu trò chuyện', 'CREATE'),
('4c94030f-f6a3-4a29-bf7b-96565ba8151b', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-22 20:47:48', 'allooo', 'READ'),
('4cff46fa-14b2-48cf-9c55-0ce42e316f62', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:37', 'aaa', 'READ'),
('4d87272f-a5f7-4029-96ee-3baefda779bd', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-22 20:47:31', 'chào', 'READ'),
('4e96f411-57a6-4b2f-8390-3543c30565fa', 2, '524f7546-9984-4e44-906f-e15c492d3c76', 'CHAT', '2021-10-05 11:07:45', 'chafo', 'SEND'),
('4f2ba6e1-256e-445f-8d31-8c168014f658', 7, 'eb2c57c5-6052-4d0a-bcf0-1ff28811a23d', 'CHAT', '2021-10-14 19:12:02', 'chào 1', 'READ'),
('51e59b46-147f-472a-9cfb-52a5e1532bac', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'ATTACK', '2021-10-22 19:23:34', 'alo', 'READ'),
('5339851e-b814-4f96-8857-cb3845493553', 2, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-04 21:34:03', 'q', 'SEND'),
('567ff970-f9be-436a-bf57-64b405d80183', 2, 'e00cece0-d368-4a3f-8bc9-e58952859afe', 'ATTACK', '2021-10-04 13:00:31', 'Hello', 'READ'),
('5add7947-b62e-4bcf-814c-9dbf5c4b6906', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-25 10:42:25', 'hú', 'READ'),
('5b0ebe63-e609-4e49-a9e7-6f6bb9a83e43', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 10:37:17', 'alo 4', 'READ'),
('5b172fbf-fbc0-4860-91a1-ee444e945308', 2, '3799438b-7f03-447a-b5c3-d32c088b5c2e', 'CREATE', '2021-10-09 15:04:51', 'Bắt đầu trò chuyện', 'CREATE'),
('5de48281-a67c-4253-8730-12e7ac3f5443', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:55', 'dmmmmmmm', 'READ'),
('5f3ecfea-9dd0-4269-b712-1fcd6ff92fc7', 2, '26cc2a71-96ed-45cb-bd0a-0278def8165c', 'ATTACK', '2021-10-30 18:09:00', 'hello', 'SEND'),
('5fe0a391-e422-400d-9729-ef657aa7aa5a', 1, 'e68815ef-bbfc-4488-a3a2-9fd2a02760ee', 'CHAT', '2021-10-25 10:54:22', 'chào ae', 'READ'),
('611a42bb-4d05-40f3-b556-61dc7ef36ba6', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-02 19:21:59', 'oke', 'READ'),
('63af11a3-fbb1-455b-98a7-6c1836bbc64c', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:40', 'aaaaaaaa', 'READ'),
('64f7a89c-5f4c-426b-9d21-21e7432861c1', 2, 'e00cece0-d368-4a3f-8bc9-e58952859afe', 'CREATE', '2021-09-29 10:31:12', 'Bắt đầu trò chuyện', 'CREATE'),
('658913c2-56db-444c-bebf-f034e80f0c7b', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 10:37:10', 'alo 2', 'READ'),
('6648ab5b-82b8-4e8f-baba-3f4bb9e5d89f', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:39', 'aaaaaaaaaa', 'READ'),
('6ba9bddc-2657-42a5-bcfb-0ce80c982c49', 3, 'c0f2973a-6acb-41b5-8ee7-1079bad0d9dd', 'CHAT', '2021-10-02 19:23:03', 'yep', 'SEND'),
('6d22daba-fd7a-4989-9f56-c4ee5f30d399', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'ATTACK', '2021-09-27 22:09:33', 'đẹp không', 'SEND'),
('6d96b688-ba7f-4508-a133-a561289f7389', 1, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-04 21:34:27', 'e', 'SEND'),
('6d9f986a-a371-4ef4-be69-6cc862315c25', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'ATTACK', '2021-10-14 19:16:25', 'dd', 'READ'),
('6e37ccc2-aa45-4750-9f7f-b302f93115f7', 3, 'e00cece0-d368-4a3f-8bc9-e58952859afe', 'CHAT', '2021-10-06 13:41:10', 'đâu r', 'READ'),
('6eb29d48-44b6-4a3a-9e54-ed5b2ff18133', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'ATTACK', '2021-10-06 17:09:19', 'image', 'READ'),
('6fca8ee5-4bf0-466d-ae31-f8690cd8d024', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:13', 'cũng đc', 'READ'),
('70a24114-3050-4b7c-9039-876ea4a2b300', 4, 'fae804e8-8aa4-4583-8f25-c170da39c0d9', 'CHAT', '2021-09-28 16:53:17', 'chào', 'READ'),
('713ca764-ccea-4708-91a8-2dd33b956286', 3, '8ea718ec-7bae-4fb5-82b8-6d13015c9c20', 'CREATE', '2021-09-29 17:08:00', 'Bắt đầu trò chuyện', 'CREATE'),
('7208acaa-01a6-4826-bdf7-ffcc684c5a0b', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-09-28 16:34:35', 'chào', 'READ'),
('73a00435-3550-4bce-9dda-45479ac08cc4', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-22 19:24:40', 'sao bro', 'READ'),
('758ed9b1-6886-4fa0-bc14-4f740921b199', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'ATTACK', '2021-10-07 22:19:06', 'dd', 'READ'),
('7d624b48-b499-4642-8906-fabb0bc468c0', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:25:14', 'loo', 'READ'),
('7d69905c-e321-4216-8420-f2c1479f0af0', 1, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'ATTACK', '2021-10-04 21:33:06', 'ccc', 'READ'),
('7e50cc13-49fb-432c-bef9-afa0cde962f7', 1, 'e68815ef-bbfc-4488-a3a2-9fd2a02760ee', 'CREATE', '2021-10-25 10:54:17', 'Bắt đầu trò chuyện', 'CREATE'),
('7fd1af30-a1be-4b1f-acf7-d4164cd82d8b', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 22:03:47', 'đâu r', 'READ'),
('89648ebd-aa80-464a-a63d-becb53222349', 2, '26cc2a71-96ed-45cb-bd0a-0278def8165c', 'CHAT', '2021-10-25 10:52:31', 'chào', 'READ'),
('89f8b808-5b47-44a2-a45a-c963554cdb30', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:22:32', 'chafo', 'READ'),
('8cb416ca-cc0b-47b4-8903-8d4d299cf199', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 10:37:14', 'alo 3', 'READ'),
('96e059e4-99f7-4e3a-905a-0e8a9c0ce933', 1, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-06 13:35:41', 'hussss', 'SEND'),
('96f6a03f-c4c7-4c93-aff6-8ace0ce5d73b', 2, '524f7546-9984-4e44-906f-e15c492d3c76', 'CHAT', '2021-10-05 10:59:58', 'chafo', 'SEND'),
('983165dd-adb3-40d2-914a-f30504049e8f', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-27 22:11:41', 'hả', 'READ'),
('99350a6a-3102-4e3c-a06a-2c47dd3a7786', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-02 19:21:56', 'yep', 'READ'),
('99740bab-7f87-47d8-91ff-b4f7f8997d35', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-09-28 16:37:10', 'chào lại', 'READ'),
('998a05c8-4e28-4b5a-bc83-1eaf32b978bd', 3, 'e00cece0-d368-4a3f-8bc9-e58952859afe', 'CHAT', '2021-10-06 13:41:32', 'dddd', 'READ'),
('9b6afc92-d4f8-47e3-a4d2-8a4bc0afed08', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CREATE', '2021-09-27 22:00:58', 'Bắt đầu trò chuyện', 'CREATE'),
('9c47af33-d593-404f-b72c-bbe6a7d2638c', 3, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-04 21:57:41', 'Hú', 'SEND'),
('9fe5e0bb-1700-43c4-8193-3cb0035ef86f', 7, 'eb2c57c5-6052-4d0a-bcf0-1ff28811a23d', 'CREATE', '2021-10-14 19:11:49', 'Bắt đầu trò chuyện', 'CREATE'),
('a0f1af3c-1fa3-43db-8ed8-409cd77e8d6e', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:42', 'dddddđ', 'READ'),
('a31b002c-934d-406c-bbfa-e1ebe34e7dbc', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:58:02', 'oke luon', 'READ'),
('a9e853ce-aab3-4985-b69d-240506642bca', 2, '3799438b-7f03-447a-b5c3-d32c088b5c2e', 'CHAT', '2021-10-09 15:05:15', 'hú', 'READ'),
('af7d4a88-f99b-4497-bf77-abf15201f512', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-03 15:35:15', 'chào', 'READ'),
('b31797eb-588e-436b-a25d-fa8821d971e3', 1, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-06 13:38:26', 'đc ko', 'SEND'),
('b3bc206f-9183-4c1e-8096-bb93da5adae1', 2, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'ATTACK', '2021-10-04 21:07:32', 'oke', 'READ'),
('b572f14d-bcf8-4620-9f4a-041fd5895d2d', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:57:56', 'teest nhiefu leen', 'READ'),
('b691f3be-d944-45d7-822d-8c1c4d693c73', 2, '524f7546-9984-4e44-906f-e15c492d3c76', 'ATTACK', '2021-10-04 21:20:29', 'aaa', 'SEND'),
('b6bdbd15-701c-4fb4-8ebb-b6509f517e69', 3, 'c0f2973a-6acb-41b5-8ee7-1079bad0d9dd', 'CREATE', '2021-09-29 17:03:30', 'Bắt đầu trò chuyện', 'CREATE'),
('b7e566c3-5ed2-4d20-9c7e-49291280f724', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-25 10:44:33', 'alo alo', 'READ'),
('ba0b27a7-c0c5-443e-9822-3ebd43fd9245', 1, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-02 15:26:49', 'chào ae', 'SEND'),
('bbf11795-6a99-424f-894f-f68234be99db', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:23:04', 'ddm', 'READ'),
('bf32d914-4302-4217-be5c-ed4e51d3bded', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:37:50', 'sao', 'READ'),
('bfb2b436-b71d-4201-9e6e-180fb3843dd4', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-27 22:09:05', 'dd', 'SEND'),
('c0640c1a-d12a-4ade-8eb4-ee67c4424f19', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 10:37:06', 'alo 1', 'READ'),
('c4282ad5-7699-442a-8c87-2e7b84962c17', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-22 19:23:19', 'alo alo', 'READ'),
('c653cb8e-33b7-4530-a7ee-0089540cdc4b', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-02 19:20:44', 'hú', 'READ'),
('c85dbf98-cbd3-4796-9200-dfdb9a5b70dd', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-14 19:12:50', 'xem', 'READ'),
('c89cbfd5-02e9-4d6e-9e5b-d7ccafe4cbec', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-30 18:59:43', 'chào', 'READ'),
('ca6fca21-83b2-41e5-8aca-2f41686a1d74', 4, 'fae804e8-8aa4-4583-8f25-c170da39c0d9', 'ATTACK', '2021-10-08 21:03:42', 'okee', 'READ'),
('caa72106-e138-4286-b5e4-ad7665f1b800', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-04 12:59:09', 'Ek ku', 'READ'),
('ce2f701a-59d4-4602-b4dc-8f8e823505a8', 1, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-06 13:37:10', 'hú hú', 'SEND'),
('cfb21cbb-f352-4f31-9acc-64165108ea0f', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-11-16 23:07:16', 'hus', 'SEND'),
('cfd96605-43b3-455c-864b-96bb598c6395', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:36', 'aaaaaaaaaaa', 'READ'),
('d0b5899e-6afc-4060-8e2b-bd2e79afdf18', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-09-28 21:11:37', 'chào bro', 'READ'),
('d2442a92-0ef7-4b97-9fd1-e38ae530f8d9', 2, '3799438b-7f03-447a-b5c3-d32c088b5c2e', 'CHAT', '2021-10-09 15:05:02', 'chào', 'READ'),
('d5a5dbf0-d1d7-4360-90e2-c053a800d1ab', 2, '524f7546-9984-4e44-906f-e15c492d3c76', 'CHAT', '2021-10-01 21:59:48', 'Chào', 'SEND'),
('d5b13393-304e-4f38-947d-498f28a46e91', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-14 18:32:48', 'oke bro', 'READ'),
('d61aeca2-4b1d-4972-9f40-b73bf80ac63a', 2, 'e00cece0-d368-4a3f-8bc9-e58952859afe', 'ATTACK', '2021-10-04 12:59:43', 'Chào bro', 'READ'),
('db8c04a4-24b0-4369-a9e3-e15d517f9647', 1, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'ATTACK', '2021-10-04 21:13:12', 'hus', 'READ'),
('dbdb02a7-bc8c-4e8b-9a56-b72cf8d54919', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-06 13:22:36', 'dau', 'READ'),
('dcefc1b9-b25b-4475-aa82-f5246e9f231c', 2, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-04 21:55:27', 'q', 'SEND'),
('de6cd54e-38f5-4563-a847-f4bb033411b1', 2, '26cc2a71-96ed-45cb-bd0a-0278def8165c', 'CREATE', '2021-10-25 10:52:26', 'Bắt đầu trò chuyện', 'CREATE'),
('df9d4602-011b-449f-93f4-4b81eabee1e3', 3, 'e00cece0-d368-4a3f-8bc9-e58952859afe', 'CHAT', '2021-10-06 13:47:07', 'alo alo', 'READ'),
('ea23d44f-1478-4e94-a89c-9f1cc6e08855', 3, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-04 21:05:42', 'Chào', 'READ'),
('ea8b14af-968d-45bb-8d04-3c16be91fb4b', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:58:06', 'nhan de', 'READ'),
('eb191a79-8a5b-405b-8196-9ba596a18f8f', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'ATTACK', '2021-10-02 19:21:35', 'chào', 'READ'),
('ed20c3fd-e8a2-406e-aa8b-88bb2c5392df', 4, '91467c30-afd4-47e4-98c7-a27c6ca5ce2d', 'CREATE', '2021-09-28 16:42:57', 'Bắt đầu trò chuyện', 'CREATE'),
('ed444569-3883-47ae-ae1f-425a029ae2fc', 2, '524f7546-9984-4e44-906f-e15c492d3c76', 'CHAT', '2021-10-04 21:57:54', 'ds', 'SEND'),
('f463b22d-322a-4586-8ff7-ad35dcfafd9a', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 22:03:28', 'aloooo', 'READ'),
('f6059961-3955-47dc-bcf8-3ee2152e8c22', 2, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-07 17:22:20', 'ồ', 'SEND'),
('f87c7a1c-03b1-4271-ac4d-d3fe3585fa5c', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-03 16:10:50', 'chào phát nữa', 'READ'),
('fd3a5b06-8ef3-4bbb-b3e9-eaa8f04a6e06', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'ATTACK', '2021-09-28 21:58:30', 'phim phim', 'READ'),
('fde7bc7b-2f01-4ac4-9342-9590740d911c', 1, '91467c30-afd4-47e4-98c7-a27c6ca52608', 'CHAT', '2021-10-08 21:04:29', 'sao', 'SEND'),
('fede294e-82c8-4a33-acd7-013d74f047d1', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-10-30 18:59:53', 'addf', 'READ'),
('fee43325-3d32-4f55-97c9-39bd80143659', 4, '91467c30-afd4-47e4-98c7-a27c6ca5ce2d', 'CHAT', '2021-09-28 16:53:03', 'chào', 'READ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notification`
--

CREATE TABLE `notification` (
                                `id` int(11) NOT NULL,
                                `userId` int(11) DEFAULT NULL,
                                `title` varchar(50) DEFAULT NULL,
                                `image` varchar(100) DEFAULT NULL,
                                `content` varchar(100) DEFAULT NULL,
                                `status` varchar(10) DEFAULT NULL,
                                `type` varchar(5) DEFAULT NULL,
                                `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `notification`
--

INSERT INTO `notification` (`id`, `userId`, `title`, `image`, `content`, `status`, `type`, `time`) VALUES
(1, 2, 'Đặng Tiến Quang', 'avt.png', 'Đặng Tiến Quang đã chấp nhận lời mời kết bạn.', 'OFF', 'AGREE', '2021-10-02 05:37:00'),
(3, 6, 'Nguyễn Đức Trung', 'b21d062b-b196-4afa-9541-4ff8346f8ca4.png', 'Nguyễn Đức Trung đã chấp nhận lời mời kết bạn.', 'OFF', 'AGREE', '2021-10-09 15:05:40'),
(4, 1, 'Nguyễn Đức Trung', 'b21d062b-b196-4afa-9541-4ff8346f8ca4.png', 'Nguyễn Đức Trung đã chấp nhận lời mời kết bạn.', 'ON', 'AGREE', '2021-10-11 20:41:52'),
(5, 1, 'Nguyễn Đức Trung', 'b21d062b-b196-4afa-9541-4ff8346f8ca4.png', 'Nguyễn Đức Trung đã chấp nhận lời mời kết bạn.', 'ON', 'AGREE', '2021-10-11 20:43:38'),
(6, 7, 'Nguyễn Đức Trung', 'b21d062b-b196-4afa-9541-4ff8346f8ca4.png', 'Nguyễn Đức Trung đã chấp nhận lời mời kết bạn.', 'OFF', 'AGREE', '2021-10-14 19:15:50'),
(7, 6, 'Nguyễn Đức Trung', 'b21d062b-b196-4afa-9541-4ff8346f8ca4.png', 'Nguyễn Đức Trung đã chấp nhận lời mời kết bạn.', 'ON', 'AGREE', '2021-10-25 10:49:24');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `room`
--

CREATE TABLE `room` (
                        `id` varchar(50) NOT NULL,
                        `image` varchar(50) DEFAULT NULL,
                        `name` varchar(100) NOT NULL,
                        `username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `room`
--

INSERT INTO `room` (`id`, `image`, `name`, `username`) VALUES
('02e06020-4cd3-4fba-9e54-59e6f33e285d', '', '', 'trung'),
('19f4723a-0c86-4da4-940b-df3c32280f57', '', '', ''),
('26cc2a71-96ed-45cb-bd0a-0278def8165c', '', '', ''),
('3799438b-7f03-447a-b5c3-d32c088b5c2e', '', '', ''),
('524f7546-9984-4e44-906f-e15c492d3c76', '', '', ''),
('5c3b7026-3856-4d86-8630-ddedcc1ea5c7', '', '', ''),
('8ea718ec-7bae-4fb5-82b8-6d13015c9c20', '', '', 'quang'),
('91467c30-afd4-47e4-98c7-a27c6ca52608', '57f255c6-83b1-4539-9fb4-c5a67584e990.jpg', 'Nhóm test', ''),
('91467c30-afd4-47e4-98c7-a27c6ca5ce2d', '', '', ''),
('93390eb6-6cf4-44ab-992e-1b79b142f1dd', '57f255c6-83b1-4539-9fb4-c5a67584e990.jpg', 'nhóm demo', ''),
('c0f2973a-6acb-41b5-8ee7-1079bad0d9dd', '', '', ''),
('e00cece0-d368-4a3f-8bc9-e58952859afe', '', '', ''),
('e68815ef-bbfc-4488-a3a2-9fd2a02760ee', '', '', ''),
('eb2c57c5-6052-4d0a-bcf0-1ff28811a23d', '', '', ''),
('fae804e8-8aa4-4583-8f25-c170da39c0d9', '', '', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roomdetail`
--

CREATE TABLE `roomdetail` (
                              `id` int(11) NOT NULL,
                              `roomId` varchar(50) DEFAULT NULL,
                              `userId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `roomdetail`
--

INSERT INTO `roomdetail` (`id`, `roomId`, `userId`) VALUES
(27, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 1),
(28, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 3),
(41, '19f4723a-0c86-4da4-940b-df3c32280f57', 1),
(42, '19f4723a-0c86-4da4-940b-df3c32280f57', 2),
(43, '91467c30-afd4-47e4-98c7-a27c6ca5ce2d', 4),
(44, '91467c30-afd4-47e4-98c7-a27c6ca5ce2d', 1),
(45, 'fae804e8-8aa4-4583-8f25-c170da39c0d9', 4),
(46, 'fae804e8-8aa4-4583-8f25-c170da39c0d9', 2),
(47, 'e00cece0-d368-4a3f-8bc9-e58952859afe', 2),
(48, 'e00cece0-d368-4a3f-8bc9-e58952859afe', 3),
(51, '8ea718ec-7bae-4fb5-82b8-6d13015c9c20', 3),
(52, '8ea718ec-7bae-4fb5-82b8-6d13015c9c20', 4),
(53, '524f7546-9984-4e44-906f-e15c492d3c76', 2),
(54, '524f7546-9984-4e44-906f-e15c492d3c76', 5),
(57, '91467c30-afd4-47e4-98c7-a27c6ca52608', 1),
(58, '91467c30-afd4-47e4-98c7-a27c6ca52608', 2),
(67, '91467c30-afd4-47e4-98c7-a27c6ca52608', 4),
(68, '91467c30-afd4-47e4-98c7-a27c6ca52608', 3),
(77, '3799438b-7f03-447a-b5c3-d32c088b5c2e', 2),
(78, '3799438b-7f03-447a-b5c3-d32c088b5c2e', 6),
(95, 'eb2c57c5-6052-4d0a-bcf0-1ff28811a23d', 7),
(96, 'eb2c57c5-6052-4d0a-bcf0-1ff28811a23d', 1),
(99, '93390eb6-6cf4-44ab-992e-1b79b142f1dd', 2),
(100, '93390eb6-6cf4-44ab-992e-1b79b142f1dd', 1),
(101, '93390eb6-6cf4-44ab-992e-1b79b142f1dd', 3),
(102, '93390eb6-6cf4-44ab-992e-1b79b142f1dd', 4),
(103, '93390eb6-6cf4-44ab-992e-1b79b142f1dd', 7),
(110, '26cc2a71-96ed-45cb-bd0a-0278def8165c', 2),
(111, '26cc2a71-96ed-45cb-bd0a-0278def8165c', 10),
(112, 'e68815ef-bbfc-4488-a3a2-9fd2a02760ee', 1),
(113, 'e68815ef-bbfc-4488-a3a2-9fd2a02760ee', 10);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
                         `id` int(11) NOT NULL,
                         `username` varchar(20) NOT NULL,
                         `firstname` varchar(100) DEFAULT NULL,
                         `lastname` varchar(100) DEFAULT NULL,
                         `email` varchar(100) DEFAULT NULL,
                         `password` varchar(100) DEFAULT NULL,
                         `phone` varchar(10) NOT NULL,
                         `image` varchar(100) DEFAULT NULL,
                         `birthdate` date DEFAULT NULL,
                         `gender` bit(1) NOT NULL,
                         `role` varchar(10) DEFAULT NULL,
                         `description` varchar(100) NOT NULL,
                         `lastonline` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `username`, `firstname`, `lastname`, `email`, `password`, `phone`, `image`, `birthdate`, `gender`, `role`, `description`, `lastonline`) VALUES
(1, 'admin', 'Thành', 'Nguyễn Đức', 'admin@gmail.com', '$2a$10$OHafDhpuGk9SQvtAPqnJK.cayB5/VgV3GbfXA6sriEg8wYFfc.Zuu', '0944485574', '0bd8982d-1561-4432-9fae-a4f4dac91b20.gif', '2005-06-17', b'1', 'ROLE_ADMIN', 'Hello Iam Duc Thanh', '2021-10-30 19:02:56'),
(2, 'trung123', 'Trung 123', 'Nguyễn Đức', 'trung@gmail.com', '$2a$10$2LtriT4g4VwvwHp0D4rDyeSmkvylv6Lx2yJ7A/ypy4A.Dpy2.bD7q', '0944485574', '10bf478a-46e6-43e7-97ba-669084898f90.gif', '2021-08-01', b'0', 'ROLE_ADMIN', 'Hello iam Thành', '2021-11-16 15:53:28'),
(3, 'quang', 'Quang', 'Đặng Tiến', 'quang@gmail.com', '$2a$10$6LZucgkwCfXT8c0UWxp4tOYY1dH0frALxeFW4GnEJJwqU0f5epDgC', '', 'avt.png', '2021-07-12', b'1', 'ROLE_ADMIN', '', '2021-10-25 10:53:40'),
(4, 'fpoly', 'Duy', 'Nguyễn Đức', 'ducthanh@gmail.com', '$2a$10$KBp6/YrR8loiY57gfk2JjeNZwR7v5JDGNHLH3AhG/CNnXQMLvOwfe', '', 'avt.png', '2007-06-12', b'1', 'ROLE_USER', '', '2021-10-14 19:24:49'),
(5, 'fpoly1', 'Trung', 'Nguyễn', 'ducthanh26@gmail.com', '$2a$10$sl5C66UwdHFNd0XNAVA7fOD8FJVoeOT8MohshjvgAr5gs2FXnc3HS', '', 'avt.png', '2021-09-07', b'1', 'ROLE_USER', '', '2021-10-14 15:28:49'),
(6, 'huyhuy', 'Đức Huy', 'Trương', 'ducthanh260801@gmail.com', '$2a$10$2OAixjfdtSzW6wPPL9EDC.sk.usrKmgT9GyJdia129aVcU.sJGYpK', '', 'avt.png', '2010-06-09', b'0', 'ROLE_USER', '', '2021-10-25 10:49:47'),
(7, 'admin123', 'Thành', 'Nguyễn', 'iamducthanh@gmail.com', '$2a$10$cQ6Sc/b8hFRxW2ptC2fzlOGtJ9u7ZL0lAaYeuVfYH/w2T7tXExXim', '', 'avt.png', '2021-10-22', b'1', 'ROLE_USER', '', '2021-10-14 19:16:55'),
(8, 'trung', 'Thành', 'Nguyễn', 'iamduch01@gmail.com', '$2a$10$BhtEuaOdb81YM4KrAjaBm.E.CcTnX5kyzIJAo0QiAP2EAytq7PEY6', '', 'avt.png', '2021-07-08', b'1', 'ROLE_USER', '', '2021-10-25 09:16:02'),
(9, 'trung321', 'Thành', 'Nguyễn', '@gmail.com', '$2a$10$dyvALqs5IGUhsXj9AY5y4..iws/1z3jbjN6.nn4q9rjw/Yj2k5Kh6', '', 'avt.png', '2021-10-14', b'1', 'ROLE_USER', '', '2021-10-22 19:28:54'),
(10, 'admin456', 'Thành', 'Nguyễn', 'ư@gmail.com', '$2a$10$8r01PkNKwX7J0EN7OCRCvurxFk3i2eULsnn7pvtf7pOwjXKm1p5da', '', 'avt.png', '2021-10-20', b'1', 'ROLE_USER', '', '2021-10-25 10:55:16'),
(11, 'trung321123', 'Thành', 'Nguyễn', 'e@gmail.com', '$2a$10$GAgK22LYz3Vrp67B29sxcO/SIjzyPCc73TgtqVnpO4VYuKsJpHu0W', '', 'avt.png', '2021-10-12', b'1', 'ROLE_USER', '', '2021-10-25 10:18:19'),
(12, 'admin789', 'Thành', 'Nguyễn', 'iamducthanh01@gmail.com', '$2a$10$/Et.onG5nLSAHq2xJ1BboOj4hV2BuP4dOavKPo7IdEMazbwgUjlYC', '', 'avt.png', '2021-10-21', b'1', 'ROLE_USER', '', '2021-10-25 10:43:53');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `attach`
--
ALTER TABLE `attach`
    ADD PRIMARY KEY (`id`),
  ADD KEY `messageId` (`messageId`);

--
-- Chỉ mục cho bảng `friend`
--
ALTER TABLE `friend`
    ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`),
  ADD KEY `friendId` (`friendId`);

--
-- Chỉ mục cho bảng `location`
--
ALTER TABLE `location`
    ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`);

--
-- Chỉ mục cho bảng `message`
--
ALTER TABLE `message`
    ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`),
  ADD KEY `roomId` (`roomId`);

--
-- Chỉ mục cho bảng `notification`
--
ALTER TABLE `notification`
    ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`);

--
-- Chỉ mục cho bảng `room`
--
ALTER TABLE `room`
    ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `roomdetail`
--
ALTER TABLE `roomdetail`
    ADD PRIMARY KEY (`id`),
  ADD KEY `roomId` (`roomId`),
  ADD KEY `userId` (`userId`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `attach`
--
ALTER TABLE `attach`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=222;

--
-- AUTO_INCREMENT cho bảng `friend`
--
ALTER TABLE `friend`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT cho bảng `location`
--
ALTER TABLE `location`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT cho bảng `notification`
--
ALTER TABLE `notification`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `roomdetail`
--
ALTER TABLE `roomdetail`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=114;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `attach`
--
ALTER TABLE `attach`
    ADD CONSTRAINT `attach_ibfk_1` FOREIGN KEY (`messageId`) REFERENCES `message` (`id`);

--
-- Các ràng buộc cho bảng `friend`
--
ALTER TABLE `friend`
    ADD CONSTRAINT `friend_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `friend_ibfk_2` FOREIGN KEY (`friendId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `location`
--
ALTER TABLE `location`
    ADD CONSTRAINT `location_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `message`
--
ALTER TABLE `message`
    ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`);

--
-- Các ràng buộc cho bảng `notification`
--
ALTER TABLE `notification`
    ADD CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `roomdetail`
--
ALTER TABLE `roomdetail`
    ADD CONSTRAINT `roomdetail_ibfk_1` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`),
  ADD CONSTRAINT `roomdetail_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
