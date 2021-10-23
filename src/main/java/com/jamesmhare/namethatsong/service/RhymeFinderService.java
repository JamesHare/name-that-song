package com.jamesmhare.namethatsong.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesmhare.namethatsong.model.RhymeSearchRequest;
import com.jamesmhare.namethatsong.model.RhymeSuggestion;
import com.jamesmhare.namethatsong.model.SongSuggestion;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RhymeFinderService {

    private final DatamuseFeignClient datamuseFeignClient;
    private final Decoder.Default decoder = new Decoder.Default();
    private final ObjectMapper mapper = new ObjectMapper();

    public RhymeFinderService(final DatamuseFeignClient datamuseFeignClient) {
        this.datamuseFeignClient = datamuseFeignClient;
    }

    public List<RhymeSuggestion> getRhymingWords(final String word) throws IOException {
        final Response feignResponse = datamuseFeignClient.getRhymingWords("words-that-rhyme", word);
        JsonNode body = mapper.readTree((String) decoder.decode(feignResponse, String.class));
        List<RhymeSuggestion> rhymeSuggestions = new ArrayList<>();

        for (JsonNode wordListItem : body) {
            rhymeSuggestions.add(RhymeSuggestion.builder()
                    .word(wordListItem.get("word").textValue())
                    .build());
        }
        return rhymeSuggestions;
    }

}
