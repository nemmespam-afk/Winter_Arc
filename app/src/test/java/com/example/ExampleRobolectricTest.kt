package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.DayRecord
import com.example.model.ChallengeCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Winter Arc", appName)
    }

    @Test
    fun `verify all 10 winter arc challenges are configured`() {
        assertEquals(10, ChallengeCatalog.challenges.size)
        assertEquals("WATER ONLY", ChallengeCatalog.challenges[0].title)
        assertEquals("NO JUNK FOOD", ChallengeCatalog.challenges[1].title)
        assertEquals("7-8 HOURS OF SLEEP", ChallengeCatalog.challenges[2].title)
        assertEquals("GYM 4X PER WEEK", ChallengeCatalog.challenges[3].title)
        assertEquals("RUN 1X PER WEEK", ChallengeCatalog.challenges[4].title)
        assertEquals("10K STEPS DAILY", ChallengeCatalog.challenges[5].title)
        assertEquals("NO PHONE DURING MEALS", ChallengeCatalog.challenges[6].title)
        assertEquals("20 MIN OUTSIDE DAILY", ChallengeCatalog.challenges[7].title)
        assertEquals("BE GRATEFUL DAILY", ChallengeCatalog.challenges[8].title)
        assertEquals("TRACK 5 DAILY WINS", ChallengeCatalog.challenges[9].title)
    }

    @Test
    fun `verify day record percentage calculation`() {
        val emptyRecord = DayRecord(date = "2026-10-01")
        assertEquals(0, emptyRecord.percentage)
        assertEquals(0, emptyRecord.completedCount)

        val halfRecord = emptyRecord.copy(
            waterOnly = true,
            noJunkFood = true,
            sleepHours = true,
            gymSession = true,
            runSession = true
        )
        assertEquals(5, halfRecord.completedCount)
        assertEquals(50, halfRecord.percentage)

        val fullRecord = halfRecord.copy(
            tenKSteps = true,
            noPhoneMeals = true,
            outsideMinutes = true,
            beGrateful = true,
            fiveWins = true
        )
        assertEquals(10, fullRecord.completedCount)
        assertEquals(100, fullRecord.percentage)
    }
}
