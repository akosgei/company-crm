package com.helpscout.review.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Getter
@Setter
public class DuplicateThread implements Serializable {
    @Id
    private Long duplicateThreadId;
    private Long id;
    private String payload;
    Long conversationId;
}