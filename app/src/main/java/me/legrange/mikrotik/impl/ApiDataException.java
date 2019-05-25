package me.legrange.mikrotik.impl;

import me.legrange.mikrotik.MikrotikApiException;

/**
 * Thrown if there is a problem unpacking data from the Api. 
 * @author GideonLeGrange
 */
public class ApiDataException extends MikrotikApiException {

    ApiDataException(String msg) {
        super(msg);
    }

    ApiDataException(String msg, Throwable err) {
        super(msg, err);
    }

    
    
}
