package com.company.crm.review.domain;

import lombok.*;
import lombok.experimental.Tolerate;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto implements Serializable {
    private  Long id;
    private  String name;
    private  LocalDateTime signedUp;
    private  List<ConversationDto> conversations = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
     public static class ConversationDto implements Serializable {
        private  Long id;
        private  Long number;
        private  Long userId;
        private  String from;
        private  LocalDateTime received;
        private  List<ThreadDto> threads = new ArrayList<>();
        private List<ThreadDto> duplicateThreads = new ArrayList<>();


        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ThreadDto implements Serializable {
            private  Long id;
            private  String payload;
        }
    }
}
