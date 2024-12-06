<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - SecureWeb Inc.</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<h2>Login</h2>

${alert}

<form method="POST" action="login">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">Login</button>
</form>
<a href="register.jsp">Don't have an account? Register here</a>
</body>
</html>

