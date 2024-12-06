<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to Global Chat</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background: linear-gradient(120deg, #6a11cb, #2575fc);
            color: #fff;
        }
        .container {
            text-align: center;
            background: rgba(0, 0, 0, 0.5);
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        h1 {
            font-size: 2.5rem;
            margin-bottom: 10px;
        }
        p {
            font-size: 1.2rem;
            margin: 15px 0;
        }
        a {
            text-decoration: none;
            color: #ffdf00;
            font-weight: bold;
        }
        a:hover {
            color: #ffe567;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome to Global Chat</h1>
    <p>Connect with the world. <a href="login.jsp">Login</a> to start chatting!</p>
</div>
</body>
</html>