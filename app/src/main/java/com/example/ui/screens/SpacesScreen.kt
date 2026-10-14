package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CustomSpace
import com.example.model.SpaceModule
import com.example.ui.components.CupertinoGroupedCard
import com.example.ui.components.CupertinoSheetHandle
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@Composable
fun SpacesScreen(
    activeSpace: CustomSpace?,
    savedSpaces: List<CustomSpace>,
    onSwitchSpace: (String) -> Unit,
    onMoveModuleUp: (String) -> Unit,
    onMoveModuleDown: (String) -> Unit,
    onSaveArrangement: () -> Unit,
    onOpenCreateSpaceModal: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("spaces_screen_list"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 82.dp, bottom = 104.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. iOS Large Title & Action Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "مساحاتي المخصصة",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "${savedSpaces.size + (if (activeSpace != null) 1 else 0)} مساحات نشطة ومتزامنة",
                        style = MaterialTheme.typography.labelMedium,
                        color = AppleSystemGray
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppleSystemBlue)
                        .clickable { onOpenCreateSpaceModal() }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                        .testTag("btn_create_new_space"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "مساحة جديدة",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 2. Active Space Card (iOS Inset Grouped)
        activeSpace?.let { space ->
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    backgroundColor = AppleSystemGray6
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Space Info Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(AppleSystemBlue.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.SmartToy,
                                        contentDescription = null,
                                        tint = AppleSystemBlue,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = space.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(7.dp)
                                                .clip(CircleShape)
                                                .background(AppleSystemGreen)
                                        )
                                    }
                                    Text(
                                        text = space.subtitleTag,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AppleSystemBlue
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(AppleSystemGreen.copy(alpha = 0.12f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "نشطة الآن",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppleSystemGreen,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Text(
                            text = space.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppleSystemGray,
                            lineHeight = 21.sp
                        )

                        // Platform Chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            space.platformChips.forEach { chip ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(AppleSystemGray5)
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = chip,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }

                        // Divider
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.5.dp)
                                .background(AppleSystemGray5)
                        )

                        // Reorderable Modules
                        Text(
                            text = "الوحدات المعروضة (${space.modules.size})",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )

                        space.modules.forEachIndexed { index, module ->
                            ModuleItemCard(
                                module = module,
                                canMoveUp = index > 0,
                                canMoveDown = index < space.modules.size - 1,
                                onMoveUp = { onMoveModuleUp(module.id) },
                                onMoveDown = { onMoveModuleDown(module.id) }
                            )
                        }

                        // Save Arrangement Action
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(AppleSystemGray5)
                                .clickable { onSaveArrangement() }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = AppleSystemBlue,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "حفظ ترتيب الوحدات",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = AppleSystemBlue
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Saved Spaces Section Header
        item {
            Text(
                text = "المساحات الأخرى المتاحة",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // 4. Saved Spaces List
        items(savedSpaces, key = { it.id }) { space ->
            SavedSpaceCard(
                space = space,
                onActivate = { onSwitchSpace(space.id) }
            )
        }
    }
}

@Composable
fun ModuleItemCard(
    module: SpaceModule,
    canMoveUp: Boolean,
    canMoveDown: Boolean,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AppleSystemGray5)
            .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(AppleSystemGray6),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Layers,
                        contentDescription = null,
                        tint = AppleSystemBlue,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column {
                    Text(
                        text = module.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = module.subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = AppleSystemGray
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                if (canMoveUp) {
                    IconButton(onClick = onMoveUp, modifier = Modifier.size(30.dp)) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "أعلى",
                            tint = AppleSystemGray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                if (canMoveDown) {
                    IconButton(onClick = onMoveDown, modifier = Modifier.size(30.dp)) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "أسفل",
                            tint = AppleSystemGray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SavedSpaceCard(
    space: CustomSpace,
    onActivate: () -> Unit
) {
    CupertinoGroupedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        backgroundColor = AppleSystemGray6
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppleSystemGray5),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Dashboard,
                        contentDescription = null,
                        tint = AppleSystemBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    Text(
                        text = space.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = space.subtitleTag,
                        style = MaterialTheme.typography.labelSmall,
                        color = AppleSystemGray
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppleSystemGray5)
                    .clickable { onActivate() }
                    .padding(horizontal = 14.dp, vertical = 7.dp)
            ) {
                Text(
                    text = "تفعيل",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppleSystemBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = space.description,
            style = MaterialTheme.typography.bodySmall,
            color = AppleSystemGray,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun CreateSpaceBottomSheet(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onCreate: (String, String) -> Unit
) {
    if (!isOpen) return

    var spaceName by remember { mutableStateOf("") }
    var contentType by remember { mutableStateOf("video") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppleSystemGray6,
        shape = RoundedCornerShape(26.dp),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CupertinoSheetHandle()
                Text(
                    text = "إنشاء مساحة جديدة",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "خصص مصادرك ووحدات العرض",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppleSystemGray
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "اسم المساحة",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    OutlinedTextField(
                        value = spaceName,
                        onValueChange = { spaceName = it },
                        placeholder = { Text("مثلاً: مساحة التصميم المعماري...", color = AppleSystemGray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppleSystemBlue,
                            unfocusedBorderColor = AppleSystemGray4,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = AppleSystemGray5,
                            unfocusedContainerColor = AppleSystemGray5
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "نوع المحتوى المفضل",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (contentType == "video") AppleSystemBlue else AppleSystemGray5)
                                .clickable { contentType = "video" }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "فيديوهات",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium,
                                color = if (contentType == "video") Color.White else AppleSystemGray
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (contentType == "posts") AppleSystemBlue else AppleSystemGray5)
                                .clickable { contentType = "posts" }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "منشورات ومقالات",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium,
                                color = if (contentType == "posts") Color.White else AppleSystemGray
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onCreate(spaceName, "مساحة تجمع مصادر $spaceName المخصصة") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppleSystemBlue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = spaceName.isNotBlank()
            ) {
                Text("إنشاء المساحة", fontWeight = FontWeight.SemiBold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء", color = AppleSystemGray)
            }
        }
    )
}
