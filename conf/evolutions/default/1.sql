# Blogpost schema

# --- !Ups

DROP TABLE IF EXISTS BLOGPOST;

CREATE TABLE BLOGPOST(ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
   TITLE VARCHAR(512) NOT NULL,
    CONTENT TEXT NOT NULL);

INSERT INTO BLOGPOST VALUES(1, 'LorremLipsum eka otsikko', 'Ekan sisältöä kfljaslkfasjlfkas ');
INSERT INTO BLOGPOST VALUES(2, 'LorremLipsum Toiiinen  otsikko', 'Tokan ssisältöä kfljaslkfasjlfkas ');


# --- !Downs

DROP TABLE BLOGPOST;