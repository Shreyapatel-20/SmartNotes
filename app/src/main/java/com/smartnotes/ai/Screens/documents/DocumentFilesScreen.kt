package com.smartnotes.ai.Screens.documents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.db.DocumentModel
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF242529)

@Composable
fun DocumentFilesScreen(
    onBackClick: () -> Unit = {},
    onNewDocumentClick: () -> Unit = {},
    onDocumentClick: (String, String, String) -> Unit = { _, _, _ -> }
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var searchQuery by remember { mutableStateOf("") }
    var documentsList by remember {
        mutableStateOf(
            if (isPreview) {
                listOf(DocumentModel(1, "Resume", "12 MB • 12/01/16", ""))
            } else {
                emptyList()
            }
        )
    }

    LaunchedEffect(Unit) {
        if (!isPreview && dbHelper != null) {
            documentsList = dbHelper.getAllDocuments()
        }
    }

    val filteredList = documentsList.filter { doc ->
        searchQuery.isBlank() ||
        doc.title.contains(searchQuery, ignoreCase = true) ||
        doc.details.contains(searchQuery, ignoreCase = true)
    }

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
                .padding(horizontal = 16.dp)
        ) {

            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 12.dp)
                    .height(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color = Color.Transparent)
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
                    text = "Document Files",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.width(36.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SEARCH BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Search",
                    tint = Color(0xFF808080),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontFamily = Lufga,
                        fontSize = 16.sp,
                        color = DarkText
                    ),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search",
                                fontFamily = Lufga,
                                fontSize = 16.sp,
                                color = Color(0xFF999999)
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SECTION TITLE
            Text(
                text = "All Documents files",
                fontFamily = Lufga,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText,
                modifier = Modifier.padding(bottom = 14.dp)
            )

            // DOCUMENT FILES LIST
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 10.dp)
            ) {
                if (filteredList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (searchQuery.isNotBlank()) "No documents found matching '$searchQuery'" else "No documents uploaded yet.",
                                fontFamily = Lufga,
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(
                        items = filteredList,
                        key = { it.id }
                    ) { doc ->
                        SwipeableDocumentCard(
                            title = doc.title,
                            details = doc.details,
                            onClick = { onDocumentClick(doc.title, doc.details, doc.fileUri) },
                            onDelete = {
                                dbHelper?.deleteDocument(doc.id)
                                documentsList = dbHelper?.getAllDocuments() ?: emptyList()
                            }
                        )
                    }
                }
            }

            // NEW DOCUMENT BUTTON
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp)
            ) {
                Button(
                    onClick = onNewDocumentClick,
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
                        text = "new Document",
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
fun SwipeableDocumentCard(
    title: String,
    details: String,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
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
            DocumentCard(
                title = title,
                details = details,
                onClick = onClick
            )
        },
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true
    )
}

@Composable
fun DocumentCard(
    title: String,
    details: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFF1E4FF))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // DOCUMENT ICON WITH PDF BADGE
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.pdf),
                    contentDescription = "Document",
                    tint = DarkText,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "PDF",
                    fontFamily = Lufga,
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // TITLE & DETAILS
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontFamily = Lufga,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = details,
                fontFamily = Lufga,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF808080),
                maxLines = 1
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Open",
            tint = DarkText,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DocumentFilesScreenPreview() {
    DocumentFilesScreen()
}
