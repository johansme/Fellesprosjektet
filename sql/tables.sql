CREATE TABLE Employee(
	id INT,
	username VARCHAR(32),
	name VARCHAR(255),
	password VARCHAR(255),
	PRIMARY KEY(id)
);

CREATE TABLE Group_(
	id INT,
	name VARCHAR(255),
	PRIMARY KEY(id)
);

CREATE TABLE Appointment(
	id INT,
	location VARCHAR(255),
	description VARCHAR(255),
	starttime DATE,
	endtime DATE,
	lastmodified DATE,
	createdby INT,
	PRIMARY KEY(id),
	FOREIGN KEY(createdby) REFERENCES Employee(id)
);

CREATE TABLE Message(
	id INT,
	content TEXT,
	sent DATE,
	PRIMARY KEY(id)
);

CREATE TABLE MeetingRoom(
	name VARCHAR(255),
	capacity INT,
	PRIMARY KEY(name)
);

CREATE TABLE GroupMember(
	groupid INT,
	employeeid INT,
	PRIMARY KEY(groupid, employeeid),
	FOREIGN KEY(groupid) REFERENCES Group_(id),
	FOREIGN KEY(employeeid) REFERENCES Employee(id)
);

CREATE TABLE MessageTo(
	employeeid INT,
	messageid INT,
	PRIMARY KEY(employeeid, messageid),
	FOREIGN KEY(employeeid) REFERENCES Employee(id),
	FOREIGN KEY(messageid) REFERENCES Message(id)
);

CREATE TABLE Invitation(
	employeeid INT,
	appointmentid INT,
	accepted BOOLEAN,
	hidden BOOLEAN,
	dirty BOOLEAN,
	alarm DATE,
	PRIMARY KEY(employeeid, appointmentid),
	FOREIGN KEY(employeeid) REFERENCES Employee(id),
	FOREIGN KEY(appointmentid) REFERENCES Appointment(id)
);

CREATE TABLE ReservedFor(
	appointmentid INT,
	room VARCHAR(255),
	starttime DATE,
	endtime DATE,
	PRIMARY KEY(appointmentid, room),
	FOREIGN KEY(appointmentid) REFERENCES Appointment(id),
	FOREIGN KEY(room) REFERENCES MeetingRoom(name)
);
