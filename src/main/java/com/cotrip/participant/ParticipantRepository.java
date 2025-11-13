package com.cotrip.participant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<ParticipantModel, UUID> {
  List<ParticipantModel> findByTripId(UUID trip_id);
}
