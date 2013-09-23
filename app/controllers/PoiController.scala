package controllers

import play.api.mvc._
import java.util
import models.{PoiCategory, User}
import services.VotingService
import controllers.LoginController.Secured
import views.html.helper.form
import play.data.Form


/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 10/08/2013
 * Time: 05:36
 * To change this template use File | Settings | File Templates.
 */
object PoiController extends Controller with Secured {


  /**
   * Create the google map with pois for the given poi categegory
   * @param name - the selected poi category
   * @return - view html that creates the google map
   */
  def getPoiCollectionFromCategory (name: String) =  withAuth { _ => _ =>
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
  def getVoteButtons (poiId: String) = withAuth { _ => implicit request =>

    println("inside vote getVoteButtons")
    val userEmail = request.session.get("email")
    val userIdLong = User.findIDByEmail(userEmail.get)

    //get the last vote status for this poi by the user
    val votingService = new VotingService()
    val currentStatus = votingService.getVoteStatus(userIdLong, poiId)

    // display the Buttons
    Ok(content = views.html.PoiController.likeButtons(currentStatus))
  }

  /**
   * Handles a user vote for the poi and reload the vote buttons
   *
   * @return - view html that display the voting buttons
   */
  def vote = withAuth { _ => implicit request =>

    println("inside vote method")
    val userEmail = request.session.get("email")
    val extPoiId = request.body.asFormUrlEncoded.get("extPoiId")
    val poiCatName = request.body.asFormUrlEncoded.get("poiCatName")
    val userVote = request.body.asFormUrlEncoded.get("userVote")
    println("## the request params are - " + userEmail +","+ extPoiId +","+ poiCatName +","+ userVote )

    //cast the parameters to write to the database
    val userIdLong = User.findIDByEmail(userEmail.get)
    val catIdLong = PoiCategory.findIDByName(poiCatName.head)
    val extPoiIdString = extPoiId.head
    val userVoteString = userVote.head
    println("## the casted params are - " + userIdLong +","+ extPoiIdString +","+ catIdLong +","+ userVoteString )

    val votingService = new VotingService()
    votingService.vote(userIdLong, extPoiIdString , catIdLong, userVoteString)

    //Ok
    //Ok((views.html.PoiController.likeButtons(extPoiIdString)))
    Redirect(routes.PoiController.getVoteButtons(extPoiIdString))

  }





  def poiCatTest() = Action {
    Ok(content = views.html.test())
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
