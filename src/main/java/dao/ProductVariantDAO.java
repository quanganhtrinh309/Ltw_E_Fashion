package dao;

import model.ProductVariant;
import java.util.*;
import java.sql.*;
import util.DatabaseConnection;

public class ProductVariantDAO {
    public List<ProductVariant> getProductVariantsByProductId(String productId){
        String sql = "SELECT * FROM product_variants WHERE product_id = ?";
        List<ProductVariant> productVariantsList = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String product_id = rs.getString("product_id");
                String color = rs.getString("color");
                String size = rs.getString("size");
                String image = rs.getString("image");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                Boolean is_active = rs.getBoolean("is_active");
                
                ProductVariant productVariants = new ProductVariant(id, product_id, color, size, image, price, stock, is_active);
                productVariantsList.add(productVariants);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        return productVariantsList;
    }
}
