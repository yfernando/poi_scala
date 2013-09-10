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
   * Display the like/dislike buttons with the relavant state based on
   * previous selections.
   * @param poiId - selected poi
   * @return - view html that display the voting buttons
   */
  def getLikeButtons (poiId: String) = Action {

    def userId = 123     // get from session

    //check if this poi id was already voted by the user
    def votingService = new VotingService()
    def status = votingService.hasVoted(userId, poiId)

    // display the Buttons
    Ok(content = views.html.PoiController.likeButtons(status))
  }


  def vote(id: String) = Action {

    //TODO: Update the votes for the id and with the value form the request voted param
    Redirect(routes.PoiController.getLikeButtons(id))
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
