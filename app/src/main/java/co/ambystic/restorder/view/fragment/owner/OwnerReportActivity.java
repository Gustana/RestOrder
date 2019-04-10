package co.ambystic.restorder.view.fragment.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import co.ambystic.restorder.R;
import co.ambystic.restorder.util.SpManager;
import co.ambystic.restorder.view.adapter.tab.TabMenuAdapter;
import co.ambystic.restorder.view.auth.LoginActivity;
import co.ambystic.restorder.view.fragment.admin.tab.report.BeverageReportFragment;
import co.ambystic.restorder.view.fragment.admin.tab.report.FoodReportFragment;

public class OwnerReportActivity extends AppCompatActivity {

    private TabLayout tabLayoutReportOwner;
    private ViewPager vPagerReportOwner;

    private TabMenuAdapter tabMenuAdapter;
    private SpManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_report);

        spManager = new SpManager(this);

        initLayout();

        tabMenuAdapter = new TabMenuAdapter(getSupportFragmentManager());
        tabMenuAdapter.addFragment(new FoodReportFragment(), "Makanan");
        tabMenuAdapter.addFragment(new BeverageReportFragment(), "Minuman");

        vPagerReportOwner.setAdapter(tabMenuAdapter);
        tabLayoutReportOwner.setupWithViewPager(vPagerReportOwner);

        getSupportActionBar().setElevation(0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                spManager.destroySession();
                Intent i = new Intent(OwnerReportActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initLayout() {
        tabLayoutReportOwner = findViewById(R.id.tabLayoutReportOwner);
        vPagerReportOwner = findViewById(R.id.vPagerReportOwner);
    }
}
