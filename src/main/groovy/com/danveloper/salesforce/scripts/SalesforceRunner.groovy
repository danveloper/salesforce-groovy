package com.danveloper.salesforce.scripts

import com.danveloper.salesforce.config.ApplicationContext

/**
 * Example class of how we might use something like this in a stand-alone context
 *
 * @author dwoods
 * @date 8/1/12
 */
class SalesforceRunner {
    public static void main(String[] args) {
        BootStrap.init()

        def ctx = ApplicationContext.INSTANCE
        def salesforceService = ctx.salesforceService

        def accounts = salesforceService.loadAccounts()

        accounts.each { account ->
            println account."Name"
        }
    }
}
