import java.sql.Time;
import java.util.*;
import Pojo.Projector;
import databaseConnectivity.DAO;

/**
 * Created by mayank on 6/25/17.
 */
public class AssignProjector {

    // names of projector - p1, p2, p3 that can be allotted across different teams.
    public static List<String> namesOfProjector = new ArrayList<String>(Arrays.asList("p1","p2","p3"));

    /**
     * request and assigns a projector
     *
     * @param  projector  Projector object
     * @param  teamName name of the team that requires project
     * @param  startTime start time when a projector is allotted to a team
     * @param  endTime end time is a time when a team is done using a projector
     *
     */
    public void assignProjector(String projector, String teamName, Time startTime, Time endTime) {
        DAO qs = new DAO();
        qs.requestProjector(projector, teamName,startTime,endTime);
    }

    /**
     * releases a projector
     *
     * @param  teamName name of the team that requires project
     * @param  startTime start time when a projector is allotted to a team
     * @return Boolean whether a projector is successfully released or not
     *
     */
    public Boolean releaseProjector(String teamName, Time startTime) {
        DAO qs = new DAO();
        Boolean cancelStatus = qs.cancelProjector(teamName,startTime);
        return cancelStatus;
    }

    /**
     * compares time and checks if a projector can be assigned during that time or not.
     *
     *
     * @param  startTime start time when a projector is allotted to a team
     * @param  endTime end time is a time when a team is done using a projector
     * @return Boolean whether a projector can be assigned during given times or not
     *
     */

    public Boolean compareTime(Time startTime, Time endTime, List<Projector> projector)
    {
        boolean flag = true;
        for(Projector p : projector){
            if((startTime.after(p.getStartTime())) && startTime.before(p.getEndTime())){
                flag = false;
            }
            if((endTime.after(p.getStartTime())) && endTime.before(p.getEndTime())){
                flag = false;
            }
            if((startTime.equals(p.getStartTime())) && endTime.equals(p.getEndTime())){
                 flag = false;
             }
             // middle condition
             if(startTime.before(p.getStartTime()) && endTime.after(p.getEndTime())){
                flag = false;
             }
        }
        return flag;
    }


    public void getListofAssignedProjector(Time startTime, Time endTime){

        DAO qs = new DAO();
        HashMap<String,List<Projector>> hmap = qs.getProjectorList();

        Set<Map.Entry<String, List<Projector>>> entrySet = hmap.entrySet();
        for (Map.Entry entry : entrySet) {

            System.out.println("key: " + entry.getKey());

            Boolean value = compareTime(startTime,endTime,(List<Projector>)entry.getValue());

            if(value){
                // insert ( entry.getKey() , startTime , endTime , Team Name)
            } else {
                System.out.println("Projector not available at given time");
            }
        }
    }

    List<String> projectors = new ArrayList<>();

    public static void main(String[] args) {
        AssignProjector service = new AssignProjector();

        try {
            service.assignProjector("p2","Random",new Time(8,00,00), new Time(8,00,00));
            service.assignProjector("p3","Random",new Time(8,00,00), new Time(8,00,00));
            service.assignProjector("p1","Random",new Time(8,00,00), new Time(8,00,00));
            service.getListofAssignedProjector(new Time(15,00,00), new Time(17,00,00));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
