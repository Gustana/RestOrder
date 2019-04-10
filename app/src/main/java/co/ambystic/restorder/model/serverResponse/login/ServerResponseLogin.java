package co.ambystic.restorder.model.serverResponse.login;

import com.google.gson.annotations.SerializedName;

public class ServerResponseLogin {

    @SerializedName("isError")
    private boolean isError;

    @SerializedName("data")
    private LoginData loginData;

    @SerializedName("message")
    private String message;

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public boolean getIsError() {
        return isError;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "ServerResponseLogin{" +
                        "isError = '" + isError + '\'' +
                        ",loginData = '" + loginData + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}