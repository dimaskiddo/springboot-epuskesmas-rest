package co.id.telkom.epuskesmas;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "e-Puskesmas API", version = "1.0"))
@SecurityScheme(
        name = "Login",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class SwaggerConfiguration {

}
