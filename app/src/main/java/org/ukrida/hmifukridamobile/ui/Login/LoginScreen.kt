package org.ukrida.hmifukridamobile.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.ukrida.hmifukridamobile.R
import org.ukrida.hmifukridamobile.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var rememberMe by remember {
        mutableStateOf(false)
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6FA))

    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center

        ) {

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                elevation = CardDefaults.cardElevation(10.dp)

            ) {

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Image(

                        painter = painterResource(R.drawable.logohmif),

                        contentDescription = null,

                        modifier = Modifier.size(90.dp)

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(

                        text = "HMIF-U Mobile",

                        fontSize = 26.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF1565C0)

                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(

                        text = "Informatics Student Union Portal",

                        color = Color.Gray,

                        fontSize = 14.sp

                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(

                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.SpaceEvenly

                    ) {

                        Text(

                            text = "Log In",

                            color = Color(0xFF1565C0),

                            fontWeight = FontWeight.Bold,

                            modifier = Modifier.clickable { }

                        )

                        Text(

                            text = "Register",

                            color = Color.Gray,

                            modifier = Modifier.clickable {

                                navController.navigate(Screen.Register.route)

                            }

                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Divider()

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(

                        value = email,

                        onValueChange = {

                            email = it

                        },

                        modifier = Modifier.fillMaxWidth(),

                        placeholder = {

                            Text("Email")

                        },

                        leadingIcon = {

                            Icon(

                                imageVector = Icons.Default.Email,

                                contentDescription = null

                            )

                        },

                        shape = RoundedCornerShape(14.dp),

                        singleLine = true,

                        keyboardOptions = KeyboardOptions(

                            keyboardType = KeyboardType.Email

                        ),

                        colors = OutlinedTextFieldDefaults.colors(

                            focusedBorderColor = Color(0xFF1565C0),

                            unfocusedBorderColor = Color.LightGray

                        )

                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    OutlinedTextField(

                        value = password,

                        onValueChange = {

                            password = it

                        },

                        modifier = Modifier.fillMaxWidth(),

                        placeholder = {

                            Text("Password")

                        },

                        leadingIcon = {

                            Icon(

                                imageVector = Icons.Default.Lock,

                                contentDescription = null

                            )

                        },

                        trailingIcon = {

                            IconButton(

                                onClick = {

                                    passwordVisible = !passwordVisible

                                }

                            ) {

                                Icon(

                                    imageVector =
                                        if(passwordVisible)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,

                                    contentDescription = null

                                )

                            }

                        },

                        visualTransformation =

                            if(passwordVisible)

                                VisualTransformation.None

                            else

                                PasswordVisualTransformation(),

                        singleLine = true,

                        shape = RoundedCornerShape(14.dp),

                        colors = OutlinedTextFieldDefaults.colors(

                            focusedBorderColor = Color(0xFF1565C0),

                            unfocusedBorderColor = Color.LightGray

                        )

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(

                        modifier = Modifier.fillMaxWidth(),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Checkbox(

                            checked = rememberMe,

                            onCheckedChange = {

                                rememberMe = it

                            }

                        )

                        Text(

                            text = "Remember Me",

                            color = Color.Gray

                        )

                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    if(errorMessage.isNotEmpty()){

                        Text(

                            text = errorMessage,

                            color = Color.Red,

                            fontSize = 13.sp

                        )

                        Spacer(modifier = Modifier.height(10.dp))

                    }

                    Button(

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),

                        shape = RoundedCornerShape(14.dp),

                        colors = ButtonDefaults.buttonColors(

                            containerColor = Color(0xFF1565C0)

                        ),

                        onClick = {

                            when{

                                email == "student@ukrida.ac.id" &&
                                        password == "123456" -> {

                                    navController.navigate(Screen.Home.route)

                                }

                                email == "admin@hmif.ac.id" &&
                                        password == "admin123" -> {

                                    navController.navigate(Screen.AdminDashboard.route)

                                }

                                else -> {

                                    errorMessage = "Email atau Password salah"

                                }

                            }

                        }

                    ) {

                        Text(

                            text = "Sign In",

                            color = Color.White,

                            fontSize = 18.sp

                        )

                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(

                        text = "Admin Access?",

                        color = Color.Gray,

                        fontSize = 13.sp

                    )

                    TextButton(

                        onClick = {

                            navController.navigate(Screen.AdminDashboard.route)

                        }

                    ) {

                        Text(

                            text = "Staff Portal",

                            color = Color(0xFF1565C0),

                            fontWeight = FontWeight.Bold

                        )

                    }

                }

            }

        }

    }

}