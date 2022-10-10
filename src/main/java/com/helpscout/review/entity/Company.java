package com.helpscout.review.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    private Long companyId;
    private Long id;
    private String name;
    private Set<Conversation> conversations = new HashSet<>();
}
