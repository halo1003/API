package com.api_ctv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "music")
public class Music {
	
	@Id
	@Column(name = "idmusic")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long mid;
	
	@Column(name = "trackid")
	String trackid;
	
	@Column(name = "musicid")
	String musicid;
	
	@Column(name = "arttic")
	String arttic;
	
	@Column(name = "name")
	String name;

	public Music() {
		super();
	}

	public Music(Long mid, String trackid, String musicid, String arttic, String name) {
		super();
		this.mid = mid;
		this.trackid = trackid;
		this.musicid = musicid;
		this.arttic = arttic;
		this.name = name;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getTrackid() {
		return trackid;
	}

	public void setTrackid(String trackid) {
		this.trackid = trackid;
	}

	public String getMusicid() {
		return musicid;
	}

	public void setMusicid(String musicid) {
		this.musicid = musicid;
	}

	public String getArttic() {
		return arttic;
	}

	public void setArttic(String arttic) {
		this.arttic = arttic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
