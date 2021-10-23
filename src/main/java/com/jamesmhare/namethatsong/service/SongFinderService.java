package com.jamesmhare.namethatsong.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesmhare.namethatsong.model.SongSuggestion;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongFinderService {

    @Value( "${name-that-song.api-key}" )
    private String apiKey;
    private final MusixmatchFeignClient musixmatchFeignClient;
    private final Decoder.Default decoder = new Decoder.Default();
    private final ObjectMapper mapper = new ObjectMapper();

    public SongFinderService(final MusixmatchFeignClient musixmatchFeignClient) {
        this.musixmatchFeignClient = musixmatchFeignClient;
    }

    public List<SongSuggestion> getSongSuggestionsByLyric(final String lyric) throws IOException {
        final Response feignResponse = musixmatchFeignClient.getSongSuggestionsByLyric("name-that-song", apiKey, lyric);
        JsonNode body = mapper.readTree((String) decoder.decode(feignResponse, String.class));
        List<SongSuggestion> songSuggestions = new ArrayList<>();

        for (JsonNode trackListing : body.get("message").get("body").get("track_list")) {
            songSuggestions.add(SongSuggestion.builder()
                    .trackName(trackListing.get("track").get("track_name").textValue())
                    .artistName(trackListing.get("track").get("artist_name").textValue())
                    .albumName(trackListing.get("track").get("album_name").textValue())
                    .build());
        }
        return songSuggestions;
    }

}
