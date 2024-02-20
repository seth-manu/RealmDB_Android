package com.coder.realmdatabase.models

import io.realm.kotlin.types.EmbeddedRealmObject

// Teacher 1-to-1 Address
// Teacher 1-to-many Courses
// Students many-to-many Courses

class Address : EmbeddedRealmObject {
    var fullName: String = ""
    var street: String = ""
    var houseNumber: String = ""
    var zip: Int = 0
    var city: String = ""
    var teacher: Teacher? = null
}