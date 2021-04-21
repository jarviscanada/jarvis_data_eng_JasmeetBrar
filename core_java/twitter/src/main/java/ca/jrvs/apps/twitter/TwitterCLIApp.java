package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.utils.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TwitterCLIApp {

    private final Controller controller;

    @Autowired
    public TwitterCLIApp(Controller controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        CrdDao<Tweet, String> dao = new TwitterDao(httpHelper);
        Service service = new TwitterService(dao);
        Controller controller = new TwitterController(service);
        TwitterCLIApp app = new TwitterCLIApp(controller);

        app.run(args);
    }

    public void run(String[] args) {
        Tweet response;

        if(args.length == 0) {
            System.out.println("USAGE: post|show|delete [options]");
            return;
        }

        switch(args[0]) {
            case "post":
                response = controller.postTweet(args);
                break;
            case "show":
                response = controller.showTweet(args);
                break;
            case "delete":
                List<Tweet> tweets = controller.deleteTweet(args);

                tweets.forEach(tweet -> {
                    try {
                        System.out.println(JsonParser.toJson(tweet, true, true));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Cannot process responses into JSON");
                    }
                });
                return;
            default:
                System.out.println("USAGE: post|show|delete [options]");
                return;
        }
        try {
            System.out.println(JsonParser.toJson(response, true, true));
        } catch(JsonProcessingException e) {
            throw new RuntimeException("Cannot process response into JSON");
        }
    }
}
