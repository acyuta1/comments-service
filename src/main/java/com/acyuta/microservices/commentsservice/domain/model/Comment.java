package com.acyuta.microservices.commentsservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments-service")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Comment {

    @Id
    private String id;

    private Long userId;

    private String userFullName;

    private String email;

    private Boolean isAnonymous = true;

    private String comment;

}
