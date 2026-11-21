package com.smartnotes.ai.Screens

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF242529)

data class DocumentFileData(
    val title: String,
    val details: String = "12 MB • 12/01/16",
    val backgroundColor: Color = Color(0xFFF1E4FF)
)

@Composable
fun DocumentFilesScreen(
    onBackClick: () -> Unit = {},
    onNewDocumentClick: () -> Unit = {},
    onDocumentClick: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    val documentFiles = remember {
        listOf(
            DocumentFileData(
                title = "Resume",
                details = "12 MB • 12/01/16",
                backgroundColor = Color(0xFFF1E4FF)
            )
        )
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
                items(documentFiles) { file ->
                    DocumentCard(
                        data = file,
                        onClick = { onDocumentClick(file.title) }
                    )
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
                        .height(60.dp).width(400.dp),
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

@Composable
fun DocumentCard(
    data: DocumentFileData,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth().height(70.dp).width(400.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(data.backgroundColor)
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
            modifier = Modifier.height(50.dp).width(240.dp)
        ) {
            Text(
                text = data.title,
                fontFamily = Lufga,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = data.details,
                fontFamily = Lufga,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF808080)
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
