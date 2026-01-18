package config;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiErrorLoggingFilter implements Filter {

    private static final Logger log =
            LoggerFactory.getLogger(ApiErrorLoggingFilter.class);

    @Override
    public Response filter(
            FilterableRequestSpecification request,
            FilterableResponseSpecification responseSpec,
            FilterContext context
    ) {

        Response response = context.next(request, responseSpec);

        if (response.statusCode() >= 400) {

            log.error("API request failed");
            log.error("REQUEST: {} {}", request.getMethod(), request.getURI());
            log.error("Request headers: {}", request.getHeaders());
            log.error("Request body:\n{}", (Object) request.getBody());

            log.error("RESPONSE STATUS: {}", response.statusCode());
            log.error("Response headers: {}", response.getHeaders());
            log.error("Response body:\n{}", response.getBody().asPrettyString());
        }

        return response;
    }
}
