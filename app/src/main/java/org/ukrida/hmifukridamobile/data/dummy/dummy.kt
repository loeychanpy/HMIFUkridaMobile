package org.ukrida.hmifukridamobile.data.dummy

import org.ukrida.hmifukridamobile.R
import org.ukrida.hmifukridamobile.data.model.Event
import org.ukrida.hmifukridamobile.data.model.Participant

object DummyData {

    val eventList = listOf(

        Event(

            id = 1,

            title = "Seminar Artificial Intelligence",

            description = "Seminar mengenai perkembangan AI di Indonesia.",

            date = "20 Juli 2026",

            location = "Aula UKRIDA",

            image = R.drawable.ic_launcher_foreground

        ),

        Event(

            id = 2,

            title = "Workshop Android",

            description = "Belajar membuat aplikasi Android menggunakan Jetpack Compose.",

            date = "25 Juli 2026",

            location = "Lab 504",

            image = R.drawable.ic_launcher_foreground

        ),

        Event(

            id = 3,

            title = "Programming Competition",

            description = "Kompetisi coding antar mahasiswa.",

            date = "30 Juli 2026",

            location = "Gedung A",

            image = R.drawable.ic_launcher_foreground

        )

    )

    val registeredList = listOf(

        RegisteredEvent(
            id = 1,
            title = "Seminar Artificial Intelligence",
            date = "24 Juli 2026",
            location = "Auditorium UKRIDA",
            status = "Confirmed",
            image = R.drawable.ic_launcher_foreground
        ),

        RegisteredEvent(
            id = 2,
            title = "Workshop Android",
            date = "25 Juli 2026",
            location = "Lab 504",
            status = "Registered",
            image = R.drawable.ic_launcher_foreground
        ),

        RegisteredEvent(
            id = 3,
            title = "Programming Competition",
            date = "30 Juli 2026",
            location = "Gedung A",
            status = "Registered",
            image = R.drawable.ic_launcher_foreground
        )

    )

    val participantList = listOf(

        Participant(

            id = 1,

            name = "Claudio Jose",

            nim = "412024028",

            major = "Informatika",

            semester = "Semester 4",

            registerDate = "24 Juli 2026",

            status = "Waiting"

        ),

        Participant(

            id = 2,

            name = "Richard Devin",

            nim = "412024019",

            major = "Informatika",

            semester = "Semester 4",

            registerDate = "24 Juli 2026",

            status = "Approved"

        ),

        Participant(

            id = 3,

            name = "Josh Valentino",

            nim = "412024035",

            major = "Informatika",

            semester = "Semester 4",

            registerDate = "25 Juli 2026",

            status = "Waiting"

        )

    )

}

data class RegisteredEvent(

    val id:Int,

    val title:String,

    val date:String,

    val location:String,

    val status:String,

    val image:Int

)

