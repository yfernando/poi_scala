package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._
import views._

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 31/07/2013
 * Time: 23:52
 * To change this template use File | Settings | File Templates.
 */
object LoginController extends Controller {


  // -- Login Form
  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => User.authenticate(email, password).isDefined
    })
  )


  /**
   * Login page which is also the main page
   * @return - the login page associating the form object
   */
  def index = Action { implicit request =>
    Ok(content = views.html.LoginController.login(loginForm))
  }

  /**
   * Handles the user authentication
   * @return
   */
  def login = Action { implicit request =>
    println("inside login action")

    loginForm.bindFromRequest.fold(
      // set "invalid username/password" error message
      formWithErrors => BadRequest(views.html.LoginController.login(formWithErrors)),
      // set user object to session
      userObject => Redirect(routes.HomePageController.showHomePage).withSession("email" -> userObject._1)
    )

  }


  trait Secured {

    trait Secured {

      /**
       * Retrieve the connected user email.
       */
      private def username(request: RequestHeader) = request.session.get("email")

      /**
       * Redirect to login if the user in not authorized.
       */
      private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.LoginController.index)

      // --

      /**
       * Action for authenticated users.
       */
      def IsAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) { user =>
        Action(request => f(user)(request))
      }
    }
  }
}
