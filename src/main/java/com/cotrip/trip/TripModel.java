package com.cotrip.trip;
import com.cotrip.trip.DTO.TripRequestPayload;
import jakarta.persistence.*;

import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripModel {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false)
  private String destination;

  @Column(name="start_at", nullable = false)
  private LocalDateTime startAt;

  @Column(name="end_at", nullable = false)
  private LocalDateTime endAt;

  @Column(name="is_confirmed", nullable = false)
  private Boolean isConfirmed;

  @Column(name="owner_name", nullable = false)
  private String ownerName;

  @Column(name="owner_email", nullable = false)
  private String ownerEmail;

  public TripModel(TripRequestPayload data) {
    this.destination = data.destination();
    this.startAt = LocalDateTime.parse(data.start_at(), DateTimeFormatter.ISO_DATE_TIME);
    this.endAt = LocalDateTime.parse(data.end_at(), DateTimeFormatter.ISO_DATE_TIME);
    this.isConfirmed = false;
    this.ownerName = data.owner_name();
    this.ownerEmail = data.owner_email();
  }
}
