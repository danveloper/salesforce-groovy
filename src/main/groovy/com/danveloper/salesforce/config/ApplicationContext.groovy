package com.danveloper.salesforce.config

import com.danveloper.salesforce.services.SalesforceService

/**
 * A singleton that holds properties pertinent to the the application
 *
 * @author dwoods
 * @date 8/1/12
 */
enum ApplicationContext {
    INSTANCE

    ConfigObject config
    String instanceUrl
    String accessToken
    SalesforceService salesforceService
}
