package com.example.musicartist

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionSource
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

class SupaBaseClient {
    val ClientSB = createSupabaseClient(
        supabaseUrl = "https://aqeewrcvbofnahpupurm.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFxZWV3cmN2Ym9mbmFocHVwdXJtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTM5Nzg0NzUsImV4cCI6MjAyOTU1NDQ3NX0.b-zp73Pfm3dF-NokGI8wHERH3oF6QYAHAWF7Bc2KSow"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
        //install other modules
    }
}