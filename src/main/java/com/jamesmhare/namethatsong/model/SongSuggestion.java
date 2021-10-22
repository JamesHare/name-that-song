package com.jamesmhare.namethatsong.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongSuggestion {

    private String trackName;
    private String albumName;
    private String artistName;

}
