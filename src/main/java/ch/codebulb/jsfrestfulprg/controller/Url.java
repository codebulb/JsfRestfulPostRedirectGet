package ch.codebulb.jsfrestfulprg.controller;

import java.util.ArrayList;
import java.util.List;

public class Url {
    private final String outcome;
    private boolean redirect;
    private final List<Parameter> parameters = new ArrayList<>();

    public Url(String outcome) {
        this(outcome, false);
    }

    public Url(String outcome, boolean redirect) {
        this.outcome = outcome;
        this.redirect = redirect;
    }

    public Url redirect() {
        this.redirect = true;
        return this;
    }

    public Url param(String name, Object value) {
        parameters.add(new Parameter(name, value.toString()));
        return this;
    }

    public String navigateTo() {
        if (!redirect && parameters.size() == 0) {
            // allows to return null
            return outcome;
        }
        if (outcome == null) {
            // ignore redirect and parameters
            return outcome;
        }

        StringBuilder ret = new StringBuilder(outcome);
        char operator = '?';
        if (redirect) {
            ret.append(operator);
            ret.append("faces-redirect=true");
            operator = '&';
        }
        for (Parameter parameter : parameters) {
            ret.append(operator);
            ret.append(parameter.name);
            ret.append('=');
            ret.append(parameter.value);
            operator = '&';
        }

        return ret.toString();
    }
    
    protected static class Parameter {
        private final String name, value;

        public Parameter(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}