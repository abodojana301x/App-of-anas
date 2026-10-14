package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import com.example.ui.components.GlassCard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
        animationSpec = tween(durationMillis = 600, easing = LinearEasing)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("platforms_screen_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header & Live Sync Status
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "المنصات المتصلة",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceDark
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(StatusWarning)
                            )
                            Text(
                                text = "$activeCount من ${platforms.size} منصات متزامنة بنشاط",
                                style = MaterialTheme.typography.labelMedium,
                                color = OnSurfaceVariantDark,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Button(
                        onClick = onRefreshSync,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SurfaceContainerHigh,
                            contentColor = OnSurfaceDark
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        modifier = Modifier.testTag("platforms_refresh_sync_btn")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Sync,
                                contentDescription = null,
                                tint = PrimaryContainerViolet,
                                modifier = Modifier
                                    .size(18.dp)
                                    .rotate(rotation)
                            )
                            Text(
                                text = "تحديث المزامنة",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }

                // Central Data Stream Status Banner
                GlassCard(
                    modifier = Modifier.fillMaxWidth()
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
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainerHigh),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Bolt,
                                    contentDescription = null,
                                    tint = PrimaryContainerViolet,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "حالة دفق البيانات المركزي",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnSurfaceDark
                                )
                                Text(
                                    text = "معدل التحديث الذكي: تلقائي كل دقيقتين",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OnSurfaceVariantDark
                                )
                            }
                        }

                        Surface(
                            color = PrimaryContainerViolet.copy(alpha = 0.2f),
                            shape = CircleShape
                        ) {
                            Text(
                                text = "مستقر",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryContainerViolet,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. Connected Platforms Cards
        items(platforms, key = { it.type.name }) { platform ->
            PlatformCard(
                platform = platform,
                onToggle = { onTogglePlatform(platform.type) },
                onManage = { onManagePlatform(platform.name) }
            )
        }

        // 3. Add Platform or Cloud Service
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "إضافة منصة أو خدمة سحابية",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceDark
                        )
                        Text(
                            text = "توسيع نطاق المزامنة لمزيد من الشبكات",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariantDark
                        )
                    }

                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "إضافة منصة",
                        tint = PrimaryContainerViolet,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CloudServiceCard(
                        title = "LinkedIn",
                        subtitle = "شبكة الأعمال والمهن",
                        badge = "استكشاف",
                        icon = Icons.Default.Business,
                        iconColor = ColorPlatformLinkedIn,
                        onConnect = { onManagePlatform("LinkedIn") },
                        modifier = Modifier.weight(1f)
                    )

                    CloudServiceCard(
                        title = "Threads",
                        subtitle = "محادثات المتابعين الفورية",
                        badge = "استكشاف",
                        icon = Icons.Default.Chat,
                        iconColor = ColorPlatformThreads,
                        onConnect = { onManagePlatform("Threads") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun PlatformCard(
    platform: ConnectedPlatform,
    onToggle: () -> Unit,
    onManage: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("platform_card_${platform.type.name}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val (icon, tint) = when (platform.type) {
                        PlatformType.X -> Pair(Icons.Default.Tag, ColorPlatformX)
                        PlatformType.INSTAGRAM -> Pair(Icons.Default.PhotoCamera, ColorPlatformInstagram)
                        PlatformType.YOUTUBE -> Pair(Icons.Default.SmartDisplay, ColorPlatformYouTube)
                        PlatformType.TIKTOK -> Pair(Icons.Default.Audiotrack, ColorPlatformTikTok)
                        PlatformType.FACEBOOK -> Pair(Icons.Default.Public, ColorPlatformFacebook)
                        else -> Pair(Icons.Default.Share, PrimaryContainerViolet)
                    }

                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerHighest),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = platform.name,
                            tint = tint,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = platform.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurfaceDark
                            )
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(StatusWarning)
                            )
                        }
                        Text(
                            text = platform.handleOrTitle,
                            style = MaterialTheme.typography.labelMedium,
                            color = OnSurfaceVariantDark
                        )
                    }
                }

                Switch(
                    checked = platform.isSyncActive,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = OnPrimaryContainerViolet,
                        checkedTrackColor = PrimaryContainerViolet,
                        uncheckedThumbColor = OnSurfaceVariantDark,
                        uncheckedTrackColor = SurfaceContainerHighest
                    ),
                    modifier = Modifier.testTag("platform_switch_${platform.type.name}")
                )
            }

            // Sync Status & Stat Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = if (platform.isSyncActive) Icons.Filled.CheckCircle else Icons.Filled.Schedule,
                        contentDescription = null,
                        tint = StatusWarning,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = platform.lastSyncText,
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariantDark
                    )
                }

                Text(
                    text = platform.statsBadge,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryContainerViolet
                )
            }

            // Detail and Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = platform.subDetailText,
                    style = MaterialTheme.typography.labelSmall,
                    color = OnSurfaceVariantDark
                )

                Surface(
                    color = SurfaceContainerHigh,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.clickable { onManage() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = OnSurfaceDark,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = platform.actionButtonText,
                            style = MaterialTheme.typography.labelMedium,
                            color = OnSurfaceDark
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CloudServiceCard(
    title: String,
    subtitle: String,
    badge: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    onConnect: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Surface(
                    color = StatusWarning.copy(alpha = 0.12f),
                    shape = CircleShape
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall,
                        color = StatusWarning,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurfaceDark
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = OnSurfaceVariantDark
                )
            }

            Button(
                onClick = onConnect,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SurfaceContainerHigh,
                    contentColor = OnSurfaceDark
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 6.dp)
            ) {
                Text(
                    text = "ربط الحساب",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
