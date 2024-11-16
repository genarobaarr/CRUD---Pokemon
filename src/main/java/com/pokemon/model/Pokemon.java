package com.pokemon.model;

import java.io.Serializable;

public class Pokemon implements Serializable {
    private int id;
    private String pokemonName;
    private double height;
    private double weight;
    private int baseExperience;
    private int type;

    public Pokemon() {
    }

    public Pokemon(int id, String pokemonName, double height, double weight, int baseExperience, int type) {
        this.id = id;
        this.pokemonName = pokemonName;
        this.height = height;
        this.weight = weight;
        this.baseExperience = baseExperience;
        this.type = type;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

