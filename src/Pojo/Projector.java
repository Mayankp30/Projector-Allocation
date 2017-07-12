package Pojo;

import java.sql.Time;

/**
 * Created by mayank on 6/25/17.
 */


// projector class having fields teamName, startTime, endTime, projectorRequested

public class Projector {

    String teamName;
    Time startTime;
    Time endTime;
    String projectorRequested;

    public Projector(String team_name,Time startTime, Time endTime, String projectorRequested){
        this.teamName = team_name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.projectorRequested = projectorRequested;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getProjectorRequested() {
        return projectorRequested;
    }

    public void setProjectorRequested(String projectorRequested) {
        this.projectorRequested = projectorRequested;
    }

    @Override
    public String toString() {
        return "Projector{" +
                "teamName='" + teamName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", projectorRequested='" + projectorRequested + '\'' +
                '}';
    }
}
