package ines.santos.proyecto;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class PagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "Memory", "Ranking" };
    private Context context;
    Fragment tab = null;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                tab = new FMemory();
                break;
            case 1:
                tab = new FRanking();
                break;
        }
        return tab;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
