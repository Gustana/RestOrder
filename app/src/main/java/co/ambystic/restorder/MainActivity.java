package co.ambystic.restorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import co.ambystic.restorder.util.SpManager;
import co.ambystic.restorder.view.auth.LoginActivity;
import co.ambystic.restorder.view.fragment.admin.AdminCartFragment;
import co.ambystic.restorder.view.fragment.admin.AdminMenuFragment;
import co.ambystic.restorder.view.fragment.admin.AdminRegisterFragment;
import co.ambystic.restorder.view.fragment.admin.AdminReportFragment;
import co.ambystic.restorder.view.fragment.cashier.CashierOrderListFragment;
import co.ambystic.restorder.view.fragment.costumer.CostumerOrderedMenuFragment;
import co.ambystic.restorder.view.fragment.waiter.WaiterMenuFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private SpManager spManager;

    private BottomNavigationView navigationViewAdmin;
    private BottomNavigationView navigationViewCashier;
    private BottomNavigationView navigationViewWaiter;
    private BottomNavigationView navigationViewCostumers;

    private List<BottomNavigationView> bottomNavigationViewList;

    /**
     * This activity will get level user from session
     * set proper menu for specified level
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spManager = new SpManager(this);
        bottomNavigationViewList = new ArrayList<>();

        initLayout();
        setBotNavDisplay();
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
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //set which botNavView will display based on user level
    private void setBotNavDisplay() {
        int level = spManager.getIntData(SpManager.LEVEL_USER);

        if (level == 1) {
            insertBottomNavViewToList(navigationViewCashier);
            insertBottomNavViewToList(navigationViewWaiter);
            insertBottomNavViewToList(navigationViewAdmin);
            insertBottomNavViewToList(navigationViewCostumers);

            hideNavigationView(bottomNavigationViewList);


        } else if (level == 2) {
            // insertBottomNavViewToList(navigationViewOwner);
            insertBottomNavViewToList(navigationViewWaiter);
            insertBottomNavViewToList(navigationViewAdmin);
            insertBottomNavViewToList(navigationViewCostumers);

            hideNavigationView(bottomNavigationViewList);

            loadFragment(new CashierOrderListFragment());
        } else if (level == 3) {
            //insertBottomNavViewToList(navigationViewOwner);
            insertBottomNavViewToList(navigationViewCashier);
            insertBottomNavViewToList(navigationViewAdmin);
            insertBottomNavViewToList(navigationViewCostumers);

            hideNavigationView(bottomNavigationViewList);

            loadFragment(new WaiterMenuFragment());
        } else if (level == 4) {
            //  insertBottomNavViewToList(navigationViewOwner);
            insertBottomNavViewToList(navigationViewCashier);
            insertBottomNavViewToList(navigationViewWaiter);
            insertBottomNavViewToList(navigationViewCostumers);

            hideNavigationView(bottomNavigationViewList);

            loadFragment(new AdminMenuFragment());
        } else {
            insertBottomNavViewToList(navigationViewCashier);
            insertBottomNavViewToList(navigationViewWaiter);
            insertBottomNavViewToList(navigationViewAdmin);

            hideNavigationView(bottomNavigationViewList);

            loadFragment(new WaiterMenuFragment());
        }
    }

    //insert bottomNavigationView to list
    private void insertBottomNavViewToList(BottomNavigationView navigationView) {
        bottomNavigationViewList.add(navigationView);
    }

    //hide all bottomNavigationViewList Component
    private void hideNavigationView(List<BottomNavigationView> bottomNavigationViewList) {
        for (BottomNavigationView bottomNavigationView : bottomNavigationViewList) {
            bottomNavigationView.setVisibility(BottomNavigationView.INVISIBLE);
        }
    }

    private void initLayout() {
        navigationViewAdmin = findViewById(R.id.navigationAdmin);
        navigationViewCashier = findViewById(R.id.navigationCashier);
        navigationViewWaiter = findViewById(R.id.navigationWaiter);
        navigationViewCostumers = findViewById(R.id.navigationCostumer);
        // navigationViewOwner = findViewById(R.id.navigationOwner);

        navigationViewAdmin.setOnNavigationItemSelectedListener(this);
        navigationViewCashier.setOnNavigationItemSelectedListener(this);
        navigationViewWaiter.setOnNavigationItemSelectedListener(this);
        navigationViewCostumers.setOnNavigationItemSelectedListener(this);
        //  navigationViewOwner.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment);
        fragmentTransaction.commit();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.admin_navigation_menu:
                fragment = new AdminMenuFragment();
                break;
            case R.id.admin_navigation_cart:
                fragment = new AdminCartFragment();
                break;
            case R.id.admin_navigation_report:
                fragment = new AdminReportFragment();
                break;
            case R.id.admin_navigation_register:
                fragment = new AdminRegisterFragment();
                break;
            case R.id.waiter_navigation_menu:
                fragment = new WaiterMenuFragment();
                break;
            case R.id.waiter_navigation_cart:
                fragment = new AdminCartFragment();
                break;
            case R.id.waiter_navigation_report:
                fragment = new AdminReportFragment();
                break;
            case R.id.waiter_navigation_register:
                fragment = new AdminRegisterFragment();
                break;
            case R.id.cashier_navigation_cart:
                fragment = new CashierOrderListFragment();
                break;
            case R.id.cashier_navigation_report:
                fragment = new AdminReportFragment();
                break;
            case R.id.cashier_navigation_register:
                fragment = new AdminRegisterFragment();
                break;
            case R.id.costumer_navigation_menu:
                fragment = new WaiterMenuFragment();
                break;
            case R.id.costumer_navigation_ordered:
                fragment = new CostumerOrderedMenuFragment();
                break;
            default:
                return false;
        }
        return loadFragment(fragment);
    }
}
