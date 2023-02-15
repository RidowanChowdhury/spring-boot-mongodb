package spring.mongo.explore.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.mongo.explore.Service.PostService;
import spring.mongo.explore.model.Post;
import spring.mongo.explore.repository.PostRepository;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostRepository repo;

    @Autowired
    PostService postService;

    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/allPosts")
    public List<Post> getAllPosts() {
        return repo.findAll();
    }

    @GetMapping("/posts/{text}")
    public List<Post> search(@PathVariable String text) {
        return postService.searchByAllProperties(text);
    }

    @GetMapping("/posts/2/{text}")
    public List<Post> search2(@PathVariable String text) {
        long start = System.nanoTime();
        List<Post> res = postService.searchByAllProperties2(text);
        System.out.println(System.nanoTime() - start);
        return res;
    }

    @GetMapping("/posts/3/{text}")
    public List<Post> search3(@PathVariable String text) {
        long start = System.nanoTime();
        List<Post> res = postService.aggregateWithPOJO(text);
        System.out.println(System.nanoTime() - start);
        return res;
    }

    @GetMapping("/posts/4/{text}")
    public List<Post> search4(@PathVariable String text) {
        long start = System.nanoTime();
        List<Post> res = postService.aggregateWithPOJO4(text);
        System.out.println(System.nanoTime() - start);
        return res;
    }

    @GetMapping("/posts/5/{text}")
    public List<Post> findByExp(@PathVariable int text) {
        return postService.repositorySearch(text);
    }


    @PostMapping("/post")
    @CrossOrigin
    public Post addPost(@RequestBody Post post) {
        return repo.save(post);
    }

}