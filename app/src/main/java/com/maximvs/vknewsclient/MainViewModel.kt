package com.maximvs.vknewsclient

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maximvs.vknewsclient.domain.FeedPost
import com.maximvs.vknewsclient.domain.StatisticItem

class MainViewModel: ViewModel() {  // Создание ViewModel, описание конспект 4.5

    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost : LiveData<FeedPost> = _feedPost

    @RequiresApi(Build.VERSION_CODES.N)  // Аннотация добавляется по желанию Студии, не знаю, зачем
    fun updateCount(item: StatisticItem) {
        val oldStatistics = feedPost.value?.statistics ?: throw IllegalStateException()  // После переноса добавлена проверка на null
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->           // См.конспект 4.4, ближе к концу. Полное описание - Блок А
                if (oldItem.type == item.type) { // Если ТИП (VIEWS и т.д.) старого элемента равен ТИПУ нового элемента, то...
                    oldItem.copy(count = oldItem.count + 1)//...этот элемент нужно заменить: у oldItem вызвать copy и увеличить count: count = oldItem.count + 1
                } else {// Иначе - оставляем старый
                    oldItem
                }
            }
        }
        _feedPost.value = feedPost.value?.copy(statistics = newStatistics) // Объект value может быть null, потому добавляю знак вопроса(?)
    }
    // Вью-модель готова, есть объект, на который можно подписаться (val feedPost : LiveData<FeedPost> = _feedPost)
    // и есть метод обновления количества элементов fun updateCount (перенесен из PostCard, описание - конспект 4.4, Блок А)

}