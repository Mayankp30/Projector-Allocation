package databaseConnectivity;
import Pojo.Projector;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mayank on 6/25/17.
 */
public class DAO {

    /**
     * gets list of projector
     *
     * @return  HashMap<String, List<Projector>  projector name as a value and list of Projector's details as value
     */

    public HashMap<String,List<Projector>> getProjectorList(){
        final String QueryStatement = "Select * from ProjectorDetail";
        HashMap<String,List<Projector>> hmap = new HashMap<>();
        Statement statement = null ;
        try {
            statement = DbConnectivity.createConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("connection not established");
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(QueryStatement);
        while (rs.next()) {
            String teamName = rs.getString("team_name");
            Time start_time = new Time(rs.getTimestamp("start_time").getTime());
            Time end_time = new Time(rs.getTimestamp("end_time").getTime());

            String projectorRequested = rs.getString("projector_requested");
            Projector p = new Projector(teamName,start_time,end_time,projectorRequested);
            if(hmap.containsKey(projectorRequested)){
                List<Projector> al = hmap.get(p.getProjectorRequested());
                al.add(p);
                hmap.put(projectorRequested,al);
            }else{
                List<Projector> al = new ArrayList<Projector>();
                al.add(p);
                hmap.put(p.getProjectorRequested(),al);
            }
        }
        }
        catch (SQLException e) {
            System.out.println("unable to find query");
            e.printStackTrace();
        }
        return hmap;
    }

    /**
     * Inserts record of team name, startTime, endTime into database
     *
     * @param  teamName name of the team that requires project
     * @param  startTime start time when a projector is allotted to a team
     * @param  endTime end time is a time when a team is done using a projector
     *
     */
    public void requestProjector(String projector, String teamName, Date startTime, Date endTime){
        Timestamp sDateTime = new Timestamp(startTime.getTime());
        Timestamp eDateTime = new Timestamp(endTime.getTime());

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO ProjectorDetail"
                + "(team_name, start_time, end_time, projector_requested) VALUES"
                + "(?,?,?,?)";

        try {
            dbConnection = DbConnectivity.createConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, teamName);
            preparedStatement.setTimestamp(2, sDateTime);
            preparedStatement.setTimestamp(3, eDateTime);
            preparedStatement.setString(4,projector);

            // execute insert SQL statement
            preparedStatement.executeUpdate();

            System.out.println("Record is inserted into ProjectorDetail table!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes record of projector and team from database
     *
     * @param  teamName name of the team that requires project
     * @param  startTime start time when a projector is allotted to a team
     * @return Boolean - if record is successfully deleted returns true otherwise false
     *
     */
    public Boolean cancelProjector(String teamName, Date startTime){
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        Timestamp sDateTime = new Timestamp(startTime.getTime());

        String deleteTableSQL = "DELETE FROM ProjectorDetail WHERE team_name = ? and start_time = ?";
        Boolean result = false;
        try {
            dbConnection = DbConnectivity.createConnection();
            preparedStatement = dbConnection.prepareStatement(deleteTableSQL);
            preparedStatement.setString(1,teamName);
            preparedStatement.setTimestamp(2,sDateTime);

            result = preparedStatement.execute();
            System.out.println("Record is deleted from ProjectorDetail table!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
