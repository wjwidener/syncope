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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.syncope.common.lib.to.CamelMetrics;
import org.apache.syncope.common.lib.to.CamelRouteTO;
import org.apache.syncope.common.lib.types.AnyTypeKind;

/**
 * REST operations for Camel routes.
 */
@Api(tags = "CamelRoutes", authorizations = {
    @Authorization(value = "BasicAuthentication")
    , @Authorization(value = "Bearer") })
@Path("camelRoutes")
public interface CamelRouteService extends JAXRSService {

    /**
     * List all routes for the given any type kind.
     *
     * @param anyTypeKind any type kind
     * @return all routes for the given any type kind
     */
    @GET
    @Path("{anyTypeKind}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    List<CamelRouteTO> list(@NotNull @PathParam("anyTypeKind") AnyTypeKind anyTypeKind);

    /**
     * Read the route with the given key.
     *
     * @param anyTypeKind any type kind
     * @param key route key
     * @return route with given key
     */
    @GET
    @Path("{anyTypeKind}/{key}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    CamelRouteTO read(
            @NotNull @PathParam("anyTypeKind") AnyTypeKind anyTypeKind,
            @NotNull @PathParam("key") String key);

    /**
     * Update the given route.
     *
     * @param anyTypeKind any type kind
     * @param route to be updated
     */
    @ApiResponses(
            @ApiResponse(code = 204, message = "Operation was successful"))
    @PUT
    @Path("{anyTypeKind}/{key}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    void update(@NotNull @PathParam("anyTypeKind") AnyTypeKind anyTypeKind, @NotNull CamelRouteTO route);

    /**
     * Restart the underlying Apache Camel context.
     */
    @ApiResponses(
            @ApiResponse(code = 204, message = "Operation was successful"))
    @POST
    @Path("restartContext")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    void restartContext();

    /**
     * Provides Camel metrics.
     *
     * @return Camel metrics
     */
    @GET
    @Path("metrics")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    CamelMetrics metrics();
}