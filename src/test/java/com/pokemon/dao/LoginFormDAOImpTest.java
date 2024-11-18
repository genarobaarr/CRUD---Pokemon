package com.pokemon.dao;

import com.pokemon.connection.DatabaseConnection;
import com.pokemon.model.Login;
import org.junit.jupiter.api.*;

import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests de LoginFormDAOImp")
class LoginFormDAOImpTest {

    private LoginFormDAOImp loginFormDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<DatabaseConnection> mockedDatabaseConnection;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock estático para DatabaseConnection
        mockedDatabaseConnection = mockStatic(DatabaseConnection.class);
        mockedDatabaseConnection.when(DatabaseConnection::getInstance).thenReturn(mock(DatabaseConnection.class));
        when(DatabaseConnection.getInstance().getConnection()).thenReturn(mockConnection);

        loginFormDAO = new LoginFormDAOImp();
    }

    @AfterEach
    void tearDown() {
        // Cerrar el mock estático
        mockedDatabaseConnection.close();
    }

    @Test
    @DisplayName("Debería insertar un registro en la tabla Users")
    void testCreate() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        Login login = new Login(0, "testUser", "testPassword");
        loginFormDAO.create(login);

        verify(mockConnection).prepareStatement("INSERT INTO Users (user,  password) VALUES (?, ?)");
        verify(mockPreparedStatement).setString(1, "testUser");
        verify(mockPreparedStatement).setString(2, "testPassword");
        verify(mockPreparedStatement).execute();
    }

    @Test
    @DisplayName("Debería leer un registro existente de la tabla Users")
    void testRead() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString(1)).thenReturn("testUser");
        when(mockResultSet.getString(2)).thenReturn("testPassword");

        Login login = loginFormDAO.read("testUser");

        assertNotNull(login);
        assertEquals("testUser", login.getUser());
        assertEquals("testPassword", login.getPassword());

        verify(mockPreparedStatement).setString(1, "testUser");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    @DisplayName("Debería actualizar un registro existente en la tabla Users")
    void testUpdate() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        Login login = new Login(1, "testUser", "newPassword");
        loginFormDAO.update(login);

        verify(mockConnection).prepareStatement("UPDATE Users SET user = ?, password = ? WHERE user = ?");
        verify(mockPreparedStatement).setString(1, "testUser");
        verify(mockPreparedStatement).setString(2, "newPassword");
        verify(mockPreparedStatement).setString(3, "testUser");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("Debería eliminar un registro existente de la tabla Users")
    void testDelete() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        loginFormDAO.delete("testUser");

        verify(mockConnection).prepareStatement("DELETE FROM Users WHERE user = ?");
        verify(mockPreparedStatement).setString(1, "testUser");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("Debería leer todos los registros de la tabla Users")
    void testReadAll() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt(1)).thenReturn(1, 2);
        when(mockResultSet.getString(2)).thenReturn("user1", "user2");
        when(mockResultSet.getString(3)).thenReturn("password1", "password2");

        List<Login> users = loginFormDAO.readAll();

        assertNotNull(users);
        assertEquals(2, users.size());

        assertEquals(1, users.get(0).getId());
        assertEquals("user1", users.get(0).getUser());
        assertEquals("password1", users.get(0).getPassword());

        assertEquals(2, users.get(1).getId());
        assertEquals("user2", users.get(1).getUser());
        assertEquals("password2", users.get(1).getPassword());

        verify(mockPreparedStatement).executeQuery();
    }
}
