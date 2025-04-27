package com.mobylab.springbackend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.context.annotation.Profile;

@Profile("backend")
@SecurityRequirement(name = "bearerAuth")
public interface SecuredRestController {
}
