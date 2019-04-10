package co.ambystic.restorder.view.fragment.admin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.ambystic.restorder.R;
import co.ambystic.restorder.view.adapter.tab.TabMenuAdapter;
import co.ambystic.restorder.view.fragment.admin.tab.menu.AdminBeverageFragment;
import co.ambystic.restorder.view.fragment.admin.tab.menu.AdminFoodFragment;

public class AdminMenuFragment extends Fragment {

    private TabLayout tabLayoutMenuAdmin;
    private ViewPager vPagerMenuAdmin;

    private TabMenuAdapter tabAdminMenuAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.admin_fragment_menu, container, false);

        initLayout(view);

        tabAdminMenuAdapter = new TabMenuAdapter(getChildFragmentManager());
        tabAdminMenuAdapter.addFragment(new AdminFoodFragment(), "Makanan");
        tabAdminMenuAdapter.addFragment(new AdminBeverageFragment(), "Minuman");

        vPagerMenuAdmin.setAdapter(tabAdminMenuAdapter);
        tabLayoutMenuAdmin.setupWithViewPager(vPagerMenuAdmin);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0f);

        return view;
    }

    private void initLayout(View view) {
        tabLayoutMenuAdmin = view.findViewById(R.id.tabLayoutMenuAdmin);
        vPagerMenuAdmin = view.findViewById(R.id.vPagerMenuAdmin);
    }

}
