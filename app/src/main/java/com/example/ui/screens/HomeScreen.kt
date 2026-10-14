package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.*
import com.example.ui.components.CupertinoSegmentedControl
import com.example.ui.components.GlassCard
import com.example.ui.components.PlatformBadge
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    feedItems: List<FeedItem>,
    selectedPlatform: PlatformType,
    selectedCategory: FeedCategory,
    onSelectPlatform: (PlatformType) -> Unit,
    onSelectCategory: (FeedCategory) -> Unit,
    onToggleLike: (String) -> Unit,
    onToggleSave: (String) -> Unit,
    onNextSlide: (String) -> Unit,
    onPrevSlide: (String) -> Unit,
    onRefreshFeed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredItems = remember(feedItems, selectedPlatform, selectedCategory) {
        feedItems.filter { item ->
            val matchPlatform = selectedPlatform == PlatformType.ALL || item.platformType == selectedPlatform
            val matchCategory = when (selectedCategory) {
                FeedCategory.ALL -> true
                FeedCategory.VIDEOS -> item.mediaType == MediaType.VIDEO_VERTICAL || item.mediaType == MediaType.VIDEO_WIDESCREEN
                FeedCategory.POSTS -> item.mediaType == MediaType.POST_TEXT || item.mediaType == MediaType.CAROUSEL_IMAGES
                FeedCategory.BOOKMARKS -> item.isSaved
                FeedCategory.TOP_ENGAGED -> true
            }
            matchPlatform && matchCategory
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_feed_list"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 82.dp, bottom = 104.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. iOS Large Title & Action Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(AppleSystemGreen)
                            )
                            Text(
                                text = "مُحدّث بالكامل • متزامن",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = AppleSystemGreen,
                                fontSize = 11.sp
                            )
                        }
                        Text(
                            text = "مساحتك الاجتماعية",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = (-0.5).sp
                        )
                    }

                    // iOS Circular Action Pill
                    IconButton(
                        onClick = onRefreshFeed,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(AppleSystemGray6)
                            .border(0.5.dp, GlassBorderSubtle, CircleShape)
                            .testTag("home_refresh_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Sync,
                            contentDescription = "تحديث المحتوى",
                            tint = AppleSystemBlue,
                            modifier = Modifier.size(19.dp)
                        )
                    }
                }

                // 2. iOS Segmented Control for Categories (الكل، منشورات، فيديوهات، المحفوظات)
                CupertinoSegmentedControl(
                    items = listOf(
                        FeedCategory.ALL,
                        FeedCategory.POSTS,
                        FeedCategory.VIDEOS,
                        FeedCategory.BOOKMARKS
                    ),
                    selectedItem = selectedCategory,
                    onItemSelected = onSelectCategory,
                    itemLabel = { it.label },
                    itemTagPrefix = "cat"
                )
            }
        }

        // 3. Platform Strip (Apple Rounded Squircle Chips)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlatformChip(
                    label = "الكل",
                    isSelected = selectedPlatform == PlatformType.ALL,
                    indicatorColor = AppleSystemBlue,
                    icon = Icons.Default.Widgets,
                    onClick = { onSelectPlatform(PlatformType.ALL) }
                )
                PlatformChip(
                    label = "𝕏",
                    isSelected = selectedPlatform == PlatformType.X,
                    indicatorColor = ColorPlatformX,
                    icon = Icons.Default.Tag,
                    onClick = { onSelectPlatform(PlatformType.X) }
                )
                PlatformChip(
                    label = "إنستغرام",
                    isSelected = selectedPlatform == PlatformType.INSTAGRAM,
                    indicatorColor = ColorPlatformInstagram,
                    icon = Icons.Default.PhotoCamera,
                    onClick = { onSelectPlatform(PlatformType.INSTAGRAM) }
                )
                PlatformChip(
                    label = "تيك توك",
                    isSelected = selectedPlatform == PlatformType.TIKTOK,
                    indicatorColor = ColorPlatformTikTok,
                    icon = Icons.Default.Audiotrack,
                    onClick = { onSelectPlatform(PlatformType.TIKTOK) }
                )
                PlatformChip(
                    label = "يوتيوب",
                    isSelected = selectedPlatform == PlatformType.YOUTUBE,
                    indicatorColor = ColorPlatformYouTube,
                    icon = Icons.Default.SmartDisplay,
                    onClick = { onSelectPlatform(PlatformType.YOUTUBE) }
                )
                PlatformChip(
                    label = "فيسبوك",
                    isSelected = selectedPlatform == PlatformType.FACEBOOK,
                    indicatorColor = ColorPlatformFacebook,
                    icon = Icons.Default.Public,
                    onClick = { onSelectPlatform(PlatformType.FACEBOOK) }
                )
            }
        }

        // 4. Feed Items List
        items(filteredItems, key = { it.id }) { item ->
            when (item.mediaType) {
                MediaType.VIDEO_VERTICAL -> TikTokFeedCard(
                    item = item,
                    onToggleLike = { onToggleLike(item.id) },
                    onToggleSave = { onToggleSave(item.id) }
                )
                MediaType.POST_TEXT -> XPostFeedCard(
                    item = item,
                    onToggleLike = { onToggleLike(item.id) },
                    onToggleSave = { onToggleSave(item.id) }
                )
                MediaType.VIDEO_WIDESCREEN -> YouTubeFeedCard(
                    item = item,
                    onToggleLike = { onToggleLike(item.id) },
                    onToggleSave = { onToggleSave(item.id) }
                )
                MediaType.CAROUSEL_IMAGES -> InstagramFeedCard(
                    item = item,
                    onToggleLike = { onToggleLike(item.id) },
                    onToggleSave = { onToggleSave(item.id) },
                    onNextSlide = { onNextSlide(item.id) },
                    onPrevSlide = { onPrevSlide(item.id) }
                )
            }
        }

        // 5. Completion State (iOS Inset Card)
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(AppleSystemGreen.copy(alpha = 0.15f))
                            .border(0.5.dp, AppleSystemGreen.copy(alpha = 0.35f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "مكتمل",
                            tint = AppleSystemGreen,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Text(
                        text = "أنت على اطلاع بكل جديد",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "تمت مزامنة جميع الحسابات المربوطة بنجاح، استمتع بيومك الرقمي المنظم.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppleSystemGray
                    )
                }
            }
        }
    }
}

@Composable
fun PlatformChip(
    label: String,
    isSelected: Boolean,
    indicatorColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) AppleSystemBlue else AppleSystemGray6,
        animationSpec = tween(180),
        label = "chipBg"
    )
    val contentColor = if (isSelected) Color.White else AppleSystemGray

    Row(
        modifier = Modifier
            .height(36.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(
                0.5.dp,
                if (isSelected) Color.Transparent else GlassBorderSubtle,
                RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(if (isSelected) Color.White else indicatorColor)
        )
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = contentColor,
            fontSize = 12.sp
        )
    }
}

@Composable
fun TikTokFeedCard(
    item: FeedItem,
    onToggleLike: () -> Unit,
    onToggleSave: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("feed_item_tiktok"),
        backgroundColor = AppleSystemGray6
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header: Author Avatar, Verified badge, Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.size(40.dp)) {
                        AsyncImage(
                            model = item.authorAvatarUrl,
                            contentDescription = item.authorName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .border(0.5.dp, GlassBorderSubtle, CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .size(15.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00F2FE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Audiotrack,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(9.dp)
                            )
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
                                color = Color.White
                            )
                            if (item.isVerified) {
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = "موثق",
                                    tint = AppleSystemBlue,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Text(
                            text = item.timeAgo,
                            style = MaterialTheme.typography.labelSmall,
                            color = AppleSystemGray
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item.trendingBadge?.let {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(ColorPlatformTikTok.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelSmall,
                                color = ColorPlatformTikTok,
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp
                            )
                        }
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreHoriz,
                            contentDescription = "خيارات",
                            tint = AppleSystemGray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Vertical Media Preview Container (Aspect ~ 9:13)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(9f / 13f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(AppleSystemGray5)
                    .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(18.dp))
            ) {
                AsyncImage(
                    model = item.mediaUrl,
                    contentDescription = item.contentText,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gentle gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.15f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.90f)
                                )
                            )
                        )
                )

                // Center Play Button with glass aura
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f))
                        .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "تشغيل",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Bottom Content & Counters
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = item.contentText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.clickable { onToggleLike() }
                            ) {
                                Icon(
                                    imageVector = if (item.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = "إعجاب",
                                    tint = if (item.isLiked) AppleSystemRed else Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = item.likesCount,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ChatBubbleOutline,
                                    contentDescription = "تعليق",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = item.commentsCount,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White
                                )
                            }
                        }

                        item.videoDuration?.let {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color.Black.copy(alpha = 0.60f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }

            // Quick Actions Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (item.isSaved) AppleSystemBlue.copy(alpha = 0.15f) else AppleSystemGray5)
                        .clickable { onToggleSave() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = if (item.isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkAdd,
                            contentDescription = null,
                            tint = if (item.isSaved) AppleSystemBlue else AppleSystemGray,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = if (item.isSaved) "محفوظ" else "حفظ",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (item.isSaved) AppleSystemBlue else Color.White
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable { }
                ) {
                    Text(
                        text = "فتح في TikTok",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppleSystemGray
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = AppleSystemGray,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun XPostFeedCard(
    item: FeedItem,
    onToggleLike: () -> Unit,
    onToggleSave: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("feed_item_x"),
        backgroundColor = AppleSystemGray6
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Profile & Handle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AsyncImage(
                        model = item.authorAvatarUrl,
                        contentDescription = item.authorName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(0.5.dp, GlassBorderSubtle, CircleShape)
                    )
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = item.authorName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Text(
                                text = "𝕏",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Text(
                            text = item.authorHandle,
                            style = MaterialTheme.typography.labelSmall,
                            color = AppleSystemGray
                        )
                    }
                }

                IconButton(
                    onClick = { },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = "المزيد",
                        tint = AppleSystemGray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Text
            Text(
                text = item.contentText,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                lineHeight = 24.sp
            )

            // Quote Metric Micro-Badge
            item.quoteMetricBadge?.let { quoteBadge ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppleSystemGray5)
                        .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
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
                                imageVector = Icons.Filled.AutoGraph,
                                contentDescription = null,
                                tint = AppleSystemBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = quoteBadge,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                        }
                        item.quoteActionLabel?.let { actionLabel ->
                            Text(
                                text = actionLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = AppleSystemBlue,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Interaction Bar (iOS Style with Apple Grays & Accents)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Chat,
                        contentDescription = "رد",
                        tint = AppleSystemGray,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = item.commentsCount,
                        style = MaterialTheme.typography.labelSmall,
                        color = AppleSystemGray
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Repeat,
                        contentDescription = "إعادة نشر",
                        tint = AppleSystemGray,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = item.sharesCount ?: "0",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppleSystemGray
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable { onToggleLike() }
                ) {
                    Icon(
                        imageVector = if (item.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "إعجاب",
                        tint = if (item.isLiked) AppleSystemRed else AppleSystemGray,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = item.likesCount,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (item.isLiked) AppleSystemRed else AppleSystemGray
                    )
                }

                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (item.isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "حفظ",
                        tint = if (item.isSaved) AppleSystemBlue else AppleSystemGray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun YouTubeFeedCard(
    item: FeedItem,
    onToggleLike: () -> Unit,
    onToggleSave: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("feed_item_youtube"),
        backgroundColor = AppleSystemGray6
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.size(40.dp)) {
                        AsyncImage(
                            model = item.authorAvatarUrl,
                            contentDescription = item.authorName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .size(15.dp)
                                .clip(CircleShape)
                                .background(ColorPlatformYouTube),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(9.dp)
                            )
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
                                color = Color.White
                            )
                            if (item.isVerified) {
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = "موثق",
                                    tint = ColorPlatformYouTube,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Text(
                            text = item.authorHandle,
                            style = MaterialTheme.typography.labelSmall,
                            color = AppleSystemGray
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ColorPlatformYouTube.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "فيديو كامل",
                        style = MaterialTheme.typography.labelSmall,
                        color = ColorPlatformYouTube,
                        fontSize = 11.sp
                    )
                }
            }

            // 16:9 Thumbnail
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppleSystemGray5)
                    .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = item.mediaUrl,
                    contentDescription = item.contentText,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Duration badge
                item.videoDuration?.let { duration ->
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black.copy(alpha = 0.75f))
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = duration,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontSize = 11.sp
                        )
                    }
                }

                // Play icon overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(ColorPlatformYouTube),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "تشغيل",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // Title and Details
            Text(
                text = item.contentText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                lineHeight = 22.sp
            )

            // Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (item.isSaved) AppleSystemBlue.copy(alpha = 0.15f) else AppleSystemGray5)
                            .clickable { onToggleSave() }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (item.isSaved) "محفوظ للمشاهدة" else "المشاهدة لاحقاً",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (item.isSaved) AppleSystemBlue else Color.White
                        )
                    }

                    IconButton(
                        onClick = onToggleLike,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AppleSystemGray5)
                    ) {
                        Icon(
                            imageVector = if (item.isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                            contentDescription = "إعجاب",
                            tint = if (item.isLiked) AppleSystemBlue else AppleSystemGray,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable { }
                ) {
                    Text(
                        text = "فتح في YouTube",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppleSystemGray
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = AppleSystemGray,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun InstagramFeedCard(
    item: FeedItem,
    onToggleLike: () -> Unit,
    onToggleSave: () -> Unit,
    onNextSlide: () -> Unit,
    onPrevSlide: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("feed_item_instagram"),
        backgroundColor = AppleSystemGray6
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
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
                            .clip(CircleShape)
                            .border(
                                1.5.dp,
                                Brush.linearGradient(listOf(Color(0xFFF59E0B), ColorPlatformInstagram, AppleSystemPurple)),
                                CircleShape
                            )
                            .padding(2.dp)
                    ) {
                        AsyncImage(
                            model = item.authorAvatarUrl,
                            contentDescription = item.authorName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
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
                                color = Color.White
                            )
                            if (item.isVerified) {
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = "موثق",
                                    tint = AppleSystemBlue,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Text(
                            text = item.authorHandle,
                            style = MaterialTheme.typography.labelSmall,
                            color = AppleSystemGray
                        )
                    }
                }

                IconButton(
                    onClick = { },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = "خيارات",
                        tint = AppleSystemGray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Carousel Image Box (1:1 Square)
            val currentImageUrl = item.carouselImages.getOrElse(item.currentSlideIndex) { "" }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(AppleSystemGray5)
                    .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(18.dp))
            ) {
                AsyncImage(
                    model = currentImageUrl,
                    contentDescription = item.contentText,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Slide Indicator Tag (Top Left)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.65f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "${item.currentSlideIndex + 1} / ${item.carouselImages.size}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontSize = 11.sp
                    )
                }

                // Prev & Next Controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onPrevSlide,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "السابق",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = onNextSlide,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "التالي",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Dot Indicators Bar
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item.carouselImages.indices.forEach { index ->
                        val isCurrent = index == item.currentSlideIndex
                        Box(
                            modifier = Modifier
                                .height(5.dp)
                                .width(if (isCurrent) 16.dp else 5.dp)
                                .clip(CircleShape)
                                .background(if (isCurrent) AppleSystemBlue else Color.White.copy(alpha = 0.4f))
                        )
                    }
                }
            }

            // Action Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onToggleLike,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (item.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "إعجاب",
                            tint = if (item.isLiked) AppleSystemRed else Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ModeComment,
                            contentDescription = "تعليق",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Send,
                            contentDescription = "إرسال",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (item.isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "حفظ",
                        tint = if (item.isSaved) AppleSystemBlue else Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Likes and Caption
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = "${item.likesCount} إعجاب",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Text(
                    text = "${item.authorName}  ${item.contentText}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppleSystemGray,
                    lineHeight = 22.sp
                )

                Text(
                    text = "عرض جميع التعليقات الـ ${item.commentsCount}",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppleSystemGray2,
                    modifier = Modifier
                        .clickable { }
                        .padding(top = 2.dp)
                )
            }
        }
    }
}
