<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"

import { useRouter, useRoute} from "vue-router"
import { ref,onMounted} from 'vue'

const router = useRouter()
const route = useRoute()

const idTableau = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)

const colonne = ref('')

const nom = ref('')
const error = ref("")

const annuler = async () => {
  router.push(`/admin/tableaux/${idTableau}/colonnes`)
}
const valider = async () => {
  if(nom.value===""){
    error.value = "Veuillez remplir le champ"
    return
  }
  let colonne = {
      clnNom:nom.value
    }

  try {
    const response = await fetch(`http://localhost:10056/colonnes/${idColonne}`, {
      method: "PATCH",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(colonne)
    })
    const data = await response.json()
    if (!response.ok) {
      error.value = data.message || "ERREUR LORS DE LA MODIFICATION"
      return
    }
    router.push(`/admin/tableaux/${idTableau}/colonnes`)
  } catch (err) {
    console.error("Impossible de modifier la colonne", err)
    error.value = "Erreur serveur"
  }
}

async function fetchColonne(idColonne) {
  try {
    const response = await fetch(`http://localhost:10056/colonnes/${idColonne}`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération colonne", response.status)
      return
    }
    const data = await response.json()
    colonne.value = data.data
    nom.value = colonne.value.clnNom
  } catch (err) {
    console.error("Impossible de récupérer le tableau", err)
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
  fetchColonne(idColonne)
})
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>Modifier une colonne </h1>
      <input v-model="nom" placeholder="Nom" />
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button> 
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
      <p v-if="error" class="error">&nbsp{{ error }}</p>
    </div>
  </div>
</template>

