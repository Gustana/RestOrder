package co.ambystic.restorder.view.fragment.admin.tab.order;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.orderedMenu.OrderedData;
import co.ambystic.restorder.model.serverResponse.orderedMenu.OrderedResponse;
import co.ambystic.restorder.model.serverResponse.register.RegisterReponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.util.SpManager;
import co.ambystic.restorder.view.adapter.recycler.costumer.RecyclerCstmOrdMenuAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFoodOrderFragment extends Fragment {

    private static final String TAG = ListFoodOrderFragment.class.getSimpleName();
    private Context context;
    private SpManager spManager;
    private List<OrderedData> orderedDataList;
    private RecyclerCstmOrdMenuAdapter recyclerCstmOrdMenuAdapter;
    private TextView txtOrderTotalPrice;
    private RecyclerView rvOrderList;
    private Button btnCheckout;
    private int tableNo, totalPrice = 0;
    private int userId, idOrder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_food_order, container, false);

        tableNo = getActivity().getIntent().getIntExtra("noMeja", 0);
        spManager = new SpManager(context);

        userId = spManager.getIntData(SpManager.ID_USER);

        initLayout(view);
        rvOrderList.setLayoutManager(new LinearLayoutManager(context));
        txtOrderTotalPrice.setText(String.valueOf(totalPrice));

        if (spManager.getIntData(SpManager.LEVEL_USER) != 4) {
            btnCheckout.setVisibility(View.GONE);
        }

        getOrderList();

        buttonClicked();

        return view;
    }

    private void buttonClicked() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                Call<RegisterReponse> makeTransactionCall = dataService.makeTransaction(
                        userId,
                        idOrder,
                        totalPrice
                );
                makeTransactionCall.enqueue(new Callback<RegisterReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<RegisterReponse> call, @NonNull Response<RegisterReponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getServerResponse().isError()) {
                                Log.e(TAG, "onResponse: Error when checkout order");
                            } else {
                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                txtOrderTotalPrice.setText("0");
                                orderedDataList.clear();
                                recyclerCstmOrdMenuAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegisterReponse> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: ", t.getCause());
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }

    private void getOrderList() {
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
                        idOrder = Integer.parseInt(response.body().getServerResponseOrdered().getData().get(0).getOrderId());

                        orderedDataList = response.body().getServerResponseOrdered().getData();
                        recyclerCstmOrdMenuAdapter = new RecyclerCstmOrdMenuAdapter(context, orderedDataList);
                        rvOrderList.setAdapter(recyclerCstmOrdMenuAdapter);

                        for (OrderedData orderedData : orderedDataList) {
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
        rvOrderList = view.findViewById(R.id.rvOrderList);
        txtOrderTotalPrice = view.findViewById(R.id.txtOrderTotalPrice);
        btnCheckout = view.findViewById(R.id.btnCheckout);
    }

}
