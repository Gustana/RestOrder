package co.ambystic.restorder.view.fragment.admin.tab.order;


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
import android.widget.Toast;

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.menu.MenuData;
import co.ambystic.restorder.model.serverResponse.menu.MenuResponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.util.SpManager;
import co.ambystic.restorder.view.adapter.recycler.admin.order.menu.RecyclerFoodOrderAdminAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = FoodOrderFragment.class.getSimpleName();

    private SpManager spManager;
    private RecyclerFoodOrderAdminAdapter recyclerFoodOrderAdminAdapter;

    private RecyclerView rvFoodOrderList;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Context context;

    private int tableNo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_order, container, false);

        rvFoodOrderList = view.findViewById(R.id.rvFoodOrderList);

        spManager = new SpManager(context);
        //get tableNo from intent
        tableNo = getActivity().getIntent().getIntExtra("noMeja", 0);

        initLayout(view);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        rvFoodOrderList.setLayoutManager(new LinearLayoutManager(getContext()));

        onRefresh();

        return view;
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
                        int userId = spManager.getIntData(SpManager.ID_USER);
                        if (tableNo != 0) {
                            recyclerFoodOrderAdminAdapter = new RecyclerFoodOrderAdminAdapter(tableNo, userId, getContext(), menuData);
                        } else {
                            Toast.makeText(context, "Error get table Noo", Toast.LENGTH_SHORT).show();
                        }

                        rvFoodOrderList.setAdapter(recyclerFoodOrderAdminAdapter);
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
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    }
}