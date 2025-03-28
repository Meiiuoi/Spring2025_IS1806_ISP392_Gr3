<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Store</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    </head>
    <body>
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
                    <div class="notifications">
                        <i class="fa-solid fa-bell"></i>
                    </div>
                    <div class="profile">
                        <img src="/ISP392_Project/views/dashboard/images/profile-img.jpg" id="profile-img" />
                        <div class="profile-menu">
                            <ul>
                                <li><a href="/ISP392_Project/user"><i class="fa-solid fa-pen"></i> User Profile</a></li>
                                <li><a href="/ISP392_Project/change-password"><i class="fa-solid fa-pen"></i> Change Password</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <!--   === Side Bar Starts ===   -->
            <aside class="side-bar">
                <!--   === Nav Bar Links Starts ===   -->
                <span class="menu-label">MENU</span>
                <ul class="navbar-links navbar-links-1">
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
                        <a href="sale">
                            <span class="nav-icon">
                                <i class="fa-solid fa-file-invoice"></i>
                            </span>
                            <span class="nav-text">Invoices</span>
                        </a>
                    </li>
                    <li>
                        <a href="/ISP392_Project/Debts">
                            <span class="nav-icon">
                                <i class="fa-solid fa-money-bill"></i>
                            </span>
                            <span class="nav-text">Debts History</span>
                        </a>
                    </li>
                    <li class="active">
                        <a href="/ISP392_Project/Stores">
                            <span class="nav-icon">
                                <i class="fa-solid fa-store"></i>
                            </span>
                            <span class="nav-text">Stores</span>
                        </a>
                    </li>
                    <c:if test="${sessionScope.role == 'owner'}">
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
                    <div class="settings">
                        <span class="gear-icon">
                            <i class="fa-solid fa-gear"></i>
                        </span>
                        <span class="text">Settings</span>
                    </div>
                    <div class="logoutBtn">
                        <a href="/ISP392_Project/logout">
                            <span class="logout-icon">
                                <i class="fa-solid fa-right-from-bracket"></i>
                            </span>
                            <span class="text"><a href="/ISP392_Project/logout">Logout</a></span>
                    </div>
                </div>
                <!--   === Side Bar Footer Ends ===   -->
            </aside>
            <!--   === Side Bar Ends ===   -->

            <div class="contents">
                <div class="panel-bar1">
                    <h2>Edit Store Information</h2>

                    <form id="storeForm" action="${pageContext.request.contextPath}/Stores" method="POST" onsubmit="confirmSave(event)">
                        <input type="hidden" name="service" value="editStore">
                        <input type="hidden" name="store_id" value="${store.id}">

                        <!-- Store Name -->
                        <div class="form-group">
                            <label for="name">Store Name</label>
                            <input type="text" class="form-control" name="name" value="${store.name}" required>
                            <c:if test="${not empty nameError}">
                                <div class="text-danger">${nameError}</div>
                            </c:if>
                        </div>

                        <!-- Address -->
                        <div class="form-group">
                            <label for="address">Address</label>
                            <input type="text" class="form-control" name="address" value="${store.address}" required>
                        </div>

                        <!-- Phone -->
                        <div class="form-group">
                            <label for="phone">Phone</label>
                            <input type="text" class="form-control" name="phone" value="${store.phone}" required>
                            <c:if test="${not empty phoneError}">
                                <div class="text-danger">${phoneError}</div>
                            </c:if>
                        </div>

                        <!-- Email -->
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" name="email" value="${store.email}" required>
                            <c:if test="${not empty emailError}">
                                <div class="text-danger">${emailError}</div>
                            </c:if>
                        </div>

                        <!-- Select Owner (only for admin) -->
                        <c:if test="${sessionScope.role == 'admin'}">
                            <div class="form-group">
                                <label for="owner">Owner</label>
                                <select class="form-control" name="owner">
                                    <option value="">--Select New Owner--</option>
                                    <c:forEach var="user" items="${ownerList}">
                                        <option value="${user.id}">
                                            ${user.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </c:if>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary" style="background-color: #007bff ">Save Changes</button>
                            <c:if test="${sessionScope.role == 'admin'}">
                                <a href="${pageContext.request.contextPath}/Stores?service=storeList" class="btn btn-secondary">Back</a>
                            </c:if>
                            <c:if test="${sessionScope.role == 'owner'}">
                                <a href="${pageContext.request.contextPath}/Stores?service=storeInfo" class="btn btn-secondary">Back</a>
                            </c:if>

                        </div>
                    </form>
                </div>
            </div>
            <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="confirmModalLabel">Confirm Changes</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            Are you sure to save the changes to this information?
                        </div>
                        <div class="modal-footer">

                            <button type="button" class="btn btn-primary" style="background-color: #007bff" id="saveChangesBtn">Save</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>

            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
            <script type="text/javascript" src="<%= request.getContextPath() %>/css/script.js"></script>
            <script type="text/javascript">
                        // Hàm xử lý khi bấm "Save" trong Modal
                        document.getElementById('saveChangesBtn').onclick = function () {
                            document.getElementById('storeForm').submit(); // Gửi form khi người dùng xác nhận
                        }

                        // Hàm gọi Modal để xác nhận
                        function confirmSave(event) {
                            event.preventDefault(); // Ngừng gửi form
                            $('#confirmModal').modal('show'); // Hiển thị Modal xác nhận
                        }
            </script>
