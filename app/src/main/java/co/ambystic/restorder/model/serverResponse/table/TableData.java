package co.ambystic.restorder.model.serverResponse.table;

import com.google.gson.annotations.SerializedName;

public class TableData {

    @SerializedName("_id")
    private String idMeja;

    @SerializedName("_noMeja")
    private String noMeja;

    public String getIdMeja() {
        return idMeja;
    }

    public void setIdMeja(String idMeja) {
        this.idMeja = idMeja;
    }

    public void setNoMeja(String noMeja) {
        this.noMeja = noMeja;
    }

    public String getNoMeja() {
        return noMeja;
    }

    @Override
    public String toString() {
        return
                "TableData{" +
                        "_noMeja = '" + noMeja + '\'' +
                        "}";
    }
}