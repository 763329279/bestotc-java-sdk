package com.bestotc.domain;

import lombok.Getter;

@Getter
public enum ApiProtocol {

    HTTP( "http://"),
    HTTPS("https://"),
    ;

    ApiProtocol(String value) {
        this.value = value;
    }

    private String value;

}
