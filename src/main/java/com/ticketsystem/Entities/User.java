package com.ticketsystem.Entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("user")
public class User {

    public static final String LABEL =  "user";
    public static final String ID_COLUMN = "id";
    public static final String USERNAME_COLUMN = "username";
    public static final String PASSWORD_COLUMN = "password";
    public static final String ROLE_COLUMN = "role";
    public static final String IMAGE_KEY_COLUMN = "imageKey";
    public static final String IS_ACTIVE_COLUMN = "isActive";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(USERNAME_COLUMN)
    private String username;
    @Column(PASSWORD_COLUMN)
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Password must contain at least one uppercase, one lowercase, one digit, and one special character")
    private String password;
    @Column(ROLE_COLUMN)
    private Role role;
    @Column(IMAGE_KEY_COLUMN)
    private String imageKey;
    @Column(IS_ACTIVE_COLUMN)
    private Boolean isActive;

    public static UserBuilder from(User user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .imageKey(user.getImageKey());
    }

    public static User update(User existing, User updated) {
        existing.setUsername(updated.getUsername());
        existing.setPassword(updated.getPassword());
        existing.setRole(updated.getRole());
        existing.setImageKey(updated.getImageKey());
        existing.setIsActive(updated.getIsActive());
        return existing;
    }
}
