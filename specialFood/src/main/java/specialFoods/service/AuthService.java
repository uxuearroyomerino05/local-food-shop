
package specialFoods.service;

import org.springframework.stereotype.Service;

import specialFoods.dao.UserRepository;
import specialFoods.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
    
    // Storage to keep the session of the users that are logged in
    private static Map<String, User> tokenStore = new HashMap<>(); 
    
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Login method that checks if the user exists in the database and validates the password
    public Optional<String> login(String email, String password) {
        User user = userRepository.findByUsername(email);
        
        if (user != null && user.checkPassword(password)) {
            String token = generateToken();  // Generate a random token for the session
            tokenStore.put(token, user);     // Store the token and associate it with the user

            return Optional.of(token);
        } else {
        	return Optional.empty();
        }
    }
    
    // Logout method to remove the token from the session store
    public Optional<Boolean> logout(String token) {
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);

            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }
    
    // Method to get the user based on the token
    public User getUserByToken(String token) {
        return tokenStore.get(token); 
    }

    // Synchronized method to guarantee unique token generation
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}