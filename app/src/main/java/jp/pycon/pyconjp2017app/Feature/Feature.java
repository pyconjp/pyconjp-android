package jp.pycon.pyconjp2017app.Feature;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import jp.pycon.pyconjp2017app.Feature.About.AboutFragment;
import jp.pycon.pyconjp2017app.Feature.Access.AccessFragment;
import jp.pycon.pyconjp2017app.Feature.Events.EventsFragment;
import jp.pycon.pyconjp2017app.Feature.FloorMap.FloorMapFragment;
import jp.pycon.pyconjp2017app.Feature.Keynote.KeynoteFragment;
import jp.pycon.pyconjp2017app.Feature.Posters.PostersFragment;
import jp.pycon.pyconjp2017app.Feature.Talks.List.TalksFragment;
import jp.pycon.pyconjp2017app.Feature.Talks.List.BookmarkFragment;
import jp.pycon.pyconjp2017app.R;

/**
 * Created by rhoboro on 5/1/16.
 */
public enum Feature {
    KEYNOTE(R.id.nav_keynote, R.string.nav_keynote, true, KeynoteFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return KeynoteFragment.newInstance();
        }
    },
    TALKS(R.id.nav_talks, R.string.nav_talks, false, TalksFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return TalksFragment.newInstance();
        }
    },
    BOOKMARK(R.id.nav_bookmark, R.string.nav_bookmark, false, BookmarkFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return BookmarkFragment.newInstance();
        }
    },
    POSTERS(R.id.nav_posters, R.string.nav_posters, true, PostersFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return PostersFragment.newInstance();
        }
    },
    EVENTS(R.id.nav_event, R.string.nav_events, true, EventsFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return EventsFragment.newInstance();
        }
    },
    ACCESS(R.id.nav_access, R.string.nav_access, true, AccessFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return AccessFragment.newInstance();
        }
    },
    FLOOR_MAP(R.id.nav_floor_map, R.string.nav_floor_map, true, FloorMapFragment.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return FloorMapFragment.newInstance();
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

    Feature(int menuId, int titleResId, boolean toggleToolbar, String pageName) {
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

    public static Feature forMenuId(MenuItem item) {
        int id = item.getItemId();
        return forMenuId(id);
    }

    public static Feature forMenuId(int id) {
        for (Feature page : values()) {
            if (page.menuId == id) {
                return page;
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
