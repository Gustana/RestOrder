package co.ambystic.restorder.model.serverResponse.report;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServerResponseReport {

	@SerializedName("isError")
	private boolean isError;

	@SerializedName("data")
	private List<ReportData> data;

	@SerializedName("message")
	private String message;

	public void setIsError(boolean isError){
		this.isError = isError;
	}

	public boolean isError(){
		return isError;
	}

	public void setData(List<ReportData> data){
		this.data = data;
	}

	public List<ReportData> getData(){
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
			"ServerResponseReport{" +
			"isError = '" + isError + '\'' + 
			",data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}