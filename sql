DROP DATABASE IF EXISTS a1;
CREATE DATABASE a1;
USE a1;

CREATE TABLE board(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(30) NOT NULL,
    regDate DATETIME NOT NULL
    
);

CREATE TABLE article(
    id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    writer VARCHAR(30) NOT NULL,
    `count` INT(10) NOT NULL,
    regDate DATETIME NOT NULL
);

INSERT INTO article
SET title = '제목1',
writer = '홍길동',
`count` = 3,
regDate = NOW(); 

INSERT INTO article
SET title = '제목2',
writer = '홍길삼',
`count` = 5,
regDate = NOW();

INSERT INTO article
SET title = '제목3',
writer = '임꺽정',
`count` = 5,
regDate = NOW();

SELECT *
FROM article;