package com.company.crm.review.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Company implements Serializable {

    private static final long serialVersionUID = 6542539993814026608L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long companyPrimaryKey;

    @Column(name="ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name = "SIGNED_UP")
    private LocalDateTime signedUp;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "company")
    private List<Conversation> conversations;
}
