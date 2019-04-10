package co.ambystic.restorder.model.serverResponse.orderedMenu;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServerResponseOrdered {

	@SerializedName("isError")
	private boolean isError;

	@SerializedName("data")
	private List<OrderedData> data;

	@SerializedName("message")
	private String message;

	public void setIsError(boolean isError){
		this.isError = isError;
	}

	public boolean isIsError(){
		return isError;
	}

	public void setData(List<OrderedData> data){
		this.data = data;
	}

	public List<OrderedData> getData(){
		return data;
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
			"ServerResponseOrdered{" +
			"isError = '" + isError + '\'' + 
			",data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}