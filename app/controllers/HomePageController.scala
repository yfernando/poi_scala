package controllers

import play.api.mvc._
import models.PoiCategory

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 11/08/2013
 * Time: 01:10
 * To change this template use File | Settings | File Templates.
 */
object HomePageController extends Controller {

  def showHomePage = Action { implicit request =>

    //create the PoiController Category liSecuredst

    //Check if user object in session

    println("username is - " + request.session.get("email"))

    Ok(views.html.HomePageController.home(PoiCategory.findAll()))

  }

}
