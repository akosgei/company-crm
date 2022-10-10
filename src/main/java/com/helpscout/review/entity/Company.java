package com.helpscout.review.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    private Long companyId;
    @NotNull
    private Long id;
    private String name;
    private LocalDateTime signedUp;
    private Set<Conversation> conversations = new HashSet<>();
}
