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

  def showHomePage = Action {

    //create the PoiController Category list

    //Check if user object in session

    Ok(views.html.HomePageController.home(PoiCategory.findAll()))

  }

}
