
package com.example.studentmanagement.repository;

import com.example.studentmanagement.config.DatabaseConfig;
import com.example.studentmanagement.model.Student;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {

    private final DatabaseConfig databaseConfig;

  
    public StudentRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public void saveStudent(Student student) {

        String sql = "INSERT INTO students (full_name, age, email) VALUES (?, ?, ?)";

        String fullName = student.getFirstName() + " "
                        + student.getLastName();

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, fullName);
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getEmail());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    student.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving student: " + e.getMessage(), e);
        }
    }


    public List<Student> findAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT id, full_name, age, email FROM students";

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                String fullName = resultSet.getString("full_name");

                String[] names = fullName.split(" ", 2);

                Student student = new Student(
                        names[0],
                        names.length > 1 ? names[1] : "",
                        resultSet.getString("email"),
                        resultSet.getInt("age")
                );

                student.setId(resultSet.getInt("id"));

                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving students: " + e.getMessage(), e);
        }

        return students;
    }
}
