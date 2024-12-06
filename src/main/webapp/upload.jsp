<link rel="stylesheet" type="text/css" href="css/style.css">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    session = request.getSession(false);
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload Profile Picture - SecureWeb Inc.</title>
</head>
<body>
<h2>Upload Profile Picture</h2>
<a href="messageBoard.jsp">Back to Message Board</a> | <a href="logout">Logout</a>

<form method="POST" action="upload" enctype="multipart/form-data">
    ${alert}
    <figure class="centered-figure">
        <img id="blah" src="images/blank_avatar.png" alt="your image"
             style="border-radius: 50%; height: 8em; width: 8em; object-fit: cover;">
    </figure>
    <input type="file" id="file" name="file" required style="width: 100%;"><br><br>
    <button type="submit">Upload</button>
</form>

<script>
    // Ensure script executes only after DOM is fully loaded
    document.addEventListener('DOMContentLoaded', function () {
        const fileInput = document.getElementById('file');
        const previewImage = document.getElementById('blah');

        fileInput.addEventListener('change', function (evt) {
            const [file] = fileInput.files;
            if (file) {
                previewImage.src = URL.createObjectURL(file);
            }
        });
    });
</script>
</body>
</html>
