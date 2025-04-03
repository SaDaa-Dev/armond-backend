package com.dev.armond.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private String phoneNumber;

    @Column(nullable = false)
    private String token;

    public void updateToken(String token) {
        this.token = token;
    }
}
