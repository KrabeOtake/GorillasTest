package com.example.jetsnack.robots

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.jetsnack.ui.MainActivity

fun myCartRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    func: MyCartRobot.() -> Unit
) = MyCartRobot(composeTestRule).apply(func)

class MyCartRobot
constructor(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
)
{

    private val homeTabItem by lazy {
        composeTestRule.onNodeWithText("HOME")
    }

    private val myCartTabItem by lazy {
        composeTestRule.onNodeWithText("MY CART")
    }

    fun clickMyCart() = myCartTabItem.assertIsDisplayed().performClick()

    fun waitForIdle() = composeTestRule.waitForIdle()

    fun assertSnackItem(count : Int, title : String) {
        composeTestRule.onNode(
            hasText(count.toString()) and hasAnySibling(hasText(title)),
            useUnmergedTree = true
        ).assertIsDisplayed()
    }

    fun decreaseSnackCount(title : String) {
        composeTestRule.onNode(
            hasContentDescription("Decrease") and hasAnyAncestor(
                hasText(title)
            )
        ).performClick()
    }

    fun assertTotalPrice(totalPrice : String) {
        composeTestRule.onNodeWithTag("Total amount").assertTextEquals(totalPrice)
    }

    fun assertSnackItemDoesNotExist(title : String) {
        composeTestRule.onNode(hasText(title)).assertDoesNotExist()
    }

    fun removeSnackItem(title: String) {
        composeTestRule.onNode(
            hasContentDescription("Remove item") and hasAnyAncestor(
                hasText(title)
            )
        ).performClick()
    }

    fun scrollDownToSuggestionList() {
        composeTestRule.onNode(hasText("Total")).onSiblings().filter(hasScrollToIndexAction()).onFirst().performScrollTo()
    }

    fun scrollSuggestionListToIndex(index : Int) {
        composeTestRule.onNode(hasText("Total")).onSiblings().filter(hasScrollToIndexAction()).onFirst().performScrollToIndex(index)
    }

    fun clickSnackItem(title: String) {
        composeTestRule.onNodeWithText(title).performClick()
    }

    fun swipeToDeleteSnackItem(title: String) {
        composeTestRule.onNodeWithText(title).performTouchInput { swipeLeft() }
    }

    fun assertNodeWithText(text : String) {
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

    fun clickCheckout() {
        composeTestRule.onNodeWithText("Checkout").performClick()
    }

    infix fun detailsScreen( func: DetailsRobot.() -> Unit): DetailsRobot {
        return detailsRobot(composeTestRule, func = func)
    }

}
