create TABLE USERS (
    id serial primary key,
    username varchar(100) unique,
    password varchar(100)
)