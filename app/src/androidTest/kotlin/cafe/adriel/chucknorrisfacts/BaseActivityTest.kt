package cafe.adriel.chucknorrisfacts

import android.app.Activity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class BaseActivityTest<T : Activity> {

    @get:Rule
    abstract val activityRule: ActivityScenarioRule<T>

    @get:Rule
    val clearPreferencesRule = ClearPreferencesRule()
    @get:Rule
    val clearDatabaseRule = ClearDatabaseRule()
    @get:Rule
    val clearFilesRule = ClearFilesRule()

    @Before
    open fun setUp(){
    }

    @After
    open fun tearDown(){
    }

}