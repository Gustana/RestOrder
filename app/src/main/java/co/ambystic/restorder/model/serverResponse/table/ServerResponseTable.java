package co.ambystic.restorder.model.serverResponse.table;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponseTable {
    @SerializedName("isError")
    private boolean isError;

    @SerializedName("data")
    private List<TableData> data;

    @SerializedName("message")
    private String message;

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public boolean isIsError() {
        return isError;
    }

    public void setData(List<TableData> data) {
        this.data = data;
    }

    public List<TableData> getData() {
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
                "ServerResponseTable{" +
                        "isError = '" + isError + '\'' +
                        ",data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
