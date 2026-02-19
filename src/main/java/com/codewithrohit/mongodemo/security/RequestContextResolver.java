package com.codewithrohit.mongodemo.security;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class RequestContextResolver {

    private final SecurityProperties securityProperties;

    public RequestContext resolve(String userId,
                                  String clientId,
                                  String requestUserId,
                                  String product)  {

        if (StringUtils.hasText(userId)) {

            return RequestContext.builder()
                    .callerType(CallerType.USER)
                    .userId(userId)
                    .product(product)
                    .build();
        }

        if (StringUtils.hasText(clientId) &&
                StringUtils.hasText(requestUserId)) {

            if (!securityProperties.getAllowedClientIds()
                    .contains(clientId)) {
                throw new BadRequestException("Unauthorized APIM client");
            }

            return RequestContext.builder()
                    .callerType(CallerType.SERVICE)
                    .clientId(clientId)
                    .userId(requestUserId)
                    .product(product)
                    .build();
        }

        throw new BadRequestException("Invalid headers");
    }
}
