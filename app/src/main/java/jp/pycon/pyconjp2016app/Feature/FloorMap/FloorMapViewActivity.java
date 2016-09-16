package jp.pycon.pyconjp2016app.Feature.FloorMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;

import jp.pycon.pyconjp2016app.BaseAppCompatActivity;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.Util.FirebaseUtil;
import jp.pycon.pyconjp2016app.Util.GAUtil;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by rhoboro on 7/30/16.
 */
public class FloorMapViewActivity extends BaseAppCompatActivity {

    private static final String BUNDLE_KEY_FLOOR_TITLE_ID = "bundle_key_floor_title_id";
    private static final String BUNDLE_KEY_FLOOR_IMAGE_ID = "bundle_key_floor_image_id";
    ImageView mImageView;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_map_view);

        final int titleResId = getIntent().getIntExtra(BUNDLE_KEY_FLOOR_TITLE_ID, 0);
        final int imageResId = getIntent().getIntExtra(BUNDLE_KEY_FLOOR_IMAGE_ID, 0);
        ActionBar bar = getSupportActionBar();
        bar.setTitle(titleResId);
        FirebaseUtil.sendFloorMap(this, getString(titleResId));
        GAUtil.sendFloorMap(this, getString(titleResId));
        mImageView = (ImageView)findViewById(R.id.image_view);
        mImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), imageResId, null));
        mAttacher = new PhotoViewAttacher(mImageView);
    }

    public static void start(Context context, int titleResId, int imageResId) {
        final Intent intent = new Intent(context, FloorMapViewActivity.class);
        intent.putExtra(BUNDLE_KEY_FLOOR_TITLE_ID, titleResId);
        intent.putExtra(BUNDLE_KEY_FLOOR_IMAGE_ID, imageResId);
        context.startActivity(intent);
    }

}
