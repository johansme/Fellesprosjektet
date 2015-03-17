CREATE TABLE User(
	id INT NOT NULL AUTO_INCREMENT,
	surname VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	username VARCHAR(32) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	admin boolean NOT NULL DEFAULT FALSE,
	PRIMARY KEY(id)
);

CREATE TABLE Group_(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	parent INT,
	createdby INT,
	PRIMARY KEY(id),
	FOREIGN KEY(parent) REFERENCES Group_(id)
		ON DELETE CASCADE,
	FOREIGN KEY(parent) REFERENCES User(id)
);

CREATE TABLE Appointment(
	id INT NOT NULL AUTO_INCREMENT,
	location VARCHAR(255),
	description VARCHAR(255) NOT NULL,
	starttime DATETIME NOT NULL,
	endtime DATETIME NOT NULL,
	lastmodified DATETIME NOT NULL,
	createdby INT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(createdby) REFERENCES User(id)
		ON DELETE CASCADE
);

CREATE TABLE MeetingRoom(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NOT NULL UNIQUE,
	capacity INT NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE GroupMember(
	groupid INT NOT NULL,
	userid INT NOT NULL,
	PRIMARY KEY(groupid, userid),
	FOREIGN KEY(groupid) REFERENCES Group_(id)
		ON DELETE CASCADE,
	FOREIGN KEY(userid) REFERENCES User(id)
		ON DELETE CASCADE
);

CREATE TABLE Invitation(
	userid INT NOT NULL,
	appointmentid INT NOT NULL,
	accepted BOOLEAN,
	hidden BOOLEAN,
	dirty BOOLEAN DEFAULT TRUE,
	alarm DATETIME,
	PRIMARY KEY(userid, appointmentid),
	FOREIGN KEY(userid) REFERENCES User(id)
		ON DELETE CASCADE,
	FOREIGN KEY(appointmentid) REFERENCES Appointment(id)
		ON DELETE CASCADE
);

CREATE TABLE GroupInvitation(
	groupid INT NOT NULL,
	appointmentid INT NOT NULL,
	PRIMARY KEY(groupid, appointmentid),
	FOREIGN KEY(appointmentid) REFERENCES Appointment(id)
		ON DELETE CASCADE,
	FOREIGN KEY(groupid) REFERENCES Group_(id)
		ON DELETE CASCADE
);

CREATE TABLE ReservedFor(
	appointmentid INT NOT NULL,
	roomid INT NOT NULL,
	starttime DATETIME NOT NULL,
	endtime DATETIME NOT NULL,
	PRIMARY KEY(appointmentid),
	FOREIGN KEY(appointmentid) REFERENCES Appointment(id)
		ON DELETE CASCADE,
	FOREIGN KEY(roomid) REFERENCES MeetingRoom(id)
		ON DELETE CASCADE
);
