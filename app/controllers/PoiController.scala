package controllers

import play.api.mvc._
import java.util
import models.PoiCategory
import services.VotingService


/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 10/08/2013
 * Time: 05:36
 * To change this template use File | Settings | File Templates.
 */
object PoiController extends Controller {


  /**
   * Create the google map with pois for the given poi categegory
   * @param name - the selected poi category
   * @return - view html that creates the google map
   */
  def getPoiCollectionFromCategory (name: String) =  Action {
    //get POi collection
    Ok(content = views.html.PoiController.poiMap(name))
  }

  /**
   * Display the like/dislike buttons with the relavent state based on
   * previous selections. A state can be liked, not liked or not voted.
   *
   * @param poiId - selected poi
   * @return - view html that display the voting buttons
   */
  def getVoteButtons (poiId: String) = Action {

    val userId = 1    // get from session

    //get the last vote status for this poi by the user
    val votingService = new VotingService()
    val currentStatus = votingService.getVoteStatus(userId, poiId)

    // display the Buttons
    Ok(content = views.html.PoiController.likeButtons(currentStatus))
  }

  /**
   * Handles a user vote for the poi and reload the vote buttons
   *
   * @param poiId - selected poi
   * @return - view html that display the voting buttons
   */
  def vote(extPoiId: String, poiCatId: Long, userVote: String) = Action {

    println("inside vote method")

    val ext_PoiId = extPoiId
    val poi_CatId = poiCatId
    val user_Vote = userVote
    val userId = 1          // todo: get from session

    val votingService = new VotingService()
    votingService.vote(userId, ext_PoiId, poi_CatId, user_Vote)

    //Redirect(routes.PoiController.getVoteButtons(poiId))
    Ok//(content = views.html.PoiController.likeButtons(userVote))
  }

  def poiCatTest() = Action {
    Ok(content = views.html.test());
  }

  /**
   *
   * @param id
   * @return
   */
  def getPoiDetails (id: String) = Action {
    Ok(content = views.html.PoiController.poiDetails(id))
  }
}
