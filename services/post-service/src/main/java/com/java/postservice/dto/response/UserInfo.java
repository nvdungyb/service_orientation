package com.java.postservice.dto.response;

import com.java.postservice.enums.Erole;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class UserInfo {
    private String username;
    private List<Erole> eroles;
    private Boolean isActive;
}
