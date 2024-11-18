package com.pokemon.dao;

import com.pokemon.connection.DatabaseConnection;
import com.pokemon.model.Pokemon;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de PokemonDAOImp")
class PokemonDAOImpTest {

    private MockedStatic<DatabaseConnection> mockedStaticDatabaseConnection;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private DatabaseConnection mockDatabaseConnection;

    @InjectMocks
    private PokemonDAOImp pokemonDAOImp;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        mockedStaticDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class);
        mockedStaticDatabaseConnection.when(DatabaseConnection::getInstance).thenReturn(mockDatabaseConnection);

        when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }




    @AfterEach
    void tearDown() {
        if (mockedStaticDatabaseConnection != null) {
            mockedStaticDatabaseConnection.close();
        }
    }


    @Test
    @DisplayName("Debería crear un nuevo Pokémon")
    void testCreate() throws Exception {
        when(mockPreparedStatement.execute()).thenReturn(true);

        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonName("Pikachu");
        pokemon.setHeight(0.4);
        pokemon.setWeight(6.0);
        pokemon.setBaseExperience(112);
        pokemon.setType(1);

        pokemonDAOImp.create(pokemon);

        verify(mockPreparedStatement).setString(1, "Pikachu");
        verify(mockPreparedStatement).setDouble(2, 0.4);
        verify(mockPreparedStatement).setDouble(3, 6.0);
        verify(mockPreparedStatement).setInt(4, 112);
        verify(mockPreparedStatement).setInt(5, 1);
        verify(mockPreparedStatement).execute();
    }

    @Test
    @DisplayName("Debería leer los datos del Pokémon al ser buscado por su nombre")
    void testRead() throws Exception {

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(25);
        when(mockResultSet.getString(2)).thenReturn("Pikachu");
        when(mockResultSet.getDouble(3)).thenReturn(0.4);
        when(mockResultSet.getDouble(4)).thenReturn(6.0);
        when(mockResultSet.getInt(5)).thenReturn(112);
        when(mockResultSet.getInt(6)).thenReturn(1);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Pokemon pokemon = pokemonDAOImp.read("Pikachu");

        assertNotNull(pokemon);
        assertEquals(25, pokemon.getId());
        assertEquals("Pikachu", pokemon.getPokemonName());
        assertEquals(0.4, pokemon.getHeight());
        assertEquals(6.0, pokemon.getWeight());
        assertEquals(112, pokemon.getBaseExperience());
        assertEquals(1, pokemon.getType());

        verify(mockPreparedStatement).setString(1, "Pikachu");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    @DisplayName("Debería modificar los datos de un Pokémon existente por su nombre")
    void testUpdate() throws Exception {
        Pokemon pokemon = new Pokemon(25, "Raichu", 0.8, 30.0, 218, 2);

        pokemonDAOImp.update(pokemon);

        verify(mockPreparedStatement).setString(1, "Raichu");
        verify(mockPreparedStatement).setDouble(2, 0.8);
        verify(mockPreparedStatement).setDouble(3, 30.0);
        verify(mockPreparedStatement).setInt(4, 218);
        verify(mockPreparedStatement).setInt(5, 2);
        verify(mockPreparedStatement).setInt(6, 25);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("Debería eliminar los datos del Pokémon al ser buscado por su nombre")
    void testDelete() throws Exception {
        pokemonDAOImp.delete("Pikachu");

        verify(mockPreparedStatement).setString(1, "Pikachu");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("Debería leer los datos de todos los Pokémon")
    void testReadAll() throws Exception {

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt(1)).thenReturn(25, 26);
        when(mockResultSet.getString(2)).thenReturn("Pikachu", "Bulbasaur");
        when(mockResultSet.getDouble(3)).thenReturn(0.4, 0.7);
        when(mockResultSet.getDouble(4)).thenReturn(6.0, 6.9);
        when(mockResultSet.getInt(5)).thenReturn(112, 64);
        when(mockResultSet.getInt(6)).thenReturn(1, 2);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        List<Pokemon> pokemons = pokemonDAOImp.readAll();

        assertNotNull(pokemons);
        assertEquals(2, pokemons.size());
        assertEquals("Pikachu", pokemons.get(0).getPokemonName());
        assertEquals("Bulbasaur", pokemons.get(1).getPokemonName());

        verify(mockPreparedStatement).executeQuery();
    }
}

