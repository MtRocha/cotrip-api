package com.cotrip.trip.DTO;
import java.util.List;

public record TripRequestPayload(String destination, String start_at, String end_at, List<String> emails_to_invite, String owner_name, String owner_email) {

}
