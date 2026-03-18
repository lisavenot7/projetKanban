<style src="../assets/css/style.css"></style>

<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import Commentaire from "../components/Commentaire.vue"

import { useRouter, useRoute } from "vue-router"
import { ref ,onMounted } from 'vue'

const router = useRouter()
const route = useRoute()

const idTableau = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)
const idTache = Number(route.params.idtache)

const tache = ref('')
const commentaires = ref([])
const user = ref('')

const idUtilisateur = ref("")

const menuOpen = ref(false)
function toggleMenu() {
  menuOpen.value = !menuOpen.value
}
function goToModifier() {
    router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}/modifier`)
}
function goToNewCom() {
    router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}/commentaires/ajouter`)
}

function goToAssigner() {
    router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}/assigner`)
}

async function fetchCommentaires() {
  try {
    const response = await fetch(`http://localhost:10056/taches/${idTache}/commentaires`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      credentials: "include"
    })
    if (!response.ok) {
      console.error("Erreur récupération des commentaires", response.status)
      return
    }
    const data = await response.json()
    commentaires.value = data.data 
  } catch (err) {
    console.error("Impossible de récupérer les commentaires", err)
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
    if(tache.value.compteId!=null){
      fetchUser(tache.value.compteId)
    }
  } catch (err) {
    console.error("Impossible de récupérer la tache", err)
  }
}

async function deleteTache () {
  const confirmed = window.confirm("Êtes-vous sûr de vouloir supprimer cette tâche ? Cette action est irréversible !");
  if (!confirmed) return;
  try {
    const response = await fetch(`http://localhost:10056/taches/${idTache}`, {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify() 
    });
    if (!response.ok) {
      console.error("Erreur lors de la suppression", response.status);
      return;
    }
    router.push(`/private/tableaux/${idTableau}`)
  } catch (err) {
    console.error("Impossible de supprimer la tâche", err);
  }
}

async function fetchUser(idUtilisateur) {
  try {
    const response = await fetch(`http://localhost:10056/comptes/${idUtilisateur}`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération utilisateur", response.status)
      return
    }
    const res = await response.json()
    user.value = res.data
  } catch (err) {
    console.error("Impossible de récupérer l'utilisateur'", err)
  }
}

const today = new Date();
today.setHours(0, 0, 0, 0); 

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
  fetchCommentaires()
})

function handleComSupprimee(comId) {
  commentaires.value = commentaires.value.filter(
    com => com.id !== comId
  )
}
</script>

<template>
<Navbar />
<div class="container">
  <div class="tache-box">
    <div v-if="tache!=null">
      <div  class="tableau-header">
        <h1>
          <span v-if="tache.tchPriorite === 1">
            <svg width="30" height="30" viewBox="0 0 24 24">
              <path d="M12 3L1 21h22L12 3z" fill="#e53935"/>
              <rect x="11" y="9" width="2" height="6" fill="white"/>
              <rect x="11" y="17" width="2" height="2" fill="white"/>
            </svg>
          </span>
          {{ tache.tchTitre }}
          <span v-if="tache.tchPriorite === 1" >
            -
            <span v-if="tache.tchPriorite === 1" style="color:red;">
              Prioritaire
            </span>
          </span>
        </h1>
        <div class="menu-container">
          <button class="menu-button3" @click.stop="toggleMenu">⋮</button>
          <div v-if="menuOpen" class="menu-dropdown">
            <p @click="goToAssigner">Assigner un participant</p>
            <p @click="goToNewCom">Ajouter un commentaire</p>
            <p @click="goToModifier">Modifier la tâche</p>
            <p @click="deleteTache">Supprimer la tâche</p>
          </div>
        </div>
      </div>
      <div v-if="tache.tchDateLimite!=null">
        <h2>A finir avant le {{tache.tchDateLimite}}</h2>
      </div>
      <div>
        <h2>Assigné à {{user.nom}} {{user.prenom}}</h2>
        <p>{{tache.tchDescription}}</p>
        <h2 v-if="tache.tchStatus==='Terminé'" 
          style="background-color:green; 
            width: 15%;text-align:center; 
            border-radius:10px;
            color:white;">
          {{ tache.tchStatus }}
        </h2>
        <h2 v-else-if="tache.tchStatus==='En cours'" 
          style="background-color:orange; 
            width: 15%;text-align:center; 
            border-radius:10px;
            color:white;">
          {{ tache.tchStatus }}
        </h2>
        <h2 v-else 
          style="background-color:black; 
          width: 15%;text-align:center; 
          border-radius:10px;
          color:white;">
          {{ tache.tchStatus }}
        </h2>
        <h2 v-if="today > new Date(tache.tchDateLimite) && tache.tchStatus!=='Terminé'" 
          style="background-color:red; 
            width: 15%;text-align:center; 
            border-radius:10px;
            color:white;">
          Retard
        </h2>
      </div>
      <div class="com-box">
        <div style="margin-left:5%;">
          <h2>Commentaires ({{commentaires.length}})</h2>
          <Commentaire v-if="commentaires.length > 0" :commentaires="commentaires" @comSupprimee="handleComSupprimee"/>
            
          <p v-else style="margin-left:5%;">Aucun commentaire pour cette tâche</p>
        </div>
      </div>
    </div>
    <div v-else>
        <h1>Tâche introuvable</h1>
    </div>
  </div>
</div>
</template>