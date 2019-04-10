package co.ambystic.restorder.model.serverResponse.order;

import com.google.gson.annotations.SerializedName;

public class OrderData {

	@SerializedName("jenisMasakan")
	private String jenisMasakan;

	@SerializedName("statusOrder")
	private String statusOrder;

	@SerializedName("keteranganDetail")
	private String keteranganDetail;

	@SerializedName("noMeja")
	private String noMeja;

	@SerializedName("idOrder")
    private String idOrder;

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

	@SerializedName("statusMasakan")
	private String statusMasakan;

	@SerializedName("tanggalOrder")
	private String tanggalOrder;

	public void setJenisMasakan(String jenisMasakan){
		this.jenisMasakan = jenisMasakan;
	}

	public String getJenisMasakan(){
		return jenisMasakan;
	}

	public void setStatusOrder(String statusOrder){
		this.statusOrder = statusOrder;
	}

	public String getStatusOrder(){
		return statusOrder;
	}

	public void setKeteranganDetail(String keteranganDetail){
		this.keteranganDetail = keteranganDetail;
	}

	public String getKeteranganDetail(){
		return keteranganDetail;
	}

	public void setNoMeja(String noMeja){
		this.noMeja = noMeja;
	}

	public String getNoMeja(){
		return noMeja;
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

	public void setStatusMasakan(String statusMasakan){
		this.statusMasakan = statusMasakan;
	}

	public String getStatusMasakan(){
		return statusMasakan;
	}

	public void setTanggalOrder(String tanggalOrder){
		this.tanggalOrder = tanggalOrder;
	}

	public String getTanggalOrder(){
		return tanggalOrder;
	}

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    @Override
 	public String toString(){
		return 
			"OrderData{" +
			"jenisMasakan = '" + jenisMasakan + '\'' + 
			",statusOrder = '" + statusOrder + '\'' + 
			",keteranganDetail = '" + keteranganDetail + '\'' + 
			",noMeja = '" + noMeja + '\'' + 
			",keteranganOrder = '" + keteranganOrder + '\'' + 
			",namaMasakan = '" + namaMasakan + '\'' + 
			",hargaMasakan = '" + hargaMasakan + '\'' + 
			",jumlahOrder = '" + jumlahOrder + '\'' + 
			",statusDetail = '" + statusDetail + '\'' + 
			",statusMasakan = '" + statusMasakan + '\'' + 
			",tanggalOrder = '" + tanggalOrder + '\'' + 
			"}";
		}
}