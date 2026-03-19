<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"

import { useRouter, useRoute } from "vue-router"
import { ref, onMounted, onBeforeUnmount } from 'vue'

const router = useRouter()
const route = useRoute()

const idTab = Number(route.params.id)  

const participants = ref([])
const createur = ref('')

function goToGestion() {
  router.push(`/admin/tableaux/${idTab}/participants/gestion`)
}


async function fetchParticipants(idTab) {
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}/participants`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération tableau", response.status)
      return
    }
    const data = await response.json()
    participants.value = data.data
    console.log(data.data)
  } catch (err) {
    console.error("Impossible de récupérer le tableau", err)
  }
}

async function fetchCreateur(idTab) {
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}/createur`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération tableau", response.status)
      return
    }
    const data = await response.json()
    createur.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer le tableau", err)
  }
}

const token = localStorage.getItem("jwtToken")
const idUser = localStorage.getItem("cptId")
onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "0") {
    router.push("/private")
  }
  fetchParticipants(idTab)
  fetchCreateur(idTab)
})
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
      ><h3> {{ createur.prenom }} {{ createur.nom }}</h3>
      </div>
      <div 
        class="tache-cards" 
        v-for="par in participants"
        :key="par.cptMail"   
      >
        <h3> {{ par.prenom }} {{ par.nom }}</h3>
      </div>
    </div>
  </div>
</template>