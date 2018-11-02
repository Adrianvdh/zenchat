package com.zenchat.server.api.user.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.zenchat.server.api.user.model.User;
import org.bson.Document;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private MongoCollection<Document> usersCollection;

    public UserRepositoryImpl(MongoCollection<Document> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public void save(User user) {
        Document document = new Document();
        document.put("_id", user.getUserId());
        document.put("username", user.getUsername());
        document.put("password", user.getPassword());
        usersCollection.insertOne(document);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public User findByUsername(String username) {
        FindIterable<Document> userDocument = usersCollection.find(new Document("username", username));
        Document document = userDocument.first();
        User user = new User(
                document.get("_id", String.class),
                document.get("username", String.class),
                document.get("password", String.class)
        );
        return user;
    }

    @Override
    public boolean exists(String username) {
        FindIterable<Document> userDocument = usersCollection.find(Filters.eq("username", username));
        Document document = userDocument.first();
        return document != null;
    }
}
