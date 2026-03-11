<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import Colonne from "../components/Colonne.vue"

import { useRouter, useRoute } from "vue-router"
import { ref, onMounted, onBeforeUnmount } from 'vue'

import usersData from '../bdd/users.json'
import tabsData from '../bdd/tableaux.json'
import colsData from '../bdd/colonnes.json'

const router = useRouter()
const route = useRoute()

const idParam = Number(route.params.id) 

const tableau = tabsData.find(t => t.id === idParam)

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

onMounted(() => {
  document.addEventListener("click", closeMenu)
})

onBeforeUnmount(() => {
  document.removeEventListener("click", closeMenu)
})

function goToModifier() {
  router.push(`/private/tableaux/${tableau.id}/modifier`)
}
</script>

<template>
<Navbar />
<div class="home">
    <br/><br/>
    <div class="tableau-header">
      <h1>{{ tableau.titre }}</h1>

      <div class="menu-container">
        <button class="menu-button" @click="toggleMenu">
          ⋮
        </button>

        <div v-if="menuOpen" class="menu-dropdown">
          <p @click="goToModifier">
            Modifier le tableau
          </p>
          <p>
            Ajouter une colonne
          </p>
          <p>
            Inviter de nouveaux participants
          </p>
          <p>
            Liste des participants
          </p>
          <p>
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