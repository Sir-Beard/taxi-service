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
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    th {
      background-color: #f2f2f2;
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
  <title>Display All Manufacturers</title>
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
  <h2>All Manufacturers:</h2>
</section>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Country</th>
    <th></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="manufacturer" items="${manufacturers}">
    <tr>
      <td><c:out value="${manufacturer.id}"/></td>
      <td><c:out value="${manufacturer.name}"/></td>
      <td><c:out value="${manufacturer.country}"/></td>
      <td>
        <a href="${pageContext.request.contextPath}/manufacturers/delete?manufacturerId=${manufacturer.id}">
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
