package com.example.ordernow.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordernow.presentation.home.components.AllRestaurants
import com.example.ordernow.presentation.home.components.BannerSection
import com.example.ordernow.presentation.home.components.CategorySection
import com.example.ordernow.presentation.home.components.SearchBar
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(modifier = modifier.fillMaxSize()) {
        // SearchBar fijo en la parte superior
        SearchBar()

        // Contenido scrolleable
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                BannerSection(state)
            }
            item {
                CategorySection(state)
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(end = 16.dp, start = 16.dp, top = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
            }
            item {
                AllRestaurants(
                    state = state,
                    onLoadMore = { viewModel.loadMoreRestaurants() }
                )
            }
        }
    }
}
