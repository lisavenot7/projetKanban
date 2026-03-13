<style src="../assets/css/style.css"></style>
<script setup>
import { useRouter } from "vue-router"
const router = useRouter()
const logout = () => {
  localStorage.removeItem("jwtToken")
  router.push("/")
}
</script>

<template>
  <nav class="navbar">
    <div class="logo">Planora</div>
    <div class="links">
      <router-link to="/private">Accueil</router-link>
      <router-link to="/private/tableaux">Tableaux</router-link>
      <router-link to="/private/profil">Mon profil</router-link>
      <a @click="logout">Déconnexion</a>
    </div>
  </nav>
</template>

