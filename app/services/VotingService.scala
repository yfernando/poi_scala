package services

import models.Vote
import java.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 10/09/2013
 * Time: 21:05
 * To change this template use File | Settings | File Templates.
 */
class VotingService {

  object Constants {
    val  STATUS_NOT_VOTED =  "NOT_VOTED"
    val  STATUS_LIKED =  "LIKED"
    val  STATUS_NOT_LIKED =  "NOT_LIKED"
  }

  /**
   * Checks if a user has already voted for a Poi.
   *
   */
  def getVoteStatus(userId: Long, extPoiId: String):  String = {

    if (userId == 0  || extPoiId.isEmpty) {
        Constants.STATUS_NOT_VOTED
    }

    val voteObj = Vote.findLastVote(userId, extPoiId)

    voteObj match {
        case None => Constants.STATUS_NOT_VOTED
        case Some(vote) => {
            if (vote.voted)
                Constants.STATUS_LIKED
            else
                Constants.STATUS_NOT_LIKED
        }
    }

//    Random randomGenerator = new Random();
//    Int number = randomGenerator.nextInt(3);
//    switch (number){
//      case 0: return NOT_VOTED;
//      case 1: return LIKED;
//      case 2: return NOT_LIKED;
//      default: return "";
//    }
  }

}
