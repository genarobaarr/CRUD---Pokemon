package com.pokemon.dao;

import com.pokemon.connection.DatabaseConnection;
import com.pokemon.model.Pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAOImp implements PokemonDAO{

    private String tableName;

    public PokemonDAOImp(){
        tableName = "Pokemon";
    }

    @Override
    public void create(Pokemon pokemon) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String insertQuery = "INSERT INTO "+tableName+" (name,  height, weight, base_experience, type_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertQuery);
        ps.setString(1, pokemon.getPokemonName());
        ps.setDouble(2, pokemon.getHeight());
        ps.setDouble(3, pokemon.getWeight());
        ps.setInt(4, pokemon.getBaseExperience());
        ps.setInt(5, pokemon.getType());
        ps.execute();
    }

    @Override
    public Pokemon read(String id) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT id, name, height, weight, base_experience, type_id FROM "+tableName+" WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        Pokemon pokemon = new Pokemon();
        if(rs.next()){
            pokemon.setId(rs.getInt(1));
            pokemon.setPokemonName(rs.getString(2));
            pokemon.setHeight(rs.getDouble(3));
            pokemon.setWeight(rs.getDouble(4));
            pokemon.setBaseExperience(rs.getInt(5));
            pokemon.setType(rs.getInt(6));

        }
        return pokemon;
    }

    @Override
    public void update(Pokemon pokemon) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "UPDATE "+tableName+" SET name = ?, height = ?, weight = ?, base_experience = ?, type_id = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, pokemon.getPokemonName());
        ps.setDouble(2, pokemon.getHeight());
        ps.setDouble(3, pokemon.getWeight());
        ps.setInt(4, pokemon.getBaseExperience());
        ps.setInt(5, pokemon.getType());
        ps.setInt(6, pokemon.getId());

        ps.executeUpdate();
    }

    @Override
    public void delete(String id) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "DELETE FROM "+tableName+" WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, id);
        ps.executeUpdate();
    }

    //***********************************************************CHECAR
    @Override
    public List<Pokemon> readAll() throws SQLException {
        List<Pokemon> pokemons = new ArrayList();
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT id, name, height, weight, base_experience, type_id FROM "+tableName;
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            pokemons.add(new Pokemon(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getDouble(4),
                    rs.getInt(5),
                    rs.getInt(6)
            ));
        }
        return pokemons;
    }
}

