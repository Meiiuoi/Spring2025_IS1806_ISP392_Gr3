<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <div id="DebtModal${listName.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl" role="document">
                <div class="modal-content">

                    <form action="${pageContext.request.contextPath}/Debts" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="service" value="addDebtInCustomers" />

                        <div class="modal-header">
                            <h4 class="modal-title">Add Debt</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        </div>

                        <div class="modal-body">
                            <!-- Debt Type Field -->
                            <div class="form-group">
                                <label>Status</label>
                                <select class="form-control" name="status">
                                    <option value="Customer repays debt">Customer repays debt</option> 
                                    <option value="Customer borrows debt">Customer pays</option>
                                    <option value="Owner repays debt">Owner repays debt</option> 
                                    <option value="Owner borrows debt">Owner borrows debt</option>
                                </select>
                            </div>

                            <!-- Amount Field -->
                            <div class="form-group">
                                <label>Amount</label>
                                <input type="text" oninput="formatNumber(event)" onblur="cleanInputBeforeSubmit(event)" class="form-control" name="amount" required />
                            </div>

                            <!-- Image Upload -->
                            <div class="form-group">
                                <label for="imageInput">Image</label>
                                <input type="file" name="image" id="imageInput" class="form-control-file">
                                <button type="button" class="btn btn-danger btn-sm mt-2" onclick="clearFile()">
                                    <i class="fas fa-times"></i> Clear
                                </button>
                            </div>


                            <div class="form-group">
                                <label>Created at</label>
                                <input type="datetime-local" name="created_at" value="" class="form-control" required />
                            </div>

                            <!-- Customer ID -->
                            <input type="hidden" name="customer_id" value="${listName.id}" />

                            <input type="hidden" name="createdBy" value="${sessionScope.username}" class="form-control" required />

                            <!-- Description -->
                            <div class="form-group">
                                <label>Description</label>
                                <textarea class="form-control" name="description" rows="3"></textarea>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
                            <input type="submit" class="btn btn-primary" value="Add Debt" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script>
            function clearFile() {
                document.getElementById("imageInput").value = "";
            }
        </script>
    </body>
</html>

