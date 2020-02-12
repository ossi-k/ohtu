
package ohtu;

public class Player {
    private String name;
    private String team;
    private int goals;
    private int assists;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " team " + team + " goals " + goals + " assists " + assists;
    }
      
}
