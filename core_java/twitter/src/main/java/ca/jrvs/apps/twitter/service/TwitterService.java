package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import static ca.jrvs.apps.twitter.utils.Validator.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

    private CrdDao<Tweet, String> dao;

    private List<String> tweetFields = new ArrayList<>(Arrays.asList("created_at",
            "id",
            "id_str",
            "text",
            "entities",
            "coordinates",
            "retweet_count",
            "favorite_count",
            "favorited",
            "retweeted"
    ));

    @Autowired
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

        if(fields != null && !Arrays.stream(fields).map(field -> tweetFields.contains(field)).reduce(true, (first, second) -> first && second)) {
            throw new IllegalArgumentException("Some of the given fields are invalid");
        }

        Tweet response = dao.findById(id);

        if(fields == null) {
            return response;
        }

        Tweet tweet = new Tweet();

        Arrays.stream(fields).forEach(field -> {
            switch(field) {
                case "id":
                    tweet.setId(response.getId());
                    break;
                case "id_str":
                    tweet.setIdStr(response.getIdStr());
                    break;
                case "text":
                    tweet.setText(response.getText());
                    break;
                case "entities":
                    tweet.setEntities(response.getEntities());
                    break;
                case "coordinates":
                    tweet.setCoordinates(response.getCoordinates());
                    break;
                case "retweet_count":
                    tweet.setRetweetCount(response.getRetweetCount());
                    break;
                case "favorite_count":
                    tweet.setFavoriteCount(response.getFavoriteCount());
                    break;
                case "favorited":
                    tweet.setFavorited(response.getFavorited());
                    break;
                case "retweeted":
                    tweet.setRetweeted(response.getRetweeted());
                    break;
                default:
                    throw new IllegalStateException("Should not fall into this state after checking the fields");
            }
        });

        return tweet;
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        if(Arrays.stream(ids).anyMatch(id -> !Validator.validateTweetId(id))) {
            throw new IllegalArgumentException("Given id is invalid");
        }

        return Arrays.stream(ids).map(id -> dao.deleteById(id)).collect(Collectors.toList());
    }
}
