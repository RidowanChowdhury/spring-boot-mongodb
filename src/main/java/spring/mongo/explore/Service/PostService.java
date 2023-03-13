package spring.mongo.explore.Service;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;
import spring.mongo.explore.model.Post;
import spring.mongo.explore.repository.PostRepository;

import java.util.*;

@Service
public class PostService {

    @Autowired
    MongoClient client;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MongoConverter converter;

    public List<Post> searchByAllPropertiesWithClient(String data) {
        MongoDatabase database = client.getDatabase("jobApplication");
        MongoCollection<Document> collection = database.getCollection("JobPost");

        AggregateIterable<Document> documents = collection.aggregate(
                Arrays.asList(
                        new Document("$search",
                                new Document("index", "allsearch")
                                        .append("text",
                                                new Document("query", data)
                                                        .append("path",
                                                                new Document("wildcard", "*"))))));
        final List<Post> posts = new ArrayList<>();
        documents.forEach(doc -> posts.add(converter.read(Post.class, doc)));
        return posts;

    }

    public List<Post> searchByAllPropertiesWithTemplate_1(String data) {
        List<Bson> pipeline = Arrays.asList(
                new Document("$search",
                        new Document("index", "allsearch")
                                .append("text",
                                        new Document("query", data)
                                                .append("path",
                                                        new Document("wildcard", "*")))));
        AggregateIterable<Document> result = mongoTemplate
                .getCollection("JobPost")
                .aggregate(pipeline);

        List<Post> pojos = new ArrayList<>();
        for (Document document : result) {
            Post res = converter.read(Post.class, document);
            pojos.add(res);
        }

        return pojos;

    }

    public List<Post> aggregateWithPOJOWithTemplate_2(String data) {
        List<Bson> pipeline = Arrays.asList(
                new Document("$search",
                        new Document("index", "allsearch")
                                .append("text",
                                        new Document("query", data)
                                                .append("path",
                                                        new Document("wildcard", "*")))));


        MongoIterable<Post> result = mongoTemplate
                .getCollection("JobPost")
                .aggregate(pipeline)
                .map(document -> converter.read(Post.class, document));

        return result.into(new ArrayList<>());
    }


    public List<Post> aggregateWithPOJOWithTemplate_3(String data) {
        List<Bson> pipeline = Arrays.asList(new Document("$search",
                        new Document("index", "allsearch")

                                .append("text",
                                        new Document("query", data)
                                                .append("path", Arrays.asList("desc")))),
                new Document("$sort",
                        new Document("exp", -1L)));

        MongoIterable<Post> result = mongoTemplate
                .getCollection("JobPost")
                .aggregate(pipeline)
                .map(document -> converter.read(Post.class, document));

        return result.into(new ArrayList<>());
    }

    public List<Post> repositorySearch(int data) {

        Optional<List<Post>> res = postRepository.findByExp(data);
        return res.orElse(null);
    }

    public Document aggregateExplainWithPOJOWithTemplate_2(String data) {
        Document pipeline =

                new Document("$search",
                        new Document("index", "allsearch")
                                .append("text",
                                        new Document("query", data)
                                                .append("path",
                                                        new Document("wildcard", "*"))));


        List<Bson> bsons = Arrays.asList(pipeline);

        AggregateIterable<Document> result = mongoTemplate
                .getCollection("JobPost")

                .aggregate(bsons);
        ArrayList<Document> documents = new ArrayList<>(2);
        for (Document document : result) {
            documents.add(document);
        }
        return result.explain();
    }


}
