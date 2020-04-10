package hu.flow.jdbcpractice.repository;

import hu.flow.jdbcpractice.model.Ride;
import java.util.List;

public interface RideRepository {
	
	List<Ride> getRides();

	Ride getRide(Integer id);

	Ride createRide(Ride ride);

	Ride updateRide(Ride ride);

	void updateRides(List<Object[]> pairs);

	void deleteRide(Integer id);
}