package co.ambystic.restorder.model.serverResponse.menu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponseMenu {
    @SerializedName("isError")
    private boolean isError;

    @SerializedName("data")
    private List<MenuData> data;

    @SerializedName("message")
    private String message;

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public boolean isError() {
        return isError;
    }

    public void setData(List<MenuData> data) {
        this.data = data;
    }

    public List<MenuData> getData() {
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
                "ServerResponseLogin{" +
                        "isError = '" + isError + '\'' +
                        ",data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
