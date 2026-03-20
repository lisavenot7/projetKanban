<script setup>
import { useRouter,useRoute} from 'vue-router'
import { ref,onMounted } from 'vue'

const router = useRouter()
const route = useRoute()

const props = defineProps({
  tache: { type: Array, default: () => [] } // valeur par défaut pour éviter undefined
})

const idTableau = Number(route.params.id)
const token = localStorage.getItem("jwtToken") 

const commentaires = ref([])
const user=ref([])

function goToTache(tacheId, idColonne) {
  router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${tacheId}`)
}

function getCommentairesByTache(tacheId) {
  return commentaires.value.filter(com => com.tchId === tacheId)
}

function getUserByTache(t) {
  return user.value.find(u => u.cptId === t.compteId) || {}
}

const today = new Date()
today.setHours(0, 0, 0, 0)

async function fetchCommentaires() {
  try {
    const response = await fetch(`http://localhost:10056/commentaires`, {
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
    commentaires.value = data.data || []
  } catch (err) {
    console.error("Impossible de récupérer les commentaires", err)
    commentaires.value = []
  }
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

onMounted(() => {
  fetchCommentaires()
  fetchUsers()
})
</script>

<template>
  <div class="taches">
    <div 
      class="tache-cards" 
      v-for="t in tache"
      :key="t.tchId"
      @click="goToTache(t.tchId, t.colonneId)"
      style="cursor:pointer"
      >
      <h3>
      <span v-if="t.tchPriorite === 1">
        <svg width="20" height="20" viewBox="0 0 24 24">
          <path d="M12 3L1 21h22L12 3z" fill="#e53935"/>
          <rect x="11" y="9" width="2" height="6" fill="white"/>
          <rect x="11" y="17" width="2" height="2" fill="white"/>
        </svg>
      </span>
        {{ t.tchTitre }} 
      </h3>
      <p v-if="t.tchDateLimite!=null">
        A faire avant le : {{ t.tchDateLimite }}
      </p>
      <p v-if="getUserByTache(t).cptId">
        Assigné à : {{ getUserByTache(t).nom }} {{ getUserByTache(t).prenom }}
      </p>
      <p v-if="t.tchStatus==='Terminé'" style="color:green">
        {{ t.tchStatus }}
      </p>
      <p v-else-if="t.tchStatus==='En cours'" style="color:orange">
        {{ t.tchStatus }}
      </p>
      <p v-else >
        {{ t.tchStatus }}
      </p>

      <p v-if="t.tchDateLimite && new Date(t.tchDateLimite) < today && t.tchStatus!=='Terminé'" style="color:red">
        Retard
      </p>
      <p >
        {{ getCommentairesByTache(t.tchId).length }} commentaires
      </p> 
    </div>
  </div>
</template>

