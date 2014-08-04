package com.cs.animators.entity;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

public class Group implements Parcelable{
	
	private String group ;
	private List<GroupItem> list ;

	public Group(Parcel source) {
		group = source.readString();
		source.readList(list, GroupItem.class.getClassLoader());
	}
	
	//
	public Group() {
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(group);
		dest.writeList(list);
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<GroupItem> getList() {
		return list;
	}

	public void setList(List<GroupItem> list) {
		this.list = list;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<Group> CREATOR = new Creator<Group>() {
		
		@Override
		public Group[] newArray(int size) {
			return new Group[size];
		}
		
		@Override
		public Group createFromParcel(Parcel source) {
			return new Group(source);
		}
	};
	
}
