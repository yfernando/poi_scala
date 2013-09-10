package services;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 05/09/2013
 * Time: 19:26
 * To change this template use File | Settings | File Templates.
 */
public class VotingService {

    private static String NOT_VOTED =  "NOT_VOTED";
    private static String LIKED = "LIKED";
    private static String NOT_LIKED = "NOT_LIKED";

    public String hasVoted(int userId, String extenal_poiId) {
        // TODO: Search on the database
        Random randomGenerator = new Random();
        int number = randomGenerator.nextInt(3);
        switch (number){
            case 0: return NOT_VOTED;
            case 1: return LIKED;
            case 2: return NOT_LIKED;
            default: return "";
        }
    }
}
