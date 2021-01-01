package com.acyuta.microservices.commentsservice.domain.converter;

import com.acyuta.microservices.commentsservice.domain.model.Comment;
import com.acyuta.microservices.commentsservice.domain.service.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentConverter implements Converter<String, Comment> {

    private final CommentRepository commentRepository;


    /**
     * Fetches a user when the id is provided.
     *
     * @param id String
     * @return user.
     */
    @Override
    public Comment convert(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));
    }

}
