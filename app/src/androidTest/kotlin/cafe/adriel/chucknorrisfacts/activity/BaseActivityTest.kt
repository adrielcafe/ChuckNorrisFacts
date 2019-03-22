package cafe.adriel.chucknorrisfacts.activity

import android.app.Activity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import cafe.adriel.chucknorrisfacts.rule.DisableAnimationsRule
import com.pacoworks.rxpaper2.RxPaperBook
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
    val disableAnimationsRule = DisableAnimationsRule()
    @get:Rule
    val clearPreferencesRule = ClearPreferencesRule()
    @get:Rule
    val clearDatabaseRule = ClearDatabaseRule()
    @get:Rule
    val clearFilesRule = ClearFilesRule()

    @Before
    open fun setUp(){
        // Clear local DB
        RxPaperBook.with().destroy().blockingAwait()
    }

    @After
    open fun tearDown(){
    }

}