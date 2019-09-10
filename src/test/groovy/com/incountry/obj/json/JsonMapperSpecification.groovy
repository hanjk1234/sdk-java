package com.incountry.obj.json

import com.incountry.storage.dto.MidPopData
import groovy.json.JsonSlurper
import spock.lang.Specification
import spock.lang.Unroll

class JsonMapperSpecification extends Specification {

    @Unroll
    def "test serialization #I"() {

        expect:
        JsonParser.getInstance().readValue(str, MidPopData).equals(obj) == result

        where:
        I   | str                                                                                       | obj                                                                   | result
        "1" | '{"country":"us"}'                                                                        | MidPopData.builder().setCountry('us').build()                         | true
        "2" | '{"country":"us","profile_key":"pf-key"}'                                                 | MidPopData.builder().setCountry('us').setProfileKey('pf-key').build() | true
        "3" | '{"country":"us","profile_key":"pf-key"}'                                                 | MidPopData.builder().setCountry('us').setProfileKey('pF-key').build() | false

        "4" | '{"country":"us","profile_key":"pf-key","body":"body",' +
                '"range_key":"rk", "key":"key", "key2":"key2","key3":"key3"}' \
                           | MidPopData.builder().setCountry('us').setProfileKey('pf-key').setBody('body')
                .setKey('key').setKey2('key2').setKey3('key3').setRangeKey('rk').build()                                                                                        | true
    }
}
