package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.weatherradar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.ui.theme.Typography

@Composable
fun SectionHeader(title: String, subtitle: String) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = title,
            style = Typography.h5,
            color = AppTheme.colors.primaryText
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = subtitle,
            style = Typography.h6,
            color = AppTheme.colors.secondaryText
        )
    }
}