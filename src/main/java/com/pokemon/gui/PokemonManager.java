package com.pokemon.gui;

import com.pokemon.dao.PokemonDAO;
import com.pokemon.dao.PokemonDAOImp;
import com.pokemon.model.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokemonManager extends JFrame {
    private DefaultListModel<String> pokemonListModel;
    private JList<String> pokemonList;
    private JTextField searchField;
    private ArrayList<String> pokemons;

    public PokemonManager() {
        setTitle("Gestión de Pokémon");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        pokemons = new ArrayList<>();
        pokemonListModel = new DefaultListModel<>();
        pokemonList = new JList<>(pokemonListModel);
        searchField = new JTextField();

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Buscar Pokémon:");
        topPanel.add(searchLabel, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);

        JButton addButton = new JButton("Agregar");
        JButton updateButton = new JButton("Actualizar");
        JButton deleteButton = new JButton("Eliminar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(pokemonList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        searchField.addActionListener(e -> filterPokemonList());

        addButton.addActionListener(e -> addPokemon());
        updateButton.addActionListener(e -> updatePokemon());
        deleteButton.addActionListener(e -> deletePokemon());

        loadInitialPokemons();
    }

    private void loadInitialPokemons() {

        try {
            PokemonDAO pokemonDAO = new PokemonDAOImp();
            List<Pokemon> pokemones = pokemonDAO.readAll();

            for (Pokemon pokemon : pokemones) {

                pokemons.add(pokemon.getPokemonName());

            }
            updatePokemonList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePokemonList() {
        pokemonListModel.clear();
        for (String pokemon : pokemons) {
            pokemonListModel.addElement(pokemon);
        }
    }

    private void filterPokemonList() {
        String searchText = searchField.getText().toLowerCase();
        pokemonListModel.clear();
        for (String pokemon : pokemons) {
            if (pokemon.toLowerCase().contains(searchText)) {
                pokemonListModel.addElement(pokemon);
            }
        }
    }

    private void addPokemon() {
        try {
            ImageIcon icon = new ImageIcon("C:/Users/genae/Pictures/Logo-Pokemon.png");
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);

            PokemonDAO pokemonDAO = new PokemonDAOImp();

            int lecturaNewPKID;
            String lecturaNewPKName;
            double lecturaNewPKHeight;
            double lecturaNewPKWeight;
            int lecturaNewPKBE;
            int lecturaNewPKTypeID;

            lecturaNewPKName = (String) JOptionPane.showInputDialog(null,"Escribe el nombre del nuevo Pokémon:",
                    "Creando nuevo Pokémon",JOptionPane.PLAIN_MESSAGE, icon, null, "");

            String newPokemon = lecturaNewPKName;
            if (newPokemon != null && !newPokemon.trim().isEmpty()) {

                lecturaNewPKID = 1;

                String lecturaInput1 = (String) JOptionPane.showInputDialog(null,
                        "Escribe la altura del nuevo Pokémon:",
                        "Creando nuevo Pokémon", JOptionPane.PLAIN_MESSAGE, icon, null, "");
                lecturaNewPKHeight = Double.parseDouble(lecturaInput1);

                String lecturaInput2 = (String) JOptionPane.showInputDialog(null,
                        "Escribe el peso del nuevo Pokémon:",
                        "Creando nuevo Pokemón", JOptionPane.PLAIN_MESSAGE,icon, null, "");
                lecturaNewPKWeight = Double.parseDouble(lecturaInput2);

                String lecturaInput3 = (String) JOptionPane.showInputDialog(null,
                        "Escribe la experiencia base del nuevo Pokémon:",
                        "Creando nuevo Pokemón", JOptionPane.PLAIN_MESSAGE, icon, null, "");
                lecturaNewPKBE = Integer.parseInt(lecturaInput3);

                String lecturaInput4 = (String) JOptionPane.showInputDialog(null,
                        "Escribe el ID del TIPO de Pokémon del nuevo Pokémon:",
                        "Creando nuevo Pokemón", JOptionPane.PLAIN_MESSAGE,icon, null, "");
                lecturaNewPKTypeID = Integer.parseInt(lecturaInput4);

                Pokemon nuevoPokemon = new Pokemon(lecturaNewPKID, lecturaNewPKName,
                        lecturaNewPKHeight, lecturaNewPKWeight, lecturaNewPKBE, lecturaNewPKTypeID);
                pokemonDAO.create(nuevoPokemon);

                JOptionPane.showMessageDialog(null, "¡Pokémon Creado!",
                        "Pokémon Creado", JOptionPane.PLAIN_MESSAGE, icon);

                pokemons.add(newPokemon.trim());
                updatePokemonList();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePokemon() {
        try {
            ImageIcon icon = new ImageIcon("C:/Users/genae/Pictures/Pokeball-PNG-Photo-Image.png");
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);

            PokemonDAO pokemonDAO = new PokemonDAOImp();

            String selectedPokemon = pokemonList.getSelectedValue();
            if (selectedPokemon != null) {

                int lecturaUpdPKOpc;
                String lecturaUpdPKName;
                double lecturaUpdPKHeight;
                double lecturaUpdPKWeight;
                int lecturaUpdPKEB;
                int lecturaUpdPKTypeID;

                Pokemon pokemon2 = pokemonDAO.read(selectedPokemon);

                String input = (String) JOptionPane.showInputDialog(null,
                        "Escribe la opción que quieras actualizar del Pokémon " + selectedPokemon +
                                "." + "\n1. Nombre" + "\n2. Altura" + "\n3. Peso" + "\n4. Experiencia base" +
                                "\n5. Tipo de Pokémon", "Actualizando Pokemón",
                        JOptionPane.PLAIN_MESSAGE, icon, null, "");

                lecturaUpdPKOpc = Integer.parseInt(input);

                switch (lecturaUpdPKOpc) {
                    case 1:
                        lecturaUpdPKName = (String) JOptionPane.showInputDialog(null,
                                "Pokémon actual: " + pokemon2.getPokemonName() +
                                        "\nAltura actual: " + pokemon2.getHeight() +
                                        "\nPeso actual: " + pokemon2.getWeight() +
                                        "\nExperiencia base actual: " + pokemon2.getBaseExperience() +
                                        "\nTipo de Pokémon actual: " + pokemon2.getType() +
                                        "\n\nNuevo Nombre del Pokémon:",
                                "Actualizando Nombre de Pokémon",
                                JOptionPane.PLAIN_MESSAGE, icon, null, "");
                        pokemon2.setPokemonName(lecturaUpdPKName);
                        break;
                    case 2:
                        String lecturaInput1 = (String) JOptionPane.showInputDialog(null,
                                "Pokémon actual: " + pokemon2.getPokemonName() +
                                        "\nAltura actual: " + pokemon2.getHeight() +
                                        "\nPeso actual: " + pokemon2.getWeight() +
                                        "\nExperiencia base actual: " + pokemon2.getBaseExperience() +
                                        "\nTipo de Pokémon actual: " + pokemon2.getType() +
                                        "\n\nNueva Altura del Pokémon:",
                                "Actualizando Altura de " + pokemon2.getPokemonName(),
                                JOptionPane.PLAIN_MESSAGE, icon, null, "");

                        lecturaUpdPKHeight = Double.parseDouble(lecturaInput1);
                        pokemon2.setHeight(lecturaUpdPKHeight);
                        break;
                    case 3:
                        String lecturaInput2 = (String) JOptionPane.showInputDialog(null,
                                "Pokémon actual: " + pokemon2.getPokemonName() +
                                        "\nAltura actual: " + pokemon2.getHeight() +
                                        "\nPeso actual: " + pokemon2.getWeight() +
                                        "\nExperiencia base actual: " + pokemon2.getBaseExperience() +
                                        "\nTipo de Pokémon actual: " + pokemon2.getType() +
                                        "\n\nNuevo Peso del Pokémon:",
                                "Actualizando Peso de " + pokemon2.getPokemonName(),
                                JOptionPane.PLAIN_MESSAGE, icon, null, "");

                        lecturaUpdPKWeight = Double.parseDouble(lecturaInput2);
                        pokemon2.setWeight(lecturaUpdPKWeight);
                        break;
                    case 4:
                        String lecturaInput3 = (String) JOptionPane.showInputDialog(null,
                                "Pokémon actual: " + pokemon2.getPokemonName() +
                                        "\nAltura actual: " + pokemon2.getHeight() +
                                        "\nPeso actual: " + pokemon2.getWeight() +
                                        "\nExperiencia base actual: " + pokemon2.getBaseExperience() +
                                        "\nTipo de Pokémon actual: " + pokemon2.getType() +
                                        "\n\nNueva Experiencia base del Pokémon:",
                                "Actualizando XP Base de " + pokemon2.getPokemonName(),
                                JOptionPane.PLAIN_MESSAGE, icon, null, "");

                        lecturaUpdPKEB = Integer.parseInt(lecturaInput3);
                        pokemon2.setBaseExperience(lecturaUpdPKEB);
                        break;
                    case 5:
                        String lecturaInput4 = (String) JOptionPane.showInputDialog(null,
                                "Pokémon actual: " + pokemon2.getPokemonName() +
                                        "\nAltura actual: " + pokemon2.getHeight() +
                                        "\nPeso actual: " + pokemon2.getWeight() +
                                        "\nExperiencia base actual: " + pokemon2.getBaseExperience() +
                                        "\nTipo de Pokémon actual: " + pokemon2.getType() +
                                        "\n\nNuevo Tipo de Pokémon del Pokémon:",
                                "Actualizando Tipo de Pokémon de " + pokemon2.getPokemonName(),
                                JOptionPane.PLAIN_MESSAGE, icon, null, "");

                        lecturaUpdPKTypeID = Integer.parseInt(lecturaInput4);
                        pokemon2.setType(lecturaUpdPKTypeID);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Introduce una opción válida...",
                                "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
                        break;
                }

                pokemonDAO.update(pokemon2);

                JOptionPane.showMessageDialog(null, "Pokémon " +
                                pokemon2.getPokemonName() + " actualizado!", pokemon2.getPokemonName()+" Actualizado",
                        JOptionPane.PLAIN_MESSAGE, icon);

                pokemons.set(pokemons.indexOf(selectedPokemon), pokemon2.getPokemonName().trim());
                updatePokemonList();

            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un Pokémon para actualizar...",
                        "ERROR", JOptionPane.PLAIN_MESSAGE, icon);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void deletePokemon() {
        try {
            ImageIcon icon = new ImageIcon("C:/Users/genae/Pictures/Pokémon_FanMade_Logo.png");
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);

            PokemonDAO pokemonDAO = new PokemonDAOImp();

            String selectedPokemon = pokemonList.getSelectedValue();

            if (selectedPokemon != null) {

                String lecturaDelPK = (String) JOptionPane.showInputDialog(null,
                        "Escribe el nombre del Pokémon\npara confirmar la eliminación:",
                        "Eliminando Pokémon", JOptionPane.PLAIN_MESSAGE, icon, null,"");

                if (selectedPokemon.equals(lecturaDelPK)) {

                    pokemonDAO.delete(selectedPokemon);

                    JOptionPane.showMessageDialog(null, "¡Pokémon eliminado!", "Pokémon eliminado",
                            JOptionPane.PLAIN_MESSAGE, icon);

                    pokemons.remove(selectedPokemon);
                    updatePokemonList();
                }else {
                    JOptionPane.showMessageDialog(this, "Eliminación cancelada...",
                            "Eliminación cancelada", JOptionPane.PLAIN_MESSAGE, icon);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un Pokemon para eliminar...",
                        "ERROR", JOptionPane.PLAIN_MESSAGE, icon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
