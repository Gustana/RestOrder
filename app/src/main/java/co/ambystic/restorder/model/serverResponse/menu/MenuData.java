package co.ambystic.restorder.model.serverResponse.menu;

import com.google.gson.annotations.SerializedName;

public class MenuData {

    @SerializedName("_id")
    private String idMasakan;

    @SerializedName("_namaMasakan")
    private String namaMasakan;

    @SerializedName("_harga")
    private String harga;

    @SerializedName("_deskripsi")
    private String deskripsiMasakan;

    @SerializedName("_statusMasakan")
    private String statusMasakan;

    @SerializedName("_jenisMasakan")
    private String jenisMasakan;

    public String getIdMasakan() {
        return idMasakan;
    }

    public String getNamaMasakan() {
        return namaMasakan;
    }

    public String getHarga() {
        return harga;
    }

    public String getStatusMasakan() {
        return statusMasakan;
    }

    public String getJenisMasakan() {
        return jenisMasakan;
    }

    public String getDeskripsiMasakan() {
        return deskripsiMasakan;
    }

    @Override
    public String toString() {
        return
                "MenuData{" +
                        "_namaMasakan = '" + namaMasakan + '\'' +
                        ",_harga = '" + harga + '\'' +
                        ",_statusMasakan = '" + statusMasakan + '\'' +
                        ",_jenisMasakan = '" + jenisMasakan + '\'' +
                        "}";
    }
}