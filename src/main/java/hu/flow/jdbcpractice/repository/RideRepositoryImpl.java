package hu.flow.jdbcpractice.repository;

import hu.flow.jdbcpractice.model.Ride;
import hu.flow.jdbcpractice.repository.util.RideRowMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
//	#1: inserting data with JdbcTemplate.update() method;
/*    jdbcTemplate.update("insert into rides (name, duration) values (?,?)", ride.getName(),
        ride.getDuration());
        return null;*/
    //#2: inserting data with SimpleJdbcInsert class to retieve the key;
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

    //#3: inserting data with JdbcTemplate.update() method and KeyHolder;
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

    //#4: solution based on #2 and combined with getRide() method;
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
    //#5: get one row by using JdbcTemplate.queryForObject() method;
    Ride ride = jdbcTemplate
        .queryForObject("select * from rides where id = ?", new RideRowMapper(), id);
    return ride;
  }

  @Override
  public List<Ride> getRides() {
    //#6: get all records by using JdbcTemplate.query() method;
    List<Ride> rides = jdbcTemplate.query("select * from rides", new RideRowMapper());
    return rides;
  }

  //#7: get all records by using JdbcTemplate.query() method with anonymus innerclass implementation;
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
    //#8: update record with JdbcTemplate.update() method;
    jdbcTemplate.update("update rides set name = ?, duration = ? where id = ?",
        ride.getName(),
        ride.getDuration(),
        ride.getId());
    return ride;
  }

  @Override
  public void updateRides(List<Object[]> pairs) {
    //#9: update records with JdbcTemplate.batchUpdate() method;
    jdbcTemplate.batchUpdate("update rides set ride_date = ? where id = ?", pairs);
  }

  @Override
  public void deleteRide(Integer id) {
    //#10: delete record with JdbcTemplate.update() method;
    //jdbcTemplate.update("delete from rides where id = ?", id);

    //#11: delete record with NamedParameterJdbcTemplate() class;
    NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("id", id);
    namedTemplate.update("delete from rides where id = :id", paramMap);
  }
}
