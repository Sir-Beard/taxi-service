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
    .input-container {
      display: flex;
      align-items: center;
      margin-top: 10px;
    }
    .input-label {
      min-width: 100px;
      padding-right: 10px;
      text-align: right;
    }
    .input-field {
      max-width: 40ch;
      width: 80%;
    }
    .create-button {
      margin-top: 10px;
      background-color: #007bff;
      color: white;
      border: none;
      padding: 5px 10px;
      cursor: pointer;
    }
    .input-section {
      display: inline-grid;
      flex-direction: column;
      align-items: flex-start;
      margin-top: 10px;
      margin-left: 50%;
    }
    .menu {
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  </style>
  <title>Create Car</title>
</head>
<body>
<header>
  <h1>TAXI SERVICE app!</h1>
</header>
<nav class="menu">
  <div style="margin: 5px">
    <button onclick="location.href='/'">Home Page</button>
    <button onclick="location.href='/DisplayAllManufacturersController'">List of All Manufacturers</button>
    <button onclick="location.href='/DisplayAllDriversController'">List of All Drivers</button>
    <button onclick="location.href='/DisplayAllCarsController'">List of All Cars</button>
  </div>
  <div>
    <button onclick="location.href='/AddDriverToCarController'">Add Driver to a Car</button>
    <button onclick="location.href='/ManufacturerController'">Create Manufacturer</button>
    <button onclick="location.href='/DriverController'">Create Driver</button>
    <button onclick="location.href='/CarController'">Create Car</button>
  </div>
</nav>
<section>
  <h2>Please, create a car:</h2>
  <form method="post" action="CarController">
    <div class="input-section">
      <div class="input-container">
        <label class="input-label" for="carManufacturerId">Manufacturer ID:</label>
        <input class="input-field" type="number" name="manufacturerId" id="carManufacturerId" />
      </div>
      <div class="input-container">
        <label class="input-label" for="carModel">Model:</label>
        <input class="input-field" type="text" name="model" id="carModel" />
      </div>
      <button class="create-button" name="submit" type="submit">Create</button>
    </div>
  </form>
</section>
</body>
</html>
