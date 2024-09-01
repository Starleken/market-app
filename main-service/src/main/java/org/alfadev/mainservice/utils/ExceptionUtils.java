package org.alfadev.mainservice.utils;

import org.alfadev.mainservice.exception.EntityNotFoundException;
import org.alfadev.mainservice.exception.Error;
import org.alfadev.mainservice.exception.IllegalActionException;

public abstract class ExceptionUtils {

    public static EntityNotFoundException getEntityNotFoundException(Class clazz, Long id) {
        return new EntityNotFoundException(clazz.getSimpleName() + "by id: " + id + " not found");
    }

    public static void throwIllegalActionException(String message) {
        throw new IllegalActionException(message);
    }

    public static Error toError(Exception ex) {
        Error error = new Error();
        error.getErrors().add(ex.getMessage());

        return error;
    }
}