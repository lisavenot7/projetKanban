<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import NavbarUtilisateur from "../components/NavbarUtilisateur.vue"
import { useRouter ,useRoute} from "vue-router"
import usersData from '../bdd/users.json'

const router = useRouter()
const route = useRoute()

const goToModifie = () => {
  if (route.path.startsWith('/private')) {
    router.push("/private/profil/modifier")
  } else {
    router.push("/admin/profil/modifier")
  }
}

const pseudo = "robertD"

const user = usersData.find(u => u.pseudo === pseudo) || {}
</script>

<template>
  <NavbarUtilisateur v-if="route.path.startsWith('/private')" />
  <Navbar v-else />
  <div class="container">
    <div class="box">
      <h1>Mon profil</h1>

      <h3>Nom: {{ user.nom }}</h3>
      <h3>Prénom: {{ user.prenom }}</h3>
      <h3>Mail: {{ user.mail }}</h3>
      <h3 v-if="user.isAdmin === 1">Rôle: Administrateur</h3>
      <h3 v-else>Rôle: Utilisateur</h3>
      <button class="boutonsNav" @click="goToModifie">Modifier</button>
    </div>
  </div>
</template>
