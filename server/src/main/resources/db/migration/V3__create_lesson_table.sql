create TABLE LESSON (
    id serial primary key,
    name varchar(100),
    teacherName varchar(100),
    teacherId bigint,
    classRoom varchar(100),
    timeStart bigint,
    timeEnd bigint,
    scheduleId integer references SCHEDULE(id)
)