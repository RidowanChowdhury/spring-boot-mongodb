package spring.mongo.explore.resource;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.mongo.explore.model.Book;
import spring.mongo.explore.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {


    @Autowired
    private BookRepository repository;

    @PostMapping("/addBook")
    public String saveBook(@RequestBody Book book) {
        Book last = repository.findTopByOrderByIdDesc();
        int lastNum = last.getId();
        System.out.println(lastNum);
        book.setId(lastNum+1);
        repository.save(book);
        return "Added book with id : " + book.getId();
    }

    @GetMapping("/findAllBooks")
    public List<Book> getBooks() {
        return repository.findAll();
    }

    @GetMapping("/findAllBooks/{id}")
    public Optional<Book> getBook(@PathVariable int id) {
        return repository.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        repository.deleteById(id);
        return "book deleted with id : " + id;
    }
}
