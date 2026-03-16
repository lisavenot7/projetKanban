<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import NavbarUtilisateur from "../components/NavbarUtilisateur.vue"

import { useRouter ,useRoute} from "vue-router"
import { ref,onMounted } from "vue"

const router = useRouter()
const route = useRoute()

const goToModifie = () => {
  if (route.path.startsWith('/private')) {
    router.push("/private/profil/modifier")
  } else {
    router.push("/admin/profil/modifier")
  }
}

const token = localStorage.getItem("jwtToken")
const idUser = localStorage.getItem("cptId")
onMounted(() => {
  if (!token) {
    router.push("/connexion")
  }
  monProfil()
})

const user=ref('')

async function monProfil() {
  try {
    const response = await fetch(`http://localhost:10056/comptes/${idUser}`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération profil", response.status)
      return
    }
    const res = await response.json()
    user.value = res.data
  } catch (err) {
    console.error("Impossible de récupérer le profil", err)
  }
}

</script>

<template>
  <NavbarUtilisateur v-if="route.path.startsWith('/private')" />
  <Navbar v-else />
  <div class="container">
    <div class="box">
      <h1>Mon profil</h1>

      <h3>Nom: {{ user.nom }}</h3>
      <h3>Prénom: {{ user.prenom }}</h3>
      <h3>Mail: {{ user.cptMail }}</h3>
      <h3 v-if="user.cptIsAdmin === 1">Rôle: Administrateur</h3>
      <h3 v-else>Rôle: Utilisateur</h3>
      <button class="boutonsNav" @click="goToModifie">Modifier</button>
    </div>
  </div>
</template>
