package co.ambystic.restorder.view.adapter.recycler.admin.report;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.report.ReportData;

public class RecyclerReportAdminAdapter extends RecyclerView.Adapter<RecyclerReportAdminAdapter.RecyclerReportUI> {

    private List<ReportData> reportDataLists;

    public RecyclerReportAdminAdapter(List<ReportData> reportDataLists) {
        this.reportDataLists = reportDataLists;
    }

    @NonNull
    @Override
    public RecyclerReportUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_report_admin, viewGroup, false);
        return new RecyclerReportUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerReportUI recyclerReportUI, int i) {
        ReportData reportData = reportDataLists.get(i);
        recyclerReportUI.bindView(reportData);
    }

    @Override
    public int getItemCount() {
        return reportDataLists.size();
    }

    class RecyclerReportUI extends RecyclerView.ViewHolder {

        private TextView txtFoodName, txtFoodTotalPrice, txtFoodOrdered;

        RecyclerReportUI(@NonNull View itemView) {
            super(itemView);

            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodTotalPrice = itemView.findViewById(R.id.txtFoodTotalPrice);
            txtFoodOrdered = itemView.findViewById(R.id.txtFoodOrdered);
        }

        void bindView(ReportData reportData) {
            int foodTotalPrice = Integer.parseInt(reportData.getHarga())*Integer.parseInt(reportData.getJumlahPesanan());

            txtFoodTotalPrice.setText(String.valueOf(foodTotalPrice));
            txtFoodName.setText(reportData.getNamaMasakan());
            txtFoodOrdered.setText(reportData.getJumlahPesanan());
        }
    }
}
