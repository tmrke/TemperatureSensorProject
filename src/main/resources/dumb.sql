# drop table sensor;
# drop table temperature_measurements;

create table sensor
(
    id                int primary key auto_increment,
    name              varchar(100) not null,
    password          varchar(255),
    registration_date datetime
);

create table temperature_measurements
(
    id         int primary key auto_increment,
    is_raining boolean,
    value      double not null,
    sensor_id  int,
    timestamp  timestamp,
    foreign key (sensor_id) references sensor (id)
);

