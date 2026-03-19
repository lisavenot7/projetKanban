<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/Navbar.vue"
import { ref } from "vue"
import { useRouter } from "vue-router"

const mail = ref("")
const password = ref("")
const error = ref("")
const router = useRouter()

const goSignUp = async () => {
  router.push("/inscription")
}

const login = async () => {
  error.value = ""
  if (password.value === "" || mail.value === "") {
    error.value = "Les champs doivent être remplis"
    return
  }
  const loginUserDto = {
    email: mail.value,
    password: password.value,
  }
  try {
    const response = await fetch("http://localhost:10056/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(loginUserDto)
    })

    if (!response.ok) {
      const data = await response.json()
      error.value = data.message || "Identifiant ou mot de passe incorrect"
      return
    }

    const data = await response.json()
    
    localStorage.setItem("jwtToken", data.token)
    localStorage.setItem("cptId", data.cptId)
    localStorage.setItem("isAdmin", data.isAdmin)
    
    const isAdmin = parseInt(localStorage.getItem("isAdmin"))
    
    if (isAdmin === 0) {
      router.push("/private")
    } else {
      router.push("/admin")
    }
  } catch (err) {
    console.error(err)
    error.value = "Impossible de contacter le serveur"
  }
}

const showPassword = ref(false)
const togglePassword = () => {
  showPassword.value = !showPassword.value
}
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>Connexion</h1>

      <input v-model="mail" placeholder="Mail" />
      <div class="input-password">
        <input
          v-model="password"
          :type="showPassword ? 'text' : 'password'"
          placeholder="Mot de passe"
        />
        <span class="eye" @click="togglePassword">
          <svg v-if="!showPassword" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" stroke="black" fill="none" stroke-width="2">
            <path d="M1 12C3.5 7 7.5 4 12 4s8.5 3 11 8c-2.5 5-6.5 8-11 8S3.5 17 1 12z"/>
            <circle cx="12" cy="12" r="3"/>
          </svg>
          <svg v-else xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" stroke="black" fill="none" stroke-width="2">
            <path d="M1 1l22 22"/>
            <path d="M17.94 17.94A10.94 10.94 0 0112 20C7.5 20 3.5 17 1 12a21.86 21.86 0 015.06-6.94"/>
          </svg>
        </span>
      </div>
      <div >
      <button class="boutonsNav" @click="login">Se connecter</button>
      </div>
      <div class="nav">
        <p>Si vous n'avez pas encore de compte :</p>
        <button  class="boutonsNav" @click="goSignUp">S'inscrire</button>
      </div>
      <p v-if="error" class="error">&nbsp{{ error }}</p>
    </div>
  </div>
</template>
