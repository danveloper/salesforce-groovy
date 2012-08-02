package com.danveloper.salesforce

/**
 * TestConfig
 *
 * @author dwoods
 * @date 8/2/12
 */
salesforce {
    loginUrl = 'https://login.salesforce.com'
    serviceUri = '/services/oauth2/token'
    queryUri = '/services/data/v20.0/query'
    grantType = 'password'
    clientId = 'CLIENT_ID'
    clientSecret = 'CLIENT_SECRET'
    clientToken = 'CLIENT_TOKEN'
    username = 'USERNAME'
    password = 'PASSWORD'
    sql = [
            accounts: "Select Name, Site from Account"
    ]
}
