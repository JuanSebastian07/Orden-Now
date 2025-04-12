package com.example.ordernow.presentation.food_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.ordernow.R
import com.example.ordernow.domain.model.ItemsRestaurant
import com.example.ordernow.presentation.food_detail.components.DescriptionSection
import com.example.ordernow.presentation.food_detail.components.FooterSection
import com.example.ordernow.presentation.food_detail.components.OrderDetailsRow
import com.example.ordernow.presentation.food_detail.components.RowDetail


@Composable
fun FoodDetailScreen(itemRestaurant: ItemsRestaurant, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (back, fav, mainImage, arcImage, title, detailRow, orderDetailsRow, descriptionSection, footer) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(itemRestaurant.ImagePath),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(
                    RoundedCornerShape(
                    bottomStart = 32.dp,bottomEnd = 32.dp
                    )
                )
                .constrainAs(mainImage) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        )

        Image(
            painter = painterResource(R.drawable.arc_bg),
            contentDescription = null,
            modifier = Modifier
                .height(190.dp)
                .constrainAs(arcImage) {
                    top.linkTo(mainImage.bottom, margin = (-30).dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        )

        BackButton(Modifier.constrainAs(back){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        })
        FavoriteButton(Modifier.constrainAs(fav){
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        })

        Text(
            text = itemRestaurant.Title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.black),
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .constrainAs(title){
                    top.linkTo(arcImage.top, margin = 32.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        )

        RowDetail(itemRestaurant, Modifier.constrainAs(detailRow){
            top.linkTo(title.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        OrderDetailsRow(itemRestaurant, Modifier.constrainAs(orderDetailsRow){
            top.linkTo(detailRow.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        DescriptionSection(itemRestaurant, Modifier.constrainAs(descriptionSection){
            top.linkTo(orderDetailsRow.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        FooterSection(itemRestaurant,Modifier.constrainAs(footer){
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
            start.linkTo(parent.start)
        })

    }
}

@Composable
private fun BackButton(modifier: Modifier){
    Box(
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp)
            .clip(RoundedCornerShape(48.dp))
            .size(48.dp)
            .background(color = Color.DarkGray, shape = CircleShape)
            .clickable {  },
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
private fun FavoriteButton(modifier: Modifier){
    Box(
        modifier = modifier
            .padding(end = 16.dp, top = 16.dp)
            .clip(RoundedCornerShape(48.dp))
            .size(48.dp)
            .background(color = Color.DarkGray, shape = CircleShape)
            .clickable {  },
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = Icons.Filled.FavoriteBorder,
            contentDescription = null,
            tint = Color.White
        )
    }
}