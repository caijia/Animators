package com.cs.animators.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.fastjson.annotation.JSONField;

public class HotItem implements Parcelable {

	@JSONField(name="video_id")
	private String videoId;
	private String name;
	private String intro;
	private String area;
	private String cover;
	private String character;
	private String category;
	private String score;
	private String id ;
	
	private String update;
	
	@JSONField(name="cur_num")
	private String curNum;

	@JSONField(name="total_num")
	private String totalNum;

	@JSONField(name="update_time")
	private String updateYear ;
	
	
	public HotItem() {
	}
	
	

	public HotItem(String videoId, String name, String cover, String category,
                   String score, String curNum, String totalNum,
                   String updateYear) {
		this.videoId = videoId;
		this.name = name;
		this.cover = cover;
		this.category = category;
		this.score = score;
		this.curNum = curNum;
		this.totalNum = totalNum;
		this.updateYear = updateYear;
	}



	public String getUpdateYear() {
		return updateYear;
	}

	public void setUpdateYear(String updateYear) {
		this.updateYear = updateYear;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getVideoId() {
		return videoId;
	}

	
	public String getUpdate() {
		return update;
	}



	public void setUpdate(String update) {
		this.update = update;
	}



	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCurNum() {
		return curNum;
	}

	public void setCurNum(String curNum) {
		this.curNum = curNum;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoId);
        dest.writeString(this.name);
        dest.writeString(this.intro);
        dest.writeString(this.area);
        dest.writeString(this.cover);
        dest.writeString(this.character);
        dest.writeString(this.category);
        dest.writeString(this.score);
        dest.writeString(this.id);
        dest.writeString(this.update);
        dest.writeString(this.curNum);
        dest.writeString(this.totalNum);
        dest.writeString(this.updateYear);
    }

    private HotItem(Parcel in) {
        this.videoId = in.readString();
        this.name = in.readString();
        this.intro = in.readString();
        this.area = in.readString();
        this.cover = in.readString();
        this.character = in.readString();
        this.category = in.readString();
        this.score = in.readString();
        this.id = in.readString();
        this.update = in.readString();
        this.curNum = in.readString();
        this.totalNum = in.readString();
        this.updateYear = in.readString();
    }

    public static final Parcelable.Creator<HotItem> CREATOR = new Parcelable.Creator<HotItem>() {
        public HotItem createFromParcel(Parcel source) {
            return new HotItem(source);
        }

        public HotItem[] newArray(int size) {
            return new HotItem[size];
        }
    };
}
