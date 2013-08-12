package services;

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 29/07/2013
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationService {

    private ExternalAuthService externalAuthService;

    public void setExternalAuthService(ExternalAuthService externalAuthService) {
        this.externalAuthService = externalAuthService;
        System.out.println("...setting as ExternalAuthService");
    }

    public boolean authenticate (String userName, String password) {
            if(userName == null || password == null)
                return false;

            //return this.externalAuthService.authenticate(userName,password);
            return true;
    }
}
