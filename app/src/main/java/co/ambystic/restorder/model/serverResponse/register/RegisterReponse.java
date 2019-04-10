package co.ambystic.restorder.model.serverResponse.register;

import com.google.gson.annotations.SerializedName;

public class RegisterReponse {

    @SerializedName("serverResponse")
    private RegisterItem serverResponse;

    public void setServerResponse(RegisterItem serverResponse){
        this.serverResponse = serverResponse;
    }

    public RegisterItem getServerResponse(){
        return serverResponse;
    }

    @Override
    public String toString(){
        return
                "Responses{" +
                        "serverResponse = '" + serverResponse + '\'' +
                        "}";
    }
}