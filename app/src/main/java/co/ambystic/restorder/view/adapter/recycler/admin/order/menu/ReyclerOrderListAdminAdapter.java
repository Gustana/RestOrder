package co.ambystic.restorder.view.adapter.recycler.admin.order.menu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.sqlite.food_order.FoodOrderTableModel;

public class ReyclerOrderListAdminAdapter extends RecyclerView.Adapter<ReyclerOrderListAdminAdapter.RecyclerAdapterUI> {
    private List<FoodOrderTableModel> menuOrderModelList = new ArrayList<>();

    public ReyclerOrderListAdminAdapter(List<FoodOrderTableModel> menuOrderModelList) {
        this.menuOrderModelList = menuOrderModelList;
    }

    @NonNull
    @Override
    public RecyclerAdapterUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_order_list_admin, viewGroup, false);
        return new RecyclerAdapterUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterUI recyclerAdapterUI, int i) {
        FoodOrderTableModel menuOrderModel = menuOrderModelList.get(i);
        recyclerAdapterUI.bindItem(menuOrderModel);
    }

    @Override
    public int getItemCount() {
        return menuOrderModelList.size();
    }

    class RecyclerAdapterUI extends RecyclerView.ViewHolder {
        TextView txtFoodName, txtFoodOrderQuantity, txtFoodPrice, txtFoodTotalPrice;

        RecyclerAdapterUI(@NonNull View itemView) {
            super(itemView);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodOrderQuantity = itemView.findViewById(R.id.txtFoodOrderQuantity);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
            txtFoodTotalPrice = itemView.findViewById(R.id.txtFoodTotalPrice);
        }

        void bindItem(FoodOrderTableModel menuOrderModel) {
            txtFoodName.setText(menuOrderModel.getFoodName());
            txtFoodOrderQuantity.setText(String.valueOf(menuOrderModel.getOrderQuantity()));
            txtFoodPrice.setText(String.valueOf(menuOrderModel.getFoodPrice()));

            int foodTotalPrice = menuOrderModel.getFoodPrice() * menuOrderModel.getOrderQuantity();
            txtFoodTotalPrice.setText(String.valueOf(foodTotalPrice));

        }
    }
}
