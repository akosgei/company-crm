package com.company.crm.review.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
public class Thread implements Serializable {

    private static final long serialVersionUID = -2174349006271944509L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long threadPrimaryKey;

    @Column(name = "ID")
    private Long id;

    @Column(name = "PAYLOAD", length = 1000)
    private String payload;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversationFk", referencedColumnName = "conversationPrimaryKey")
    Conversation conversation;

}