package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppPreferences
import com.example.ui.components.CupertinoSegmentedControl
import com.example.ui.components.CupertinoSwitch
import com.example.ui.theme.*

/**
 * Apple iOS Inset Grouped Settings Screen
 * Features intuitive controls, blur quality engine tuning (High/Low/Off), accent palettes,
 * media toggles, and memory optimization with instant tactile feedback.
 */
@Composable
fun SettingsScreen(
    preferences: AppPreferences,
    onUpdatePreferences: ((AppPreferences) -> AppPreferences) -> Unit,
    onClearCache: () -> Unit,
    onClearSearchHistory: () -> Unit,
    onResetDefaults: () -> Unit,
    onSavePreferences: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("settings_screen_list"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 84.dp, bottom = 110.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 1. iOS Large Navigation Title
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "الإعدادات والتفضيلات",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = (-0.6).sp
                )
                Text(
                    text = "تحكم بالمظهر، دقة التغبيش الزجاجي، وتجربة تدفق المحتوى بأسلوب أبل",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppleSystemGray
                )
            }
        }

        // 2. Section: Appearance & Blur Engine
        item {
            SettingsSectionHeader(title = "المظهر ومحرك التغبيش الزجاجي (BLUR)")
            Spacer(modifier = Modifier.height(8.dp))
            SettingsGroupedCard {
                // Theme Mode Segmented Control
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SettingsRowLabel(
                            icon = Icons.Default.DarkMode,
                            iconBg = AppleSystemIndigo,
                            title = "نمط العرض"
                        )
                        Text(
                            text = if (preferences.themeMode == "light") "نهاري نقي" else "داكن OLED فاخر",
                            style = MaterialTheme.typography.labelMedium,
                            color = AppleSystemGray,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    CupertinoSegmentedControl(
                        items = listOf("dark", "light"),
                        selectedItem = preferences.themeMode,
                        onItemSelected = { mode -> onUpdatePreferences { it.copy(themeMode = mode) } },
                        itemLabel = { if (it == "dark") "داكن OLED" else "فاتح كلاسيكي" },
                        itemTagPrefix = "theme_mode"
                    )
                }

                SettingsDivider()

                // NEW: Blur & Glass Intensity Selector (ضعيف ولا قوي ولا معطل)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SettingsRowLabel(
                            icon = Icons.Default.BlurOn,
                            iconBg = AppleSystemBlue,
                            title = "دقة تأثير الـ Blur الزجاجي",
                            subtitle = "مخصص ليتناسب مع قوة المعالج والبطارية"
                        )
                        Text(
                            text = when (preferences.blurIntensity) {
                                "high" -> "قوي"
                                "low" -> "خفيف"
                                else -> "معطل"
                            },
                            style = MaterialTheme.typography.labelMedium,
                            color = AppleSystemBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    CupertinoSegmentedControl(
                        items = listOf("high", "low", "off"),
                        selectedItem = preferences.blurIntensity,
                        onItemSelected = { intensity ->
                            onUpdatePreferences { it.copy(blurIntensity = intensity) }
                        },
                        itemLabel = {
                            when (it) {
                                "high" -> "قوي (أجهزة قوية)"
                                "low" -> "خفيف (متوسطة)"
                                else -> "معطل (اقتصادية)"
                            }
                        },
                        itemTagPrefix = "blur_intensity"
                    )

                    // Helper badge explaining device benefit
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(AppleSystemGray5.copy(alpha = 0.60f))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = when (preferences.blurIntensity) {
                                    "high" -> Icons.Default.AutoAwesome
                                    "low" -> Icons.Default.Speed
                                    else -> Icons.Default.BatteryChargingFull
                                },
                                contentDescription = null,
                                tint = AppleSystemBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = when (preferences.blurIntensity) {
                                    "high" -> "تأثير ضبابي كامل 24dp وعمق ضوئي عالي (مظهر فاخر للأجهزة القوية)"
                                    "low" -> "تأثير ضبابي مخفف 8dp فائق السلاسة (متوازن للهواتف المتوسطة)"
                                    else -> "واجهات صلبة وسريعة بدون معالجة ضبابية (أعلى أداء وتوفير للأجهزة الضعيفة)"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = AppleSystemGray,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                SettingsDivider()

                // Accent Color Selector (5 iOS Palettes)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SettingsRowLabel(
                        icon = Icons.Default.Palette,
                        iconBg = AppleSystemPurple,
                        title = "اللون المميز (Accent Color)",
                        subtitle = "لون الأزرار والمؤشرات واللمسات التفاعلية"
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf(
                            Triple("violet", "أزرق أبل", AppleSystemBlue),
                            Triple("indigo", "نيلي", AppleSystemIndigo),
                            Triple("emerald", "زمردي", AppleSystemGreen),
                            Triple("amber", "عنبري", AppleSystemOrange),
                            Triple("purple", "أرجواني", AppleSystemPurple)
                        ).forEach { (colorName, label, color) ->
                            val isSelected = preferences.accentColorName == colorName
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) AppleSystemGray5 else Color.Transparent)
                                    .border(
                                        if (isSelected) 1.dp else 0.dp,
                                        if (isSelected) color else Color.Transparent,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable { onUpdatePreferences { it.copy(accentColorName = colorName) } }
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clip(CircleShape)
                                            .background(color)
                                            .border(
                                                if (isSelected) 2.dp else 0.dp,
                                                Color.White,
                                                CircleShape
                                            )
                                    )
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color.White else AppleSystemGray,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Section: Content & Media Feed Behavior
        item {
            SettingsSectionHeader(title = "سلوك التغذية وتدفق الوسائط")
            Spacer(modifier = Modifier.height(8.dp))
            SettingsGroupedCard {
                // Vertical Videos Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsRowLabel(
                        icon = Icons.Default.PlayCircle,
                        iconBg = ColorPlatformTikTok,
                        title = "تشغيل الفيديوهات الرأسية",
                        subtitle = "عرض مقاطع تيك توك وريلز مدمجة بسلاسة"
                    )
                    CupertinoSwitch(
                        checked = preferences.verticalVideosEnabled,
                        onCheckedChange = { checked ->
                            onUpdatePreferences { it.copy(verticalVideosEnabled = checked) }
                        },
                        activeColor = AppleSystemGreen,
                        testTag = "switch_vertical_videos"
                    )
                }

                SettingsDivider()

                // Silent Preview Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsRowLabel(
                        icon = Icons.Default.VolumeOff,
                        iconBg = AppleSystemOrange,
                        title = "معاينة صامتة تلقائية",
                        subtitle = "كتم الصوت افتراضياً أثناء التمرير في التغذية"
                    )
                    CupertinoSwitch(
                        checked = preferences.silentPreviewEnabled,
                        onCheckedChange = { checked ->
                            onUpdatePreferences { it.copy(silentPreviewEnabled = checked) }
                        },
                        activeColor = AppleSystemGreen,
                        testTag = "switch_silent_preview"
                    )
                }

                SettingsDivider()

                // Hide Duplicates Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsRowLabel(
                        icon = Icons.Default.FilterAlt,
                        iconBg = AppleSystemTeal,
                        title = "منع وتصفية التكرار",
                        subtitle = "دمج المنشورات المتشابهة تلقائياً بين المنصات"
                    )
                    CupertinoSwitch(
                        checked = preferences.hideDuplicates,
                        onCheckedChange = { checked ->
                            onUpdatePreferences { it.copy(hideDuplicates = checked) }
                        },
                        activeColor = AppleSystemGreen,
                        testTag = "switch_hide_duplicates"
                    )
                }
            }
        }

        // 4. Section: Storage, Cache & Maintenance
        item {
            SettingsSectionHeader(title = "الأداء والذاكرة المؤقتة")
            Spacer(modifier = Modifier.height(8.dp))
            SettingsGroupedCard {
                // Clear Cache Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClearCache() }
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsRowLabel(
                        icon = Icons.Default.Storage,
                        iconBg = AppleSystemGray3,
                        title = "مسح الذاكرة المؤقتة",
                        subtitle = if (preferences.cacheSizeMb > 0) "${preferences.cacheSizeMb} ميغابايت مستخدمة للمعاينة" else "الذاكرة المؤقتة فارغة ونظيفة"
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = if (preferences.cacheSizeMb > 0) "تنظيف" else "مفرغة",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (preferences.cacheSizeMb > 0) AppleSystemBlue else AppleSystemGray,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = AppleSystemGray,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                SettingsDivider()

                // Clear Search History
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClearSearchHistory() }
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsRowLabel(
                        icon = Icons.Default.History,
                        iconBg = AppleSystemGray4,
                        title = "مسح سجل البحث والكلمات المفتاحية",
                        subtitle = "حذف سجل الاستكشاف المحفوظ محلياً"
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "مسح",
                            style = MaterialTheme.typography.labelSmall,
                            color = AppleSystemGray,
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = AppleSystemGray,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                SettingsDivider()

                // Reset Defaults
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onResetDefaults() }
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsRowLabel(
                        icon = Icons.Default.Restore,
                        iconBg = AppleSystemRed,
                        title = "استعادة الإعدادات الافتراضية",
                        subtitle = "إعادة ضبط السمات والتفضيلات للوضع الأصلي"
                    )
                    Text(
                        text = "إعادة ضبط",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppleSystemRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 5. Action Button: Save & Apply Preferences
        item {
            val accentColor = when (preferences.accentColorName) {
                "indigo" -> AppleSystemIndigo
                "emerald" -> AppleSystemGreen
                "amber" -> AppleSystemOrange
                "purple" -> AppleSystemPurple
                else -> AppleSystemBlue
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(accentColor)
                    .clickable { onSavePreferences() }
                    .padding(vertical = 14.dp)
                    .testTag("btn_save_preferences"),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "حفظ وتطبيق التغييرات فورا",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = (-0.3).sp
                    )
                }
            }
        }

        // 6. Section: About App Branding (iOS App Info with modern typography)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(AppleSystemBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Hub,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = "SocialHub • الإصدار 2.5",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Text(
                    text = "مصمم بأسلوب Apple Human Interface Guidelines مع معمارية OLED ومحرك الزجاج السائل",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppleSystemGray,
                    fontSize = 11.sp
                )
            }
        }
    }
}

/**
 * Reusable Apple Inset Grouped Container Card
 */
@Composable
private fun SettingsGroupedCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(AppleSystemGray6.copy(alpha = 0.88f))
            .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(20.dp)),
        content = content
    )
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = AppleSystemGray,
        letterSpacing = 0.6.sp,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

@Composable
private fun SettingsRowLabel(
    icon: ImageVector,
    iconBg: Color,
    title: String,
    subtitle: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth(if (subtitle == null) 0.60f else 0.80f)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = AppleSystemGray,
                    fontSize = 11.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@Composable
private fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
            .height(0.5.dp)
            .background(AppleSystemGray5)
    )
}
