<%-- 
    Document   : editProduct
    Created on : Jan 26, 2025, 2:53:49 PM
    Author     : phamh
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
        <script src="h    ttps://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

        <title>Edit Product</title>

    </head>
    <body>
        <c:forEach var="product" items="${product}">
            <div class="form-container">
                <h4 class="modal-title">Edit Product</h4>
                <form action="Products" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="service" value="editProduct"/>
                    <input type="hidden" name="product_id" value="${product.getProductId()}" readonly/>

                    <label for="name">Enter product name:</label>
                    <div class="form-group">
                        <input type="text" id="name" class="form-control" name="name" value="${product.getName()}" readonly=""/>
                    </div>
                    <label for="image">Enter image URL:</label>
                    <input type="file" id="image" class="form-control" name="image" />
                    <input type="hidden" name="current_image" value="${product.getImage()}" />
                    <p style="color: red;">${requestScope.Notification}</p>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="price">Enter price:</label>
                            <input type="number" id="price" step="0.01" class="form-control" name="price" value="${product.getPrice()}" required/>
                            <p style="color: red;">${requestScope.Notification1}</p>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="wholesale_price">Enter wholesale price:</label>
                            <input type="number" id="wholesale_price" step="0.01" class="form-control" name="wholesale_price" value="${product.getWholesalePrice()}" required/>
                            <p style="color: red;">${requestScope.Notification2}</p>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="retail_price">Enter retail price:</label>
                            <input type="number" id="retail_price" step="0.01" class="form-control" name="retail_price" value="${product.getRetailPrice()}" required/>
                            <p style="color: red;">${requestScope.Notification3}</p>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="weight">Enter weight:</label>
                            <input type="number" id="weight" class="form-control" name="weight" value="${product.getWeight()}" required/>
                            <p style="color: red;">${requestScope.Notification4}</p>
                        </div>
                    </div>

                    <label for="location">Enter location:</label>
                    <input type="text" id="location" class="form-control" name="location" value="${product.getLocation()}" readonly=""/>

                    <label for="description">Enter description:</label>
                    <textarea id="description" class="form-control" name="description" rows="5" required>${product.getDescription()}</textarea>

                    <label for="location">Status</label><br>
                    <input type="radio" name="status" value="Available" checked/> Available 
                    <input type="radio" name="status" value="Run out" /> Run out
                    <br>
                    <input type="submit" class="btn btn-outline-primary" value="Save"/>
                </form>
            </div>
        </c:forEach> 
    </body>
</html>
