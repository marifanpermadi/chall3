package com.example.chall3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.chall3.utils.MainDispatcherRule
import com.example.chall3.utils.SettingPreferences
import com.example.chall3.viewmodel.SettingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(TestRunner::class)
class SettingViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()


    @Mock
    private lateinit var preferences: SettingPreferences
    private lateinit var settingViewModel: SettingViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        settingViewModel = SettingViewModel(preferences)
    }

    @Test
    fun `test getThemeSettings should return LiveData with correct value`() {

        runBlocking {
            val mockThemeSetting = true
            preferences.saveThemeSetting(mockThemeSetting)
            `when`(preferences.getThemeSetting()).thenReturn(flowOf(mockThemeSetting))

            val resultLiveData = settingViewModel.getThemeSettings()
            assertEquals(mockThemeSetting, getValue(resultLiveData))
        }
    }

    private suspend fun <T> getValue(liveData: LiveData<T>): T {
        var value: T? = null
        val observer = Observer<T> {
            value = it
        }

        withContext(Dispatchers.Main) {
            liveData.observeForever(observer)
        }

        return value!!
    }

    @Test
    fun `test saveThemeSetting should call preferences saveThemeSetting`() = runTest {
        val isDarkModeActive = true

        settingViewModel.saveThemeSetting(isDarkModeActive)

        verify(preferences).saveThemeSetting(isDarkModeActive)
    }

}