package org.ukrida.hmifukridamobile.data.repository

import org.ukrida.hmifukridamobile.data.dummy.DummyData
import org.ukrida.hmifukridamobile.data.model.Event

class EventRepository {

    fun getAllEvent(): List<Event> {

        return DummyData.eventList

    }

}