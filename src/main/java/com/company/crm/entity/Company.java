package com.company.crm.entity;


import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NotNull
@Builder
public class Company {

    @Id
    private Long companyId;
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private LocalDateTime signedUp;
    private Set<Conversation> conversations = new HashSet<>();
}
