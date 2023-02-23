package com.maximvs.vknewsclient.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maximvs.vknewsclient.domain.FeedPost
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MainScreen() {

    val feedPost = remember {
        mutableStateOf(FeedPost())
    }
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
        PostCard(
            modifier = Modifier.padding(8.dp), //  В параметрах PostCard() при создании передан параметр modifier, поэтому могу его здесь использовать
            feedPost = feedPost.value,
            onStatisticItemClickListener = { newItem ->  // Заменил название, для понимания
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
            }
        )  // См.конспект 4.4, ближе к концу. Полное описание - Блок А
    }
}