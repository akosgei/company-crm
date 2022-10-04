package com.company.crm.review.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
public class DuplicateThread implements Serializable {

    private static final long serialVersionUID = -5399231471835634840L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long primaryKey;

    @Column(name = "ID")
    private Long id;

    @Column(name = "PAYLOAD", length = 1000)
    private String payload;

    @Column(name = "CONVERSATION_ID")
    Long conversationId;
}