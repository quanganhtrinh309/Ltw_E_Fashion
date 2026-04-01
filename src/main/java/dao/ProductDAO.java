/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import util.DatabaseConnection;
import model.Product;
import java.util.*;
/**
 *
 * @author Chinh
 */
public class ProductDAO {
    public List<Product> getAllProduct(){
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT p.id, "
                + "p.product_name, "
                + "p.brand_id, "
                + "p.category_id, "
                + "p.is_active, "
                + "p.description, "
                + "MIN(v.price) AS display_price, "
                + "(SELECT image FROM product_variants WHERE product_id = p.id LIMIT 1) AS display_image "
                + "FROM product p "
                + "LEFT JOIN product_variants v ON p.id = v.product_id "
                + "GROUP BY p.id, p.product_name, p.brand_id, p.category_id, p.is_active, p.description;";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                String id = rs.getString("id");
                String product_name = rs.getString("product_name");
                String brand_id = rs.getString("brand_id");
                int category_id = rs.getInt("category_id");
                Boolean is_Active = rs.getBoolean("is_active");
                String description = rs.getString("description");
                Double display_price = rs.getDouble("display_price");
                String display_image = rs.getString("display_image");
            
                Product product = new Product(id, product_name, brand_id, category_id, is_Active, description, display_price, display_image);
                productList.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        return productList;
    }
    
    public Product getProductById(String id){
        String sql = "SELECT * FROM product WHERE id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                    rs.getString("id"), rs.getString("product_name"), rs.getString("brand_id"),
                    rs.getInt("category_id"), rs.getBoolean("is_active"),
                    rs.getString("description"), null, null
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }   
        
        return null;
    }
}
