package co.ambystic.restorder.model.serverResponse.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("serverResponse")
    private ServerResponseLogin serverResponseLogin;

    public void setServerResponseLogin(ServerResponseLogin serverResponseLogin) {
        this.serverResponseLogin = serverResponseLogin;
    }

    public ServerResponseLogin getServerResponseLogin() {
        return serverResponseLogin;
    }

    @Override
    public String toString() {
        return
                "LoginResponse{" +
                        "serverResponseLogin = '" + serverResponseLogin + '\'' +
                        "}";
    }
}