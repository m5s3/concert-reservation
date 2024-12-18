package com.consertreservation.api.usertoken;

import com.consertreservation.api.usertoken.dto.ResponseUserToken;
import com.consertreservation.domain.usertoken.application.UserTokenService;
import com.consertreservation.domain.usertoken.application.dto.ResultUserTokenServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/usertoken")
@RequiredArgsConstructor
public class UserTokenController {

    private final UserTokenService userTokenService;

    @GetMapping
    public ResponseEntity<ResponseUserToken> getUserToken(@RequestParam(name = "user_id") Long userId) {
        ResultUserTokenServiceDto userToken = userTokenService.getUserToken(userId);
        return ResponseEntity.ok()
                .header("Authorization", String.valueOf(userToken.userId()))
                .body(ResponseUserToken.fromResultUserTokenServiceDto(userToken));
    }
}
