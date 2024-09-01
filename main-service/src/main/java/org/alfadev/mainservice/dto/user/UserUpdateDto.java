package org.alfadev.mainservice.dto.user;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDto {

    private Long id;
    private String lastName;
    private String name;
    private String patronymic;
}
