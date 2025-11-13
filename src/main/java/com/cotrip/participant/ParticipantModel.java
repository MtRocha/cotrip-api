package com.cotrip.participant;

import com.cotrip.trip.TripModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Table(name = "participants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantModel{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "trip_id", nullable = false)
  private TripModel trip;

  @Column(name="is_confirmed", nullable = false)
  private Boolean isConfirmed;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String name;

  public ParticipantModel(String email, TripModel trip) {
    this.email = email;
    this.trip = trip;
    this.isConfirmed = false;
    this.name = "";
  }
}
