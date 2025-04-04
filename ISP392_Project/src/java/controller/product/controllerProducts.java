/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import entity.Products;
import dal.productsDAO;
import dal.zoneDAO;
import entity.Zone;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phamh
 */
@WebServlet(name = "controllerProducts", urlPatterns = {"/Products"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class controllerProducts extends HttpServlet {

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        productsDAO products = new productsDAO();
        zoneDAO zonesDao = new zoneDAO();

        String fullName = (String) session.getAttribute("fullName");
        if (service == null) {
            service = "products";
        }
        if (service.equals("products")) {
            String storeIDStr = (String) session.getAttribute("storeID");
            int storeID = Integer.parseInt(storeIDStr);
            String indexPage = request.getParameter("index");
            String pageSizeStr = request.getParameter("pageSize");
            String command = request.getParameter("command");

            // Xử lý giá trị mặc định cho command
            if (command == null || command.isEmpty()) {
                command = (String) session.getAttribute("command");
                if (command == null || command.isEmpty()) {
                    command = "id DESC";
                }
            } else {
                session.setAttribute("command", command);
            }

            // Xử lý giá trị mặc định cho pageSize
            if (pageSizeStr == null) {
                pageSizeStr = (String) session.getAttribute("pageSize");
                if (pageSizeStr == null) {
                    pageSizeStr = "5"; // Mặc định 5 nếu chưa có
                }
            } else {
                session.setAttribute("pageSize", pageSizeStr);
            }
            int pageSize = Integer.parseInt(pageSizeStr);

            // Xử lý giá trị mặc định cho indexPage
            if (indexPage == null || indexPage.isEmpty()) {
                indexPage = "1";
            }
            int index = Integer.parseInt(indexPage);

            // Tính tổng số sản phẩm
            int count = products.countProducts(null, storeID);

            // Tính toán số trang dựa vào pageSize
            int endPage = count / pageSize;
            if (count % pageSize != 0) {
                endPage++;
            }

            // Lấy danh sách sản phẩm và Zone Active
            List<Products> listProducts = products.viewAllProducts(command, index, pageSize, storeID);
            List<String> listZoneName = zonesDao.getActiveZoneNames(); // Thay đổi ở đây

            // Đặt các thuộc tính cho request
            request.setAttribute("totalProducts", count);
            request.setAttribute("zoneName", listZoneName);
            request.setAttribute("list", listProducts);
            request.setAttribute("endPage", endPage);
            request.setAttribute("index", index);
            request.setAttribute("pageSize", pageSize);

            // Kiểm tra và truyền thông báo nếu có
            String notification = (String) request.getAttribute("Notification");
            if (notification != null && !notification.isEmpty()) {
                request.setAttribute("Notification", notification);
            }

            // Chuyển hướng tới trang JSP
            request.getRequestDispatcher("views/product/products.jsp").forward(request, response);
        }

        if (service.equals("searchProducts")) {
            String storeIDStr = (String) session.getAttribute("storeID");
            int storeID = Integer.parseInt(storeIDStr);
            String name = request.getParameter("browser");
            try {
                List<Products> list = products.searchProducts(name, false, storeID);
                request.setAttribute("list", list);
                request.setAttribute("name", name);
                request.getRequestDispatcher("views/product/products.jsp").forward(request, response);
            } catch (NumberFormatException e) {
            }
        }
        if (service.equals("UpdateStatusServlet")) {
            String indexStr = request.getParameter("index");
            int index = Integer.parseInt(indexStr);
            int productId = Integer.parseInt(request.getParameter("productId"));
            String newStatus = request.getParameter("status");
            boolean isUpdated = products.updateStatus(productId, newStatus);

            if (isUpdated) {
                response.sendRedirect("Products?service=products&index=" + index); // Load lại trang sau khi cập nhật
            } else {
                response.getWriter().println("Lỗi khi cập nhật trạng thái sản phẩm.");
            }
        }
        if (service.equals("searchProductsEditHistory")) {
            String storeIDStr = (String) session.getAttribute("storeID");
            int storeID = Integer.parseInt(storeIDStr);
            String name = request.getParameter("browser");
            try {
                List<Products> list = products.searchProducts(name, true, storeID);
                request.setAttribute("listHistory", list);
                request.setAttribute("name", name);
                request.getRequestDispatcher("views/product/productEditHistory.jsp").forward(request, response);
            } catch (NumberFormatException e) {
            }
        }

        if (service.equals("getProductById")) {
            String storeIDStr = (String) session.getAttribute("storeID");
            int storeID = Integer.parseInt(storeIDStr);
            String id_raw = request.getParameter("product_id");
            int id;
            try {
                id = Integer.parseInt(id_raw);
                List<Products> product = products.getProductById(id, storeID);
                request.setAttribute("list", product);
                request.getRequestDispatcher("views/product/detailProduct.jsp").forward(request, response);
            } catch (NumberFormatException e) {
            }
        }

        if (service.equals("detailProduct")) {
            String storeIDStr = (String) session.getAttribute("storeID");
            int storeID = Integer.parseInt(storeIDStr);
            int id = Integer.parseInt(request.getParameter("product_id"));
            List<Products> list = products.getProductById(id, storeID);
            request.setAttribute("product", list);
            request.getRequestDispatcher("views/product/editProduct.jsp").forward(request, response);

        }

        if (service.equals("editProduct")) {
            String storeIDStr = (String) session.getAttribute("storeID");
            int storeID = Integer.parseInt(storeIDStr);
            try {
                int productId = Integer.parseInt(request.getParameter("product_id"));
                String name = request.getParameter("name");
                Part file = request.getPart("image");
                String currentImage = request.getParameter("current_image");
                String imageFileName = (currentImage != null) ? currentImage : "";
                int index = Integer.parseInt(request.getParameter("index"));
                if (file != null && file.getSize() > 0) {
                    String fileType = file.getContentType();
                    if (!ALLOWED_MIME_TYPES.contains(fileType)) {
                        session.setAttribute("Notification", "Invalid file type! Only JPG, PNG, and GIF are allowed.");
                        response.sendRedirect("Products");
                        return;
                    }

                    imageFileName = getSubmittedFileName(file);
                    String uploadDirectory = "D:\\Ki_5\\Spring2025_IS1806_ISP392_Gr3\\ISP392_Project\\web\\views\\product\\images";
                    File uploadDir = new File(uploadDirectory);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    String uploadPath = uploadDirectory + File.separator + imageFileName;
                    try (FileOutputStream fos = new FileOutputStream(uploadPath); InputStream is = file.getInputStream()) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }

                BigDecimal price = new BigDecimal(request.getParameter("price"));
                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    session.setAttribute("Notification", "Price must be greater than 0.");
                    response.sendRedirect("Products");
                    return;
                }
                String quantityStr = request.getParameter("quantity");
                int quantity = Integer.parseInt(quantityStr);
                String createBy = request.getParameter("createBy");
                String deletedBy = request.getParameter("deletedBy");
                String description = request.getParameter("description");
                String status = request.getParameter("status");

                Date deletedAt = new java.sql.Date(System.currentTimeMillis());
                Date updatedAt = new java.sql.Date(System.currentTimeMillis());
                boolean isDelete = false;
                Date createdAt = new java.sql.Date(System.currentTimeMillis());
                String[] zoneNames = request.getParameterValues("zoneName");

                Products product = new Products(productId, name, imageFileName, price, quantity, description, createdAt, createBy, deletedAt, deletedBy, isDelete, updatedAt, status);
                List<Zone> zones = new ArrayList<>();
                if (zoneNames != null && zoneNames.length > 0) {
                    for (String zoneName : zoneNames) {
                        if (zoneName != null && !zoneName.trim().isEmpty()) {
                            Zone zone = new Zone();
                            zone.setName(zoneName);
                            zones.add(zone);
                        }
                    }
                }
//                boolean success = products.editProduct(product, zones);

                // Truyền fullName làm updatedBy
                boolean success = products.editProduct(product, zones, fullName, storeID);

                if (success) {
                    session.setAttribute("Notification", "Product updated successfully.");
                } else {
                    session.setAttribute("Notification", "Error updating product. Please try again.");
                }
                response.sendRedirect("Products?service=products&index=" + index);
            } catch (NumberFormatException e) {
                session.setAttribute("Notification", "Invalid number format.");
                response.sendRedirect("Products");
            } catch (NullPointerException e) {
                session.setAttribute("Notification", "Missing required parameters.");
                response.sendRedirect("Products");
            }
        }
        if (service.equals("addProduct")) {
            String storeIDStr = (String) session.getAttribute("storeID");
            int storeID = Integer.parseInt(storeIDStr);
            String name = request.getParameter("name");
            if (products.isProductNameExists(name, storeID)) {
                request.setAttribute("Notification", "Product name already exists!");
                request.getRequestDispatcher("Products?service=products").forward(request, response);
                return;
            }

            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("Notification", "Product name is required.");
                request.getRequestDispatcher("Products?service=products").forward(request, response);
                return;
            }

            Part file = request.getPart("image");
            String imageFileName = null;

            if (file != null && file.getSize() > 0) {
                String fileType = file.getContentType();
                if (!ALLOWED_MIME_TYPES.contains(fileType)) {
                    request.setAttribute("Notification", "Invalid file type! Only JPG, PNG, and GIF are allowed.");
                    request.getRequestDispatcher("Products?service=products").forward(request, response);
                    return;
                }
                imageFileName = getSubmittedFileName(file);

                String uploadDirectory = "D:\\Ki_5\\Spring2025_IS1806_ISP392_Gr3\\ISP392_Project\\web\\views\\product\\images";
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String uploadPath = uploadDirectory + File.separator + imageFileName;
                try (FileOutputStream fos = new FileOutputStream(uploadPath); InputStream is = file.getInputStream()) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("Notification", "Error uploading image.");
                    request.getRequestDispatcher("Products?service=products").forward(request, response);
                    return;
                }
            }

            String priceRaw = request.getParameter("price");
            BigDecimal price;
            try {
                price = new BigDecimal(priceRaw);
                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    request.setAttribute("Notification", "Price must be greater than 0.");
                    request.getRequestDispatcher("Products?service=products").forward(request, response);
                    return;
                }
            } catch (NumberFormatException | NullPointerException e) {
                request.setAttribute("Notification", "Invalid price format.");
                request.getRequestDispatcher("Products?service=products").forward(request, response);
                return;
            }

            int quantity;
            try {
                String quantityRaw = request.getParameter("quantity");
                quantity = (quantityRaw != null && !quantityRaw.isEmpty()) ? Integer.parseInt(quantityRaw) : 0;
                if (quantity < 0) {
                    request.setAttribute("Notification", "Quantity must be 0 or greater.");
                    request.getRequestDispatcher("Products?service=products").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("Notification", "Invalid quantity format.");
                request.getRequestDispatcher("Products?service=products").forward(request, response);
                return;
            }

            String description = request.getParameter("description");
            String createdBy = fullName; // Sử dụng fullName từ session
            String deletedBy = null; // Không cần deletedBy khi thêm mới
            String status = request.getParameter("status") != null ? request.getParameter("status") : "Active";
            String isDeleteRaw = request.getParameter("isDelete");
            boolean isDelete = Boolean.parseBoolean(isDeleteRaw);
            Date createdAt = new java.sql.Date(System.currentTimeMillis());
            Date updatedAt = null; // Chưa cập nhật khi mới thêm
            Date deletedAt = null; // Chưa xóa khi mới thêm

            Products product = new Products(0, name, imageFileName, price, quantity, description, createdAt, createdBy, deletedAt, deletedBy, isDelete, updatedAt, status);

            // Lấy danh sách Zone Active để kiểm tra
            List<String> activeZoneNames = zonesDao.getActiveZoneNames();
            String[] zoneNames = request.getParameterValues("zoneName");
            List<Zone> zones = new ArrayList<>();

            if (zoneNames != null) {
                for (String zoneName : zoneNames) {
                    if (zoneName != null && !zoneName.trim().isEmpty() && activeZoneNames.contains(zoneName)) {
                        Zone zone = new Zone();
                        zone.setName(zoneName);
                        zones.add(zone);
                    }
                }
            }

            boolean success = products.insertProduct(product, zones, fullName, storeID);

            if (success) {
                request.setAttribute("Notification", "Product added successfully!");
            } else {
                request.setAttribute("Notification", "Failed to add product!");
            }

            request.getRequestDispatcher("Products?service=products").forward(request, response);
        }

        if (service.equals("deleteProduct")) {
            String[] selectedProducts = request.getParameterValues("id");

            if (selectedProducts != null && selectedProducts.length > 0) {
                try {
                    for (String id : selectedProducts) {
                        int product_id = Integer.parseInt(id);
                        // Xóa sản phẩm
                        products.deleteProduct(product_id);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            response.sendRedirect("Products");
        }

    }

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
