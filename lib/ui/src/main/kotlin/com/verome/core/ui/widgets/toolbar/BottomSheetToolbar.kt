package com.verome.core.ui.widgets.toolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verome.core.domain.localization.string.VmRes
import com.verome.core.domain.localization.string.resolve
import com.verome.core.domain.localization.string.toVmResStr
import com.verome.core.ui.R
import com.verome.core.ui.theme.AppTheme
import com.verome.core.ui.theme.additionalColors
import com.verome.core.ui.theme.bold

@Composable
fun BottomSheetToolbar(
    modifier: Modifier = Modifier,
    title: VmRes<CharSequence>,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier.height(60.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, start = 6.dp, end = 6.dp),
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(22.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_navigate_back),
                    contentDescription = null,
                    tint = MaterialTheme.additionalColors.btnText,
                    modifier = Modifier.size(22.dp).padding(end = 5.dp),
                )
            }
            Text(
                text = title.resolve(),
                style = MaterialTheme.typography.h4.bold(),
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(22.dp).padding(start = 5.dp))
        }
        Divider()
    }
}

@Preview
@Composable
fun BottomSheetToolbarPreview() {
    AppTheme {
        BottomSheetToolbar(
            title = "Profile".toVmResStr(),
            onBackClick = {},
        )
    }
}