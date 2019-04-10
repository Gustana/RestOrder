package co.ambystic.restorder.model.serverResponse.report;

import com.google.gson.annotations.SerializedName;

public class ReportData {

	@SerializedName("_namaMasakan")
	private String namaMasakan;

	@SerializedName("jumlahPesanan")
	private String jumlahPesanan;

	@SerializedName("totalBayar")
	private String totalBayar;

	@SerializedName("_jumlah")
	private String jumlah;

	@SerializedName("_harga")
	private String harga;

	@SerializedName("_statusMasakan")
	private String statusMasakan;

	@SerializedName("_deskripsi")
	private String deskripsi;

	@SerializedName("_id")
	private String id;

	@SerializedName("_jenisMasakan")
	private String jenisMasakan;

	public void setNamaMasakan(String namaMasakan){
		this.namaMasakan = namaMasakan;
	}

	public String getNamaMasakan(){
		return namaMasakan;
	}

	public void setJumlahPesanan(String jumlahPesanan){
		this.jumlahPesanan = jumlahPesanan;
	}

	public String getJumlahPesanan(){
		return jumlahPesanan;
	}

	public void setTotalBayar(String totalBayar){
		this.totalBayar = totalBayar;
	}

	public String getTotalBayar(){
		return totalBayar;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setStatusMasakan(String statusMasakan){
		this.statusMasakan = statusMasakan;
	}

	public String getStatusMasakan(){
		return statusMasakan;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setJenisMasakan(String jenisMasakan){
		this.jenisMasakan = jenisMasakan;
	}

	public String getJenisMasakan(){
		return jenisMasakan;
	}

	@Override
 	public String toString(){
		return 
			"ReportData{" +
			"_namaMasakan = '" + namaMasakan + '\'' + 
			",jumlahPesanan = '" + jumlahPesanan + '\'' + 
			",totalBayar = '" + totalBayar + '\'' + 
			",_jumlah = '" + jumlah + '\'' + 
			",_harga = '" + harga + '\'' + 
			",_statusMasakan = '" + statusMasakan + '\'' + 
			",_deskripsi = '" + deskripsi + '\'' + 
			",_id = '" + id + '\'' + 
			",_jenisMasakan = '" + jenisMasakan + '\'' + 
			"}";
		}
}