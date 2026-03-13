<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import { useRouter, useRoute } from "vue-router"
import tabsData from '../bdd/tableaux.json'
import usersData from '../bdd/users.json'

const router = useRouter()
const route = useRoute()

const idParam = Number(route.params.id)  

const tableau = tabsData.find(t => t.id === idParam) || { participants: [] }

const participants = tableau.participants.map(pseudo => {
  return usersData.find(u => u.pseudo === pseudo) || { pseudo: "Inconnu", nom: "", prenom: "" }
})

function goToGestion() {
  router.push(`/private/tableaux/${tableau.id}/participants/gestion`)
}

</script>

<template>
  <Navbar />
  <div class="container">
    <div class="box">
      <h1>Liste des participants</h1>
      <div style="margin-bottom:10px;">
        <button class="boutonsNav" style="height:40px;" @click="goToGestion">Gestion des participants</button>
      </div>
      <div 
        class="tache-cards" 
        v-for="par in participants"
        :key="par.pseudo"   
      >
        <h3>{{ par.pseudo }} - {{ par.prenom }} {{ par.nom }}</h3>
      </div>
    </div>
  </div>
</template>