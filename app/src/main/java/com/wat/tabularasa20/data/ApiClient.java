package com.wat.tabularasa20.data;

@com.amazonaws.mobileconnectors.apigateway.annotation.Service(endpoint = "https://6ztwzizmp2.execute-api.eu-west-1.amazonaws.com/v1")
public interface ApiClient {

    /**
     * A generic invoker to invoke any API Gateway endpoint.
     * @param request
     * @return ApiResponse
     */
    com.amazonaws.mobileconnectors.apigateway.ApiResponse execute(com.amazonaws.mobileconnectors.apigateway.ApiRequest request);

    /**
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/hello-world", method = "GET")
    Constants helloWorldGet();

}
