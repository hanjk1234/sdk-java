package com.incountry.rest.client.api

import com.google.common.reflect.TypeToken
import com.incountry.storage.dto.MidPopData
import spock.lang.IgnoreIf
import spock.lang.Specification

@IgnoreIf({ true })
//TODO integration tests
class AbstractRestClientSpecification extends Specification {

    def "testGet"() {
        given:
        def client = new AbstractRestClient() {
            def testGet() {
                return getAndParse('https://us.staging.incountry.io/v2/storage/records/us/201e5a2194145a780aeef762deea82aa/',
                        new TypeToken<MidPopData>() {
                        }.getType(), null, null)
            }
        }
        when:
        def result = client.testGet()
        then:
        //thrown(Exception)
        result == ""

    }

    def "testGet1"() {
        given:
        def client = new AbstractRestClient() {
            def testGet() {
                return getAndParse('https://ru.api.incountry.io/v2/storage/records/ru/201e5a2194145a780aeef762deea82aa',
                        new TypeToken<MidPopData>() {
                        }.getType(), ['x-zone-id': 'zone_55', 'Authorization': 'Bearer 81f491a2d53a4c729f069663b5880bb9'], null)
            }
        }
        when:
        def result = client.testGet()
        then:
        //thrown(Exception)
        result == ""

    }
}
