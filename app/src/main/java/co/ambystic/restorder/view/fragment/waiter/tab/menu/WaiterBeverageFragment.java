package co.ambystic.restorder.view.fragment.waiter.tab.menu;


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

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.menu.MenuData;
import co.ambystic.restorder.model.serverResponse.menu.MenuResponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.view.adapter.recycler.admin.menu.RecyclerFoodAdminAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaiterBeverageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = WaiterBeverageFragment.class.getSimpleName();

    private RecyclerFoodAdminAdapter recyclerBeverageWaiterAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvFoodWaiter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_waiter_beverage, container, false);

        initLayout(view);

        rvFoodWaiter.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setRefreshing(true);
        onRefresh();

        return view;
    }

    @Override
    public void onRefresh() {
        getBeverageList();
    }

    private void getBeverageList() {
        if (swipeRefreshLayout.isRefreshing()) {
            DataService dataService = RetrofitClient.getInstance().create(DataService.class);
            Call<MenuResponse> menuResponseCall = dataService.getBeverageForAdmin();
            menuResponseCall.enqueue(new Callback<MenuResponse>() {
                @Override
                public void onResponse(@NonNull Call<MenuResponse> call, @NonNull Response<MenuResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getServerResponse().isError()) {
                            Log.i(TAG, "onResponse: Beverage List : IsError : Empty Data");
                        } else {
                            List<MenuData> menuData = response.body().getServerResponse().getData();
                            recyclerBeverageWaiterAdapter = new RecyclerFoodAdminAdapter(getContext(), menuData);

                            rvFoodWaiter.setAdapter(recyclerBeverageWaiterAdapter);
                        }
                    } else {
                        Log.i(TAG, "onResponse: Food List : null response body from server");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MenuResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    Log.e(TAG, "onFailure: ", t.getCause());
                }
            });
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initLayout(View view) {
        rvFoodWaiter = view.findViewById(R.id.rvBeverageWaiter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    }

}
