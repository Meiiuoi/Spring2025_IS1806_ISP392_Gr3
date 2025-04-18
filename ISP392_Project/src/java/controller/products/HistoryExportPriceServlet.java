package controller.products;

import dal.DAOProduct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import entity.ProductPriceHistory;

public class HistoryExportPriceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        int currentPage = 1;
        int recordsPerPage = 5;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }

        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder == null || (!sortOrder.equals("asc") && !sortOrder.equals("desc"))) {
            sortOrder = "desc";
        }

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if (startDate == null || startDate.isEmpty()) {
            startDate = "2025-01-01";
        }
        if (endDate == null || endDate.isEmpty()) {
            endDate = LocalDate.now().toString();
        }

        List<ProductPriceHistory> HistoryList = new ArrayList<>();
        HistoryList = DAOProduct.INSTANCE.getExportPriceHistory(
                keyword, currentPage, recordsPerPage, userId, sortOrder, startDate, endDate
        );

        int totalRecords = DAOProduct.INSTANCE.getTotalHistoryExportRecords(keyword, userId, startDate, endDate);
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        request.setAttribute("HistoryList", HistoryList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        request.getRequestDispatcher("/views/priceHistory/historyExportPrice.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        String userName = (String) session.getAttribute("fullName");
        
        String productIdStr = request.getParameter("productId");
        String newPriceStr = request.getParameter("newPrice");
        
        if (productIdStr != null && newPriceStr != null) {
            try {
                int productId = Integer.parseInt(productIdStr);
                double newPrice = Double.parseDouble(newPriceStr);
                
                // Update product price and log history
                boolean success = DAOProduct.INSTANCE.updateProductPrice(productId, newPrice, userId, userName);
                if (success) {
                    response.sendRedirect("HistoryExportPriceServlet?success=1");
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        response.sendRedirect("HistoryExportPriceServlet?error=1");
    }
} 