package com.example.learnandroid.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.learnandroid.R
import com.example.learnandroid.ui.screens.CustomTabItem
import com.example.learnandroid.ui.theme.LearnAndroidTheme
import com.example.learnandroid.ui.theme.LearnAndroidTheme.themeColors

/**
 * @author zhuhao
 * @date  21:47
 **/
@Composable
fun CustomAppBottomBar(selected: Int, onSelectedChange: (Int) -> Unit) {
    Row(Modifier.background(LearnAndroidTheme.themeColors.bottomBar)) {
        CustomTabItem(
            if (selected == 0) R.drawable.ic_chat_filled else R.drawable.ic_chat_outlined,
            "聊天",
            if (selected == 0) themeColors.iconCurrent else themeColors.icon,
            modifier = Modifier
                .weight(1f)
                .clickable { onSelectedChange(0) }
        )
        CustomTabItem(
            if (selected == 1) R.drawable.ic_contacts_filled else R.drawable.ic_contacts_outlined,
            "联系人",
            if (selected == 1) themeColors.iconCurrent else themeColors.icon,
            modifier = Modifier
                .weight(1f)
                .clickable { onSelectedChange(1) }
        )
        CustomTabItem(
            if (selected == 2) R.drawable.ic_me_filled else R.drawable.ic_me_outlined,
            "我的",
            if (selected == 2) themeColors.iconCurrent else themeColors.icon,
            modifier = Modifier
                .weight(1f)
                .clickable { onSelectedChange(2) }
        )
    }
}

@Preview
@Composable
fun CustomAppBottomBarPreview() {
    var selectedTab by remember { mutableIntStateOf(0) }
    CustomAppBottomBar(selectedTab) {
        selectedTab = it
    }
}
