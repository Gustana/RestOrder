package co.ambystic.restorder.view.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import co.ambystic.restorder.R;
import co.ambystic.restorder.view.adapter.tab.TabMenuAdapter;
import co.ambystic.restorder.view.fragment.admin.tab.order.BeverageOrderFragment;
import co.ambystic.restorder.view.fragment.admin.tab.order.FoodOrderFragment;
import co.ambystic.restorder.view.fragment.admin.tab.order.ListFoodOrderFragment;

public class MakeOrderActivity extends AppCompatActivity {

    private TabMenuAdapter tabMenuAdapter;

    private TabLayout tabLayoutOrder;
    private ViewPager vPagerOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        initLayout();

        getSupportActionBar().setElevation(0f);

        tabMenuAdapter = new TabMenuAdapter(getSupportFragmentManager());
        tabMenuAdapter.addFragment(new FoodOrderFragment(), "Makanan");
        tabMenuAdapter.addFragment(new BeverageOrderFragment(), "Minuman");
        tabMenuAdapter.addFragment(new ListFoodOrderFragment(), "Keranjang");

        vPagerOrder.setAdapter(tabMenuAdapter);
        tabLayoutOrder.setupWithViewPager(vPagerOrder);

    }

    private void initLayout() {
        tabLayoutOrder = findViewById(R.id.tabLayoutOrder);
        vPagerOrder = findViewById(R.id.vPagerOrder);
    }
}