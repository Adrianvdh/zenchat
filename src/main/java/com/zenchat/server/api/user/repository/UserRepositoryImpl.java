package com.zenchat.server.api.user.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.zenchat.server.api.user.model.Session;
import com.zenchat.server.api.user.model.User;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class UserRepositoryImpl implements UserRepository {
    private MongoCollection<User> usersCollection;

    public UserRepositoryImpl(MongoCollection<User> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public void save(User user) {
        usersCollection.insertOne(user);
    }

    @Override
    public void deleteAll() {
        usersCollection.deleteMany(new Document());
    }

    @Override
    public List<User> findAll() {
        FindIterable<User> usersIterable = usersCollection.find(new Document());

        List<User> users = new ArrayList<>();
        usersIterable.forEach((Consumer<User>) users::add);

        return users;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public User findByUsername(String username) {
        FindIterable<User> userDocument = usersCollection.find(Filters.eq("username", username));
        return userDocument.first();
    }

    @Override
    public User findBySessionId(String sessionId) {
        FindIterable<User> userDocument = usersCollection.find(Filters.eq("session.sessionId", sessionId));
        return userDocument.first();
    }

    @Override
    public void updateUserSession(String userId, Session session) {
        usersCollection.updateOne(
                eq("_id", userId),
                combine(
                        set("session.sessionId", session.getSessionId()),
                        set("session.expirationDate", session.getExpirationDate())
                )
        );
    }
}