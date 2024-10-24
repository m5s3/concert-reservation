package com.consertreservation.api.common.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Builder;

public class LogDto {

    @Builder
    public record Request(
            @JsonProperty(value = "traceId")
            String traceId,
            @JsonProperty(value = "http_method")
            String httpMethod,
            @JsonProperty(value = "uri")
            String uri,
            @JsonProperty(value = "params")
            Map<String, String[]> params,
            @JsonProperty(value = "log_time")
            String logTime,
            @JsonProperty(value = "server_ip")
            String serverIp
    ) {
    }

    @Builder
    public record Response(
            @JsonProperty(value = "traceId")
            String traceId,
            @JsonProperty(value = "http_method")
            String httpMethod,
            @JsonProperty(value = "uri")
            String uri,
            @JsonProperty(value = "elapsed_time")
            String elapsedTime,
            @JsonProperty(value = "http_status")
            String httpStatus,
            @JsonProperty(value = "exception")
            String exception
    ) {
    }
}
