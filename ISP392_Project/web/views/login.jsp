<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<html>
    <head>
        <title>Login Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            /* General Styling */
            body, html {
                height: 100%;
                margin: 0;
                font-family: Arial, sans-serif;
                background-color: #e9ecef;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            /* Container Styling */
            .container {
                width: 100%;
                max-width: 400px;
                padding: 30px 25px;
                background-color: #ffffff;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.15);
                text-align: center;
            }

            /* Header Styling */
            h1 {
                color: #333;
                font-size: 24px;
                margin-bottom: 20px;
            }

            /* Table Styling */
            table {
                width: 100%;
                margin-bottom: 15px;
            }

            td {
                padding: 8px 5px;
                text-align: left;
                font-size: 16px;
            }

            /* Input Fields Styling */
            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 16px;
            }

            /* Submit Button Styling */
            input[type="submit"] {
                width: 100%;
                padding: 12px 0;
                background-color: #007bff;
                color: white;
                font-size: 16px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            input[type="submit"]:hover {
                background-color: #45a049;
            }
            .forgot-password {
                display: block;
                text-align: right;
                font-size: 14px;
                color: #007bff;
                text-decoration: none;
                margin-top: 5px;
                transition: color 0.3s ease;
            }

            .forgot-password:hover {
                color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Login</h1>
            <form id="loginForm" action="loginServlet" method="post">
                <table>
                    <tr>
                        <td>User name:</td>
                        <td><input type="text" name="username" required /></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="password" required />
                            <a href="/ISP392_Project/forgotpw" class="forgot-password">Forgot password?</a></td>
                    </tr>
                </table>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" style="color: red">${error}</div>
                </c:if>
                
                
                <br/>
                <input type="submit" value="Login" />
            </form>
        </div>

    </body>
</html>

