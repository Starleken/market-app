package org.alfadev.mainservice.core.db;

import lombok.RequiredArgsConstructor;
import org.alfadev.mainservice.core.utils.entity.UserEntityUtils;
import org.alfadev.mainservice.entity.UserEntity;
import org.alfadev.mainservice.repository.UserRepository;
import org.alfadev.mainservice.service.EncodingService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDbHelper {

    private final UserRepository userRepository;
    private final EncodingService encodingService;

    public void clearDb() {
        userRepository.deleteAll();
    }

    public UserEntity saveUser() {
        var generated = UserEntityUtils.generateUser();
        generated.setPassword(encodingService.encode(generated.getPassword()));
        return userRepository.save(generated);
    }

    public UserEntity saveUser(String password) {
        var generated = UserEntityUtils.generateUser();
        generated.setPassword(encodingService.encode(password));
        return userRepository.save(generated);
    }

    public List<UserEntity> saveUsers(int count) {
        List<UserEntity> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            var saved = saveUser();
            users.add(saved);
        }
        return users;
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }
}
