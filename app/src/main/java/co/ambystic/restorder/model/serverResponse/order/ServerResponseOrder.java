package co.ambystic.restorder.model.serverResponse.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponseOrder {

    @SerializedName("isError")
    private boolean isError;

    @SerializedName("data")
    private List<OrderData> data;

    @SerializedName("message")
    private String message;

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public boolean isError() {
        return isError;
    }

    public void setData(List<OrderData> data) {
        this.data = data;
    }

    public List<OrderData> getData() {
        return data;
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
                "ServerResponseOrder{" +
                        "isError = '" + isError + '\'' +
                        ",data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}