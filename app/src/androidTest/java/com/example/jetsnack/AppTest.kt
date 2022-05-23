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
import com.example.jetsnack.robots.myCartRobot
import com.example.jetsnack.ui.MainActivity
import org.junit.Rule
import org.junit.Test

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
        myCartRobot(composeTestRule) {
            clickMyCart()
        }
    }

    @Test
    fun testDecreaseOneSnack() {
        myCartRobot(composeTestRule) {
            clickMyCart()
            assertSnackItem(3, "Ice Cream Sandwich")
            decreaseSnackCount("Ice Cream Sandwich")
            assertSnackItem(2, "Ice Cream Sandwich")
        }

//        composeTestRule.onNodeWithText("MY CART").performClick()
//        composeTestRule.onRoot().printToLog("currentLabelExists")
//        composeTestRule.onNode(
//            hasText("3") and hasAnySibling(hasText("Ice Cream Sandwich")),
//            useUnmergedTree = true
//        ).assertIsDisplayed()
//        composeTestRule.onNode(
//            hasContentDescription("Decrease") and hasAnyAncestor(
//                hasText("Ice Cream Sandwich")
//            )
//        ).performClick()
//        composeTestRule.onNode(
//            hasText("10") and hasAnySibling(hasText("Ice Cream Sandwich")),
//            useUnmergedTree = true
//        ).assertIsDisplayed()
    }

    @Test
    fun testDecreaseAllOfSnack() {
        myCartRobot(composeTestRule) {
            clickMyCart()
            assertSnackItem(3, "Ice Cream Sandwich")
            assertTotalPrice("\$58.13")
            for (i in 1..3) {
                decreaseSnackCount("Ice Cream Sandwich")
            }
            assertSnackItemDoesNotExist("Ice Cream Sandwich")
            assertTotalPrice("\$19.16")
        }
    }

    @Test
    fun testRemoveSnack() {
        myCartRobot(composeTestRule) {
            clickMyCart()
            assertSnackItem(3, "Ice Cream Sandwich")
            removeSnackItem("Ice Cream Sandwich")
            assertSnackItemDoesNotExist("Ice Cream Sandwich")
        }
    }

    @Test
    fun testTapSuggestions() {
        myCartRobot(composeTestRule) {
            clickMyCart()
            scrollDownToSuggestionList()
            tapSuggestionItem("Cupcake")
        } detailsScreen {
            //TODO: assert DetailsScreen
        }
    }

    @Test
    fun testSwipeSnack() {
        myCartRobot(composeTestRule) {
            clickMyCart()
            assertSnackItem(3, "Ice Cream Sandwich")
            swipeToDeleteSnackItem("Ice Cream Sandwich")
            waitForIdle()
            assertSnackItemDoesNotExist("Ice Cream Sandwich")
        }
    }

    @Test
    fun testSwipeSuggestions() {
        myCartRobot(composeTestRule) {
            clickMyCart()
            scrollDownToSuggestionList()
            scrollSuggestionListToIndex(10)
            waitForIdle()
            assertNodeWithText("Oreo")
        }
    }

    @Test
    fun testTapCheckout() {
        myCartRobot(composeTestRule) {
            clickMyCart()
            clickCheckout()
        }
    }
}
