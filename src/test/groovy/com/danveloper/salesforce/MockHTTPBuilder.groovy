package com.danveloper.salesforce

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.apache.http.client.ClientProtocolException

/**
 * A MockHTTPBuilder class specific for Salesforce calls
 *
 * @author dwoods
 * @date 8/2/12
 */
class MockHTTPBuilder extends HTTPBuilder {
    // This is a mock response that we expect to get back from Salesforce
    def data = [done: true, records: []]

    @Override
    public Object request( Method method, Object contentType, Closure configClosure )
    throws ClientProtocolException, IOException {
        return this.doRequest( new URI(""), method,
                contentType, configClosure );
    }

    @Override
    protected Object doRequest( URI uri, Method method, Object contentType, Closure configClosure )
    throws ClientProtocolException, IOException {
        def request = new Request()
        configClosure.resolveStrategy = Closure.DELEGATE_FIRST
        configClosure.delegate = request
        configClosure()
        doRequest(request)
    }

    def doRequest(Request req) {
        req.response.success("", data)
    }

}

/**
 * This is a mock request class for the http-builder package, and is needed since their project didn't include one.
 */
class Request {
    def uri = [:]
    def headers = [:]
    def response = [:]
}