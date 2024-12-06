package com.secureweb.controls;


import com.secureweb.utils.Database;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            String alert = "<div class=\"alert\">\n" +
                    "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
                    "                            Passwords do not match!\n" +
                    "                        </p>\n" +
                    "                    </div>";
            // Set attribute for alert tag in login.jsp page.
            request.setAttribute("alert", alert);
            // Resend to login page.
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

        Connection conn = null;
        try {
            conn = Database.getConnection();
            String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashPassword(password));
            stmt.executeUpdate();

            response.sendRedirect("login.jsp");
        } catch (Exception e) {
            response.getWriter().println("Registration failed: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                response.getWriter().println("Error closing connection: " + e.getMessage());
            }
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
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
