DROP DATABASE IF EXISTS a1;

CREATE DATABASE a1;

USE a1;

CREATE TABLE board(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(30)NOT NULL,
    regDate DATETIME NOT NULL
);

CREATE TABLE article(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(30) NOT NULL,
    `body` TEXT NOT NULL,
    `count` INT(10) UNSIGNED NOT NULL DEFAULT '0',
    boardId INT(10) UNSIGNED NOT NULL DEFAULT '0',
    memberId INT(10) UNSIGNED NOT NULL DEFAULT '1',
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL
);

CREATE TABLE `member`(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    loginId CHAR(30) NOT NULL,
    loginPw VARCHAR(50) NOT NULL,    
    `name` CHAR(30) NOT NULL,
    regDate DATETIME NOT NULL
);


#운영시작

#기본 게시판
INSERT INTO board
SET title = 'Notice',
regDate = NOW();

INSERT INTO board
SET title = 'IT',
regDate = NOW();

#사용자 전부 삭제
TRUNCATE `member`;


#기본 사용자
INSERT INTO `member`
SET loginId = 'admin',
loginPw = 'admin',
`name` ='제야',
regDate = NOW();

# 글 전부 삭제 
TRUNCATE article;


# 칼럼추가
ALTER TABLE article ADD COLUMN likesCount INT(10) UNSIGNED NOT NULL DEFAULT '0';
ALTER TABLE article ADD COLUMN commentsCount INT(10) UNSIGNED NOT NULL DEFAULT '0'; 

SELECT*
FROM article;


# 각종 함수
/*
SELECT DATE(NOW());
SELECT YEAR(NOW());
SELECT MONTH(NOW());
SELECT DAY(NOW());
SELECT SUBSTR("안녕하세요.", 1, 2);
SELECT SUBSTR("안녕하세요.", 2, 2);
SELECT CONCAT("안녕", "하세요.");
SELECT RAND() * 100;
*/

# 게시물 랜덤 생성
/*
insert into article
set regDate = NOW(),
updateDate = NOW(),
title = concat("제목_", rand()),
`body` = CONCAT("내용_", RAND()),
memberId = FLOOR(RAND() * 2) + 1,
boardId = FLOOR(RAND() * 2) + 1;
*/

