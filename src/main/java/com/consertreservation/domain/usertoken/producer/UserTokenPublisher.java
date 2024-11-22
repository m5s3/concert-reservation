package com.consertreservation.domain.usertoken.producer;

import com.consertreservation.domain.outbox.components.dto.ResultOutBox;

public interface UserTokenPublisher {

    void expireUserTokenPublish(ResultOutBox event);
}
