package org.loop.troop.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.loop.troop.dto.UserDto;
import org.modelmapper.ModelMapper;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jpa_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @ElementCollection
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

    @Column(nullable = false)
    private String gender;

    // optional user details
    @Column(nullable = true,length = 1000)
    private String profileUrl;

    private String bio;

    private String lastName;

    // user accessablity 
    private boolean privateAccount = false;

    private boolean emailVerified = false;

    private boolean enable = true;

    @Column(updatable = false)
    private LocalDate createdAt;

    @PrePersist
    public void postConstruct() {
        if (createdAt == null)
            this.createdAt = LocalDate.now();
    }

    public boolean checkForPrivateAccount() {
        return privateAccount;
    }

    public boolean checkForAccountActive(){
        return enable;
    }

    public UserDto toUserDto(ModelMapper mapper) {
        return mapper.map(this, UserDto.class);
    }
}
