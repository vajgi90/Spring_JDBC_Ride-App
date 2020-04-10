package hu.flow.jdbcpractice.service;

import hu.flow.jdbcpractice.model.Ride;
import java.util.List;


public interface RideService {

	Ride createRide(Ride ride);
	
	List<Ride> getRides();

	Ride getRide(Integer id);

	Ride updateRide(Ride ride);

	void batch();

	void deleteRide(Integer id);
}