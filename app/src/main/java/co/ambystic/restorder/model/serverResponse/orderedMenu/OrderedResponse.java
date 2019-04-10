package co.ambystic.restorder.model.serverResponse.orderedMenu;

import com.google.gson.annotations.SerializedName;

public class OrderedResponse {

	@SerializedName("serverResponse")
	private ServerResponseOrdered serverResponseOrdered;

	public void setServerResponseOrdered(ServerResponseOrdered serverResponseOrdered){
		this.serverResponseOrdered = serverResponseOrdered;
	}

	public ServerResponseOrdered getServerResponseOrdered(){
		return serverResponseOrdered;
	}

	@Override
 	public String toString(){
		return 
			"OrderedResponse{" +
			"serverResponseOrdered = '" + serverResponseOrdered + '\'' +
			"}";
		}
}