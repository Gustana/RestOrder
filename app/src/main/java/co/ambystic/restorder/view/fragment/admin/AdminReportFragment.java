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
import co.ambystic.restorder.view.fragment.admin.tab.report.BeverageReportFragment;
import co.ambystic.restorder.view.fragment.admin.tab.report.FoodReportFragment;

public class AdminReportFragment extends Fragment {

    private TabLayout tabLayoutReportAdmin;
    private ViewPager vPagerReportAdmin;

    private TabMenuAdapter tabMenuAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment_report, container, false);

        initLayout(view);

        tabMenuAdapter = new TabMenuAdapter(getChildFragmentManager());
        tabMenuAdapter.addFragment(new FoodReportFragment(), "Makanan");
        tabMenuAdapter.addFragment(new BeverageReportFragment(), "Minuman");

        vPagerReportAdmin.setAdapter(tabMenuAdapter);
        tabLayoutReportAdmin.setupWithViewPager(vPagerReportAdmin);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setElevation(0f);

        return view;
    }

    private void initLayout(View view) {
        tabLayoutReportAdmin = view.findViewById(R.id.tabLayoutReportAdmin);
        vPagerReportAdmin = view.findViewById(R.id.vPagerReportAdmin);
    }
}
