package com.jamesmhare.namethatsong.service;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "DatamuseFeignClient", url = "https://api.datamuse.com")
public interface DatamuseFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/words?rel_rhy={word}")
    Response getRhymingWords(@RequestHeader("User-Agent") String userAgent, @PathVariable final String word);

}
