package service;

import dao.UserDAO;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class UserService {

    public static User login(String username, String plainPassword) {
        User user = UserDAO.findByUsername(username);
        if (user != null && BCrypt.checkpw(plainPassword, user.getPasswordHash())) {
            if (!user.isActive()) {
                throw new RuntimeException("Tài khoản đã bị khóa!");
            }
            return user;
        }
        return null; 
    }

    public boolean register(User newUser, String plainPassword) {
        if (UserDAO.findByUsername(newUser.getUsername()) != null) {
            return false;
        }
        newUser.setId(UUID.randomUUID().toString());
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        newUser.setPasswordHash(hashedPassword);
        
        return UserDAO.addUser(newUser);
    }
}