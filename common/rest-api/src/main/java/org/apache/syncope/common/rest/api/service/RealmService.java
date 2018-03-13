/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.common.rest.api.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.ResponseHeader;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.syncope.common.lib.to.ProvisioningResult;
import org.apache.syncope.common.lib.to.RealmTO;
import org.apache.syncope.common.rest.api.RESTHeaders;

/**
 * REST operations for realms.
 */
@Api(tags = "Realms", authorizations = {
    @Authorization(value = "BasicAuthentication")
    , @Authorization(value = "Bearer") })
@Path("realms")
public interface RealmService extends JAXRSService {

    /**
     * Returns a list of all realms.
     *
     * @return list of all realms.
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    List<RealmTO> list();

    /**
     * Returns realms rooted at the given path.
     *
     * @param fullPath full path of the root realm where to read from
     * @return realms rooted at the given path
     */
    @GET
    @Path("{fullPath:.*}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    List<RealmTO> list(@NotNull @PathParam("fullPath") String fullPath);

    /**
     * Creates a new realm under the given path.
     *
     * @param parentPath full path of the parent realm
     * @param realmTO new realm.
     * @return Response object featuring Location header of created realm as well as the realm itself
     * enriched with propagation status information
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name = RESTHeaders.PREFER, paramType = "header", dataType = "string",
                value = "Allows the client to specify a preference for the result to be returned from the server",
                defaultValue = "return-content", allowableValues = "return-content, return-no-content",
                allowEmptyValue = true)
        , @ApiImplicitParam(name = RESTHeaders.NULL_PRIORITY_ASYNC, paramType = "header", dataType = "boolean",
                value = "If 'true', instructs the propagation process not to wait for completion when communicating"
                + " with External Resources with no priority set",
                defaultValue = "false", allowEmptyValue = true) })
    @ApiResponses(
            @ApiResponse(code = 201,
                    message = "Realm successfully created enriched with propagation status information, as Entity,"
                    + "or empty if 'Prefer: return-no-content' was specified",
                    response = ProvisioningResult.class, responseHeaders = {
                @ResponseHeader(name = RESTHeaders.RESOURCE_KEY, response = String.class,
                        description = "UUID generated for the realm created")
                , @ResponseHeader(name = HttpHeaders.LOCATION, response = String.class,
                        description = "URL of the realm created")
                , @ResponseHeader(name = RESTHeaders.PREFERENCE_APPLIED, response = String.class,
                        description = "Allows the server to inform the "
                        + "client about the fact that a specified preference was applied") }))
    @POST
    @Path("{parentPath:.*}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response create(@NotNull @PathParam("parentPath") String parentPath, @NotNull RealmTO realmTO);

    /**
     * Updates the realm under the given path.
     *
     * @param realmTO realm to be stored
     * @return Response object featuring the updated realm enriched with propagation status information
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name = RESTHeaders.PREFER, paramType = "header", dataType = "string",
                value = "Allows the client to specify a preference for the result to be returned from the server",
                defaultValue = "return-content", allowableValues = "return-content, return-no-content",
                allowEmptyValue = true)
        , @ApiImplicitParam(name = RESTHeaders.NULL_PRIORITY_ASYNC, paramType = "header", dataType = "boolean",
                value = "If 'true', instructs the propagation process not to wait for completion when communicating"
                + " with External Resources with no priority set",
                defaultValue = "false", allowEmptyValue = true) })
    @ApiResponses({
        @ApiResponse(code = 200,
                message = "Realm successfully updated enriched with propagation status information, as Entity",
                response = ProvisioningResult.class)
        , @ApiResponse(code = 204,
                message = "No content if 'Prefer: return-no-content' was specified", responseHeaders =
                @ResponseHeader(name = RESTHeaders.PREFERENCE_APPLIED, response = String.class,
                        description = "Allows the server to inform the "
                        + "client about the fact that a specified preference was applied")) })
    @PUT
    @Path("{fullPath:.*}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response update(@NotNull RealmTO realmTO);

    /**
     * Deletes the realm under the given path.
     *
     * @param fullPath realm path
     * @return Response object featuring the deleted realm enriched with propagation status information
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name = RESTHeaders.PREFER, paramType = "header", dataType = "string",
                value = "Allows the client to specify a preference for the result to be returned from the server",
                defaultValue = "return-content", allowableValues = "return-content, return-no-content",
                allowEmptyValue = true)
        , @ApiImplicitParam(name = RESTHeaders.NULL_PRIORITY_ASYNC, paramType = "header", dataType = "boolean",
                value = "If 'true', instructs the propagation process not to wait for completion when communicating"
                + " with External Resources with no priority set",
                defaultValue = "false", allowEmptyValue = true) })
    @ApiResponses({
        @ApiResponse(code = 200,
                message = "Realm successfully deleted enriched with propagation status information, as Entity",
                response = ProvisioningResult.class)
        , @ApiResponse(code = 204,
                message = "No content if 'Prefer: return-no-content' was specified", responseHeaders =
                @ResponseHeader(name = RESTHeaders.PREFERENCE_APPLIED, response = String.class,
                        description = "Allows the server to inform the "
                        + "client about the fact that a specified preference was applied")) })
    @DELETE
    @Path("{fullPath:.*}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response delete(@NotNull @PathParam("fullPath") String fullPath);
}