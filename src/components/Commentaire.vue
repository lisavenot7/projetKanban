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

const user=ref([])

const menuOpen = ref(null)

const emit = defineEmits(["comSupprimee"])

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
  fetchUsers()
})
onBeforeUnmount(() => {
  document.removeEventListener("click", closeMenu)
})

function goToModifier(idCom) {
    router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}/commentaires/${idCom}/modifier`)
}

async function fetchUsers() {
  try {
    const response = await fetch(`http://localhost:10056/comptes`, {
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

async function deleteCom(idCom) {
  const confirmed = window.confirm("Êtes-vous sûr de vouloir supprimer cet commentaire ? Cette action est irréversible !");
  if (!confirmed) return;
  try {
    const response = await fetch(`http://localhost:10056/commentaires/${idCom}`, {
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
    emit("comSupprimee", idCom)
  } catch (err) {
    console.error("Impossible de supprimer le commentaire", err);
  }
  router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}`)
}

const idUser = localStorage.getItem("cptId")
const token = localStorage.getItem("jwtToken") 

function getUserByCom(c) {
  return user.value.find(u => u.cptId === c.cptId) || {}
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
        <h3>{{ getUserByCom(com).nom }} {{ getUserByCom(com).prenom }}- {{ com.datePublication }}</h3>
        <div v-if="com.cptId===Number(idUser)" class="menu-container">
          <button class="menu-button3" @click.stop="toggleMenu(com.id)">
            ⋮
          </button>
          <div v-if="menuOpen === com.id" class="menu-dropdown">
            <p @click="() => goToModifier(com.id)">Modifier le commentaire</p>
            <p @click="() => deleteCom(com.id)">Supprimer le commentaire</p>
          </div>
        </div>
      </div>
      <p>{{ com.contenu }}</p>
    </div>
  </div>
</template>