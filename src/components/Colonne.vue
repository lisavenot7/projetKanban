<script setup>
import { useRouter, useRoute } from "vue-router"
import { defineProps, ref, onMounted, onBeforeUnmount } from 'vue'

import Tache from "../components/Tache.vue"

const router = useRouter()
const route = useRoute()   

const taches = ref([])

const idTableau = Number(route.params.id)

const emit = defineEmits(["colonneSupprimee"])

const createur=ref("")

const props = defineProps({
  colonne: Array
})

const menuOpen = ref(null)

function getTachesParColonne(colonneId) {
  return taches.value.filter(tache => tache.colonneId === colonneId)
}

function toggleMenu(colId) {
  menuOpen.value = menuOpen.value === colId ? null : colId
}

function closeMenu(event) {
  if (!event.target.closest(".menu-container")) {
    menuOpen.value = null
  }
}

onMounted(() => {
  document.addEventListener("click", closeMenu)
  fetchCreateur()
  fetchTaches()
})

onBeforeUnmount(() => {
  document.removeEventListener("click", closeMenu)
})

function goToModifier(colId) {
  router.push(`/private/tableaux/${idTableau}/colonnes/${colId}/modifier`)
}

function goToAjouter(colId) {
  router.push(`/private/tableaux/${idTableau}/colonnes/${colId}/taches/ajouter`)
}

async function deleteColonne (colId) {
  const confirmed = window.confirm("Êtes-vous sûr de vouloir supprimer cette colonne ? Cette action est irréversible !");
  if (!confirmed) return;
  try {
    const response = await fetch(`http://localhost:10056/colonnes/${colId}`, {
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
    emit("colonneSupprimee", colId)
  } catch (err) {
    console.error("Impossible de supprimer la colonne", err);
  }
  router.push(`/private/tableaux/${idTableau}`)
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
  } catch (err) {
    console.error("Impossible de récupérer le créateur", err)
  }
}

const token = localStorage.getItem("jwtToken")
const idUser = Number(localStorage.getItem("cptId"))

async function fetchTaches() {
  try {
    const response = await fetch(`http://localhost:10056/taches`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    credentials: "include" 
});
    if (!response.ok) {
      console.error("Erreur récupération des taches", response.status)
      return
    }
    const data = await response.json()
    taches.value = data.data
  } catch (err) {
    console.error("Impossible de récupérer les taches", err)
  }
}
</script>


<template>
  <div class="row2">
    <div class="colonnes">
      <div
        class="col-cards"
        v-for="col in colonne"
        :key="col.id"
      >

        <div class="tableau-header">
          <h3>{{ col.clnNom }}</h3>

          <div class="menu-container">
            <button class="menu-button2" @click.stop="toggleMenu(col.clnId)">
              ⋮
            </button>

            <div v-if="menuOpen === col.clnId && Number(idUser)===createur" class="menu-dropdown">
              <p @click="() => goToModifier(col.clnId)">Modifier la colonne</p>
              <p @click="() => goToAjouter(col.clnId)">Ajouter une tâche</p>
              <p @click="() => deleteColonne(col.clnId)">Supprimer la colonne et les tâches</p>
            </div>
            <div v-else-if="menuOpen === col.clnId" class="menu-dropdown">
              <p @click="() => goToAjouter(col.clnId)">Ajouter une tâche</p>
            </div>
          </div>

        </div>

        <div>
          <Tache 
            v-if="getTachesParColonne(col.clnId).length > 0" 
            :tache="getTachesParColonne(col.clnId)" 
          />

          <p v-else style="font-size:18px;padding:20px 40px;">
            Aucune Tâche trouvée
          </p>
        </div>

      </div>
    </div>
  </div>
</template>