<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        header {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 10px;
        }
        nav {
            display: flex;
            justify-content: center;
            background-color: #555;
            padding: 10px;
        }
        nav button {
            margin: 0 10px;
            padding: 5px;
            background-color: #444;
            color: white;
            border: none;
            cursor: pointer;
        }
        nav button:hover {
            background-color: #666;
        }
        section {
            float: left;
            width: 80%;
            padding: 20px;
        }
        .menu {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .mail {
            text-decoration: none;
            background-color: #555;
            color: #FFFFF7;
        }
    </style>
    <title>TaxiService</title>
</head>
<body>
<header>
    <h1>TAXI SERVICE app!</h1>
</header>
<nav class="menu">
    <div style="margin: 5px">
        <button onclick="location.href='/'">Home Page</button>
        <button onclick="location.href='/manufacturers'">List of All Manufacturers</button>
        <button onclick="location.href='/drivers'">List of All Drivers</button>
        <button onclick="location.href='/cars'">List of All Cars</button>
    </div>
    <div>
        <button onclick="location.href='/cars/drivers/add'">Add Driver to a Car</button>
        <button onclick="location.href='/manufacturers/add'">Create Manufacturer</button>
        <button onclick="location.href='/drivers/add'">Create Driver</button>
        <button onclick="location.href='/cars/add'">Create Car</button>
    </div>
</nav>
<section>
    <h2>WELCOME!</h2>
    <p>Тут може бути ваша реклама. Звертайтесь: <a class="mail" href="mailto: oleksiyhubanov@gmail.com">oleksiyhubanov@gmail.com</a>
</section>
</body>
</html>
