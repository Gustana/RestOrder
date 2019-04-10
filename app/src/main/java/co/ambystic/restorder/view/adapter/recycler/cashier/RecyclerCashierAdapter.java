package co.ambystic.restorder.view.adapter.recycler.cashier;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.order.OrderData;

public class RecyclerCashierAdapter extends RecyclerView.Adapter<RecyclerCashierAdapter.CashierAdapterUI> {

    private List<OrderData> orderDataList;

    public RecyclerCashierAdapter(List<OrderData> orderDataList) {
        this.orderDataList = orderDataList;
    }

    @NonNull
    @Override
    public CashierAdapterUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_cart_costumer_item, viewGroup, false);
        return new CashierAdapterUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CashierAdapterUI cashierAdapterUI, int i) {
        OrderData orderData = orderDataList.get(i);
        cashierAdapterUI.bindView(orderData);
    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }

    class CashierAdapterUI extends RecyclerView.ViewHolder {
        private TextView txtMenuName, txtMenuPrice, txtMenuOrderQuantity, txtOrderTotalPrice;

        CashierAdapterUI(@NonNull View itemView) {
            super(itemView);
            txtMenuName = itemView.findViewById(R.id.txtMenuName);
            txtMenuPrice = itemView.findViewById(R.id.txtMenuPrice);
            txtMenuOrderQuantity = itemView.findViewById(R.id.txtMenuOrderQuantity);
            txtOrderTotalPrice = itemView.findViewById(R.id.txtOrderTotalPrice);
        }

        void bindView(OrderData orderData) {
            int orderPrice = Integer.parseInt(orderData.getHargaMasakan());
            int orderQuantity = Integer.parseInt(orderData.getJumlahOrder());
            int orderTotalPrice = orderPrice * orderQuantity;

            txtMenuName.setText(orderData.getNamaMasakan());
            txtMenuPrice.setText(orderData.getHargaMasakan());
            txtMenuOrderQuantity.setText(orderData.getJumlahOrder());
            txtOrderTotalPrice.setText(String.valueOf(orderTotalPrice));
        }
    }
}
