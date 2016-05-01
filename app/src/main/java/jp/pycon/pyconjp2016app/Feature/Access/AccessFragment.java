package jp.pycon.pyconjp2016app.Feature.Access;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 4/22/16.
 */
public class AccessFragment extends Fragment implements OnMapReadyCallback {

    final static private double VENUE_LATITUDE = 35.706069;
    final static private double VENUE_LONGITUDE = 139.70681;
    final static private int DEFAULT_ZOOM_LEVEL = 15;
    final static private String htmlStr = "<a href=\"https://www.waseda.jp/top/access/nishiwaseda-campus\">早稲田大学西早稲田キャンパス</a>";

    public AccessFragment() {
        super();
    }

    public static AccessFragment newInstance() {
        AccessFragment fragment = new AccessFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_access, container, false);

        // リンク
        TextView link = (TextView)v.findViewById(R.id.access_link);
        link.setText(Html.fromHtml(htmlStr));
        MovementMethod method = LinkMovementMethod.getInstance();
        link.setMovementMethod(method);

        // GoogleMap を表示
        SupportMapFragment fragment = new SupportMapFragment();
        final FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.map, fragment, fragment.getClass().getSimpleName());
        ft.commit();
        fragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng waseda = new LatLng(VENUE_LATITUDE, VENUE_LONGITUDE);
        googleMap.addMarker(new MarkerOptions().position(waseda).title(getString(R.string.campus_short_name)));
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(waseda, DEFAULT_ZOOM_LEVEL, 0, 0)));
    }
}
