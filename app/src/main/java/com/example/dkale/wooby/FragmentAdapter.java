package com.example.dkale.wooby;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    HistoryFragment mHistory;
    ToWatchFragment mToWatch;

    public FragmentAdapter(FragmentManager manager){
        super(manager);
        mHistory = HistoryFragment.newInstance(1);
        mToWatch = ToWatchFragment.newInstance(1);
    }
    public int getCount(){
        return 3;
    }
    public Fragment getItem(int position){
        Fragment page = null;
        switch(position){
            case 0: page = Suggestion.newInstance("One","Two"); break;
            case 1: page = mHistory; break;
            case 2: page = mToWatch; break;
            default: page = Suggestion.newInstance("One","Two"); break;
        }
        return page;
    }

    public CharSequence getPageTitle(int position) {
        CharSequence result = "";
        switch(position){
            case 0: result = "Suggestion"; break;
            case 1: result = "Watched"; break;
            case 2: result = "To-Watch"; break;
            default: result = "Suggestion"; break;
        }
        return result;
    }
}