package com.company.crm.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DuplicateThread {
    @Id
    private Long duplicateThreadId;
    private Long id;
    private String payload;
    @Transient
    Conversation conversation;
}