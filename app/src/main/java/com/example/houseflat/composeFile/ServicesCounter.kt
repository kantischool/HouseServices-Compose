package com.example.houseflat.composeFile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.houseflat.models.HouseService

@Composable

// count the total services
fun ServicesCounter(state: MutableState<Boolean>, servicesCharge: MutableState<Int>, services: MutableState<HouseService?>, index: Int){
    val totalBed = remember {
        mutableStateOf(1)
    }
    Row(
        modifier = Modifier
            .padding(8.dp)
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(corner = CornerSize(12.dp))
            )
            .wrapContentHeight()
            .wrapContentWidth()
    ) {

        Text(
            text = "-",
            modifier = Modifier
                .padding(all = 9.dp)
                .width(12.dp)
                .clickable {
                    totalBed.value = totalBed.value - 1
                    if (totalBed.value == 0) {
                        state.value = false
                        servicesCharge.value = 0
                    } else {
                        servicesCharge.value =
                            totalBed.value * services.value?.list!![index].price
                    }

                },
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
        Text(
            text = "${totalBed.value}",
            modifier = Modifier
                .padding(all = 9.dp)
                .width(12.dp),
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
        Text(
            text = "+",
            modifier = Modifier
                .padding(all = 9.dp)
                .width(12.dp)
                .clickable {
                    totalBed.value = totalBed.value + 1
                    servicesCharge.value =
                        totalBed.value * services.value?.list!![index].price
                },
            textAlign = TextAlign.Center
        )

    }
}