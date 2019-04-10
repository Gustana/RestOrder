package co.ambystic.restorder.view.fragment.admin.tab.report;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.report.ReportData;
import co.ambystic.restorder.model.serverResponse.report.ReportResponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.view.adapter.recycler.admin.report.RecyclerReportAdminAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeverageReportFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = FoodReportFragment.class.getSimpleName();

    private RecyclerReportAdminAdapter recyclerReportAdminAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner spMonthFilter;
    private EditText edtYearFilter;
    private Button btnFilter;
    private RecyclerView rvAdminBeverageReportList;
    private Context context;
    private TextView txtTotalIncome;

    private int totalIncome;
    private int monthFilter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_beverage_report, container, false);

        initLayout(view);

        List<String> monthList = Arrays.asList(getResources().getStringArray(R.array.month_list));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, monthList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonthFilter.setAdapter(arrayAdapter);

        rvAdminBeverageReportList.setLayoutManager(new LinearLayoutManager(getContext()));

        rvAdminBeverageReportList.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout.setOnRefreshListener(this);
        getBeverageList();

        buttonClicked();
        monthFilter();

        return view;
    }

    private void monthFilter() {
        spMonthFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthFilter = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                monthFilter = 1;
            }
        });
    }

    private void getBeverageList() {
        DataService dataService = RetrofitClient.getInstance().create(DataService.class);
        Call<ReportResponse> reportResponseCall = dataService.getReport("2");
        reportResponseCall.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReportResponse> call, @NonNull Response<ReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getServerResponseReport().isError()) {
                        Log.i(TAG, "onResponse:  Food List : IsError : Empty Data");
                    } else {
                        List<ReportData> reportDataList = response.body().getServerResponseReport().getData();
                        recyclerReportAdminAdapter = new RecyclerReportAdminAdapter(reportDataList);
                        recyclerReportAdminAdapter.notifyDataSetChanged();
                        rvAdminBeverageReportList.setAdapter(recyclerReportAdminAdapter);

                        for (ReportData reportData : response.body().getServerResponseReport().getData()) {
                            totalIncome += Integer.parseInt(reportData.getTotalBayar());
                        }

                    }
                    txtTotalIncome.setText(String.valueOf(totalIncome));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReportResponse> call, @NonNull Throwable t) {
                call.cancel();
                Log.e(TAG, "onFailure: ", t.getCause());
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initLayout(View view) {
        edtYearFilter = view.findViewById(R.id.edtYearFilter);
        spMonthFilter = view.findViewById(R.id.spMonthFilter);
        btnFilter = view.findViewById(R.id.btnFilter);
        rvAdminBeverageReportList = view.findViewById(R.id.rvAdminBeverageReportList);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        txtTotalIncome = view.findViewById(R.id.txtTotalIncome);
    }

    @Override
    public void onRefresh() {
        totalIncome = 0;
        getBeverageList();
    }

    public void buttonClicked() {
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();

                String yearFilter, newMonthFilter = String.valueOf(monthFilter);

                yearFilter = edtYearFilter.getText().toString();

                if (monthFilter < 10) {
                    newMonthFilter = "0" + String.valueOf(monthFilter);
                }

                if (yearFilter.isEmpty()) {
                    Toast.makeText(getContext(), "Isi Semua Form", Toast.LENGTH_SHORT).show();
                } else {
                    DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                    Call<ReportResponse> reportResponseCall = dataService.getReportFiltered("2", newMonthFilter, yearFilter);
                    reportResponseCall.enqueue(new Callback<ReportResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ReportResponse> call, @NonNull Response<ReportResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().getServerResponseReport().isError()) {
                                    Log.i(TAG, "onResponse Filtered:  Food List : IsError : Empty Data");
                                    Toast.makeText(getContext(), "Tidak ada penjualan pada waktu filter", Toast.LENGTH_SHORT).show();
                                } else {
                                    List<ReportData> reportDataList = response.body().getServerResponseReport().getData();
                                    recyclerReportAdminAdapter = new RecyclerReportAdminAdapter(reportDataList);
                                    rvAdminBeverageReportList.setAdapter(recyclerReportAdminAdapter);
                                    for (ReportData reportData : response.body().getServerResponseReport().getData()) {
                                        totalIncome += Integer.parseInt(reportData.getTotalBayar());
                                    }
                                    Log.i(TAG, "onResponse Filter: totalIncome : " + totalIncome);
                                }
                                swipeRefreshLayout.setRefreshing(false);
                                txtTotalIncome.setText(String.valueOf(totalIncome));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ReportResponse> call, @NonNull Throwable t) {
                            call.cancel();
                            Log.e(TAG, "onFailure Food Filter: ", t.getCause());
                            Log.e(TAG, "onFailure Food Filter: " + t.getMessage());
                        }
                    });
                }
            }
        });
    }
}
