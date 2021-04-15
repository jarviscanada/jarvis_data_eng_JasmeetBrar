package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import static ca.jrvs.apps.twitter.utils.Validator.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TwitterService implements Service {

    private CrdDao<Tweet, String> dao;

//    @Autowired
    public TwitterService(CrdDao<Tweet, String> dao) {
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) {
        if(!validatePostTweet(tweet)) {
            throw new IllegalArgumentException("Cannot post tweet because it is invalid");
        }

        return dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        if(!validateTweetId(id)) {
            throw new IllegalArgumentException("Given id is invalid");
        }

        return dao.findById(id);
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        if(Arrays.stream(ids).anyMatch(id -> !Validator.validateTweetId(id))) {
            throw new IllegalArgumentException("Given id is invalid");
        }

        return Arrays.stream(ids).map(id -> dao.deleteById(id)).collect(Collectors.toList());
    }
}
