package id.co.kurindo.apartment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import id.co.kurindo.apartment.base.BaseTabFragment;

/**
 * Created by dwim on 1/7/2017.
 */

public class MainTabFragment extends BaseTabFragment {
    @Override
    public int[] getIcons() {
        return new int[0];
    }

    @Override
    public boolean isShowIcon() {
        return false;
    }

    @Override
    public FragmentPagerAdapter getFragmentPagerAdapter() {
        return new MyAdapter(getChildFragmentManager());
    }


    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new HomeFragment();
                case 1 : return new BillingFragment();
                case 2 : return new NewsFragment();
                case 3 : return new WarjokFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Home";
                case 1 :
                    return "Billing";
                case 2 :
                    return "News";
                case 3 :
                    return "Warjok";
            }
            return null;
        }
    }
}
