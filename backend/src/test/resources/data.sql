CREATE TABLE USERS (
  user_no BIGINT NOT NULL AUTO_INCREMENT,
  id VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  gender VARCHAR(255) NOT NULL,
  age VARCHAR(255) NOT NULL,
  PRIMARY KEY (user_no)
);