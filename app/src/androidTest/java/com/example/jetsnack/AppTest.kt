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

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.jetsnack.robots.homeRobot
import com.example.jetsnack.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class AppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testOpenCartScreen() {
        homeRobot(composeTestRule) {
            clickMyCart()
        } myCartScreen {
            clickMyCart()
        }
    }

    @Test
    fun testDecreaseOneSnack() {
        homeRobot(composeTestRule) {
            clickMyCart()
        } myCartScreen {
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
        homeRobot(composeTestRule) {
            clickMyCart()
        } myCartScreen {
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
        homeRobot(composeTestRule) {
            clickMyCart()
        } myCartScreen {
            assertSnackItem(3, "Ice Cream Sandwich")
            removeSnackItem("Ice Cream Sandwich")
            assertSnackItemDoesNotExist("Ice Cream Sandwich")
        }
    }

    @Test
    fun testTapSuggestions() {
        homeRobot(composeTestRule) {
            clickMyCart()
        } myCartScreen {
            scrollDownToSuggestionList()
            clickSnackItem("Cupcake")
        } detailsScreen {
            assertScreenIsDisplayed()
        }
    }

    @Test
    fun testSwipeSnack() {
        homeRobot(composeTestRule) {
            clickMyCart()
        } myCartScreen {
            assertSnackItem(3, "Ice Cream Sandwich")
            swipeToDeleteSnackItem("Ice Cream Sandwich")
            waitForIdle()
            assertSnackItemDoesNotExist("Ice Cream Sandwich")
        }
    }

    @Test
    fun testSwipeSuggestions() {
        homeRobot(composeTestRule) {
            clickMyCart()
        } myCartScreen {
            scrollDownToSuggestionList()
            scrollSuggestionListToIndex(10)
            waitForIdle()
            assertNodeWithText("Oreo")
        }
    }

    @Test
    fun testTapCheckout() {
        homeRobot(composeTestRule) {
            clickMyCart()
        } myCartScreen {
            clickCheckout()
        }
    }

    //DETAILS SCREEN

    @Test
    fun testSnackDetailsScreen() {
        homeRobot(composeTestRule) {
            clickSnackItem("Pretzels")
        } detailsScreen {
            waitForIdle()
            assertScreenIsDisplayed()
        }
    }

    @Test
    fun testExpandDescription() {
        homeRobot(composeTestRule) {
            clickSnackItem("Pretzels")
        } detailsScreen {
            waitForIdle()
            assertNodeWithText("SEE MORE")
            assertSeeLessDoesNotExist()
            clickSeeMore()
            assertSeeMoreDoesNotExist()
            scrollDownToSuggestionList()
            assertNodeWithText("SEE LESS")
//            composeTestRule.onRoot().printToLog("currentLabelExists")
        }
    }

    @Test
    fun testChangeQuantity() {
        homeRobot(composeTestRule) {
            clickSnackItem("Pretzels")
        } detailsScreen {
            waitForIdle()
            assertQuantity("1")
            clickIncrease()
            assertQuantity("2")
            clickDecrease()
            clickDecrease()
            assertQuantity("0")
            clickDecrease()
            assertQuantity("0")
        }
    }
    @Test
    fun testClickAddToCart() {
        homeRobot(composeTestRule) {
            clickSnackItem("Pretzels")
        } detailsScreen {
            waitForIdle()
            clickAddToCart()
        }
    }
}
