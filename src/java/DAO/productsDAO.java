/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Products;
import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author phamh
 */
public class productsDAO extends DBcontext {
public List<Products> viewAllProducts(String command, int index) {
    List<Products> list = new ArrayList<>();
    
    // Modify the SQL query to include the search filter (using LIKE)
    String sql = "SELECT * FROM products b ORDER BY " + command + " LIMIT 10 OFFSET ?";
    
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        // Set the parameters for the query
      
        st.setInt(1, (index - 1) * 10); 
        
        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Products product = new Products(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getBigDecimal("price"),
                        rs.getBigDecimal("wholesale_price"),
                        rs.getBigDecimal("retail_price"),
                        rs.getInt("weight"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getBoolean("isDelete"),
                        rs.getTimestamp("deletedAt"),
                        rs.getString("status")
                );
                list.add(product);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error fetching products: " + e.getMessage());
    }

    return list;
}
   

        public int countProducts(){
         String sql ="Select count(*) from products";
         try {
            PreparedStatement st=connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                    return rs.getInt(1);
            }
      
             } catch (SQLException e) {
        e.printStackTrace();
    }
         return 0;
     }
        public List<Products> searchProducts(String name) {
    String sql = "SELECT * FROM products WHERE name LIKE ?;";
    List<Products> productsList = new ArrayList<>();
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setString(1, "%" + name + "%");
        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Products products = new Products(
                      rs.getInt("product_id"),            
                        rs.getString("name"),          
                        rs.getString("image"),          
                        rs.getBigDecimal("price"),           
                        rs.getBigDecimal("wholesale_price"),  
                        rs.getBigDecimal("retail_price"),      
                        rs.getInt("weight"),                
                        rs.getString("location"),      
                        rs.getString("description"),        
                        rs.getTimestamp("created_at"),   
                        rs.getTimestamp("updated_at"),    
                        rs.getBoolean("isDelete"),         
                        rs.getTimestamp("deletedAt"),       
                        rs.getString("status") 
                );
                productsList.add(products);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return productsList;
}
        public List<Products> getProductById(int id) {
    List<Products> products = new ArrayList<>();
    String sql = "SELECT * FROM products WHERE product_id = ?";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, id);
        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Products product = new Products(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getBigDecimal("price"),
                        rs.getBigDecimal("wholesale_price"),
                        rs.getBigDecimal("retail_price"),
                        rs.getInt("weight"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at"),
                        rs.getBoolean("isDelete"),
                        rs.getDate("deletedAt"),
                        rs.getString("status")
                );
                products.add(product);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return products;
}

    public void editProduct(Products products) {
    String sql = "UPDATE products " +
                 "SET `name` = ?, " +
                 "`image` = ?, " +
                 "`price` = ?, " +
                 "`wholesale_price` = ?, " +
                 "`retail_price` = ?, " +
                 "`weight` = ?, " +
                 "`location` = ?, " +
                 "`description` = ?, " +
                 "`updated_at` = CURRENT_TIMESTAMP, " +
                 "`isDelete` = ?, " +
                 "`deletedAt` = ?, " +
                 "`status` = ? " +
                 "WHERE `product_id` = ?;";

    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setString(1, products.getName());
        st.setString(2, products.getImage());
        st.setBigDecimal(3, products.getPrice());
        st.setBigDecimal(4, products.getWholesalePrice());
        st.setBigDecimal(5, products.getRetailPrice());
        st.setInt(6, products.getWeight());
        st.setString(7, products.getLocation());
        st.setString(8, products.getDescription());

        // You don't need to set 'created_at' here since it's handled by the database (unless needed explicitly)
        // st.setDate(9, new java.sql.Date(products.getCreatedAt().getTime())); // Removed

        st.setBoolean(9, products.isIsDelete());  // Adjusted index since created_at was removed
        if (products.getDeletedAt() != null) {
            st.setDate(10, new java.sql.Date(products.getDeletedAt().getTime()));
        } else {
            st.setNull(10, java.sql.Types.DATE);
        }
        st.setString(11, products.getStatus());
        st.setInt(12, products.getProductId()); // Adjusted index

        st.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void insertProduct(Products products) {
    String sql = "INSERT INTO products (name, image, price, wholesale_price, retail_price, weight, location, description, created_at, updated_at, status) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, products.getName());
        st.setString(2, products.getImage());
        st.setBigDecimal(3, products.getPrice());
        st.setBigDecimal(4, products.getWholesalePrice());  // This now matches the column name in the table
        st.setBigDecimal(5, products.getRetailPrice());
        st.setInt(6, products.getWeight());
        st.setString(7, products.getLocation());
        st.setString(8, products.getDescription());
        st.setDate(9, new java.sql.Date(System.currentTimeMillis()));  // Set current timestamp for created_at
        st.setNull(10, java.sql.Types.DATE);
        st.setString(11, products.getStatus());
        st.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error for debugging
    }
}
       public void deleteProduct(int id) {
        String sql = "DELETE FROM products\n"
                + "      WHERE product_id=?";
        try{
            PreparedStatement st=connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {


        try {


            // Create a test product
   Products product = new Products();
product.setName("Example Product");
product.setImage("https://example.com/image.jpg");
product.setPrice(new BigDecimal("100.50"));
product.setWholesalePrice(new BigDecimal("80.00"));
product.setRetailPrice(new BigDecimal("120.00"));
product.setWeight(500);
product.setLocation("Warehouse 1, Shelf A");
product.setDescription("A detailed product description.");
product.setUpdatedAt(new Date(0)); // Set the current date
product.setStatus("Available");


            // Create a ProductDAO instance and call insertProduct
            productsDAO productDAO = new productsDAO();
            productDAO.insertProduct(product);

            System.out.println("Product inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace(); // Handle any errors during database connection or insertion
        }
    }

    
}
