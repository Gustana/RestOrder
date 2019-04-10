package co.ambystic.restorder.view.fragment.costumer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.orderedMenu.OrderedData;
import co.ambystic.restorder.model.serverResponse.orderedMenu.OrderedResponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.util.SpManager;
import co.ambystic.restorder.view.adapter.recycler.costumer.RecyclerCstmOrdMenuAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CostumerOrderedMenuFragment extends Fragment {

    private final String TAG = CostumerOrderedMenuFragment.class.getSimpleName();

    private TextView txtOrderTotalPrice;
    private RecyclerView rvCostumerOrdMenuList;
    private SpManager spManager;
    private RecyclerCstmOrdMenuAdapter recyclerCstmOrdMenuAdapter;

    private int tableNo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_costumer_ordered_menu, container, false);

        spManager = new SpManager(getContext());
        tableNo = spManager.getIntData(SpManager.TABLE_NO);

        Log.i(TAG, "onCreateView: tableNo :" + tableNo);

        initLayout(view);
        rvCostumerOrdMenuList.setLayoutManager(new LinearLayoutManager(getContext()));

        getCostumerOrderedMenu();

        return view;
    }

    private void getCostumerOrderedMenu() {
        DataService dataService = RetrofitClient.getInstance().create(DataService.class);
        Call<OrderedResponse> orderedResponseCall = dataService.getCostumerOrderedMenu(tableNo);
        orderedResponseCall.enqueue(new Callback<OrderedResponse>() {
            @Override
            public void onResponse(@NonNull Call<OrderedResponse> call, @NonNull Response<OrderedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getServerResponseOrdered().isIsError()) {
                        //Data Empty
                        Log.i(TAG, "onResponse: Data Empty");
                    } else {
                        List<OrderedData> orderedDataList = response.body().getServerResponseOrdered().getData();

                        Log.i(TAG, "onResponse: orderedData : " + orderedDataList.toString());

                        recyclerCstmOrdMenuAdapter = new RecyclerCstmOrdMenuAdapter(getContext(), orderedDataList);
                        rvCostumerOrdMenuList.setAdapter(recyclerCstmOrdMenuAdapter);

                        int totalPrice = 0;

                        for (OrderedData orderedData : response.body().getServerResponseOrdered().getData()) {
                            int menuPrice = Integer.parseInt(orderedData.getHargaMasakan());
                            int orderQuantity = Integer.parseInt(orderedData.getJumlahOrder());
                            int total = menuPrice * orderQuantity;

                            totalPrice += total;

                        }

                        txtOrderTotalPrice.setText(String.valueOf(totalPrice));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderedResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t.getCause());
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void initLayout(View view) {
        rvCostumerOrdMenuList = view.findViewById(R.id.rvCostumerOrdMenuList);
        txtOrderTotalPrice = view.findViewById(R.id.txtOrderTotalPrice);
    }

}
