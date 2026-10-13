package com.university;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    @Test
    void shouldRegisterStudentForValidCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS301"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectInactiveCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                false,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }

    @Test
    void shouldRejectMissingPrerequisite() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS401"));
    }

    @Test
    void shouldAllowRegistrationWhenPrerequisiteIsCompleted() {

        Student student = new Student("S101", "Anita");
        student.addCompletedPrerequisite("CS301");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS401"));
    }

    @Test
    void shouldRejectDuplicateRegistration() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, course));

        boolean secondAttempt = service.register(student, course);

        assertFalse(secondAttempt);
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationWhenCreditLimitIsExceeded() {

        Student student = new Student("S101", "Anita");

        // Simulate 20 existing credits
        student.addRegistration("CS101", 4);
        student.addRegistration("CS102", 4);
        student.addRegistration("CS201", 4);
        student.addRegistration("CS202", 4);
        student.addRegistration("CS203", 4);

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }
    @Test
    void shouldRejectRegistrationWhenCourseIsFull() {
        Student student = new Student("S201", "Priya");
        Course course = new Course(
                "CS501", "Networks", 3, 1, true, null);

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(new Student("S202", "Other"), course));
        int creditsBefore = student.getTotalCredits();
        int enrollmentBefore = course.getCurrentEnrollment();

        assertFalse(service.register(student, course));
        assertFalse(student.isRegistered("CS501"));
        assertEquals(creditsBefore, student.getTotalCredits());
        assertEquals(enrollmentBefore, course.getCurrentEnrollment());
    }

    @Test
    void shouldAllowRegistrationForLastAvailableSeat() {
        Student firstStudent = new Student("S203", "Asha");
        Student secondStudent = new Student("S204", "Ravi");
        Course course = new Course(
                "CS502", "Databases", 3, 2, true, null);

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(firstStudent, course));
        assertEquals(1, course.getCurrentEnrollment());

        assertTrue(service.register(secondStudent, course));
        assertEquals(2, course.getCurrentEnrollment());
        assertTrue(secondStudent.isRegistered("CS502"));
    }

    @Test
    void shouldAllowFifthCourse() {
        Student student = new Student("S205", "Meera");

        for (int i = 1; i <= 4; i++) {
            student.addRegistration("OLD" + i, 3);
        }

        Course fifthCourse = new Course(
                "CS505", "Networks", 3, 10, true, null);

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, fifthCourse));
        assertEquals(5, student.getRegisteredCourses().size());
        assertEquals(15, student.getTotalCredits());
        assertEquals(1, fifthCourse.getCurrentEnrollment());
    }

    @Test
    void shouldRejectSixthCourseWithoutChangingState() {
        Student student = new Student("S206", "Arun");

        for (int i = 1; i <= 5; i++) {
            student.addRegistration("OLD" + i, 3);
        }

        Course sixthCourse = new Course(
                "CS506", "Security", 3, 10, true, null);

        RegistrationService service = new RegistrationService();

        assertFalse(service.register(student, sixthCourse));
        assertEquals(5, student.getRegisteredCourses().size());
        assertEquals(15, student.getTotalCredits());
        assertFalse(student.isRegistered("CS506"));
        assertEquals(0, sixthCourse.getCurrentEnrollment());
    }

}