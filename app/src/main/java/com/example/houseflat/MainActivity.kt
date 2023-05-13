package com.example.houseflat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.houseflat.composeFile.RadioButtons
import com.example.houseflat.composeFile.ServicesOption
import com.example.houseflat.models.HouseService
import com.example.houseflat.models.ListItem
import com.example.houseflat.models.MainModel
import com.example.houseflat.ui.theme.HouseFlatTheme
import com.google.gson.Gson
import java.io.InputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // different services list
        val bedRoomServicesList: ArrayList<HouseService> = ArrayList()
        val bathRoomServicesList: ArrayList<HouseService> = ArrayList()
        val livingRoomServicesList: ArrayList<HouseService> = ArrayList()
        val kitchenServicesList: ArrayList<HouseService> = ArrayList()

        // json data
        val data = readJsonFile()

        val apartMan = data.specifications[0]


        // create services list
        for (i in data.specifications) {
            if (i.name[0] == "Bedroom Cleaning ") {
                bedRoomServicesList.add(i);
            }
            if (i.name[0] == "Bathroom Cleaning") {
                bathRoomServicesList.add(i);
            }
            if (i.name[0] == "Kitchen Cleaning") {
                kitchenServicesList.add(i);
            }
            if (i.name[0] == "Living Room/Dining Room Cleaning") {
                livingRoomServicesList.add(i);
            }
        }

        setContent {
            HouseFlatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainUi(
                        data,
                        apartMan,
                        bedRoomServicesList,
                        bathRoomServicesList,
                        kitchenServicesList,
                        livingRoomServicesList
                    )
                }
            }
        }
    }

    private fun readJsonFile(): MainModel {
        var json: String? = null

        try {
            val inputStream: InputStream = assets.open("housedata.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        val data: MainModel = Gson().fromJson(json, MainModel::class.java)

        return data
    }

    @Composable
    fun MainUi(
        data: MainModel,
        apartMan: HouseService,
        bedRoomServicesList: ArrayList<HouseService>,
        bathRoomServicesList: ArrayList<HouseService>,
        kitchenServicesList: ArrayList<HouseService>,
        livingRoomServicesList: ArrayList<HouseService>
    ) {
        var bed: HouseService? = null
        var bath: HouseService? = null
        var kit: HouseService? = null
        var liv: HouseService? = null
        var defaultPrice: Int = 0

        var defaultSelected: ListItem? = null

        for (i in apartMan.list) {
            if (i.is_default_selected) {
                defaultSelected = i
                defaultPrice = i.price
            }
        }

        // set the default value for services
        bedRoomServicesList.forEach { ser ->
            if (ser.modifierName == defaultSelected!!.name[0])
                bed = ser
        }
        bathRoomServicesList.forEach { ser ->
            if (ser.modifierName == defaultSelected!!.name[0]) {
                bath = ser
            }
        }
        kitchenServicesList.forEach { ser ->
            if (ser.modifierName == defaultSelected!!.name[0])
                kit = ser
        }
        livingRoomServicesList.forEach { ser ->
            if (ser.modifierName == defaultSelected!!.name[0]) {
                liv = ser
            }
        }


        // mutable state of services
        val bedRoom: MutableState<HouseService?> = remember {
            mutableStateOf(bed)
        }
        val bathRoom: MutableState<HouseService?> = remember {
            mutableStateOf(bath)
        }
        val kitchen: MutableState<HouseService?> = remember {
            mutableStateOf(kit)
        }
        val livingRoom: MutableState<HouseService?> = remember {
            mutableStateOf(liv)
        }


        // bedroom checkbox state
        val bedCheck1 = remember { mutableStateOf(false) }
        val bedCheck2 = remember { mutableStateOf(false) }
        val bedCheck3 = remember { mutableStateOf(false) }
        val bedCheckList: List<MutableState<Boolean>> = listOf(bedCheck1, bedCheck2, bedCheck3)

        // bathroom checkbox state
        val bathCheck1 = remember { mutableStateOf(false) }
        val bathCheck2 = remember { mutableStateOf(false) }
        val bathCheckList = listOf(bathCheck1, bathCheck2)

        // living checkbox state
        val livingCheck1 = remember { mutableStateOf(false) }
        val livingCheck2 = remember { mutableStateOf(false) }
        val livingCheck3 = remember { mutableStateOf(false) }
        val livingChecklist = listOf(livingCheck1, livingCheck2, livingCheck3)

        // kitchen checkbox state
        val kitchenCheck1 = remember { mutableStateOf(false) }
        val kitchenCheck2 = remember { mutableStateOf(false) }
        val kitchenCheck3 = remember { mutableStateOf(false) }
        val kitchenCheck4 = remember { mutableStateOf(false) }
        val kitchenCheckList = listOf(kitchenCheck1, kitchenCheck2, kitchenCheck3, kitchenCheck4)

        val allCheckList = ArrayList<MutableState<Boolean>>()
        allCheckList.addAll(0, bedCheckList)
        allCheckList.addAll(1, bathCheckList)
        allCheckList.addAll(2, livingChecklist)
        allCheckList.addAll(3, kitchenCheckList)

        Column {

            // mutable state charges
            val apartManCharge = remember {
                mutableStateOf(defaultPrice)
            }
            val bedRoomCharge = remember {
                mutableStateOf(0)
            }
            val bathRoomCharge = remember {
                mutableStateOf(0)
            }
            val livingRoomCharge = remember {
                mutableStateOf(0)
            }
            val kitchenRoomCharge = remember {
                mutableStateOf(0)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(8f)
                    .verticalScroll(rememberScrollState())
            )
            {

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(8.dp)
                )

                Text(
                    text = data.name[0],
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Divider(
                    modifier = Modifier.padding(
                        start = 6.dp,
                        top = 16.dp,
                        end = 6.dp,
                        bottom = 8.dp
                    ),
                    thickness = 2.dp,
                    color = Color.LightGray
                )

                Text(
                    text = apartMan.name[0],
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Choose ${apartMan.range}",
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
                RadioButtons(
                    apartMan.list,
                    bedRoomServicesList,
                    bathRoomServicesList,
                    kitchenServicesList,
                    livingRoomServicesList,
                    bedRoom,
                    bathRoom,
                    kitchen,
                    livingRoom,
                    apartManCharge,
                    allCheckList,
                    bedRoomCharge,
                    bathRoomCharge,
                    livingRoomCharge,
                    kitchenRoomCharge
                )

                Divider(
                    modifier = Modifier.padding(
                        start = 6.dp,
                        top = 16.dp,
                        end = 6.dp,
                        bottom = 8.dp
                    ),
                    thickness = 2.dp,
                    color = Color.LightGray
                )

                // bed room services list
                Text(
                    text = bedRoom.value?.name?.get(0) ?: "kanti",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Choose up to ${bedRoom.value!!.max_range}",
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )

                Column {

                    ServicesOption(
                        checkList = bedCheckList,
                        myCheck = bedCheck1,
                        chargeType = bedRoomCharge,
                        services = bedRoom,
                        index = 0
                    )

                    ServicesOption(
                        checkList = bedCheckList,
                        myCheck = bedCheck2,
                        chargeType = bedRoomCharge,
                        services = bedRoom,
                        index = 1
                    )

                    ServicesOption(
                        checkList = bedCheckList,
                        myCheck = bedCheck3,
                        chargeType = bedRoomCharge,
                        services = bedRoom,
                        index = 2
                    )

                }

                Divider(
                    modifier = Modifier.padding(
                        start = 6.dp,
                        top = 16.dp,
                        end = 6.dp,
                        bottom = 8.dp
                    ),
                    thickness = 2.dp,
                    color = Color.LightGray
                )

                // bath room services list
                Text(
                    text = bathRoom.value?.name?.get(0) ?: "kanti",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Choose up to ${bathRoom.value!!.max_range}",
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
                Column {

                    ServicesOption(
                        checkList = bathCheckList,
                        myCheck = bathCheck1,
                        chargeType = bathRoomCharge,
                        services = bathRoom,
                        index = 0
                    )

                    ServicesOption(
                        checkList = bathCheckList,
                        myCheck = bathCheck2,
                        chargeType = bathRoomCharge,
                        services = bathRoom,
                        index = 1
                    )
                }

                Divider(
                    modifier = Modifier.padding(
                        start = 6.dp,
                        top = 16.dp,
                        end = 6.dp,
                        bottom = 8.dp
                    ),
                    thickness = 2.dp,
                    color = Color.LightGray
                )

                // living room services list
                Text(
                    text = livingRoom.value?.name?.get(0) ?: "kanti",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Choose up to ${livingRoom.value!!.max_range}",
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
                Column {

                    ServicesOption(
                        checkList = livingChecklist,
                        myCheck = livingCheck1,
                        chargeType = livingRoomCharge,
                        services = livingRoom,
                        index = 0
                    )

                    ServicesOption(
                        checkList = livingChecklist,
                        myCheck = livingCheck2,
                        chargeType = livingRoomCharge,
                        services = livingRoom,
                        index = 1
                    )

                    ServicesOption(
                        checkList = livingChecklist,
                        myCheck = livingCheck3,
                        chargeType = livingRoomCharge,
                        services = livingRoom,
                        index = 2
                    )
                }

                Divider(
                    modifier = Modifier.padding(
                        start = 6.dp,
                        top = 16.dp,
                        end = 6.dp,
                        bottom = 8.dp
                    ),
                    thickness = 2.dp,
                    color = Color.LightGray
                )

                // kitchen services list
                Text(
                    text = kitchen.value?.name?.get(0) ?: "kanti",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Choose up to ${kitchen.value!!.max_range}",
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
                Column {

                    ServicesOption(
                        checkList = kitchenCheckList,
                        myCheck = kitchenCheck1,
                        chargeType = kitchenRoomCharge,
                        services = kitchen,
                        index = 0
                    )

                    ServicesOption(
                        checkList = kitchenCheckList,
                        myCheck = kitchenCheck2,
                        chargeType = kitchenRoomCharge,
                        services = kitchen,
                        index = 1
                    )

                    ServicesOption(
                        checkList = kitchenCheckList,
                        myCheck = kitchenCheck3,
                        chargeType = kitchenRoomCharge,
                        services = kitchen,
                        index = 2
                    )

                    ServicesOption(
                        checkList = kitchenCheckList,
                        myCheck = kitchenCheck4,
                        chargeType = kitchenRoomCharge,
                        services = kitchen,
                        index = 3
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val totalItem = remember {
                    mutableStateOf(1)
                }

                val finalAmount = totalItem.value * (apartManCharge.value + bedRoomCharge.value + bathRoomCharge.value + kitchenRoomCharge.value + livingRoomCharge.value)

                Row(
                    modifier = Modifier
                        .padding(12.dp)
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
                            .width(20.dp)
                            .clickable {
                                if (totalItem.value > 1)
                                    totalItem.value = totalItem.value - 1

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
                        text = "${totalItem.value}",
                        modifier = Modifier
                            .padding(all = 9.dp)
                            .width(20.dp),
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
                            .width(20.dp)
                            .clickable {
                                totalItem.value = totalItem.value + 1
                            },
                        textAlign = TextAlign.Center
                    )

                }

                Button(
                    onClick = { },
                    modifier = Modifier
                        .padding(10.dp)
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = "Add To Cart - ₹ ${finalAmount}",
                        modifier = Modifier.padding(5.dp)
                    )

                }

            }
        }

    }
}