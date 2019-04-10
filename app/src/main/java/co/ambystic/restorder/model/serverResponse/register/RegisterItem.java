package co.ambystic.restorder.model.serverResponse.register;

import com.google.gson.annotations.SerializedName;

public class RegisterItem {

    @SerializedName("isError")
    private boolean isError;

    @SerializedName("message")
    private String message;

    public void setIsError(boolean isError){
        this.isError = isError;
    }

    public boolean isError(){
        return isError;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "ServerResponse{" +
                        "isError = '" + isError + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}