package com.secureweb.controls;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.secureweb.entities.Message;
import com.secureweb.daos.MessageDao;
import com.secureweb.utils.URLValidator;
import org.apache.commons.text.StringEscapeUtils;

public class MessageBoardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the search query from the request
        String searchQuery = request.getParameter("search");

        MessageDao messageDao = new MessageDao();
        List<Message> messages;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            // Perform the search if a query is provided
            messages = messageDao.searchMessages(searchQuery);
        } else {
            // Get all messages if no search query is provided
            messages = messageDao.getAllMessages();
        }

        request.setAttribute("messages", messages);
        RequestDispatcher dispatcher = request.getRequestDispatcher("messageBoard.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the posted message content and username from session
        String messageContent = request.getParameter("messageContent");
        String username = (String) request.getSession().getAttribute("username");

        // Escape the message content to prevent XSS
        messageContent = StringEscapeUtils.escapeHtml4(messageContent);

        // Optionally, check for URLs in the message content and ensure they're from a trusted domain
        if (containsUntrustedURL(messageContent)) {
            request.setAttribute("error", "The message contains a URL from an untrusted domain.");
            request.getRequestDispatcher("messageBoard.jsp").forward(request, response);
            return;
        }

        // Insert the sanitized message into the database
        MessageDao messageDao = new MessageDao();
        messageDao.addMessage(username, messageContent);

        // Redirect back to the message board page to display the new message
        response.sendRedirect("messageBoard");
    }

    // Helper method to check if the message contains an untrusted URL
    private boolean containsUntrustedURL(String messageContent) {
        // Extract URLs from the message content (simple regex for HTTP/HTTPS links)
        String regex = "https?://[\\w.-]+(?:/[\\w/?.&%#-]*)?";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(messageContent);

        while (matcher.find()) {
            String url = matcher.group();
            if (!URLValidator.isDomainWhitelisted(url)) {
                return true; // URL is untrusted
            }
        }

        return false; // All URLs are trusted
    }
}
