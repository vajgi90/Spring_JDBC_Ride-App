package hu.flow.jdbcpractice.controller;

import hu.flow.jdbcpractice.model.Ride;
import java.util.List;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RideControllerTest {

  @Test(timeout=3000)
  public void testCreateRide() {
    RestTemplate restTemplate = new RestTemplate();

    Ride ride = new Ride();
    ride.setName("Grand Canaria");
    ride.setDuration(230);

    ride = restTemplate.postForObject("http://localhost:8080/ride", ride, Ride.class);
    System.out.println("Ride: " + ride);
  }

  @Test(timeout=3000)
  public void testGetRides() {
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
        "http://localhost:8080/rides", HttpMethod.GET,
        null, new ParameterizedTypeReference<List<Ride>>() {
        });
    List<Ride> rides = ridesResponse.getBody();

    for (Ride ride : rides) {
      System.out.println("Ride name: " + ride.getName());
    }
  }

  @Test(timeout=3000)
  public void testGetRide() {
    RestTemplate restTemplate = new RestTemplate();

    Ride ride = restTemplate.getForObject("http://localhost:8080/ride/1", Ride.class);

    System.out.println("Ride name: " + ride.getName());
  }

  @Test(timeout=3000)
  public void testUpdateRide() {
    RestTemplate restTemplate = new RestTemplate();

    Ride ride = restTemplate.getForObject("http://localhost:8080/ride/1", Ride.class);

    ride.setDuration(ride.getDuration() + 1);

    restTemplate.put("http://localhost:8080/ride", ride);

    System.out.println("Ride name: " + ride.getName());
  }

  @Test(timeout=3000)
  public void testBatchUpdate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getForObject("http://localhost:8080/ride/batch", Object.class);
  }

  @Test(timeout=3000)
  public void testDelete() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.delete("http://localhost:8080/ride/4");
  }

  @Test(timeout=3000)
  public void testException() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getForObject("http://localhost:8080/exception", Ride.class);
  }

}
