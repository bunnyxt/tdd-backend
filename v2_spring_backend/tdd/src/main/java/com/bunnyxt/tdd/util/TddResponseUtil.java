package com.bunnyxt.tdd.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TddResponseUtil {

    public static <T> ResponseEntity<List<T>> AssembleList(List<T> list, Integer totalCount){
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(totalCount));
        headers.add("Access-Control-Allow-Headers", "x-total-count");
        headers.add("Access-Control-Expose-Headers", "x-total-count");
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> SetMaxAge(T obj, Integer maxAge) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "max-age=" + maxAge.toString());
        return new ResponseEntity<>(obj, headers, HttpStatus.OK);
    }

    // TODO create a process chain which can add multiple headers and set status using brief codes
}
