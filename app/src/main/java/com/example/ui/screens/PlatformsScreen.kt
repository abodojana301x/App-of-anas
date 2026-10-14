package com.example.ui.screens

import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ConnectedPlatform
import com.example.model.PlatformType
import com.example.ui.components.CupertinoGroupedCard
import com.example.ui.components.CupertinoSwitch
import com.example.ui.components.GlassCard
import com.example.ui.components.PlatformBadge
import com.example.ui.theme.*

@Composable
fun PlatformsScreen(
    platforms: List<ConnectedPlatform>,
    onTogglePlatform: (PlatformType) -> Unit,
    onRefreshSync: () -> Unit,
    isRefreshing: Boolean,
    onManagePlatform: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeCount = remember(platforms) { platforms.count { it.isSyncActive } }
    val rotation by animateFloatAsState(
        targetValue = if (isRefreshing) 360f else 0f,
        animationSpec = tween(durationMillis = 600, easing = LinearEasing),
        label = "syncRotation"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("platforms_screen_list"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 82.dp, bottom = 104.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. iOS Large Title & Summary
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
                        text = "المنصات المتصلة",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = (-0.5).sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(if (activeCount > 0) AppleSystemGreen else AppleSystemOrange)
                        )
                        Text(
                            text = "$activeCount من ${platforms.size} منصات متزامنة بنشاط",
                            style = MaterialTheme.typography.labelMedium,
                            color = AppleSystemGray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                IconButton(
                    onClick = onRefreshSync,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(AppleSystemGray6)
                        .border(0.5.dp, GlassBorderSubtle, CircleShape)
                        .testTag("platforms_refresh_sync_btn")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Sync,
                        contentDescription = "مزامنة الكل",
                        tint = AppleSystemBlue,
                        modifier = Modifier
                            .size(19.dp)
                            .rotate(rotation)
                    )
                }
            }
        }

        // 2. iOS Inset Grouped Platforms List
        items(platforms, key = { it.name }) { platform ->
            CupertinoGroupedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("platform_card_${platform.type.name.lowercase()}"),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = AppleSystemGray6
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Top Row: Avatar/Brand, Title, Handle, and Cupertino Switch
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
                                    .background(AppleSystemGray5)
                                    .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                val brandIcon = when (platform.type) {
                                    PlatformType.X -> Icons.Default.Tag
                                    PlatformType.INSTAGRAM -> Icons.Default.PhotoCamera
                                    PlatformType.TIKTOK -> Icons.Default.Audiotrack
                                    PlatformType.YOUTUBE -> Icons.Default.SmartDisplay
                                    PlatformType.FACEBOOK -> Icons.Default.Public
                                    PlatformType.LINKEDIN -> Icons.Default.Business
                                    PlatformType.THREADS -> Icons.Default.Chat
                                    PlatformType.ALL -> Icons.Default.Widgets
                                }
                                val brandColor = when (platform.type) {
                                    PlatformType.X -> ColorPlatformX
                                    PlatformType.INSTAGRAM -> ColorPlatformInstagram
                                    PlatformType.TIKTOK -> ColorPlatformTikTok
                                    PlatformType.YOUTUBE -> ColorPlatformYouTube
                                    PlatformType.FACEBOOK -> ColorPlatformFacebook
                                    PlatformType.LINKEDIN -> ColorPlatformLinkedIn
                                    PlatformType.THREADS -> ColorPlatformThreads
                                    PlatformType.ALL -> AppleSystemBlue
                                }
                                Icon(
                                    imageVector = brandIcon,
                                    contentDescription = platform.name,
                                    tint = brandColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = platform.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                    PlatformBadge(platformType = platform.type)
                                }
                                Text(
                                    text = platform.handleOrTitle,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppleSystemGray
                                )
                            }
                        }

                        // iOS Switch for toggling sync
                        CupertinoSwitch(
                            checked = platform.isSyncActive,
                            onCheckedChange = { onTogglePlatform(platform.type) },
                            activeColor = AppleSystemGreen,
                            testTag = "switch_${platform.type.name.lowercase()}"
                        )
                    }

                    // Hairline separator
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(AppleSystemGray5)
                    )

                    // Bottom Row: Stats & Action
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = AppleSystemGray,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = platform.lastSyncText,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppleSystemGray,
                                    fontSize = 11.sp
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(AppleSystemBlue.copy(alpha = 0.12f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = platform.statsBadge,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppleSystemBlue,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.clickable { onManagePlatform(platform.name) }
                        ) {
                            Text(
                                text = platform.actionButtonText,
                                style = MaterialTheme.typography.labelSmall,
                                color = AppleSystemBlue,
                                fontWeight = FontWeight.Medium
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = AppleSystemBlue,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }

        // 3. Apple Inset Security & Cloud Note
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = AppleSystemGray6
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(AppleSystemBlue.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                            tint = AppleSystemBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "ربط مشفر ومحمي بخصوصية Apple",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "يتم الاتصال بجلسات القراءة فقط دون مشاركة كلمات المرور أو صلاحيات النشر غير المصرح بها.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppleSystemGray
                        )
                    }
                }
            }
        }
    }
}
