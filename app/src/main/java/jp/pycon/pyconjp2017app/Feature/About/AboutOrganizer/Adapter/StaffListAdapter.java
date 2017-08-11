package jp.pycon.pyconjp2017app.Feature.About.AboutOrganizer.Adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jp.pycon.pyconjp2017app.Model.GHPages.StaffEntity;
import jp.pycon.pyconjp2017app.R;
import jp.pycon.pyconjp2017app.databinding.CellStaffListBinding;

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
    final StaffEntity staff = list.get(position);
    holder.binding.nameTextView.setText(staff.getName());
    holder.binding.teamIDTextView.setText((staff.getTeam()));
    holder.binding.titleTextView.setText(staff.getTitle());
    if (!staff.getFacebook().equals("")) {
      holder.binding.facebookButtonFalse.setVisibility(View.GONE);
      holder.binding.facebookButtonTrue.setVisibility(View.VISIBLE);
      holder.binding.facebookButtonTrue.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
          customTabsIntent.launchUrl(activity, Uri.parse(staff.getFacebook()));
        }
      });
    } else {
      holder.binding.facebookButtonFalse.setVisibility(View.VISIBLE);
      holder.binding.facebookButtonTrue.setVisibility(View.GONE);
    }

    if (!staff.getTwitter().isEmpty()) {
      holder.binding.twitterButtonFalse.setVisibility(View.GONE);
      holder.binding.twitterButtonTrue.setVisibility(View.VISIBLE);
      holder.binding.twitterButtonTrue.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
          customTabsIntent.launchUrl(activity, Uri.parse(staff.getTwitter()));
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

}
