-- drop database with same name
DROP DATABASE if exists project_database;

-- create database
CREATE DATABASE project_database;
USE project_database;

/* Create tables */
GRANT ALL PRIVILEGES ON project_database.* to toonfinder@localhost IDENTIFIED BY 'lovewebtoon';

-- Entity
CREATE TABLE Cartoonist (
 cid   INTEGER NOT NULL,
 position  CHAR(20),
 name   VARCHAR(256),
 PRIMARY KEY (cid)
);

CREATE TABLE Webtoon (
 wid     INTEGER NOT NULL,
 name    VARCHAR(256),
 score   FLOAT,
 price   FLOAT,
 genre   CHAR(20),
 views   FLOAT,
 summary VARCHAR(500),
 PRIMARY KEY (wid)
);

CREATE TABLE Webtoon_Worth (
 views	FLOAT,
 score	FLOAT,
 worth	CHAR(5),
 PRIMARY KEY (views, score)
);

CREATE TABLE Reader (
 rid   INTEGER NOT NULL,
 age   INTEGER,
 gender   CHAR(10),
 ccode  CHAR(10),
 uname	VARCHAR(256),
 pw		VARCHAR(256),
 CHECK (age < 100),
 PRIMARY KEY (rid)
);

CREATE TABLE Reader_age_pay (
 age	INTEGER,
 payamount	FLOAT,
 PRIMARY KEY (age)
);

CREATE TABLE Reader_Country (
 ccode	CHAR(10) NOT NULL,
 country VARCHAR(256),
 PRIMARY KEY (ccode)
);

CREATE TABLE Team (
 tid   INTEGER NOT NULL,
 name   VARCHAR(256),
 wnumber	INTEGER,
 PRIMARY KEY (tid)
);

CREATE TABLE Team_Pay (
 wnumber	INTEGER,
 wage		REAL,
 PRIMARY KEY (wnumber)
);

CREATE TABLE Company (
 comid   INTEGER NOT NULL,
 since   INTEGER,
 name   VARCHAR(256),
 PRIMARY KEY (comid)
);

CREATE TABLE Content (
 content_id  INTEGER NOT NULL,
 age_range  VARCHAR(256),
 title   VARCHAR(256),
 price   FLOAT,
 ctype	VARCHAR(256),
 PRIMARY KEY (content_id)
);

CREATE TABLE Duration (
 did   INTEGER NOT NULL,
 since   INTEGER,
 _to   INTEGER,
 PRIMARY KEY (did)
);

-- Relationship
CREATE TABLE Related_to (
 wid   INTEGER NOT NULL,
 content_id  INTEGER NOT NULL,
 rtype   VARCHAR(256),
 PRIMARY KEY (wid, content_id), 
 FOREIGN KEY (wid) REFERENCES Webtoon (wid) ON DELETE CASCADE,
 FOREIGN KEY (content_id) REFERENCES Content (content_id) ON DELETE CASCADE
);

CREATE TABLE Serialize (
 wid   INTEGER NOT NULL,
 comid   INTEGER NOT NULL,
 did   INTEGER NOT NULL,
 PRIMARY KEY (wid, comid, did),
 FOREIGN KEY (wid) REFERENCES Webtoon (wid) ON DELETE CASCADE,
 FOREIGN KEY (comid) REFERENCES Company (comid) ON DELETE CASCADE,
 FOREIGN KEY (did) REFERENCES Duration (did)
);

CREATE TABLE Written_by (
 wid   INTEGER NOT NULL,
 tid   INTEGER NOT NULL,
 PRIMARY KEY (wid, tid),
 FOREIGN KEY (wid) REFERENCES Webtoon (wid) ON DELETE CASCADE,
 FOREIGN KEY (tid) REFERENCES Team (tid) ON DELETE CASCADE
);

CREATE TABLE Member_of (
 tid   INTEGER NOT NULL,
 cid   INTEGER NOT NULL,
 PRIMARY KEY (tid, cid),
 FOREIGN KEY (tid) REFERENCES Team (tid) ON DELETE CASCADE,
 FOREIGN KEY (cid) REFERENCES Cartoonist (cid) ON DELETE CASCADE
);

CREATE TABLE Favorite_of (
 cid   INTEGER NOT NULL,
 rid   INTEGER NOT NULL,
 PRIMARY KEY (cid, rid),
 FOREIGN KEY (cid) REFERENCES Cartoonist (cid) ON DELETE CASCADE,
 FOREIGN KEY (rid) REFERENCES Reader (rid) ON DELETE CASCADE
);

CREATE TABLE Likes (
 rid   INTEGER NOT NULL,
 wid   INTEGER NOT NULL,
 PRIMARY KEY (rid, wid),
 FOREIGN KEY (rid) REFERENCES Reader (rid) ON DELETE CASCADE,
 FOREIGN KEY (wid) REFERENCES Webtoon (wid) ON DELETE CASCADE
);

CREATE TABLE Subscribes (
 rid   INTEGER NOT NULL,
 wid   INTEGER NOT NULL,
 PRIMARY KEY (rid, wid),
 FOREIGN KEY (rid) REFERENCES Reader (rid) ON DELETE CASCADE,
 FOREIGN KEY (wid) REFERENCES Webtoon (wid) ON DELETE CASCADE
);

-- VIEWS
CREATE VIEW FrequentSubscribedWebtoons (wid)
	AS SELECT S.wid
	FROM Subscribes S GROUP BY S.wid Having COUNT(S.rid) > 3;

CREATE VIEW FrequentLikedWebtoons (wid)
	AS SELECT L.wid
	FROM Likes L GROUP BY L.wid Having COUNT(L.rid) > 3;

CREATE VIEW FrequentLikedCartoonists (cid)
	AS SELECT F.cid
	FROM Favorite_of F GROUP BY F.cid Having COUNT(F.rid) > 3;
	
CREATE TRIGGER WnumberIncrease AFTER UPDATE ON Webtoon
	FOR EACH ROW BEGIN
		DECLARE v_newPrice INTEGER;
		DECLARE v_oldPrice INTEGER;
		
		SELECT price into v_newPrice FROM Webtoon WHERE wid = NEW.wid;
		SELECT price into v_oldPrice FROM Webtoon WHERE wie = OLD.wid;
		IF v_newPrice > 1000 THEN
			Webtoon.price = v_newPrice;
		END IF;
	END;	

---------- Insert database

--- Cartoonist

-- NAVER
INSERT INTO Cartoonist (cid, position, name) VALUES (0, "B", "No Jihyoon");
INSERT INTO Cartoonist (cid, position, name) VALUES (1, "B", "Miti");
INSERT INTO Cartoonist (cid, position, name) VALUES (2, "B", "Ha Ilkwon");
INSERT INTO Cartoonist (cid, position, name) VALUES (3, "B", "Im Inseu");
INSERT INTO Cartoonist (cid, position, name) VALUES (4, "B", "Kim Seongwon");
INSERT INTO Cartoonist (cid, position, name) VALUES (5, "W", "Sini");
INSERT INTO Cartoonist (cid, position, name) VALUES (6, "D", "Hyeono");
INSERT INTO Cartoonist (cid, position, name) VALUES (7, "B", "Joo Homin");
INSERT INTO Cartoonist (cid, position, name) VALUES (8, "B", "Gang Naengi");
INSERT INTO Cartoonist (cid, position, name) VALUES (9, "B", "Jakka");
INSERT INTO Cartoonist (cid, position, name) VALUES (10, "W", "Jang Hui");
INSERT INTO Cartoonist (cid, position, name) VALUES (11, "B", "Won Jumin");
INSERT INTO Cartoonist (cid, position, name) VALUES (12, "W", "Gang Hwanyeong");
INSERT INTO Cartoonist (cid, position, name) VALUES (13, "D", "Kim Hyeona");
INSERT INTO Cartoonist (cid, position, name) VALUES (14, "B", "SIU");
INSERT INTO Cartoonist (cid, position, name) VALUES (15, "B", "Jo Seok");
INSERT INTO Cartoonist (cid, position, name) VALUES (16, "B", "Park Yongje");
INSERT INTO Cartoonist (cid, position, name) VALUES (17, "B", "Kim Serae");
INSERT INTO Cartoonist (cid, position, name) VALUES (18, "W", "Son Jeho");
INSERT INTO Cartoonist (cid, position, name) VALUES (19, "D", "Lee Gwangsu");

-- DAUM
INSERT INTO Cartoonist (cid, position, name) VALUES (20, "B", "Kang Pul");
INSERT INTO Cartoonist (cid, position, name) VALUES (21, "B", "Shim Seunghyeon");
INSERT INTO Cartoonist (cid, position, name) VALUES (22, "B", "Cho Gyeonggyu");
INSERT INTO Cartoonist (cid, position, name) VALUES (23, "B", "Nan Da");
INSERT INTO Cartoonist (cid, position, name) VALUES (24, "B", "Ma Ru");
INSERT INTO Cartoonist (cid, position, name) VALUES (25, "B", "Yoon Taeho");
INSERT INTO Cartoonist (cid, position, name) VALUES (26, "B", "Ma Simel");
INSERT INTO Cartoonist (cid, position, name) VALUES (27, "B", "Yeo Eun");
INSERT INTO Cartoonist (cid, position, name) VALUES (28, "B", "Peter Mon");
INSERT INTO Cartoonist (cid, position, name) VALUES (29, "B", "Crom");
INSERT INTO Cartoonist (cid, position, name) VALUES (30, "B", "EBICHU");
INSERT INTO Cartoonist (cid, position, name) VALUES (31, "B", "Bodam");
INSERT INTO Cartoonist (cid, position, name) VALUES (32, "B", "Bae Hyesu");
INSERT INTO Cartoonist (cid, position, name) VALUES (33, "W", "Neon Bi");
INSERT INTO Cartoonist (cid, position, name) VALUES (34, "D", "Caramel");
INSERT INTO Cartoonist (cid, position, name) VALUES (35, "B", "Bang Soyeon");
INSERT INTO Cartoonist (cid, position, name) VALUES (36, "B", "Son Dalseop");
INSERT INTO Cartoonist (cid, position, name) VALUES (37, "B", "Gold Kiwi Bird");

-- Kakao
INSERT INTO Cartoonist (cid, position, name) VALUES (38, "B", "Snow Cat");
INSERT INTO Cartoonist (cid, position, name) VALUES (39, "B", "Park Gyeongran");
INSERT INTO Cartoonist (cid, position, name) VALUES (40, "B", "Bad Wolf");
INSERT INTO Cartoonist (cid, position, name) VALUES (41, "B", "Lee Narae");
INSERT INTO Cartoonist (cid, position, name) VALUES (42, "B", "Jee Gangmin");
INSERT INTO Cartoonist (cid, position, name) VALUES (43, "W", "Rino");
INSERT INTO Cartoonist (cid, position, name) VALUES (44, "D", "Yoon Seul");
INSERT INTO Cartoonist (cid, position, name) VALUES (45, "W", "Hae Hyeon");
INSERT INTO Cartoonist (cid, position, name) VALUES (46, "D", "Lee Jeya");
INSERT INTO Cartoonist (cid, position, name) VALUES (47, "B", "Song Baek");
INSERT INTO Cartoonist (cid, position, name) VALUES (48, "W", "Seri");
INSERT INTO Cartoonist (cid, position, name) VALUES (49, "D", "Biwan");
INSERT INTO Cartoonist (cid, position, name) VALUES (50, "B", "Kim Myeongmi");
INSERT INTO Cartoonist (cid, position, name) VALUES (51, "B", "Tam-ibu");
INSERT INTO Cartoonist (cid, position, name) VALUES (52, "B", "Ine");
INSERT INTO Cartoonist (cid, position, name) VALUES (53, "D", "Magenta Black");
INSERT INTO Cartoonist (cid, position, name) VALUES (54, "D", "Team Lynch");
INSERT INTO Cartoonist (cid, position, name) VALUES (55, "D", "Kim Legna");
INSERT INTO Cartoonist (cid, position, name) VALUES (56, "W", "Bichyu");
INSERT INTO Cartoonist (cid, position, name) VALUES (57, "W", "whale");
INSERT INTO Cartoonist (cid, position, name) VALUES (58, "D", "Milcha");
INSERT INTO Cartoonist (cid, position, name) VALUES (59, "W", "In A");
INSERT INTO Cartoonist (cid, position, name) VALUES (60, "D", "Jeong Yuna");
INSERT INTO Cartoonist (cid, position, name) VALUES (61, "W", "Son Gyuho");
INSERT INTO Cartoonist (cid, position, name) VALUES (62, "D", "Caribou");
INSERT INTO Cartoonist (cid, position, name) VALUES (63, "W", "DURUFIX");
INSERT INTO Cartoonist (cid, position, name) VALUES (64, "D", "Shin Cheol");
INSERT INTO Cartoonist (cid, position, name) VALUES (65, "B", "Cho Seokho");
INSERT INTO Cartoonist (cid, position, name) VALUES (66, "B", "Yu Gi");
INSERT INTO Cartoonist (cid, position, name) VALUES (67, "D", "Ant Studio");
INSERT INTO Cartoonist (cid, position, name) VALUES (68, "W", "So Sori");

--- Webtoon

-- NAVER
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (101, 'Tattoo', 9.2, 0, 'fantasy', 8.8, 'A bright and cheerful high school girl, HyunJin and a newly opened tattoo shop, Jinwoo, an omnibus that is centered around organic! What a mysterious thing happens to people who come to tattoo ... what will happen?');
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (102, 'Elite Gi-han Nam', 9.1, 700, 'sf-comedy', 2.8, "Twenty-seven years old, ready to take the exam for civil servants! One day I opened my eyes and returned to the past of 16 years ago and became a chord!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (103, 'Chang-nam Kim, a three-stage combined body', 9.5, 300, 'sf', 98.6, "The High School of Sambong Barbershop has returned.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (104, 'Let\'s fight ghost!', 9.9, 0, 'romentic-horror', 4.3, "A giant bout of demons and exorcists!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (105, 'Investigation 9th degree', 9.3, 200, 'mystery-comedy', 81.1, "A self-proclaimed criminal investigator, a nine-tier criminal, And it is a rumor investigation that Jang Ho-jin and Cha Yang are together with a stubborn person.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (106, 'Annasumanara', 9.8, 300, 'drama', 25.1, "Written by Kiyoshi Kiyoshi of the warm sensibility 2010 work.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (107, 'God of Bath', 9.5, 300, 'comedy', 97.8, "The battle begins to become the best bath manager.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (108, 'Aftereffect', 9.1, 200, 'thriller', 63.9, "Aftereffect that appears a little after one accident. From that time on, everything began to twist.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (109, 'About Death', 9.5, 200, 'drama', 98.6, "Boundary of life and death, who is there?");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (110, 'Along with the God', 9.9, 200, 'drama', 98.1, "An ordinary man who is neither bad nor good is Kim Ja-hong In the afterlife, you will receive seven trials for 49 days.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (111, 'Legend of Legend', 8.2, 100, 'action', 6.4, "The legendary legend");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (112, 'University Diary', 10, 0, 'slice of life', 98.1, "Our college diary is so realistic based on extreme realism");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (113, 'Sugar Fruit Candy', 9.7, 0, 'historical-fantasy', 87.3, "Chinese Song dynasty, the cruel and merciless fantasies,Guan Yu-do and Kwang-Yeon leave the journey of fighting off their paws.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (114, 'Jumin Won Horror Movie', 9.8, 0, 'horror', 25.1, "Do not be vigilant until the end!A real reversal horror thriller that goes beyond your imagination!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (115, 'Rough Day', 9.9, 0, 'romance', 12.4, "The opposite manfriend I met in a sudden confession. We ... Why are you dating?");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (116, 'Tower of God', 9.7, 0, 'fantasy', 99.1, "The boy who came to the tower after the girl who was all his own");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (117, 'The Sound of Your Heart', 9.9, 0, 'comedy', 109.1, "The best gag cartoon of honest sticker <sound of heart>");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (118, 'The God of High School', 8.7, 0, 'action', 47.8, "There will be a competition to select the strongest boy among the high school students in the whole country and all over the world.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (119, 'Magician', 9.9, 0, 'fantasy', 6.6, "Last story of the unified continent Kantera.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (120, 'Noblesse', 9.2, 200, 'fantasy', 87.3, "Long sleeper for 820 years. I finally open my eyes to a new world.");

--DAUM
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (201, 'Love Story', 9.5, 100, 'romance', 50.1, "The representative work of Gwanghwool artist is 'Genuine cartoon'. Best hit in 2004");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (202, 'I Love You', 9.6, 200, 'romance', 27, "Genuine third comic. The love story of adults coming out of the fingertips");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (203, 'Papepopo', 9.5, 0, 'romance', 6.2, "Meet me first in the comic world, in the Papepo fever that swept Korea!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (204, 'Omurice Jam Jam', 8.9, 0, 'slice of life', 7.6, "The world of the author Cho Kyung-kyu who eats the best of one meal!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (205, 'Acoustic Life', 9.7, 200, 'slice of life', 12.4, "The story of an al-kwon-gong sympathy life story comedy episode!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (206, 'Pinocchio', 9.7, 200, 'fantasy', 86, "Find the happiness of a boy and the people who want to be happy!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (207, 'Incomplete Life', 9.8, 200, 'drama', 9.2, "Stories of people who have a few numbers to win their lives.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (208, 'Women Employees in Game Company', 9.6, 200, 'slice of life', 10.3, "A game like a game of a female employee");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (209, 'Makeup Story of University Freshmen', 9.6, 200, 'romance', 21.2, "This is the story of Kim Kyung-ae's excited romance + makeup!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (210, 'Carp-king\'s 2030 Life', 8.8, 0, 'comedy', 6.4, "Happy Single Koreans");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (211, 'Brothers Grimm\'s Cruel Fairy Tale', 9.5, 0, 'thriller', 15.6, "Fairy tale stories of beautiful pictures brothers. The brutal secret of the fairy tale is revealed!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (212, 'Ebichu', 9.6, 200, 'comedy', 9.1, "A small (?) Story of Ebuchee who loves Camembert cheese and ice cream");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (213, 'Rooftop Bread', 9.9, 0, 'drama', 12.4, "Our story about the smell of bread from the rooftop bakery that left the company");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (214, 'Cartoonist\'s Private Life', 9.9, 0, 'slice of life', 6.4, "4th year webtoon writer's vigorous privacy");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (215, 'Moss', 9.9, 200, 'thriller', 15.9, "What are the eyes of the people who reject me, and why? The Korean-style cruel thriller has come to an end!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (216, 'Ssanggap Pocha', 10, 0, 'fantasy', 9.3, "Awarded <2017 Korean Cartoon Award> Excellence Prize. Late night, the wonder stalls appeared in strange places. Stories happening there");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (217, 'Employee from Hell', 9.9, 0, 'fantasy-drama', 6.4, "The social adaptation of demons who became human! And those who threaten him. Caramel, a strong new personality neon rain!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (218, 'Night Perfume', 9.9, 0, 'romance', 1.8, "I want you to be constantly desperate and suffering like me.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (219, 'Daily Life of Brother and Sister', 9.1, 0, 'slice of life', 1.2, "The brilliant everyday life of a sibling who could be anywhere in Korea!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (220, 'Happy Even If I Die', 9.8, 0, 'drama', 24.8, "Mr. Yilda's senior manager in charge of the firm. A strange event between the two");

-- Kakao
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (301, 'Ongdongs', 9.9, 200, 'slice of life', 3.9, "I separated from my brother, but cute and pure little cats are still living with me today.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (302, 'Imitation', 9.9, 200, 'romance', 35, "I am a fake but dreams of real");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (303, 'Hide', 9.9, 200, 'action', 4.1, "Living as an ordinary family of ordinary people");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (304, 'Honey Blood', 9.9, 200, 'romance', 26.8, "You smell delicious. May I have a bite?");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (305, 'Everyday Family Goes to Work', 9.9, 100, 'slice of life', 9.2, "My mother is a bus driver, my dad is a taxi driver, my sister is an underground Pearl station employee");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (306, 'The Only Daughter of an Emperor', 10, 200, 'romance', 37.8, "Aria-de-Lergue-lère-Stree-Pregregent");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (307, 'Petit Daughter of an Emperor', 9.9, 0, 'comedy', 7.1, "The cute and irregular stories that were not seen in this book are reborn as a cute SD character.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (308, 'Lion Queen', 10, 200, 'drama', 10.7, "I wanted to work too much.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (309, 'Her Tale of Shim Chong', 10, 200, 'romance', 3.8, "Why did she say that she would give her a substitute?");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (310, 'What\'s Wrong with Secretary Kim', 9.9, 200, 'romance', 63.9, "Masterpieces of heavenly heaven, master pieces made by God");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (311, 'High Schooler Vampire Pi-Mandu Season 1', 9.9, 200, 'drama', 5.2, "Blood-burning vampire High school student Pum-dum is a student who has a bad head and has spent 100 years in high school");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (312, 'Watch Out Your Boyfriend', 9.9, 200, 'romance', 21.1, "Dain and Shi Hyun are the same class, but not lose friend");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (313, 'Born as the Daughter of a King', 9.9, 200, 'romance', 26.9, "Twenty-six, behind the love of simple but beautiful sweetheart Jinsu with a disgusting incident ....");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (314, 'The Reason why she had to go to the Duke', 10, 200, 'romance', 14.3, "The death of a doubt The character of Bing who fell into the novel is a poisonous extinct die");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (315, 'The Abandoned Empress', 10, 200, 'romance', 20.3, "The Castilian Empire boasts a thousand years of history. After Monique, the only daughter of the artist, Aristi la Monica, grows into a preliminary empress by the trust");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (316, 'Youth Market', 9.9, 200, 'drama', 8.1, "If all those who have not been drunk by the age of 29 are taken to the youth market, the story of the world's most dangerous youth begins.");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (317, 'Doctor Tae Soo Choi', 9.9, 200, 'drama', 21.1, "The most end-doctor of a university hospital, a walking tin intern");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (318, 'Girl Shop', 10, 0, 'drama', 9.1, "I bought clothes at the mall and received a model ?!");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (319, 'The Skeleton Soldiers did not keep the Dungeon', 9.9, 0, 'action', 6.4, "In order to protect, I become stronger.\" One nameless monster \"skeleton soldier");
INSERT INTO Webtoon (wid, name, score, price, genre, views, summary) VALUES (320, 'High Schooler Vampire Pi-Mandu Season 2', 9.9, 100, 'drama', 8.8, "After leaving for 100 years, I went to Miyagoro");

--- Webtoon_Worth
/*	
	F: views<3 OR score<6
	D: views<6 OR score<8
	C: views<8 OR score<9
	B: views<10 OR score<9.5
	A: Otherwise 	
*/
INSERT Webtoon_Worth (views, score, worth) VALUES (1.2, 9.1, 'F');
INSERT Webtoon_Worth (views, score, worth) VALUES (1.8, 9.9, 'F');
INSERT Webtoon_Worth (views, score, worth) VALUES (2.8, 9.1, 'F');
INSERT Webtoon_Worth (views, score, worth) VALUES (3.8, 10, 'D');
INSERT Webtoon_Worth (views, score, worth) VALUES (3.9, 9.9, 'D');
INSERT Webtoon_Worth (views, score, worth) VALUES (4.1, 9.9, 'D');
INSERT Webtoon_Worth (views, score, worth) VALUES (4.3, 9.9, 'D');
INSERT Webtoon_Worth (views, score, worth) VALUES (5.2, 9.9, 'D');
INSERT Webtoon_Worth (views, score, worth) VALUES (6.2, 9.5, 'C');
INSERT Webtoon_Worth (views, score, worth) VALUES (6.4, 8.2, 'C');
INSERT Webtoon_Worth (views, score, worth) VALUES (6.4, 8.8, 'C');
INSERT Webtoon_Worth (views, score, worth) VALUES (6.4, 9.9, 'C');
INSERT Webtoon_Worth (views, score, worth) VALUES (6.6, 9.9, 'C');
INSERT Webtoon_Worth (views, score, worth) VALUES (7.1, 9.9, 'C');
INSERT Webtoon_Worth (views, score, worth) VALUES (7.6, 8.9, 'C');
INSERT Webtoon_Worth (views, score, worth) VALUES (8.1, 9.9, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (8.8, 9.2, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (8.8, 9.9, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (9.1, 9.6, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (9.1, 10, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (9.2, 9.8, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (9.2, 9.9, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (9.3, 10, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (10.3, 9.6, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (10.7, 10, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (12.4, 9.7, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (12.4, 9.9, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (14.3, 10, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (15.6, 9.5, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (15.9, 9.9, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (20.3, 10, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (21.1, 9.9, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (21.2, 9.6, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (24.8, 9.8, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (25.1, 9.8, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (26.8, 9.9, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (26.9, 9.9, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (27, 9.6, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (35, 9.9, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (37.8, 10, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (47.8, 8.7, 'C');
INSERT Webtoon_Worth (views, score, worth) VALUES (50.1, 9.5, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (63.9, 9.1, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (63.9, 9.9, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (81.1, 9.3, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (86, 9.7, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (87.3, 9.2, 'B');
INSERT Webtoon_Worth (views, score, worth) VALUES (87.3, 9.7, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (97.8, 9.5, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (98.1, 9.9, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (98.1, 10, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (98.6, 9.5, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (99.1, 9.7, 'A');
INSERT Webtoon_Worth (views, score, worth) VALUES (109.1, 9.9, 'A');


--- Reader
INSERT INTO Reader (rid, age, gender, ccode, uname, pw) VALUES (0, 22, "F", "KR", "daye98", "dmsekdP");
INSERT INTO Reader (rid, age, gender, ccode, uname, pw) VALUES (1, 22, "F", "KR", "kyounga98", "wka1234");
INSERT INTO Reader (rid, age, gender, ccode, uname, pw) VALUES (2, 25, "M", "KR", "cts", "ctsctscts");
INSERT INTO Reader (rid, age, gender, ccode, uname, pw) VALUES (3, 17, "M", "US", "lucky7", "77777");
INSERT INTO Reader (rid, age, gender, ccode, uname, pw) VALUES (4, 27, "F", "US", "mary123", "12345678");
INSERT INTO Reader (rid, age, gender, ccode, uname, pw) VALUES (5, 10, "M", "RU", "catlover", "1234");
INSERT INTO Reader (rid, age, gender, ccode, uname, pw) VALUES (6, 33, "F", "RU", "doghater", "9999999");
INSERT INTO Reader (rid, age, gender, ccode, uname, pw) VALUES (7, 40, "F", "ID", "historyteacher", "12345");
INSERT INTO Reader (rid, age, gender, ccode, uname, pw) VALUES (8, 20, "F", "TR", "hello1004", "1004");
INSERT INTO Reader (rid, age, gender, ccode, uname, pw) VALUES (9, 19, "M", "CA", "edywkacts", "webtoons!");

--- Reader_age_pay
/*
	Age	  Pay Amount
	1 - 4: 0 Won
	5 - 15 : 6000 Won
	16 - 25: 10000 Won
	26 - 40: 50000 Won
	41 - 60: 100000 Won
	61 - 100: 50000 Won
*/
INSERT Reader_age_pay (age, payamount) VALUES (1, 0);
INSERT Reader_age_pay (age, payamount) VALUES (2, 0);
INSERT Reader_age_pay (age, payamount) VALUES (3, 0);
INSERT Reader_age_pay (age, payamount) VALUES (4, 0);
INSERT Reader_age_pay (age, payamount) VALUES (5, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (6, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (7, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (8, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (9, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (10, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (11, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (12, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (13, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (14, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (15, 6000);
INSERT Reader_age_pay (age, payamount) VALUES (16, 10000);
INSERT Reader_age_pay (age, payamount) VALUES (17, 10000);
INSERT Reader_age_pay (age, payamount) VALUES (18, 10000);
INSERT Reader_age_pay (age, payamount) VALUES (19, 10000);
INSERT Reader_age_pay (age, payamount) VALUES (20, 10000);
INSERT Reader_age_pay (age, payamount) VALUES (21, 10000);
INSERT Reader_age_pay (age, payamount) VALUES (22, 10000);
INSERT Reader_age_pay (age, payamount) VALUES (23, 10000);
INSERT Reader_age_pay (age, payamount) VALUES (24, 10000);
INSERT Reader_age_pay (age, payamount) VALUES (25, 10000);
INSERT Reader_age_pay (age, payamount) VALUES (26, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (27, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (28, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (29, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (30, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (31, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (32, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (33, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (34, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (35, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (36, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (37, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (38, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (39, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (40, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (41, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (42, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (43, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (44, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (45, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (46, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (47, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (48, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (49, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (50, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (51, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (52, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (53, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (54, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (55, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (56, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (57, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (58, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (59, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (60, 100000);
INSERT Reader_age_pay (age, payamount) VALUES (61, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (62, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (63, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (64, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (65, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (66, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (67, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (68, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (69, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (70, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (71, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (72, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (73, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (74, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (75, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (76, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (77, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (78, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (79, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (80, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (81, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (82, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (83, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (84, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (85, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (86, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (87, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (88, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (89, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (90, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (91, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (92, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (93, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (94, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (95, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (96, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (97, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (98, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (99, 50000);
INSERT Reader_age_pay (age, payamount) VALUES (100, 50000);

--- Reader_Country
INSERT Reader_Country (ccode, country) VALUES ('ZW', 'Zimbabwe');
INSERT Reader_Country (ccode, country) VALUES ('ZM', 'Zambia');
INSERT Reader_Country (ccode, country) VALUES ('ZA', 'South Africa');
INSERT Reader_Country (ccode, country) VALUES ('YT', 'Mayotte');
INSERT Reader_Country (ccode, country) VALUES ('YE', 'Yemen');
INSERT Reader_Country (ccode, country) VALUES ('WS', 'Samoa');
INSERT Reader_Country (ccode, country) VALUES ('WF', 'Wallis and Futuna');
INSERT Reader_Country (ccode, country) VALUES ('VU', 'Vanuatu');
INSERT Reader_Country (ccode, country) VALUES ('VN', 'Viet Nam');
INSERT Reader_Country (ccode, country) VALUES ('VI', 'Virgin Islands (U.S.)');
INSERT Reader_Country (ccode, country) VALUES ('VG', 'Virgin Islands (British)');
INSERT Reader_Country (ccode, country) VALUES ('VE', 'Venezuela (Bolivarian Republic of)');
INSERT Reader_Country (ccode, country) VALUES ('VC', 'Saint Vincent and the Grenadines');
INSERT Reader_Country (ccode, country) VALUES ('VA', 'Holy See (the)');
INSERT Reader_Country (ccode, country) VALUES ('UZ', 'Uzbekistan');
INSERT Reader_Country (ccode, country) VALUES ('UY', 'Uruguay');
INSERT Reader_Country (ccode, country) VALUES ('US', 'United States of America (the)');
INSERT Reader_Country (ccode, country) VALUES ('UM', 'United States Minor Outlying Islands (the)');
INSERT Reader_Country (ccode, country) VALUES ('UG', 'Uganda');
INSERT Reader_Country (ccode, country) VALUES ('UA', 'Ukraine');
INSERT Reader_Country (ccode, country) VALUES ('TZ', 'Tanzania, United Republic of');
INSERT Reader_Country (ccode, country) VALUES ('TW', 'Taiwan (Province of China)');
INSERT Reader_Country (ccode, country) VALUES ('TV', 'Tuvalu');
INSERT Reader_Country (ccode, country) VALUES ('TT', 'Trinidad and Tobago');
INSERT Reader_Country (ccode, country) VALUES ('TR', 'Turkey');
INSERT Reader_Country (ccode, country) VALUES ('TO', 'Tonga');
INSERT Reader_Country (ccode, country) VALUES ('TN', 'Tunisia');
INSERT Reader_Country (ccode, country) VALUES ('TM', 'Turkmenistan');
INSERT Reader_Country (ccode, country) VALUES ('TL', 'Timor-Leste');
INSERT Reader_Country (ccode, country) VALUES ('TK', 'Tokelau');
INSERT Reader_Country (ccode, country) VALUES ('TJ', 'Tajikistan');
INSERT Reader_Country (ccode, country) VALUES ('TH', 'Thailand');
INSERT Reader_Country (ccode, country) VALUES ('TG', 'Togo');
INSERT Reader_Country (ccode, country) VALUES ('TF', 'French Southern Territories (the)');
INSERT Reader_Country (ccode, country) VALUES ('TD', 'Chad');
INSERT Reader_Country (ccode, country) VALUES ('TC', 'Turks and Caicos Islands (the)');
INSERT Reader_Country (ccode, country) VALUES ('SZ', 'Eswatini');
INSERT Reader_Country (ccode, country) VALUES ('SY', 'Syrian Arab Republic');
INSERT Reader_Country (ccode, country) VALUES ('SX', 'Sint Maarten (Dutch part)');
INSERT Reader_Country (ccode, country) VALUES ('SV', 'El Salvador');
INSERT Reader_Country (ccode, country) VALUES ('ST', 'Sao Tome and Principe');
INSERT Reader_Country (ccode, country) VALUES ('SS', 'South Sudan');
INSERT Reader_Country (ccode, country) VALUES ('SR', 'Suriname');
INSERT Reader_Country (ccode, country) VALUES ('SO', 'Somalia');
INSERT Reader_Country (ccode, country) VALUES ('SN', 'Senegal');
INSERT Reader_Country (ccode, country) VALUES ('SM', 'San Marino');
INSERT Reader_Country (ccode, country) VALUES ('SL', 'Sierra Leone');
INSERT Reader_Country (ccode, country) VALUES ('SK', 'Slovakia');
INSERT Reader_Country (ccode, country) VALUES ('SJ', 'Svalbard and Jan Mayen');
INSERT Reader_Country (ccode, country) VALUES ('SI', 'Slovenia');
INSERT Reader_Country (ccode, country) VALUES ('SH', 'Saint Helena, Ascension and Tristan da Cunha');
INSERT Reader_Country (ccode, country) VALUES ('SG', 'Singapore');
INSERT Reader_Country (ccode, country) VALUES ('SE', 'Sweden');
INSERT Reader_Country (ccode, country) VALUES ('SD', 'Sudan (the)');
INSERT Reader_Country (ccode, country) VALUES ('SC', 'Seychelles');
INSERT Reader_Country (ccode, country) VALUES ('SB', 'Solomon Islands');
INSERT Reader_Country (ccode, country) VALUES ('SA', 'Saudi Arabia');
INSERT Reader_Country (ccode, country) VALUES ('RW', 'Rwanda');
INSERT Reader_Country (ccode, country) VALUES ('RU', 'Russian Federation (the)');
INSERT Reader_Country (ccode, country) VALUES ('RS', 'Serbia');
INSERT Reader_Country (ccode, country) VALUES ('RO', 'Romania');
INSERT Reader_Country (ccode, country) VALUES ('RE', 'Reunion');
INSERT Reader_Country (ccode, country) VALUES ('QA', 'Qatar');
INSERT Reader_Country (ccode, country) VALUES ('PY', 'Paraguay');
INSERT Reader_Country (ccode, country) VALUES ('PW', 'Palau');
INSERT Reader_Country (ccode, country) VALUES ('PT', 'Portugal');
INSERT Reader_Country (ccode, country) VALUES ('PS', 'Palestine, State of');
INSERT Reader_Country (ccode, country) VALUES ('PR', 'Puerto Rico');
INSERT Reader_Country (ccode, country) VALUES ('PN', 'Pitcairn');
INSERT Reader_Country (ccode, country) VALUES ('PM', 'Saint Pierre and Miquelon');
INSERT Reader_Country (ccode, country) VALUES ('PL', 'Poland');
INSERT Reader_Country (ccode, country) VALUES ('PK', 'Pakistan');
INSERT Reader_Country (ccode, country) VALUES ('PH', 'Philippines (the)');
INSERT Reader_Country (ccode, country) VALUES ('PG', 'Papua New Guinea');
INSERT Reader_Country (ccode, country) VALUES ('PF', 'French Polynesia');
INSERT Reader_Country (ccode, country) VALUES ('PE', 'Peru');
INSERT Reader_Country (ccode, country) VALUES ('PA', 'Panama');
INSERT Reader_Country (ccode, country) VALUES ('OM', 'Oman');
INSERT Reader_Country (ccode, country) VALUES ('NZ', 'New Zealand');
INSERT Reader_Country (ccode, country) VALUES ('NU', 'Niue');
INSERT Reader_Country (ccode, country) VALUES ('NR', 'Nauru');
INSERT Reader_Country (ccode, country) VALUES ('NP', 'Nepal');
INSERT Reader_Country (ccode, country) VALUES ('NO', 'Norway');
INSERT Reader_Country (ccode, country) VALUES ('NL', 'Netherlands (the)');
INSERT Reader_Country (ccode, country) VALUES ('NI', 'Nicaragua');
INSERT Reader_Country (ccode, country) VALUES ('NG', 'Nigeria');
INSERT Reader_Country (ccode, country) VALUES ('NF', 'Norfolk Island');
INSERT Reader_Country (ccode, country) VALUES ('NE', 'Niger (the)');
INSERT Reader_Country (ccode, country) VALUES ('NC', 'New Caledonia');
INSERT Reader_Country (ccode, country) VALUES ('NA', 'Namibia');
INSERT Reader_Country (ccode, country) VALUES ('MZ', 'Mozambique');
INSERT Reader_Country (ccode, country) VALUES ('MY', 'Malaysia');
INSERT Reader_Country (ccode, country) VALUES ('MX', 'Mexico');
INSERT Reader_Country (ccode, country) VALUES ('MW', 'Malawi');
INSERT Reader_Country (ccode, country) VALUES ('MV', 'Maldives');
INSERT Reader_Country (ccode, country) VALUES ('MU', 'Mauritius');
INSERT Reader_Country (ccode, country) VALUES ('MT', 'Malta');
INSERT Reader_Country (ccode, country) VALUES ('MS', 'Montserrat');
INSERT Reader_Country (ccode, country) VALUES ('MR', 'Mauritania');
INSERT Reader_Country (ccode, country) VALUES ('MQ', 'Martinique');
INSERT Reader_Country (ccode, country) VALUES ('MP', 'Northern Mariana Islands (the)');
INSERT Reader_Country (ccode, country) VALUES ('MO', 'Macao');
INSERT Reader_Country (ccode, country) VALUES ('MN', 'Mongolia');
INSERT Reader_Country (ccode, country) VALUES ('MM', 'Myanmar');
INSERT Reader_Country (ccode, country) VALUES ('ML', 'Mali');
INSERT Reader_Country (ccode, country) VALUES ('MK', 'Macedonia (the former Yugoslav Republic of)');
INSERT Reader_Country (ccode, country) VALUES ('MH', 'Marshall Islands (the)');
INSERT Reader_Country (ccode, country) VALUES ('MG', 'Madagascar');
INSERT Reader_Country (ccode, country) VALUES ('MF', 'Saint Martin (French part)');
INSERT Reader_Country (ccode, country) VALUES ('ME', 'Montenegro');
INSERT Reader_Country (ccode, country) VALUES ('MD', 'Moldova (the Republic of)');
INSERT Reader_Country (ccode, country) VALUES ('MC', 'Monaco');
INSERT Reader_Country (ccode, country) VALUES ('MA', 'Morocco');
INSERT Reader_Country (ccode, country) VALUES ('LY', 'Libya');
INSERT Reader_Country (ccode, country) VALUES ('LV', 'Latvia');
INSERT Reader_Country (ccode, country) VALUES ('LU', 'Luxembourg');
INSERT Reader_Country (ccode, country) VALUES ('LT', 'Lithuania');
INSERT Reader_Country (ccode, country) VALUES ('LS', 'Lesotho');
INSERT Reader_Country (ccode, country) VALUES ('LR', 'Liberia');
INSERT Reader_Country (ccode, country) VALUES ('LK', 'Sri Lanka');
INSERT Reader_Country (ccode, country) VALUES ('LI', 'Liechtenstein');
INSERT Reader_Country (ccode, country) VALUES ('LC', 'Saint Lucia');
INSERT Reader_Country (ccode, country) VALUES ('LB', 'Lebanon');
INSERT Reader_Country (ccode, country) VALUES ('LA', 'Lao People\'s Democratic Republic (the)');
INSERT Reader_Country (ccode, country) VALUES ('KZ', 'Kazakhstan');
INSERT Reader_Country (ccode, country) VALUES ('KY', 'Cayman Islands (the)');
INSERT Reader_Country (ccode, country) VALUES ('KW', 'Kuwait');
INSERT Reader_Country (ccode, country) VALUES ('KR', 'Korea (the Republic of)');
INSERT Reader_Country (ccode, country) VALUES ('KP', 'Korea (the Democratic People\'s Republic of)');
INSERT Reader_Country (ccode, country) VALUES ('KN', 'Saint Kitts and Nevis');
INSERT Reader_Country (ccode, country) VALUES ('KM', 'Comoros (the)');
INSERT Reader_Country (ccode, country) VALUES ('KI', 'Kiribati');
INSERT Reader_Country (ccode, country) VALUES ('KH', 'Cambodia');
INSERT Reader_Country (ccode, country) VALUES ('KG', 'Kyrgyzstan');
INSERT Reader_Country (ccode, country) VALUES ('KE', 'Kenya');
INSERT Reader_Country (ccode, country) VALUES ('JP', 'Japan');
INSERT Reader_Country (ccode, country) VALUES ('JO', 'Jordan');
INSERT Reader_Country (ccode, country) VALUES ('JM', 'Jamaica');
INSERT Reader_Country (ccode, country) VALUES ('JE', 'Jersey');
INSERT Reader_Country (ccode, country) VALUES ('IT', 'Italy');
INSERT Reader_Country (ccode, country) VALUES ('IS', 'Iceland');
INSERT Reader_Country (ccode, country) VALUES ('IR', 'Iran (Islamic Republic of)');
INSERT Reader_Country (ccode, country) VALUES ('IQ', 'Iraq');
INSERT Reader_Country (ccode, country) VALUES ('IO', 'British Indian Ocean Territory (the)');
INSERT Reader_Country (ccode, country) VALUES ('IN', 'India');
INSERT Reader_Country (ccode, country) VALUES ('IM', 'Isle of Man');
INSERT Reader_Country (ccode, country) VALUES ('IL', 'Israel');
INSERT Reader_Country (ccode, country) VALUES ('IE', 'Ireland');
INSERT Reader_Country (ccode, country) VALUES ('ID', 'Indonesia');
INSERT Reader_Country (ccode, country) VALUES ('HU', 'Hungary');
INSERT Reader_Country (ccode, country) VALUES ('HT', 'Haiti');
INSERT Reader_Country (ccode, country) VALUES ('HR', 'Croatia');
INSERT Reader_Country (ccode, country) VALUES ('HN', 'Honduras');
INSERT Reader_Country (ccode, country) VALUES ('HM', 'Heard Island and McDonald Islands');
INSERT Reader_Country (ccode, country) VALUES ('HK', 'Hong Kong');
INSERT Reader_Country (ccode, country) VALUES ('GY', 'Guyana');
INSERT Reader_Country (ccode, country) VALUES ('GW', 'Guinea-Bissau');
INSERT Reader_Country (ccode, country) VALUES ('GU', 'Guam');
INSERT Reader_Country (ccode, country) VALUES ('GT', 'Guatemala');
INSERT Reader_Country (ccode, country) VALUES ('GS', 'South Georgia and the South Sandwich Islands');
INSERT Reader_Country (ccode, country) VALUES ('GR', 'Greece');
INSERT Reader_Country (ccode, country) VALUES ('GQ', 'Equatorial Guinea');
INSERT Reader_Country (ccode, country) VALUES ('GP', 'Guadeloupe');
INSERT Reader_Country (ccode, country) VALUES ('GN', 'Guinea');
INSERT Reader_Country (ccode, country) VALUES ('GM', 'Gambia (the)');
INSERT Reader_Country (ccode, country) VALUES ('GL', 'Greenland');
INSERT Reader_Country (ccode, country) VALUES ('GI', 'Gibraltar');
INSERT Reader_Country (ccode, country) VALUES ('GH', 'Ghana');
INSERT Reader_Country (ccode, country) VALUES ('GG', 'Guernsey');
INSERT Reader_Country (ccode, country) VALUES ('GF', 'French Guiana');
INSERT Reader_Country (ccode, country) VALUES ('GE', 'Georgia');
INSERT Reader_Country (ccode, country) VALUES ('GD', 'Grenada');
INSERT Reader_Country (ccode, country) VALUES ('GB', 'United Kingdom of Great Britain and Northern Ireland (the)');
INSERT Reader_Country (ccode, country) VALUES ('GA', 'Gabon');
INSERT Reader_Country (ccode, country) VALUES ('FR', 'France');
INSERT Reader_Country (ccode, country) VALUES ('FO', 'Faroe Islands (the)');
INSERT Reader_Country (ccode, country) VALUES ('FM', 'Micronesia (Federated States of)');
INSERT Reader_Country (ccode, country) VALUES ('FK', 'Falkland Islands (the) [Malvinas]');
INSERT Reader_Country (ccode, country) VALUES ('FJ', 'Fiji');
INSERT Reader_Country (ccode, country) VALUES ('FI', 'Finland');
INSERT Reader_Country (ccode, country) VALUES ('ET', 'Ethiopia');
INSERT Reader_Country (ccode, country) VALUES ('ES', 'Spain');
INSERT Reader_Country (ccode, country) VALUES ('ER', 'Eritrea');
INSERT Reader_Country (ccode, country) VALUES ('EH', 'Western Sahara');
INSERT Reader_Country (ccode, country) VALUES ('EG', 'Egypt');
INSERT Reader_Country (ccode, country) VALUES ('EE', 'Estonia');
INSERT Reader_Country (ccode, country) VALUES ('EC', 'Ecuador');
INSERT Reader_Country (ccode, country) VALUES ('DZ', 'Algeria');
INSERT Reader_Country (ccode, country) VALUES ('DO', 'Dominican Republic (the)');
INSERT Reader_Country (ccode, country) VALUES ('DM', 'Dominica');
INSERT Reader_Country (ccode, country) VALUES ('DK', 'Denmark');
INSERT Reader_Country (ccode, country) VALUES ('DJ', 'Djibouti');
INSERT Reader_Country (ccode, country) VALUES ('DE', 'Germany');
INSERT Reader_Country (ccode, country) VALUES ('CZ', 'Czechia');
INSERT Reader_Country (ccode, country) VALUES ('CY', 'Cyprus');
INSERT Reader_Country (ccode, country) VALUES ('CX', 'Christmas Island');
INSERT Reader_Country (ccode, country) VALUES ('CW', 'Curacao');
INSERT Reader_Country (ccode, country) VALUES ('CV', 'Cabo Verde');
INSERT Reader_Country (ccode, country) VALUES ('CU', 'Cuba');
INSERT Reader_Country (ccode, country) VALUES ('CR', 'Costa Rica');
INSERT Reader_Country (ccode, country) VALUES ('CO', 'Colombia');
INSERT Reader_Country (ccode, country) VALUES ('CN', 'China');
INSERT Reader_Country (ccode, country) VALUES ('CM', 'Cameroon');
INSERT Reader_Country (ccode, country) VALUES ('CL', 'Chile');
INSERT Reader_Country (ccode, country) VALUES ('CK', 'Cook Islands (the)');
INSERT Reader_Country (ccode, country) VALUES ('CI', 'Cote d\'Ivoire');
INSERT Reader_Country (ccode, country) VALUES ('CH', 'Switzerland');
INSERT Reader_Country (ccode, country) VALUES ('CG', 'Congo (the)');
INSERT Reader_Country (ccode, country) VALUES ('CF', 'Central African Republic (the)');
INSERT Reader_Country (ccode, country) VALUES ('CD', 'Congo (the Democratic Republic of the)');
INSERT Reader_Country (ccode, country) VALUES ('CC', 'Cocos (Keeling) Islands (the)');
INSERT Reader_Country (ccode, country) VALUES ('CA', 'Canada');
INSERT Reader_Country (ccode, country) VALUES ('BZ', 'Belize');
INSERT Reader_Country (ccode, country) VALUES ('BY', 'Belarus');
INSERT Reader_Country (ccode, country) VALUES ('BW', 'Botswana');
INSERT Reader_Country (ccode, country) VALUES ('BV', 'Bouvet Island');
INSERT Reader_Country (ccode, country) VALUES ('BT', 'Bhutan');
INSERT Reader_Country (ccode, country) VALUES ('BS', 'Bahamas (the)');
INSERT Reader_Country (ccode, country) VALUES ('BR', 'Brazil');
INSERT Reader_Country (ccode, country) VALUES ('BQ', 'Bonaire, Sint Eustatius and Saba');
INSERT Reader_Country (ccode, country) VALUES ('BO', 'Bolivia (Plurinational State of)');
INSERT Reader_Country (ccode, country) VALUES ('BN', 'Brunei Darussalam');
INSERT Reader_Country (ccode, country) VALUES ('BM', 'Bermuda');
INSERT Reader_Country (ccode, country) VALUES ('BL', 'Saint Barthelemy');
INSERT Reader_Country (ccode, country) VALUES ('BJ', 'Benin');
INSERT Reader_Country (ccode, country) VALUES ('BI', 'Burundi');
INSERT Reader_Country (ccode, country) VALUES ('BH', 'Bahrain');
INSERT Reader_Country (ccode, country) VALUES ('BG', 'Bulgaria');
INSERT Reader_Country (ccode, country) VALUES ('BF', 'Burkina Faso');
INSERT Reader_Country (ccode, country) VALUES ('BE', 'Belgium');
INSERT Reader_Country (ccode, country) VALUES ('BD', 'Bangladesh');
INSERT Reader_Country (ccode, country) VALUES ('BB', 'Barbados');
INSERT Reader_Country (ccode, country) VALUES ('BA', 'Bosnia and Herzegovina');
INSERT Reader_Country (ccode, country) VALUES ('AZ', 'Azerbaijan');
INSERT Reader_Country (ccode, country) VALUES ('AX', 'Aland Islands');
INSERT Reader_Country (ccode, country) VALUES ('AW', 'Aruba');
INSERT Reader_Country (ccode, country) VALUES ('AU', 'Australia');
INSERT Reader_Country (ccode, country) VALUES ('AT', 'Austria');
INSERT Reader_Country (ccode, country) VALUES ('AS', 'American Samoa');
INSERT Reader_Country (ccode, country) VALUES ('AR', 'Argentina');
INSERT Reader_Country (ccode, country) VALUES ('AQ', 'Antarctica');
INSERT Reader_Country (ccode, country) VALUES ('AO', 'Angola');
INSERT Reader_Country (ccode, country) VALUES ('AM', 'Armenia');
INSERT Reader_Country (ccode, country) VALUES ('AL', 'Albania');
INSERT Reader_Country (ccode, country) VALUES ('AI', 'Anguilla');
INSERT Reader_Country (ccode, country) VALUES ('AG', 'Antigua and Barbuda');
INSERT Reader_Country (ccode, country) VALUES ('AF', 'Afghanistan');
INSERT Reader_Country (ccode, country) VALUES ('AE', 'United Arab Emirates (the)');
INSERT Reader_Country (ccode, country) VALUES ('AD', 'Andorra');

--- Team

-- NAVER TEAM
INSERT Team (tid, name, wnumber) VALUES (0, "No Jihyoon", 1);
INSERT Team (tid, name, wnumber) VALUES (1, "Miti", 1);
INSERT Team (tid, name, wnumber) VALUES (2, "Ha Ilkwon", 3);
INSERT Team (tid, name, wnumber) VALUES (3, "Im Inseu", 1);
INSERT Team (tid, name, wnumber) VALUES (4, "Kim Seongwon", 2);
INSERT Team (tid, name, wnumber) VALUES (5, "Sini, Hyeono", 1);
INSERT Team (tid, name, wnumber) VALUES (6, "Joo Homin", 1);
INSERT Team (tid, name, wnumber) VALUES (7, "Gang Naengi", 1); 
INSERT Team (tid, name, wnumber) VALUES (8, "Jakka", 1);
INSERT Team (tid, name, wnumber) VALUES (9, "Jang Hui, Joo Homin", 1);
INSERT Team (tid, name, wnumber) VALUES (10, "Won Jumin", 1);
INSERT Team (tid, name, wnumber) VALUES (11, "Gang Hwanyeong, Kim Hyeona", 1);
INSERT Team (tid, name, wnumber) VALUES (12, "SIU", 1);
INSERT Team (tid, name, wnumber) VALUES (13, "Jo Seok", 1);
INSERT Team (tid, name, wnumber) VALUES (14, "Park Yongje", 1);
INSERT Team (tid, name, wnumber) VALUES (15, "Kim Serae", 1);
INSERT Team (tid, name, wnumber) VALUES (16, "Son Jeho, Lee Gwangsu", 1);

-- DAUM TEAM
INSERT Team (tid, name, wnumber) VALUES (17, "Kang Pul", 2);
INSERT Team (tid, name, wnumber) VALUES (18, "Shim Seunghyeon", 1);
INSERT Team (tid, name, wnumber) VALUES (19, "Cho Gyeonggyu", 1);
INSERT Team (tid, name, wnumber) VALUES (20, "Nan Da", 1);
INSERT Team (tid, name, wnumber) VALUES (21, "Ma Ru", 1);
INSERT Team (tid, name, wnumber) VALUES (22, "Ma Simel", 2);
INSERT Team (tid, name, wnumber) VALUES (23, "Yoon Taeho", 2);
INSERT Team (tid, name, wnumber) VALUES (24, "Yeo Eun", 1);
INSERT Team (tid, name, wnumber) VALUES (25, "Peter Mon", 1);
INSERT Team (tid, name, wnumber) VALUES (26, "Crom", 1);
INSERT Team (tid, name, wnumber) VALUES (27, "EBICHU", 1);
INSERT Team (tid, name, wnumber) VALUES (28, "Bodam", 1);
INSERT Team (tid, name, wnumber) VALUES (29, "Bae Hyesu", 1);
INSERT Team (tid, name, wnumber) VALUES (30, "Neon Bi, Caramel", 1);
INSERT Team (tid, name, wnumber) VALUES (31, "Bang Soyeon", 1);
INSERT Team (tid, name, wnumber) VALUES (32, "Son Dalseop", 1);
INSERT Team (tid, name, wnumber) VALUES (33, "Gold Kiwi Bird", 1);

-- KAKAO TEAM
INSERT Team (tid, name, wnumber) VALUES (34, "Snow Cat", 1);
INSERT Team (tid, name, wnumber) VALUES (35, "Park Gyeongran", 1);
INSERT Team (tid, name, wnumber) VALUES (36, "Bad Wolf", 1);
INSERT Team (tid, name, wnumber) VALUES (37, "Lee Narae", 1);
INSERT Team (tid, name, wnumber) VALUES (38, "Jee Gangmin", 1);
INSERT Team (tid, name, wnumber) VALUES (39, "Rino, Yoon Seul", 1);
INSERT Team (tid, name, wnumber) VALUES (40, "Hae Hyeon, Lee Jeya", 1);
INSERT Team (tid, name, wnumber) VALUES (41, "Song Baek", 1);
INSERT Team (tid, name, wnumber) VALUES (42, "Seri, Biwan", 1);
INSERT Team (tid, name, wnumber) VALUES (43, "Kim Myeongmi", 1);
INSERT Team (tid, name, wnumber) VALUES (44, "Tam-ibu", 2);
INSERT Team (tid, name, wnumber) VALUES (45, "Ine", 1);
INSERT Team (tid, name, wnumber) VALUES (46, "Magenta Black, Team Lynch, Kim Legna, Lee Jeya, Bichyu", 1);
INSERT Team (tid, name, wnumber) VALUES (47, "whale, Milcha", 1);
INSERT Team (tid, name, wnumber) VALUES (48, "In A, Jeong Yuna", 1);
INSERT Team (tid, name, wnumber) VALUES (49, "Son Gyuho, Caribou", 1);
INSERT Team (tid, name, wnumber) VALUES (50, "DURUFIX, Shin Cheol, Cho Seokho", 1);
INSERT Team (tid, name, wnumber) VALUES (51, "Yu Gi", 1);
INSERT Team (tid, name, wnumber) VALUES (52, "Ant Studio, So Sori", 1);

--- Team_Pay
INSERT INTO Team_Pay (wnumber, wage) VALUES (1, 300);
INSERT INTO Team_Pay (wnumber, wage) VALUES (2, 500);
INSERT INTO Team_Pay (wnumber, wage) VALUES (3, 700);

--- Company
INSERT INTO Company (comid, since, name) VALUES (1, 1999, "NAVER");
INSERT INTO Company (comid, since, name) VALUES (2, 1995, "DAUM");
INSERT INTO Company (comid, since, name) VALUES (3, 2010, "KAKAO");

--- Content
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (0, 'Above 15', 'Let\'s fight ghost!', 26400, 'TV series');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (1, 'Above 10', 'Annasumanara', 35000, 'Play');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (2, 'Above 12', 'Along with the Gods: The Last 49 Days', 2500, 'Movie');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (3, 'Above 12', 'Along With the Gods: The Two Worlds', 1800, 'Movie');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (4, 'Above 8', 'Along with the Gods: This World', 90000, 'Musical');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (5, 'Above 8', 'Along with the Gods: Next World', 90000, 'Musical');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (6, 'Above 12', 'The Sound of Your Heart', 10920, 'Cartoon');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (7, 'All', 'The Sound of Your Heart', 0, 'TV series');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (8, 'All', 'The God of High School', 0, 'Game');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (9, 'All', '2019 The God of High School', 0, 'Game');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (10, 'Above 12', 'Hello, Schoolgirl', 1000, 'Movie');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (11, 'Above 15', 'I Love You', 1000, 'Movie');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (12, 'Above 15', 'Incomplete Life', 36300, 'TV series');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (13, 'Above 15', 'Women Employees in Game Company', 4950, 'TV series');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (14, 'Above 19', 'Moss', 1200, 'Movie');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (15, 'Above 15', 'Feel Good to Die', 28050, 'TV series');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (16, 'All', 'The Only Daughter of an Emperor', 58000, 'Novel');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (17, 'All', 'Born as the Daughter of a King', 200, 'Webtoon');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (18, 'All', 'Petit Daughter of an Emperor', 0, 'Webtoon');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (19, 'Above 15', 'What\'s Wrong with Secretary Kim', 26400, 'TV series');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (20, 'All', 'What\'s Wrong with Secretary Kim', 22000, 'Novel');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (21, 'All', 'High Schooler Vampire Pi-Mandu Season 1', 200, 'Webtoon');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (22, 'All', 'High Schooler Vampire Pi-Mandu Season 2', 200, 'Webtoon');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (23, 'All', 'Born as the Daughter of a King', 75000, 'Novel');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (24, 'All', 'The Reason why she had to go to the Duke', 24000, 'Novel');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (25, 'All', 'The Abandoned Empress', 55000, 'Novel');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (26, 'All', 'Doctor Tae Soo Choi', 323600, 'Novel');
INSERT INTO Content (content_id, age_range, title, price, ctype) VALUES (27, 'All', 'The Skeleton Soldiers did not keep the Dungeon', 39300, 'Novel');

--- Duration
INSERT Duration (did, since, _to) VALUES (0, 2003, 2004);
INSERT Duration (did, since, _to) VALUES (1, 2006, 2006);
INSERT Duration (did, since, _to) VALUES (2, 2006, 2011);
INSERT Duration (did, since, _to) VALUES (3, 2006, -1);
INSERT Duration (did, since, _to) VALUES (4, 2007, 2007);
INSERT Duration (did, since, _to) VALUES (5, 2007, 2010);
INSERT Duration (did, since, _to) VALUES (6, 2007, 2018);
INSERT Duration (did, since, _to) VALUES (7, 2007, 2019);
INSERT Duration (did, since, _to) VALUES (8, 2008, 2008);
INSERT Duration (did, since, _to) VALUES (9, 2008, 2011);
INSERT Duration (did, since, _to) VALUES (10, 2009, 2009);
INSERT Duration (did, since, _to) VALUES (11, 2010, 2011);
INSERT Duration (did, since, _to) VALUES (12, 2010, 2012);
INSERT Duration (did, since, _to) VALUES (13, 2010, 2018);
INSERT Duration (did, since, _to) VALUES (14, 2010, -1);
INSERT Duration (did, since, _to) VALUES (15, 2011, 2012);
INSERT Duration (did, since, _to) VALUES (16, 2011, 2013);
INSERT Duration (did, since, _to) VALUES (17, 2011, -1);
INSERT Duration (did, since, _to) VALUES (18, 2012, 2012);
INSERT Duration (did, since, _to) VALUES (19, 2012, 2013);
INSERT Duration (did, since, _to) VALUES (20, 2012, 2018);
INSERT Duration (did, since, _to) VALUES (21, 2014, 2015);
INSERT Duration (did, since, _to) VALUES (22, 2014, 2017);
INSERT Duration (did, since, _to) VALUES (23, 2014, -1);
INSERT Duration (did, since, _to) VALUES (24, 2015, 2017);
INSERT Duration (did, since, _to) VALUES (25, 2015, 2018);
INSERT Duration (did, since, _to) VALUES (26, 2015, -1);
INSERT Duration (did, since, _to) VALUES (27, 2016, 2016);
INSERT Duration (did, since, _to) VALUES (28, 2016, 2017);
INSERT Duration (did, since, _to) VALUES (29, 2016, 2018);
INSERT Duration (did, since, _to) VALUES (30, 2016, 2019);
INSERT Duration (did, since, _to) VALUES (31, 2016, -1);
INSERT Duration (did, since, _to) VALUES (32, 2017, 2017);
INSERT Duration (did, since, _to) VALUES (33, 2017, 2018);
INSERT Duration (did, since, _to) VALUES (34, 2017, 2019);
INSERT Duration (did, since, _to) VALUES (35, 2017, -1);
INSERT Duration (did, since, _to) VALUES (36, 2018, 2018);
INSERT Duration (did, since, _to) VALUES (37, 2018, 2019);
INSERT Duration (did, since, _to) VALUES (38, 2018, -1);
INSERT Duration (did, since, _to) VALUES (39, 2019, 2019);

--- Related_to
INSERT INTO Related_to (wid, content_id, rtype) VALUES (104, 0, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (106, 1, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (110, 2, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (110, 3, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (110, 4, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (110, 5, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (117, 6, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (117, 7, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (118, 8, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (118, 9, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (201, 10, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (202, 11, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (207, 12, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (208, 13, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (215, 14, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (220, 15, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (306, 16, 'Underlying work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (307, 17, 'Underlying work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (313, 18, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (310, 19, 'Derivative work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (310, 20, 'Underlying work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (320, 21, 'Series');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (311, 22, 'Series');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (307, 23, 'Underlying work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (314, 24, 'Underlying work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (315, 25, 'Underlying work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (317, 26, 'Underlying work');
INSERT INTO Related_to (wid, content_id, rtype) VALUES (319, 27, 'Underlying work');

--- Serialize
INSERT INTO Serialize (wid, comid, did) VALUES (101, 1, 1);
INSERT INTO Serialize (wid, comid, did) VALUES (102, 1, 9);
INSERT INTO Serialize (wid, comid, did) VALUES (103, 1, 8);
INSERT INTO Serialize (wid, comid, did) VALUES (104, 1, 5);
INSERT INTO Serialize (wid, comid, did) VALUES (105, 1, 2);
INSERT INTO Serialize (wid, comid, did) VALUES (106, 1, 11);
INSERT INTO Serialize (wid, comid, did) VALUES (107, 1, 15);
INSERT INTO Serialize (wid, comid, did) VALUES (108, 1, 18);
INSERT INTO Serialize (wid, comid, did) VALUES (109, 1, 19);
INSERT INTO Serialize (wid, comid, did) VALUES (110, 1, 12);
INSERT INTO Serialize (wid, comid, did) VALUES (111, 1, 24);
INSERT INTO Serialize (wid, comid, did) VALUES (112, 1, 30);
INSERT INTO Serialize (wid, comid, did) VALUES (113, 1, 35);
INSERT INTO Serialize (wid, comid, did) VALUES (114, 1, 35);
INSERT INTO Serialize (wid, comid, did) VALUES (115, 1, 38);
INSERT INTO Serialize (wid, comid, did) VALUES (116, 1, 14);
INSERT INTO Serialize (wid, comid, did) VALUES (117, 1, 3);
INSERT INTO Serialize (wid, comid, did) VALUES (118, 1, 17);
INSERT INTO Serialize (wid, comid, did) VALUES (119, 1, 6);
INSERT INTO Serialize (wid, comid, did) VALUES (120, 1, 7);
INSERT INTO Serialize (wid, comid, did) VALUES (201, 2, 0);
INSERT INTO Serialize (wid, comid, did) VALUES (202, 2, 4);
INSERT INTO Serialize (wid, comid, did) VALUES (203, 2, 10);
INSERT INTO Serialize (wid, comid, did) VALUES (204, 2, 13);
INSERT INTO Serialize (wid, comid, did) VALUES (205, 2, 13);
INSERT INTO Serialize (wid, comid, did) VALUES (206, 2, 16);
INSERT INTO Serialize (wid, comid, did) VALUES (207, 2, 20);
INSERT INTO Serialize (wid, comid, did) VALUES (208, 2, 22);
INSERT INTO Serialize (wid, comid, did) VALUES (209, 2, 25);
INSERT INTO Serialize (wid, comid, did) VALUES (210, 2, 27);
INSERT INTO Serialize (wid, comid, did) VALUES (211, 2, 29);
INSERT INTO Serialize (wid, comid, did) VALUES (212, 2, 28);
INSERT INTO Serialize (wid, comid, did) VALUES (213, 2, 37);
INSERT INTO Serialize (wid, comid, did) VALUES (214, 2, 36);
INSERT INTO Serialize (wid, comid, did) VALUES (215, 2, 36);
INSERT INTO Serialize (wid, comid, did) VALUES (216, 2, 31);
INSERT INTO Serialize (wid, comid, did) VALUES (217, 2, 35);
INSERT INTO Serialize (wid, comid, did) VALUES (218, 2, 37);
INSERT INTO Serialize (wid, comid, did) VALUES (219, 2, 37);
INSERT INTO Serialize (wid, comid, did) VALUES (220, 2, 37);
INSERT INTO Serialize (wid, comid, did) VALUES (301, 3, 23);
INSERT INTO Serialize (wid, comid, did) VALUES (302, 3, 23);
INSERT INTO Serialize (wid, comid, did) VALUES (303, 3, 21);
INSERT INTO Serialize (wid, comid, did) VALUES (304, 3, 22);
INSERT INTO Serialize (wid, comid, did) VALUES (305, 3, 24);
INSERT INTO Serialize (wid, comid, did) VALUES (306, 3, 26);
INSERT INTO Serialize (wid, comid, did) VALUES (307, 3, 32);
INSERT INTO Serialize (wid, comid, did) VALUES (308, 3, 34);
INSERT INTO Serialize (wid, comid, did) VALUES (309, 3, 39);
INSERT INTO Serialize (wid, comid, did) VALUES (310, 3, 29);
INSERT INTO Serialize (wid, comid, did) VALUES (311, 3, 32);
INSERT INTO Serialize (wid, comid, did) VALUES (312, 3, 33);
INSERT INTO Serialize (wid, comid, did) VALUES (313, 3, 31);
INSERT INTO Serialize (wid, comid, did) VALUES (314, 3, 35);
INSERT INTO Serialize (wid, comid, did) VALUES (315, 3, 35);
INSERT INTO Serialize (wid, comid, did) VALUES (316, 3, 35);
INSERT INTO Serialize (wid, comid, did) VALUES (317, 3, 35);
INSERT INTO Serialize (wid, comid, did) VALUES (318, 3, 35);
INSERT INTO Serialize (wid, comid, did) VALUES (319, 3, 38);
INSERT INTO Serialize (wid, comid, did) VALUES (320, 3, 35);

--- Written_by

-- NAVER
INSERT Written_by (wid, tid) VALUES (101, 0);
INSERT Written_by (wid, tid) VALUES (102, 1);
INSERT Written_by (wid, tid) VALUES (103, 2);
INSERT Written_by (wid, tid) VALUES (104, 3);
INSERT Written_by (wid, tid) VALUES (105, 4);
INSERT Written_by (wid, tid) VALUES (106, 2);
INSERT Written_by (wid, tid) VALUES (107, 2);
INSERT Written_by (wid, tid) VALUES (108, 4);
INSERT Written_by (wid, tid) VALUES (109, 5);
INSERT Written_by (wid, tid) VALUES (110, 6);
INSERT Written_by (wid, tid) VALUES (111, 7);
INSERT Written_by (wid, tid) VALUES (112, 8);
INSERT Written_by (wid, tid) VALUES (113, 9);
INSERT Written_by (wid, tid) VALUES (114, 10);
INSERT Written_by (wid, tid) VALUES (115, 11);
INSERT Written_by (wid, tid) VALUES (116, 12);
INSERT Written_by (wid, tid) VALUES (117, 13);
INSERT Written_by (wid, tid) VALUES (118, 14);
INSERT Written_by (wid, tid) VALUES (119, 15);
INSERT Written_by (wid, tid) VALUES (120, 16);


-- DAUM
INSERT Written_by (wid, tid) VALUES (201, 17);
INSERT Written_by (wid, tid) VALUES (202, 17);
INSERT Written_by (wid, tid) VALUES (203, 18);
INSERT Written_by (wid, tid) VALUES (204, 19);
INSERT Written_by (wid, tid) VALUES (205, 20);
INSERT Written_by (wid, tid) VALUES (206, 21);
INSERT Written_by (wid, tid) VALUES (207, 23);
INSERT Written_by (wid, tid) VALUES (208, 22);
INSERT Written_by (wid, tid) VALUES (209, 24);
INSERT Written_by (wid, tid) VALUES (210, 25);
INSERT Written_by (wid, tid) VALUES (211, 26);
INSERT Written_by (wid, tid) VALUES (212, 27);
INSERT Written_by (wid, tid) VALUES (213, 28);
INSERT Written_by (wid, tid) VALUES (214, 22);
INSERT Written_by (wid, tid) VALUES (215, 23);
INSERT Written_by (wid, tid) VALUES (216, 29);
INSERT Written_by (wid, tid) VALUES (217, 30);
INSERT Written_by (wid, tid) VALUES (218, 31);
INSERT Written_by (wid, tid) VALUES (219, 32);
INSERT Written_by (wid, tid) VALUES (220, 33);


-- KAKAO
INSERT Written_by (wid, tid) VALUES (301, 34);
INSERT Written_by (wid, tid) VALUES (302, 35);
INSERT Written_by (wid, tid) VALUES (303, 36);
INSERT Written_by (wid, tid) VALUES (304, 37);
INSERT Written_by (wid, tid) VALUES (305, 38);
INSERT Written_by (wid, tid) VALUES (306, 39);
INSERT Written_by (wid, tid) VALUES (307, 40);
INSERT Written_by (wid, tid) VALUES (308, 41);
INSERT Written_by (wid, tid) VALUES (309, 42);
INSERT Written_by (wid, tid) VALUES (310, 43);
INSERT Written_by (wid, tid) VALUES (311, 44);
INSERT Written_by (wid, tid) VALUES (312, 45);
INSERT Written_by (wid, tid) VALUES (313, 46);
INSERT Written_by (wid, tid) VALUES (314, 47);
INSERT Written_by (wid, tid) VALUES (315, 48);
INSERT Written_by (wid, tid) VALUES (316, 49);
INSERT Written_by (wid, tid) VALUES (317, 50);
INSERT Written_by (wid, tid) VALUES (318, 51);
INSERT Written_by (wid, tid) VALUES (319, 52);
INSERT Written_by (wid, tid) VALUES (320, 44);

--- Member_of
INSERT Member_of (tid, cid) VALUES (0, 0);
INSERT Member_of (tid, cid) VALUES (1, 1);
INSERT Member_of (tid, cid) VALUES (2, 2);
INSERT Member_of (tid, cid) VALUES (3, 3);
INSERT Member_of (tid, cid) VALUES (4, 4);
INSERT Member_of (tid, cid) VALUES (5, 5);
INSERT Member_of (tid, cid) VALUES (5, 6);
INSERT Member_of (tid, cid) VALUES (6, 7);
INSERT Member_of (tid, cid) VALUES (7, 8);
INSERT Member_of (tid, cid) VALUES (8, 9);
INSERT Member_of (tid, cid) VALUES (9, 10);
INSERT Member_of (tid, cid) VALUES (9, 7);
INSERT Member_of (tid, cid) VALUES (10, 11);
INSERT Member_of (tid, cid) VALUES (11, 12);
INSERT Member_of (tid, cid) VALUES (11, 13);
INSERT Member_of (tid, cid) VALUES (12, 14);
INSERT Member_of (tid, cid) VALUES (13, 15);
INSERT Member_of (tid, cid) VALUES (14, 16);
INSERT Member_of (tid, cid) VALUES (15, 17);
INSERT Member_of (tid, cid) VALUES (16, 18);
INSERT Member_of (tid, cid) VALUES (16, 19);

INSERT Member_of (tid, cid) VALUES (17, 20);
INSERT Member_of (tid, cid) VALUES (18, 21);
INSERT Member_of (tid, cid) VALUES (19, 22);
INSERT Member_of (tid, cid) VALUES (20, 23);
INSERT Member_of (tid, cid) VALUES (21, 24);
INSERT Member_of (tid, cid) VALUES (22, 26);
INSERT Member_of (tid, cid) VALUES (23, 25);
INSERT Member_of (tid, cid) VALUES (24, 27);
INSERT Member_of (tid, cid) VALUES (25, 28);
INSERT Member_of (tid, cid) VALUES (26, 29);
INSERT Member_of (tid, cid) VALUES (27, 30);
INSERT Member_of (tid, cid) VALUES (28, 31);
INSERT Member_of (tid, cid) VALUES (29, 32);
INSERT Member_of (tid, cid) VALUES (30, 33);
INSERT Member_of (tid, cid) VALUES (30, 34);
INSERT Member_of (tid, cid) VALUES (31, 35);
INSERT Member_of (tid, cid) VALUES (32, 36);
INSERT Member_of (tid, cid) VALUES (33, 37);

INSERT Member_of (tid, cid) VALUES (34, 38);
INSERT Member_of (tid, cid) VALUES (35, 39);
INSERT Member_of (tid, cid) VALUES (36, 40);
INSERT Member_of (tid, cid) VALUES (37, 41);
INSERT Member_of (tid, cid) VALUES (38, 42);
INSERT Member_of (tid, cid) VALUES (39, 43);
INSERT Member_of (tid, cid) VALUES (39, 44);
INSERT Member_of (tid, cid) VALUES (40, 45);
INSERT Member_of (tid, cid) VALUES (40, 46);
INSERT Member_of (tid, cid) VALUES (41, 47);
INSERT Member_of (tid, cid) VALUES (42, 48);
INSERT Member_of (tid, cid) VALUES (42, 49);
INSERT Member_of (tid, cid) VALUES (43, 50);
INSERT Member_of (tid, cid) VALUES (44, 51);
INSERT Member_of (tid, cid) VALUES (45, 52);
INSERT Member_of (tid, cid) VALUES (46, 53);
INSERT Member_of (tid, cid) VALUES (46, 54);
INSERT Member_of (tid, cid) VALUES (46, 55);
INSERT Member_of (tid, cid) VALUES (46, 56);
INSERT Member_of (tid, cid) VALUES (47, 57);
INSERT Member_of (tid, cid) VALUES (47, 58);
INSERT Member_of (tid, cid) VALUES (48, 59);
INSERT Member_of (tid, cid) VALUES (48, 60);
INSERT Member_of (tid, cid) VALUES (49, 61);
INSERT Member_of (tid, cid) VALUES (49, 62);
INSERT Member_of (tid, cid) VALUES (50, 63);
INSERT Member_of (tid, cid) VALUES (50, 64);
INSERT Member_of (tid, cid) VALUES (50, 65);
INSERT Member_of (tid, cid) VALUES (51, 66);
INSERT Member_of (tid, cid) VALUES (52, 67);
INSERT Member_of (tid, cid) VALUES (52, 68);

--- Favorite_of
INSERT Favorite_of (cid, rid) VALUES (1, 0);
INSERT Favorite_of (cid, rid) VALUES (9, 0);
INSERT Favorite_of (cid, rid) VALUES (15, 0);
INSERT Favorite_of (cid, rid) VALUES (5, 1);
INSERT Favorite_of (cid, rid) VALUES (20, 1);
INSERT Favorite_of (cid, rid) VALUES (1, 2);
INSERT Favorite_of (cid, rid) VALUES (3, 2);
INSERT Favorite_of (cid, rid) VALUES (15, 3);
INSERT Favorite_of (cid, rid) VALUES (15, 4);
INSERT Favorite_of (cid, rid) VALUES (67, 4);
INSERT Favorite_of (cid, rid) VALUES (29, 4);
INSERT Favorite_of (cid, rid) VALUES (6, 4);
INSERT Favorite_of (cid, rid) VALUES (1, 5);
INSERT Favorite_of (cid, rid) VALUES (9, 5);
INSERT Favorite_of (cid, rid) VALUES (30, 5);
INSERT Favorite_of (cid, rid) VALUES (2, 6);
INSERT Favorite_of (cid, rid) VALUES (44, 6);
INSERT Favorite_of (cid, rid) VALUES (13, 6);
INSERT Favorite_of (cid, rid) VALUES (14, 6);
INSERT Favorite_of (cid, rid) VALUES (58, 6);
INSERT Favorite_of (cid, rid) VALUES (24, 7);
INSERT Favorite_of (cid, rid) VALUES (32, 7);
INSERT Favorite_of (cid, rid) VALUES (48, 7);
INSERT Favorite_of (cid, rid) VALUES (54, 7);
INSERT Favorite_of (cid, rid) VALUES (28, 8);
INSERT Favorite_of (cid, rid) VALUES (32, 8);
INSERT Favorite_of (cid, rid) VALUES (31, 8);
INSERT Favorite_of (cid, rid) VALUES (13, 8);
INSERT Favorite_of (cid, rid) VALUES (2, 9);
INSERT Favorite_of (cid, rid) VALUES (11, 9);
INSERT Favorite_of (cid, rid) VALUES (44, 9);

--- Likes
INSERT Likes (rid, wid) VALUES (0, 103);
INSERT Likes (rid, wid) VALUES (0, 104);
INSERT Likes (rid, wid) VALUES (0, 206);
INSERT Likes (rid, wid) VALUES (0, 219);
INSERT Likes (rid, wid) VALUES (1, 109);
INSERT Likes (rid, wid) VALUES (1, 201);
INSERT Likes (rid, wid) VALUES (1, 202);
INSERT Likes (rid, wid) VALUES (1, 214);
INSERT Likes (rid, wid) VALUES (2, 102);
INSERT Likes (rid, wid) VALUES (2, 104);
INSERT Likes (rid, wid) VALUES (2, 109);
INSERT Likes (rid, wid) VALUES (2, 120);
INSERT Likes (rid, wid) VALUES (3, 105);
INSERT Likes (rid, wid) VALUES (3, 117);
INSERT Likes (rid, wid) VALUES (4, 113);
INSERT Likes (rid, wid) VALUES (4, 305);
INSERT Likes (rid, wid) VALUES (4, 308);
INSERT Likes (rid, wid) VALUES (4, 311);
INSERT Likes (rid, wid) VALUES (4, 312);
INSERT Likes (rid, wid) VALUES (5, 102);
INSERT Likes (rid, wid) VALUES (5, 112);
INSERT Likes (rid, wid) VALUES (5, 212);
INSERT Likes (rid, wid) VALUES (5, 218);
INSERT Likes (rid, wid) VALUES (5, 306);
INSERT Likes (rid, wid) VALUES (5, 307);
INSERT Likes (rid, wid) VALUES (6, 106);
INSERT Likes (rid, wid) VALUES (6, 107);
INSERT Likes (rid, wid) VALUES (6, 203);
INSERT Likes (rid, wid) VALUES (6, 205);
INSERT Likes (rid, wid) VALUES (6, 214);
INSERT Likes (rid, wid) VALUES (6, 215);
INSERT Likes (rid, wid) VALUES (6, 218);
INSERT Likes (rid, wid) VALUES (6, 219);
INSERT Likes (rid, wid) VALUES (6, 303);
INSERT Likes (rid, wid) VALUES (6, 313);
INSERT Likes (rid, wid) VALUES (6, 315);
INSERT Likes (rid, wid) VALUES (7, 104);
INSERT Likes (rid, wid) VALUES (7, 108);
INSERT Likes (rid, wid) VALUES (7, 205);
INSERT Likes (rid, wid) VALUES (7, 206);
INSERT Likes (rid, wid) VALUES (7, 216);
INSERT Likes (rid, wid) VALUES (7, 309);
INSERT Likes (rid, wid) VALUES (7, 313);
INSERT Likes (rid, wid) VALUES (7, 314);
INSERT Likes (rid, wid) VALUES (8, 110);
INSERT Likes (rid, wid) VALUES (8, 205);
INSERT Likes (rid, wid) VALUES (8, 210);
INSERT Likes (rid, wid) VALUES (8, 214);
INSERT Likes (rid, wid) VALUES (8, 302);
INSERT Likes (rid, wid) VALUES (9, 106);
INSERT Likes (rid, wid) VALUES (9, 107);
INSERT Likes (rid, wid) VALUES (9, 114);
INSERT Likes (rid, wid) VALUES (9, 211);


--- Subscribes
INSERT Subscribes (rid, wid) VALUES (0, 103);
INSERT Subscribes (rid, wid) VALUES (0, 104);
INSERT Subscribes (rid, wid) VALUES (0, 109);
INSERT Subscribes (rid, wid) VALUES (0, 110);
INSERT Subscribes (rid, wid) VALUES (0, 206);
INSERT Subscribes (rid, wid) VALUES (0, 207);
INSERT Subscribes (rid, wid) VALUES (0, 216);
INSERT Subscribes (rid, wid) VALUES (0, 219);
INSERT Subscribes (rid, wid) VALUES (1, 109);
INSERT Subscribes (rid, wid) VALUES (1, 110);
INSERT Subscribes (rid, wid) VALUES (1, 201);
INSERT Subscribes (rid, wid) VALUES (1, 202);
INSERT Subscribes (rid, wid) VALUES (1, 208);
INSERT Subscribes (rid, wid) VALUES (1, 214);
INSERT Subscribes (rid, wid) VALUES (1, 217);
INSERT Subscribes (rid, wid) VALUES (2, 102);
INSERT Subscribes (rid, wid) VALUES (2, 104);
INSERT Subscribes (rid, wid) VALUES (2, 109);
INSERT Subscribes (rid, wid) VALUES (2, 117);
INSERT Subscribes (rid, wid) VALUES (2, 120);
INSERT Subscribes (rid, wid) VALUES (3, 105);
INSERT Subscribes (rid, wid) VALUES (3, 117);
INSERT Subscribes (rid, wid) VALUES (3, 209);
INSERT Subscribes (rid, wid) VALUES (3, 213);
INSERT Subscribes (rid, wid) VALUES (3, 219);
INSERT Subscribes (rid, wid) VALUES (4, 113);
INSERT Subscribes (rid, wid) VALUES (4, 304);
INSERT Subscribes (rid, wid) VALUES (4, 305);
INSERT Subscribes (rid, wid) VALUES (4, 307);
INSERT Subscribes (rid, wid) VALUES (4, 308);
INSERT Subscribes (rid, wid) VALUES (4, 311);
INSERT Subscribes (rid, wid) VALUES (4, 312);
INSERT Subscribes (rid, wid) VALUES (4, 315);
INSERT Subscribes (rid, wid) VALUES (5, 102);
INSERT Subscribes (rid, wid) VALUES (5, 107);
INSERT Subscribes (rid, wid) VALUES (5, 112);
INSERT Subscribes (rid, wid) VALUES (5, 113);
INSERT Subscribes (rid, wid) VALUES (5, 212);
INSERT Subscribes (rid, wid) VALUES (5, 218);
INSERT Subscribes (rid, wid) VALUES (5, 220);
INSERT Subscribes (rid, wid) VALUES (5, 306);
INSERT Subscribes (rid, wid) VALUES (5, 307);
INSERT Subscribes (rid, wid) VALUES (5, 309);
INSERT Subscribes (rid, wid) VALUES (6, 106);
INSERT Subscribes (rid, wid) VALUES (6, 107);
INSERT Subscribes (rid, wid) VALUES (6, 203);
INSERT Subscribes (rid, wid) VALUES (6, 205);
INSERT Subscribes (rid, wid) VALUES (6, 214);
INSERT Subscribes (rid, wid) VALUES (6, 218);
INSERT Subscribes (rid, wid) VALUES (6, 219);
INSERT Subscribes (rid, wid) VALUES (6, 301);
INSERT Subscribes (rid, wid) VALUES (6, 303);
INSERT Subscribes (rid, wid) VALUES (6, 305);
INSERT Subscribes (rid, wid) VALUES (6, 313);
INSERT Subscribes (rid, wid) VALUES (6, 315);
INSERT Subscribes (rid, wid) VALUES (7, 104);
INSERT Subscribes (rid, wid) VALUES (7, 108);
INSERT Subscribes (rid, wid) VALUES (7, 110);
INSERT Subscribes (rid, wid) VALUES (7, 117);
INSERT Subscribes (rid, wid) VALUES (7, 205);
INSERT Subscribes (rid, wid) VALUES (7, 206);
INSERT Subscribes (rid, wid) VALUES (7, 207);
INSERT Subscribes (rid, wid) VALUES (7, 211);
INSERT Subscribes (rid, wid) VALUES (7, 216);
INSERT Subscribes (rid, wid) VALUES (7, 217);
INSERT Subscribes (rid, wid) VALUES (7, 304);
INSERT Subscribes (rid, wid) VALUES (7, 309);
INSERT Subscribes (rid, wid) VALUES (7, 313);
INSERT Subscribes (rid, wid) VALUES (7, 314);
INSERT Subscribes (rid, wid) VALUES (8, 110);
INSERT Subscribes (rid, wid) VALUES (8, 205);
INSERT Subscribes (rid, wid) VALUES (8, 206);
INSERT Subscribes (rid, wid) VALUES (8, 210);
INSERT Subscribes (rid, wid) VALUES (8, 211);
INSERT Subscribes (rid, wid) VALUES (8, 214);
INSERT Subscribes (rid, wid) VALUES (8, 302);
INSERT Subscribes (rid, wid) VALUES (9, 106);
INSERT Subscribes (rid, wid) VALUES (9, 107);
INSERT Subscribes (rid, wid) VALUES (9, 114);
INSERT Subscribes (rid, wid) VALUES (9, 118);
INSERT Subscribes (rid, wid) VALUES (9, 202);
INSERT Subscribes (rid, wid) VALUES (9, 211);
INSERT Subscribes (rid, wid) VALUES (9, 215);
INSERT Subscribes (rid, wid) VALUES (9, 220);
INSERT Subscribes (rid, wid) VALUES (9, 303);
INSERT Subscribes (rid, wid) VALUES (9, 311);
INSERT Subscribes (rid, wid) VALUES (9, 320);