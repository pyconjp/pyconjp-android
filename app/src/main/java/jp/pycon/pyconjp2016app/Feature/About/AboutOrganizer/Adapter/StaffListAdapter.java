package jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jp.pycon.pyconjp2016app.Model.PyConJP.StaffEntity;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.databinding.CellStaffListBinding;

/**
 * Created by wj on 16/8/30.
 */
public class StaffListAdapter extends RecyclerView.Adapter<StaffListAdapter.ViewHolder> {

  private Activity activity;
  private List<StaffEntity> list;

  public StaffListAdapter(Activity activity, List<StaffEntity> list) {
    this.activity = activity;
    this.list = list;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_staff_list, parent, false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, final int position) {
    holder.binding.nameTextView.setText(list.get(position).getName());
    holder.binding.teamIDTextView.setText(getTeamName(list.get(position).getTeamID()));
    holder.binding.titleTextView.setText(list.get(position).getTitle());
    if (!list.get(position).getFacebook().equals("")) {
      holder.binding.facebookButtonFalse.setVisibility(View.GONE);
      holder.binding.facebookButtonTrue.setVisibility(View.VISIBLE);
      holder.binding.facebookButtonTrue.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
          customTabsIntent.launchUrl(activity, Uri.parse(list.get(position).getFacebook()));
        }
      });
    } else {
      holder.binding.facebookButtonFalse.setVisibility(View.VISIBLE);
      holder.binding.facebookButtonTrue.setVisibility(View.GONE);
    }

    if (!list.get(position).getTwitter().isEmpty()) {
      holder.binding.twitterButtonFalse.setVisibility(View.GONE);
      holder.binding.twitterButtonTrue.setVisibility(View.VISIBLE);
      holder.binding.twitterButtonTrue.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
          customTabsIntent.launchUrl(activity, Uri.parse(list.get(position).getTwitter()));
        }
      });
    } else {
      holder.binding.twitterButtonFalse.setVisibility(View.VISIBLE);
      holder.binding.twitterButtonTrue.setVisibility(View.GONE);
    }
  }

  @Override public int getItemCount() {
    return list.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    private CellStaffListBinding binding;

    public ViewHolder(View itemView) {
      super(itemView);
      binding = DataBindingUtil.bind(itemView);
    }
  }

  public String getTeamName(String s) {
    switch (s) {
      case "0":
        return "理事";
      case "1":
        return "事務局";
      case "2":
        return "会場";
      case "3":
        return "プログラム";
      case "4":
        return "メディア";
    }
    return "";
  }
}
