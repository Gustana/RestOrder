package co.ambystic.restorder.model.serverResponse.menu;

import com.google.gson.annotations.SerializedName;

public class MenuResponse {

    @SerializedName("serverResponse")
    private ServerResponseMenu serverResponse;

    public void setServerResponse(ServerResponseMenu serverResponse) {
        this.serverResponse = serverResponse;
    }

    public ServerResponseMenu getServerResponse() {
        return serverResponse;
    }

    @Override
    public String toString() {
        return
                "MenuResponse{" +
                        "serverResponse = '" + serverResponse + '\'' +
                        "}";
    }
}