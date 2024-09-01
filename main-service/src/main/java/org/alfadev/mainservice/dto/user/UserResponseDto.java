package org.alfadev.mainservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String email;
    private String lastName;
    private String name;
    private String patronymic;
}
