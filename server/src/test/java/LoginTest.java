/**import authorization.authCredentials.AuthenticationData;
import clientLogic.AuthorizeManager;
import exceptions.authorizationExceptions.AuthorizeException;
import mains.MainS;
import requestLogic.CallerBack;

import java.net.InetSocketAddress;

public class LoginTest {
    public void test() throws AuthorizeException {
        var b = AuthorizeManager.authorize(new CallerBack(new InetSocketAddress(MainS.PORT).getAddress(), MainS.PORT),
                new AuthenticationData("Nikita", new char[]{'q', 'w', 'e', 'r', 't', 'y'}));
        System.out.println(b);
    }
}**/
