package com.example.jetsnack.robots

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.jetsnack.ui.MainActivity

fun detailsRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    func: DetailsRobot.() -> Unit
) = DetailsRobot(composeTestRule).apply(func)

class DetailsRobot
constructor(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    private val details =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut tempus, sem vitae convallis imperdiet, lectus nunc pharetra diam, ac rhoncus quam eros eu risus. Nulla pulvinar condimentum erat, pulvinar tempus turpis blandit ut. Etiam sed ipsum sed lacus eleifend hendrerit eu quis quam. Etiam ligula eros, finibus vestibulum tortor ac, ultrices accumsan dolor. Vivamus vel nisl a libero lobortis posuere. Aenean facilisis nibh vel ultrices bibendum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse ac est vitae lacus commodo efficitur at ut massa. Etiam vestibulum sit amet sapien sed varius. Aliquam non ipsum imperdiet, pulvinar enim nec, mollis risus. Fusce id tincidunt nisl."

    fun waitForIdle() = composeTestRule.waitForIdle()

    fun assertNodeWithText(text: String) {
        composeTestRule.onAllNodesWithText(text).assertAll(hasText(text))
    }

    fun assertButtonIsDisplayed(text: String) {
        composeTestRule.onNode(hasContentDescription(text)).assertIsDisplayed()
    }

    fun scrollDownToSuggestionList() {
        composeTestRule.onNode(hasScrollAction() and !(hasScrollToIndexAction()))
            .performScrollToNode(
                hasText("Smoothies")
            )
    }

    fun assertScreenIsDisplayed() {
        assertNodeWithText("Details")
        assertNodeWithText(details)
        assertNodeWithText("SEE MORE")
        assertNodeWithText("Ingredients")
        assertNodeWithText("Vanilla, Almond Flour, Eggs, Butter, Cream, Sugar")
        scrollDownToSuggestionList()
        assertNodeWithText("Customers also bought")
        assertNodeWithText("Popular on Jetsnack")
        assertNodeWithText("Cupcake")
        assertNodeWithText("Donut")
        assertNodeWithText("Eclair")
        assertNodeWithText("Chips")
        assertNodeWithText("Smoothies")
        assertNodeWithText("Qty")
        assertNodeWithText("1")
        assertNodeWithText("ADD TO CART")
        assertButtonIsDisplayed("Decrease")
        assertButtonIsDisplayed("Increase")
    }

    fun clickSeeMore() {
        composeTestRule.onNodeWithText("SEE MORE").performClick()
    }

    fun clickSeeLess() {
        composeTestRule.onNodeWithText("SEE MORE").performClick()
    }

    fun assertSeeMoreDoesNotExist() {
        composeTestRule.onNodeWithText("SEE MORE").assertDoesNotExist()
    }

    fun assertSeeLessDoesNotExist() {
        composeTestRule.onNodeWithText("SEE LESS").assertDoesNotExist()
    }

    fun clickIncrease() {
        composeTestRule.onNode(hasContentDescription("Increase")).performClick()
    }

    fun clickDecrease() {
        composeTestRule.onNode(hasContentDescription("Decrease")).performClick()
    }

    fun assertQuantity(quantity: String) {
        composeTestRule.onNodeWithText("Qty").onSiblings().filterToOne(hasText(quantity))
            .assertIsDisplayed()
    }

    fun clickAddToCart() {
        composeTestRule.onNodeWithText("ADD TO CART").performClick()
    }
}