package com.newtouc.twophase_termination;

public class AlarmInfo {

	public final AlarmType alarmType;
	public final String id;
	public final String extraInfo;

	public AlarmInfo(AlarmType alarmType, String id, String extraInfo) {
        this.alarmType=alarmType;
        this.id=id;
        this.extraInfo=extraInfo;
	}

	public AlarmType getAlarmType() {
		return alarmType;
	}

	public String getId() {
		return id;
	}

	public String getExtraInfo() {
		return extraInfo;
	}
    
}
