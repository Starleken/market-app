package org.alfadev.mainservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserChangePasswordDto {

    private Long id;
    private String oldPassword;
    private String newPassword;
}
