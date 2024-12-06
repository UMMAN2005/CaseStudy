package com.secureweb.controls;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.logging.Logger;

import com.secureweb.utils.Database;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

public class FileUploadServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");

            if (ServletFileUpload.isMultipartContent(request)) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);

                try (Connection conn = Database.getConnection()) {
                    for (FileItem item : upload.parseRequest(request)) {
                        if (!item.isFormField()) {
                            InputStream inputStream = item.getInputStream();

                            // Save the image to the database
                            String sql = "UPDATE users SET profile_picture = ? WHERE username = ?";
                            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                                statement.setBlob(1, inputStream);
                                statement.setString(2, username);
                                int rowsUpdated = statement.executeUpdate();

                                if (rowsUpdated > 0) {
                                    String alert = "<div style=\"color: green;\">\n" +
                                            "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
                                            "                            Uploaded successfully!\n" +
                                            "                        </p>\n" +
                                            "                    </div>";
                                    // Set attribute for alert tag in login.jsp page.
                                    request.setAttribute("alert", alert);
                                    // Resend to login page.
                                    request.getRequestDispatcher("upload.jsp").forward(request, response);
                                } else {
                                    String alert = "<div style=\"color: red;\">\n" +
                                            "                        <p style=\"font-family: Ubuntu-Bold; font-size: 18px; margin: 0.25em 0; text-align: center\">\n" +
                                            "                            An error occurred!\n" +
                                            "                        </p>\n" +
                                            "                    </div>";
                                    // Set attribute for alert tag in login.jsp page.
                                    request.setAttribute("alert", alert);
                                    request.getRequestDispatcher("upload.jsp").forward(request, response);
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    response.getWriter().println("File upload failed: " + ex.getMessage());
                }
            } else {
                response.getWriter().println("This servlet only handles file upload requests.");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
