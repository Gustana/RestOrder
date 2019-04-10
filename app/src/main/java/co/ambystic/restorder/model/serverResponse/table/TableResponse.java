package co.ambystic.restorder.model.serverResponse.table;

import com.google.gson.annotations.SerializedName;

public class TableResponse {
    @SerializedName("serverResponse")
    private ServerResponseTable serverResponseTable;

    public void setServerResponse(ServerResponseTable serverResponseTable) {
        this.serverResponseTable = serverResponseTable;
    }

    public ServerResponseTable getServerResponse() {
        return serverResponseTable;
    }

    @Override
    public String toString() {
        return
                "OrderResponse{" +
                        "serverResponse = '" + serverResponseTable + '\'' +
                        "}";
    }
}
