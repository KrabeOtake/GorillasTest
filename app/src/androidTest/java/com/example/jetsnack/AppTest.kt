/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetsnack

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.jetsnack.ui.JetsnackApp
import com.example.jetsnack.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.regex.Pattern.matches

class AppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun app_launches() {
        // Check app launches at the correct destination
        composeTestRule.onNodeWithText("HOME").assertIsDisplayed()
        composeTestRule.onNodeWithText("Android's picks").assertIsDisplayed()
    }

    @Test
    fun testOpenCartScreen() {
        composeTestRule.onNodeWithText("MY CART").performClick().assertIsDisplayed()
    }

    @Test
    fun testDecreaseOneSnack() {
        composeTestRule.onNodeWithText("MY CART").performClick().assertIsDisplayed()
//        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.onNode(hasText("3") and hasAnySibling(hasText("Ice Cream Sandwich")), useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNode(hasContentDescription("Decrease") and hasAnyAncestor(
            hasText("Ice Cream Sandwich")
        )).performClick()
        composeTestRule.onNode(hasText("2") and hasAnySibling(hasText("Ice Cream Sandwich")), useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun testDecreaseAllOfSnack() {
        composeTestRule.onNodeWithText("MY CART").performClick().assertIsDisplayed()
//        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.onNode(hasText("Ice Cream Sandwich")).assertIsDisplayed()
        composeTestRule.onNodeWithTag("Total amount").assertTextEquals("\$58.13")
        for (i in 1..3) {
            composeTestRule.onNode(
                hasContentDescription("Decrease") and hasAnyAncestor(
                    hasText("Ice Cream Sandwich")
                )
            ).performClick()
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.onNodeWithTag("Total amount").assertTextEquals("\$19.16")
    }
}
