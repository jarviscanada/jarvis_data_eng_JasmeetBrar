package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {

    private Controller controller;
    private Long id;

    @Before
    public void setUp() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        TwitterDao twitterDao = new TwitterDao(httpHelper);
        Service service = new TwitterService(twitterDao);
        controller = new TwitterController(service);
    }

    @After
    public void tearDown() {
        String[] args = new String[]{"delete", id.toString()};
        List<Tweet> response = controller.deleteTweet(args);
        response.forEach(this::testResponse);
    }

    @Test
    public void createAndShowTweet() {
        // Post tweet
        String[] args = new String[]{"post", "Testing 1...2...3!", "25.0:35.0"};
        Tweet response = controller.postTweet(args);
        testResponse(response);
        id = response.getId();

        // Show tweet
        args = new String[]{"show", id.toString()};
        response = controller.showTweet(args);
        testResponse(response);
    }

    private void testResponse(Tweet response) {
        assertNotNull(response);

        Double longitude = response.getCoordinates().getCoordinates().get(0);
        Double latitude = response.getCoordinates().getCoordinates().get(1);

        assertNotNull(response.getId());
        assertEquals(response.getText(), "Testing 1...2...3!");
        assertEquals(latitude, 25.0, 0.1);
        assertEquals(longitude, 35.0, 0.1);
    }


}