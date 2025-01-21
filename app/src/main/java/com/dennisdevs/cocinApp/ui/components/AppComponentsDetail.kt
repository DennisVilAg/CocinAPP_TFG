package com.dennisdevs.cocinApp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.SignalCellularAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dennisdevs.cocinApp.model.data.Ingredient
import com.dennisdevs.cocinApp.ui.theme.errorContainerLight
import com.dennisdevs.cocinApp.ui.theme.onPrimaryContainerLight
import com.dennisdevs.cocinApp.ui.theme.primaryContainerLight
import com.dennisdevs.cocinApp.ui.theme.surfaceDimLight

@Composable
fun LikeButton(
    isLiked: Boolean, onLikeClicked: (Boolean) -> Unit, modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onLikeClicked(!isLiked) },
        modifier = modifier
            .padding(12.dp)
            .clip(CircleShape)
            .size(44.dp)
            .background(primaryContainerLight)
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Botón de Like",
            tint = if (isLiked) errorContainerLight else onPrimaryContainerLight
        )
    }
}

@Composable
fun RecipeImageBox(
    imageUrl: String,
    isLiked: Boolean,
    onLikeClicked: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp)),
            model = imageUrl,
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            clipToBounds = true
        )
        LikeButton(
            isLiked = isLiked, onLikeClicked = onLikeClicked
        )
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        textAlign = TextAlign.Center,
        text = title,
        fontSize = 22.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun TimeInfo(time: String) {
    Icon(
        imageVector = Icons.Default.AccessTime, contentDescription = "IconTime"
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(text = time, fontWeight = FontWeight.SemiBold)
}

@Composable
fun DifficultInfo(difficult: String) {
    Icon(
        imageVector = Icons.Outlined.SignalCellularAlt, contentDescription = ""
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(text = difficult, fontWeight = FontWeight.SemiBold)
}

@Composable
fun RationsInfo(rations: Double) {
    Icon(
        imageVector = Icons.Outlined.Restaurant, contentDescription = "", Modifier.size(20.dp)
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
        text = rations.toInt().toString(), fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun TextDescription(description: String) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = description,
        color = Color.White,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        softWrap = true
    )
}

@Composable
fun TitleTextSmall(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
fun KitchenTypeInfo(kitchenType: String) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(surfaceDimLight)
            .padding(horizontal = 25.dp)
            .height(34.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = kitchenType, fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun MyRowInfo(content: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(surfaceDimLight, shape = CircleShape)
            .padding(horizontal = 25.dp, vertical = 4.dp)
            .height(26.dp)
    ) {
        content()
    }
}

@Composable
fun MyColumnInfo(
    modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp)
            .height(90.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        content = content
    )
}

@Composable
fun IngredientsList(
    ingredients: List<Ingredient>, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(.8f)
            .clip(CircleShape)
            .background(surfaceDimLight)
            .padding(vertical = 10.dp, horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ingredients.forEach { ingredient ->
            Text(
                text = "${ingredient.quantity.toInt()} ${ingredient.unit} de ${ingredient.name}",
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun PreparationSteps(
    preparation: List<String>?, modifier: Modifier = Modifier
) {
    preparation?.forEachIndexed { index, step ->
        Text(
            text = "${index + 1}. $step",
            fontSize = 14.sp,
            color = Color.White,
            modifier = modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 14.dp)
        )
    }
}