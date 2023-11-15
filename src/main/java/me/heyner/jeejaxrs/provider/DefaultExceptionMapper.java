package me.heyner.jeejaxrs.provider;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Exception exception) {

        final var jsonObject = Json.createObjectBuilder()
                .add("host",uriInfo.getAbsolutePath().getHost())
                .add("resource", uriInfo.getAbsolutePath().getPath())
                .add("title", exception.getClass().getSimpleName())
                .add("error", exception.getMessage());


        JsonObject errorJsonEntity = jsonObject.build();

        return Response.status(Response.Status.BAD_REQUEST).entity(errorJsonEntity).build();
    }
}
