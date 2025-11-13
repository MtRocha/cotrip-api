package com.cotrip.activities;

import com.cotrip.activities.DTO.ActivityData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivitiesRepository extends JpaRepository<ActivitiesModel, UUID> {
  List<ActivitiesModel> findByTripId(UUID trip_id);
}
