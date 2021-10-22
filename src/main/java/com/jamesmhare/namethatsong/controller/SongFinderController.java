package com.jamesmhare.namethatsong.controller;

import com.jamesmhare.namethatsong.model.LyricSearchRequest;
import com.jamesmhare.namethatsong.model.SongSuggestion;
import com.jamesmhare.namethatsong.service.SongFinderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class SongFinderController {

    private final SongFinderService songFinderService;

    public SongFinderController(final SongFinderService songFinderService) {
        this.songFinderService = songFinderService;
    }

    @GetMapping("/")
    public String showSearchView(final Model model) {
        model.addAttribute("lyricSearchRequest", new LyricSearchRequest());
        return "index";
    }

    @PostMapping("/show-results")
    public String showSearchResultsView(final LyricSearchRequest lyricSearchRequest, final Model model) {
        List<SongSuggestion> songSuggestionsByLyric;
        try {
            songSuggestionsByLyric = songFinderService.getSongSuggestionsByLyric(lyricSearchRequest.getLyric());
        } catch (final Exception e) {
            log.error("An exception occurred when trying to find the song by lyrics");
            songSuggestionsByLyric = new ArrayList<>();
        }
        if (songSuggestionsByLyric.isEmpty()) {
            model.addAttribute("success", false);
            model.addAttribute("messages", List.of("Oops. We couldn't find any songs matching those lyrics. " +
                    "Please try a different lyric."));
            return "results-page";
        }
        model.addAttribute("success", true);
        model.addAttribute("messages", List.of("Looks like we found a match!"));
        model.addAttribute("trackListingResultList", songSuggestionsByLyric);
        return "results-page";
    }

}