package co.id.telkom.epuskesmas.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Queues", description = "Endpoints for Queus")
@SecurityRequirement(name = "Login")
@RequestMapping(value="/api/v1/queues", produces={"application/json"})
@RestController
public class QueueController {

}
