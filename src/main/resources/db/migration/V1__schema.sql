CREATE TABLE courses (
    id            VARCHAR(36)    NOT NULL,
    name          VARCHAR(255)   NOT NULL,
    instructor    VARCHAR(255)   NOT NULL,
    duration_hours INT           NOT NULL,
    price         DECIMAL(15, 2) NOT NULL,
    CONSTRAINT pk_courses PRIMARY KEY (id)
);

CREATE TABLE students (
    id        VARCHAR(36)  NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_students    PRIMARY KEY (id),
    CONSTRAINT uk_students_email UNIQUE (email)
);

CREATE TABLE enrollments (
    id           VARCHAR(36)    NOT NULL,
    student_id   VARCHAR(36)    NOT NULL,
    enrolled_at  TIMESTAMP      NOT NULL,
    total_amount DECIMAL(15, 2) NOT NULL,
    CONSTRAINT pk_enrollments       PRIMARY KEY (id),
    CONSTRAINT fk_enrollments_student FOREIGN KEY (student_id) REFERENCES students (id)
);

CREATE TABLE enrollment_lines (
    id            VARCHAR(36)    NOT NULL,
    enrollment_id VARCHAR(36)    NOT NULL,
    course_id     VARCHAR(36)    NOT NULL,
    course_name   VARCHAR(255)   NOT NULL,
    unit_price    DECIMAL(15, 2) NOT NULL,
    CONSTRAINT pk_enrollment_lines            PRIMARY KEY (id),
    CONSTRAINT fk_enrollment_lines_enrollment FOREIGN KEY (enrollment_id) REFERENCES enrollments (id),
    CONSTRAINT fk_enrollment_lines_course     FOREIGN KEY (course_id)     REFERENCES courses (id)
);
