package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.viewmodel.SimulationResult
import com.example.viewmodel.SimulatorParams
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.math.pow

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun readStringFromContext() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Ascend Pro", appName)
  }

  @Test
  fun testCompoundInterestLogic() {
    val initial = 10000.0
    val monthly = 500.0
    val rate = 0.08 / 12.0
    val months = 120.0 // 10 years

    val fvInitial = initial * (1.0 + rate).pow(months)
    val fvAnnuity = monthly * (((1.0 + rate).pow(months) - 1.0) / rate)
    val finalCapital = fvInitial + fvAnnuity

    assertTrue(finalCapital > initial + (monthly * months))
  }
}
