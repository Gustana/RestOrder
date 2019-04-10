package co.ambystic.restorder.view.adapter.recycler.admin.menu;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.menu.MenuData;
import co.ambystic.restorder.model.serverResponse.register.RegisterReponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.util.SpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerFoodAdminAdapter extends RecyclerView.Adapter<RecyclerFoodAdminAdapter.RecyclerFoodUI> {

    private static final String TAG = RecyclerFoodAdminAdapter.class.getSimpleName();

    private List<MenuData> menuDataList;
    private Context context;

    //Dialog Component
    private TextView txtMenuName, txtMenuPrice, txtMenuDesc;
    private Button btnAddToCart;
    private EditText edtOrderDesc, edtOrderQuantity, edtDetailDesc;

    private SpManager spManager;

    private int tableNo, userLevel, menuStatus;

    public RecyclerFoodAdminAdapter(Context context, List<MenuData> menuDataList) {
        this.menuDataList = menuDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerFoodUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_food_admin, viewGroup, false);
        return new RecyclerFoodUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerFoodUI recyclerFoodUI, int i) {
        final MenuData menuData = menuDataList.get(i);

        spManager = new SpManager(context);

        userLevel = spManager.getIntData(SpManager.LEVEL_USER);

        if (userLevel == 5) {
            menuStatus = 1;
        } else {
            menuStatus = 2;
        }

        recyclerFoodUI.bindView(menuData);

        if (userLevel == 5) {

            recyclerFoodUI.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_menu_detail);

                    initLayoutDialog(dialog);

                    txtMenuDesc.setText(menuData.getDeskripsiMasakan());
                    txtMenuName.setText(menuData.getNamaMasakan());
                    txtMenuPrice.setText(menuData.getHarga());

                    if (spManager.getIntData(SpManager.TABLE_NO) != null) {
                        tableNo = spManager.getIntData(SpManager.TABLE_NO);
                    }

                    btnAddToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int orderQuantity = Integer.parseInt(edtOrderQuantity.getText().toString());
                            final String orderDesc = edtOrderDesc.getText().toString();
                            String detailDesc = edtDetailDesc.getText().toString();

                            Log.i(TAG, "onClick: " + orderQuantity);
                            DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                            Call<RegisterReponse> makeOrderCall = dataService.makeOrder(
                                    tableNo,
                                    spManager.getIntData(SpManager.ID_USER),
                                    orderDesc,
                                    Integer.parseInt(menuData.getIdMasakan()),
                                    menuStatus,
                                    orderQuantity,
                                    detailDesc);

                            makeOrderCall.enqueue(new Callback<RegisterReponse>() {
                                @Override
                                public void onResponse(@NonNull Call<RegisterReponse> call, @NonNull Response<RegisterReponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().getServerResponse().isError()) {
                                            Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<RegisterReponse> call, @NonNull Throwable t) {
                                    Log.e(TAG, "onFailure: " + t.getMessage());
                                    Log.e(TAG, "onFailure: ", t.getCause());
                                }
                            });
                            dialog.dismiss();

                        }
                    });

                    dialog.show();
                }
            });
        } else if (userLevel == 4) {
            recyclerFoodUI.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_edit_menu_stock);

                    final EditText edtMenuStock = dialog.findViewById(R.id.edtMenuStock);
                    Button btnUpdateMenu = dialog.findViewById(R.id.btnUpdateMenu);
                    Button btnDeleteMenu = dialog.findViewById(R.id.btnDeleteMenu);

                    btnUpdateMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                            Call<RegisterReponse> updateStockCall = dataService.editStockMenu(
                                    Integer.parseInt(menuData.getIdMasakan()),
                                    Integer.parseInt(edtMenuStock.getText().toString()));

                            updateStockCall.enqueue(new Callback<RegisterReponse>() {
                                @Override
                                public void onResponse(@NonNull Call<RegisterReponse> call, @NonNull Response<RegisterReponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().getServerResponse().isError()) {
                                            Log.i(TAG, "onResponse: An error occurred when update stock menu");
                                        } else {
                                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<RegisterReponse> call, @NonNull Throwable t) {
                                    Log.e(TAG, "onFailure: ", t.getCause());
                                    Log.e(TAG, "onFailure: " + t.getMessage());
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.dismiss();
                        }
                    });

                    btnDeleteMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                            Call<RegisterReponse> deleteMenuCall = dataService.deleteMenu(Integer.parseInt(menuData.getIdMasakan()));
                            deleteMenuCall.enqueue(new Callback<RegisterReponse>() {
                                @Override
                                public void onResponse(@NonNull Call<RegisterReponse> call, @NonNull Response<RegisterReponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().getServerResponse().isError()) {
                                            Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
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

                    dialog.show();
                }
            });
        }
    }

    private void initLayoutDialog(Dialog dialog) {
        txtMenuDesc = dialog.findViewById(R.id.txtMenuDesc);
        txtMenuName = dialog.findViewById(R.id.txtMenuName);
        txtMenuPrice = dialog.findViewById(R.id.txtMenuPrice);
        btnAddToCart = dialog.findViewById(R.id.btnOrder);
        edtOrderDesc = dialog.findViewById(R.id.edtOrderDesc);
        edtOrderQuantity = dialog.findViewById(R.id.edtOrderQuantity);
        edtDetailDesc = dialog.findViewById(R.id.edtDetailDesc);
    }

    @Override
    public int getItemCount() {
        return menuDataList.size();
    }

    class RecyclerFoodUI extends RecyclerView.ViewHolder {

        private TextView txtFoodName, txtFoodPrice, txtFoodDesc, txtFoodStatus;

        RecyclerFoodUI(@NonNull View itemView) {
            super(itemView);

            txtFoodDesc = itemView.findViewById(R.id.txtFoodDesc);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
            txtFoodStatus = itemView.findViewById(R.id.txtFoodStatus);
        }

        void bindView(MenuData menuData) {
            txtFoodName.setText(menuData.getNamaMasakan());
            txtFoodPrice.setText(menuData.getHarga());
            txtFoodDesc.setText(menuData.getDeskripsiMasakan());

            if (Integer.parseInt(menuData.getStatusMasakan()) == 1) {
                txtFoodStatus.setText(R.string.txt_menu_available);
            } else {
                txtFoodStatus.setText(R.string.txt_menu_sold_out);
            }
        }
    }
}
