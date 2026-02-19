package com.codewithrohit.mongodemo.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestContext {

    private CallerType callerType;

    private String userId;

    private String clientId;

    private String product;
}

