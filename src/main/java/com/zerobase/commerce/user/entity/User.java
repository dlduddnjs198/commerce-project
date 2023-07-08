package com.zerobase.commerce.user.entity;

import com.zerobase.commerce.user.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

}
