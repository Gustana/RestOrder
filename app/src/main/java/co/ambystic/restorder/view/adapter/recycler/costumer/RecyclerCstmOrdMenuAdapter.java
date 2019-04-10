package co.ambystic.restorder.view.adapter.recycler.costumer;

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
import co.ambystic.restorder.model.serverResponse.orderedMenu.OrderedData;
import co.ambystic.restorder.model.serverResponse.register.RegisterReponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.util.SpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerCstmOrdMenuAdapter extends RecyclerView.Adapter<RecyclerCstmOrdMenuAdapter.RecyclerOrderedMenuUI> {
    private final String TAG = RecyclerCstmOrdMenuAdapter.class.getSimpleName();

    private List<OrderedData> orderedDataList;
    private Context context;
    private SpManager spManager;

    private int userLevel;

    public RecyclerCstmOrdMenuAdapter(Context context, List<OrderedData> orderedDataList) {
        this.context = context;
        this.orderedDataList = orderedDataList;
    }

    @NonNull
    @Override
    public RecyclerOrderedMenuUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_costumer_ordered_menu_item, viewGroup, false);
        spManager = new SpManager(context);
        userLevel = spManager.getIntData(SpManager.LEVEL_USER);
        return new RecyclerOrderedMenuUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerOrderedMenuUI recyclerOrderedMenuUI, int i) {
        final OrderedData orderedData = orderedDataList.get(i);
        recyclerOrderedMenuUI.bindView(orderedData);

        if (userLevel != 5) {
            recyclerOrderedMenuUI.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_change_order_status);

                    Button btnChangeMenuStat = dialog.findViewById(R.id.btnChangeMenuStat);
                    final EditText edtMenuStatus = dialog.findViewById(R.id.edtMenuStatus);

                    btnChangeMenuStat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                            Call<RegisterReponse> changeMenuStatCall = dataService.changeMenuStat(
                                    Integer.parseInt(orderedData.getOrderId()),
                                    Integer.parseInt(orderedData.getIdMasakan()),
                                    Integer.parseInt(edtMenuStatus.getText().toString()));
                            changeMenuStatCall.enqueue(new Callback<RegisterReponse>() {
                                @Override
                                public void onResponse(@NonNull Call<RegisterReponse> call, @NonNull Response<RegisterReponse> response) {
                                    if (response.isSuccessful() && response.body() != null){
                                        if (response.body().getServerResponse().isError()){
                                            Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                                        }else{
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
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderedDataList.size();
    }

    class RecyclerOrderedMenuUI extends RecyclerView.ViewHolder {

        private TextView txtMenuName, txtMenuPrice, txtMenuOrderQuantity, txtOrderTotalPrice;

        RecyclerOrderedMenuUI(@NonNull View itemView) {
            super(itemView);

            txtMenuName = itemView.findViewById(R.id.txtMenuName);
            txtMenuPrice = itemView.findViewById(R.id.txtMenuPrice);
            txtMenuOrderQuantity = itemView.findViewById(R.id.txtMenuOrderQuantity);
            txtOrderTotalPrice = itemView.findViewById(R.id.txtOrderTotalPrice);

        }

        void bindView(OrderedData orderedData) {

            int orderQuantity = Integer.parseInt(orderedData.getJumlahOrder());
            int menuPrice = Integer.parseInt(orderedData.getHargaMasakan());
            int totalPrice = orderQuantity * menuPrice;

            txtMenuOrderQuantity.setText(String.valueOf(orderQuantity));
            txtMenuName.setText(orderedData.getNamaMasakan());
            txtMenuPrice.setText(String.valueOf(menuPrice));
            txtOrderTotalPrice.setText(String.valueOf(totalPrice));
        }

    }
}
