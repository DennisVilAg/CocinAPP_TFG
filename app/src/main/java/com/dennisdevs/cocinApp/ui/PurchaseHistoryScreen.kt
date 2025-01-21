package com.dennisdevs.cocinApp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dennisdevs.cocinApp.model.data.ShopList
import com.dennisdevs.cocinApp.ui.components.SlideInColumn
import com.dennisdevs.cocinApp.ui.theme.customColor1Dark
import com.dennisdevs.cocinApp.ui.theme.onCustomColor1ContainerDark
import com.dennisdevs.cocinApp.ui.theme.tertiaryLight
import com.dennisdevs.cocinApp.utils.formatDate
import com.dennisdevs.cocinApp.viewmodel.HistoryShopListViewModel

@Composable
fun PurchaseHistoryScreen(viewModel: HistoryShopListViewModel) {
    val purchaseHistory by viewModel.historyShopLists.collectAsState()

    SlideInColumn {
        Text(
            text = "Historial de compras",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        bottomStart = 12.dp, bottomEnd = 12.dp
                    )
                )
                .background(tertiaryLight)
                .padding(10.dp, 6.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (purchaseHistory.isEmpty()) {
                Text(
                    text = "No hay historial de compras disponible.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                purchaseHistory.forEachIndexed { index, shopList ->
                    ShopListCard(
                        shopList = shopList,
                        backgroundColor = if (index % 2 == 0) onCustomColor1ContainerDark else customColor1Dark
                    )
                }
            }
        }
    }
}

@Composable
fun ShopListCard(shopList: ShopList, backgroundColor: Color) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable { expanded = !expanded }
    ) {
        Column {
            Text(
                text = formatDate(shopList.timestamp),
                modifier = Modifier.fillMaxWidth(),
                color = Color.DarkGray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W500
            )

            if (expanded) {
                Spacer(modifier = Modifier.height(16.dp))
                // Lista de productos comprados
                shopList.items.forEach { item ->
                    Text(
                        text = item.name,
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}