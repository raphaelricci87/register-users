package com.registration.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "address")
public class CustomerAddress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "streetAddress", length = 100)
    @NotEmpty(message = "{label.streetAddress.required}")
    private String streetAddress;

    @Column(name = "number")
    @NotNull
    private Long number;
    
    @Column(name = "city", length = 100)
    @NotEmpty(message = "{label.city.required}")
    private String city;
    
    @Column(name = "postalCode", length = 100)
    @NotEmpty(message = "{label.postalCode.required}")
    private String postalCode;
    
    @Column(name = "country", length = 100)
    @NotEmpty(message = "{label.country.required}")
    private String country;
    
    @Column(name = "addressComplement", length = 100)
    private String addressComplement;

    @OneToOne(mappedBy = "address")
    private Customer customer;
}