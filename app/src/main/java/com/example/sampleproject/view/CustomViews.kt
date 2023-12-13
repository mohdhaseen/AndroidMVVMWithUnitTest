package com.example.sampleproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sampleproject.R
import com.example.sampleproject.model.Result

object CustomViews {
    @Composable
    fun Conversation(messages: List<Result>) {
        LazyColumn {
            itemsIndexed(messages) { index, message ->
                ChatItem(message, index < messages.size - 1)
            }
        }
    }

    @Composable
    fun ChatItem(msg: Result, showDivider: Boolean = false) {
        Column() {
            Row(
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "this is image",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(1.dp, color = Color.Blue, shape = CircleShape)
                )
                Spacer(
                    modifier = Modifier
                        .size(5.dp)
                )
                var isExpanded by remember { mutableStateOf(false) }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = !isExpanded }) {
                    Text(
                        text = msg.title,
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        text = msg.abstract,
                        style = MaterialTheme.typography.body2,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                    Text(
                        text = msg.updated,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(1.dp)
                            .align(Alignment.End),
                    )
                }
            }
            if (showDivider) {
                Divider(
                    color = Color.White, modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                )
            }
        }

    }
}