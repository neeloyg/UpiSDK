package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.ConfigElement;
import com.upi.sdk.domain.InputConfigParam;
import com.upi.sdk.errors.ConversionException;

/**
 * Created by NeeloyG on 5/19/2016.
 */
public class ConfigParamConverter implements Converter<InputConfigParam, ConfigElement> {

    @Override
    public ConfigElement convert(InputConfigParam in) throws ConversionException {
        ConfigElement configElement = new ConfigElement();
        return configElement;
    }
}
