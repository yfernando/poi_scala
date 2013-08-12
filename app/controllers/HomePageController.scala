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

    //create the Poi Category list
    Ok(views.html.home(List(new PoiCategory(1, "Pub"),
                            new PoiCategory(2, "Cafe"),
                            new PoiCategory(3, "Restaurant"),
                            new PoiCategory(4, "Bus_Stop"),
                            new PoiCategory(5, "Underground"))))

  }

}
