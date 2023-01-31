package com.maximvs.vknewsclient.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maximvs.vknewsclient.R
import com.maximvs.vknewsclient.ui.theme.VkNewsClientTheme


@Composable
fun PostCard() {
    Card() {
        Row(
            modifier = Modifier //добавляю атрибут modifier, чтобы установить размер
                .padding(8.dp)  // отступ со всех сторон
                .fillMaxWidth(), // сделал во всю ширину экрана
            verticalAlignment = Alignment.CenterVertically // каждый элемент в Row будет расположен по центру по вертикали
        ) {
            Image(
                modifier = Modifier // добавляю атрибут modifier, чтобы установить размеры и вид
                    .size(50.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.icons_tt),
                contentDescription = "image1"
            )
            Spacer( // Элемент Spacer - делает зазор, здесь - между Image и Column теперь 8dp
                modifier = Modifier
                    .width(8.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f) // аргумент веса, занимает все пространство, не занятое
                // другими элементами, здесь так: занимает весь центр, прижав крайние элементы к краям
            ) {
                Text(
                    text = "Будет потом",
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer( // Здесь Элемент Spacer делает зазор между Text и Text - 4dp
                    modifier = Modifier
                        .width(4.dp)
                )
                Text(
                    text = "14:00",
                    color = MaterialTheme.colors.onSecondary
                )
            }
            Icon(
                imageVector = Icons.Rounded.MoreVert, // Иконку подтягивать в проект не надо, просто прописать так
                contentDescription = "image2",
                tint = MaterialTheme.colors.onSecondary
            )


        }

    }
}

@Preview
@Composable
fun PreviewCardLight() {
    VkNewsClientTheme(
        darkTheme = false
    ) {
        PostCard()
    }
}

@Preview
@Composable
fun PreviewCardDark() {
    VkNewsClientTheme(
        darkTheme = true
    ) {
        PostCard()
    }
}