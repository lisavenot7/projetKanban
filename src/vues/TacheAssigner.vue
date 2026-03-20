<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"

import { useRouter, useRoute } from "vue-router"
import { ref, computed, onMounted } from 'vue'

const router = useRouter()
const route = useRoute()

const idTableau = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)
const idTache = Number(route.params.idtache)

const tache = ref('')
const assignéA = ref("")
const error=ref("")

const participants = ref([])

const annuler = async () => {
  router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}`)
}
const valider = async () => {
  let tache ={
    compteId:assignéA.value,
  }
  console.log(tache)
  try {
      const response = await fetch(`http://localhost:10056/taches/${idTache}`, {
        method: "PATCH",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        },
        body: JSON.stringify(tache)
        
      })
      if (!response.ok) {
        console.error("Erreur modification assignation", response.status)
        return
      }
      router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}`)

    } catch (err) {
      console.error(err)
      error.value = "Impossible de contacter le serveur"
  }
  }

async function fetchTache() {
  try {
    const response = await fetch(`http://localhost:10056/taches/${idTache}`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      credentials: "include"
    })
    if (!response.ok) {
      console.error("Erreur récupération tache", response.status)
      return
    }
    const data = await response.json()
    tache.value = data.data 
    assignéA.value  = tache.value.compteId || ""
  } catch (err) {
    console.error("Impossible de récupérer la tache", err)
  }
}

async function fetchParticipants() {
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTableau}/participants`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération participants", response.status)
      return
    }
    const data = await response.json()
    participants.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer les participants", err)
  }
}

async function fetchCreateur() {
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTableau}/createur`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération créateur", response.status)
      return
    }
    const data = await response.json()
    participants.value .push(data.data)
  } catch (err) {
    console.error("Impossible de récupérer le créateur", err)
  }
}

const token = localStorage.getItem("jwtToken")
onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "1") {
    router.push("/admin")
  }
  fetchTache()
  fetchParticipants()
  fetchCreateur()
})
</script>

<template>
<Navbar />
<div class="container">
  <div class="box">
    <h1>Assigner un utilisateur</h1>

    <h3 :style="{ marginTop: '5px' }">Assigné à :</h3>
    <select v-model="assignéA">
      <option value="">Choisir un participant</option>
      <option 
        v-for="user in participants" 
        :key="user.cptId" 
        :value="user.cptId"
      >
        {{ user.prenom }} {{ user.nom }}
      </option>
    </select>

    

    <div class="nav">
      <button class="boutonsNav" @click="valider">Valider</button> 
      <button class="boutonsNav" @click="annuler">Annuler</button>
    </div>

    <p v-if="error" class="error">&nbsp{{ error }}</p>
  </div>
</div>
</template>