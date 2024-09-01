package org.alfadev.mainservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateDto {

    private String email;
    private String password;
    private String lastName;
    private String name;
    private String patronymic;
}
