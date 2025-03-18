package com.example.learnandroid.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnandroid.R
import com.example.learnandroid.bean.NavigationItem
import com.example.learnandroid.ui.components.CustomAppBottomBar
import com.example.learnandroid.ui.theme.LearnAndroidTheme
import com.example.learnandroid.ui.theme.LearnAndroidTheme.themeColors
import com.example.learnandroid.vm.LearnAndroidVM

/**
 * 首页
 *
 * @author zhuhao
 * @date  16:21
 **/
@Composable
fun MainFrame() {

    var currentNavigationIndex by remember {
        mutableIntStateOf(0)
    }
    val navigationItems = listOf(
        NavigationItem(title = "学习", Icons.Filled.Home, null, 0),
        NavigationItem(title = "资讯", Icons.Filled.Newspaper, null, 0),
        NavigationItem(title = "我的", Icons.Filled.Person, null, 0),
    )

    Scaffold(bottomBar = {
        currentNavigationIndex = AppBottomBar(navigationItems, currentNavigationIndex)
    }) { innerPadding ->
        Text(
            text = "当前页面 $currentNavigationIndex",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun AppBottomBar(
    navigationItems: List<NavigationItem>,
    currentNavigationIndex: Int
): Int {
    var currentNavigationIndex1 = currentNavigationIndex
//    NavigationBar(Modifier.background(MaterialTheme.colorScheme.surface)) {
//        navigationItems.forEachIndexed { index, navigationItem ->
//            NavigationBarItem(
//                alwaysShowLabel = false,
//                selected = currentNavigationIndex1 == index,
//                onClick = {
//                    currentNavigationIndex1 = index
//                }, icon = {
//                    Icon(imageVector = navigationItem.icon, contentDescription = navigationItem.title)
//                }, label = {
//                    Text(text = navigationItem.title)
//                },
//                colors = NavigationBarItemColors(
//                    selectedIconColor = themeColors.background,
//                    selectedTextColor = themeColors.iconCurrent,
//                    selectedIndicatorColor = themeColors.iconCurrent,
//                    unselectedIconColor = themeColors.icon,
//                    unselectedTextColor = themeColors.icon,
//                    disabledIconColor = themeColors.icon,
//                    disabledTextColor = themeColors.icon,
//                )
//            )
//        }
//    }
    LearnAndroidTheme(LearnAndroidTheme.Theme.NewYear) {
        val viewModel = LearnAndroidVM()
        Column {
            CustomAppBottomBar(viewModel.selectedTab) {
                viewModel.selectedTab = it
            }
        }
    }
    return currentNavigationIndex1
}


@Preview(showBackground = true)
@Composable
private fun CustomBottomBarPreview() {
    LearnAndroidTheme(LearnAndroidTheme.Theme.Light) {
        var selectedTab by remember { mutableIntStateOf(0) }
        CustomAppBottomBar(selectedTab) {
            selectedTab = it
        }
    }
}

@Composable
fun ChatTabItem(
    @DrawableRes iconId: Int,
    title: String,
    tint: Color,
    modifier: Modifier
) {
    Column(modifier.padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = painterResource(iconId), contentDescription = title, Modifier.size(24.dp), tint = tint)
        Text(text = title, fontSize = 11.sp, color = tint)
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatTabItemPreview() {
    ChatTabItem(
        R.drawable.ic_me_filled,
        "我的",
        themeColors.iconCurrent,
        modifier = Modifier.padding(10.dp)
    )
}

@Preview
@Composable
private fun MainFramePreview() {
    MainFrame()
}