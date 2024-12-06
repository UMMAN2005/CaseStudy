package com.secureweb.daos;

import com.secureweb.entities.Message;
import com.secureweb.utils.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    private static final Logger log = LoggerFactory.getLogger(MessageDao.class);

    // Method to get all messages
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM MESSAGES ORDER BY created_at DESC")) {

            while (rs.next()) {
                Message message = new Message();
                message.setUsername(rs.getString("username"));
                message.setContent(rs.getString("content"));
                message.setCreatedAt(Message.formatTimestamp(rs.getTimestamp("created_at")));
                messages.add(message);
            }
        } catch (SQLException e) {
            log.error("Failed to get all messages: {}", e.getMessage());
        }
        return messages;
    }

    // Method to search messages based on the search query
    public List<Message> searchMessages(String searchQuery) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM MESSAGES WHERE content LIKE ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + searchQuery + "%");  // Use LIKE for partial matches

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Message message = new Message();
                    message.setUsername(rs.getString("username"));
                    message.setContent(rs.getString("content"));
                    message.setCreatedAt(String.valueOf(rs.getTimestamp("created_at")));
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to search messages: {}", e.getMessage());
        }
        return messages;
    }

    // Method to add a new message
    public void addMessage(String username, String content) {
        String sql = "INSERT INTO MESSAGES (username, content, created_at) VALUES (?, ?, NOW())";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, content);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to add message: {}", e.getMessage());
        }
    }
}
