package run.blog.app.aspect;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.blog.app.model.entity.PostComment;
import run.blog.app.model.entity.User;
import run.blog.app.security.authentication.AuthenticationImpl;
import run.blog.app.security.context.SecurityContextHolder;
import run.blog.app.security.context.SecurityContextImpl;
import run.blog.app.security.support.UserDetail;
import run.blog.app.service.PostCommentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author giveup
 * @description SensitiveConcealAspectTest
 * @date 1:14 AM 27/5/2020
 */
@SpringBootTest
@Disabled("Due to ip address: [0:0:0:0:0:0:0:1]")
class SensitiveConcealAspectTest {

    @Autowired
    PostCommentService postCommentService;

    @Test
    void testGuest() {
        List<PostComment> postComments = postCommentService.listBy(1);
        for (PostComment postComment : postComments) {
            assertEquals("", postComment.getIpAddress());
            assertEquals("", postComment.getEmail());
        }
    }

    @Test
    void testAdmin() {
        SecurityContextHolder.setContext(new SecurityContextImpl(new AuthenticationImpl(new UserDetail(new User()))));

        List<PostComment> postComments = postCommentService.listBy(1);
        for (PostComment postComment : postComments) {
            assertEquals("127.0.0.1", postComment.getIpAddress());
            assertEquals("hi@halo.run", postComment.getEmail());
        }
    }

}
