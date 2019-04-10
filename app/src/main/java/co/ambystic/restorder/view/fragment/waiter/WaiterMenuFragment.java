package co.ambystic.restorder.view.fragment.waiter;


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
import co.ambystic.restorder.view.fragment.waiter.tab.menu.WaiterBeverageFragment;
import co.ambystic.restorder.view.fragment.waiter.tab.menu.WaiterFoodFragment;

public class WaiterMenuFragment extends Fragment {

    private TabLayout tabLayoutMenuWaiter;
    private ViewPager vPagerMenuWaiter;

    private TabMenuAdapter tabWaiterMenuAdapter;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_waiter_menu, container, false);

        initLayout(view);

        tabWaiterMenuAdapter = new TabMenuAdapter(getChildFragmentManager());
        tabWaiterMenuAdapter.addFragment(new WaiterFoodFragment(), "Makanan");
        tabWaiterMenuAdapter.addFragment(new WaiterBeverageFragment(), "Minuman");

        vPagerMenuWaiter.setAdapter(tabWaiterMenuAdapter);
        tabLayoutMenuWaiter.setupWithViewPager(vPagerMenuWaiter);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0f);
        
        return view;
    }

    private void initLayout(View view) {
        tabLayoutMenuWaiter = view.findViewById(R.id.tabLayoutMenuWaiter);
        vPagerMenuWaiter = view.findViewById(R.id.vPagerMenuWaiter);
    }

}
