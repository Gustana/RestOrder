package co.ambystic.restorder.view.fragment.cashier;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.order.OrderData;
import co.ambystic.restorder.model.serverResponse.order.OrderResponse;
import co.ambystic.restorder.model.serverResponse.register.RegisterReponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.util.SpManager;
import co.ambystic.restorder.view.adapter.recycler.cashier.RecyclerCashierAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashierOrderListFragment extends Fragment {

    private final String TAG = CashierOrderListFragment.class.getSimpleName();

    private TextView txtOrderTotalPrice;
    private EditText edtTableNo, edtUserPay;
    private Button btnCheckout, btnFindOrder;
    private RecyclerView rvOrderList;
    private SpManager spManager;
    private List<OrderData> orderDataList;
    private RecyclerCashierAdapter recyclerCashierAdapter;

    private int orderTotalPrice, idUser, idOrder;
    private int total = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cashier_order_list, container, false);
        orderDataList = new ArrayList<>();
        spManager = new SpManager(getContext());
        idUser = spManager.getIntData(SpManager.ID_USER);
        initLayout(view);

        txtOrderTotalPrice.setText(String.valueOf(total));
        rvOrderList.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonClicked();

        return view;
    }

    private void getCostumerOrderList(int tableNo) {
        if (!orderDataList.isEmpty()) {
            txtOrderTotalPrice.setText("0");
            orderDataList.clear();
            recyclerCashierAdapter.notifyDataSetChanged();
        }

        DataService dataService = RetrofitClient.getInstance().create(DataService.class);
        Call<OrderResponse> menuResponseCall = dataService.getMenuOrderPerTable(tableNo);
        menuResponseCall.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<OrderResponse> call, @NonNull Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getServerResponseOrder().isError()) {
                        Log.i(TAG, "onResponse: Error get Menu");
                        Toast.makeText(getContext(), "Order Kosong", Toast.LENGTH_SHORT).show();
                    } else {

                        idOrder = Integer.parseInt(response.body().getServerResponseOrder().getData().get(0).getIdOrder());

                        for (OrderData orderData : response.body().getServerResponseOrder().getData()) {
                            int orderPrice = Integer.parseInt(orderData.getHargaMasakan());
                            int orderQuantity = Integer.parseInt(orderData.getJumlahOrder());
                            orderTotalPrice = orderPrice * orderQuantity;
                            total += orderTotalPrice;
                        }

                        txtOrderTotalPrice.setText(String.valueOf(total));

                        orderDataList = response.body().getServerResponseOrder().getData();
                        recyclerCashierAdapter = new RecyclerCashierAdapter(orderDataList);
                        rvOrderList.setAdapter(recyclerCashierAdapter);
                        recyclerCashierAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderResponse> call, @NonNull Throwable t) {
                call.cancel();
                Log.e(TAG, "onFailure: " + t.getMessage());
                Log.e(TAG, "onFailure: ", t.getCause());
            }
        });
    }

    private void buttonClicked() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userPay = Integer.parseInt(edtUserPay.getText().toString());
                final int balance = userPay - total;
                if (!edtUserPay.getText().toString().isEmpty()) {
                    if (userPay >= total) {
                        DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                        Call<RegisterReponse> makeTransactionCall = dataService.makeTransaction(
                                idUser,
                                idOrder,
                                total
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
                                        edtUserPay.getText().clear();
                                        orderDataList.clear();
                                        recyclerCashierAdapter.notifyDataSetChanged();

                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                                        alertDialog.setTitle("Kembalian");
                                        alertDialog.setMessage(String.valueOf(balance));
                                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        alertDialog.show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<RegisterReponse> call, @NonNull Throwable t) {
                                Log.e(TAG, "onFailure: ", t.getCause());
                                Log.e(TAG, "onFailure: " + t.getMessage());
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Uangnya Kurang", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Isi form total bayar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFindOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tableNo = Integer.parseInt(edtTableNo.getText().toString());
                getCostumerOrderList(tableNo);
            }
        });
    }

    private void initLayout(View view) {
        txtOrderTotalPrice = view.findViewById(R.id.txtOrderTotalPrice);
        btnCheckout = view.findViewById(R.id.btnCheckout);
        rvOrderList = view.findViewById(R.id.rvCashierOrderList);
        edtTableNo = view.findViewById(R.id.edtTableNo);
        edtUserPay = view.findViewById(R.id.edtUserPay);
        btnFindOrder = view.findViewById(R.id.btnFindOrder);
    }

}
