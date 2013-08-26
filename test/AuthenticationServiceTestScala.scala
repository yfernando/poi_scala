import org.specs2.mock.Mockito
import org.specs2.Specification
import services.{AuthService, ExternalAuthService}

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 29/07/2013
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
class AuthenticationServiceTestScala extends Specification with Mockito {

  def is =
    "Test Auth Service" ! {
      val mockExtAuthService = mock[ExternalAuthService]
      val authService = new AuthService()
      authService.setExternalAuthService(mockExtAuthService)
      val result = authService.authenticate("testusername","testpassword")
      there was one(mockExtAuthService).authenticate("testusername","testpassword")

    }

}
