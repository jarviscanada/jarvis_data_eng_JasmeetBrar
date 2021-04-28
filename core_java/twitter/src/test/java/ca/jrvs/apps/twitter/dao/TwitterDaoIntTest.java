package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class TwitterDaoIntTest {

    private TwitterDao twitterDao;
    private Tweet tweet;
    private Long id;

    @Before
    public void setUp() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        twitterDao = new TwitterDao(httpHelper);

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
        Tweet response = twitterDao.deleteById(id.toString());

        // Test that deleting a tweet worked
        testResponse(response);
    }

    @Test
    public void createAndFindTweet() {
        Tweet response = twitterDao.create(tweet);

        // Test that creating a tweet worked
        testResponse(response);

        id = response.getId();
        response = twitterDao.findById(id.toString());

        // Test that finding a tweet worked
        testResponse(response);
    }

    private void testResponse(Tweet response) {
        assertNotNull(response);

        Double longitude = response.getCoordinates().getCoordinates().get(0);
        Double latitude = response.getCoordinates().getCoordinates().get(1);

        assertNotNull(response.getId());
        assertEquals(response.getText(), "Test");
        assertEquals(latitude, 48.7, 0.1);
        assertEquals(longitude, 2.0, 0.1);
    }

}
