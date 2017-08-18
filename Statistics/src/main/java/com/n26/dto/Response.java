package com.n26.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * This class which holds response for each service call, this is a wrapper which holds status code and data for each service
 * response. 
 * 
 * */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Response {

   
        /**
         * Contains HTTP response Status Code.
         * */
        private String code;

        /**
         * This contains the Response body, in case of "error" or "fail" statuses, this is empty.
         * */
        private Object data;


        /**
         * Parameterized Constructor.
         * 
         * @param code
         * @param data
         * */
        public Response(String code,Object data) {
            this.code = code;
            this.data = data;
        }

        /**
         * Parameterized Constructor, in case of recording transaction.
         * 
         * @param code
         * */
        public Response(String code) {
            this.code = code;
        }

        /**
         * @return {@link String}
         * */
        public String getCode() {
            return code;
        }

        /**
         * @return {@link Object}
         * */
        public Object getData() {
            return data;
        }


        @Override
        public String toString() {
            return "Response [code=" + code  + ",data=" + data.toString() + "]";
        }

}

