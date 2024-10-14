package responses;

import authorization.AuthorizedUserData;

public class AuthorizeResponse extends BaseResponse {
    private final AuthorizedUserData authorizedAs;

    public AuthorizeResponse(AuthorizedUserData authorizedAs) {
        this.authorizedAs = authorizedAs;
    }

    public AuthorizedUserData getAuthorizedAs() {
        return authorizedAs;
    }
}
