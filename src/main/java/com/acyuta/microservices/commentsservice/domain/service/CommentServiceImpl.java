package com.acyuta.microservices.commentsservice.domain.service;

import com.acyuta.microservices.commentsservice.domain.dto.UserDto;
import com.acyuta.microservices.commentsservice.domain.model.Comment;
import com.acyuta.microservices.commentsservice.domain.proxy.UserServicesProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserServicesProxy userServicesProxy;

    private Comment addCommentAnonymously(Comment comment) {
        return commentRepository.save(comment.setIsAnonymous(true).setUserId(null).setUserFullName(null).setEmail(null));
    }

    @Override
    public Comment addCommentRestClient(Comment comment) {
        if (!comment.getIsAnonymous() && comment.getEmail() != null) {
            var userDto = new RestTemplate().getForEntity(String.format("http://localhost:8000/api/users/by-email?email=%s", comment.getEmail()), UserDto.class);
            if (!userDto.getStatusCode().is2xxSuccessful())
                throw new ResponseStatusException(userDto.getStatusCode(), "Error");
            var user = userDto.getBody();
            return commentRepository.save(comment.setIsAnonymous(false).setUserId(user.getId()).setUserFullName(user.getName()));
        } else if (!comment.getIsAnonymous() && comment.getEmail() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "please provide email of yourself");
        return addCommentAnonymously(comment);
    }

    @Override
    public Comment addCommentFeign(Comment comment) {
        if (!comment.getIsAnonymous() && comment.getEmail() != null) {
            var userDto = userServicesProxy.getUserByEmail(comment.getEmail());

            log.info("Request served by port {}", userDto.getPort());
            return commentRepository.save(comment.setIsAnonymous(false).setUserId(userDto.getId()).setUserFullName(userDto.getName()));
        } else if (!comment.getIsAnonymous() && comment.getEmail() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "please provide email of yourself");
        return addCommentAnonymously(comment);
    }
}
