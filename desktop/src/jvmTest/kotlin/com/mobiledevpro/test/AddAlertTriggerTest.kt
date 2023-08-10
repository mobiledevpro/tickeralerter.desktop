package com.mobiledevpro.test

import com.mobiledepro.main.domain.model.*
import com.mobiledevpro.alert.triggers.data.repository.ImplAlertTriggersRepository
import com.mobiledevpro.alert.triggers.di.featureAlertTriggersModule
import com.mobiledevpro.alert.triggers.domain.interactor.AlertTriggersInteractor
import com.mobiledevpro.alert.triggers.domain.interactor.ImplAlertTriggersInteractor
import com.mobiledevpro.database.AppDatabase
import com.mobiledevpro.di.databaseModule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import kotlin.test.*

class AddAlertTriggerTest : KoinTest {

    private lateinit var interactor: AlertTriggersInteractor
    private val fakeTrigger = AlertTrigger(
        timeCreated = null,
        symbol = "BTCUSDT",
        timeFrame = null,
        alertSettings = AlertSettings(
            conditionSource = ConditionSource.TICKER_PRICE,
            conditionType = ConditionType.CROSSING_UP,
            conditionTarget = ConditionTarget.PRICE,
            targetPrice = 30000.00
        )
    )

    @BeforeTest
    fun setup() {

        startKoin {
            modules(databaseModule + featureAlertTriggersModule)
        }

        val database = getKoin().get<AppDatabase>()
        val repository = ImplAlertTriggersRepository(database)
        interactor = ImplAlertTriggersInteractor(repository)
    }

    @Test
    fun testInsert() = runTest {
        interactor.saveTrigger(fakeTrigger)
    }

    @AfterTest
    fun testGet() = runTest {
        val list = interactor.getTriggersList().first()

        val alertTrigger = list.find { it.symbol == fakeTrigger.symbol }

        assertTrue(alertTrigger != null, "Alert Trigger is not found")
        assertTrue((alertTrigger.timeCreated ?: 0L) > 0L, "Time created is 0")
        assertEquals(
            ConditionSource.TICKER_PRICE,
            alertTrigger.alertSettings.conditionSource,
            "Condition source is incorrect"
        )
        assertEquals(
            fakeTrigger.alertSettings.conditionType,
            alertTrigger.alertSettings.conditionType,
            "Condition type is incorrect"
        )
        assertEquals(
            fakeTrigger.alertSettings.conditionTarget,
            alertTrigger.alertSettings.conditionTarget,
            "Condition target is incorrect"
        )
        assertTrue(alertTrigger.status != AlertStatus.ACTIVE, "Trigger status is not active")
        assertTrue((alertTrigger.alertSettings.targetPrice ?: 0.0) > 0.0, "Price is null or 0")
        assertEquals(
            fakeTrigger.alertSettings.targetPrice,
            alertTrigger.alertSettings.targetPrice,
            "Price is incorrect"
        )
        assertEquals(fakeTrigger.title(), alertTrigger.title(), "Title is incorrect")
    }
}