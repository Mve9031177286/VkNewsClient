package com.maximvs.vknewsclient.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maximvs.vknewsclient.R
import com.maximvs.vknewsclient.domain.FeedPost
import com.maximvs.vknewsclient.domain.StatisticItem
import com.maximvs.vknewsclient.domain.StatisticType
import com.maximvs.vknewsclient.ui.theme.VkNewsClientTheme


@Composable
fun PostCard(
    modifier: Modifier = Modifier, // Чтобы можно было добавлять паддинг и пр. при вызове PostCard из вне и...
    feedPost: FeedPost,  // Вторым параметром принимает feedPost и из него можно взять статистику:
    // StatisticsCard(statistics = feedPost.statistics) - см.ниже
    // onStatisticItemClickListener: (StatisticItem) -> Unit,  // См.конспект 4.4, ближе к концу.
                        // Конспект 4.5 - удаляю onItemClickListener, добавляю 4 других, см.ниже.

    onLikeClickListener: (StatisticItem) -> Unit,    // 4 ClickListener для отдельных действий
    onShareClickListener: (StatisticItem) -> Unit,   // кликам на каждый элемент статистики,
    onViewsClickListener: (StatisticItem) -> Unit,   // вместо onStatisticItemClickListener,
    onCommentClickListener: (StatisticItem) -> Unit  // см.конспект 4.5, Блок Б
) {
    Card(
        modifier = modifier  //  ...здесь - тоже. См.конспект, 4.4
    ) {
        Column(
            modifier = Modifier //добавляю атрибут modifier, чтобы установить размер
                .padding(8.dp)  // отступ со всех сторон
        ) {
            OneCard(feedPost) // Передаю объект feedPost
            Spacer( // Элемент Spacer - делает зазор
                modifier = Modifier
                    .height(8.dp)
            )
            Text(text = feedPost.contentText)  // Текст беру из feedPost
            Spacer( // Элемент Spacer - делает зазор
                modifier = Modifier
                    .height(8.dp)
            )
            Image(
                modifier = Modifier    //  добавляю атрибут modifier, чтобы установить размер
                    .fillMaxWidth()    //  сделал во всю ширину экрана
                    .height(200.dp),   //  высота картинки, см.4.4
                painter = painterResource(id = feedPost.contentImageResId),  // Картинку беру из feedPost
                contentDescription = "image2",
                contentScale = ContentScale.FillWidth // каринка растянется, сохраняя соотношение сторон
            )
            Spacer( // Элемент Spacer - делает зазор
                modifier = Modifier
                    .height(8.dp)
            )
            StatisticsCard(
                statistics = feedPost.statistics,
                onLikeClickListener = onLikeClickListener, // Передал 4 каллбэка, конспект 4.5, Блок Б
                onCommentClickListener = onCommentClickListener,
                onViewsClickListener = onViewsClickListener,
                onShareClickListener = onShareClickListener

                // onItemClickListener = onStatisticItemClickListener  // При вызове функции добавил слушатель, конспект 4.4, ближе к концу
            )   // 4.5 - убрал onItemClickListener = onStatisticItemClickListener, добавил 4 других.
        }
    }
}

@Composable
private fun StatisticsCard(  // функция для отображения нескольких fun IconCard, в качестве параметров принимает:
    statistics: List<StatisticItem>, // коллекцию элементов StatisticItem, назову statistics
    //  onItemClickListener: (StatisticItem) -> Unit    // См.конспект 4.4, ближе к концу.
                //  Конспект 4.5 - удаляю onItemClickListener, добавляю 4 других, см.ниже.

    onLikeClickListener: (StatisticItem) -> Unit,    // 4 ClickListener для отдельных действий
    onShareClickListener: (StatisticItem) -> Unit,   // кликам на каждый элемент статистики,
    onViewsClickListener: (StatisticItem) -> Unit,   // вместо onStatisticItemClickListener,
    onCommentClickListener: (StatisticItem) -> Unit  // см.конспект 4.5, Блок Б
) {
    Row() {
        Row(
            modifier = Modifier
                .weight(1f) // Если у двух элементов делать так - они займут одинаковое расстояние
        ) {
            val viewsItem =
                statistics.getItemByType(StatisticType.VIEWS) // Создаю переменную (val viewsItem) и в нее кладу полученный здесь же (методом getItemByType) нужный элемент коллекции (VIEWS)
            IconCard(
                iconId = R.drawable.ic_outline_remove_red_eye_24,
                text = viewsItem.count.toString(),  // берем полученный элемент(viewsItem), получаем количество(count) и приводим к типу стринг
                onItemClickListener = {             // См.конспект 4.4, ближе к концу
                    onViewsClickListener(viewsItem)  // См.конспект 4.4, ближе к концу.
                    // 4.5 - меняю onItemClickListener на onViewsItemClickListener
                }
            )
        }
        Row(
            modifier = Modifier
                .weight(1f), // Если у двух элементов делать так - они займут одинаковое расстояние
            horizontalArrangement = Arrangement.SpaceBetween // Равномерное распределение элементов
        ) {
            val sharesItem =
                statistics.getItemByType(StatisticType.SHARES) // Все так же, как у элемента VIEWS
            IconCard(
                iconId = R.drawable.ic_outline_reply_24,
                text = sharesItem.count.toString(),
                onItemClickListener = {             // См.конспект 4.4, ближе к концу
                    onShareClickListener(sharesItem)  // См.конспект 4.4, ближе к концу
                    // 4.5 - меняю onItemClickListener на onShareItemClickListener
                }
            )
            val commentItem =
                statistics.getItemByType(StatisticType.COMMENTS) // Все так же, как у элемента VIEWS
            IconCard(
                iconId = R.drawable.ic_outline_mode_comment_24,
                text = commentItem.count.toString(),
                onItemClickListener = {             // См.конспект 4.4, ближе к концу
                    onCommentClickListener(commentItem)  // См.конспект 4.4, ближе к концу
                    // 4.5 - меняю onItemClickListener на onCommentItemClickListener

                }
            )
            val likesItem =
                statistics.getItemByType(StatisticType.LICKES) // Все так же, как у элемента VIEWS
            IconCard(
                iconId = R.drawable.ic_baseline_favorite_border_24,
                text = likesItem.count.toString(),
                onItemClickListener = {             // См.конспект 4.4, ближе к концу
                    onLikeClickListener(likesItem)  // См.конспект 4.4, ближе к концу
                    // 4.5 - меняю onItemClickListener на onLikeItemClickListener
                }
            )
            // Icon(Icons.Default.Star,contentDescription = null) - Так выбирается иконка из предустановленных
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {  // extension fun, принимает StatisticType и возвращает StatisticItem
    return this.find { it.type == type }
        ?: throw IllegalStateException()// здесь прохожу по всем элементам коллекции, ищу элемент, тип которого совпадает с принятым функцией
}  // ищу элемент, тип которого совпадает с принятым функцией и добавляю элвис-оператор на случай, если объект нулевой, см.конспект
// Теперь есть метод, который в коллекции statistics: List<StatisticItem> может найти объект по его типу

@Composable
private fun IconCard(   // Функция для отображения иконки и текста, принимает...
    iconId: Int, // ...Id картинки и...
    text: String,// ...отображаемый текст
    onItemClickListener: () -> Unit  // Добавил слушатель (который здесь в Row), для пробрасывания наверх...
    // ...далее в функции StatisticsCard у каждого элемента повешу слушатель клика
) {
    Row(
        modifier = Modifier.clickable { // Элемент clickable, при клике на элемент Row будет вызван данный метод
            onItemClickListener() // Вызов проброшенного наверх слушателя
        },
        verticalAlignment = Alignment.CenterVertically // каждый элемент в Row будет расположен по центру по вертикали
    ) { // чтобы разместить рядом иконку с текстом
        Icon(
            painter = painterResource(id = iconId), // принимает iconId, строка 57
            contentDescription = "image3",
            tint = MaterialTheme.colors.onSecondary // меняю цвет иконки
        )
        Spacer( // Элемент Spacer - делает зазор
            modifier = Modifier
                .width(4.dp)
        )
        Text(     // принимает text, строка 58
            text = text,
            color = MaterialTheme.colors.onSecondary // меняю цвет текста
        )
    }
}


@Composable
private fun OneCard(
    feedPost: FeedPost  // Принимает нужный параметр: feedPost
) {
    Row(
        modifier = Modifier //добавляю атрибут modifier, чтобы установить размер
            .fillMaxWidth(), // сделал во всю ширину экрана
        verticalAlignment = Alignment.CenterVertically // каждый элемент в Row будет расположен по центру по вертикали
    ) {
        Image(
            modifier = Modifier // добавляю атрибут modifier, чтобы установить размеры и вид
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(id = feedPost.avatarResId),  // Из feedPost принимает аватарку
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
                text = feedPost.communityName, // Из feedPost принимает название группы
                color = MaterialTheme.colors.onPrimary
            )
            Spacer( // Здесь Элемент Spacer делает зазор между Text и Text - 4dp
                modifier = Modifier
                    .height(4.dp)
            )
            Text(
                text = feedPost.publicationDate, // Из feedPost принимает дату публикации
                color = MaterialTheme.colors.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert, // Именно здесь: иконку подтягивать в проект не надо, просто прописать так
            contentDescription = "image2",
            tint = MaterialTheme.colors.onSecondary  // меняю цвет иконки
        )
    }
}

