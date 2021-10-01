-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 01, 2021 lúc 05:03 PM
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
(187, 'fd3a5b06-8ef3-4bbb-b3e9-eaa8f04a6e06', '2513751d-18a7-40d3-ab75-57521905fd97.jpg');

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
(3, 1, 2, 'WAIT', '2021-10-01 22:02:06'),
(4, 2, 4, 'WAIT', '2021-09-30 17:30:19'),
(28, 2, 5, 'WAIT', '2021-10-01 21:58:55');

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
                           `status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `message`
--

INSERT INTO `message` (`id`, `userId`, `roomId`, `type`, `time`, `content`, `status`) VALUES
('00619e81-62a4-4164-af19-df888f3a6a43', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CREATE', '2021-09-28 16:34:24', 'Bắt đầu trò chuyện', 'CREATE'),
('0b82cd99-71d3-4577-a3d7-dda78ffbd20e', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:48', 'wwwwwwwwww', 'READ'),
('11b94c20-3250-433d-8d1d-592b9aceb893', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-09-28 16:37:31', 'chào', 'READ'),
('181388c5-d852-47f3-83b8-008dbba95202', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 10:01:22', 'sao', 'READ'),
('36bcfd18-3acf-4967-a13e-ad2632bddfa1', 2, '524f7546-9984-4e44-906f-e15c492d3c76', 'CREATE', '2021-09-30 21:06:04', 'Bắt đầu trò chuyện', 'CREATE'),
('44abf221-0890-403d-aede-ae3c1e4e7fb2', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:46', 'wwwwwwwww', 'SEND'),
('4af86928-e475-42d3-92d8-bc0271f3c259', 4, 'fae804e8-8aa4-4583-8f25-c170da39c0d9', 'CREATE', '2021-09-28 16:53:13', 'Bắt đầu trò chuyện', 'CREATE'),
('4cff46fa-14b2-48cf-9c55-0ce42e316f62', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:37', 'aaa', 'READ'),
('5de48281-a67c-4253-8730-12e7ac3f5443', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:55', 'dmmmmmmm', 'READ'),
('63af11a3-fbb1-455b-98a7-6c1836bbc64c', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:40', 'aaaaaaaa', 'READ'),
('64f7a89c-5f4c-426b-9d21-21e7432861c1', 2, 'e00cece0-d368-4a3f-8bc9-e58952859afe', 'CREATE', '2021-09-29 10:31:12', 'Bắt đầu trò chuyện', 'CREATE'),
('6648ab5b-82b8-4e8f-baba-3f4bb9e5d89f', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:39', 'aaaaaaaaaa', 'READ'),
('6d22daba-fd7a-4989-9f56-c4ee5f30d399', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'ATTACK', '2021-09-27 22:09:33', 'đẹp không', 'SEND'),
('6fca8ee5-4bf0-466d-ae31-f8690cd8d024', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:13', 'cũng đc', 'READ'),
('70a24114-3050-4b7c-9039-876ea4a2b300', 4, 'fae804e8-8aa4-4583-8f25-c170da39c0d9', 'CHAT', '2021-09-28 16:53:17', 'chào', 'READ'),
('713ca764-ccea-4708-91a8-2dd33b956286', 3, '8ea718ec-7bae-4fb5-82b8-6d13015c9c20', 'CREATE', '2021-09-29 17:08:00', 'Bắt đầu trò chuyện', 'CREATE'),
('7208acaa-01a6-4826-bdf7-ffcc684c5a0b', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-09-28 16:34:35', 'chào', 'READ'),
('7fd1af30-a1be-4b1f-acf7-d4164cd82d8b', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 22:03:47', 'đâu r', 'READ'),
('983165dd-adb3-40d2-914a-f30504049e8f', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-27 22:11:41', 'hả', 'READ'),
('99740bab-7f87-47d8-91ff-b4f7f8997d35', 2, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-09-28 16:37:10', 'chào lại', 'READ'),
('9b6afc92-d4f8-47e3-a4d2-8a4bc0afed08', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CREATE', '2021-09-27 22:00:58', 'Bắt đầu trò chuyện', 'CREATE'),
('a0f1af3c-1fa3-43db-8ed8-409cd77e8d6e', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:42', 'dddddđ', 'READ'),
('a31b002c-934d-406c-bbfa-e1ebe34e7dbc', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:58:02', 'oke luon', 'READ'),
('b572f14d-bcf8-4620-9f4a-041fd5895d2d', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:57:56', 'teest nhiefu leen', 'READ'),
('b6bdbd15-701c-4fb4-8ebb-b6509f517e69', 3, 'c0f2973a-6acb-41b5-8ee7-1079bad0d9dd', 'CREATE', '2021-09-29 17:03:30', 'Bắt đầu trò chuyện', 'CREATE'),
('bfb2b436-b71d-4201-9e6e-180fb3843dd4', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-27 22:09:05', 'dd', 'SEND'),
('cfd96605-43b3-455c-864b-96bb598c6395', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:59:36', 'aaaaaaaaaaa', 'READ'),
('d0b5899e-6afc-4060-8e2b-bd2e79afdf18', 1, '19f4723a-0c86-4da4-940b-df3c32280f57', 'CHAT', '2021-09-28 21:11:37', 'chào bro', 'SEND'),
('d5a5dbf0-d1d7-4360-90e2-c053a800d1ab', 2, '524f7546-9984-4e44-906f-e15c492d3c76', 'CHAT', '2021-10-01 21:59:48', 'Chào', 'SEND'),
('ea8b14af-968d-45bb-8d04-3c16be91fb4b', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 21:58:06', 'nhan de', 'READ'),
('ed20c3fd-e8a2-406e-aa8b-88bb2c5392df', 4, '91467c30-afd4-47e4-98c7-a27c6ca5ce2d', 'CREATE', '2021-09-28 16:42:57', 'Bắt đầu trò chuyện', 'CREATE'),
('f463b22d-322a-4586-8ff7-ad35dcfafd9a', 3, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'CHAT', '2021-09-28 22:03:28', 'aloooo', 'READ'),
('fd3a5b06-8ef3-4bbb-b3e9-eaa8f04a6e06', 1, '5c3b7026-3856-4d86-8630-ddedcc1ea5c7', 'ATTACK', '2021-09-28 21:58:30', 'phim phim', 'READ'),
('fee43325-3d32-4f55-97c9-39bd80143659', 4, '91467c30-afd4-47e4-98c7-a27c6ca5ce2d', 'CHAT', '2021-09-28 16:53:03', 'chào', 'READ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notification`
--

CREATE TABLE `notification` (
                                `id` int(11) NOT NULL,
                                `userId` int(11) DEFAULT NULL,
                                `friendId` int(11) DEFAULT NULL,
                                `type` varchar(10) DEFAULT NULL,
                                `status` varchar(10) NOT NULL,
                                `time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `notification`
--

INSERT INTO `notification` (`id`, `userId`, `friendId`, `type`, `status`, `time`) VALUES
(1, 2, 3, 'AGREE', 'ON', '2021-09-15 15:27:06');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `room`
--

CREATE TABLE `room` (
                        `id` varchar(50) NOT NULL,
                        `groupchat` bit(1) DEFAULT NULL,
                        `name` varchar(100) NOT NULL,
                        `username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `room`
--

INSERT INTO `room` (`id`, `groupchat`, `name`, `username`) VALUES
('19f4723a-0c86-4da4-940b-df3c32280f57', b'0', '', ''),
('524f7546-9984-4e44-906f-e15c492d3c76', b'0', '', ''),
('5c3b7026-3856-4d86-8630-ddedcc1ea5c7', b'0', '', ''),
('8ea718ec-7bae-4fb5-82b8-6d13015c9c20', b'0', '', 'quang'),
('91467c30-afd4-47e4-98c7-a27c6ca5ce2d', b'0', '', ''),
('c0f2973a-6acb-41b5-8ee7-1079bad0d9dd', b'0', '', 'quang'),
('e00cece0-d368-4a3f-8bc9-e58952859afe', b'0', '', 'trung'),
('fae804e8-8aa4-4583-8f25-c170da39c0d9', b'0', '', '');

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
(49, 'c0f2973a-6acb-41b5-8ee7-1079bad0d9dd', 3),
(50, 'c0f2973a-6acb-41b5-8ee7-1079bad0d9dd', 2),
(51, '8ea718ec-7bae-4fb5-82b8-6d13015c9c20', 3),
(52, '8ea718ec-7bae-4fb5-82b8-6d13015c9c20', 4),
(53, '524f7546-9984-4e44-906f-e15c492d3c76', 2),
(54, '524f7546-9984-4e44-906f-e15c492d3c76', 5);

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
(1, 'admin', 'Thành', 'Nguyễn Đức', 'admin@gmail.com', '$2a$10$OHafDhpuGk9SQvtAPqnJK.cayB5/VgV3GbfXA6sriEg8wYFfc.Zuu', '0944485574', '0bd8982d-1561-4432-9fae-a4f4dac91b20.gif', '2005-06-17', b'1', 'ROLE_ADMIN', 'Hello Iam Duc Thanh', '2021-10-01 22:02:23'),
(2, 'trung', 'Trung', 'Nguyễn Đức', 'trung@gmail.com', '$2a$10$6LZucgkwCfXT8c0UWxp4tOYY1dH0frALxeFW4GnEJJwqU0f5epDgC', '', 'avt.png', '2021-08-01', b'1', 'ROLE_ADMIN', '', '2021-10-01 22:02:24'),
(3, 'quang', 'Quang', 'Đặng Tiến', 'quang@gmail.com', '$2a$10$6LZucgkwCfXT8c0UWxp4tOYY1dH0frALxeFW4GnEJJwqU0f5epDgC', '', 'avt.png', '2021-07-12', b'1', 'ROLE_ADMIN', '', '2021-09-30 17:18:50'),
(4, 'fpoly', 'Nguyễn', 'Duy', 'ducthanh@gmail.com', '$2a$10$KBp6/YrR8loiY57gfk2JjeNZwR7v5JDGNHLH3AhG/CNnXQMLvOwfe', '', 'avt.png', '2007-06-12', b'1', 'ROLE_USER', '', '2021-09-30 17:31:08'),
(5, 'fpoly1', 'Trung', 'Nguyễn', 'ducthanh260801@gmail.com', '$2a$10$sl5C66UwdHFNd0XNAVA7fOD8FJVoeOT8MohshjvgAr5gs2FXnc3HS', '', 'avt.png', '2021-09-07', b'1', 'ROLE_USER', '', '2021-10-01 21:59:54');

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
  ADD KEY `userId` (`userId`),
  ADD KEY `friendId` (`friendId`);

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
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=188;

--
-- AUTO_INCREMENT cho bảng `friend`
--
ALTER TABLE `friend`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT cho bảng `notification`
--
ALTER TABLE `notification`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `roomdetail`
--
ALTER TABLE `roomdetail`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

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
-- Các ràng buộc cho bảng `message`
--
ALTER TABLE `message`
    ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`);

--
-- Các ràng buộc cho bảng `notification`
--
ALTER TABLE `notification`
    ADD CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`friendId`) REFERENCES `users` (`id`);

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
