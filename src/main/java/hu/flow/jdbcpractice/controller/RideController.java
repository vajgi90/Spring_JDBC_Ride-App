package hu.flow.jdbcpractice.controller;

import hu.flow.jdbcpractice.exception.ServiceError;
import hu.flow.jdbcpractice.model.Ride;
import hu.flow.jdbcpractice.service.RideService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RideController {
  @Autowired
  private RideService rideService;

  @GetMapping("rides")
  public List<Ride> getRides() {
    return rideService.getRides();
  }

  @GetMapping("ride/{id}")
  public Ride getRideById(@PathVariable Integer id) {
    return rideService.getRide(id);
  }

  @GetMapping("ride/batch")
  public void batch() {
    rideService.batch();
  }

  @PostMapping("ride")
  public Ride createRide(@RequestBody Ride ride) {
    return rideService.createRide(ride);
  }

  @PutMapping("ride")
  public Ride updateRide(@RequestBody Ride ride) {
    return rideService.updateRide(ride);
  }

  @DeleteMapping("ride/{id}")
  public void deleteById(@PathVariable Integer id) {
    rideService.deleteRide(id);
  }

  @GetMapping("exception")
  public void exceptionThrow() {
    throw new DataAccessException("Testing exception thrown") {};
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ServiceError> handle(RuntimeException ex) {
    ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }
}
