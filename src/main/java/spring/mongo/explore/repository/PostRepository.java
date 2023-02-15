package spring.mongo.explore.repository;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import spring.mongo.explore.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post,String> {

    Optional<List<Post>> findByExp(int exp);
}
