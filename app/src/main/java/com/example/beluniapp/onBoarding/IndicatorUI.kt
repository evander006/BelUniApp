package com.example.beluniapp.onBoarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IndicatorUI(pageSize: Int,
                currPage:Int,
                selColor:Color = MaterialTheme.colorScheme.primary,
                unselColor:Color = MaterialTheme.colorScheme.secondary){
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize) {
            Spacer(modifier = Modifier.size(2.5.dp))
            Box(modifier = Modifier.height(16.dp)
                .width(width = if (it==currPage) 32.dp else 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = if (it==currPage) selColor else unselColor)
            )
            Spacer(modifier = Modifier.size(2.5.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun IndicatorUI1Preview(){
    IndicatorUI(pageSize = 3, currPage = 0)
}
@Preview(showBackground = true)
@Composable
fun IndicatorUI2Preview(){
    IndicatorUI(pageSize = 3, currPage = 1)
}
@Preview(showBackground = true)
@Composable
fun IndicatorUI3Preview(){
    IndicatorUI(pageSize = 3, currPage = 2)
}