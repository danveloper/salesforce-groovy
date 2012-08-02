package com.danveloper.salesforce.scripts

import com.danveloper.salesforce.config.ApplicationContext
import com.danveloper.salesforce.services.SalesforceService
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

/**
 * Application initialization class. Logs in to salesforce and gets the instanceUrl and accessToken for this session
 *
 * @author dwoods
 * @date 8/1/12
 */
class BootStrap {
    static HTTPBuilder http
    static Class configClass

    /**
     * Setups up the initial application context and logs in to salesforce service
     * @param none
     * @return void
     */
    static void init() {
        applicationContext(configClass ?: com.danveloper.salesforce.config.Config)
        salesforce(http ?: new HTTPBuilder(ApplicationContext.INSTANCE.config.salesforce.loginUrl))

        ApplicationContext.INSTANCE.salesforceService = new SalesforceService()
    }

    private static void applicationContext(configClass) {
        ConfigObject config = new ConfigSlurper().parse(configClass)
        ApplicationContext.INSTANCE.config = config
    }

    private static void salesforce(HTTPBuilder http) {
        def ctx = ApplicationContext.INSTANCE

        http.request( Method.POST, ContentType.JSON ) { req ->
            uri.path = ctx.config.salesforce.serviceUri
            uri.query = [
                    grant_type: ctx.config.salesforce.grantType,
                    client_id: ctx.config.salesforce.clientId,
                    client_secret: ctx.config.salesforce.clientSecret,
                    username: ctx.config.salesforce.username,
                    password: "${ctx.config.salesforce.password}${ctx.config.salesforce.clientToken}"
            ]
            headers.Accept = "application/json"

            response.success = { resp, reader ->
                ctx.instanceUrl = reader.instance_url
                ctx.accessToken = reader.access_token
            }

            response.failure = { resp, reader ->
                throw new GroovyRuntimeException("[login] failed ${resp.statusLine}")
            }
        }
    }
}
