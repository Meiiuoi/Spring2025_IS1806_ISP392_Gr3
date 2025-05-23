<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Zone</title>
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
                        <li class="active">
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
                <!--   === Side Bar Footer Ends ===   -->
            </aside>
            <!--   === Side Bar Ends ===   -->
            <!-- Main Content -->
            <div class="contents">
                <div class="panel-bar1">
                    <h2>Edit Zone</h2>
                    <c:if test="${not empty requestScope.nameError}">
                        <div class="alert alert-danger">${requestScope.nameError}</div>
                    </c:if>
                    <c:if test="${not empty requestScope.descriptionError}">
                        <div class="alert alert-danger">${requestScope.descriptionError}</div>
                    </c:if>
                    <c:if test="${not empty requestScope.statusError}">
                        <div class="alert alert-danger">${requestScope.statusError}</div>
                    </c:if>
                    <c:if test="${not empty requestScope.zoneIdError}">
                        <div class="alert alert-danger">${requestScope.zoneIdError}</div>
                    </c:if>
                    <form id="zoneForm" action="${pageContext.request.contextPath}/zones" method="POST" onsubmit="confirmSave(event)">
                        <input type="hidden" name="service" value="editZone" />
                        <input type="hidden" name="zone_id" value="${zone.id}" />
                        <input type="hidden" name="index" value="${index}" />
                        <input type="hidden" name="sortBy" value="${sortBy}" />
                        <input type="hidden" name="sortOrder" value="${sortOrder}" />

                        <div class="form-group">
                            <label for="name">Name</label>
                            <input type="text" class="form-control ${not empty requestScope.nameError ? 'is-invalid' : ''}" 
                                   name="name" value="${not empty requestScope.name ? requestScope.name : zone.name}" required>
                            <c:if test="${not empty requestScope.nameError}">
                                <div class="invalid-feedback">${requestScope.nameError}</div>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="description">Description</label>
                            <textarea class="form-control ${not empty requestScope.descriptionError ? 'is-invalid' : ''}" 
                                      name="description" rows="3">${not empty requestScope.description ? requestScope.description : zone.description}</textarea>
                            <c:if test="${not empty requestScope.descriptionError}">
                                <div class="invalid-feedback">${requestScope.descriptionError}</div>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="status">Status</label>
                            <select class="form-control ${not empty requestScope.statusError ? 'is-invalid' : ''}" 
                                    id="status" name="status">
                                <option value="Active" ${not empty requestScope.status ? (requestScope.status == 'Active' ? 'selected' : '') : (zone.status == 'Active' ? 'selected' : '')}>Active</option>
                                <option value="Inactive" ${not empty requestScope.status ? (requestScope.status == 'Inactive' ? 'selected' : '') : (zone.status == 'Inactive' ? 'selected' : '')}>Inactive</option>
                            </select>
                            <c:if test="${not empty requestScope.statusError}">
                                <div class="invalid-feedback">${requestScope.statusError}</div>
                            </c:if>
                        </div>
                        <input type="hidden" name="updateBy" value="${zone.createdBy}" />
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary" style="background-color: #007bff">Save Changes</button>
                            <a href="${pageContext.request.contextPath}/zones?service=zones&sortOrder=${param.sortOrder}" class="btn btn-secondary">Back to Zones List</a>
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
                            Are you sure to save the changes to this zone?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" style="background-color: #007bff" id="saveChangesBtn">Save</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
            <script type="text/javascript" src="<%= request.getContextPath() %>/css/script.js"></script>
            <script type="text/javascript">
                        document.getElementById('saveChangesBtn').onclick = function () {
                            document.getElementById('zoneForm').submit();
                        }
                        function confirmSave(event) {
                            event.preventDefault();
                            $('#confirmModal').modal('show');
                        }
            </script>
    </body>
</html>