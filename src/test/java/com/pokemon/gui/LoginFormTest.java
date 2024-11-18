package com.pokemon.gui;

import org.junit.jupiter.api.*;
import org.mockito.*;

import com.pokemon.dao.LoginFormDAO;
import com.pokemon.model.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests de LoginForm")
public class LoginFormTest {
    @Mock
    private LoginFormDAO mockLoginFormDAO;

    private LoginForm loginForm;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializar LoginForm con un mock de LoginFormDAO
        loginForm = new LoginForm();
        loginForm.loginFormDAO = mockLoginFormDAO;
    }

    @Test
    @DisplayName("Prueba de autenticación exitosa con credenciales válidas")
    void testAuthenticateSuccess() throws SQLException {
        // Datos simulados para LoginFormDAO
        List<Login> mockUsers = new ArrayList<>();
        mockUsers.add(new Login(1, "admin", "1234"));
        when(mockLoginFormDAO.readAll()).thenReturn(mockUsers);

        // Invocar el método authenticate
        boolean isAuthenticated = invokeAuthenticate(loginForm, "admin", "1234");

        // Verificar resultados
        assertTrue(isAuthenticated);
    }

    @Test
    @DisplayName("Prueba de autenticación fallida con credenciales inválidas")
    void testAuthenticateFailure() throws SQLException {
        // Datos simulados para LoginFormDAO
        List<Login> mockUsers = new ArrayList<>();
        mockUsers.add(new Login(1, "admin", "1234"));
        when(mockLoginFormDAO.readAll()).thenReturn(mockUsers);

        // Invocar el método authenticate con credenciales incorrectas
        boolean isAuthenticated = invokeAuthenticate(loginForm, "admin", "wrongpassword");

        // Verificar resultados
        assertFalse(isAuthenticated);
    }

    @Test
    @DisplayName("Prueba de inicio de sesión exitosa con credenciales válidas, lanzando cuadro de texto")
    void testLoginActionSuccess() throws SQLException, NoSuchFieldException, IllegalAccessException {
        // Simular usuarios en LoginFormDAO
        List<Login> mockUsers = new ArrayList<>();
        mockUsers.add(new Login(1, "admin", "1234"));
        when(mockLoginFormDAO.readAll()).thenReturn(mockUsers);

        // Usar reflexión para configurar campos privados
        Field usernameField = LoginForm.class.getDeclaredField("usernameField");
        Field passwordField = LoginForm.class.getDeclaredField("passwordField");
        usernameField.setAccessible(true);
        passwordField.setAccessible(true);

        JTextField username = (JTextField) usernameField.get(loginForm);
        JPasswordField password = (JPasswordField) passwordField.get(loginForm);

        username.setText("admin");
        password.setText("1234");

        // Crear instancia de LoginAction
        LoginForm.LoginAction loginAction = loginForm.new LoginAction();
        ActionEvent mockEvent = mock(ActionEvent.class);
        loginAction.actionPerformed(mockEvent);

        // Acceder a messageLabel con reflexión
        Field messageLabelField = LoginForm.class.getDeclaredField("messageLabel");
        messageLabelField.setAccessible(true);
        JLabel messageLabel = (JLabel) messageLabelField.get(loginForm);

        // Verificar que el mensaje de éxito sea mostrado
        assertEquals("Inicio de sesión exitoso!", messageLabel.getText());
    }

    @Test
    @DisplayName("Prueba de inicio de sesión fallida con credenciales inválidas")
    void testLoginActionFailure() throws SQLException, NoSuchFieldException, IllegalAccessException {
        // Simular usuarios en LoginFormDAO
        List<Login> mockUsers = new ArrayList<>();
        mockUsers.add(new Login(1, "admin", "1234"));
        when(mockLoginFormDAO.readAll()).thenReturn(mockUsers);

        // Usar reflexión para configurar campos privados
        Field usernameField = LoginForm.class.getDeclaredField("usernameField");
        Field passwordField = LoginForm.class.getDeclaredField("passwordField");
        usernameField.setAccessible(true);
        passwordField.setAccessible(true);

        JTextField username = (JTextField) usernameField.get(loginForm);
        JPasswordField password = (JPasswordField) passwordField.get(loginForm);

        // Configurar campos del formulario con datos incorrectos
        username.setText("admin");
        password.setText("wrongpassword");

        // Simular el evento de acción del botón
        ActionEvent mockEvent = mock(ActionEvent.class);
        LoginForm.LoginAction loginAction = loginForm.new LoginAction();
        loginAction.actionPerformed(mockEvent);

        // Acceder a messageLabel con reflexión
        Field messageLabelField = LoginForm.class.getDeclaredField("messageLabel");
        messageLabelField.setAccessible(true);
        JLabel messageLabel = (JLabel) messageLabelField.get(loginForm);

        // Verificar que el mensaje de error sea mostrado
        assertEquals("Usuario o contraseña incorrectos...", messageLabel.getText());
    }

    @Test
    @DisplayName("Prueba de manejo de excepciones durante la autenticación")
    void testExceptionInAuthenticate() {
        // Configurar el mock para lanzar una SQLException
        try {
            when(mockLoginFormDAO.readAll()).thenThrow(new SQLException("Simulated SQL exception"));
        } catch (SQLException e) {
            fail("Mock setup failed");
        }

        // Crear instancia de LoginAction
        LoginForm.LoginAction loginAction = loginForm.new LoginAction();

        // Verificar que se lanza RuntimeException
        assertThrows(RuntimeException.class, () -> loginAction.authenticate("admin", "1234"));
    }


    /**
     * Método auxiliar para invocar el método authenticate de LoginAction.
     */
    private boolean invokeAuthenticate(LoginForm loginForm, String username, String password) {
        LoginForm.LoginAction loginAction = loginForm.new LoginAction();
        return loginAction.authenticate(username, password);
    }

}