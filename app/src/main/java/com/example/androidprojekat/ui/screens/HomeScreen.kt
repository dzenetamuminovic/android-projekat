package com.example.androidprojekat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidprojekat.R
import com.example.androidprojekat.ui.theme.PrimaryTextBlue

@Composable
fun HomeScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(56.dp))

        Text(
            text = "Dobrodošli u IDDEEA OpenData aplikaciju.\nPratite informacije o izdanim ličnim kartama, isteklim vozačkim dozvolama i sačuvajte omiljene podatke.",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        MenuBox(text = stringResource(id = R.string.licnekarte)) {
            navController.navigate("issued_cards")
        }

        Spacer(modifier = Modifier.height(16.dp))

        MenuBox(text = stringResource(id = R.string.vozackedozvole)) {
            navController.navigate("expired_dl_cards")
        }

        Spacer(modifier = Modifier.height(16.dp))

        MenuBox(text = stringResource(id = R.string.favourites)) {
            navController.navigate("favourites")
        }
    }
}

@Composable
fun MenuBox(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x4AFFF9C4),
                        Color(0x4AFDF7BB)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = PrimaryTextBlue,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
