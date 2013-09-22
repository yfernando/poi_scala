package controllers

import play.api.mvc._
import models.PoiCategory
import controllers.LoginController.Secured


/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 11/08/2013
 * Time: 01:10
 * To change this template use File | Settings | File Templates.
 */
object HomePageController extends Controller with Secured {

  def showHomePage = withAuth { _ => _ =>
    //println("username is - " + request.session.get("email"))
    Ok(views.html.HomePageController.home(PoiCategory.findAll()))
  }


}
