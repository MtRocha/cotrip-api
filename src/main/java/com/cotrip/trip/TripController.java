package com.cotrip.trip;
import com.cotrip.activities.ActivitiesService;
import com.cotrip.activities.DTO.ActivityData;
import com.cotrip.activities.DTO.ActivityRequestPayload;
import com.cotrip.activities.DTO.ActivityResponse;
import com.cotrip.links.DTO.LinkData;
import com.cotrip.links.DTO.LinkRequestPayload;
import com.cotrip.links.DTO.LinkResponse;
import com.cotrip.links.LinkService;
import com.cotrip.participant.*;
import com.cotrip.participant.DTO.ParticipantCreatedResponse;
import com.cotrip.participant.DTO.ParticipantData;
import com.cotrip.participant.DTO.ParticipantRequestPayload;
import com.cotrip.trip.DTO.TripCreateResponse;
import com.cotrip.trip.DTO.TripGetDTO;
import com.cotrip.trip.DTO.TripRequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/trips")
public class TripController {

  @Autowired
  private ParticipantService participantService;

  @Autowired
  private ActivitiesService activitiesService;

  @Autowired
  private LinkService linkService;

  @Autowired
  private TripService tripService;


  public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {

    var newTripModel = tripService.CreateTrip(payload);

    return ResponseEntity.ok(
      new TripCreateResponse(newTripModel.id())
    );

  }

  @GetMapping
  public ResponseEntity<List<TripGetDTO>> getAllTrips() {

    var trips = tripService.getTrips();

    if (trips.isEmpty()) {
      return ResponseEntity.<List<TripModel>>noContent().build();
    }

    return ResponseEntity.ok(trips);
  }

  @GetMapping("/{tripId}")
  public ResponseEntity<TripGetDTO> getTripDetails(@PathVariable UUID tripId) {

    var trip = tripService.getTripById(tripId);

    return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  };

  @PutMapping("/{tripId}")
  public ResponseEntity<TripGetDTO> updateTripDetails(@PathVariable UUID tripId, @RequestBody TripRequestPayload payload) {

      var trip = tripService.UpdateTrip(tripId, payload);

      return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

  };

  @GetMapping("/{tripId}/confirm")
  public ResponseEntity<TripGetDTO> confirmTrip(@PathVariable UUID tripId) {

      var trip = tripService.ConfirmTrip(tripId);

      this.participantService.triggerConfirmationEmailToParticipants(tripId);

      return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

  };

  @PostMapping("/{tripId}/new-invite")
  public ResponseEntity<String> inviteNewParticipants(@PathVariable UUID tripId, @RequestBody ParticipantRequestPayload payload) {
    Optional<TripGetDTO> trip = tripService.getTripById(tripId);

    if(trip.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    ParticipantCreatedResponse participantResponse = this.participantService.registerParticipantToEvent(payload.email(), trip.get().id());

    if(trip.get().isConfirmed()) this.participantService.triggerConfirmationEmailToParticipant(payload.email());


    return ResponseEntity.ok().build();
  };

  @GetMapping("/{tripId}/participants")
  public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID tripId) {
    List<ParticipantData> res = this.participantService.getAllParticipantsByTripId(tripId);

    return ResponseEntity.ok(res);
  }

  @PostMapping("/{tripId}/new-activity")
  public ResponseEntity<String> createNewActivity(@PathVariable UUID tripId, @RequestBody ActivityRequestPayload payload) {
      Optional<TripGetDTO> trip = tripService.getTripById(tripId);

      if(trip.isEmpty()) {
          return ResponseEntity.notFound().build();
      }
    ActivityResponse activityResponse = this.activitiesService.registerActivity(payload, trip.get().id());

    return ResponseEntity.ok().build();
  };

  @GetMapping("/{tripId}/activities")
  public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID tripId) {
    List<ActivityData> res = this.activitiesService.getAllActivitiesForTrip(tripId);
    return ResponseEntity.ok(res);
  }


  @PostMapping("/{tripId}/new-link")
  public ResponseEntity<String> createNewLink(@PathVariable UUID tripId, @RequestBody LinkRequestPayload payload) {
      Optional<TripGetDTO> trip = tripService.getTripById(tripId);

      if(trip.isEmpty()) {
          return ResponseEntity.notFound().build();
      }


    this.linkService.createLink(payload, trip.get().id());


    return ResponseEntity.ok().build();
  }

  @GetMapping("/{tripId}/links")
  public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID tripId) {
    List<LinkData> res = this.linkService.getAllLinkForTrip(tripId);
    return ResponseEntity.ok(res);

  }



}
