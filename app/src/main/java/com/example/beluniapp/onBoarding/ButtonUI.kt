package com.example.beluniapp.onBoarding

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonUI(
    text: String="Next",
    bgcolor: Color= MaterialTheme.colorScheme.primary,
    textColor: Color= MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle= MaterialTheme.typography.titleMedium,
    fontSize:Int=16,
    onClick:() ->Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = bgcolor,
            contentColor=textColor
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text=text)
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonUINextPreview(){
    ButtonUI(
        "Next"
    ){}
}
@Preview(showBackground = true)
@Composable
fun ButtonUIBackPreview(){
    ButtonUI(
        "Back",
        Color.Transparent,
        Color.Gray,
        MaterialTheme.typography.bodySmall,
    ){}
}