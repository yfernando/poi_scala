package controllers

import play.api.mvc._
import services.{GoogleAuthService, AuthService}
import scala.math.Ordering.BooleanOrdering

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 31/07/2013
 * Time: 23:52
 * To change this template use File | Settings | File Templates.
 */
object LoginController extends Controller {

  def index = Action {
    Ok(content = views.html.LoginController.login())
  }


  def login = Action {

    def authService =  new AuthService
    def googleAuth = new GoogleAuthService        // TODO - need to wire externally
    authService.setExternalAuthService(googleAuth)     // TODO - need to wire externally
    def authenticated = authService.authenticate("foo","bar"): Boolean

    if (authenticated) {
      // set user object to session

      Redirect(routes.HomePageController.showHomePage)
    } else {
      // set "invalid username/password" error message

      Ok(views.html.LoginController.login())
    }

  }

}
