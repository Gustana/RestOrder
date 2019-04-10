package co.ambystic.restorder.view.fragment.admin.tab.menu;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import co.ambystic.restorder.view.menu.AddMenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminFoodFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = AdminFoodFragment.class.getSimpleName();

    private RecyclerFoodAdminAdapter recyclerFoodAdminAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvFoodAdmin;
    private FloatingActionButton fabAddFood;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_food, container, false);

        initLayout(view);

        rvFoodAdmin.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setRefreshing(true);
        onRefresh();

        onButtonClicked();

        return view;
    }

    private void onButtonClicked() {
        fabAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddMenuActivity.class);
                i.putExtra("menuKind", "1");
                startActivity(i);
            }
        });
    }

    @Override
    public void onRefresh() {
        getFoodList();
    }

    private void getFoodList() {
        DataService dataService = RetrofitClient.getInstance().create(DataService.class);
        Call<MenuResponse> menuResponseCall = dataService.getFoodForAdmin();
        menuResponseCall.enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(@NonNull Call<MenuResponse> call, @NonNull Response<MenuResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().getServerResponse().isError()) {
                        Log.i(TAG, "onResponse: Food List : IsError : Empty Data");
                    } else {
                        List<MenuData> menuData = response.body().getServerResponse().getData();
                        recyclerFoodAdminAdapter = new RecyclerFoodAdminAdapter(getContext(), menuData);

                        rvFoodAdmin.setAdapter(recyclerFoodAdminAdapter);
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

        swipeRefreshLayout.setRefreshing(false);
    }

    private void initLayout(View view) {
        rvFoodAdmin = view.findViewById(R.id.rvFoodAdmin);
        fabAddFood = view.findViewById(R.id.fabAddFood);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    }

}
