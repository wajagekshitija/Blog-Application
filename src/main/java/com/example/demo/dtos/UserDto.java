package com.example.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {

	@NotBlank(message = "Username is required")
	@Size(min = 8, max = 20, message = "Username must contain at least 8 characters and at most 20 characters")
	@Pattern(regexp = "^[a-zA-Z0-9_@-]*$", message = "Username can only contain letters, digits, hyphens, underscores, and @")
	private String name;

	@NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit")
    @Pattern(regexp = ".*[!@#\\$%\\^&\\*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*", message = "Password must contain at least one special character")
    @Pattern(regexp = "\\S*", message = "Password must not contain any spaces")
	private String password;

	@NotBlank(message = "About is required")
	private String about;

}
