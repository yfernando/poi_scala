package controllers

import play.api.mvc._
import services.{GoogleAuthService, AuthenticationService}
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
    Ok(views.html.index())
  }

  def login = Action {

    def googleAuth = new GoogleAuthService        // need to wire externally

    def authService =  new AuthenticationService
    authService.setExternalAuthService(googleAuth)     // need to wire externally
    def authenticated = authService.authenticate("foo","bar"): Boolean

    if (authenticated) {
      Ok(views.html.home())
    } else {
      Ok(views.html.index())
    }

  }
}
