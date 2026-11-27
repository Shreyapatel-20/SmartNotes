package com.smartnotes.ai.Screens.images

import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.viewinterop.AndroidView
import com.smartnotes.ai.R
import com.smartnotes.ai.db.ImageModel
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF242529)

@Composable
fun ImageViewerScreen(
    folderTitle: String = "Exam IMP Question",
    onBackClick: () -> Unit = {},
    onAddImageClick: () -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var imagesList by remember {
        mutableStateOf(
            if (isPreview) {
                listOf(
                    ImageModel(1, "Exam IMP Question", "Important UI/UX Diagram", "", "08/01/26   5 images"),
                    ImageModel(2, "Exam IMP Question", "Architecture overview", "", "08/01/26   5 images")
                )
            } else {
                emptyList()
            }
        )
    }

    LaunchedEffect(Unit) {
        if (!isPreview && dbHelper != null) {
            imagesList = dbHelper.getAllImages()
        }
    }

    val folderImages = imagesList.filter { it.folderName.equals(folderTitle, ignoreCase = true) }

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
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
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
                    text = "Image Files",
                    fontFamily = Lufga,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.width(32.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CONTENT SCROLL
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = folderTitle,
                    fontFamily = Lufga,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                if (folderImages.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No images found in folder '$folderTitle'.",
                            fontFamily = Lufga,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    folderImages.forEach { img ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(24.dp))
                                .background(Color.White.copy(alpha = 0.9f))
                                .padding(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(320.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.LightGray)
                            ) {
                                if (img.imageUri.isNotBlank()) {
                                    AndroidView(
                                        modifier = Modifier.fillMaxSize(),
                                        factory = { ctx ->
                                            ImageView(ctx).apply {
                                                scaleType = ImageView.ScaleType.FIT_CENTER
                                                try {
                                                    setImageURI(Uri.parse(img.imageUri))
                                                } catch (_: Exception) {
                                                    setImageResource(R.drawable.profile)
                                                }
                                            }
                                        },
                                        update = { view ->
                                            try {
                                                view.setImageURI(Uri.parse(img.imageUri))
                                            } catch (_: Exception) {
                                                view.setImageResource(R.drawable.profile)
                                            }
                                        }
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(R.drawable.profile),
                                        contentDescription = "Placeholder",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                            if (img.note.isNotBlank()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = img.note,
                                    fontFamily = Lufga,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = DarkText,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // BOTTOM BUTTON
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Button(
                    onClick = onAddImageClick,
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
                        text = "Add image",
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

@Preview(showBackground = true)
@Composable
fun ImageViewerScreenPreview() {
    ImageViewerScreen()
}
