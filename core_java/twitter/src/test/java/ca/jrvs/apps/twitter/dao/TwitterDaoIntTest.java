package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class TwitterDaoIntTest {

    private static TwitterDao twitterDao;

    private static Tweet tweet1;
    private static Tweet tweet2;
    private static Tweet tweet3;

    private static Long createTweetId;
    private static Long showTweetId;

    @BeforeClass
    public static void setUp() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        twitterDao = new TwitterDao(httpHelper);

        tweet1 = new Tweet();
        tweet1.setText("Test 1");

        Coordinates coordinates1 = new Coordinates();
        coordinates1.setCoordinates(new ArrayList<Double>(){{
            add(48.7);
            add(2.0);
        }});
        tweet1.setCoordinates(coordinates1);

        tweet2 = new Tweet();
        tweet2.setText("Test 2");

        Coordinates coordinates2 = new Coordinates();
        coordinates2.setCoordinates(new ArrayList<Double>(){{
            add(50.0);
            add(10.0);
        }});
        tweet2.setCoordinates(coordinates2);

        tweet3 = new Tweet();
        tweet3.setText("Test 3");

        Coordinates coordinates3 = new Coordinates();
        coordinates3.setCoordinates(new ArrayList<Double>(){{
            add(55.0);
            add(5.0);
        }});
        tweet3.setCoordinates(coordinates3);
    }

    @AfterClass
    public static void tearDown() {
        if(createTweetId != null) {
            twitterDao.deleteById(createTweetId.toString());
        }
        if(showTweetId != null) {
            twitterDao.deleteById(showTweetId.toString());
        }
    }

    @Test
    public void createTweet() throws Exception {
        Tweet response = twitterDao.create(tweet1);
        Double longitude = response.getCoordinates().getCoordinates().get(0);
        Double latitude = response.getCoordinates().getCoordinates().get(1);


        assertNotNull(response.getId());

        createTweetId = response.getId();

        assertEquals(response.getText(), "Test 1");
        assertEquals(latitude, 48.7, 0.1);
        assertEquals(longitude, 2.0, 0.1);
    }

    @Test
    public void showTweet() throws Exception {
        Tweet createResponse = twitterDao.create(tweet2);
        Long id = createResponse.getId();

        showTweetId = id;

        assertNotNull(id);

        Tweet response = twitterDao.findById(id.toString());

        Double longitude = response.getCoordinates().getCoordinates().get(0);
        Double latitude = response.getCoordinates().getCoordinates().get(1);

        assertEquals(response.getText(), "Test 2");
        assertEquals(latitude, 50.0, 0.1);
        assertEquals(longitude, 10.0, 0.1);
    }

    @Test
    public void deleteTweet() throws Exception {
        Tweet createResponse = twitterDao.create(tweet3);
        Long id = createResponse.getId();

        assertNotNull(id);

        Tweet response = twitterDao.deleteById(id.toString());

        Double longitude = response.getCoordinates().getCoordinates().get(0);
        Double latitude = response.getCoordinates().getCoordinates().get(1);

        assertEquals(response.getText(), "Test 3");
        assertEquals(latitude, 55.0, 0.1);
        assertEquals(longitude, 5.0, 0.1);

        Tweet showResponse = twitterDao.findById(id.toString());

        assertNull(showResponse.getId());
    }

}
