package com.example.jetsnack.screentests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.jetsnack.robots.homeRobot
import com.example.jetsnack.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class DetailsScreen {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

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
            scrollDown()
            assertNodeWithText("SEE MORE")
            assertSeeLessDoesNotExist()
            clickSeeMore()
            assertSeeMoreDoesNotExist()
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