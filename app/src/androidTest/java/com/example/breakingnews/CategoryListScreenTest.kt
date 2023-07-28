package com.example.breakingnews

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.breakingnews.ui.composeviews.CategoryListView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testOnItemSelectedCallback() {
        var selectedItem: String? = null
        val items = listOf("Category 1", "Category 2", "Category 3")

        composeTestRule.setContent {
            CategoryListView(items = items) {
                selectedItem = it
            }
        }

        val targetItem = items.first()
        composeTestRule.onNodeWithText(targetItem).performClick()
        assert(selectedItem == targetItem)

        val newItem = items.last()
        composeTestRule.onNodeWithText(newItem).performClick()
        assert(selectedItem == newItem)

        // to check if item is unselected when we click again the same item
        composeTestRule.onNodeWithText(newItem).performClick()
        assert(selectedItem == "")
    }
}
