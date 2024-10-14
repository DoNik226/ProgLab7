/**import authorization.authCredentials.RegistrationData;
import clientLogic.AuthorizeManager;
import exceptions.authorizationExceptions.AuthorizeException;
import requestLogic.CallerBack;

import java.net.InetSocketAddress;

public class RegisterTest {
    public void test() throws AuthorizeException {
        var a = AuthorizeManager.register(new CallerBack(new InetSocketAddress(Main.PORT).getAddress(), Main.PORT),
                new RegistrationData("Kirill", "zerumi", new char[]{'q', 'w', 'e', 'r', 't', 'y'}));
        System.out.println(a);
    }
}
**/