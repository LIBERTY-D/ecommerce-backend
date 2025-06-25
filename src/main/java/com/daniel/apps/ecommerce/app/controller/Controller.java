package com.daniel.apps.ecommerce.app.controller;

import com.daniel.apps.ecommerce.app.http.HttpResponse;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * A generic controller interface defining standard CRUD operations.
 *
 * @param <T>  The input type (usually a request DTO used for creating or updating an entity).
 * @param <D>  The output type (usually a response DTO used to send data back to the client).
 * @param <ID> The type of the entity's identifier (e.g., Long, UUID, String).
 */
public interface Controller<T, D, ID> {

    /**
     * Retrieves all resources.
     *
     * @return a {@link ResponseEntity} containing a {@link HttpResponse} wrapping a collection of response DTOs.
     */
    ResponseEntity<HttpResponse<Collection<D>>> findAll();

    /**
     * Retrieves a single resource by its ID.
     *
     * @param id the ID of the resource to retrieve.
     * @return a {@link ResponseEntity} containing a {@link HttpResponse} wrapping the resource's response DTO.
     */
    ResponseEntity<HttpResponse<D>> findOne(@PathVariable("id") ID id);

    /**
     * Deletes a single resource by its ID.
     *
     * @param id the ID of the resource to delete.
     * @return a {@link ResponseEntity} containing a {@link HttpResponse} wrapping the deleted resource or confirmation.
     */
    ResponseEntity<HttpResponse<D>> deleteOne(@PathVariable("id") ID id);

    /**
     * Updates an existing resource.
     *
     * @param id            the ID of the resource to update.
     * @param updatedEntity the updated data for the resource, typically passed as a request DTO.
     * @return a {@link ResponseEntity} containing a {@link HttpResponse} wrapping the updated resource.
     */
    ResponseEntity<HttpResponse<D>> updateOne(@PathVariable("id") ID id,
                                                @Valid @RequestBody T updatedEntity);

    /**
     * Creates a new resource.
     *
     * @param newEntity the data for the new resource, typically passed as a request DTO.
     * @return a {@link ResponseEntity} containing a {@link HttpResponse} wrapping the created resource.
     */
    ResponseEntity<HttpResponse<D>> createOne(@Valid @RequestBody T newEntity);
}
