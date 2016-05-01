package jp.pycon.pyconjp2016app.Feature;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import jp.pycon.pyconjp2016app.Feature.About.AboutFragment;
import jp.pycon.pyconjp2016app.Feature.Access.AccessFragment;
import jp.pycon.pyconjp2016app.Feature.Talks.MyTalksFragment;
import jp.pycon.pyconjp2016app.Feature.Talks.TalksFragment;
import jp.pycon.pyconjp2016app.R;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by rhoboro on 5/1/16.
 */
@RunWith(Enclosed.class)
public class FeatureTest {

    @RunWith(JUnit4.class)
    public static class forNameTest {
        @Test
        public void Talks画面のgetMenuID() throws Exception {
            Feature feature = Feature.forName(new TalksFragment());
            int expected = R.id.nav_talks;
            int actual = feature.getMenuId();
            assertThat(expected, is(actual));
        }

        @Test
        public void Talks画面のgetTitleResId() throws Exception {
            Feature feature = Feature.forName(new TalksFragment());
            int expected = R.string.nav_talks;
            int actual = feature.getTitleResId();
            assertThat(expected, is(actual));
        }

        @Test
        public void MyTalks画面のgetMenuID() throws Exception {
            Feature feature = Feature.forName(new MyTalksFragment());
            int expected = R.id.nav_my_talks;
            int actual = feature.getMenuId();
            assertThat(expected, is(actual));
        }

        @Test
        public void MyTalks画面のgetTitleResId() throws Exception {
            Feature feature = Feature.forName(new MyTalksFragment());
            int expected = R.string.nav_my_talks;
            int actual = feature.getTitleResId();
            assertThat(expected, is(actual));
        }

        @Test
        public void Access画面のgetMenuID() throws Exception {
            Feature feature = Feature.forName(new AccessFragment());
            int expected = R.id.nav_access;
            int actual = feature.getMenuId();
            assertThat(expected, is(actual));
        }

        @Test
        public void Access画面のgetTitleResId() throws Exception {
            Feature feature = Feature.forName(new AccessFragment());
            int expected = R.string.nav_access;
            int actual = feature.getTitleResId();
            assertThat(expected, is(actual));
        }

        @Test
        public void About画面のgetMenuID() throws Exception {
            Feature feature = Feature.forName(new AboutFragment());
            int expected = R.id.nav_about;
            int actual = feature.getMenuId();
            assertThat(expected, is(actual));
        }

        @Test
        public void About画面のgetTitleResId() throws Exception {
            Feature feature = Feature.forName(new AboutFragment());
            int expected = R.string.nav_about;
            int actual = feature.getTitleResId();
            assertThat(expected, is(actual));
        }
    }
}
