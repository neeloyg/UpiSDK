package com.upi.sdk.converters;

import com.upi.sdk.errors.ConversionException;

/**
 * Created by SwapanP on 26-04-2016.
 */
public interface Converter<I,O> {
    public O convert(I in) throws ConversionException;
}
