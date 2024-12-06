<%@ page import="com.secureweb.entities.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Message Board</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<h2>Welcome to the Message Board</h2>

<!-- Search Box -->
<div style="text-align: right; padding-right: 20px;">
    <form action="messageBoard" method="GET">
        <input type="text" name="search" placeholder="Search messages..." />
        <button type="submit">Search</button>
    </form>
</div>

<!-- Form to post a new message -->
<div class="message-form">
    <form action="messageBoard" method="POST">
        <textarea name="messageContent" required placeholder="Write your message here..."></textarea><br>
        <button type="submit">Post Message</button>
    </form>
</div>

<!-- Warn users about URL restrictions (for future features) -->
<div>
    <p><strong>Note:</strong> URLs in messages are restricted to trusted domains only.</p>
</div>

<a href="upload.jsp"><button type="button">Upload Profile Picture</button></a>

<a href="logout">Logout</a>

<!-- Display previous messages -->
<div id="messages">
    <%
        List<Message> messages = (List<Message>) request.getAttribute("messages");
        if (messages != null && !messages.isEmpty()) {
            for (Message message : messages) {
    %>
    <div class="message">
        <p><strong><%= message.getUsername() %></strong></p>
        <p><%= StringEscapeUtils.escapeHtml4(message.getContent()) %></p> <!-- Escape content to prevent XSS -->
        <p><em>Posted at: <%= message.getCreatedAt() %></em></p>
    </div>
    <%
        }
    } else {
    %>
    <p>No results yet.</p>
    <%
        }
    %>
</div>

</body>
</html>