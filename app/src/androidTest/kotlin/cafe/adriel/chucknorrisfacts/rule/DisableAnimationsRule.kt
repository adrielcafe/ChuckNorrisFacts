package cafe.adriel.chucknorrisfacts.rule

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

// Based on https://gist.github.com/blipinsk/782c0abab52b61e14fc78045aa375d72/
class DisableAnimationsRule : TestRule {

    override fun apply(base: Statement?, description: Description?) = object : Statement() {
        override fun evaluate() {
            disableAnimations()
            try {
                base?.evaluate()
            } finally {
                enableAnimations()
            }
        }
    }

    private fun disableAnimations() {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).apply {
            executeShellCommand("settings put global transition_animation_scale 0")
            executeShellCommand("settings put global window_animation_scale 0")
            executeShellCommand("settings put global animator_duration_scale 0")
        }
    }

    private fun enableAnimations() {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).apply {
            executeShellCommand("settings put global transition_animation_scale 1")
            executeShellCommand("settings put global window_animation_scale 1")
            executeShellCommand("settings put global animator_duration_scale 1")
        }
    }
}
