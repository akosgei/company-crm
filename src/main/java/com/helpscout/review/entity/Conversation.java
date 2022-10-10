package com.helpscout.review.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Conversation {

    @Id
    private Long ConversationId;
    private Long id;
    private Long number;
    private Long userId;
    private String from;
    private LocalDateTime received;

    private Set<Thread> threads = new HashSet<>();
    private Set<DuplicateThread> duplicateThreads = new HashSet<>();
    @Transient
    Company company;
}