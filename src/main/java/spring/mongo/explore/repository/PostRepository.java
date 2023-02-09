package spring.mongo.explore.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import spring.mongo.explore.model.Post;

public interface PostRepository extends MongoRepository<Post,String> {
}
