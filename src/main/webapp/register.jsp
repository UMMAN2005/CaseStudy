<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Register - SecureWeb Inc.</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<h2>Register</h2>
${alert}
<form method="POST" action="register">

  <label for="username">Username:</label>
  <input type="text" id="username" name="username" required><br><br>

  <label for="password">Password:</label>
  <input type="password" id="password" name="password" required><br><br>

  <label for="confirmPassword">Confirm P:</label>
  <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>

  <button type="submit">Register</button>
</form>
<a href="login.jsp">Already have an account? Login here</a>
</body>
</html>
