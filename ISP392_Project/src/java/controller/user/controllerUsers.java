/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.userDAO;
import entity.Stores;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import utils.GlobalUtils;

/**
 *
 * @author THC
 */
@WebServlet(name = "controllerUsers", urlPatterns = {"/Users"})
@MultipartConfig

public class controllerUsers extends HttpServlet {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$");

    userDAO userDAO = new userDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        if (service == null) {
            service = "users";
        }
        String sortBy = request.getParameter("sortBy");
        if (sortBy == null) {
            sortBy = "id";
        }

        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder == null || (!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC"))) {
            sortOrder = "ASC";
        }

        switch (service) {
            case "users": {
                String keyword = request.getParameter("searchUser");
                if (keyword == null) {
                    keyword = "";
                }
                int index = 1;
                try {
                    index = Integer.parseInt(request.getParameter("index"));
                } catch (NumberFormatException ignored) {
                }
                int total = userDAO.countUsers(keyword);
                int endPage = (total % 5 == 0) ? total / 5 : (total / 5) + 1;

                List<Users> list = userDAO.searchUsers(keyword, index, 5, sortBy, sortOrder);

                request.setAttribute("list", list);
                request.setAttribute("endPage", endPage);
                request.setAttribute("index", index);
                request.setAttribute("searchUser", keyword);
                request.setAttribute("sortBy", sortBy);
                request.setAttribute("sortOrder", sortOrder);
                request.getRequestDispatcher("views/user/users.jsp").forward(request, response);
                break;
            }

            case "getUserById": {
                int id = Integer.parseInt(request.getParameter("user_id"));
                Users user = userDAO.getUserById(id);
                request.setAttribute("user", user);

                request.getRequestDispatcher("views/user/detailUser.jsp").forward(request, response);
                break;
            }
            case "editUser": {

                int userId = Integer.parseInt(request.getParameter("user_id"));
                Users userForEdit = userDAO.getUserById(userId);
                request.setAttribute("user", userForEdit);
                String userName = (String) session.getAttribute("username");
                request.setAttribute("userName", userName);

                request.getRequestDispatcher("views/user/detailUser.jsp").forward(request, response);
                break;
            }
            case "createAccount": {
                request.getRequestDispatcher("views/user/addUser.jsp").forward(request, response);
                break;
            }

            case "addInfor": {
                request.getRequestDispatcher("views/user/addInforUser.jsp").forward(request, response);
                break;

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");

        if ("editUser".equals(service)) {

            int userId = Integer.parseInt(request.getParameter("user_id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String status = request.getParameter("status");
            String role = request.getParameter("role");
            String updatedBy = request.getParameter("updatedBy");
            java.sql.Date dob = java.sql.Date.valueOf(request.getParameter("dob"));

            if (status == null || status.isEmpty()) {
                status = "Active";
            }
            boolean emailExists = userDAO.checkEmailExists(email, userId);
            boolean phoneExists = userDAO.checkPhoneExists(phone, userId);

            if (emailExists) {
                request.setAttribute("emailError", "Email already exists.");
                request.setAttribute("user", getUserFromRequest(request));
                request.getRequestDispatcher("views/user/detailUser.jsp").forward(request, response);
                return;
            }
            if (phoneExists) {
                request.setAttribute("phoneError", "Phone number already exists.");
                request.setAttribute("user", getUserFromRequest(request));
                request.getRequestDispatcher("views/user/detailUser.jsp").forward(request, response);
                return;
            }
            if (!phone.matches("^0\\d{9}$")) {
                request.setAttribute("phoneError", "Invalid phone number format.");
                request.setAttribute("user", getUserFromRequest(request));
                request.getRequestDispatcher("views/user/detailUser.jsp").forward(request, response);
                return;
            }
            Users user = Users.builder()
                    .id(Integer.parseInt(request.getParameter("user_id")))
                    .name(request.getParameter("name"))
                    .phone(request.getParameter("phone"))
                    .address(request.getParameter("address"))
                    .gender(request.getParameter("gender"))
                    .dob(java.sql.Date.valueOf(request.getParameter("dob")))
                    .role(role)
                    .email(request.getParameter("email"))
                    .status(status)
                    .build();
            userDAO.editUser(user);

            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "User details updated successfully.");
            response.sendRedirect("Users?service=users");
        }
        if ("createAccount".equals(service)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            // Lấy store_id từ session
            HttpSession session = request.getSession();
            String storeIdString = (String) session.getAttribute("storeID"); // Lấy store_id dưới dạng String

            Integer storeId = null;

            if (storeIdString != null) {
                // Nếu store_id không null, chuyển storeIdString thành Integer
                try {
                    storeId = Integer.parseInt(storeIdString); // Chuyển đổi String thành Integer
                } catch (NumberFormatException e) {
                    request.setAttribute("storeError", "Invalid Store ID format.");
                    request.getRequestDispatcher("views/user/addUser.jsp").forward(request, response);
                    return;
                }
            } else {
                // Nếu store_id là null, gán store_id của người mới là null
                storeId = null;
            }
            userDAO dao = new userDAO();
            if (storeId != null && !dao.isStoreIdValid(storeId)) {
                request.setAttribute("storeError", "Invalid Store ID.");
                request.getRequestDispatcher("views/user/addUser.jsp").forward(request, response);
                return;
            }
            // Kiểm tra mật khẩu hợp lệ
            if (!PASSWORD_PATTERN.matcher(password).matches()) {
                request.setAttribute("passwordError", "Password must be at least 6 characters long, including at least one letter and one number.");
                request.getRequestDispatcher("views/user/addUser.jsp").forward(request, response);
                return;
            }

            if (!password.equals(confirmPassword)) {
                request.setAttribute("passwordError", "Confirm password does not match.");
                request.getRequestDispatcher("views/user/addUser.jsp").forward(request, response);
                return;
            }

            password = GlobalUtils.getMd5(password);  // Mã hóa mật khẩu

            // Kiểm tra xem tên người dùng đã tồn tại chưa
            if (userDAO.checkUsernameExists(username)) {
                request.setAttribute("usernameError", "Username already exists.");
                request.getRequestDispatcher("views/user/addUser.jsp").forward(request, response);
                return;
            }
            String role = "staff"; // Mặc định là "staff"
            if ("admin".equals(session.getAttribute("role"))) {  // Kiểm tra nếu người dùng là admin
                role = "owner";  // Nếu là admin, gán role là owner
            }
            // Lấy thông tin cửa hàng từ storeId (store_id đã lấy từ session)
            Stores store = null;
            if (storeId != null) {
                store = dao.getStoreById(storeId);
                if (store == null) {
                    request.setAttribute("storeError", "Invalid store ID.");
                    request.getRequestDispatcher("views/user/addUser.jsp").forward(request, response);
                    return;
                }
            }
            // Tạo đối tượng User với thông tin từ form và thông tin cửa hàng
            Users user = new Users();
            user.setUsername(username);
            user.setPassword(password);
            user.setImage("default.png");
            user.setEmail("Pending");
            user.setName("Pending");
            user.setPhone("Pending");
            user.setAddress("Pending");
            user.setGender("Pending");
            user.setDob(null);
            user.setStatus("Active");
            user.setRole(role);

            // Nếu store_id không null thì gán store_id của người tạo tài khoản cho người mới
            if (storeId != null) {
                user.setStoreId(store);  // Gán store_id của người tạo tài khoản cho người mới
            } else {
                user.setStoreId(null); // Nếu store_id là null, người mới sẽ không có store_id
            }

            boolean isInserted = dao.insertUsers(user);
            if (isInserted) {
                session.setAttribute("successMessage", "Account created successfully.");
                session.setAttribute("userId", dao.getUserByUsername(username).getId());
                request.getRequestDispatcher("views/user/addInforUser.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "An error occurred while creating the account.");
                request.getRequestDispatcher("views/user/addUser.jsp").forward(request, response);
            }
        }
        if ("addInfo".equals(service)) {
            HttpSession session = request.getSession();
            int userId = (int) session.getAttribute("userId");

            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");
            java.sql.Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
            Part file = request.getPart("image");
            String imageFileName = null;

            if (userDAO.checkPhoneExists(phone, userId)) {
                request.setAttribute("phoneError", "Phone already exists.");
                request.setAttribute("name", name);
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("gender", gender);
                request.setAttribute("dob", dob);
                request.getRequestDispatcher("views/user/addInforUser.jsp").forward(request, response);
                return;
            }

            if (userDAO.checkEmailExists(email, userId)) {
                request.setAttribute("emailError", "Email already exists.");
                request.setAttribute("name", name);
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("gender", gender);
                request.setAttribute("dob", dob);
                request.getRequestDispatcher("views/user/addInforUser.jsp").forward(request, response);
                return;
            }
            
            if (!phone.matches("^0\\d{9}$")) {
                request.setAttribute("phoneError", "Invalid phone number format.");
                request.setAttribute("name", name);
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("address", address);
                request.setAttribute("gender", gender);
                request.setAttribute("dob", dob);
                request.getRequestDispatcher("views/user/addInforUser.jsp").forward(request, response);
                return;
            }
            if (file != null && file.getSize() > 0) {
                imageFileName = getSubmittedFileName(file);
                String uploadDirectory = getServletContext().getRealPath("/") + "images";
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

            Users user = userDAO.getUserById(userId);
            user.setName(name);
            user.setPhone(phone);
            user.setEmail(email);
            user.setAddress(address);
            user.setGender(gender);
            user.setDob(dob);
            user.setImage(imageFileName);

            userDAO.updateUserInfo(user);
            session.removeAttribute("userId");
            response.sendRedirect("Users?service=users");
        }
    }

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    private Users getUserFromRequest(HttpServletRequest request) {
        Users user = new Users();
        user.setId(Integer.parseInt(request.getParameter("user_id")));
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));
        user.setDob(java.sql.Date.valueOf(request.getParameter("dob")));
        user.setGender(request.getParameter("gender"));
        user.setStatus(request.getParameter("status"));
        user.setRole(request.getParameter("role"));
        return user;
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
