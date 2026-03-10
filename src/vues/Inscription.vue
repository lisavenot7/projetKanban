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
  router.push("/")
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
