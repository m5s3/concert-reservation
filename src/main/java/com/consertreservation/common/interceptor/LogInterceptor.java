package com.consertreservation.common.interceptor;

import com.consertreservation.api.common.log.LogDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = UUID.randomUUID().toString();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        requestWrapper.setAttribute("traceId", traceId);
        request.setAttribute("startTime", System.currentTimeMillis());
        LogDto.Request logRequest = LogDto.Request.builder()
                .traceId(traceId)
                .serverIp(requestWrapper.getRemoteAddr())
                .httpMethod(requestWrapper.getMethod())
                .uri(requestWrapper.getRequestURI())
                .logTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .params(requestWrapper.getParameterMap())
                .build();
        log.info(objectMapper.writeValueAsString(logRequest));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        long elapsedTime = System.currentTimeMillis() - Long.parseLong(String.valueOf(request.getAttribute("startTime")));

        LogDto.Response.ResponseBuilder responseBuilder = LogDto.Response.builder()
                .traceId(String.valueOf(request.getAttribute("traceId")))
                .httpMethod(request.getMethod())
                .uri(request.getRequestURI())
                .elapsedTime(String.valueOf(elapsedTime));

        log.info(objectMapper.writeValueAsString(responseBuilder.build()));
    }
}
