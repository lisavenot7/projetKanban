<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import Colonne from "../components/Colonne.vue"

import { useRouter, useRoute } from "vue-router"
import { ref, onMounted, onBeforeUnmount } from 'vue'

const router = useRouter()
const route = useRoute()

const idTab = Number(route.params.id) 

const tableau=ref('')

const tableauColonnes = ref([])

const menuOpen = ref(false)

const createur = ref("")

function toggleMenu() {
  menuOpen.value = !menuOpen.value
}

function closeMenu(event) {
  if (!event.target.closest(".menu-container")) {
    menuOpen.value = false
  }
}

const token = localStorage.getItem("jwtToken")
const idUser = Number(localStorage.getItem("cptId"))
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
  fetchColonnes()
  fetchCreateur()
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
    console.error("Impossible de supprimer le tableau", err);
  }
  router.push(`/private/tableaux`)
}

async function fetchColonnes() {
  try {
    const response = await fetch(`http://localhost:10056/tableaux/${idTab}/colonnes`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération des colonnes", response.status)
      return
    }
    const data = await response.json()
    tableauColonnes.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer les colonnes", err)
  }
}

function handleColonneSupprimee(colId) {
  tableauColonnes.value = tableauColonnes.value.filter(
    col => col.clnId !== colId
  )
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
</script>

<template>
<Navbar />
<div class="home">
    <br/><br/>
    <div class="tableau-header">
      <h1>{{ tableau.tabNom }}</h1>

      <div v-if="idUser===createur" class="menu-container">
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
      <div v-else class="menu-container">
        <button class="menu-button" @click="toggleMenu">
          ⋮
        </button>

        <div v-if="menuOpen" class="menu-dropdown">
          <p @click="goToParticipants">
            Liste des participants
          </p>
        </div>
      </div>
    </div>
    
    <div style="height:calc(100vh - 150px);">
      <Colonne v-if="tableauColonnes.length > 0" 
      :colonne="tableauColonnes" 
      @colonneSupprimee="handleColonneSupprimee"
      />
      <p v-else style="color: white; font-size: 18px; padding: 20px 40px;">
        Aucune Colonne trouvée
      </p>
    </div>
</div>
</template>