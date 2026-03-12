<script setup>
import { useRouter,useRoute} from 'vue-router'

const router = useRouter()
const route = useRoute()
const props = defineProps({
  tache: Array
})

const idTableau = Number(route.params.id)

function goToTache(tacheId, idColonne) {
   router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${tacheId}`)
}

const today = new Date();
today.setHours(0, 0, 0, 0); 
</script>

<template>
  <div class="taches">
    <div 
      class="tache-cards" 
      v-for="t in tache"
      :key="t.id"
      @click="goToTache(t.id, t.colonne)"
      style="cursor:pointer"
      >
      <h3>
      <span v-if="t.prioritaire === 1">
        <svg width="20" height="20" viewBox="0 0 24 24">
          <path d="M12 3L1 21h22L12 3z" fill="#e53935"/>
          <rect x="11" y="9" width="2" height="6" fill="white"/>
          <rect x="11" y="17" width="2" height="2" fill="white"/>
        </svg>
      </span>
        {{ t.nom }} 
      </h3>
      <p v-if="t.dateLimite!=null">
        A faire avant le : {{ t.dateLimite }}
      </p>
      <p>
        Assigné à : {{ t.utilisateur }}
      </p>
      <p v-if="t.etat==='Terminé'" style="color:green">
        {{ t.etat }}
      </p>
      <p v-else-if="t.etat==='En cours'" style="color:orange">
        {{ t.etat }}
      </p>
      <p v-else >
        {{ t.etat }}
      </p>
      <p v-if="t.dateLimite && new Date(t.dateLimite) < today && t.etat!=='Terminé'" style="color:red">
        Retard
      </p>
      <p >
        {{ t.commentaires.length }} commentaires
      </p>
    </div>
  </div>
</template>

