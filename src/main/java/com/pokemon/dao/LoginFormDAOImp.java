package com.pokemon.dao;

import com.pokemon.connection.DatabaseConnection;
import com.pokemon.model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginFormDAOImp implements LoginFormDAO{

    private String tableName;

    public LoginFormDAOImp(){
        tableName = "Users";
    }

    @Override
    public void create(Login login) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String insertQuery = "INSERT INTO "+tableName+" (user,  password) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertQuery);
        ps.setString(1, login.getUser());
        ps.setString(2, login.getPassword());
        ps.execute();
    }

    @Override
    public Login read(String user) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT user, password FROM "+tableName+" WHERE user = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, user);
        ResultSet rs = ps.executeQuery();
        Login login = new Login();
        if(rs.next()){
            login.setUser(rs.getString(1));
            login.setPassword(rs.getString(2));

        }
        return login;
    }

    @Override
    public void update(Login login) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "UPDATE "+tableName+" SET user = ?, password = ? WHERE user = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, login.getUser());
        ps.setString(2, login.getPassword());
        ps.setString(3, login.getUser());
        ps.executeUpdate();
    }

    @Override
    public void delete(String user) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "DELETE FROM "+tableName+" WHERE user = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, user);
        ps.executeUpdate();
    }

    @Override
    public List<Login> readAll() throws SQLException {
        List<Login> users = new ArrayList();
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT id, user, password FROM " + tableName;
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            users.add(new Login(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3)
            ));
        }

        return users;
    }

}

