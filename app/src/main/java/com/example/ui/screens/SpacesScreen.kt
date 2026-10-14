package com.example.ui.screens

import androidx.compose.foundation.background
import com.example.ui.components.GlassCard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Overview & Quick Action Hero
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = PrimaryContainerViolet.copy(alpha = 0.2f),
                            shape = CircleShape
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AutoAwesome,
                                    contentDescription = null,
                                    tint = PrimaryContainerViolet,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "تخصيص ديناميكي كامل",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PrimaryContainerViolet,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Text(
                            text = "${savedSpaces.size + (if (activeSpace != null) 1 else 0)} مساحات نشطة",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariantDark
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "مساحاتك المخصصة",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceDark
                        )
                        Text(
                            text = "نظّم عالمك الرقمي في وحدات ذكية ومستقلة تجمع القنوات والموجزات المفضلة بدون ضجيج الخوارزميات.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariantDark,
                            lineHeight = 22.sp
                        )
                    }

                    Button(
                        onClick = onOpenCreateSpaceModal,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryContainerViolet,
                            contentColor = OnPrimaryContainerViolet
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("btn_create_new_space")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "إنشاء مساحة جديدة",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // 2. Active Space Card
        activeSpace?.let { space ->
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        // Header of the Space
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SurfaceContainerHigh.copy(alpha = 0.6f))
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
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
                                            .size(46.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(PrimaryContainerViolet.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.SmartToy,
                                            contentDescription = null,
                                            tint = PrimaryContainerViolet,
                                            modifier = Modifier.size(26.dp)
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
                                                fontWeight = FontWeight.SemiBold,
                                                color = OnSurfaceDark
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .size(7.dp)
                                                    .clip(CircleShape)
                                                    .background(PrimaryContainerViolet)
                                            )
                                        }
                                        Text(
                                            text = space.subtitleTag,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = PrimaryContainerViolet
                                        )
                                    }
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    IconButton(
                                        onClick = { },
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(SurfaceContainer)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "مشاركة",
                                            tint = OnSurfaceVariantDark,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = { },
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(SurfaceContainer)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Tune,
                                            contentDescription = "ضبط",
                                            tint = OnSurfaceVariantDark,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }

                            Text(
                                text = space.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurfaceVariantDark,
                                lineHeight = 22.sp
                            )

                            // Platform Chips
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                space.platformChips.forEach { chip ->
                                    Surface(
                                        color = SurfaceContainerHighest,
                                        shape = CircleShape
                                    ) {
                                        Text(
                                            text = chip,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = OnSurfaceDark,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Modular Blocks Section
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "الوحدات المعروضة في الشاشة (${space.modules.size} وحدات)",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = OnSurfaceVariantDark,
                                    fontWeight = FontWeight.Medium
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DragIndicator,
                                        contentDescription = null,
                                        tint = OutlineDark,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "اضغط مع السحب لتعديل الترتيب",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = OutlineDark
                                    )
                                }
                            }

                            // Modules List
                            space.modules.forEachIndexed { index, module ->
                                ModuleItemCard(
                                    module = module,
                                    canMoveUp = index > 0,
                                    canMoveDown = index < space.modules.size - 1,
                                    onMoveUp = { onMoveModuleUp(module.id) },
                                    onMoveDown = { onMoveModuleDown(module.id) }
                                )
                            }

                            // Sub Actions Bar
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = onOpenCreateSpaceModal,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = SurfaceContainerHighest,
                                        contentColor = OnSurfaceDark
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = null,
                                            tint = PrimaryContainerViolet,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(text = "إضافة وحدة إلى المساحة", style = MaterialTheme.typography.labelMedium)
                                    }
                                }

                                Surface(
                                    color = SurfaceContainerHighest,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.clickable { }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Layers,
                                            contentDescription = null,
                                            tint = OnSurfaceVariantDark,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = "تخطيط الشبكة",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = OnSurfaceVariantDark
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Other Saved Spaces Section
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "باقي المساحات المحفوظة",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurfaceDark
                )
                Text(
                    text = "انقر للتبديل السريع",
                    style = MaterialTheme.typography.labelSmall,
                    color = OutlineDark
                )
            }
        }

        items(savedSpaces, key = { it.id }) { space ->
            SavedSpaceCard(
                space = space,
                onActivate = { onSwitchSpace(space.id) }
            )
        }

        // 4. Bottom Sticky Customizer Strip
        item {
            GlassCard(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = GlassSurfaceElevated,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
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
                                .background(PrimaryContainerViolet.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ViewQuilt,
                                contentDescription = null,
                                tint = PrimaryContainerViolet,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "تخصيص ترتيب الوحدات",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurfaceDark
                            )
                            Text(
                                text = "اسحب الوحدات لإعادة التشكيل",
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSurfaceVariantDark
                            )
                        }
                    }

                    Button(
                        onClick = onSaveArrangement,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryContainerViolet,
                            contentColor = OnPrimaryContainerViolet
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(text = "حفظ التنسيق", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }
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
    GlassCard(
        shape = RoundedCornerShape(14.dp),
        backgroundColor = GlassSurfaceElevated,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DragIndicator,
                    contentDescription = "سحب",
                    tint = OutlineDark,
                    modifier = Modifier.size(20.dp)
                )

                val (icon, tint) = when (module.iconType) {
                    "dynamic_feed" -> Pair(Icons.Default.DynamicFeed, PrimaryContainerViolet)
                    "slow_motion_video" -> Pair(Icons.Default.SlowMotionVideo, StatusWarning)
                    "ondemand_video" -> Pair(Icons.Default.OndemandVideo, ColorPlatformYouTube)
                    else -> Pair(Icons.Default.Layers, PrimaryContainerViolet)
                }

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerHighest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = tint,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    Text(
                        text = module.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium,
                        color = OnSurfaceDark
                    )
                    Text(
                        text = module.subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariantDark
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    color = SurfaceContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = module.badgeText,
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariantDark,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                if (canMoveUp) {
                    IconButton(onClick = onMoveUp, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "أعلى", tint = OutlineDark, modifier = Modifier.size(18.dp))
                    }
                }
                if (canMoveDown) {
                    IconButton(onClick = onMoveDown, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "أسفل", tint = OutlineDark, modifier = Modifier.size(18.dp))
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
    GlassCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                    val icon = when {
                        space.title.contains("التعلم") -> Icons.Default.School
                        space.title.contains("الترفيه") -> Icons.Default.SentimentVerySatisfied
                        space.title.contains("الأخبار") -> Icons.Default.Bolt
                        else -> Icons.Default.Dashboard
                    }
                    val tint = when {
                        space.title.contains("التعلم") -> StatusWarning
                        space.title.contains("الترفيه") -> PrimaryContainerViolet
                        space.title.contains("الأخبار") -> ColorPlatformYouTube
                        else -> PrimaryContainerViolet
                    }

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerHighest),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
                    }

                    Column {
                        Text(
                            text = space.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = OnSurfaceDark
                        )
                        Text(
                            text = space.subtitleTag,
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariantDark
                        )
                    }
                }

                Button(
                    onClick = onActivate,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceContainerHigh,
                        contentColor = PrimaryContainerViolet
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "تفعيل", style = MaterialTheme.typography.labelMedium)
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Text(
                text = space.description,
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceVariantDark,
                lineHeight = 22.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                space.platformChips.forEach { chip ->
                    Surface(
                        color = SurfaceContainerHighest,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = chip,
                            style = MaterialTheme.typography.labelSmall,
                            color = OutlineDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
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
        containerColor = SurfaceContainerHigh,
        shape = RoundedCornerShape(24.dp),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(PrimaryContainerViolet.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DashboardCustomize,
                        contentDescription = null,
                        tint = PrimaryContainerViolet,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Column {
                    Text(
                        text = "إنشاء مساحة جديدة",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceDark
                    )
                    Text(
                        text = "خصص مصادرك ووحدات العرض",
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariantDark
                    )
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "اسم المساحة",
                        style = MaterialTheme.typography.labelMedium,
                        color = OnSurfaceDark
                    )
                    OutlinedTextField(
                        value = spaceName,
                        onValueChange = { spaceName = it },
                        placeholder = { Text("مثلاً: مساحة التصميم المعماري...", color = OutlineDark) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryContainerViolet,
                            unfocusedBorderColor = OutlineVariantDark,
                            focusedTextColor = OnSurfaceDark,
                            unfocusedTextColor = OnSurfaceDark,
                            focusedContainerColor = CanvasDark,
                            unfocusedContainerColor = CanvasDark
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "نوع المحتوى الأساسي",
                        style = MaterialTheme.typography.labelMedium,
                        color = OnSurfaceDark
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = if (contentType == "video") PrimaryContainerViolet.copy(alpha = 0.2f) else SurfaceContainer,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { contentType = "video" }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayCircle,
                                    contentDescription = null,
                                    tint = PrimaryContainerViolet,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "فيديو مكثف",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = OnSurfaceDark
                                )
                            }
                        }

                        Surface(
                            color = if (contentType == "posts") PrimaryContainerViolet.copy(alpha = 0.2f) else SurfaceContainer,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { contentType = "posts" }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Feed,
                                    contentDescription = null,
                                    tint = StatusWarning,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "منشورات ومقالات",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = OnSurfaceDark
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onCreate(spaceName, "مساحة تجمع مصادر $spaceName المخصصة") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryContainerViolet,
                    contentColor = OnPrimaryContainerViolet
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = spaceName.isNotBlank()
            ) {
                Text("إنشاء وحفظ المساحة")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء", color = OnSurfaceVariantDark)
            }
        }
    )
}
