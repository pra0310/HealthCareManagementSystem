package HealthCareManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner in;

    public Patient(Connection connection, Scanner in) {
        this.connection = connection;
        this.in = in;
    }

    public void addPatient(){
        System.out.print("Enter Patient Name : ");
        String name = in.next();
        System.out.print("Enter Patient Age : ");
        int age = in.nextInt();
        System.out.print("Enter Patient Gender : ");
        String gender = in.next();

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0){
                System.out.println("Patient added Successfully !!");
            } else {
                System.out.println("Failed to add Patient !!");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients(){
        String query = "select * from patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+---------+-----------+");
            System.out.println("| Patient Id |         Name       |   Age   |   Gender  |");
            System.out.println("+------------+--------------------+---------+-----------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-11s| %-19s| %-8s| %-10s|\n", id, name, age, gender);
                System.out.println("+------------+--------------------+---------+-----------+");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            } else {
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}