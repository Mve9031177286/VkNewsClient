package com.maximvs.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maximvs.vknewsclient.ui.PostCard
import com.maximvs.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {  // описание функции есть в записях, темная/светлая темы
                Box(  // Здесь обычно используентся Surface, этот элемент заполняет все пространство
                    // и устанавливает фон. Чтобы его не настраивать используем Box
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background) // сначала - фон, затем...
                        .padding(8.dp)  // ...отступ, если наоборот - будет белая рамка

                ) {
                    PostCard()
                }
            }
        }
    }
}
