package com.maximvs.vknewsclient.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maximvs.vknewsclient.MainViewModel
import com.maximvs.vknewsclient.domain.FeedPost
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MainScreen(viewModel: MainViewModel) {  // Теперь в качестве параметра принимает viewModel, а именно - MainViewModel

    /* val feedPost = remember {       // Это все переписывается, т.к. теперь используется вьюмодель,
           mutableStateOf(FeedPost())  //  и переносится туда, где используется - к PostCard
    } */

    /*  // Три переменных для FAB и SnackBar, видео 3.4
    val snackbarHostState = remember {  // создаю State для SnackbarHost(hostState = )
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val fabIsVisible = remember {
        mutableStateOf(true)
    } */

    Scaffold(
        /* snackbarHost = {      //  FAB и SnackBar, видео 3.4
            SnackbarHost(hostState = snackbarHostState) // кладу/применяю State snackbarHostState
        },
        floatingActionButton = { // создаю FAB с иконкой
            if (fabIsVisible.value) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            val action = snackbarHostState.showSnackbar(
                                message = "Oooooo!!",
                                actionLabel = "Hide FAB",
                                duration = SnackbarDuration.Long
                            )
                            if (action == SnackbarResult.ActionPerformed) {
                                fabIsVisible.value = false
                            }
                        }
                    }
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }
            }
        }, */
        bottomBar = {  //  создаю BottomBar
            BottomNavigation {

                val selectedItemPosition = remember {
                    mutableStateOf(0)
                }

                val items = listOf(  //  коллекция элементов, которые нужно отобразить
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        selected = selectedItemPosition.value == index,
                        onClick = { selectedItemPosition.value = index },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }

    ) {
        val feedPosts = viewModel.feedPosts.observeAsState(listOf()) // Перенес туда, где используется; Конспект 4.9 - переименовал feedPost в feedPosts-
                                                                     // конспект 4.5, добавил значение по умолчанию (FeedPost()), чтобы не обрабатывать возможный null


        LazyColumn (
            contentPadding = PaddingValues(   //  Конспект 4.9
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 72.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
                ){
            items(
                items = feedPosts.value,
                key = { it.id }
            ) { feedPost ->
                val dismissState = rememberDismissState() // Для свайпа, Конспект 4.9
                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.remove(feedPost)
                }
                SwipeToDismiss(  // Для свайпа, Конспект 4.9
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    background = {},
                    directions = setOf(DismissDirection.EndToStart) // Если это не сделать, элемент пропадет при свайпе и вправо тоже, но останется пустое поле
                ) {
                    PostCard(
                        feedPost = feedPost,  // Было feedPost = feedPost.value, теперь так, Конспект 4.9

                        /* Этот onStatisticItemClickListener (который ниже) меняю на 4 других, 4.5, Блок Б
                        onStatisticItemClickListener = { // После перехода на вью-модель название параметра более не требуется
                            viewModel.updateCount(it) // После создания вью-модели вызываю ее здесь
                            }
                         */

                        // Вместо  onStatisticItemClickListener теперь 4 других, каждый на свой тип, Конспект 4.5, Блок Б.
                        onViewsClickListener = { statisticItem -> // Конспект 4.9
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onLikeClickListener = { statisticItem -> // Конспект 4.9
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onShareClickListener = { statisticItem -> // Конспект 4.9
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onCommentClickListener = { statisticItem -> // Конспект 4.9
                            viewModel.updateCount(feedPost, statisticItem)
                        }
                    )
                }
            }
        }

        // Все, что ниже - перенес во вью-модель, здесь уже не нужно
        /*  { newItem ->  // Заменил название, для понимания
        val oldStatistics = feedPost.value.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->           // См.конспект 4.4, ближе к концу. Полное описание - Блок А
                if (oldItem.type == newItem.type) { // Если ТИП (VIEWS и т.д.) старого элемента равен ТИПУ нового элемента, то...
                    oldItem.copy(count = oldItem.count + 1)//...этот элемент нужно заменить: у oldItem вызвать copy и увеличить count: count = oldItem.count + 1
                } else {// Иначе - оставляем старый
                    oldItem
                }
            }
        }
        feedPost.value = feedPost.value.copy(statistics = newStatistics)
         // См.конспект 4.4, ближе к концу. Полное описание - Блок А
        */

    }
}