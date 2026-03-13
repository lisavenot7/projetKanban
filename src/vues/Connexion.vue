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
mail
    const data = await response.json()
    console.log("Connecté", data)
    router.push("/admin") 
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
      <h1>Connexion</h1>

      <input v-model="mail" placeholder="Mail" />
      <input v-model="password" type="password" placeholder="Mot de passe" />
      <div >
      <button class="boutonsNav" @click="login">Se connecter</button>
      </div>
      <div class="nav">
        <p>Si vous n'avez pas encore de compte :</p>
        <button  class="boutonsNav" @click="goSignUp">S'inscrire</button>
      </div>
      <h2 v-if="error" class="error">&nbsp{{ error }}</h2>
    </div>
  </div>
</template>
