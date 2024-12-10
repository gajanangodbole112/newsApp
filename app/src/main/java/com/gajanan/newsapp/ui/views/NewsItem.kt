package com.gajanan.newsapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gajanan.newsapp.domain.models.Article
import com.gajanan.newsapp.ui.theme.Orange

@Composable
fun NewsItem(
    article: Article,
    showDeleteButton: Boolean = false,
    deleteButtonClick:() -> Unit,
    readMoreInvoke: () -> Unit
) {
    ElevatedCard(
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .requiredHeight(200.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.LightGray),
                model = article.urlToImage,
                contentDescription = "",
                clipToBounds = false,
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = article.title ?: "No Title",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = article.description ?: "No Description",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.secondary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    colors = ButtonColors(
                        containerColor = Orange,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(5.dp),
                    onClick = {
                        readMoreInvoke()
                    }) {
                    Text(text = "Read more")
                }
                if (showDeleteButton) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                deleteButtonClick()
                            },
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}