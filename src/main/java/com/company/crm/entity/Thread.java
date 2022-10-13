package com.company.crm.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Thread {
    @Id
    private Long ThreadId;
    @NotNull
    private Long id;
    @NotBlank
    private String payload;
    @Transient
    Conversation conversation;
}