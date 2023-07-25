package com.example.breakingnews.ui.composeviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryListView(
    items: List<String>,
    onItemSelected: (String) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { item ->
            CategoryItemView(
                item = item,
                onItemSelected = {
                    onItemSelected(it)
                }
            )
        }
    }
}

@Composable
fun CategoryItemView(
    item: String,
    onItemSelected: (String) -> Unit,
) {
    var isSelected by remember { mutableStateOf(false) }

    val textStyle = TextStyle(
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        fontSize = if (isSelected) 20.sp else 16.sp,
        textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None
    )

    Surface(
        modifier = Modifier
            .clickable {
                if (!isSelected) onItemSelected(item) else onItemSelected("")
                isSelected = !isSelected
            }
    ) {
        Text(
            text = item,
            style = textStyle,
            color = if (isSelected) Color.Blue else Color.Gray
        )
    }
}
