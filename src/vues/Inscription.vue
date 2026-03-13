<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/Navbar.vue"
import { useRouter } from "vue-router"
import { computed, ref } from 'vue'

const router = useRouter() 

const pseudo = ref("")
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

  if (password.value !== confirmPassword.value) {
    error.value = "Les mots de passe ne correspondent pas"
    return
  }

  const registerUserDto = {
    email: mail.value,
    password: password.value,
    fullName: pseudo.value
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
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>S'inscrire</h1>
      <input v-model="pseudo" placeholder="Nom d'utilisateur / Pseudo" />
      <input v-model="nom" placeholder="Nom" />
      <input v-model="prenom" placeholder="Prenom" />
      <input v-model="mail" placeholder="Mail" />

      <input type="password" v-model="password" placeholder="Mot de passe" />
      <input type="password" v-model="confirmPassword" placeholder="Confirmer le mot de passe" />
      
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button>
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
      <h2 v-if="error" class="error">&nbsp{{ error }}</h2>
    </div>
  </div>
</template>
