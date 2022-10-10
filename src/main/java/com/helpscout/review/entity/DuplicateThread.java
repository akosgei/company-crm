package com.helpscout.review.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

@Getter
@Setter
public class DuplicateThread {
    @Id
    private Long duplicateThreadId;
    private Long id;
    private String payload;
    @Transient
    Long conversationId;
}