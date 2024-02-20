package com.coder.realmdatabase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.realmdatabase.RealmApp
import com.coder.realmdatabase.models.Address
import com.coder.realmdatabase.models.Course
import com.coder.realmdatabase.models.Student
import com.coder.realmdatabase.models.Teacher
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel : ViewModel() {

    private val realm = RealmApp.realm

    val courses = realm
        .query<Course>()
        .asFlow()
        .map { results ->
            results.list.toList()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    fun createSimpleEntries() {

        val address1 = Address().apply {
            fullName = "Prakash"
            street = "Jai Prakash Street"
            houseNumber = "12345"
            zip = 123
            city = "Jai Prakash City"
        }
        val address2 = Address().apply {
            fullName = "Raman Doe"
            street = "CV Raman Street"
            houseNumber = "12346"
            zip = 124
            city = "CV Raman City"
        }

        val course1 = Course().apply {
            name = "Data Structure and Algorithm"
        }
        val course2 = Course().apply {
            name = "Digital Electronics"
        }
        val course3 = Course().apply {
            name = "Programming Language"
        }

        val teacher1 = Teacher().apply {
            address = address1
            courseList = realmListOf(course1, course2)
        }

        val teacher2 = Teacher().apply {
            address = address2
            courseList = realmListOf(course3)
        }

        course1.teacher = teacher1
        course2.teacher = teacher1
        course3.teacher = teacher2

        address1.teacher = teacher1
        address2.teacher = teacher2

        val student1 = Student().apply {
            name = "Prakash Junior"
        }
        val student2 = Student().apply {
            name = "Raman Junior"
        }

        course1.enrolledStudent.add(student1)
        course2.enrolledStudent.add(student2)
        course3.enrolledStudent.addAll(listOf(student1, student2))

        realm.writeBlocking {
            copyToRealm(teacher1, UpdatePolicy.ALL)
            copyToRealm(teacher2, UpdatePolicy.ALL)

            copyToRealm(course1, UpdatePolicy.ALL)
            copyToRealm(course2, UpdatePolicy.ALL)
            copyToRealm(course3, UpdatePolicy.ALL)

            copyToRealm(student1, UpdatePolicy.ALL)
            copyToRealm(student2, UpdatePolicy.ALL)
        }
    }
}