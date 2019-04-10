package co.ambystic.restorder.view.adapter.recycler.admin.menu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.menu.MenuData;

public class RecyclerBeverageAdminAdapter extends RecyclerView.Adapter<RecyclerBeverageAdminAdapter.RecyclerFoodUI> {

    private List<MenuData> menuDataList;

    public RecyclerBeverageAdminAdapter(List<MenuData> menuDataList) {
        this.menuDataList = menuDataList;
    }

    @NonNull
    @Override
    public RecyclerFoodUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_food_admin, viewGroup, false);
        return new RecyclerFoodUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerFoodUI recyclerFoodUI, int i) {
        MenuData menuData = menuDataList.get(i);

        recyclerFoodUI.bindView(menuData);
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

            if (Integer.parseInt(menuData.getStatusMasakan()) == 1){
                txtFoodStatus.setText(R.string.txt_menu_available);
            }else{
                txtFoodStatus.setText(R.string.txt_menu_sold_out);
            }
        }
    }
}
