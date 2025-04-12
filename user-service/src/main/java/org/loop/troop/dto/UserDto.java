package org.loop.troop.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 92414210438655957L;
    private UUID id;
    private String firstName;
    private String username;
    private String email;
    private List<String> roles = new ArrayList<>();
    private String gender;
    private String profileUrl;
    private String bio;
    private String lastName;
    private boolean privateAccount;
    private LocalDate createdAt;
    private boolean enable;
    private boolean emailVerified;
}
