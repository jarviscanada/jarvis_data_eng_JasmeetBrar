package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {

    private TwitterService twitterService;
    private Tweet tweet;
    private Long id;

    @Before
    public void setUp() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        TwitterDao twitterDao = new TwitterDao(httpHelper);
        twitterService = new TwitterService(twitterDao);

        tweet = new Tweet();
        tweet.setText("Test");

        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new ArrayList<Double>(){{
            add(48.7);
            add(2.0);
        }});

        tweet.setCoordinates(coordinates);
    }

    @After
    public void tearDown() {
        assertNotNull(id);
        List<Tweet> response = twitterService.deleteTweets(new String[]{id.toString()});

        // Test that deleting tweets worked
        response.forEach(this::testResponse);
    }

    @Test
    public void createAndFindTweet() {
        Tweet response = twitterService.postTweet(tweet);

        // Test that creating a tweet worked
        testResponse(response);

        id = response.getId();
        response = twitterService.showTweet(id.toString(), null);

        // Test that finding a tweet worked
        testResponse(response);
    }

    private void testResponse(Tweet response) {
        Double longitude = response.getCoordinates().getCoordinates().get(0);
        Double latitude = response.getCoordinates().getCoordinates().get(1);

        assertNotNull(response.getId());
        assertEquals(response.getText(), "Test");
        assertEquals(latitude, 48.7, 0.1);
        assertEquals(longitude, 2.0, 0.1);
    }


}