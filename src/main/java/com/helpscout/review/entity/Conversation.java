package com.helpscout.review.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Conversation {

    @Id
    private Long ConversationId;
    private Long id;
    private Set<Thread> threads = new HashSet<>();
    @Transient
    Company company;
}