package controller.order;

import dal.DAOProduct;
import dal.productsDAO;
import entity.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.OrderQueue;
import service.OrderTask;
import service.OrderWorker;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "controllerImport", urlPatterns = {"/Import"})
public class controllerImport extends HttpServlet {

    private DAOProduct daoProduct;

    @Override
    public void init() throws ServletException {
        super.init();
        daoProduct = DAOProduct.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if ("admin".equals(role)) {
            response.sendRedirect("/dashboard");
            return;
        }

        String invoiceNumber = Optional.ofNullable(request.getParameter("invoice")).orElse("1");
        request.setAttribute("invoiceNumber", invoiceNumber);
        request.getRequestDispatcher("views/invoice/import.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String fullName = (String) session.getAttribute("fullName");
        int userId = (int) session.getAttribute("userId");
        int storeId = Integer.parseInt((String) session.getAttribute("storeID"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String status = request.getParameter("status");

        double totalOrderPrice = Double.parseDouble(request.getParameter("totalOrderPriceHidden"));
        double balanceAmount = Double.parseDouble(request.getParameter("balanceAmount"));
        String balanceAction = request.getParameter("balanceAction");
        double epsilon = 1e-9;

        // Validate customer
        if (customerId <= 0) {
            request.setAttribute("message", "Please choose customer!");
            request.getRequestDispatcher("views/invoice/import.jsp").forward(request, response);
            return;
        }
        // Validate paid amount
        String paidAmountStr = request.getParameter("paidAmount");
        double paidAmount = 0;
        if (paidAmountStr != null && !paidAmountStr.trim().isEmpty()) {
            try {
                paidAmount = Double.parseDouble(paidAmountStr.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Payment error!");
                request.getRequestDispatcher("views/invoice/import.jsp").forward(request, response);
                return;
            }
        }
        try {
            String[] productIds = request.getParameterValues("productID");
            String[] productNames = request.getParameterValues("productName");
            String[] totalPrices = request.getParameterValues("totalPriceHidden");
            String[] unitPrices = request.getParameterValues("unitPrice");
            String[] quantities = request.getParameterValues("totalWeight");

            List<OrderDetails> orderDetailsList = new ArrayList<>();
            double calculatedTotal = 0;

            for (int i = 0; i < productIds.length; i++) {
                int productID = Integer.parseInt(productIds[i]);
                String productName = productNames[i];
                double total = Double.parseDouble(totalPrices[i]);
                double unitPrice = Double.parseDouble(unitPrices[i]);
                int quantity = Integer.parseInt(quantities[i]);

                double expectedPrice = unitPrice * quantity;

                if (quantity <= 0 || unitPrice <= 0 || total < 0 || Math.abs(expectedPrice - total) > epsilon) {
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid data\"}");
                    return;
                }

                calculatedTotal += expectedPrice;

                OrderDetails detail = OrderDetails.builder()
                        .productID(Products.builder().productId(productID).build())
                        .productName(productName)
                        .unitPrice(unitPrice)
                        .price(expectedPrice)
                        .importPrice(unitPrice)
                        .quantity(quantity)
                        .description("Import order detail")
                        .status("ACTIVE")
                        .storeId(Stores.builder().id(storeId).build())
                        .build();

                orderDetailsList.add(detail);
                
                // Cập nhật số lượng và giá nhập
                int currentQuantity = productsDAO.INSTANCE.getProductQuantity(productID);
                int newQuantity = currentQuantity + quantity;
                boolean updated = productsDAO.INSTANCE.updateProductQuantity(productID, newQuantity);
                if (!updated) {
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Failed to update stock.\"}");
                    return;
                }
                
                // Ghi log giá nhập vào lịch sử
                //daoProduct.logPriceChange(productID, unitPrice, "import", userId, customerId, fullName);
            }

            if (Math.abs(calculatedTotal - totalOrderPrice) > epsilon) {
                response.getWriter().write("{\"status\": \"error\", \"message\": \"Total mismatch\"}");
                return;
            }

            if (balanceAmount < 0 || (balanceAmount > 0 && "debt".equals(balanceAction))) {
                balanceAmount = paidAmount - calculatedTotal;
            } else {
                balanceAmount = 0;
            }

            Orders order = Orders.builder()
                    .customerID(Customers.builder().id(customerId).build())
                    .userID(Users.builder().id(userId).build())
                    .storeId(Stores.builder().id(storeId).build())
                    .type("Import")
                    .amount(calculatedTotal)
                    .paidAmount(paidAmount)
                    .description(status)
                    .status("PENDING")
                    .createdBy(fullName)
                    .build();

            OrderWorker.startWorker();
            OrderWorker.clearProcessedOrder(userId);
            
            OrderQueue.addOrder(new OrderTask(order, orderDetailsList, balanceAction, fullName));

            // Đợi một chút để đảm bảo order đã được xử lý
            Thread.sleep(100);

            // Ghi log giá nhập với order_id
            for (OrderDetails detail : orderDetailsList) {
                int orderId = daoProduct.getLatestOrderId(userId);
                if (orderId != -1) {
                    daoProduct.logPriceChange(
                        detail.getProductID().getProductId(), 
                        detail.getUnitPrice(), 
                        "import",
                        userId,
                        orderId,
                        fullName
                    );
                }
            }

            request.setAttribute("message", "success");
            request.getRequestDispatcher("views/invoice/import.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(controllerImport.class.getName()).log(Level.SEVERE, "Import failed", e);
            request.setAttribute("message", "error");
            request.getRequestDispatcher("views/invoice/import.jsp").forward(request, response);
        }
    }
}
