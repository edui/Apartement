package id.co.kurindo.apartment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import id.co.kurindo.apartment.base.BaseTabFragment;

/**
 * Created by dwim on 1/7/2017.
 */

public class HistoryTabFragment extends BaseTabFragment {
    @Override
    public int[] getIcons() {
        return new int[0];
    }

    @Override
    public boolean isShowIcon() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.top_tab_layout;
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
                case 0 : return new HistoryInProgressFragment();
                case 1 : return new HistoryCompletedFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Inprogress";
                case 1 :
                    return "Completed";
            }
            return null;
        }
    }
}
