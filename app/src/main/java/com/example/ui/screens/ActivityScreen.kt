package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.ui.components.CupertinoSegmentedControl
import com.example.ui.components.GlassCard
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
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 82.dp, bottom = 104.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. iOS Large Title & Action Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "النشاط الموحد",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = (-0.5).sp
                        )
                        if (unreadCount > 0) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(AppleSystemBlue.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "$unreadCount جديدة",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppleSystemBlue,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(AppleSystemGray6)
                            .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(10.dp))
                            .clickable { onMarkAllRead() }
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = "قراءة الكل",
                            tint = AppleSystemBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "قراءة الكل",
                            style = MaterialTheme.typography.labelSmall,
                            color = AppleSystemBlue,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // 2. iOS Segmented Control for Activity Tabs
                CupertinoSegmentedControl(
                    items = ActivityTab.entries,
                    selectedItem = currentTab,
                    onItemSelected = onTabSelected,
                    itemLabel = { it.title },
                    itemTagPrefix = "activity_tab"
                )
            }
        }

        // 3. Platform Filter Strip
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    Pair(PlatformType.ALL, "الكل"),
                    Pair(PlatformType.X, "𝕏"),
                    Pair(PlatformType.INSTAGRAM, "Instagram"),
                    Pair(PlatformType.YOUTUBE, "YouTube"),
                    Pair(PlatformType.TIKTOK, "TikTok")
                ).forEach { (type, label) ->
                    val isSelected = selectedFilter == type
                    Box(
                        modifier = Modifier
                            .height(34.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) AppleSystemBlue else AppleSystemGray6)
                            .border(
                                0.5.dp,
                                if (isSelected) Color.Transparent else GlassBorderSubtle,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { onFilterSelected(type) }
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) Color.White else AppleSystemGray
                        )
                    }
                }
            }
        }

        // 4. Activity Items List (iOS Inset Style)
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
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .height(3.dp)
                        .clip(CircleShape)
                        .background(AppleSystemGray5)
                )
                Text(
                    text = "أنت على اطلاع بكل جديد في مساحتك",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppleSystemGray
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
        shape = RoundedCornerShape(20.dp),
        backgroundColor = AppleSystemGray6,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row: Avatar, Name, Platform tag, Unread Indicator
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
                        Box(modifier = Modifier.size(40.dp)) {
                            AsyncImage(
                                model = item.avatarUrl,
                                contentDescription = item.authorName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .border(0.5.dp, GlassBorderSubtle, CircleShape)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(AppleSystemGray5),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = AppleSystemBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = item.authorName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            if (item.isSystemNotification) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(AppleSystemBlue.copy(alpha = 0.15f))
                                        .padding(horizontal = 6.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "نظام",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 10.sp,
                                        color = AppleSystemBlue,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                        Text(
                            text = item.timeAgo,
                            style = MaterialTheme.typography.labelSmall,
                            color = AppleSystemGray
                        )
                    }
                }

                if (item.isUnread) {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(9.dp)
                            .clip(CircleShape)
                            .background(AppleSystemBlue)
                    )
                }
            }

            // Body text
            Text(
                text = item.contentText,
                style = MaterialTheme.typography.bodyMedium,
                color = AppleSystemGray,
                lineHeight = 21.sp
            )

            // Optional Media Thumbnail
            if (item.mediaThumbnailUrl != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(AppleSystemGray5)
                        .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(14.dp))
                ) {
                    AsyncImage(
                        model = item.mediaThumbnailUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Action Buttons
            if (item.actionPrimaryLabel != null || item.actionSecondaryLabel != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        item.actionSecondaryLabel?.let { label ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(AppleSystemGray5)
                                    .clickable { onSecondaryAction() }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White
                                )
                            }
                        }
                        item.actionPrimaryLabel?.let { label ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(AppleSystemBlue)
                                    .clickable { onPrimaryAction() }
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
