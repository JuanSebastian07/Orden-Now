package com.example.ordernow.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ordernow.domain.model.ItemsRestaurant
import com.example.ordernow.presentation.home.HomeState

// Modifica AllRestaurants para permitir paginación pero sin usar LazyColumn
@Composable
fun AllRestaurants(state: HomeState, onLoadMore: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Restaurantes",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isLoading && state.restaurants.isEmpty()) {
            // Mostrar el efecto shimmer cuando carga inicialmente
            repeat(3) {
                ShimmerRestaurantItem()
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            // Mostrar los restaurantes
            state.restaurants.forEach { restaurant ->
                RestaurantItem(restaurant = restaurant)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Indicador de carga al final
            if (state.isLoadingMoreRestaurants) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.canLoadMoreRestaurants) {
                Button(
                    onClick = onLoadMore,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Cargar más restaurantes")
                }
            }
        }
    }
}

@Composable
fun RestaurantItem(restaurant: ItemsRestaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(restaurant.ImagePath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

