package com.smartnotes.ai.Screens.youtube

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.db.YouTubeNoteModel
import com.smartnotes.ai.ui.theme.Lufga
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val DarkText = Color(0xFF242529)

data class UrlFolderData(
    val title: String,
    val details: String = "08/01/26   Links",
    val backgroundColor: Color,
    val iconBackgroundColor: Color = Color(0xFFFAF4FF)
)

@Composable
fun UrlFilesScreen(
    onBackClick: () -> Unit = {},
    onNewLinksClick: () -> Unit = {},
    onFolderClick: (String) -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var ytNotesList by remember { mutableStateOf<List<YouTubeNoteModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        if (!isPreview && dbHelper != null) {
            ytNotesList = dbHelper.getAllYouTubeNotes()
        }
    }

    val folderGroups = ytNotesList.groupBy { it.folderName }
    val currentDate = remember { SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date()) }
    val pastelColors = listOf(
        Color(0xFFF2E2FE) to Color(0xFFF0EDFF),
        Color(0xFFC9F0EE) to Color(0xFFE4F9F6),
        Color(0xFFFFF3D3) to Color(0xFFFFF9E5),
        Color(0xFFFFE1CE) to Color(0xFFFFEEE0)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // BACKGROUND IMAGE
        Image(
            painter = painterResource(R.drawable.home),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)                        .size(32.dp)
                        .background(Color.Transparent)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = DarkText,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "URL Files",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.width(32.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // FOLDERS LIST
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                item {
                    Text(
                        text = "All URL Folders",
                        fontSize = 18.sp,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF242529),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                if (folderGroups.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No URL links saved yet.\nClick '+ new Links' below to create one!",
                                fontFamily = Lufga,
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    val entries = folderGroups.entries.toList()
                    items(
                        items = entries,
                        key = { it.key }
                    ) { (folderName, itemsInFolder) ->
                        val colorPair = pastelColors[folderName.hashCode().let { if (it < 0) -it else it } % pastelColors.size]
                        val detailsText = "$currentDate   ${itemsInFolder.size} links"

                        val folderData = UrlFolderData(
                            title = folderName,
                            details = detailsText,
                            backgroundColor = colorPair.first,
                            iconBackgroundColor = colorPair.second
                        )

                        SwipeableUrlCard(
                            folder = folderData,
                            onClick = { onFolderClick(folderName) },
                            onDelete = {
                                dbHelper?.deleteYouTubeNotesByFolder(folderName)
                                ytNotesList = dbHelper?.getAllYouTubeNotes() ?: emptyList()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // NEW LINKS BUTTON
            Box(
                modifier = Modifier
                    .padding(vertical = 24.dp)
            ) {
                Button(
                    onClick = onNewLinksClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "new Links",
                        color = Color.White,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableUrlCard(
    folder: UrlFolderData,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                showDeleteDialog = true
                false
            } else {
                false
            }
        }
    )

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Folder", fontFamily = Lufga, fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to delete folder '${folder.title}' and all its links?", fontFamily = Lufga) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    }
                ) {
                    Text("Delete", color = Color.Red, fontFamily = Lufga, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", fontFamily = Lufga)
                }
            }
        )
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFFE53935))
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "Delete",
                        color = Color.White,
                        fontFamily = Lufga,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        },
        content = {
            UrlFolderCard(
                folder = folder,
                onClick = onClick
            )
        },
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true
    )
}

@Composable
fun UrlFolderCard(
    folder: UrlFolderData,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(folder.backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // URL ICON BOX WITH CUSTOM BACKGROUND COLOR
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(folder.iconBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.url),
                contentDescription = "URL Folder",
                tint = DarkText,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                fontFamily = Lufga,
                text = folder.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF242529),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                fontFamily = Lufga,
                text = folder.details,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF808080),
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UrlFilesScreenPreview() {
    UrlFilesScreen()
}
