package com.petrs.smartlab

import com.petrs.smartlab.ui.fragments.start.onboarding.models.OnboardingData
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getFromQueue_isCorrect() {
        val testList = listOf(
            OnboardingData(
                title = "Анализы",
                description = "Экспресс сбор и получение проб",
                image = R.drawable.ic_onboarding_image_1
            ),
            OnboardingData(
                title = "Уведомления",
                description = "Вы быстро узнаете о результатах",
                image = R.drawable.ic_onboarding_image_2
            ),
            OnboardingData(
                title = "Мониторинг",
                description = "Наши врачи всегда наблюдают \nза вашими показателями здоровья",
                image = R.drawable.ic_onboarding_image_3
            )
        )

        val testFirst = OnboardingData(
            title = "Анализы",
            description = "Экспресс сбор и получение проб",
            image = R.drawable.ic_onboarding_image_1
        )

        val testSecond = OnboardingData(
            title = "Уведомления",
            description = "Вы быстро узнаете о результатах",
            image = R.drawable.ic_onboarding_image_2
        )

        val testThird = OnboardingData(
            title = "Мониторинг",
            description = "Наши врачи всегда наблюдают \nза вашими показателями здоровья",
            image = R.drawable.ic_onboarding_image_3
        )

        for (i in 0..testList.lastIndex) {
            when (i) {
                0 -> assertEquals(testList[i], testFirst)
                1 -> assertEquals(testList[i], testSecond)
                2 -> assertEquals(testList[i], testThird)
            }
        }
    }

    @Test
    fun subOneAfterGetFromQueue_isCorrect() {
        val testList = arrayListOf(
            OnboardingData(
                title = "Анализы",
                description = "Экспресс сбор и получение проб",
                image = R.drawable.ic_onboarding_image_1
            ),
            OnboardingData(
                title = "Уведомления",
                description = "Вы быстро узнаете о результатах",
                image = R.drawable.ic_onboarding_image_2
            ),
            OnboardingData(
                title = "Мониторинг",
                description = "Наши врачи всегда наблюдают \nза вашими показателями здоровья",
                image = R.drawable.ic_onboarding_image_3
            )
        )

        for (i in 0..testList.lastIndex) {
            val data = testList[0]
            testList.remove(data)
            assertEquals(2-i, testList.size)
        }
    }
}