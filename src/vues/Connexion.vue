<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/Navbar.vue"
import { ref } from "vue"
import { useRouter } from "vue-router"

const username = ref("")
const password = ref("")
const error = ref("")
const router = useRouter()

const handleLogin = async () => {
  try {
    router.push("/private")
  } catch (err) {
    error.value = "Identifiants incorrects"
  }
}

const goSignUp = async () => {
  router.push("/inscription")
}

const login = async () => {
  try {
    router.push("/admin")
  } catch (err) {
    error.value = "Identifiants incorrects"
  }
}
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>Connexion</h1>

      <input v-model="username" placeholder="Nom d'utilisateur / mail" />
      <input v-model="password" type="password" placeholder="Mot de passe" />
      <div >
      <button class="boutonsNav" @click="handleLogin">Se connecter</button>
      </div>
      <div class="signup">
        <p>Si vous n'avez pas encore de compte :</p>
        <button  class="boutonsNav" @click="goSignUp">S'inscrire</button>
      </div>
      <h2 v-if="error" class="error">&nbsp{{ error }}</h2>
    </div>
  </div>
</template>
