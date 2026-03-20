<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"

import { useRouter, useRoute} from "vue-router"
import { ref,onMounted } from 'vue'

const router = useRouter()
const route = useRoute()

const idTab = Number(route.params.id)

const nom = ref("")
const error = ref("")


const annuler = async () => {
  router.push(`/admin/tableaux/${idTab}/colonnes`)
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
    router.push(`/admin/tableaux/${idTab}/colonnes`)
  } catch (err) {
    console.error(err)
    error.value = "Impossible de contacter le serveur"
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

