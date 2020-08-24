package com.registration.users.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 100)
    @NotEmpty(message = "{label.name.required}")
    private String name;  

    @Column(name = "cpf", length = 100)
    @NotEmpty(message = "{label.cpf.required}")
    @CPF(message = "Informe cpf válido")
    private String cpf;  

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "customerAddress_id")
    private CustomerAddress address;
}
