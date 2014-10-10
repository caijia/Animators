package com.cs.animators.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

/**
 * TODO:
 * Created by cai.jia on 2014/10/8.
 */
public class RankListAsGridItem implements AsymmetricItem {

    private int rowSpan ;

    private int columnSpan ;

    private HotItem rankItem ;

    public RankListAsGridItem(Parcel source) {
        columnSpan = source.readInt();
        rowSpan = source.readInt();
    }

    public RankListAsGridItem() {

    }

    public RankListAsGridItem(HotItem rankItem , int rowSpan , int columnSpan) {
        this.rankItem = rankItem ;
        this.rowSpan = rowSpan ;
        this.columnSpan = columnSpan ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(columnSpan);
        dest.writeInt(rowSpan);
    }

    @Override
    public int getColumnSpan() {
        return columnSpan;
    }

    @Override
    public int getRowSpan() {
        return rowSpan;
    }

    public static final Parcelable.Creator<RankListAsGridItem> CREATOR = new Creator<RankListAsGridItem>() {

        @Override
        public RankListAsGridItem[] newArray(int size) {
            return new RankListAsGridItem[size];
        }

        @Override
        public RankListAsGridItem createFromParcel(Parcel source) {
            return new RankListAsGridItem(source);
        }
    };

    public HotItem getRankItem() {
        return rankItem;
    }

    public void setRankItem(HotItem rankItem) {
        this.rankItem = rankItem;
    }
}
