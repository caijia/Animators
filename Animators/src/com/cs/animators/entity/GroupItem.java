package com.cs.animators.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class GroupItem implements Parcelable{

	private String id;
	private String name;
	private String pic;

	public GroupItem(Parcel source) {
		id = source.readString();
		name = source.readString();
		pic = source.readString();
	}
	
	public GroupItem() {
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(pic);
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

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<GroupItem> CREATOR = new Creator<GroupItem>() {
			
		@Override
		public GroupItem[] newArray(int size) {
			return new GroupItem[size];
		}
		
		@Override
		public GroupItem createFromParcel(Parcel source) {
			return new GroupItem(source);
		}
	};

}
