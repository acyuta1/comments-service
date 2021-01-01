package com.acyuta.microservices.commentsservice.domain.service;

import com.acyuta.microservices.commentsservice.domain.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
