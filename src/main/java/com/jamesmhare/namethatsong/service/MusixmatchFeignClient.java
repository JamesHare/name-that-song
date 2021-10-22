package com.jamesmhare.namethatsong.service;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "RedditFeignClient", url = "http://api.musixmatch.com/ws/1.1/")
public interface MusixmatchFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/track.search?apikey={apiKey}&q_lyrics={lyric}")
    Response getSongSuggestionsByLyric(@RequestHeader("User-Agent") String userAgent,
                                       @PathVariable final String apiKey,
                                       @PathVariable final String lyric);

}
