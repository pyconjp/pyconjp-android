package jp.pycon.pyconjp2016app.Feature;

import android.support.v4.app.Fragment;

import jp.pycon.pyconjp2016app.Feature.About.AboutFragment;
import jp.pycon.pyconjp2016app.Feature.Access.AccessFragment;
import jp.pycon.pyconjp2016app.Feature.Settings.SettingsFragment;
import jp.pycon.pyconjp2016app.Feature.Talks.List.BookmarkFragment;
import jp.pycon.pyconjp2016app.Feature.Talks.List.TalksFragment;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 5/1/16.
 */
public enum Feature {
    TALKS(R.id.nav_talks, R.string.nav_talks, false, TalksFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return TalksFragment.newInstance();
        }
    },
    MY_TALKS(R.id.nav_bookmark, R.string.nav_bookmark, false, BookmarkFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return BookmarkFragment.newInstance();
        }
    },
    ACCESS(R.id.nav_access, R.string.nav_access, true, AccessFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return AccessFragment.newInstance();
        }
    },
    SETTINGS(R.id.nav_settings, R.string.nav_settings, true, SettingsFragment.class.getSimpleName()){
        @Override
        public Fragment createFragment() {
            return SettingsFragment.newInstance();
        }
    },
    ABOUT(R.id.nav_about, R.string.nav_about, true, AboutFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return AboutFragment.newInstance();
        }
    };

    private final int menuId;
    private final int titleResId;
    private final boolean toggleToolbar;
    private final String pageName;

    Feature(int menuId, int titleResId, boolean toggleToolbar, String pageName){
        this.menuId = menuId;
        this.titleResId = titleResId;
        this.toggleToolbar = toggleToolbar;
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

    public boolean shouldToggleToolbar() {
        return toggleToolbar;
    }
    
    public int getTitleResId() {
        return titleResId;
    }

    public String getPageName() {
        return pageName;
    }

    public abstract Fragment createFragment();
}
