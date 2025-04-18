<%-- 
    Document   : profile
    Created on : Feb 4, 2025, 1:51:14 PM
    Author     : THC
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="entity.Users"%>
<!DOCTYPE html>
<html>
    <head>
        <title>User Profile</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    </head>
    <body>
        <!--   *** Page Wrapper Starts ***   -->
        <div class="page-wrapper">
            <!--   *** Top Bar Starts ***   -->
            <div class="top-bar">
                <div class="top-bar-left">
                    <div class="hamburger-btn">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                    <div class="logo">
                        <img src="/ISP392_Project/views/dashboard/images/logo.png" style="width: 170px; height: 70px" />
                    </div>
                </div>


                <div class="top-bar-right">
                    <div class="mode-switch">
                        <i class="fa-solid fa-moon"></i>
                    </div>
                    <div class="notifications" style="font-size: 16px; color: #333;">
                        Howdy, <span style="font-weight: 600;">${sessionScope.user.name}</span>
                    </div>
                    <div class="profile">
                        <img src="${pageContext.request.contextPath}/views/profile/image/${sessionScope.user.image}" id="profile-img" onerror="this.src='${pageContext.request.contextPath}/views/dashboard/images/profile-img.jpg'"/>
                        <div class="profile-menu">
                            <ul>
                                <li><a href="/ISP392_Project/user"><i class="fa-solid fa-pen"></i> User Profile</a></li>
                                <li><a href="/ISP392_Project/change-password"><i class="fa-solid fa-pen"></i> Change Password</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <!--   *** Top Bar Ends ***   -->

            <!--   === Side Bar Starts ===   -->
            <aside class="side-bar">
                <!--   === Nav Bar Links Starts ===   -->
                <span class="menu-label">MENU</span>
                <ul class="navbar-links navbar-links-1">
                    <c:if test="${sessionScope.role == 'owner' || sessionScope.role == 'staff'}">
                        <li>
                            <a href="/ISP392_Project/dashboard">
                                <span class="nav-icon">
                                    <i class="fa-solid fa-house"></i>
                                </span>
                                <span class="nav-text">Dashboard</span>
                            </a>
                        </li>

                        <li>
                            <a href="/ISP392_Project/Products">
                                <span class="nav-icon">
                                    <i class="fas fa-box"></i>
                                </span>
                                <span class="nav-text">Products</span>
                            </a>
                        </li>
                        <li>
                            <a href="/ISP392_Project/zones">
                                <span class="nav-icon">
                                    <i class="fa-solid fa-table"></i>
                                </span>
                                <span class="nav-text">Zones</span>
                            </a>
                        </li>
                        <li>
                            <a href="/ISP392_Project/Customers">
                                <span class="nav-icon">
                                    <i class="fa-solid fa-user"></i>
                                </span>
                                <span class="nav-text">Customers</span>
                            </a>
                        </li>
                        <li>
                            <a href="/ISP392_Project/Orders">
                                <span class="nav-icon">
                                    <i class="fa-solid fa-file-invoice"></i>
                                </span>
                                <span class="nav-text">Orders</span>
                            </a>
                        </li>
                        <li>
                            <a href="/ISP392_Project/InvoiceHistory">
                                <span class="nav-icon">
                                    <i class="fa-solid fa-file-invoice"></i>
                                </span>
                                <span class="nav-text">Invoice History</span>
                            </a>
                        </li>
                        <li>
                            <a href="/ISP392_Project/Import">
                                <span class="nav-icon">
                                    <i class="fa-solid fa-file-invoice"></i>
                                </span>
                                <span class="nav-text">Imports</span>
                            </a>
                        </li>
                        <li>
                            <a href="/ISP392_Project/HistoryExportPriceServlet">
                                <span class="nav-icon">
                                    <i class="fa-solid fa-file-invoice"></i>
                                </span>
                                <span class="nav-text">Price History</span>
                            </a>
                        </li>
                    </c:if>
                    <li>
                        <a href="/ISP392_Project/Stores">
                            <span class="nav-icon">
                                <i class="fa-solid fa-store"></i>
                            </span>
                            <span class="nav-text">Stores</span>
                        </a>
                    </li>
                    <c:if test="${sessionScope.role == 'owner' || sessionScope.role == 'admin'}">
                        <li>
                            <a href="/ISP392_Project/Users">
                                <span class="nav-icon">
                                    <i class="fa-solid fa-user-tie"></i>
                                </span>
                                <span class="nav-text">Staffs</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
                <!--   === Nav Bar Links Ends ===   -->
                <!--   === Side Bar Footer Starts ===   -->
                <div class="sidebar-footer">
                    <div class="logoutBtn">
                        <a href="/ISP392_Project/logout">
                            <span class="logout-icon">
                                <i class="fa-solid fa-right-from-bracket"></i>
                            </span>
                            <span class="text"><a href="/ISP392_Project/logout">Logout</a></span>
                    </div>
                </div>
            </aside>
            <!--   === Side Bar Ends ===   -->

            <!--   === Profile Content Starts ===   -->
            <div class="contents">
                <div class="panel-bar1">
                    <h1>Profile</h1>

                    <div class="profile-container">
                        <!-- Avatar Section -->
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                background-color: #f4f4f4;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                height: 100vh;
                                margin: 0;
                            }

                            .avatar {
                                width: 150px;
                                height: 150px;
                                border-radius: 50%;
                                border: 3px solid #007BFF;
                                background-size: cover;
                                background-position: center;
                                background-repeat: no-repeat;
                                display: inline-block;
                                margin-bottom: 15px;
                            }

                            h1 {
                                font-size: 32px;
                                font-weight: 500;
                                color: #333;
                                margin-bottom: 40px;
                            }

                            h2 {
                                font-size: 20px;
                                font-weight: 500;
                                color: #777;

                            }

                            .profile-container {
                                display: flex;
                                flex-direction: column;
                                gap: 20px;
                            }

                            .profile-header {
                                display: flex;
                                align-items: flex-start;
                                gap: 20px;
                            }

                            .form-container {
                                flex: 1;
                                order: -1;
                            }

                            .avatar-section {
                                order: 1;
                            }

                            .form-row {
                                display: flex;
                                gap: 15px;
                                margin-bottom: 15px;
                            }

                            .form-group {
                                flex: 1;
                                display: flex;
                                flex-direction: column;
                            }

                            .form-group label {
                                font-size: 14px;
                                font-weight: bold;
                                color: #333;
                                margin-bottom: 8px;
                            }

                            .form-group .info {
                                font-size: 18px;
                                color: #333;
                                margin-bottom: 10px;
                            }

                            .alert .close {
                                position: absolute;
                                top: 10px;
                                right: 10px;
                                padding: 0;
                                border: none;
                                background: transparent;
                                font-size: 30px;


                            }


                            .btn:hover {
                                background-color: #0056b3;
                            }

                            @media (max-width: 768px) {
                                .profile-header {
                                    flex-direction: column;
                                    align-items: center;
                                }

                                .form-row {
                                    flex-direction: column;
                                    gap: 15px;
                                }

                                body {
                                    padding: 20px;
                                }
                            }
                        </style>

                        <c:if test="${not empty sessionScope.successMessage}">

                            <div class="alert alert-success alert-dismissible fade show">
                                <strong>Success!</strong> ${sessionScope.successMessage}
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <c:set var="successMessage" value="${sessionScope.successMessage}" />
                            <c:remove var="successMessage" />
                        </c:if>

                        <div class="profile-container">
                            <div class="profile-header">
                                <div class="avatar-section">
                                    <div class="avatar" style="background-image: url('${pageContext.request.contextPath}/views/profile/image/${user.image}');"></div>
                                </div>
                                <div class="form-container">
                                    <h2>Personal Information</h2>

                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="name">Name</label>
                                            <p class="info"><c:out value="${user.name}" /></p>
                                        </div>
                                        <div class="form-group">
                                            <label for="gender">Gender</label>
                                            <p class="info"><c:out value="${user.gender}" /></p>
                                        </div>
                                    </div>

                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="dob">Date of Birth</label>
                                            <p class="info"><c:out value="${user.dob}" /></p>
                                        </div>

                                        <div class="form-group">
                                            <label for="email">Email</label>
                                            <p class="info"><c:out value="${user.email}" /></p>

                                        </div>
                                    </div>

                                    <div class="form-row">
                                        <div class="form-group">
                                            <label for="phone">Phone</label>
                                            <p class="info"><c:out value="${user.phone}" /></p>
                                        </div>
                                        <div class="form-group">
                                            <label for="address">Address</label>
                                            <p class="info"><c:out value="${user.address}" /></p>
                                        </div>
                                    </div>


                                    <a href="${pageContext.request.contextPath}/views/profile/editProfile.jsp" class="btn btn-primary" style="background-color: #007bff ">Edit Profile</a>
                                    <c:if test="${sessionScope.role == 'admin'}">
                                        <a href="${pageContext.request.contextPath}/Stores?service=storeList" class="btn btn-secondary">Back</a>
                                    </c:if>
                                    <c:if test="${sessionScope.role == 'owner'}">
                                        <a href="${pageContext.request.contextPath}/Stores?service=storeInfo" class="btn btn-secondary">Back</a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--   === Profile Content Ends ===   -->
                </div>
                <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
                <script type="text/javascript" src="<%= request.getContextPath() %>/css/script.js"></script>
                </body>
                </html>

