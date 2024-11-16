package com.pokemon.dao;

import com.pokemon.model.Login;

import java.sql.SQLException;
import java.util.List;

public interface LoginFormDAO {

    public void create(Login login) throws SQLException;
    public Login read(String user) throws SQLException;
    public void update(Login login) throws SQLException;
    public void delete(String user) throws SQLException;
    public List<Login> readAll() throws SQLException;

}
