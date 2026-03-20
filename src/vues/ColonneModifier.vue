<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"

import { useRouter, useRoute} from "vue-router"
import { ref,onMounted} from 'vue'

const router = useRouter()
const route = useRoute()

const idTableau = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)

const colonne = ref('')

const nom = ref('')
const error = ref("")

const createur = ref("")

const annuler = async () => {
  router.push(`/private/tableaux/${idTableau}`)
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
    router.push(`/private/tableaux/${idTableau}`)
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
  fetchColonne(idColonne)
  fetchCreateur()
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

