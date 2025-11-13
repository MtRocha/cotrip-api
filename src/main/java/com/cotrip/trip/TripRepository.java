package com.cotrip.trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface TripRepository extends JpaRepository<TripModel, UUID> {
  public List<TripModel> findByOwnerEmail(String email);
}
