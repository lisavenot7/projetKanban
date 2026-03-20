<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import { useRouter, useRoute } from "vue-router"
import { ref, computed, onMounted } from 'vue'

const router = useRouter()
const route = useRoute()

const idTableau = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)

const nom = ref("")
const dateLimite = ref(null)
const assignéA = ref("")   
const description = ref("")
const prioritaire = ref(false)
const error = ref("")

const participants = ref([])

const annuler = async () => {
  router.push(`/admin/tableaux/${idTableau}/colonnes/${idColonne}/taches`)
}
const valider = async () => {
  if(nom.value==="" ||
  description.value===""){
    error.value = "Veuillez remplir les champs nom et description"
    return
  }
  let tache ={
    tchTitre :nom.value,
    tchDescription : description.value,
    tchDateLimite :dateLimite.value,
    tchPriorite:prioritaire.value ? 1:0,
    compteId:assignéA.value,
    colonneId:idColonne,
    tchStatus:"A faire"
  }
  try {
      const response = await fetch(`http://localhost:10056/taches`, {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        },
        body: JSON.stringify(tache)
        
      })
      if (!response.ok) {
        console.error("Erreur création tache", response.status)
        return
      }
      router.push(`/admin/tableaux/${idTableau}/colonnes/${idColonne}/taches`)
    } catch (err) {
      console.error(err)
      error.value = "Impossible de contacter le serveur"
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
  if (admin === "0") {
    router.push("/private")
  }
  fetchParticipants()
  fetchCreateur()
})
</script>

<template>
<Navbar />
<div class="container">
  <div class="box">
    <h1>Ajouter une tâche</h1>

    <input v-model="nom" placeholder="Nom" />

    <h3 :style="{ marginTop: '5px' }">Assigné à (facultatif) :</h3>
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

    <h3 :style="{ marginTop: '5px' }">Date limite (facultatif)</h3>
    <input v-model="dateLimite" type="date" placeholder="Date limite (facultatif)" />

    <textarea 
      v-model="description" 
      placeholder="Description" 
      rows="4" style="resize: none;">
    </textarea>

    <div class="checkbox-container">
      <label class="checkbox-label">
        <input type="checkbox" v-model="prioritaire" />
        <h3 :style="{ marginTop: '5px' }">Prioritaire</h3>
      </label>
    </div>

    <div class="nav">
      <button class="boutonsNav" @click="valider">Valider</button> 
      <button class="boutonsNav" @click="annuler">Annuler</button>
    </div>

    <p v-if="error" class="error">&nbsp{{ error }}</p>
  </div>
</div>
</template>