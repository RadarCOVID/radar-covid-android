package es.gob.radarcovid

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented background_shape_exposition_low, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under background_shape_exposition_low.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.indra.contacttracing", appContext.packageName)
    }
}
