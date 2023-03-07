package com.maximvs.vknewsclient

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maximvs.vknewsclient.domain.FeedPost
import com.maximvs.vknewsclient.domain.StatisticItem

class MainViewModel: ViewModel() {  // Создание ViewModel, описание конспект 4.5

    /* private val _feedPost = MutableLiveData(FeedPost())  // Ранее объект назывался _feedPost, с конспект 4.9:  _feedPosts
    val feedPost : LiveData<FeedPost> = _feedPosts */    // 2 строки поменялись (см.далее), конспект 4.9

    private val sourseList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }
    private val _feedPosts = MutableLiveData<List<FeedPost>>(sourseList)  //  Конспект 4.9
    val feedPosts : LiveData<List<FeedPost>> = _feedPosts                 //  Конспект 4.9

    @RequiresApi(Build.VERSION_CODES.N)  // Аннотация добавляется по желанию Студии, не знаю, зачем
    fun updateCount(feedPost: FeedPost, item: StatisticItem) { // Изменение, конспект 4.9
        val oldPosts = feedPosts.value?.toMutableList() ?: mutableListOf() //  Можно бросить исключение, особой разницы нет: throw IllegalStateException()
        // val oldStatistics = feedPosts.value?.statistics ?: throw IllegalStateException()  // После переноса добавлена проверка на null
        val oldStatistics = feedPost.statistics                // Теперь так, Конспект 4.9
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->           // См.конспект 4.4, ближе к концу. Полное описание - Блок А
                if (oldItem.type == item.type) { // Если ТИП (VIEWS и т.д.) старого элемента равен ТИПУ нового элемента, то...
                    oldItem.copy(count = oldItem.count + 1)//...этот элемент нужно заменить: у oldItem вызвать copy и увеличить count: count = oldItem.count + 1
                } else {// Иначе - оставляем старый
                    oldItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)// В переменную statistics кладу новую коллекцию, Конспект 4.9
        // _feedPosts.value = feedPosts.value?.copy(statistics = newStatistics) // Объект value может быть null, потому добавляю знак вопроса(?)
        _feedPosts.value = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }  // Описание работы метода - Конспект 4.9
            }
        }// Cтрока _feedPosts.value = feedPosts.value?.copy(statistics = newStatistics) изменена, см. Конспект 4.9
    }

    fun remove(feedPost: FeedPost) {// Добавляю метод удаления элементов из коллекции, Конспект 4.9
        val oldPosts = feedPosts.value?.toMutableList() ?: mutableListOf()
        oldPosts.remove(feedPost)
        _feedPosts.value = oldPosts

    }
    // Вью-модель готова, есть объект, на который можно подписаться (val feedPost : LiveData<FeedPost> = _feedPost)
    // и есть метод обновления количества элементов fun updateCount (перенесен из PostCard, описание - конспект 4.4, Блок А)

}