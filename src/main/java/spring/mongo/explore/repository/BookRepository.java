package spring.mongo.explore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import spring.mongo.explore.model.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, Integer> {

    Book findTopByOrderByIdDesc();

}
