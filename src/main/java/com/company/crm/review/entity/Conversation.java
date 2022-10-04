package com.company.crm.review.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Conversation implements Serializable {

    private static final long serialVersionUID = -5265728519133619978L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationPrimaryKey;

    @Column(name = "ID")
    private Long id;

    @Column(name = "`NUMBER`")
    private Long number;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "`FROM`")
    private String from;

    @Column(name = "RECEIVED")
    private LocalDateTime received;

    @ManyToOne
    @JoinColumn(name = "companyFk", referencedColumnName = "companyPrimaryKey")
    Company company;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conversation")
    private List<Thread> threads;

}