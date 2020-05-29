package com.app.group15.persistence.entity;

public class CourseInstructorMapperEntity extends PersistenceEntity {

	private int courseId;
	private int instructorId;
	private int taId;

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}

	public int getTaId() {
		return taId;
	}

	public void setTaId(int taId) {
		this.taId = taId;
	}

}