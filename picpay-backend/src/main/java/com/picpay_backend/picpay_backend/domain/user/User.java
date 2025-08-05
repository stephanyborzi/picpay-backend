package com.picpay_backend.picpay_backend.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity(name = "user")
@Table(name = "user")
@AllArgsConstructor
@EqualsAndHashCode(of= "id")
@NoArgsConstructor

public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )

    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

}
