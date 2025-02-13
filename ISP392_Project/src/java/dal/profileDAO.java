/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Users;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author THC
 */
public class profileDAO extends DBContext {

    public static final profileDAO INSTANCE = new profileDAO();
    private static final Logger LOGGER = Logger.getLogger(profileDAO.class.getName());
    private Connection connection;

    public profileDAO() {
        connection = getConnection();
    }

   public Users getUserById(int userId) {
    Users user = null;
    String sql = "SELECT id, username, password, image, name, phone, address, gender, dob, role, email, " +
                 "createdAt, updatedAt, status, deletedAt FROM users WHERE id = ?";

    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        ps = connection.prepareStatement(sql);
        ps.setInt(1, userId);
        rs = ps.executeQuery();

        if (rs.next()) {
            user = Users.builder()
                    .id(rs.getInt("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .image(rs.getString("image"))
                    .name(rs.getString("name"))
                    .phone(rs.getString("phone"))
                    .address(rs.getString("address"))
                    .gender(rs.getString("gender"))
                    .dob(rs.getDate("dob")) // Có thể kiểm tra NULL nếu cần
                    .role(rs.getString("role"))
                    .email(rs.getString("email"))
                    .createdAt(rs.getDate("createdAt"))
                    .updatedAt(rs.getDate("updatedAt"))
                    .status(rs.getString("status"))
                    .deletedAt(rs.getDate("deletedAt"))
                    .build();

            // Lấy danh sách hóa đơn của user
           // user.setInvoices(getUserInvoices(userId));
        }
    } catch (SQLException e) {
        LOGGER.severe("Error retrieving user by ID: " + e.getMessage());
    } finally {
        closeResources(ps, rs);
    }
    return user;
}
    
   public boolean updateUser(Users user) {
    String sql = "UPDATE users SET name = ?, email = ?, phone = ?, address = ?, gender = ?, dob = ?, status = ?, image = ? WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getAddress());
        ps.setString(5, user.getGender());
        ps.setDate(6, user.getDob());
        ps.setString(7, user.getStatus());
        ps.setString(8, user.getImage());
        ps.setInt(9, user.getId());
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}



    /**
     * Đóng các tài nguyên (PreparedStatement, ResultSet) sau khi truy vấn
     */
    private void closeResources(PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            LOGGER.severe("Error" + e.getMessage());
        }
    }
}

