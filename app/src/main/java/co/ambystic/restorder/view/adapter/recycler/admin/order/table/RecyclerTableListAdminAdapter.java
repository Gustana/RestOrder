package co.ambystic.restorder.view.adapter.recycler.admin.order.table;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.table.TableData;
import co.ambystic.restorder.view.order.MakeOrderActivity;

public class RecyclerTableListAdminAdapter extends RecyclerView.Adapter<RecyclerTableListAdminAdapter.RecyclerTableListUI> {
    private List<TableData> tableDataList;
    private Context context;

    public RecyclerTableListAdminAdapter(Context context, List<TableData> tableDataList) {
        this.context = context;
        this.tableDataList = tableDataList;
    }

    @NonNull
    @Override
    public RecyclerTableListUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_table_admin, viewGroup, false);
        return new RecyclerTableListUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerTableListUI recyclerTableListUI, int i) {
        final TableData tableData = tableDataList.get(i);
        recyclerTableListUI.bindView(tableData);

        recyclerTableListUI.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get table id proceed to oders based on the table id
                int noMeja = Integer.parseInt(tableData.getNoMeja());
                Intent i = new Intent(context, MakeOrderActivity.class);
                i.putExtra("noMeja", noMeja);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return tableDataList.size();
    }

    class RecyclerTableListUI extends RecyclerView.ViewHolder {
        private TextView txtTableNo;

        RecyclerTableListUI(@NonNull View itemView) {
            super(itemView);
            txtTableNo = itemView.findViewById(R.id.txtTableNo);
        }

        private void bindView(TableData tableData) {
            txtTableNo.setText(tableData.getNoMeja());
        }
    }
}
