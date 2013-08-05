package services;

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 31/07/2013
 * Time: 22:41
 * To change this template use File | Settings | File Templates.
 */
public class GoogleAuthService implements ExternalAuthService {

    public GoogleAuthService() {
        System.out.print("Google");
    }

    @Override
    public boolean authenticate(String userName, String password) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
