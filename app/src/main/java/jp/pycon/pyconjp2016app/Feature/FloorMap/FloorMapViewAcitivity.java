package jp.pycon.pyconjp2016app.Feature.FloorMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ImageView;

import jp.pycon.pyconjp2016app.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by rhoboro on 7/30/16.
 */
public class FloorMapViewAcitivity extends Activity {

    ImageView mImageView;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_map_view);

        mImageView = (ImageView)findViewById(R.id.image_view);
        mImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.floor_map_01, null));
        mAttacher = new PhotoViewAttacher(mImageView);
    }

    public static void start(Context context) {
        final Intent intent = new Intent(context, FloorMapViewAcitivity.class);
        context.startActivity(intent);
    }
}
