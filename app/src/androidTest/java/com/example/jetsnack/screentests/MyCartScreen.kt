package com.example.jetsnack.screentests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.jetsnack.robots.homeRobot
import com.example.jetsnack.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class MyCartScreen {
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
            scrollDown()
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
            scrollDown()
            waitForIdle()
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
}