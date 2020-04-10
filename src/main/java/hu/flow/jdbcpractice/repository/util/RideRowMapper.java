package hu.flow.jdbcpractice.repository.util;

import hu.flow.jdbcpractice.model.Ride;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RideRowMapper implements RowMapper<Ride> {

  @Override
  public Ride mapRow(ResultSet resultSet, int i) throws SQLException {
    Ride ride = new Ride();
    ride.setId(resultSet.getInt("id"));
    ride.setName(resultSet.getString("name"));
    ride.setDuration(resultSet.getInt("duration"));
    return ride;
  }
}
