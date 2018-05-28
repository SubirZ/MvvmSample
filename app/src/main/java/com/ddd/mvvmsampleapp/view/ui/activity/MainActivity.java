package com.ddd.mvvmsampleapp.view.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ddd.mvvmsampleapp.R;
import com.ddd.mvvmsampleapp.databinding.ActivityMainBinding;
import com.ddd.mvvmsampleapp.view.ui.fragment.MoviesFragment;
import com.ddd.mvvmsampleapp.view.ui.fragment.UpcomimgMoviesFragment;

/**
 * Created by S.C. on 11/05/18.
 */

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initView();
    }

    private void initView() {
        replaceFragment(R.id.flContainer, new MoviesFragment(), false);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.in_theaters));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.upcomimg));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getSupportFragmentManager().popBackStackImmediate();
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(R.id.flContainer, new MoviesFragment(), false);
                        break;

                    case 1:
                        replaceFragment(R.id.flContainer, new UpcomimgMoviesFragment(), false);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Adds the Fragment into layout container
     *
     * @param fragmentContainerResourceId Resource id of the layout in which Fragment will be added
     * @param currentFragment             Current loaded Fragment to be hide
     * @param nextFragment                New Fragment to be loaded into fragmentContainerResourceId
     * @param commitAllowingStateLoss     true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     * @throws IllegalStateException Exception if Fragment transaction is invalid
     */
    public boolean addFragment(final int fragmentContainerResourceId, final Fragment currentFragment, final Fragment nextFragment, final boolean commitAllowingStateLoss) throws IllegalStateException {
        if (currentFragment == null || nextFragment == null) {
            return false;
        }
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainerResourceId, nextFragment, nextFragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(nextFragment.getClass().getSimpleName());

        final Fragment parentFragment = currentFragment.getParentFragment();
        fragmentTransaction.hide(parentFragment == null ? currentFragment : parentFragment);

        if (!commitAllowingStateLoss) {
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.commitAllowingStateLoss();
        }

        return true;
    }

    /**
     * Replaces the Fragment into layout container
     *
     * @param fragmentContainerResourceId Resource id of the layout in which Fragment will be added
     * @param nextFragment                New Fragment to be loaded into fragmentContainerResourceId
     * @param commitAllowingStateLoss     true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     * @throws IllegalStateException Exception if Fragment transaction is invalid
     */
    public boolean replaceFragment(final int fragmentContainerResourceId, final Fragment nextFragment, final boolean commitAllowingStateLoss) throws IllegalStateException {
        if (nextFragment == null) {
            return false;
        }
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(fragmentContainerResourceId, nextFragment, nextFragment.getClass().getSimpleName());

        if (!commitAllowingStateLoss) {
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.commitAllowingStateLoss();
        }

        return true;
    }
}