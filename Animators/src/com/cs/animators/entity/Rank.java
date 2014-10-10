package com.cs.animators.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO:
 * Created by cai.jia on 2014/10/7.
 */
public class Rank implements Parcelable {

    private String id ;
    private String title ;
    private String count ;
    private List<HotItem> list ;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<HotItem> getList() {
        return list;
    }

    public void setList(List<HotItem> list) {
        this.list = list;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.count);
        dest.writeTypedList(list);
    }

    public Rank() {
    }

    private Rank(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.count = in.readString();
        list = new ArrayList<HotItem>();
        in.readTypedList(list, HotItem.CREATOR);
    }

    public static final Parcelable.Creator<Rank> CREATOR = new Parcelable.Creator<Rank>() {
        public Rank createFromParcel(Parcel source) {
            return new Rank(source);
        }

        public Rank[] newArray(int size) {
            return new Rank[size];
        }
    };
}
