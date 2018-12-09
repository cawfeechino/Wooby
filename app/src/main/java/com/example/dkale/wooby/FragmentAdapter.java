package com.example.dkale.wooby;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager manager){
        super(manager);
    }
    public int getCount(){
        return 4;
    }
    public Fragment getItem(int position){
        Fragment page = null;
        switch(position){
            case 0: page = Suggestion.newInstance("One","Two"); break;
            case 1: page = ChatMessageFragment.newInstance("One","Two"); break;
            case 2: page = HistoryFragment.newInstance(1); break;
            case 3: page = ToWatchFragment.newInstance(2); break;
            default: page = ChatMessageFragment.newInstance("One","Two"); break;
        }
        return page;
    }

    public CharSequence getPageTitle(int position) {
        CharSequence result = "";
        switch(position){
            case 0: result = "Suggestion"; break;
            case 1: result = "Chat"; break;
            case 2: result = "Watched"; break;
            case 3: result = "To-Watch"; break;
            default: result = "Chat"; break;
        }
        return result;
    }
}
