package com.example.houseflat.composeFile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.houseflat.models.HouseService
import com.example.houseflat.models.ListItem


@Composable
fun RadioButtons(
    list: List<ListItem>,
    bedRoomServicesList: ArrayList<HouseService>,
    bathRoomServicesList: ArrayList<HouseService>,
    kitchenServicesList: ArrayList<HouseService>,
    livingRoomServicesList: ArrayList<HouseService>,
    bedRoom: MutableState<HouseService?>,
    bathRoom: MutableState<HouseService?>,
    kitchen: MutableState<HouseService?>,
    livingRoom: MutableState<HouseService?>,
    apartManCharge: MutableState<Int>,
    allCheckList: ArrayList<MutableState<Boolean>>,
    bedRoomCharge: MutableState<Int>,
    bathRoomCharge: MutableState<Int>,
    livingRoomCharge: MutableState<Int>,
    kitchenRoomCharge: MutableState<Int>
) {
    var defaultSelected: ListItem? = null

    for (i in list) {
        if (i.is_default_selected) {
            defaultSelected = i
        }
    }
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(
            defaultSelected
        )
    }

    fun clearAllCheckList(){
        for (i in allCheckList){
            i.value = false
        }
        bedRoomCharge.value = 0
        bathRoomCharge.value = 0
        livingRoomCharge.value = 0
        kitchenRoomCharge.value = 0
    }

    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        list.forEach { item ->
            if (selectedOption != null) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (item._id == selectedOption._id),
                            onClick = {
                                apartManCharge.value = item.price
                                onOptionSelected(item)
                                bedRoomServicesList.forEach { ser ->
                                    if (ser.modifierName == item.name[0])
                                        bedRoom.value = ser
                                }
                                bathRoomServicesList.forEach { ser ->
                                    if (ser.modifierName == item.name[0]) {
                                        bathRoom.value = ser
                                    }
                                }
                                kitchenServicesList.forEach { ser ->
                                    if (ser.modifierName == item.name[0])
                                        kitchen.value = ser
                                }
                                livingRoomServicesList.forEach { ser ->
                                    if (ser.modifierName == item.name[0]) {
                                        livingRoom.value = ser
                                    }
                                }
                                clearAllCheckList()
                            }
                        )
                        .padding()
                ) {

                    RadioButton(

                        selected = (item._id == selectedOption._id),
                        modifier = Modifier.padding(0.dp),
                        onClick = {
                            apartManCharge.value = item.price
                            onOptionSelected(item)
                            bedRoomServicesList.forEach { ser ->
                                if (ser.modifierName == item.name[0])
                                    bedRoom.value = ser
                            }
                            bathRoomServicesList.forEach { ser ->
                                if (ser.modifierName == item.name[0]) {
                                    bathRoom.value = ser
                                }
                            }
                            kitchenServicesList.forEach { ser ->
                                if (ser.modifierName == item.name[0])
                                    kitchen.value = ser
                            }
                            livingRoomServicesList.forEach { ser ->
                                if (ser.modifierName == item.name[0]) {
                                    livingRoom.value = ser
                                }
                            }
                            clearAllCheckList()
                        }
                    )

                    Text(
                        text = item.name[0],
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1F)
                    )
                    Text(
                        text = "₹ ${item.price.toLong()}",
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1F)
                    )
                }
            }
        }
    }
}