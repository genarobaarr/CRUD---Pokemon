package com.pokemon.connection;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseConnectionTest {

    @Test
    @DisplayName("Debería obtener siempre la misma instancia (Singleton)")
    void testSingletonInstance() throws SQLException {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2, "Las instancias no son iguales, el Singleton está fallando");
    }

    @Test
    @DisplayName("Debería obtener una conexión válida")
    void testConnectionNotNull() throws SQLException {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        Connection connection = instance.getConnection();

        assertNotNull(connection, "La conexión no debería ser nula");
        assertFalse(connection.isClosed(), "La conexión debería estar abierta");
    }

    @Test
    @DisplayName("Debería lanzar excepción al fallar el driver")
    void testDriverClassNotFound() {
        try (MockedStatic<Class> mockedClass = mockStatic(Class.class)) {
            mockedClass.when(() -> Class.forName("com.mysql.cj.jdbc.Driver"))
                    .thenThrow(new ClassNotFoundException());

            SQLException exception = assertThrows(SQLException.class, DatabaseConnection::getInstance);
            assertNotNull(exception, "Se esperaba una excepción al fallar el driver");
        }
    }

    @Test
    @DisplayName("Debería lanzar excepción al fallar la conexión")
    void testConnectionFailure() {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenThrow(new SQLException("Error simulado de conexión"));

            SQLException exception = assertThrows(SQLException.class, DatabaseConnection::getInstance);
            assertEquals("Error simulado de conexión", exception.getMessage());
        }
    }
}
