package com.danveloper.salesforce.services

import com.danveloper.salesforce.config.ApplicationContext
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

/**
 * Service class for interfacing with salesforce
 *
 * @author dwoods
 * @date 8/1/12
 */
class SalesforceService {
    static def ctx = ApplicationContext.INSTANCE
    final HTTPBuilder httpBuilder

    SalesforceService() {
        this(new HTTPBuilder(ctx.instanceUrl))
    }

    SalesforceService(HTTPBuilder httpBuilder) {
        this.httpBuilder = httpBuilder
    }

    private def restCall(sql) {
        def queryUri = ctx.config.salesforce.queryUri
        def result = []
        def done = false
        while (!done) {
            httpBuilder.request(Method.GET, ContentType.JSON) { req ->
                uri.path = queryUri
                uri.query = [q: sql]
                headers.'Authorization' =  "OAuth ${ctx.accessToken}"

                // we have to do this shit because salesforce returns results in 2000 piece increments
                response.success = { resp, json ->
                    result << json.records
                    done = json.done
                    if (!done) {
                        queryUri = json.nextRecordsUrl
                    }
                }
            }
        }
        result.flatten()
    }

    /**
     * Pull back the Account objects from the Salesforce SQL statement in the config
     * @return Map [Column: Value]
     */
    def loadAccounts() {
        restCall(ctx.config.salesforce.sql.accounts)
    }

}
