package org.alfadev.mainservice.dto.user;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseShortDto {

    private Long id;
    private String email;
    private String lastName;
    private String name;
}
