<%@ page import="com.taxi.model.Driver" %>
<%@ page import="com.taxi.model.Car" %>
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
        table {
            width: 80%;
            margin: auto;
            border-collapse: collapse;
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        th.small-center {
            font-size: 12px;
            text-align: center;
        }
        .icon {
            display: inline-block;
            width: 16.3px;
            height: 13.45px;
            overflow: hidden;
        }
        .menu {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
    </style>
    <title>Display All Cars</title>
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
    <h2>All Cars:</h2>
</section>
<table>
    <thead>
    <tr>
        <th style="border-right: 1px solid black; text-align: center;">ID</th>
        <th colspan="2" style="border-right: 1px solid black; text-align: center;">Manufacturer</th>
        <th style="border-right: 1px solid black;">Model</th>
        <th colspan="2">Driver</th>
        <th></th>
    </tr>
    <tr>
        <th style="border-right: 1px solid black; border-bottom: 10px solid #9deea6"></th>
        <th class="small-center" style="border-bottom: 10px solid #9deea6">Name</th>
        <th class="small-center" style="border-right: 1px solid black; border-bottom: 10px solid #9deea6">Country</th>
        <th style="border-right: 1px solid black; border-bottom: 10px solid #9deea6"></th>
        <th class="small-center" style="border-bottom: 10px solid #9deea6">Name</th>
        <th class="small-center" style="border-bottom: 10px solid #9deea6">License Number</th>
        <th style="border-bottom: 10px solid #9deea6"></th>
    </tr>
    </thead>
    <tbody style="border-top: 2px solid black;">
    <c:forEach var="car" items="${cars}">
        <tr>
            <td style="border-right: 1px solid black;"><c:out value="${car.id}"/></td>
            <td><c:out value="${car.manufacturer.name}"/></td>
            <td style="border-right: 1px solid black;"><c:out value="${car.manufacturer.country}"/></td>
            <td style="border-right: 1px solid black;"><c:out value="${car.model}"/></td>
            <c:if test="${car.getDrivers().size() == 1}">
                <c:forEach var="driver" items="${car.getDrivers()}">
                    <td><c:out value="${driver.name}"/></td>
                    <td><c:out value="${driver.licenseNumber}"/></td>
                </c:forEach>
            </c:if>
        <c:if test="${car.getDrivers().size() > 1}">
            <c:set var="counter" value="1" />
            <c:forEach var="driver" items="${car.getDrivers()}">
                <td>${counter}. <c:out value="${driver.name}"/></td>
                <td><c:out value="${driver.licenseNumber}"/></td>
                <tr></tr>
                <td></td>
                <td></td>
                <td></td>
                <td style="border-right: 1px solid black;"></td>
                <c:set var="counter" value="${counter + 1}" />
            </c:forEach>
        </c:if>
            <c:if test="${empty car.getDrivers()}">
                <td style="background-color: #add8e6">No Driver Name</td>
                <td style="background-color: #add8e6">No Driver Lic Num</td>
            </c:if>
        <td>
            <a href="${pageContext.request.contextPath}/cars/delete?carId=${car.id}">
                <button name="delete" type="submit">
                    <span class="icon"><%@include file="../../resources/icons/delete-filled.svg"%></span>
                </button>
            </a>
        </td>
        </tr>
    </c:forEach>

    </tbody>
</table>
</body>
</html>
