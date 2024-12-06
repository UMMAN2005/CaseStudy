package com.secureweb.controls;

import com.secureweb.utils.Database;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT id, password_hash FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (storedHash.equals(hashPassword(password))) {
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", rs.getInt("id"));
                    session.setAttribute("username", username);
                    session.setMaxInactiveInterval(300);
                    response.sendRedirect("messageBoard.jsp");
                    return;
                }
            }
            logFailedLoginAttempt(username, request.getRemoteAddr());  // Log the failed attempt with IP address
            // An alert to send to login page.
            String alert = "<div class=\"alert\">\n" +
                    "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
                    "                            Wrong username or password!\n" +
                    "                        </p>\n" +
                    "                    </div>";
            // Set attribute for alert tag in login.jsp page.
            request.setAttribute("alert", alert);
            // Resend to login page.
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("Login failed: " + e.getMessage());
        }
    }

    private void logFailedLoginAttempt(String username, String ipAddress) {
        String insertSQL = "INSERT INTO failed_login_attempts (username, ip_address, reason) VALUES (?, ?, ?)";

        // Use Database to get the connection
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            // Sanitize the username if necessary (e.g., ensure no malicious input is logged)
            String sanitizedUsername = sanitizeInput(username);

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, sanitizedUsername);
            preparedStatement.setString(2, ipAddress);
            preparedStatement.setString(3, "Invalid username or password");

            // Execute the insert query to log the failed attempt
            preparedStatement.executeUpdate();

            // Log to the console or file (optional)
            logger.warning("Failed login attempt - Username: " + sanitizedUsername + ", IP: " + ipAddress);

        } catch (SQLException e) {
            logger.severe("Error logging failed login attempt: " + e.getMessage());
        }
    }

    // Sanitize input to prevent logging sensitive or malicious data
    private String sanitizeInput(String input) {
        // You can apply additional sanitization techniques (e.g., stripping out special characters)
        if (input != null) {
            return input.replaceAll("[^a-zA-Z0-9]", "");  // Remove non-alphanumeric characters
        }
        return "";
    }

    private String hashPassword(String password) throws Exception {
        return getString(password);
    }

    @NotNull
    static String getString(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}

