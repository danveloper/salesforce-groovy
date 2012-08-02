package com.danveloper.salesforce

import com.danveloper.salesforce.config.ApplicationContext
import com.danveloper.salesforce.scripts.BootStrap
import com.danveloper.salesforce.services.SalesforceService
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

/**
 * Spec for testing configuration, login, and query result construction
 *
 * @author dwoods
 * @date 8/1/12
 */
@RunWith(Sputnik)
class SalesforceSpec extends Specification {
    def setupSpec() {
        BootStrap.with {
            http = new MockHTTPBuilder(data: [access_token: "ABCDEF", instance_url: "http://test.salesforce.com"])
            configClass = TestConfig
            init()
        }
    }

    /**
     * We're testing that our TestConfig class is what's being taken as opposed to our real config.
     */
    def "Pseudo-Config"() {
        expect:
            assert ApplicationContext.INSTANCE.config.salesforce.username == "USERNAME"
    }

    /**
     * Test that the login process is setting the accessToken and instanceUrl in the ApplicationContext singleton
     */
    def "Login"() {
        expect:
            assert ApplicationContext.INSTANCE.accessToken == "ABCDEF"
            assert ApplicationContext.INSTANCE.instanceUrl == "http://test.salesforce.com"
    }

    /**
     * Test that the response from the SalesforceService#restCall(Object sql) is being constructed properly.
     * We omit a portion of the data that we get back from Salesforce because we don't need it, so let's make
     * sure that we're putting it together properly.
     */
    def "Query Construction"() {
        given:
            def salesforceService = new SalesforceService(new MockHTTPBuilder(data: [done: true, records: [Name: 'Dans Awesome', Site: '1234']]))
            def accounts = salesforceService.loadAccounts()

        expect:
            assert accounts[0]."Name" == "Dans Awesome"
            assert accounts[0]."Site" == "1234"
            assert !accounts[0].done
    }

}
