package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NavTab
import com.example.model.PlatformType
import com.example.ui.theme.*

/**
 * iOS-style Frosted Glass Container with subtle translucency, gradient rim lighting, and specular sheen.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    backgroundColor: Color = GlassSurfaceDark,
    borderColor: Color = GlassBorderSubtle,
    borderWidth: Dp = 1.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor.copy(alpha = (backgroundColor.alpha * 1.12f).coerceAtMost(0.85f)),
                        backgroundColor.copy(alpha = (backgroundColor.alpha * 0.82f).coerceAtLeast(0.30f))
                    )
                )
            )
            .border(
                borderWidth,
                Brush.linearGradient(
                    colors = listOf(
                        borderColor.copy(alpha = (borderColor.alpha * 1.6f).coerceAtMost(0.45f)),
                        borderColor.copy(alpha = (borderColor.alpha * 0.7f).coerceAtLeast(0.12f)),
                        Color.White.copy(alpha = 0.05f)
                    )
                ),
                shape
            )
    ) {
        // Specular top highlight line (characteristic of iOS liquid glass reflection)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            GlassHighlight.copy(alpha = 0.35f),
                            GlassHighlight.copy(alpha = 0.70f),
                            GlassHighlight.copy(alpha = 0.35f),
                            Color.Transparent
                        )
                    )
                )
        )
        // Subtle top gloss gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.04f),
                            Color.Transparent
                        )
                    )
                )
        )
        Column(content = content)
    }
}

@Composable
fun SocialHubTopBar(
    unreadCount: Int = 7,
    onOpenSearch: () -> Unit,
    onOpenNotifications: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("socialhub_top_bar"),
        color = Color.Transparent,
        tonalElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            GlassSurfaceElevated.copy(alpha = 0.88f),
                            GlassSurfaceDark.copy(alpha = 0.72f)
                        )
                    )
                )
        ) {
            // Specular bottom divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .align(Alignment.BottomCenter)
                    .background(GlassBorderSubtle)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand Logo & Title (Right side in RTL)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(GlassSurfaceDark)
                            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Hub,
                            contentDescription = "SocialHub Logo",
                            tint = PrimaryContainerViolet,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "المساحة الموحدة",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceDark
                        )
                        Text(
                            text = "SocialHub",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariantDark,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Action Icons (Left side in RTL)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    IconButton(
                        onClick = onOpenSearch,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(GlassSurfacePill)
                            .border(0.5.dp, GlassBorderSubtle, CircleShape)
                            .testTag("top_bar_search_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "البحث",
                            tint = OnSurfaceVariantDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Box {
                        IconButton(
                            onClick = onOpenNotifications,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(GlassSurfacePill)
                                .border(0.5.dp, GlassBorderSubtle, CircleShape)
                                .testTag("top_bar_notifications_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "التنبيهات",
                                tint = OnSurfaceVariantDark,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        if (unreadCount > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 8.dp, end = 8.dp)
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryContainerViolet)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainerViolet)
                            .border(1.dp, GlassBorder, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "الملف الشخصي",
                            tint = OnPrimaryViolet,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SocialHubBottomNav(
    currentTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("bottom_nav_bar"),
        color = Color.Transparent,
        tonalElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            GlassSurfaceElevated.copy(alpha = 0.82f),
                            GlassSurfaceDark.copy(alpha = 0.92f)
                        )
                    )
                )
        ) {
            // Specular top edge highlight line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.75.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                GlassBorder.copy(alpha = 0.55f),
                                GlassHighlight.copy(alpha = 0.80f),
                                GlassBorder.copy(alpha = 0.55f),
                                Color.Transparent
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavTab.entries.forEach { tab ->
                    val isSelected = currentTab == tab
                    val itemTag = when (tab) {
                        NavTab.HOME -> "bottom_nav_home"
                        NavTab.PLATFORMS -> "bottom_nav_platforms"
                        NavTab.SPACES -> "bottom_nav_spaces"
                        NavTab.ACTIVITY -> "bottom_nav_activity"
                        NavTab.SETTINGS -> "bottom_nav_settings"
                    }

                    Column(
                        modifier = Modifier
                            .testTag(itemTag)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) GlassSurfacePill else Color.Transparent)
                            .then(
                                if (isSelected) Modifier.border(0.5.dp, GlassBorder, RoundedCornerShape(14.dp))
                                else Modifier
                            )
                            .clickable { onTabSelected(tab) }
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val icon = when (tab) {
                            NavTab.HOME -> Icons.Filled.DynamicFeed
                            NavTab.PLATFORMS -> Icons.Filled.Share
                            NavTab.SPACES -> Icons.Filled.DashboardCustomize
                            NavTab.ACTIVITY -> Icons.Filled.Insights
                            NavTab.SETTINGS -> Icons.Filled.Tune
                        }

                        Icon(
                            imageVector = icon,
                            contentDescription = tab.title,
                            tint = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark,
                            modifier = Modifier.size(22.dp)
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) PrimaryContainerViolet else OnSurfaceVariantDark
                        )

                        if (isSelected) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Box(
                                modifier = Modifier
                                    .width(14.dp)
                                    .height(2.dp)
                                    .clip(RoundedCornerShape(1.dp))
                                    .background(PrimaryContainerViolet)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlatformBadge(
    platformType: PlatformType,
    modifier: Modifier = Modifier
) {
    val (label, color, icon) = when (platformType) {
        PlatformType.X -> Triple("𝕏", ColorPlatformX, Icons.Default.Tag)
        PlatformType.INSTAGRAM -> Triple("Instagram", ColorPlatformInstagram, Icons.Default.PhotoCamera)
        PlatformType.TIKTOK -> Triple("TikTok", ColorPlatformTikTok, Icons.Default.Audiotrack)
        PlatformType.YOUTUBE -> Triple("YouTube", ColorPlatformYouTube, Icons.Default.SmartDisplay)
        PlatformType.FACEBOOK -> Triple("Facebook", ColorPlatformFacebook, Icons.Default.Public)
        PlatformType.LINKEDIN -> Triple("LinkedIn", ColorPlatformLinkedIn, Icons.Default.Business)
        PlatformType.THREADS -> Triple("Threads", ColorPlatformThreads, Icons.Default.Chat)
        PlatformType.ALL -> Triple("الكل", PrimaryContainerViolet, Icons.Default.Widgets)
    }

    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(color.copy(alpha = 0.14f))
            .border(0.5.dp, color.copy(alpha = 0.35f), CircleShape)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(13.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = color
        )
    }
}

@Composable
fun ToastNotificationBanner(
    message: String?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = message != null,
        enter = fadeIn() + slideInVertically { it / 2 },
        exit = fadeOut() + slideOutVertically { it / 2 },
        modifier = modifier
    ) {
        if (message != null) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(GlassSurfaceElevated)
                    .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
                    .clickable { onDismiss() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Success",
                        tint = PrimaryContainerViolet,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceDark
                    )
                }
            }
        }
    }
}
