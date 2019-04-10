package co.ambystic.restorder.view.fragment.admin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.table.TableData;
import co.ambystic.restorder.model.serverResponse.table.TableResponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.view.adapter.recycler.admin.order.table.RecyclerTableListAdminAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCartFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = AdminCartFragment.class.getSimpleName();

    private RecyclerView rvChairList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerTableListAdminAdapter recyclerTableListAdminAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.admin_fragment_cart, container, false);

        initLayout(view);
        rvChairList.setLayoutManager(new GridLayoutManager(getContext(), 3));

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();

        return view;
    }

    private void getTableListData() {
        DataService dataService = RetrofitClient.getInstance().create(DataService.class);
        Call<TableResponse> tableResponseCall = dataService.getTableForAdmin();
        tableResponseCall.enqueue(new Callback<TableResponse>() {
            @Override
            public void onResponse(@NonNull Call<TableResponse> call, @NonNull Response<TableResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TableData> tableDataList = response.body().getServerResponse().getData();
                    recyclerTableListAdminAdapter = new RecyclerTableListAdminAdapter(getContext(), tableDataList);
                    rvChairList.setAdapter(recyclerTableListAdminAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TableResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getCause());
                Log.e(TAG, "onFailure: ", t.getCause());
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initLayout(View view) {
        rvChairList = view.findViewById(R.id.rvChairList);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public void onRefresh() {
        getTableListData();
    }
}
