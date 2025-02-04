package com.example.hirfa.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hirfa.model.Category
import com.example.hirfa.model.Craftsman
import com.example.hirfa.viewmodel.CategoryViewModel
import com.example.hirfa.viewmodel.CraftsmanViewModel

@Composable
fun MainScreen(
    categoryViewModel: CategoryViewModel,
    craftsmanViewModel: CraftsmanViewModel,
    modifier: Modifier = Modifier
) {
    val categoryState by categoryViewModel.categoryState.collectAsState()
    val craftsmanState by craftsmanViewModel.uiState.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {

        // Display categories
        Text("Categories", style = MaterialTheme.typography.headlineMedium)
        CategoryList(categories = categoryState.categories)

        Spacer(modifier = Modifier.height(16.dp))

        // Display craftsmen
        Text("Craftsmen", style = MaterialTheme.typography.headlineMedium)
        when {
            craftsmanState.isLoading -> CircularProgressIndicator()
            craftsmanState.error != null -> Text("Error: ${craftsmanState.error}")
            else -> CraftsmanList(craftsmen = craftsmanState.craftsmen)
        }
    }
}

@Composable
fun CategoryList(categories: List<Category>) {
    LazyColumn {
        items(categories) { category ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = category.icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(category.name, style = MaterialTheme.typography.titleMedium)
                        Text(category.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}


@Composable
fun CraftsmanList(craftsmen: List<Craftsman>) {
    LazyColumn {
        items(craftsmen) { craftsman ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    AsyncImage(
                        model = craftsman.profilePicture,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(craftsman.name, style = MaterialTheme.typography.titleMedium)
                        Text(craftsman.description, style = MaterialTheme.typography.bodyMedium)
                        Text("Category: ${craftsman.category}", style = MaterialTheme.typography.bodySmall)
                        Text("Rating: ${craftsman.rating}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}