package com.eganin.jetpack.thebest.weatherapp.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme

@Composable
fun SearchWidget(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            color = AppTheme.colors.primaryText.copy(alpha = 0.5f),
            thickness = 2.dp,
            modifier = Modifier
                .weight(1f)
                .padding(end = 5.dp)
        )
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.clickable { onClick() }
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = stringResource(R.string.search_button_sescription),
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
            }
        }
        Divider(
            color = AppTheme.colors.primaryText.copy(alpha = 0.5f),
            thickness = 2.dp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp)
        )
    }
}