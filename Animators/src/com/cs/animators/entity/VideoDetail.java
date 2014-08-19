package com.cs.animators.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

/**
 * Created by Administrator on 2014/8/19.
 */
public class VideoDetail implements Parcelable {

    @JSONField(name = "video_id")
    private String videoId;

    private String name;

    private String intro;

    private String area;

    private String cover;

    private String character ;

    @JSONField(name = "is_over")
    private String isOver;

    @JSONField(name = "cur_num")
    private String curNum;

    @JSONField(name = "total_num")
    private String totalNum;

    private String category;

    private String director;

    private String score;

    @JSONField(name = "update_time")
    private String updateTime;

    @JSONField(name = "publish_time")
    private String publishTime;

    private String error;

    private List<VideoDetailSeries> episode;

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
        dest.writeString(this.isOver);
        dest.writeString(this.curNum);
        dest.writeString(this.totalNum);
        dest.writeString(this.category);
        dest.writeString(this.director);
        dest.writeString(this.score);
        dest.writeString(this.updateTime);
        dest.writeString(this.publishTime);
        dest.writeString(this.error);
        dest.writeTypedList(episode);
    }

    public VideoDetail() {
    }

    private VideoDetail(Parcel in) {
        this.videoId = in.readString();
        this.name = in.readString();
        this.intro = in.readString();
        this.area = in.readString();
        this.cover = in.readString();
        this.character = in.readString();
        this.isOver = in.readString();
        this.curNum = in.readString();
        this.totalNum = in.readString();
        this.category = in.readString();
        this.director = in.readString();
        this.score = in.readString();
        this.updateTime = in.readString();
        this.publishTime = in.readString();
        this.error = in.readString();
//        in.readTypedList(episode, VideoDetailSeries.CREATOR);
        episode = in.createTypedArrayList(VideoDetailSeries.CREATOR);
    }

    public static final Parcelable.Creator<VideoDetail> CREATOR = new Parcelable.Creator<VideoDetail>() {
        public VideoDetail createFromParcel(Parcel source) {
            return new VideoDetail(source);
        }

        public VideoDetail[] newArray(int size) {
            return new VideoDetail[size];
        }
    };

	public String getVideoId() {
		return videoId;
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

	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<VideoDetailSeries> getEpisode() {
		return episode;
	}

	public void setEpisode(List<VideoDetailSeries> episode) {
		this.episode = episode;
	}
    
}
