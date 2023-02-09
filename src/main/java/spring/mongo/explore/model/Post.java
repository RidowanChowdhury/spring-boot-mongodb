package spring.mongo.explore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "JobPost")
public class Post {
    private String profile;
    private String desc;
    private int exp;
    private String[] techs;


}
