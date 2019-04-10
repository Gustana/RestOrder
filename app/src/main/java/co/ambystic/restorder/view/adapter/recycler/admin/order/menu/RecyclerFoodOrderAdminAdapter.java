package co.ambystic.restorder.view.adapter.recycler.admin.order.menu;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.menu.MenuData;
import co.ambystic.restorder.model.serverResponse.register.RegisterReponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerFoodOrderAdminAdapter extends RecyclerView.Adapter<RecyclerFoodOrderAdminAdapter.RecyclerFoodUI> {
    private final String TAG = RecyclerFoodOrderAdminAdapter.class.getSimpleName();
    private Context context;
    private List<MenuData> menuDataList;

    private int userId, tableNo;

    /**
     * @param userId       this parameter will be used to know who made the order
     * @param context      this parameter will be used for context of the activity
     * @param menuDataList this parameter is listData of menu
     */

    public RecyclerFoodOrderAdminAdapter(int tableNo, int userId, Context context, List<MenuData> menuDataList) {
        this.tableNo = tableNo;
        this.userId = userId;
        this.context = context;
        this.menuDataList = menuDataList;
    }

    @NonNull
    @Override
    public RecyclerFoodUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_food_order_admin, viewGroup, false);
        return new RecyclerFoodUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerFoodUI recyclerFoodUI, int i) {
        MenuData menuData = menuDataList.get(i);

        if (Integer.parseInt(menuData.getStatusMasakan()) == 2) {
            recyclerFoodUI.imgOrder.setEnabled(false);
        }

        recyclerFoodUI.bindView(menuData);
        recyclerFoodUI.buttonClicked(menuData);
    }

    @Override
    public int getItemCount() {
        return menuDataList.size();
    }

    class RecyclerFoodUI extends RecyclerView.ViewHolder {

        private TextView txtFoodName, txtFoodPrice, txtFoodDesc, txtFoodStatus;
        private ImageView imgOrder;

        RecyclerFoodUI(@NonNull View itemView) {
            super(itemView);

            txtFoodDesc = itemView.findViewById(R.id.txtFoodDesc);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
            txtFoodStatus = itemView.findViewById(R.id.txtFoodStatus);
            imgOrder = itemView.findViewById(R.id.imgOrder);
        }

        private void bindView(MenuData menuData) {
            txtFoodName.setText(menuData.getNamaMasakan());
            txtFoodPrice.setText(menuData.getHarga());
            txtFoodDesc.setText(menuData.getDeskripsiMasakan());

            if (Integer.parseInt(menuData.getStatusMasakan()) == 1) {
                txtFoodStatus.setText(R.string.txt_menu_available);
            } else {
                txtFoodStatus.setText(R.string.txt_menu_sold_out);
            }
        }

        private void buttonClicked(final MenuData menuData) {
            imgOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_order_menu);

                    Button btnOrder = dialog.findViewById(R.id.btnOrder);
                    final EditText edtOrderQuantity = dialog.findViewById(R.id.edtOrderQuantity);
                    final EditText edtOrderDesc = dialog.findViewById(R.id.edtOrderDesc);
                    final EditText edtDetailDesc = dialog.findViewById(R.id.edtDetailDesc);
                    TextView txtFoodName = dialog.findViewById(R.id.txtFoodName);

                    txtFoodName.setText(menuData.getNamaMasakan());

                    btnOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String orderDesc = edtOrderDesc.getText().toString();
                            String detailDesc = edtDetailDesc.getText().toString();
                            int orderQuantity = Integer.parseInt(edtOrderQuantity.getText().toString());
                            int menuId = Integer.parseInt(menuData.getIdMasakan());

                            DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                            Call<RegisterReponse> makeOrderCall = dataService.makeOrder(
                                    tableNo,
                                    userId,
                                    orderDesc,
                                    menuId,
                                    2,
                                    orderQuantity,
                                    detailDesc);

                            makeOrderCall.enqueue(new Callback<RegisterReponse>() {
                                @Override
                                public void onResponse(@NonNull Call<RegisterReponse> call, @NonNull Response<RegisterReponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().getServerResponse().isError()) {
                                            Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Berhasil Dong", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<RegisterReponse> call, @NonNull Throwable t) {
                                    Log.e(TAG, "onFailure: ", t.getCause());
                                    Log.e(TAG, "onFailure: " + t.getMessage());
                                }
                            });

                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                }
            });
        }
    }

}
