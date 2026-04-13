package dao;

import java.sql.*;
import java.util.*;
import model.Category;
import util.DatabaseConnection;

public class CategoryDAO {

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Integer parentId = rs.getObject("parent_id") != null
                        ? rs.getInt("parent_id")
                        : null;

                list.add(new Category(
                    rs.getInt("id"),
                    parentId,
                    rs.getString("name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public String getFullCategory(int categoryId) {
        String sql = "SELECT id, name, parent_id FROM category WHERE id = ?";
        StringBuilder fullName = new StringBuilder();

        try (Connection conn = DatabaseConnection.getConnection()) {

            Integer currentId = categoryId;

            while (currentId != null) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, currentId);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String name = rs.getString("name");

                        Integer parentId = rs.getObject("parent_id") != null
                                ? rs.getInt("parent_id")
                                : null;

                        if (fullName.length() == 0) {
                            fullName.insert(0, name);
                        } else {
                            fullName.insert(0, name + " > ");
                        }

                        currentId = parentId;
                    } else {
                        break;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fullName.toString();
    }
    
    public List<Category> getParentCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category WHERE parent_id IS NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category c = new Category(
                    rs.getInt("id"),
                    null, // cha không có parent
                    rs.getString("name")
                );
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Category> getChildCategories(int parentId) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category WHERE parent_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, parentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Category c = new Category(
                    rs.getInt("id"),
                    rs.getInt("parent_id"),
                    rs.getString("name")
                );
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
}