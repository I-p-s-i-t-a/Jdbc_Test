package com.capgemini.jdbc.Dao;


public interface StudentDAO {

    void createTable() throws Exception;

    void addStudent(Student student) throws Exception;

    void viewStudents() throws Exception;

    void updateStudent(Student student) throws Exception;

    void deleteStudent(int id) throws Exception;
}
