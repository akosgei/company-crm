package com.company.crm.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Conversation {

    @Id
    private Long ConversationId;
    @NotNull
    private Long id;
    @NotNull
    private Long number;
    @NotNull
    private Long userId;
    @NotBlank
    private String from;
    @NotNull
    private LocalDateTime received;

    private Set<Thread> threads = new HashSet<>();
    private Set<DuplicateThread> duplicateThreads = new HashSet<>();
    @Transient
    Company company;
}