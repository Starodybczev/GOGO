package com.example.prac_five.components



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.prac_five.R
import com.example.prac_five.constElems.marking.AppRow
import com.example.prac_five.fireBase.viewmodel.UserViewModel
import com.example.prac_five.googlemap.components.MapTypeMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.maps.android.compose.MapType

@Composable
fun Header(navController: NavHostController, mapType: MapType, onMapTypeChange: (MapType) -> Unit) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val viewModel: UserViewModel = viewModel()
    val user by viewModel.user.collectAsState()

    LaunchedEffect(uid) {
        if (uid != null) {
            viewModel.loadUser(uid)
        }
    }

    AppRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF00693E)) // Фон теперь заполняет всё пространство
            .statusBarsPadding()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentRoute != "home") {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }




        user?.let { Text(text = " ${user!!.name}") }
        Spacer(modifier = Modifier.width(16.dp))
        AsyncImage(
            model = user?.photoURL,
            contentDescription = "User Photo",
            placeholder = painterResource(R.drawable.basic_denied_reject), // Покажи что-то пока грузится
            error = painterResource(R.drawable.basic_denied_reject),       // Покажи если ссылка битая
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )

        MapTypeMenu(
            currentMapType = mapType,
            onMapTypeChange = onMapTypeChange
        )
    }
}
