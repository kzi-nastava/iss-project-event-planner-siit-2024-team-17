package com.ftn.event_hopper.controllers.locations;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/geocode")
public class GeocodingController {

    private final RestTemplate restTemplate;

    public GeocodingController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping
    public ResponseEntity<?> getCoordinates(@RequestParam String address, @RequestParam String city) {
        String query = UriUtils.encodeQuery(address + ", " + city, StandardCharsets.UTF_8);
        String url = "https://nominatim.openstreetmap.org/search?q=" + query + "&format=json&limit=1";

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "MyApp/1.0 (your-email@example.com)"); // obavezno za Nominatim
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<NominatimResponse[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    NominatimResponse[].class
            );

            if (response.getBody() == null || response.getBody().length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Location not found"));
            }

            NominatimResponse loc = response.getBody()[0];
            return ResponseEntity.ok(Map.of(
                    "latitude", loc.getLat(),
                    "longitude", loc.getLon()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Server error"));
        }
    }

    static class NominatimResponse {
        private String lat;
        private String lon;
        public String getLat() { return lat; }
        public void setLat(String lat) { this.lat = lat; }
        public String getLon() { return lon; }
        public void setLon(String lon) { this.lon = lon; }
    }
}
