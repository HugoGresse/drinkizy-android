package fr.drinkizy.pageradapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import fr.drinkizy.BarAvisFragment;
import fr.drinkizy.BarBoissonsFragment;
import fr.drinkizy.BarInfoFragment;
import fr.drinkizy.BarPlanFragment;

public class BarTabsPagerAdapter extends FragmentPagerAdapter {

	public BarTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
	        case 0:
	        	return new BarInfoFragment();
	        case 1:
	            return new BarBoissonsFragment();
	        case 2:
	            return new BarAvisFragment();
	        case 3:
	            return new BarPlanFragment();
	        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }

}
