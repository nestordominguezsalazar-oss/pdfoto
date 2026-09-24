package com.pdffoto.domain.support

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FeedbackDiagnosticsTest {

    @Test
    fun `incluye versión, Android y dispositivo`() {
        val text = buildFeedbackDiagnostics(
            versionName = "1.2.0",
            versionCode = 3,
            androidRelease = "15",
            sdkInt = 35,
            manufacturer = "Google",
            model = "Pixel 7",
        )

        assertThat(text).contains("App version: 1.2.0 (3)")
        assertThat(text).contains("Android: 15 (API 35)")
        assertThat(text).contains("Device: Google Pixel 7")
    }
}
