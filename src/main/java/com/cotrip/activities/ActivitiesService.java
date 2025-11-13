package com.cotrip.activities;

import com.cotrip.activities.DTO.ActivityData;
import com.cotrip.activities.DTO.ActivityRequestPayload;
import com.cotrip.activities.DTO.ActivityResponse;
import com.cotrip.trip.TripModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivitiesService {

  @Autowired
  private ActivitiesRepository repository;

  public ActivityResponse registerActivity(ActivityRequestPayload payload, TripModel tripModel) {
    ActivitiesModel newActivity = new ActivitiesModel(payload.title(), payload.occurs_at(), tripModel);

    this
      .repository
      .save(newActivity);

    return new ActivityResponse(newActivity.getId());
  }

  public List<ActivityData> getAllActivitiesForTrip(UUID trip_id) {
    return this
      .repository
      .findByTripId(trip_id)
      .stream()
      .map(activity -> new ActivityData(activity.getId(), activity.getTitle(), activity.getOccursAt()))
      .toList();
  }
}
