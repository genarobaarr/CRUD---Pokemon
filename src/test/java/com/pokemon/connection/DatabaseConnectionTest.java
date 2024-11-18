package com.pokemon.connection;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests de DatabaseConnection")
class DatabaseConnectionTest {

    @Mock
    private Connection mockConnection;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        mockStatic(DriverManager.class);
        when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);
    }

    @Test
    @DisplayName("Debe devolver siempre la misma instancia cuando la conexión esté activa (Patrón Singleton)")
    void testSingletonInstance() throws SQLException {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2, "La instancia no debería cambiar mientras la conexión esté abierta.");
    }

    @Test
    @DisplayName("La conexión no debe ser nula después de crear una instancia")
    void testConnectionIsNotNull() throws SQLException {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        assertNotNull(instance.getConnection(), "La conexión no debería ser nula.");
    }

    @Test
    @DisplayName("Debe crear una nueva instancia si la conexión actual está cerrada")
    void testRecreateInstanceWhenConnectionIsClosed() throws SQLException {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        Connection connection = instance1.getConnection();

        when(connection.isClosed()).thenReturn(true);

        DatabaseConnection instance2 = DatabaseConnection.getInstance();

        assertNotSame(instance1, instance2, "Se debería crear una nueva instancia si la conexión está cerrada.");
    }

}
