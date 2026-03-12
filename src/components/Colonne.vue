<script setup>
import { useRouter, useRoute } from "vue-router"
import { defineProps, ref, onMounted, onBeforeUnmount } from 'vue'
import taches from '../bdd/taches.json'
import Tache from "../components/Tache.vue"

const router = useRouter()
const route = useRoute()   // pour récupérer l'id du tableau

const idTableau = Number(route.params.id)

const props = defineProps({
  colonne: Array
})

const menuOpen = ref(null)

function getTachesParColonne(colonneId) {
  return taches.filter(tache => tache.colonne === colonneId)
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
          <h3>{{ col.titre }}</h3>

          <div class="menu-container">
            <button class="menu-button2" @click.stop="toggleMenu(col.id)">
              ⋮
            </button>

            <div v-if="menuOpen === col.id" class="menu-dropdown">
              <p @click="() => goToModifier(col.id)">Modifier la colonne</p>
              <p @click="() => goToAjouter(col.id)">Ajouter une tâche</p>
              <p>Supprimer la colonne et les tâches</p>
            </div>
          </div>

        </div>

        <div>
          <Tache 
            v-if="getTachesParColonne(col.id).length > 0" 
            :tache="getTachesParColonne(col.id)" 
          />

          <p v-else style="font-size:18px;padding:20px 40px;">
            Aucune Tâche trouvée
          </p>
        </div>

      </div>
    </div>
  </div>
</template>