package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    @Autowired
    public TwitterController(Service service) {
        this.service = service;
    }

    @Override
    public Tweet postTweet(String[] args) {
        if(args.length != 3 || !args[0].equals("post")) {
            throw new RuntimeException("USAGE: TwitterCLIApp post \"tweet text\" latitude:longitude");
        }

        Tweet tweet = new Tweet();
        Coordinates coordinates = new Coordinates();
        tweet.setText(args[1]);

        List<Double> coordinatesArgs = Arrays.stream(args[2].split(COORD_SEP))
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        coordinates.setCoordinates(coordinatesArgs);
        tweet.setCoordinates(coordinates);

        return service.postTweet(tweet);
    }

    @Override
    public Tweet showTweet(String[] args) {
        if(args.length < 2 || !args[0].equals("show")) {
            throw new RuntimeException("USAGE: TwitterCLIApp show id [fields]");
        }

        String[] fields = null;

        if(args.length > 2) {
            fields = Arrays.copyOfRange(args, 2, args.length);
        }

        return service.showTweet(args[1], fields);
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if(args.length < 2 || !args[0].equals("delete")) {
            throw new RuntimeException("USAGE: TwitterCLIApp delete [1 or more ids]");
        }

        return service.deleteTweets(Arrays.copyOfRange(args, 1, args.length));
    }
}
