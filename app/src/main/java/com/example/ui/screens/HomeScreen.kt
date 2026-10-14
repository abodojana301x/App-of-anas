package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import com.example.ui.components.GlassCard
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
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Top Greeting & Feed Controls
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
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
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(PrimaryContainerViolet)
                        )
                        Text(
                            text = "تزامن حي وفعال",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryContainerViolet,
                            letterSpacing = 0.5.sp
                        )
                    }
                    Text(
                        text = "مساحتك الاجتماعية",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceDark
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onRefreshFeed,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainer)
                            .testTag("home_refresh_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Sync,
                            contentDescription = "تحديث المحتوى",
                            tint = OnSurfaceVariantDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainer)
                            .clickable { }
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Tune,
                            contentDescription = "تصفية",
                            tint = PrimaryContainerViolet,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "تصفية",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Medium,
                            color = OnSurfaceDark
                        )
                    }
                }
            }
        }

        // 2. Platform Selector (Horizontal Scrollable Strip)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PlatformChip(
                    label = "الكل",
                    isSelected = selectedPlatform == PlatformType.ALL,
                    indicatorColor = PrimaryContainerViolet,
                    icon = Icons.Default.Widgets,
                    onClick = { onSelectPlatform(PlatformType.ALL) }
                )
                PlatformChip(
                    label = "إكس",
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

        // 3. Feed Category Pills
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FeedCategory.entries.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        color = if (isSelected) PrimaryContainerViolet.copy(alpha = 0.25f) else GlassSurfacePill,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(0.5.dp, if (isSelected) PrimaryContainerViolet.copy(alpha = 0.5f) else GlassBorderSubtle),
                        modifier = Modifier
                            .clickable { onSelectCategory(cat) }
                            .padding(0.dp)
                    ) {
                        Text(
                            text = cat.label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }

        // 4. Feed Items
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

        // 5. Completion Footer
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
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
                            .background(GlassSurfacePill)
                            .border(0.5.dp, GlassBorderSubtle, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DoneAll,
                            contentDescription = "مكتمل",
                            tint = PrimaryContainerViolet,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Text(
                        text = "أنت على اطلاع بكل جديد!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurfaceDark
                    )
                    Text(
                        text = "تمت مزامنة جميع الحسابات المربوطة بنجاح، استمتع بيومك الرقمي المنظم.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariantDark
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
        targetValue = if (isSelected) PrimaryContainerViolet else GlassSurfacePill,
        animationSpec = tween(200)
    )
    val textColor = if (isSelected) OnPrimaryContainerViolet else OnSurfaceDark

    Row(
        modifier = Modifier
            .height(38.dp)
            .clip(CircleShape)
            .background(bgColor)
            .border(
                0.5.dp,
                if (isSelected) GlassBorder else GlassBorderSubtle,
                CircleShape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(if (isSelected) OnPrimaryContainerViolet else indicatorColor)
        )
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = textColor,
            modifier = Modifier.size(15.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = textColor
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
        backgroundColor = GlassSurfaceDark
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
                    Box(modifier = Modifier.size(42.dp)) {
                        AsyncImage(
                            model = item.authorAvatarUrl,
                            contentDescription = item.authorName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF082F49)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Audiotrack,
                                contentDescription = null,
                                tint = ColorPlatformTikTok,
                                modifier = Modifier.size(10.dp)
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
                                color = OnSurfaceDark
                            )
                            if (item.isVerified) {
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = "موثق",
                                    tint = ColorPlatformTikTok,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Text(
                            text = item.timeAgo,
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariantDark
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item.trendingBadge?.let {
                        Surface(
                            color = SurfaceContainer,
                            shape = CircleShape
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.TrendingUp,
                                    contentDescription = null,
                                    tint = ColorPlatformTikTok,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ColorPlatformTikTok
                                )
                            }
                        }
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "خيارات",
                            tint = OnSurfaceVariantDark,
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
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerHighest)
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
                                    Color.Black.copy(alpha = 0.2f),
                                    Color.Transparent,
                                    CanvasDark.copy(alpha = 0.95f)
                                )
                            )
                        )
                )

                // Center Play Button with glowing aura
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(PrimaryContainerViolet.copy(alpha = 0.88f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "تشغيل",
                        tint = OnPrimaryContainerViolet,
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
                                    tint = if (item.isLiked) ColorPlatformInstagram else Color.White,
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
                            item.sharesCount?.let {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Share,
                                        contentDescription = "مشاركة",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        item.videoDuration?.let {
                            Surface(
                                color = Color.Black.copy(alpha = 0.55f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ColorPlatformTikTok,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
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
                Surface(
                    color = SurfaceContainer,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.clickable { onToggleSave() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = if (item.isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkAdd,
                            contentDescription = null,
                            tint = PrimaryContainerViolet,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (item.isSaved) "محفوظ بالفهرس" else "حفظ للفهرس",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceDark
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
                        color = OnSurfaceVariantDark
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = OnSurfaceVariantDark,
                        modifier = Modifier.size(14.dp)
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
        backgroundColor = GlassSurfaceDark
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
                            .size(42.dp)
                            .clip(CircleShape)
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
                                color = OnSurfaceDark
                            )
                            Text(
                                text = "𝕏",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnSurfaceDark
                            )
                        }
                        Text(
                            text = item.authorHandle,
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariantDark
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item.trendingBadge?.let {
                        Surface(
                            color = SurfaceContainer,
                            shape = CircleShape
                        ) {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSurfaceVariantDark,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
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
                            tint = OnSurfaceVariantDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Analytical Arabic Text
            Text(
                text = item.contentText,
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurfaceDark,
                lineHeight = 24.sp
            )

            // Quote Metric Micro-Badge
            item.quoteMetricBadge?.let { quoteBadge ->
                Surface(
                    color = SurfaceContainer,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
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
                                tint = PrimaryContainerViolet,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = quoteBadge,
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSurfaceDark
                            )
                        }
                        item.quoteActionLabel?.let { actionLabel ->
                            Text(
                                text = actionLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryContainerViolet,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Interaction Bar (X Style)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
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
                        tint = OnSurfaceVariantDark,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = item.commentsCount,
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariantDark
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Repeat,
                        contentDescription = "إعادة نشر",
                        tint = OnSurfaceVariantDark,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = item.sharesCount ?: "0",
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariantDark
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
                        tint = if (item.isLiked) ColorPlatformInstagram else OnSurfaceVariantDark,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = item.likesCount,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (item.isLiked) ColorPlatformInstagram else OnSurfaceVariantDark
                    )
                }

                item.impressionsCount?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Analytics,
                            contentDescription = "مشاهدات",
                            tint = OnSurfaceVariantDark,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariantDark
                        )
                    }
                }

                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (item.isSaved) Icons.Filled.Bookmark else Icons.Outlined.Share,
                        contentDescription = "مشاركة أو حفظ",
                        tint = if (item.isSaved) PrimaryContainerViolet else OnSurfaceVariantDark,
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
        backgroundColor = GlassSurfaceDark
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
                    Box(modifier = Modifier.size(42.dp)) {
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
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(ColorPlatformYouTube),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(10.dp)
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
                                color = OnSurfaceDark
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
                            color = OnSurfaceVariantDark
                        )
                    }
                }

                Surface(
                    color = Color(0xFF450A0A),
                    shape = CircleShape
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(ColorPlatformYouTube)
                        )
                        Text(
                            text = "فيديو كامل",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFF87171)
                        )
                    }
                }
            }

            // 16:9 Thumbnail
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerHighest)
            ) {
                AsyncImage(
                    model = item.mediaUrl,
                    contentDescription = item.contentText,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // 4K Stamp
                item.resolutionBadge?.let { res ->
                    Surface(
                        color = CanvasDark.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = res,
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariantDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                // Duration badge
                item.videoDuration?.let { duration ->
                    Surface(
                        color = CanvasDark.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = ColorPlatformYouTube,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = duration,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                        }
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
                text = "المقارنة الشاملة: هل يستحق حاسوب الذكاء الاصطناعي المحلي الترقية في 2025؟",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = OnSurfaceDark,
                lineHeight = 24.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "43 ألف مشاهدة", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                Text(text = "•", color = OnSurfaceVariantDark)
                Text(text = "منذ 5 ساعات", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariantDark)
                Text(text = "•", color = OnSurfaceVariantDark)
                Text(text = "98% تقييم إيجابي", style = MaterialTheme.typography.labelSmall, color = Color(0xFF34D399))
            }

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
                    Surface(
                        color = SurfaceContainer,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.clickable { onToggleSave() }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlaylistAdd,
                                contentDescription = null,
                                tint = ColorPlatformYouTube,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "المشاهدة لاحقاً",
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSurfaceDark
                            )
                        }
                    }

                    IconButton(
                        onClick = onToggleLike,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainer)
                    ) {
                        Icon(
                            imageVector = if (item.isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                            contentDescription = "إعجاب",
                            tint = if (item.isLiked) PrimaryContainerViolet else OnSurfaceVariantDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable { }
                ) {
                    Text(
                        text = "عرض الوصف والروابط",
                        style = MaterialTheme.typography.labelSmall,
                        color = PrimaryContainerViolet
                    )
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = PrimaryContainerViolet,
                        modifier = Modifier.size(14.dp)
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
        backgroundColor = GlassSurfaceDark
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
                            .size(42.dp)
                            .clip(CircleShape)
                            .border(
                                1.5.dp,
                                Brush.linearGradient(listOf(Color(0xFFF59E0B), ColorPlatformInstagram, PrimaryContainerViolet)),
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
                                color = OnSurfaceDark
                            )
                            if (item.isVerified) {
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = "موثق",
                                    tint = ColorPlatformInstagram,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Text(
                            text = item.authorHandle,
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariantDark
                        )
                    }
                }

                IconButton(
                    onClick = { },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "خيارات",
                        tint = OnSurfaceVariantDark,
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
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerHighest)
            ) {
                AsyncImage(
                    model = currentImageUrl,
                    contentDescription = item.contentText,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Slide Indicator Tag (Top Left)
                Surface(
                    color = CanvasDark.copy(alpha = 0.75f),
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "${item.currentSlideIndex + 1} / ${item.carouselImages.size}",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceDark
                        )
                        Icon(
                            imageVector = Icons.Default.Collections,
                            contentDescription = null,
                            tint = ColorPlatformInstagram,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                // Prev & Next Quick Clickable Controls
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
                            .background(CanvasDark.copy(alpha = 0.6f))
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
                            .background(CanvasDark.copy(alpha = 0.6f))
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
                                .height(6.dp)
                                .width(if (isCurrent) 18.dp else 6.dp)
                                .clip(CircleShape)
                                .background(if (isCurrent) PrimaryContainerViolet else Color.White.copy(alpha = 0.4f))
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
                            tint = if (item.isLiked) ColorPlatformInstagram else OnSurfaceDark,
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
                            tint = OnSurfaceDark,
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
                            tint = OnSurfaceDark,
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
                        tint = if (item.isSaved) PrimaryContainerViolet else OnSurfaceDark,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Likes and Caption
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = "أعجب به فهد التميمي و ${item.likesCount} آخرين",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurfaceDark
                )

                Text(
                    text = "${item.authorName}  ${item.contentText}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariantDark,
                    lineHeight = 22.sp
                )

                Text(
                    text = "عرض جميع التعليقات الـ ${item.commentsCount}",
                    style = MaterialTheme.typography.labelSmall,
                    color = OutlineDark,
                    modifier = Modifier
                        .clickable { }
                        .padding(top = 2.dp)
                )
            }
        }
    }
}
