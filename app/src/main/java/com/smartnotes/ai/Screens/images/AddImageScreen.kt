package com.smartnotes.ai.Screens.images

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartnotes.ai.R
import com.smartnotes.ai.Screens.common.dashedBorder
import com.smartnotes.ai.db.UserDatabaseHelper
import com.smartnotes.ai.ui.theme.Lufga

private val DarkText = Color(0xFF242529)

@Composable
fun AddImageScreen(
    folderTitle: String = "Exam IMP Question",
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {},
    onFolderFieldClick: () -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val dbHelper = remember { if (isPreview) null else UserDatabaseHelper(context) }

    var noteText by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf("") }
    var selectedImageName by remember { mutableStateOf("") }
    var saveError by remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            try {
                context.contentResolver.takePersistableUriPermission(
                    selectedUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) {
            }
            selectedImageUri = selectedUri.toString()
            selectedImageName = selectedUri.lastPathSegment ?: "Selected Image"
            saveError = null
        }
    }

    val saveAction = {
        if (selectedImageUri.isBlank()) {
            saveError = "Choose an image before continuing."
        } else {
            val insertedId = dbHelper?.insertImage(
                folderName = folderTitle.ifBlank { "Exam IMP Question" },
                note = noteText,
                imageUri = selectedImageUri
            ) ?: -1L
            if (insertedId > 0L) {
                saveError = null
                onContinueClick()
            } else {
                saveError = "Couldn't save the image. Please try again."
            }
        }
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

            Spacer(modifier = Modifier.height(26.dp))

            // CONTENT SCROLL
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // ADD IMAGES SECTION
                Text(
                    text = "Add images",
                    fontFamily = Lufga,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                // DASHED DROPZONE BOX
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .dashedBorder(Color(0xFF000000), 2.dp, 10.dp, 8.dp, 30.dp)
                        .background(Color.White.copy(alpha = 0.4f))
                        .clickable {
                            imagePickerLauncher.launch(arrayOf("image/*"))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (selectedImageName.isNotBlank()) selectedImageName else "Select Image",
                        fontFamily = Lufga,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }

                saveError?.let { error ->
                    Text(
                        text = error,
                        color = Color(0xFFB00020),
                        fontFamily = Lufga,
                        fontSize = 13.sp
                    )
                }

                // NOTE SECTION
                Text(
                    text = "Note",
                    fontFamily = Lufga,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkText
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(27))
                        .background(Color(0xFFD3C6FC))
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontFamily = Lufga,
                            fontSize = 16.sp,
                            color = DarkText
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (noteText.isEmpty()) {
                                Text(
                                    text = "Enter note",
                                    fontFamily = Lufga,
                                    fontSize = 15.sp,
                                    color = Color(0xFF808080)
                                )
                            }
                            innerTextField()
                        }
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                // FOLDER SECTION
                Text(
                    text = "folder",
                    fontFamily = Lufga,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkText
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFFFF3D3))
                        .clickable { onFolderFieldClick() }
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = folderTitle,
                        fontFamily = Lufga,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = DarkText
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // BOTTOM CONTINUE BUTTON
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Button(
                    onClick = { saveAction() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Continue",
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
fun AddImageScreenPreview() {
    AddImageScreen()
}
