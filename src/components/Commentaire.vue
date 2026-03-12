<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const props = defineProps({
  commentaires: Array
})

const router = useRouter()
const route = useRoute()

const idTableau = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)
const idTache = Number(route.params.idtache)

const menuOpen = ref(null)

function toggleMenu(comId) {
  menuOpen.value = menuOpen.value === comId ? null : comId
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

const user="robertD";

function goToModifier(idCom) {
    router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}/commentaires/${idCom}/modifier`)
}
</script>

<template>
  <div class="commentaires">
    <div 
      class="com-cards" 
      v-for="com in commentaires"
      :key="com.id"
    >
      <div class="tableau-header">
        <h3>{{ com.utilisateur }} - {{ com.date }}</h3>
        <div v-if="com.utilisateur===user" class="menu-container">
          <button class="menu-button3" @click.stop="toggleMenu(com.id)">
            ⋮
          </button>
          <div v-if="menuOpen === com.id" class="menu-dropdown">
            <p @click="() => goToModifier(com.id)">Modifier le commentaire</p>
            <p>Supprimer le commentaire</p>
          </div>
        </div>
      </div>
      <p>{{ com.texte }}</p>
    </div>
  </div>
</template>