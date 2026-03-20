<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import { useRouter, useRoute } from "vue-router"
import { ref, computed, onMounted} from 'vue'


const router = useRouter()
const route = useRoute()

const idTableau = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)
const idTache = Number(route.params.idtache)
const idCommentaire = route.params.idcom

const com = ref('') 

const texte = ref("")
const error = ref("")

const annuler = async () => {
  router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}`)
}
const valider = async () => {
  if(texte.value==="" ){
    error.value = "Veuillez remplir le champ"
    return
  }
  const today = new Date();
  const dateOnly = today.toISOString().split("T")[0];

  let com ={
    contenu :texte.value,
    datePublication :dateOnly 
  }

  try {
      const response = await fetch(`http://localhost:10056/commentaires/${idCommentaire}`, {
        method: "PATCH",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        },
        body: JSON.stringify(com)
        
      })
      if (!response.ok) {
        console.error("Erreur modification commentaire", response.status)
        return
      }
      router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}`)
    } catch (err) {
      console.error(err)
      error.value = "Impossible de contacter le serveur"
  }
}

async function fetchCom() {
  try {
    const response = await fetch(`http://localhost:10056/commentaires/${idCommentaire}`, {
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
    com.value = data.data 
    texte.value = com.value.contenu
    if(Number(idUser)!=com.value.cptId){
      router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}`)
    }
  } catch (err) {
    console.error("Impossible de récupérer la tache", err)
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
  fetchCom()
})
</script>

<template>
<Navbar />
<div class="container">
  <div class="box">
    <h1>Modifier un commentaire</h1>

    <textarea 
      v-model="texte" 
      placeholder="Texte du commentaire" 
      rows="4" style="resize: none;">
    </textarea>

    <div class="nav">
      <button class="boutonsNav" @click="valider">Valider</button> 
      <button class="boutonsNav" @click="annuler">Annuler</button>
    </div>

    <p v-if="error" class="error">&nbsp{{ error }}</p>
  </div>
</div>
</template>