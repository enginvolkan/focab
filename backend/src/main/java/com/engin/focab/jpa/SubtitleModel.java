package com.engin.focab.jpa;

public class SubtitleModel {

	public int id;
	public String startTime;
	public String endTime;
	public String text;
	public long timeIn;
	public long timeOut;
	public SubtitleModel nextSubtitle;

	@Override
	public String toString() {
		return "SubtitleModel [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", text=" + text
				+ ", timeIn=" + timeIn + ", timeOut=" + timeOut + "]";
	}

}