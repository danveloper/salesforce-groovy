package com.danveloper.salesforce.config

/**
 * The Salesforce REST & application configuration
 *
 * @author dwoods
 * @date 8/1/12
 */
salesforce {
    /**
     * loginUrl, serviceUri, queryUri, and grantType should never change
     */
    loginUrl = 'https://login.salesforce.com'
    serviceUri = '/services/oauth2/token'
    queryUri = '/services/data/v20.0/query'
    grantType = 'password'

    /**
     * For these properties, you will need to start by doing the following:
     * Login to Salesforce > Go up to your name in the top right > "Setup"
     */
    // "Setup" > "App Setup" > "Develop" > "Remote Access" > "Consumer Key"
    clientId = 'CLIENT_ID'

    // "Setup" > "App Setup" > "Develop" > "Remote Access" > "Consumer Secret"
    clientSecret = 'CLIENT_SECRET'

    // "Personal Setup" > "My Personal Information" > "Reset My Security Token"
    clientToken = 'SECURITY_TOKEN'

    // "Personal Setup" > "My Personal Information"
    username = 'USERNAME'

    // "Personal Setup" > "My Personal Information"
    password = 'PASSWORD'

    // Maybe store your salesforce sqls here? You don't have to... Put them whever you want.
    sql = [
            accounts: "Select Name, Site from Account"
    ]
}

