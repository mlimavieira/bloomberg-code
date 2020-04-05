package com.bloomberg.client.feign.operation.dto;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CoreServiceOperationRequest {

    private String operation;
    private List<Double> values;

    private CoreServiceOperationRequest(String operation, List<Double> values) {
        super();
        this.operation = operation;
        this.values = values;
    }

    public static class Builder {
        private String operation;
        private List<Double> values = new ArrayList<>();

        public Builder sum() {
            operation = "ADD";
            return this;
        }

        public Builder minus() {
            operation = "SUB";
            return this;
        }

        public Builder divide() {
            operation = "DIV";
            return this;
        }

        public Builder multiply() {
            operation = "MULT";
            return this;
        }

        public Builder addValues(List<Double> values) {
            this.values.addAll(values);
            return this;
        }

        public CoreServiceOperationRequest build() {

            validate();
            return new CoreServiceOperationRequest(operation, values);
        }

        private void validate() {
            if(StringUtils.isEmpty(operation)) {
                throw new ValidationException("Operation is required");
            }
            if(values.isEmpty() || values.size() < 2) {
                throw new ValidationException("List of values requires at least 2 numbers");
            }
        }
    }

}
