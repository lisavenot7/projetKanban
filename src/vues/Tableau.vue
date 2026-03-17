<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import Colonne from "../components/Colonne.vue"

import { useRouter, useRoute } from "vue-router"
import { ref, onMounted, onBeforeUnmount } from 'vue'

import usersData from '../bdd/users.json'
import colsData from '../bdd/colonnes.json'

const router = useRouter()
const route = useRoute()

const idTab = Number(route.params.id) 

const tableau=ref('')

function getTableauColumns(tableau) {
  if (!tableau || !tableau.colonnes) return []
  return colsData.filter(col => tableau.colonnes.includes(col.id))
}

const tableauColonnes = ref(getTableauColumns(tableau))

const menuOpen = ref(false)

function toggleMenu() {
  menuOpen.value = !menuOpen.value
}

function closeMenu(event) {
  if (!event.target.closest(".menu-container")) {
    menuOpen.value = false
  }
}


const token = localStorage.getItem("jwtToken")
onMounted(() => {
  document.addEventListener("click", closeMenu)
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "1") {
    router.push("/admin")
  }
  fetchTab(idTab)
})

onBeforeUnmount(() => {
  document.removeEventListener("click", closeMenu)
})

function goToModifier() {
  router.push(`/private/tableaux/${idTab}/modifier`)
}

function goToParticipants() {
  router.push(`/private/tableaux/${idTab}/participants`)
}

function goToParticipantsGestion() {
  router.push(`/private/tableaux/${idTab}/participants/gestion`)
}

function goToColonneAdd() {
  router.push(`/private/tableaux/${idTab}/colonnes/ajouter`)
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
    tableau.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer le tableau", err)
  }
}

async function deleteTableau(){
  const confirmed = window.confirm("Êtes-vous sûr de vouloir supprimer ce tableau ? Cette action est irréversible !");
  if (!confirmed) return;
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}`, {
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
  } catch (err) {
    console.error("Impossible de supprimer l'utilisateur", err);
  }
  router.push(`/private/tableaux`)
}
</script>

<template>
<Navbar />
<div class="home">
    <br/><br/>
    <div class="tableau-header">
      <h1>{{ tableau.tabNom }}</h1>

      <div class="menu-container">
        <button class="menu-button" @click="toggleMenu">
          ⋮
        </button>

        <div v-if="menuOpen" class="menu-dropdown">
          <p @click="goToModifier">
            Modifier le tableau
          </p>
          <p @click="goToColonneAdd">
            Ajouter une colonne
          </p>
          <p @click="goToParticipantsGestion">
            Gestion des participants
          </p>
          <p @click="goToParticipants">
            Liste des participants
          </p>
          <p @click="deleteTableau">
            Supprimer le tableau
          </p>
        </div>
      </div>
    </div>
    
    <div style="height:calc(100vh - 150px);">
      <Colonne v-if="tableauColonnes.length > 0" :colonne="tableauColonnes" />
      <p v-else style="color: white; font-size: 18px; padding: 20px 40px;">
        Aucune Colonne trouvée
      </p>
    </div>
</div>
</template>