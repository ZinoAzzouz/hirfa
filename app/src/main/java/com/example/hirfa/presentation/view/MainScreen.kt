package com.example.hirfa.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hirfa.presentation.view.components.CarftsmenText
import com.example.hirfa.presentation.view.components.CategoryList
import com.example.hirfa.presentation.view.components.CraftsmanList
import com.example.hirfa.presentation.view.components.SearchView
import com.example.hirfa.presentation.view.components.ServiceText
import com.example.hirfa.presentation.view.components.SliderImages
import com.example.hirfa.presentation.view.components.Toolbar
import com.example.hirfa.presentation.viewmodel.CategoryViewModel
import com.example.hirfa.presentation.viewmodel.CraftsmanViewModel

@Composable
fun MainScreen(
    categoryViewModel: CategoryViewModel,
    craftsmanViewModel: CraftsmanViewModel,
    modifier: Modifier = Modifier
) {
    val categoryState by categoryViewModel.categoryState.collectAsState()
    val craftsmanState by craftsmanViewModel.uiState.collectAsState()

    Column(modifier = modifier.padding(10.dp)) {

        //Display toolbar
        Spacer(modifier = Modifier.width(40.dp))
        Toolbar()
        Spacer(modifier = Modifier.height(15.dp))
        // Display search view
        SearchView()
        Spacer(modifier = Modifier.height(15.dp))
        // Display categories
        ServiceText()
        CategoryList(categories = categoryState.categories)

        Spacer(modifier = Modifier.height(20.dp))
        //Slider Images
        SliderImages()
        Spacer(modifier = Modifier.height(10.dp))
        // Display craftsmen
        CarftsmenText()
       // Text("Craftsmen", style = MaterialTheme.typography.headlineMedium)
        when {
            craftsmanState.isLoading -> CircularProgressIndicator()
            craftsmanState.error != null -> Text("Error: ${craftsmanState.error}")
            else -> CraftsmanList(craftsmen = craftsmanState.craftsmen)
        }
    }
}











