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
)
{


}