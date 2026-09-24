package com.pdffoto.ui.screens.editor

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.pdffoto.R
import com.pdffoto.domain.model.Photo
import com.pdffoto.ui.theme.PDFotoTheme
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

/** Máximo de fotos permitidas en una selección (ver docs/00-overview.md). */
private const val MaxPhotos = 50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit,
    onAddFromCamera: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditorViewModel = hiltViewModel(),
) {
    val photos by viewModel.photos.collectAsStateWithLifecycle()

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(MaxPhotos),
    ) { uris ->
        viewModel.onPhotosPicked(uris.map(Uri::toString))
    }

    val launchPicker = {
        photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        Unit
    }

    // Solo se abre el selector al entrar si el documento está vacío (p. ej. tras
    // "Crear PDF"). Si ya hay fotos —por ejemplo una captura de cámara— no se abre.
    LaunchedEffect(Unit) {
        if (viewModel.photos.value.isEmpty()) {
            launchPicker()
        }
    }

    var addMenuExpanded by remember { mutableStateOf(false) }
    var previewPhoto by remember { mutableStateOf<Photo?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.editor_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back),
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            // El botón "Añadir" ofrece elegir de la galería o hacer una foto.
            Box {
                ExtendedFloatingActionButton(
                    onClick = { addMenuExpanded = true },
                    icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) },
                    text = { Text(text = stringResource(R.string.editor_add_photos)) },
                )
                DropdownMenu(
                    expanded = addMenuExpanded,
                    onDismissRequest = { addMenuExpanded = false },
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.editor_add_from_gallery)) },
                        onClick = {
                            addMenuExpanded = false
                            launchPicker()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.editor_add_from_camera)) },
                        onClick = {
                            addMenuExpanded = false
                            onAddFromCamera()
                        },
                    )
                }
            }
        },
        bottomBar = {
            Surface(tonalElevation = 3.dp) {
                Button(
                    onClick = onContinue,
                    enabled = photos.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                ) {
                    Text(text = stringResource(R.string.action_continue))
                }
            }
        },
    ) { innerPadding ->
        if (photos.isEmpty()) {
            EmptyEditor(
                onAddFromGallery = launchPicker,
                onAddFromCamera = onAddFromCamera,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
            )
        } else {
            PhotoList(
                photos = photos,
                onRotate = viewModel::onRotate,
                onDelete = viewModel::onDelete,
                onMove = viewModel::onMove,
                onPreview = { previewPhoto = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )
        }
    }

    previewPhoto?.let { photo ->
        PhotoPreviewDialog(
            photo = photo,
            onDismiss = { previewPhoto = null },
        )
    }
}

@Composable
private fun PhotoList(
    photos: List<Photo>,
    onRotate: (String) -> Unit,
    onDelete: (String) -> Unit,
    onMove: (Int, Int) -> Unit,
    onPreview: (Photo) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    val reorderableState = rememberReorderableLazyListState(lazyListState) { from, to ->
        onMove(from.index, to.index)
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(photos, key = { _, photo -> photo.id }) { index, photo ->
            ReorderableItem(reorderableState, key = photo.id) { isDragging ->
                PhotoCard(
                    index = index,
                    photo = photo,
                    isDragging = isDragging,
                    onPreview = { onPreview(photo) },
                    onRotate = { onRotate(photo.id) },
                    onDelete = { onDelete(photo.id) },
                    dragHandle = {
                        IconButton(onClick = {}, modifier = Modifier.draggableHandle()) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = stringResource(R.string.editor_drag_handle),
                            )
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun EmptyEditor(
    onAddFromGallery: () -> Unit,
    onAddFromCamera: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    ) {
        Text(
            text = stringResource(R.string.editor_empty_message),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = onAddFromGallery,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.editor_add_from_gallery))
        }
        OutlinedButton(
            onClick = onAddFromCamera,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.editor_add_from_camera))
        }
    }
}

@Composable
private fun PhotoCard(
    index: Int,
    photo: Photo,
    isDragging: Boolean,
    onPreview: () -> Unit,
    onRotate: () -> Unit,
    onDelete: () -> Unit,
    dragHandle: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onPreview),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isDragging) 8.dp else 1.dp),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            AsyncImage(
                model = photo.uri,
                contentDescription = stringResource(R.string.editor_photo_description, index + 1),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .rotate(photo.rotationDegrees.toFloat()),
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.editor_photo_index, index + 1),
                    style = MaterialTheme.typography.titleMedium,
                )
                if (photo.rotationDegrees != 0) {
                    Text(
                        text = stringResource(R.string.editor_photo_rotated, photo.rotationDegrees),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            IconButton(onClick = onRotate) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.editor_rotate),
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.editor_delete),
                )
            }
            dragHandle()
        }
    }
}

/**
 * Vista previa a pantalla completa de una foto, para poder distinguir documentos. Se cierra
 * tocando la imagen o el botón de cerrar.
 */
@Composable
private fun PhotoPreviewDialog(
    photo: Photo,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .clickable(onClick = onDismiss),
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                // Al girar 90/270 se intercambian los lados para que la imagen no se recorte.
                val rotatedQuarterTurn = photo.rotationDegrees % 180 != 0
                val imageModifier = if (rotatedQuarterTurn) {
                    Modifier
                        .size(width = maxHeight, height = maxWidth)
                        .rotate(photo.rotationDegrees.toFloat())
                } else {
                    Modifier
                        .fillMaxSize()
                        .rotate(photo.rotationDegrees.toFloat())
                }

                AsyncImage(
                    model = photo.uri,
                    contentDescription = stringResource(R.string.editor_photo_preview),
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier,
                )
            }

            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.action_close),
                    tint = Color.White,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoCardPreview() {
    PDFotoTheme {
        PhotoCard(
            index = 0,
            photo = Photo(id = "1", uri = "", rotationDegrees = 90),
            isDragging = false,
            onPreview = {},
            onRotate = {},
            onDelete = {},
            dragHandle = {},
        )
    }
}
