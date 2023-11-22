package com.example.chall3

import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
class TestRunner(testClass: Class<*>) : RobolectricTestRunner(testClass)