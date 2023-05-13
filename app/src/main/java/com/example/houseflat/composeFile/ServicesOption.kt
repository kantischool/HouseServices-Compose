package com.example.houseflat.composeFile

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.houseflat.models.HouseService


// render check boxes for each services
@Composable
fun ServicesOption(checkList: List<MutableState<Boolean>>, myCheck: MutableState<Boolean>, chargeType: MutableState<Int>, services: MutableState<HouseService?>, index: Int){
    Row {
        Checkbox(

            checked = myCheck.value,

            onCheckedChange = {
                for (i in checkList){
                    if (i == myCheck){
                        i.value = it
                    }else{
                        i.value = false
                    }
                }
                if (it)
                    chargeType.value = services.value?.list!![index].price
                else
                    chargeType.value = 0

            },
        )
        Text(
            text = services.value?.list!![index].name[0],
            modifier = Modifier
                .padding(10.dp)
                .weight(1f),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
        if (myCheck.value){

            ServicesCounter(state = myCheck, servicesCharge = chargeType, services, index)

        }
        if (services.value?.list!![index].price != 0) {
            Text(
                text = "₹ ${services.value?.list!![index].price}",
                modifier = Modifier
                    .padding(10.dp),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }


    }
}