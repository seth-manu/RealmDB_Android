package com.coder.realmdatabase

import android.app.Application
import com.coder.realmdatabase.models.Address
import com.coder.realmdatabase.models.Course
import com.coder.realmdatabase.models.Student
import com.coder.realmdatabase.models.Teacher
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmApp : Application() {

    companion object {
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    Address::class,
                    Course::class,
                    Student::class,
                    Teacher::class
                )
            )
        )
    }
}