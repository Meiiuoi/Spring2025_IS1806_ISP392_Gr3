<%-- 
    Document   : detaildebt
    Created on : Jan 27, 2025, 9:28:37 PM
    Author     : phamh
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
    </head>
    <body>
        <c:forEach var="customer" items="${list}">
            <div id="debtListModal${customer.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="debtListLabel${customer.id}" aria-hidden="true">
                <div class="modal-dialog modal-xl" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Debt List - record ${customer.debtNotes.size()}</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        </div>
                        <h3 class="font-weight-bold">${customer.name} - ${customer.phone}</h3>

                        <button type="button" class="btn btn-outline-success ml-auto mr-5" data-toggle="modal" data-target="#DebtModal${customer.id}">
                            <i class="fas fa-plus"></i> Add
                        </button>

                        <div class="modal-body">
                            <div class="table-container">
                                <table class="table-bordered" id="myTable1-${customer.id}" >

                                    <thead>
                                        <tr>
                                            <th class="resizable" onclick="sortTable1(${customer.id}, 0)">ID</th>
                                            <th class="resizable" onclick="sortTable1(${customer.id}, 1)">Amount</th>
                                            <th>Images</th>
                                            <th class="resizable">Description</th>
                                            <th class="resizable">Created At</th>
                                            <th class="resizable">Updated At</th>
                                            <th class="resizable">Created By</th>
                                            <th class="resizable">Status</th>
                                            <th class="sticky-col1">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>


                                        <c:forEach var="debt" items="${customer.debtNotes}">
                                            <tr>
                                                <td>${debt.debt_note_id}</td>
                                                <td class="${debt.type == '-' ? 'text-danger' : ''}">
                                                    <fmt:formatNumber value="${debt.type == '-' ? -debt.amount : debt.amount}" pattern="###,##0"/>
                                                </td>

                                                <td>
                                                    <img src="images/${debt.image}" class="myImg" style="width: 100px; height: 100px; object-fit: cover; cursor: pointer;" alt="Debt evidence"/>
                                                </td>
                                                <td>${debt.description}</td>
                                                <td>${debt.createdAt}</td>
                                                <td>${debt.updatedAt}</td>
                                                <td>${debt.createdBy}</td>
                                                <td>${debt.status}</td>
                                                <td class="sticky-col1">
                                                    <!-- Nút xem chi tiết nợ -->
                                                    <button type="button" class="btn btn-outline-primary openDebtModal" 
                                                            data-id="${debt.id}" 
                                                            data-amount="${debt.amount}" 
                                                            data-type="${debt.type}"
                                                            data-createdat="${debt.createdAt}"
                                                            data-description="${debt.description}" 
                                                            data-status="${debt.status}"
                                                            data-image="images/${debt.image}">
                                                        <i class="fas fa-info-circle"></i>
                                                    </button>


                                                </td>
                                            </tr>

                                            <!-- Modal hiển thị chi tiết nợ -->


                                        </c:forEach>

                                        <c:forEach begin="1" end="${10 - customer.debtNotes.size()}" varStatus="loop">
                                            <tr>
                                                <td colspan="9" style="height: 40px;">&nbsp;</td> <!-- Dòng trống -->
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <h4>Total Amount: <fmt:formatNumber value="${customer.balance}"/></h4>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

        </c:forEach>   
        <div class="modal fade" id="debtDetailModal" tabindex="-1" role="dialog" aria-labelledby="debtDetailLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Debt Details - <strong id="modalDebtId"></strong></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label><strong>Amount:</strong></label>
                            <input id="modalDebtAmount" type="text" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label><strong>Type:</strong></label>
                            <input id="modalDebtType" type="text" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label><strong>Created At:</strong></label>
                            <input id="modalDebtCreatedAt" type="text" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label><strong>Description:</strong></label>
                            <input id="modalDebtDescription" type="text" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label><strong>Status:</strong></label>
                            <input id="modalDebtStatus" type="text" class="form-control" readonly>
                        </div>
                        <div class="form-group">
                            <label><strong>Evidence:</strong></label>
                            <img id="modalDebtImage" class="myImg" style="width: 100%; height: auto; object-fit: cover; border-radius: 8px;" alt="Debt evidence">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>


        <!-- Modal để hiển thị ảnh -->
        <div id="myModal" class="modalImage">
            <span class="close">&times;</span>
            <img class="modalImage-content" id="img01">
            <div id="caption"></div>
        </div>
        <script>
            function myFunction(customerId) {
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("myInput");
                filter = input.value.toUpperCase();

                // Tạo ID động dựa trên customerId
                table = document.getElementById(`myTable1-${customer.id}`);

                if (!table)
                    return; // Tránh lỗi nếu bảng không tồn tại

                tr = table.getElementsByTagName("tr");

                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[0];
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }
            $("#myModal").on("show.bs.modal", function (e) {
                if ($(this).hasClass("already-open"))
                    return;
                $(this).addClass("already-open");
            });

            $(document).ready(function () {
                $(".openDebtModal").on("click", function () {
                    try {
                        console.log("Opening modal...");
                        let debtId = $(this).data("id");
                        let amount = $(this).data("amount");
                        let type = $(this).data("type");
                        let createdAt = $(this).data("createdat");
                        let description = $(this).data("description");
                        let status = $(this).data("status");
                        let imageSrc = $(this).data("image");

                        console.log("Debt Data:", {debtId, amount, type, createdAt, description, status, imageSrc});

                        $("#modalDebtId").text(debtId);
                        $("#modalDebtAmount").val(amount);
                        $("#modalDebtType").val(type);
                        $("#modalDebtCreatedAt").val(createdAt);
                        $("#modalDebtDescription").val(description);
                        $("#modalDebtStatus").val(status);

                        if (imageSrc && imageSrc !== "images/null") {
                            console.log("Loading image:", imageSrc);
                            $("#modalDebtImage").attr("src", imageSrc).show();
                        } else {
                            $("#modalDebtImage").hide();
                        }

                        $("#debtDetailModal").modal("show");
                        console.log("Modal shown successfully");
                    } catch (error) {
                        console.error("Error opening modal:", error);
                    }
                });

                $(".close, .btn-secondary").on("click", function () {
                    $("#debtDetailModal").modal("hide");
                });
            });


            document.addEventListener("DOMContentLoaded", function () {
                var modal = document.getElementById("myModal");
                var modalImg = document.getElementById("img01");
                var captionText = document.getElementById("caption");

                // Kiểm tra nếu modal có tồn tại
                if (!modal || !modalImg || !captionText) {
                    console.error("Modal elements not found!");
                    return;
                }

                // Lặp qua tất cả ảnh có class "myImg"
                document.querySelectorAll(".myImg").forEach(img => {
                    img.addEventListener("click", function () {
                        modal.style.display = "block";
                        modalImg.src = this.src;
                        captionText.innerHTML = this.alt;
                    });
                });

                // Đóng modal khi bấm vào nền đen
                modal.addEventListener("click", function () {
                    modal.style.display = "none";
                });
            });




        </script>
        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
        <script type="text/javascript" src="<%= request.getContextPath() %>/css/script.js"></script>

    </body>
</html>
