package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.PlatformType
import com.example.ui.components.CupertinoGroupedCard
import com.example.ui.components.CupertinoSegmentedControl
import com.example.ui.components.GlassCard
import com.example.ui.theme.*

@Composable
fun SearchScreen(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onCloseSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPlatform by remember { mutableStateOf(PlatformType.ALL) }
    var selectedTab by remember { mutableStateOf("أفضل النتائج") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("unified_search_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Apple-style Search Bar & Cancel Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // iOS Pill Search Input
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppleSystemGray6)
                        .border(0.5.dp, GlassBorderSubtle, RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "بحث",
                            tint = AppleSystemGray,
                            modifier = Modifier.size(18.dp)
                        )

                        Box(modifier = Modifier.weight(1f)) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "ابحث في كل المنصات...",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = AppleSystemGray,
                                    fontSize = 14.sp
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = onQueryChange,
                                singleLine = true,
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                ),
                                cursorBrush = SolidColor(AppleSystemBlue),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("unified_search_input_field")
                            )
                        }

                        if (searchQuery.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(AppleSystemGray4)
                                    .clickable { onClearQuery() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "مسح",
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }
                }

                // Apple Cancel Text Button
                Text(
                    text = "إلغاء",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = AppleSystemBlue,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .clickable { onCloseSearch() }
                        .padding(horizontal = 4.dp, vertical = 6.dp)
                )
            }
        }

        // 2. iOS Segmented Control for Result Tabs
        item {
            val tabs = listOf("أفضل النتائج", "حسابات", "وسائط", "منشورات")
            CupertinoSegmentedControl(
                items = tabs,
                selectedItem = selectedTab,
                onItemSelected = { selectedTab = it },
                itemLabel = { it },
                itemTagPrefix = "search_tab"
            )
        }

        // 3. Platform Target Selector (Apple Squircles)
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
                    Pair(PlatformType.TIKTOK, "TikTok"),
                    Pair(PlatformType.FACEBOOK, "Facebook")
                ).forEach { (type, label) ->
                    val isSelected = selectedPlatform == type
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) AppleSystemBlue else AppleSystemGray6)
                            .border(
                                0.5.dp,
                                if (isSelected) Color.Transparent else GlassBorderSubtle,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedPlatform = type }
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) Color.White else AppleSystemGray,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // 4. Recent Searches & Trending Topics (iOS Inset Card)
        item {
            CupertinoGroupedCard(
                shape = RoundedCornerShape(18.dp),
                backgroundColor = AppleSystemGray6
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
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = AppleSystemBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "عمليات البحث المتداولة",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "مسح السجل",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppleSystemGray,
                        fontSize = 11.sp,
                        modifier = Modifier.clickable { }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "#الذكاء_الاصطناعي",
                        "تصميم واجهات iOS",
                        "#الحوسبة_المحلية",
                        "مقارنة نماذج اللغة"
                    ).forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(AppleSystemGray5)
                                .clickable { onQueryChange(tag.replace("#", "")) }
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // 5. Result Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "النتائج المطابقة (١٤٢)",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppleSystemGray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "الأكثر صلة",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppleSystemBlue
                )
            }
        }

        // Result Card 1: Verified Creator
        item {
            GlassCard(
                shape = RoundedCornerShape(18.dp),
                backgroundColor = AppleSystemGray6,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
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
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBd7Zu5tzYXQpjs-xr1bfa-PlHbvq6olY6UV8mLlL8fcpEMBLVQjdp7D1A1BVbOr5eFY5APBdmBL6SZHBFwYbKEQUVWkNm_vAnrvaadjVfxi-ZT4FzNXgKhKtgJhD7GhwNPoSnXSdoxjWbQNn5DXdhhNkL0ELj_BaPGzaGyj0gIHnFT5l8dXvPwY7Ju552PGvFAqoHVTv3T6NZePfdCN9LPjYSdubEGxdVA5A8P8mK4TW9yN6TQJgg",
                                contentDescription = "د. طارق المنصور",
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
                                        text = "د. طارق المنصور",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.Verified,
                                        contentDescription = null,
                                        tint = AppleSystemBlue,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                                Text(
                                    text = "@almansoor_tech • 𝕏",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppleSystemGray
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(AppleSystemBlue)
                                .clickable { }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "متابعة",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }

                    Text(
                        text = "مستشار في أتمتة الأنظمة وتجربة المنتجات الرقمية. أقود مبادرات توطين تقنيات الذكاء التوليدي في بيئات العمل الإبداعية العربية.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppleSystemGray,
                        lineHeight = 21.sp
                    )
                }
            }
        }

        // Result Card 2: YouTube Video
        item {
            GlassCard(
                shape = RoundedCornerShape(18.dp),
                backgroundColor = AppleSystemGray6,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(AppleSystemGray5)
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuB2QepRUsDQHyvEFC3UBfZn7F7eXahvdmROdREmOmHnafM5dUllOiXJHeUn1-8S60LnC2eFOsxTFSGTZS2RQzbp_KsywF8W1c1ZIkX-ZfhcH7zEYGjupItBkb0PYpbaUKZhFTuvDAUfYFs-M-fTTDYgyWO9Lqe0Y4RKID2aWnSceRxIf2ZqsIoUo8TU09tc2XHensjHo8aUfVA1sjcTjumoA0GMw12lcNfDEJfLFnD3V_i94kodlws",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Black.copy(alpha = 0.75f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "14:28",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Text(
                        text = "كيف تعيد واجهات الذكاء الاصطناعي تشكيل تجربة المستخدم في عام 2025؟ دراسة أداء وحلول عملية",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        lineHeight = 22.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "قناة تجارب التقنية • ٥٤ ألف مشاهدة",
                            style = MaterialTheme.typography.labelSmall,
                            color = AppleSystemGray
                        )
                        Text(
                            text = "فتح في YouTube ↗",
                            style = MaterialTheme.typography.labelSmall,
                            color = AppleSystemBlue,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
