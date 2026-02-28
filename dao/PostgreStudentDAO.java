package com.capgemini.jdbc.Dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;


public class PostgreStudentDAO implements StudentDAO {

    Connection getConnection() throws Exception {

        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://localhost:5432/studentdb";
        String username = "postgres";
        String password = "root";

        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public void createTable() throws Exception {

        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        statement.executeUpdate("DROP TABLE IF EXISTS student");

        String query = "CREATE TABLE student ("
                + "id SERIAL PRIMARY KEY, "
                + "name VARCHAR(100), "
                + "email VARCHAR(100), "
                + "age INT, "
                + "mobile VARCHAR(10))";

        statement.executeUpdate(query);

        connection.close();

        System.out.println("Table created.");
    }
    

    @Override
    public void addStudent(Student student) throws Exception {
        Connection connection = getConnection();
        String query = "INSERT INTO student(name,email,age,mobile) VALUES(?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, student.getName());
        ps.setString(2, student.getEmail());
        ps.setInt(3, student.getAge());
        ps.setString(4, student.getMobile());
        ps.executeUpdate();
        connection.close();
        System.out.println("Student added successfully.");
    }

    @Override
    public void viewStudents() throws Exception {

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM student";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + "  " +
                    rs.getString("name") + "  " +
                    rs.getString("email") + "  " +
                    rs.getInt("age") + "  " +
                    rs.getString("mobile")
            );
        }

        connection.close();
    }
    @Override
    public void updateStudent(Student student) throws Exception {

        Connection connection = getConnection();
        String query = "UPDATE student SET name=?, email=?, age=?, mobile=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, student.getName());
        ps.setString(2, student.getEmail());
        ps.setInt(3, student.getAge());
        ps.setString(4, student.getMobile());
        ps.setInt(5, student.getId());
        ps.executeUpdate();
        connection.close();

        System.out.println("Student updated successfully.");
    }
    @Override
    public void deleteStudent(int id) throws Exception {
        Connection connection = getConnection();
        String query = "DELETE FROM student WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.close();
        System.out.println("Student deleted successfully.");
    }
}