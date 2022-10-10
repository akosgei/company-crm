package com.helpscout.review.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Getter
@Setter
public class Thread {
    @Id
    private Long ThreadId;
    private Long id;
    private String payload;
    @Transient
    Conversation conversation;
}