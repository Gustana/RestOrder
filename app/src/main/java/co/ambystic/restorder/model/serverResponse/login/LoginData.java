package co.ambystic.restorder.model.serverResponse.login;

import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("_id")
    private String idUSer;

    @SerializedName("_id_level")
    private String idLevel;

    @SerializedName("_namaUser")
    private String namaUser;

    @SerializedName("_username")
    private String username;

    @SerializedName("_noMeja")
    private String noMeja;

    public String getIdUSer() {
        return idUSer;
    }

    public String getIdLevel() {
        return idLevel;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public String getUsername() {
        return username;
    }

    public String getNoMeja() {
        return noMeja;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "idUSer='" + idUSer + '\'' +
                ", idLevel='" + idLevel + '\'' +
                ", namaUser='" + namaUser + '\'' +
                ", username='" + username + '\'' +
                ", noMeja='" + noMeja + '\'' +
                '}';
    }
}