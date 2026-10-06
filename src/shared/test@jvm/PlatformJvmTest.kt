package com.swe.studyplanner

import kotlin.test.Test
import kotlin.test.assertTrue

class PlatformJvmTest {
    @Test
    fun `platform name is reported`() {
        assertTrue(getPlatform().name.isNotBlank())
    }
}
