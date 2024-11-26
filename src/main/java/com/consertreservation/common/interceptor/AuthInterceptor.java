package com.consertreservation.common.interceptor;

import static com.consertreservation.domain.usertoken.exception.UserTokenErrorCode.UNAUTHORIZED;

import com.consertreservation.domain.usertoken.application.UserTokenService;
import com.consertreservation.domain.usertoken.application.dto.ResultUserTokenServiceDto;
import com.consertreservation.domain.usertoken.exception.UserTokenException;
import com.consertreservation.domain.usertoken.model.TokenStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final UserTokenService userTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorization = request.getHeader("Authorization");

        if (Objects.nonNull(authorization) && !userTokenService.isAuthorized(Long.valueOf(authorization))) {
            buildUnAuthorizedResponse(response);
            return false;
        }

        return true;
    }

    private void buildUnAuthorizedResponse(HttpServletResponse response) throws IOException {
        String result = objectMapper.writeValueAsString(new UserTokenException(UNAUTHORIZED, "해당 유저는 권한이 없습니다"));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }
}
