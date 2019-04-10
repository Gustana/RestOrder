package co.ambystic.restorder.model.serverResponse.report;

import com.google.gson.annotations.SerializedName;

public class ReportResponse {

    @SerializedName("serverResponse")
    private ServerResponseReport serverResponseReport;

    public void setServerResponseReport(ServerResponseReport serverResponseReport) {
        this.serverResponseReport = serverResponseReport;
    }

    public ServerResponseReport getServerResponseReport() {
        return serverResponseReport;
    }

    @Override
    public String toString() {
        return
                "ReportResponse{" +
                        "serverResponseReport = '" + serverResponseReport + '\'' +
                        "}";
    }
}