package hu.flow.jdbcpractice.repository;

import hu.flow.jdbcpractice.model.Ride;
import hu.flow.jdbcpractice.repository.util.RideRowMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.WritingConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class RideRepositoryImpl implements RideRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public Ride createRide(Ride ride) {
    //#1: This is the shortest solution, to insert data
/*    jdbcTemplate.update("insert into rides (name, duration) values (?,?)", ride.getName(),
        ride.getDuration());
        return null;*/
    //#2: This is a longer option, but it provides the generated id
/*    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

    List<String> columns = new ArrayList<>();
    columns.add("name");
    columns.add("duration");

    insert.setTableName("rides");
    insert.setColumnNames(columns);

    Map<String, Object> data = new HashMap<>();
    data.put("name", ride.getName());
    data.put("duration", ride.getDuration());

    insert.setGeneratedKeyName("id");
    Number key = insert.executeAndReturnKey(data);

    System.out.println(key);
    return null */

    //#3: Solution with keyholder
/*    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into rides (name, duration) values (?,?)",
            new String[]{"id"});
        ps.setString(1, ride.getName());
        ps.setInt(2, ride.getDuration());
        return ps;
      }
    }, keyHolder);
    Number id = keyHolder.getKey();
    return getRide(id.intValue());*/

    //#4: Solution based on #2 and combined with getRide() method
    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

    insert.setGeneratedKeyName("id");

    Map<String, Object> data = new HashMap<>();
    data.put("name", ride.getName());
    data.put("duration", ride.getDuration());

    List<String> columns = new ArrayList<>();
    columns.add("name");
    columns.add("duration");

    insert.setTableName("rides");
    insert.setColumnNames(columns);
    Number id = insert.executeAndReturnKey(data);

    return getRide(id.intValue());
  }

  @Override
  public Ride getRide(Integer id) {
    Ride ride = jdbcTemplate
        .queryForObject("select * from rides where id = ?", new RideRowMapper(), id);
    return ride;
  }


  @Override
  public List<Ride> getRides() {
    List<Ride> rides = jdbcTemplate.query("select * from rides", new RideRowMapper());
    return rides;
  }

  //This is the longer version of the prior method above, with anonymous innerclass and without RideRowMapper interface
/*  @Override
  public List<Ride> getRides() {
    List<Ride> rides = jdbcTemplate.query("select * from rides", new RowMapper<Ride>(){

      @Override
      public Ride mapRow(ResultSet resultSet, int i) throws SQLException {
        Ride ride = new Ride();
        ride.setId(resultSet.getInt("id"));
        ride.setName(resultSet.getString("name"));
        ride.setDuration(resultSet.getInt("duration"));
        return ride;
      }
    });
    return rides;
  }*/

  @Override
  public Ride updateRide(Ride ride) {
    jdbcTemplate.update("update rides set name = ?, duration = ? where id = ?",
        ride.getName(),
        ride.getDuration(),
        ride.getId());
    return ride;
  }

  @Override
  public void updateRides(List<Object[]> pairs) {
    jdbcTemplate.batchUpdate("update rides set ride_date = ? where id = ?", pairs);
  }

  @Override
  public void deleteRide(Integer id) {
    //# : This is the original solution for deleting data from table by id
    //jdbcTemplate.update("delete from rides where id = ?", id);

    //# : This is the namedParameter solution for deleting
    NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("id", id);
    namedTemplate.update("delete from rides where id = :id", paramMap);
  }
}
