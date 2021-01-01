package com.acyuta.microservices.commentsservice.domain.controller;

import com.acyuta.microservices.commentsservice.domain.model.Comment;
import com.acyuta.microservices.commentsservice.domain.service.CommentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CommentsController {

    private final CommentService commentService;

    @GetMapping("/comments/{id}")
    public Comment getComment(@PathVariable("id") Comment comment){
        log.info("fetched comment{}", comment);
        return comment;
    }

    @PostMapping("/comments/add-rest-client")
    @HystrixCommand(fallbackMethod = "addCommentFallbackMethod")
    public Comment addCommentRestClient(@RequestBody Comment comment){
        log.info("adding new comment {} -- using rest client",comment);
        return commentService.addCommentRestClient(comment);
    }

    @PostMapping("/comments/add-feign")
    @HystrixCommand(fallbackMethod = "addCommentFallbackMethod")
    public Comment addComment(@RequestBody Comment comment){
        log.info("adding new comment {} -- using feign client", comment);
        return commentService.addCommentFeign(comment);
    }

    /**
     * Hystrix fallback method, in case above two methods fail to call user-service.
     * @param comment comment.
     * @return the same comment.
     */
    public Comment addCommentFallbackMethod(@RequestBody Comment comment){
        return comment.setComment(comment.getComment() + "; Couldn't persist to db.");
    }

}
