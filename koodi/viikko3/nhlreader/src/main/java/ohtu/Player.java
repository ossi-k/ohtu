
package ohtu;

import java.util.Comparator;

public class Player implements Comparator<Player>{
    private String name;
    private String team;
    private String nationality;
    private int goals;
    private int assists;
    private int points;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public String getNationality() {
        return nationality;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }
    
    public int getPoints() {
        points = this.goals+this.assists;
        return points;
    }

    public String getTeam() {
        return team;
    }
    
    @Override
    public String toString() {
        return name + " " + nationality + " team " + team + " goals " + goals + " assists " + assists;
    }
    
    @Override
    public int compare(Player a, Player b) {
        int returnValue = 0;
        if (a.goals < b.goals) {
            returnValue = -1;
        } else {
            returnValue = 1;
        }
        return returnValue;
    }
}
