package mobile.proj.spoilers.model;

import com.google.gson.annotations.SerializedName;

import mobile.proj.spoilers.service.BaseResults;

import java.util.List;

public class TVResults extends BaseResults{
    @SerializedName("results")
    private List<TV> mTVs;

    public List<TV> getTVs() {
        return mTVs;
    }

    public void setTVs(List<TV> TVs) {
        mTVs = TVs;
    }
}
