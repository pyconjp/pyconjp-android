package jp.pycon.pyconjp2016app.Feature;

import android.support.v4.app.Fragment;

import jp.pycon.pyconjp2016app.Feature.About.AboutFragment;
import jp.pycon.pyconjp2016app.Feature.Access.AccessFragment;
import jp.pycon.pyconjp2016app.Feature.Talks.MyTalksFragment;
import jp.pycon.pyconjp2016app.Feature.Talks.TalksFragment;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 5/1/16.
 */
public enum Feature {
    TALKS(R.id.nav_talks, R.string.nav_talks, TalksFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return TalksFragment.newInstance();
        }
    },
    MY_TALKS(R.id.nav_my_talks, R.string.nav_my_talks, MyTalksFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return MyTalksFragment.newInstance();
        }
    },
    ACCESS(R.id.nav_access, R.string.nav_access, AccessFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return AccessFragment.newInstance();
        }
    },
    ABOUT(R.id.nav_about, R.string.nav_about, AboutFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return AboutFragment.newInstance();
        }
    };

    private final int menuId;
    private final int titleResId;
    private final String pageName;

    Feature(int menuId, int titleResId, String pageName){
        this.menuId = menuId;
        this.titleResId = titleResId;
        this.pageName = pageName;
    }

    public static Feature forName(Fragment fragment) {
        String name = fragment.getClass().getSimpleName();
        for (Feature feature : values()) {
            if (feature.pageName.equals(name)) {
                return feature;
            }
        }
        throw new AssertionError("no menu enum found for the id. you forgot to implement?");
    }

    public int getMenuId() {
        return menuId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public String getPageName() {
        return pageName;
    }

    public abstract Fragment createFragment();
}
