package com.pdfoto.domain.pdf

import com.google.common.truth.Truth.assertThat
import java.util.TimeZone
import org.junit.Test

class DateFormatterTest {

    @Test
    fun `formatea la fecha en dd MM yyyy HH mm`() {
        // 1700000000000 ms = 2023-11-14T22:13:20Z
        assertThat(formatTimestamp(1_700_000_000_000L, TimeZone.getTimeZone("UTC")))
            .isEqualTo("14/11/2023 22:13")
    }
}
