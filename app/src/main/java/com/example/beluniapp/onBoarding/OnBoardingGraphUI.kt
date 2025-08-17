package com.example.beluniapp.onBoarding

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnBoardingGraphUI(onBoardingModel: OnBoardingModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(id = onBoardingModel.image),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
                .padding(50.dp, 0.dp),
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = onBoardingModel.title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.titleMedium,
            color= MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = onBoardingModel.description,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.bodySmall,
            color= MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(15.dp))

    }
}


