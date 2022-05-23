package com.example.jetsnack.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.jetsnack.ui.MainActivity

fun homeRobot(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    func: HomeRobot.() -> Unit
) = HomeRobot(composeTestRule).apply(func)

class HomeRobot
constructor(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
)
{
    private val myCartTabItem by lazy {
        composeTestRule.onNodeWithText("MY CART")
    }

    fun clickMyCart() = myCartTabItem.assertIsDisplayed().performClick()

    fun clickSnackItem(title: String) {
        composeTestRule.onNodeWithText(title).performClick()
    }

    infix fun myCartScreen( func: MyCartRobot.() -> Unit): MyCartRobot {
        return myCartRobot(composeTestRule, func = func)
    }

    infix fun detailsScreen( func: DetailsRobot.() -> Unit): DetailsRobot {
        return detailsRobot(composeTestRule, func = func)
    }

}