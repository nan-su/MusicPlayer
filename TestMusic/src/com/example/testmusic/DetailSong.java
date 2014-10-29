package com.example.testmusic;

public class DetailSong {
	
	String songtitle;
	int songImageID,songID;
	
	public DetailSong(String songtitle,int songImageID,int songID) {
		// TODO Auto-generated constructor stub
		
		this.songtitle=songtitle;
		this.songImageID=songImageID;
		this.songID=songID;
	}

	public String getSongtitle() {
		return songtitle;
	}
	public int getSongImageID() {
		return songImageID;
	}
	public int getSongID() {
		return songID;
	}
}
