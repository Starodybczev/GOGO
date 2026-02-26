package com.example.prac_five.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.prac_five.components.Header
import com.example.prac_five.constElems.Gap
import com.example.prac_five.fireBase.viewmodel.UserViewModel
import com.google.maps.android.compose.MapType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Favorites(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel()

    val markers by viewModel.markers.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val currentUser by viewModel.user.collectAsState()

    val favoriteMarkers = markers.filter {
        favorites.contains(it.id)
    }
    val vScroll = rememberScrollState()


    LaunchedEffect(currentUser?.id) {
        currentUser?.id?.let {
            viewModel.listenFavorites(it)
        }
    }
    var mapType by rememberSaveable { mutableStateOf(MapType.NORMAL) }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 320),
        topBar = { Header(navController, mapType = mapType , onMapTypeChange = {mapType = it}) },
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = if (favoriteMarkers.isEmpty()) Arrangement.Center else Arrangement.Top) {
            item { Gap() }
            item { Text("Favorites", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.headlineMedium) }
            item { Gap() }

            items(favoriteMarkers) { marker ->

                val isOwner = marker.userId == currentUser?.id
                val authorName = if (isOwner) "You" else marker.nickname

                val formattedDate = remember(marker.createdAt) {
                    SimpleDateFormat(
                        "dd MMM yyyy, HH:mm",
                        Locale.getDefault()
                    ).format(Date(marker.createdAt))
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Text(
                            text = authorName,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = marker.title,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(text = "Type: ${marker.type}")

                        Spacer(Modifier.height(4.dp))

                        Text(text = marker.description)

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

        }

    }
}