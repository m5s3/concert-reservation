package com.consertreservation.domain.usertoken.producer;

import com.consertreservation.domain.usertoken.infra.event.ExpiredUserTokenEvent;

public interface UserTokenPublisher {

    void expireUserTokenPublish(ExpiredUserTokenEvent event);
}
