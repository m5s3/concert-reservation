package com.consertreservation.api.user;

import com.consertreservation.api.user.dto.RequestCharge;
import com.consertreservation.api.user.dto.ResponseCharge;
import com.consertreservation.domain.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/charge")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResponseCharge> charge(@RequestParam(name = "user_id") Long userId) {
        return ResponseEntity.ok().body(ResponseCharge.fromResultServiceDto(userService.getCharge(userId)));
    }

    @PostMapping
    public ResponseEntity<ResponseCharge> charge(@RequestBody RequestCharge request) {
        return ResponseEntity.ok()
                .body(ResponseCharge.fromResultServiceDto(userService.charge(request.userId(), request.amount())));
    }
}
