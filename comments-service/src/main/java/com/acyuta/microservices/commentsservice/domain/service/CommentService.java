package com.acyuta.microservices.commentsservice.domain.service;

import com.acyuta.microservices.commentsservice.domain.model.Comment;

public interface CommentService {

    Comment addCommentRestClient(Comment comment);

    Comment addCommentFeign(Comment comment);
}
