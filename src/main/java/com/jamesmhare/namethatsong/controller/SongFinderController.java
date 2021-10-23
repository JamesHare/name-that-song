package com.jamesmhare.namethatsong.controller;

import com.jamesmhare.namethatsong.model.LyricSearchRequest;
import com.jamesmhare.namethatsong.model.RhymeSearchRequest;
import com.jamesmhare.namethatsong.model.RhymeSuggestion;
import com.jamesmhare.namethatsong.model.SongSuggestion;
import com.jamesmhare.namethatsong.service.RhymeFinderService;
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
    private final RhymeFinderService rhymeFinderService;

    public SongFinderController(final SongFinderService songFinderService,
                                final RhymeFinderService rhymeFinderService) {
        this.songFinderService = songFinderService;
        this.rhymeFinderService = rhymeFinderService;
    }

    @GetMapping("/song-search")
    public String showSongSearchView(final Model model) {
        model.addAttribute("lyricSearchRequest", new LyricSearchRequest());
        return "song-search";
    }

    @PostMapping("/show-song-results")
    public String showSongSearchResultsView(final LyricSearchRequest lyricSearchRequest, final Model model) {
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
            return "song-results-page";
        }
        model.addAttribute("success", true);
        model.addAttribute("messages", List.of("Looks like we found a match!"));
        model.addAttribute("trackListingResultList", songSuggestionsByLyric);
        return "song-results-page";
    }

    @GetMapping("/rhyme-search")
    public String showRhymeSearchView(final Model model) {
        model.addAttribute("rhymeSearchRequest", new RhymeSearchRequest());
        return "rhyme-search";
    }

    @PostMapping("/show-rhyme-results")
    public String showRhymeSearchResultsView(final RhymeSearchRequest rhymeSearchRequest, final Model model) {
        List<RhymeSuggestion> rhymeSuggestions;
        try {
            rhymeSuggestions = rhymeFinderService.getRhymingWords(rhymeSearchRequest.getWord());
        } catch (final Exception e) {
            log.error("An exception occurred when trying to find the rhyming word.", e);
            rhymeSuggestions = new ArrayList<>();
        }
        if (rhymeSuggestions.isEmpty()) {
            model.addAttribute("success", false);
            model.addAttribute("messages", List.of("Oops. We couldn't find any words that rhyme with "
                    + rhymeSearchRequest.getWord() + ". Please try a different word."));
            return "rhyme-results-page";
        }
        model.addAttribute("success", true);
        model.addAttribute("messages", List.of("Looks like we found a match!"));
        model.addAttribute("rhymeSuggestions", rhymeSuggestions);
        return "rhyme-results-page";
    }

}