package es.gob.radarcovid.common.base.events

import com.squareup.otto.Bus
import com.squareup.otto.ThreadEnforcer

object BUS {

    private val bus = Bus(ThreadEnforcer.MAIN)

    fun register(listener: Any) {
        bus.register(listener)
    }

    fun unregister(listener: Any) {
        bus.unregister(listener)
    }

    fun post(event: Any) {
        bus.post(event)
    }

}