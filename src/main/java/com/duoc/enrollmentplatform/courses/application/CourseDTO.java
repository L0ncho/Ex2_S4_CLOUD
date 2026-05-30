package com.duoc.enrollmentplatform.courses.application;

public class CourseDTO {
    public String id;
    public String name;
    public String instructor;
    public int durationHours;
    public double price;

    public CourseDTO(String id, String name, String instructor, int durationHours, double price) {
        this.id = id; 
        this.name = name;
        this.instructor = instructor;
        this.durationHours = durationHours;
        this.price = price;
    }
}
