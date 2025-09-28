package com.task.moviesdbapp.presentation.auth

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun AccountChip(
    modifier: Modifier = Modifier,
    vm: AuthViewModel = hiltViewModel()
) {
    val state by vm.container.stateFlow.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { res ->
        vm.handleSignInResult(res.data) // parses result inside VM
    }

    if (state.signedIn) {
        ElevatedAssistChip(
            onClick = { vm.signOut() },
            label = { Text(state.profile?.displayName ?: (state.profile?.email ?: "Sign out")) },
            leadingIcon = {
                val pic = state.profile?.photoUrl
                if (!pic.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(pic),
                        contentDescription = null
                    )
                }
            },
            modifier = modifier
        )
    } else {
        AssistChip(
            onClick = { launcher.launch(vm.signInIntent()) }, // ← no Activity needed
            label = { Text("Sign in with Google") },
            modifier = modifier
        )
    }
}