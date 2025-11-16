package com.cotrip.trip.DTO;

import java.time.LocalDateTime;
import java.util.UUID;


public record TripGetDTO(UUID id, String destination, LocalDateTime startAt, LocalDateTime endAt, boolean isConfirmed) { }
