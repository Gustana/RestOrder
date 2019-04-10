package co.ambystic.restorder.model.serverResponse.order;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {

	@SerializedName("serverResponse")
	private ServerResponseOrder serverResponseOrder;

	public void setServerResponseOrder(ServerResponseOrder serverResponseOrder){
		this.serverResponseOrder = serverResponseOrder;
	}

	public ServerResponseOrder getServerResponseOrder(){
		return serverResponseOrder;
	}

	@Override
 	public String toString(){
		return 
			"OrderResponse{" +
			"serverResponseOrder = '" + serverResponseOrder + '\'' +
			"}";
		}
}