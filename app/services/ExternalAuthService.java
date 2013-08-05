package services;

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 29/07/2013
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
public interface ExternalAuthService {
    public boolean authenticate (String userName, String password);
}
