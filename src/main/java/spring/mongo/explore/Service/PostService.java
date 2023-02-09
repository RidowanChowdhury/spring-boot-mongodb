package spring.mongo.explore.Service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;
import spring.mongo.explore.model.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PostService {

    @Autowired
    MongoClient client;

    @Autowired
    MongoConverter converter;

    public List<Post> searchByAllProperties(String data) {
        MongoDatabase database = client.getDatabase("jobApplication");
        MongoCollection<Document> collection = database.getCollection("JobPost");

        AggregateIterable<Document> documents = collection.aggregate(Arrays.asList(new Document("$search",
                new Document("index", "default")
                        .append("text",
                                new Document("query", data)
                                        .append("path",
                                                new Document("wildcard", "*"))))));
        final List<Post> posts = new ArrayList<>();
        documents.forEach(doc -> posts.add(converter.read(Post.class, doc)));
        return posts;

    }
}
