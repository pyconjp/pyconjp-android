package jp.pycon.pyconjp2016app.API.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rhoboro on 7/5/16.
 */
public class PyConJPScheduleEntity {

    @SerializedName("data0")
    public DataEntity data0;
    @SerializedName("data1")
    public DataEntity data1;
    @SerializedName("data2")
    public DataEntity data2;
    @SerializedName("data3")
    public DataEntity data3;
}
