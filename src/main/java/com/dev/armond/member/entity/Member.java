package com.dev.armond.member.entity;


import com.dev.armond.common.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "nickName", "email"})
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    private int loginFailCount;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Double height;
    private Double weight;
    private int goalCalories;
    private int recommendCalories;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    public Set<GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Builder
    public Member(String name, String nickName, String password, String email, Gender gender,
                  Double height, Double weight, int goalCalories, Set<Role> roles) {
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.goalCalories = goalCalories;
        this.roles = roles;
    }
}
