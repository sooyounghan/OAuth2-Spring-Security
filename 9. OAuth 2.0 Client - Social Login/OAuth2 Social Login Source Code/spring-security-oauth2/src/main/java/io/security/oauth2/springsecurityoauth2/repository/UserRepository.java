package io.security.oauth2.springsecurityoauth2.repository;

import io.security.oauth2.springsecurityoauth2.model.users.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private Map<String, Object> users = new HashMap<>(); // DB 사용이 아니므로 Map 사용

    public User findByUsername(String username) {
        if(users.containsKey(username)) {
            return (User) users.get(username);
        }

        return null;
    }

    public void register(User user) {
        if(users.containsKey(user.getUsername())) {
            return; // 있으면, return
        }

        users.put(user.getUsername(), user); // 없으면 추가
    }
}
