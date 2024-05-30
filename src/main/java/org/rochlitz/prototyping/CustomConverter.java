package org.rochlitz.prototyping;

import org.apache.camel.dataformat.bindy.Format;

public class CustomConverter implements Format<String> {
    @Override
    public String format(String object) throws Exception {
        return (new StringBuffer(object)).reverse().toString();
    }

    @Override
    public String parse(String string) throws Exception {
        return (new StringBuffer(string)).reverse().toString();
    }
}