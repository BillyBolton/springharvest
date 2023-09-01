package com.billybolton.errors.models;

import lombok.Builder;

import java.io.Serializable;

/**
 * This class is used to represent the exception detail.
 *
 * @param field   The field that caused the exception.
 * @param message The message that describes the exception relating to the field.
 */
@Builder
public record ExceptionDetail(String field, String message) implements Serializable {
}
