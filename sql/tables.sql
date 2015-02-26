CREATE TABLE Employee(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	username VARCHAR(32) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE Group_(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	parent INT,
	PRIMARY KEY(id),
	FOREIGN KEY(parent) REFERENCES Group_(id)
		ON DELETE CASCADE
);

CREATE TABLE Appointment(
	id INT NOT NULL AUTO_INCREMENT,
	location VARCHAR(255),
	description VARCHAR(255) NOT NULL,
	starttime DATE NOT NULL,
	endtime DATE NOT NULL,
	lastmodified DATE NOT NULL,
	createdby INT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(createdby) REFERENCES Employee(id)
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
	employeeid INT NOT NULL,
	PRIMARY KEY(groupid, employeeid),
	FOREIGN KEY(groupid) REFERENCES Group_(id)
		ON DELETE CASCADE,
	FOREIGN KEY(employeeid) REFERENCES Employee(id)
		ON DELETE CASCADE
);

CREATE TABLE Invitation(
	employeeid INT NOT NULL,
	appointmentid INT NOT NULL,
	accepted BOOLEAN,
	hidden BOOLEAN,
	dirty BOOLEAN DEFAULT TRUE,
	alarm DATE,
	PRIMARY KEY(employeeid, appointmentid),
	FOREIGN KEY(employeeid) REFERENCES Employee(id)
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
	starttime DATE NOT NULL,
	endtime DATE NOT NULL,
	PRIMARY KEY(appointmentid),
	FOREIGN KEY(appointmentid) REFERENCES Appointment(id)
		ON DELETE CASCADE,
	FOREIGN KEY(roomid) REFERENCES MeetingRoom(id)
		ON DELETE CASCADE
);
