package jp.pycon.pyconjp2016app.Model.PyConJP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wj on 16/8/31.
 */

public class StaffListEntity {

    @SerializedName("staffList")
    @Expose
    private List<StaffEntity> staffList = new ArrayList<StaffEntity>();

    /**
     *
     * @return
     * The staffList
     */
    public List<StaffEntity> getStaffList() {
        return staffList;
    }

    /**
     *
     * @param staffList
     * The staffList
     */
    public void setStaffList(List<StaffEntity> staffList) {
        this.staffList = staffList;
    }

}
