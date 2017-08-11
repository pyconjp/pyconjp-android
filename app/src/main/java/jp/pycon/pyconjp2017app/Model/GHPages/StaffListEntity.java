package jp.pycon.pyconjp2017app.Model.GHPages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wj on 16/8/31.
 */

public class StaffListEntity {

    @SerializedName("staffList")
    @Expose
    public List<StaffEntity> staffList;

}
