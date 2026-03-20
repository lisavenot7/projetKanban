<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"

import { useRouter, useRoute} from "vue-router"
import { ref,onMounted } from 'vue'


const router = useRouter()
const route = useRoute()

const idTab = Number(route.params.id)
const tab = ref('')
const nom = ref('')
const error = ref('')

const createur = ref("")

const annuler = async () => {
  router.push(`/admin/tableaux`)
}
const valider = async () => {
  let tableau = {
      tabNom:nom.value
    }

  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}`, {
      method: "PATCH",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(tableau)
    })
    const data = await response.json()
    if (!response.ok) {
      error.value = data.message || "ERREUR LORS DE LA MODIFICATION"
      return
    }
    tab.value = data.data
    router.push(`/admin/tableaux`)
  } catch (err) {
    console.error("Impossible de modifier le tableau", err)
    error.value = "Erreur serveur"
  }
}

async function fetchTab(idTab) {
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}`, {
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
    tab.value = data.data
    nom.value = tab.value.tabNom
  } catch (err) {
    console.error("Impossible de récupérer le tableau", err)
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
  fetchTab(idTab)
  fetchCreateur()
})
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>Modifier un tableau</h1>
      <input v-model="nom" placeholder="Nom" />

        
      
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button> 
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
      <p v-if="error" class="error">&nbsp{{ error }}</p>
    </div>
  </div>
</template>

