import org.junit.Test;
import services.AuthService;

import static org.junit.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 29/07/2013
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationServiceTest {

    @Test
    public void checkCreds() {
        AuthService as = new AuthService();
        boolean result = as.authenticate(null,null);
        assertFalse(result);

    }
}
