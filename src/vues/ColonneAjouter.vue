<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"

import { useRouter, useRoute} from "vue-router"
import { ref,onMounted } from 'vue'

const router = useRouter()
const route = useRoute()

const idTab = Number(route.params.id)

const nom = ref("")
const error = ref("")

const createur = ref("")

const annuler = async () => {
  router.push(`/private/tableaux/${idTab}`)
}
const valider = async () => {
  if(nom.value===""){
    error.value = "Veuillez remplir le champ"
    return
  }
  const colonne = {
    clnNom: nom.value,
    tabId:idTab
  }
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}/colonnes`, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(colonne)
    })
    if (!response.ok) {
      const data = await response.json()
      error.value = data.message || "Erreur lors de la création de la colonne"
      return
    }
    const data = await response.json()
    console.log("Colonne créée:", data)
    router.push(`/private/tableaux/${idTab}`)
  } catch (err) {
    console.error(err)
    error.value = "Impossible de contacter le serveur"
  }
}

async function fetchCreateur() {
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
      console.error("Erreur récupération créateur", response.status)
      return
    }
    const data = await response.json()
    createur.value = data.data.cptId
    if(createur.value != Number(idUser)){
      router.push("/private/tableaux")
    }
  } catch (err) {
    console.error("Impossible de récupérer le créateur", err)
  }
}

const token = localStorage.getItem("jwtToken")
const idUser = localStorage.getItem("cptId")
onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "1") {
    router.push("/admin")
  }
  fetchCreateur()
})
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>Ajouter une colonne </h1>
      <input v-model="nom" placeholder="Nom" />
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button> 
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
      <p v-if="error" class="error">&nbsp{{ error }}</p>
    </div>
  </div>
</template>

