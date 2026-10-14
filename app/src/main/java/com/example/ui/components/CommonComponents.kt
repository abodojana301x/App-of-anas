package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
 * CompositionLocal to broadcast the active blur intensity across the UI hierarchy:
 * "high" (قوي - للأجهزة الرائدة), "low" (خفيف - للأجهزة المتوسطة), "off" (معطل - للأجهزة الاقتصادية والضعيفة).
 */
val LocalBlurIntensity = compositionLocalOf { "high" }

/**
 * Modern iOS Frosted Glass Container with squircle curves, semi-transparent backdrop, delicate rim highlight, and specular top glow.
 * Dynamically scales its backdrop opacity and highlights based on LocalBlurIntensity to ensure 60/120fps on all devices.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(22.dp),
    backgroundColor: Color = GlassSurfaceDark,
    borderColor: Color = GlassBorderSubtle,
    borderWidth: Dp = 0.75.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val blurIntensity = LocalBlurIntensity.current
    val (alphaTop, alphaBottom) = when (blurIntensity) {
        "off" -> Pair(0.96f, 0.94f)
        "low" -> Pair(0.86f, 0.68f)
        else -> Pair(0.78f, 0.52f)
    }

    Box(
        modifier = modifier
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor.copy(alpha = alphaTop),
                        backgroundColor.copy(alpha = alphaBottom)
                    )
                )
            )
            .border(
                borderWidth,
                Brush.verticalGradient(
                    colors = listOf(
                        borderColor.copy(alpha = if (blurIntensity == "off") 0.20f else 0.40f),
                        borderColor.copy(alpha = if (blurIntensity == "off") 0.08f else 0.12f)
                    )
                ),
                shape
            )
    ) {
        // Specular top highlight line (hidden on 'off' to optimize render passes on low-end devices)
        if (blurIntensity != "off") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.75.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                GlassHighlight.copy(alpha = 0.20f),
                                GlassHighlight.copy(alpha = if (blurIntensity == "low") 0.40f else 0.60f),
                                GlassHighlight.copy(alpha = 0.20f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
        Column(content = content)
    }
}

/**
 * Apple iOS Segmented Control (UISegmentedControl style) with sliding pill capsule and glass finish.
 */
@Composable
fun <T> CupertinoSegmentedControl(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    itemLabel: (T) -> String,
    modifier: Modifier = Modifier,
    itemTagPrefix: String = "segment"
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(AppleSystemGray6.copy(alpha = 0.85f))
            .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(14.dp))
            .padding(3.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = item == selectedItem
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(11.dp))
                        .background(
                            if (isSelected) AppleSystemGray5.copy(alpha = 0.90f) else Color.Transparent
                        )
                        .then(
                            if (isSelected) Modifier.border(0.5.dp, GlassBorder, RoundedCornerShape(11.dp))
                            else Modifier
                        )
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onItemSelected(item)
                        }
                        .padding(vertical = 8.dp)
                        .testTag("${itemTagPrefix}_${itemLabel(item)}"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = itemLabel(item),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        color = if (isSelected) Color.White else AppleSystemGray,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/**
 * Apple iOS-style Smooth Switch Toggle (UISwitch)
 */
@Composable
fun CupertinoSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    activeColor: Color = AppleSystemGreen,
    testTag: String = "cupertino_switch"
) {
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 20.dp else 2.dp,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f),
        label = "thumbOffset"
    )

    Box(
        modifier = modifier
            .width(50.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(if (checked) activeColor else AppleSystemGray5)
            .border(
                0.5.dp,
                if (checked) Color.Transparent else AppleSystemGray4,
                RoundedCornerShape(15.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onCheckedChange(!checked)
            }
            .padding(horizontal = 2.dp)
            .testTag(testTag),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(26.dp)
                .shadow(elevation = 2.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}

/**
 * Apple Inset Grouped Card (iOS Settings & Details Table) with frosted glass translucency
 */
@Composable
fun CupertinoGroupedCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(18.dp),
    backgroundColor: Color = AppleSystemGray6,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor.copy(alpha = 0.85f))
            .border(0.5.dp, GlassBorderSubtle, shape)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        content = content
    )
}

/**
 * iOS Bottom Sheet Drag Handle (Grabber)
 */
@Composable
fun CupertinoSheetHandle(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(38.dp)
                .height(5.dp)
                .clip(CircleShape)
                .background(AppleSystemGray3)
        )
    }
}

/**
 * iOS-styled Translucent Top Bar with Blur Backdrop, Specular Hairline, Brand Title, and Action controls.
 */
@Composable
fun SocialHubTopBar(
    unreadCount: Int = 7,
    blurIntensity: String = "high",
    onOpenSearch: () -> Unit,
    onOpenNotifications: () -> Unit,
    modifier: Modifier = Modifier
) {
    val blurRadius = when (blurIntensity) {
        "high" -> 24.dp
        "low" -> 8.dp
        else -> 0.dp
    }
    val backdropAlpha = when (blurIntensity) {
        "off" -> 0.96f
        "low" -> 0.88f
        else -> 0.78f
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("socialhub_top_bar"),
        color = Color.Transparent,
        tonalElevation = 0.dp
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 1. Frosted Glass Blur Backdrop Layer (Dynamic based on device performance)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .then(
                        if (blurRadius > 0.dp) Modifier.blur(radius = blurRadius, edgeTreatment = BlurredEdgeTreatment.Rectangle)
                        else Modifier
                    )
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                GlassTopBarBackdrop.copy(alpha = backdropAlpha),
                                GlassTopBarBackdrop.copy(alpha = backdropAlpha * 0.85f),
                                GlassTopBarBackdrop.copy(alpha = backdropAlpha * 0.70f)
                            )
                        )
                    )
            )

            // 2. Specular Hairline Separator at Bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                GlassBorderSubtle.copy(alpha = 0.8f),
                                GlassBorderSubtle.copy(alpha = 0.8f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // 3. Crisp Foreground Row Content
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand Header with iOS-inspired typography and icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(GlassSurfacePill)
                            .border(0.75.dp, GlassBorderSubtle, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Hub,
                            contentDescription = "SocialHub Logo",
                            tint = AppleSystemBlue,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "المساحة الموحدة",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = (-0.3).sp
                        )
                        Text(
                            text = "SocialHub",
                            style = MaterialTheme.typography.labelSmall,
                            color = AppleSystemGray,
                            letterSpacing = 0.2.sp
                        )
                    }
                }

                // Action Controls: Frosted Circular Glass Buttons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = onOpenSearch,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(GlassSurfacePill)
                            .border(0.5.dp, GlassBorderSubtle, CircleShape)
                            .testTag("top_bar_search_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "البحث",
                            tint = Color.White,
                            modifier = Modifier.size(19.dp)
                        )
                    }

                    Box {
                        IconButton(
                            onClick = onOpenNotifications,
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(GlassSurfacePill)
                                .border(0.5.dp, GlassBorderSubtle, CircleShape)
                                .testTag("top_bar_notifications_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "التنبيهات",
                                tint = Color.White,
                                modifier = Modifier.size(19.dp)
                            )
                        }
                        if (unreadCount > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 6.dp, end = 6.dp)
                                    .size(9.dp)
                                    .clip(CircleShape)
                                    .background(AppleSystemRed)
                                    .border(1.5.dp, CanvasDark, CircleShape)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(AppleSystemBlue)
                            .border(0.75.dp, GlassBorder, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "الملف الشخصي",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Modern iOS Floating Frosted Glass Tab Bar with Translucent Backdrop, Blur Effect & Specular Sheen.
 */
@Composable
fun SocialHubBottomNav(
    currentTab: NavTab,
    blurIntensity: String = "high",
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val blurRadius = when (blurIntensity) {
        "high" -> 20.dp
        "low" -> 8.dp
        else -> 0.dp
    }
    val backdropAlpha = when (blurIntensity) {
        "off" -> 0.96f
        "low" -> 0.88f
        else -> 0.78f
    }

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
                .padding(horizontal = 14.dp, vertical = 8.dp)
                .navigationBarsPadding()
        ) {
            // 1. Ambient Diffusion Blur Layer (Only for high blur mode)
            if (blurIntensity == "high") {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .blur(radius = 28.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                        .background(
                            color = GlassBottomBarBackdrop.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(28.dp)
                        )
                )
            }

            // 2. Frosted Glass Capsule with Internal Backdrop Blur
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(26.dp))
                    .border(
                        0.75.dp,
                        Brush.verticalGradient(
                            colors = listOf(
                                GlassBorder.copy(alpha = 0.40f),
                                GlassBorderSubtle.copy(alpha = 0.15f)
                            )
                        ),
                        RoundedCornerShape(26.dp)
                    )
            ) {
                // Internal blur backdrop layer
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .then(
                            if (blurRadius > 0.dp) Modifier.blur(radius = blurRadius, edgeTreatment = BlurredEdgeTreatment.Rectangle)
                            else Modifier
                        )
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    GlassBottomBarBackdrop.copy(alpha = backdropAlpha),
                                    GlassSurfaceDark.copy(alpha = backdropAlpha * 0.95f)
                                )
                            )
                        )
                )

                // Top edge specular sheen line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    GlassBorder.copy(alpha = 0.35f),
                                    GlassHighlight.copy(alpha = 0.80f),
                                    GlassBorder.copy(alpha = 0.35f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Crisp Foreground Tab Navigation Items
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
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

                        val icon = when (tab) {
                            NavTab.HOME -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
                            NavTab.PLATFORMS -> if (isSelected) Icons.Filled.GridView else Icons.Outlined.GridView
                            NavTab.SPACES -> if (isSelected) Icons.Filled.Layers else Icons.Outlined.Layers
                            NavTab.ACTIVITY -> if (isSelected) Icons.Filled.Notifications else Icons.Outlined.Notifications
                            NavTab.SETTINGS -> if (isSelected) Icons.Filled.Settings else Icons.Outlined.Settings
                        }

                        Column(
                            modifier = Modifier
                                .testTag(itemTag)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (isSelected) GlassSurfacePill.copy(alpha = 0.70f)
                                    else Color.Transparent
                                )
                                .then(
                                    if (isSelected) Modifier.border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(16.dp))
                                    else Modifier
                                )
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { onTabSelected(tab) }
                                .padding(horizontal = 14.dp, vertical = 7.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = tab.title,
                                tint = if (isSelected) AppleSystemBlue else AppleSystemGray,
                                modifier = Modifier.size(23.dp)
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = tab.title,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) AppleSystemBlue else AppleSystemGray,
                                fontSize = 10.sp
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
        PlatformType.ALL -> Triple("الكل", AppleSystemBlue, Icons.Default.Widgets)
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.12f))
            .border(0.5.dp, color.copy(alpha = 0.30f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = color,
            fontSize = 11.sp
        )
    }
}

/**
 * iOS Dynamic Island / Capsule Toast Banner with Glassmorphism
 */
@Composable
fun ToastNotificationBanner(
    message: String?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = message != null,
        enter = fadeIn(tween(200)) + slideInVertically(tween(250)) { -it },
        exit = fadeOut(tween(150)) + slideOutVertically(tween(200)) { -it },
        modifier = modifier
    ) {
        if (message != null) {
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(GlassSurfaceElevated.copy(alpha = 0.90f))
                    .border(0.75.dp, GlassBorder, RoundedCornerShape(24.dp))
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(24.dp))
                    .clickable { onDismiss() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(AppleSystemGreen.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Success",
                            tint = AppleSystemGreen,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}
