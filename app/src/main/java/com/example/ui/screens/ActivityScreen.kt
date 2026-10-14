package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import com.example.ui.components.GlassCard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.ActivityItem
import com.example.model.ActivityTab
import com.example.model.PlatformType
import com.example.ui.theme.*

@Composable
fun ActivityScreen(
    items: List<ActivityItem>,
    currentTab: ActivityTab,
    selectedFilter: PlatformType,
    onTabSelected: (ActivityTab) -> Unit,
    onFilterSelected: (PlatformType) -> Unit,
    onMarkAllRead: () -> Unit,
    onItemAction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val unreadCount = remember(items) { items.count { it.isUnread } }
    val filteredItems = remember(items, selectedFilter) {
        if (selectedFilter == PlatformType.ALL) items
        else items.filter { it.platformType == selectedFilter }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("activity_screen_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Header Area
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "النشاط الموحد",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceDark
                    )
                    if (unreadCount > 0) {
                        Surface(
                            color = PrimaryContainerViolet.copy(alpha = 0.2f),
                            shape = CircleShape
                        ) {
                            Text(
                                text = "$unreadCount جديدة",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryContainerViolet,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .clickable { onMarkAllRead() }
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DoneAll,
                        contentDescription = "قراءة الكل",
                        tint = OnSurfaceVariantDark,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "قراءة الكل",
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariantDark
                    )
                }
            }
        }

        // 2. Primary Sub-navigation Tabs
        item {
            Surface(
                color = GlassSurfacePill,
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(0.5.dp, GlassBorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ActivityTab.entries.forEach { tab ->
                        val isSelected = currentTab == tab
                        val icon = when (tab) {
                            ActivityTab.NOTIFICATIONS -> Icons.Filled.Notifications
                            ActivityTab.INTERACTIONS -> Icons.Filled.Forum
                            ActivityTab.HISTORY -> Icons.Filled.History
                            ActivityTab.ACCOUNTS -> Icons.Filled.Link
                        }

                        Surface(
                            color = if (isSelected) SurfaceContainerHigh else Color.Transparent,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onTabSelected(tab) }
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = tab.title,
                                    tint = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = tab.title,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Source Filter Pills
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ActivityFilterPill(
                    label = "الكل",
                    isSelected = selectedFilter == PlatformType.ALL,
                    onClick = { onFilterSelected(PlatformType.ALL) }
                )
                ActivityFilterPill(
                    label = "X",
                    isSelected = selectedFilter == PlatformType.X,
                    onClick = { onFilterSelected(PlatformType.X) }
                )
                ActivityFilterPill(
                    label = "Instagram",
                    isSelected = selectedFilter == PlatformType.INSTAGRAM,
                    onClick = { onFilterSelected(PlatformType.INSTAGRAM) }
                )
                ActivityFilterPill(
                    label = "YouTube",
                    isSelected = selectedFilter == PlatformType.YOUTUBE,
                    onClick = { onFilterSelected(PlatformType.YOUTUBE) }
                )
                ActivityFilterPill(
                    label = "TikTok",
                    isSelected = selectedFilter == PlatformType.TIKTOK,
                    onClick = { onFilterSelected(PlatformType.TIKTOK) }
                )
            }
        }

        // 4. Activity Stream Feed Cards
        items(filteredItems, key = { it.id }) { item ->
            ActivityCard(
                item = item,
                onPrimaryAction = { onItemAction(item.actionPrimaryLabel ?: "") },
                onSecondaryAction = { onItemAction(item.actionSecondaryLabel ?: "") }
            )
        }

        // 5. End of Stream Indicator
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(3.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainerHigh)
                )
                Text(
                    text = "أنت على اطلاع بكل جديد في مساحتك",
                    style = MaterialTheme.typography.labelSmall,
                    color = OnSurfaceVariantDark
                )
            }
        }
    }
}

@Composable
fun ActivityFilterPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) PrimaryContainerViolet else GlassSurfacePill,
        shape = CircleShape,
        border = BorderStroke(0.5.dp, if (isSelected) GlassBorder else GlassBorderSubtle),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) OnPrimaryContainerViolet else OnSurfaceVariantDark
            )
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(OnPrimaryContainerViolet)
                )
            }
        }
    }
}

@Composable
fun ActivityCard(
    item: ActivityItem,
    onPrimaryAction: () -> Unit,
    onSecondaryAction: () -> Unit
) {
    GlassCard(
        shape = RoundedCornerShape(18.dp),
        backgroundColor = GlassSurfaceDark,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (item.avatarUrl != null) {
                        Box(modifier = Modifier.size(42.dp)) {
                            AsyncImage(
                                model = item.avatarUrl,
                                contentDescription = item.authorName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                            val badgeText = when (item.platformType) {
                                PlatformType.X -> "𝕏"
                                PlatformType.INSTAGRAM -> "IG"
                                PlatformType.TIKTOK -> "TT"
                                PlatformType.YOUTUBE -> "YT"
                                else -> "•"
                            }
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceContainerHighest),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = badgeText,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurfaceDark
                                )
                            }
                        }
                    } else {
                        val icon = when {
                            item.isSystemNotification -> Icons.Default.Sync
                            item.platformType == PlatformType.YOUTUBE -> Icons.Default.SmartDisplay
                            item.platformType == PlatformType.TIKTOK -> Icons.Default.TrendingUp
                            else -> Icons.Default.Notifications
                        }
                        val tint = when {
                            item.isSystemNotification -> PrimaryContainerViolet
                            item.platformType == PlatformType.YOUTUBE -> ColorPlatformYouTube
                            item.platformType == PlatformType.TIKTOK -> ColorPlatformTikTok
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
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = item.authorName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurfaceDark
                            )
                            if (item.isSystemNotification) {
                                Surface(
                                    color = PrimaryContainerViolet.copy(alpha = 0.25f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "تطبيق محلي",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 10.sp,
                                        color = PrimaryContainerViolet,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = when (item.platformType) {
                                    PlatformType.X -> "منصة خارجية • 𝕏"
                                    PlatformType.INSTAGRAM -> "منصة خارجية • Instagram"
                                    PlatformType.YOUTUBE -> "منصة خارجية • YouTube"
                                    PlatformType.TIKTOK -> "منصة خارجية • TikTok"
                                    else -> "مساحة العمل • التقنية"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = when (item.platformType) {
                                    PlatformType.YOUTUBE -> ColorPlatformYouTube
                                    PlatformType.INSTAGRAM -> ColorPlatformInstagram
                                    PlatformType.TIKTOK -> ColorPlatformTikTok
                                    else -> PrimaryContainerViolet
                                }
                            )
                            Box(modifier = Modifier.size(3.dp).clip(CircleShape).background(OutlineVariantDark))
                            Text(
                                text = item.timeAgo,
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSurfaceVariantDark
                            )
                        }
                    }
                }

                if (item.isUnread) {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainerViolet)
                    )
                }
            }

            // Body text
            Text(
                text = item.contentText,
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceDark,
                lineHeight = 22.sp
            )

            // Optional Media Preview Thumbnail
            if (item.mediaThumbnailUrl != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLowest)
                ) {
                    AsyncImage(
                        model = item.mediaThumbnailUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    item.videoDuration?.let { duration ->
                        Surface(
                            color = CanvasDark.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = duration,
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSurfaceDark,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainerViolet),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "تشغيل",
                            tint = OnPrimaryContainerViolet,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Actions row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                item.actionPrimaryLabel?.let { primaryLabel ->
                    Surface(
                        color = SurfaceContainerHigh,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.clickable { onPrimaryAction() }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            val icon = when {
                                primaryLabel.contains("رد") -> Icons.Default.Reply
                                primaryLabel.contains("مشاهدة") -> Icons.Default.BookmarkAdd
                                primaryLabel.contains("إعجاب") -> Icons.Default.Favorite
                                primaryLabel.contains("تحليلات") -> Icons.Default.Insights
                                else -> Icons.Default.Tune
                            }
                            Icon(imageVector = icon, contentDescription = null, tint = PrimaryContainerViolet, modifier = Modifier.size(16.dp))
                            Text(text = primaryLabel, style = MaterialTheme.typography.labelMedium, color = OnSurfaceDark)
                        }
                    }
                }

                item.actionSecondaryLabel?.let { secondaryLabel ->
                    Row(
                        modifier = Modifier.clickable { onSecondaryAction() },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = secondaryLabel, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = OnSurfaceVariantDark,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }
        }
    }
}
