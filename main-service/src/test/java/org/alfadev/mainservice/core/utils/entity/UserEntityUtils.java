package org.alfadev.mainservice.core.utils.entity;

import org.alfadev.mainservice.core.utils.FakerUtils;
import org.alfadev.mainservice.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import static org.alfadev.mainservice.core.utils.FakerUtils.*;

public abstract class UserEntityUtils {

    public static UserEntity generateUser() {
        var generated = new UserEntity();
        generated.setName(FAKER.name().name());
        generated.setLastName(FAKER.name().lastName());
        generated.setPatronymic(FAKER.name().suffix());
        generated.setEmail(FAKER.internet().emailAddress());
        generated.setPassword(FAKER.internet().password());

        return generated;
    }

    public static UserEntity generateUser(Long id) {
        var generated = generateUser();
        generated.setId(id);

        return generated;
    }

    public static List<UserEntity> generateUsers(int count) {
        List<UserEntity> users = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            users.add(generateUser(i));
        }

        return users;
    }
}
