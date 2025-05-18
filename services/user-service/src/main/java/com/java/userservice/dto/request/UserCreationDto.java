package com.java.userservice.dto.request;

import com.java.userservice.enums.Erole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreationDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;

    private Set<Erole> eroleList;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Erole> getEroleList() {
        return eroleList;
    }

    public void setEroleList(Set<Erole> eroleList) {
        this.eroleList = eroleList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
