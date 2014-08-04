package com.cs.animators.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

public class VideoDetailSeries implements Parcelable{

	private String id;
	private String name;
	@JSONField(name = "from_site")
	private String fromSite;
	private String vid;

	public VideoDetailSeries(Parcel source) {
		id = source.readString();
		name = source.readString();
		fromSite = source.readString();
		vid = source.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(fromSite);
		dest.writeString(vid);
	}
	
	public VideoDetailSeries() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFromSite() {
		return fromSite;
	}

	public void setFromSite(String fromSite) {
		this.fromSite = fromSite;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	@Override
	public int describeContents() {
		
		return 0;
	}

	
	public static final Parcelable.Creator<VideoDetailSeries> CREATOR = new Creator<VideoDetailSeries>() {
		
		@Override
		public VideoDetailSeries[] newArray(int size) {
			return new VideoDetailSeries[size];
		}
		
		@Override
		public VideoDetailSeries createFromParcel(Parcel source) {
			return new VideoDetailSeries(source);
		}
	};

}
