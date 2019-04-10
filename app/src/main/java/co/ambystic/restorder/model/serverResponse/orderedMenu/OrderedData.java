package co.ambystic.restorder.model.serverResponse.orderedMenu;

import com.google.gson.annotations.SerializedName;

public class OrderedData {

    @SerializedName("idOrder")
    private String orderId;

    @SerializedName("idMasakan")
    private String idMasakan;

	@SerializedName("jenisMasakan")
	private String jenisMasakan;

	@SerializedName("keteranganDetail")
	private String keteranganDetail;

	@SerializedName("keteranganOrder")
	private String keteranganOrder;

	@SerializedName("namaMasakan")
	private String namaMasakan;

	@SerializedName("hargaMasakan")
	private String hargaMasakan;

	@SerializedName("jumlahOrder")
	private String jumlahOrder;

	@SerializedName("statusDetail")
	private String statusDetail;

	public void setJenisMasakan(String jenisMasakan){
		this.jenisMasakan = jenisMasakan;
	}

	public String getJenisMasakan(){
		return jenisMasakan;
	}

	public void setKeteranganDetail(String keteranganDetail){
		this.keteranganDetail = keteranganDetail;
	}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIdMasakan() {
        return idMasakan;
    }

    public void setIdMasakan(String idMasakan) {
        this.idMasakan = idMasakan;
    }

    public String getKeteranganDetail(){
		return keteranganDetail;
	}

	public void setKeteranganOrder(String keteranganOrder){
		this.keteranganOrder = keteranganOrder;
	}

	public String getKeteranganOrder(){
		return keteranganOrder;
	}

	public void setNamaMasakan(String namaMasakan){
		this.namaMasakan = namaMasakan;
	}

	public String getNamaMasakan(){
		return namaMasakan;
	}

	public void setHargaMasakan(String hargaMasakan){
		this.hargaMasakan = hargaMasakan;
	}

	public String getHargaMasakan(){
		return hargaMasakan;
	}

	public void setJumlahOrder(String jumlahOrder){
		this.jumlahOrder = jumlahOrder;
	}

	public String getJumlahOrder(){
		return jumlahOrder;
	}

	public void setStatusDetail(String statusDetail){
		this.statusDetail = statusDetail;
	}

	public String getStatusDetail(){
		return statusDetail;
	}

	@Override
 	public String toString(){
		return 
			"OrderedData{" +
			"jenisMasakan = '" + jenisMasakan + '\'' + 
			",keteranganDetail = '" + keteranganDetail + '\'' + 
			",keteranganOrder = '" + keteranganOrder + '\'' + 
			",namaMasakan = '" + namaMasakan + '\'' + 
			",hargaMasakan = '" + hargaMasakan + '\'' + 
			",jumlahOrder = '" + jumlahOrder + '\'' + 
			",statusDetail = '" + statusDetail + '\'' + 
			"}";
		}
}