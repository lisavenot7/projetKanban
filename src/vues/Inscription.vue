<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/Navbar.vue"
import { useRouter } from "vue-router"
import { computed, ref } from 'vue'

const router = useRouter() 

const nom = ref("")
const prenom = ref("")
const mail = ref("")
const password = ref("")
const confirmPassword = ref("")


const error = ref("")


const annuler = async () => {
  router.push("/")
}

const valider = async () => {
  error.value = ""

  if (password.value ==="" 
    || confirmPassword.value==="" 
    || nom.value==="" 
    || prenom.value==="" 
    || mail.value==="") {
    error.value = "Les champs doivent être remplis"
    return
  }

  if (password.value !== confirmPassword.value) {
    error.value = "Les mots de passe ne correspondent pas"
    return
  }

  const registerUserDto = {
    email: mail.value,
    password: password.value,
    prenom:prenom.value,
    nom:nom.value
  }
  try {
    const response = await fetch("http://localhost:10056/auth/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(registerUserDto)
    })

    if (!response.ok) {
      const data = await response.json()
      error.value = data.message || "Un autre compte existe déjà avec ce mail"
      return
    }

    const data = await response.json()
    console.log("Compte créé:", data)
    router.push("/connexion") 
  } catch (err) {
    console.error(err)
    error.value = "Impossible de contacter le serveur"
  }
}

const showPassword = ref(false)
const togglePassword = () => {
  showPassword.value = !showPassword.value
}
const showPassword2 = ref(false)
const togglePassword2 = () => {
  showPassword2.value = !showPassword2.value
}
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>S'inscrire</h1>
      <input v-model="nom" placeholder="Nom" />
      <input v-model="prenom" placeholder="Prenom" />
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
      <div class="input-password">
        <input
          v-model="confirmPassword"
          :type="showPassword2 ? 'text' : 'password'"
          placeholder="Confirmer le mot de passe"
        />
        <span class="eye" @click="togglePassword2">
          <svg v-if="!showPassword2" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" stroke="black" fill="none" stroke-width="2">
            <path d="M1 12C3.5 7 7.5 4 12 4s8.5 3 11 8c-2.5 5-6.5 8-11 8S3.5 17 1 12z"/>
            <circle cx="12" cy="12" r="3"/>
          </svg>
          <svg v-else xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" stroke="black" fill="none" stroke-width="2">
            <path d="M1 1l22 22"/>
            <path d="M17.94 17.94A10.94 10.94 0 0112 20C7.5 20 3.5 17 1 12a21.86 21.86 0 015.06-6.94"/>
          </svg>
        </span>
      </div>
      
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button>
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
      <p v-if="error" class="error">&nbsp{{ error }}</p>
    </div>
  </div>
</template>
