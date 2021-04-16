package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    Service service;

    @InjectMocks
    TwitterController controller;

    @Captor
    ArgumentCaptor<Tweet> tweetCaptor;

    @Captor
    ArgumentCaptor<String> idCaptor;

    @Captor
    ArgumentCaptor<String[]> idsCaptor;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void postTweetFailureCases() {
        // Fail if it is given the wrong number of arguments
        String[] args = new String[]{"post"};
        try {
            controller.postTweet(args);
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
            verify(service, times(0)).postTweet(any(Tweet.class));
        }

        // Fail if the first argument is not "post"
        args = new String[]{"find", "Hello World!", "21.0:23.5"};
        try {
            controller.postTweet(args);
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
            verify(service, times(0)).postTweet(any(Tweet.class));
        }
    }

    @Test
    public void postTweetSuccessCase() {
        String[] args = new String[]{"post", "Hello everyone!", "25.0:15.7"};
        controller.postTweet(args);
        verify(service, times(1)).postTweet(tweetCaptor.capture());
        Tweet tweet = tweetCaptor.getValue();
        testTweet(tweet);
    }

    @Test
    public void showTweetFailureCases() {
        // Fail if it is given the wrong number of arguments
        String[] args = new String[]{"show"};
        try {
            controller.showTweet(args);
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
            verify(service, times(0)).showTweet(any(), any());
        }

        // Fail if the first argument is not "show"
        args = new String[]{"find", "1"};
        try {
            controller.showTweet(args);
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
            verify(service, times(0)).showTweet(any(), any());
        }
    }

    @Test
    public void showTweetSuccessCase() {
        String[] args = new String[]{"show", "1"};
        controller.showTweet(args);
        verify(service, times(1)).showTweet(idCaptor.capture(), eq(null));
        String id = idCaptor.getValue();
        assertNotNull(id);
        assertEquals(id, "1");
    }

    @Test
    public void deleteTweetFailureCases() {
        // Fail if it is given the wrong number of arguments
        String[] args = new String[]{"delete"};
        try {
            controller.deleteTweet(args);
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
            verify(service, times(0)).deleteTweets(any());
        }

        // Fail if the first argument is not "show"
        args = new String[]{"find", "1"};
        try {
            controller.deleteTweet(args);
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
            verify(service, times(0)).deleteTweets(any());
        }
    }

    @Test
    public void deleteTweetSuccessCase() {
        String[] args = new String[]{"delete", "1", "7", "25"};
        controller.deleteTweet(args);
        verify(service, times(1)).deleteTweets(idsCaptor.capture());
        String[] ids = idsCaptor.getValue();
        assertNotNull(ids);
        assertEquals(ids.length, 3);
        assertEquals(ids[0], "1");
        assertEquals(ids[1], "7");
        assertEquals(ids[2], "25");
    }

    private void testTweet(Tweet response) {
        assertNotNull(response);

        Double longitude = response.getCoordinates().getCoordinates().get(0);
        Double latitude = response.getCoordinates().getCoordinates().get(1);

        assertEquals(response.getText(), "Hello everyone!");
        assertEquals(latitude, 15.7, 0.1);
        assertEquals(longitude, 25.0, 0.1);
    }
}