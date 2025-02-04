package com.example.hirfa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hirfa.ui.theme.HirfaTheme
import com.example.hirfa.view.MainScreen
import com.example.hirfa.viewmodel.CategoryViewModel
import com.example.hirfa.viewmodel.CraftsmanViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HirfaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val categoryViewModel = viewModel<CategoryViewModel>()
                    val craftsmanViewModel = viewModel<CraftsmanViewModel>()
                    MainScreen(
                        categoryViewModel,
                        craftsmanViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

