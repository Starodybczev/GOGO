package com.example.prac_five.googlemap.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prac_five.constElems.AppButton
import com.example.prac_five.constElems.Input
import com.example.prac_five.constElems.marking.AppRow
import com.example.prac_five.fireBase.viewmodel.UserViewModel
import com.example.prac_five.types.MapMarker
import com.example.prac_five.types.User
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkerBottomSheet(
    marker: MapMarker,
    currentUser: User?,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String) -> Unit,
    onDelate: (String) -> Unit,
    favorites: List<String>
) {

    val scope = rememberCoroutineScope()
    val isFavorite = favorites.contains(marker.id)

    val isOwner = marker.userId == currentUser?.id

    val formattedDate = remember(marker.createdAt) {
        SimpleDateFormat(
            "dd MMM yyyy, HH:mm",
            Locale.getDefault()
        ).format(Date(marker.createdAt))
    }

    var newTitle by remember(marker.id) {
        mutableStateOf(marker.title)
    }

    var newDescription by remember(marker.id) {
        mutableStateOf(marker.description)
    }

    var newType by remember(marker.id) {
        mutableStateOf(marker.type)
    }

    val focusManager = LocalFocusManager.current

    val typeOptions = listOf(
        "UNIVERSITY",
        "CAFE",
        "SHOP",
        "MALL",
        "PARK",
        "HOTEL",
        "SCHOOL",
        "MAIL"
    )

    var expanded by remember { mutableStateOf(false) }
    val viewModel: UserViewModel = viewModel()


    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                onDismiss()
            }
        },
        sheetState = sheetState,
        modifier = Modifier
            .clickable(
                indication = null,
            interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {

                    // 1. Основной контент (как обычный поток)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = marker.nickname,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(end = 48.dp)
                        )

                        Spacer(Modifier.height(8.dp))
                        Text(text = "Тип: $newType")

                        Spacer(Modifier.height(8.dp))
                        Text(text = marker.description)

                        Spacer(Modifier.height(8.dp))
                        Text(text = formattedDate)
                    }

                    IconButton(
                        onClick = {
                            currentUser?.id?.let { userId ->
                                viewModel.toggleFavorite(userId, marker.id, isFavorite)
                            }
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = null,
                            tint = if (isFavorite) Color.Yellow else Color.Gray
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }



            if (isOwner) {

                Input(
                    value = newTitle,
                    onValueChange = { newTitle = it },
                    label = "Title Mark"
                )


                Input(
                    value = newDescription,
                    onValueChange = { newDescription = it },
                    label = "Enter description"
                )

                Spacer(Modifier.height(5.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {

                    TextField(
                        value = newType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,

                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,

                            focusedLabelColor = Color(0xFF2E7D32),
                            unfocusedLabelColor = Color.Gray,

                            cursorColor = Color(0xFF2E7D32),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .menuAnchor()
                        ,
                        shape = RoundedCornerShape(16.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {

                        typeOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option , color = Color.Black)},
                                onClick = {
                                    newType = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                AppRow(){
                    AppButton(
                        text = "Save",
                        onClick = {
                            onSave(
                                marker.id,
                                newTitle,
                                newDescription,
                                newType.uppercase()
                            )
                        }
                    )
                    AppButton(
                        text = "Delete",
                        onClick = {onDelate(marker.id)},
                        backgroundColor = Color.Red,
                        textColor = Color.White,
                        borderColor = Color.Red
                    )
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}
