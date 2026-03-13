<script setup>
import { useRouter} from 'vue-router'

const router = useRouter()
const props = defineProps({
  title: String,
  tab: Array
})

function goToTableau(tab) {
   router.push(`/private/tableaux/${tab.id}`)
}
</script>

<template>
  <div class="row">
    <h1>
      <svg width="64" height="64" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <rect x="3" y="4" width="18" height="16" rx="3" stroke="currentColor" stroke-width="2"/>
        <rect x="5" y="7" width="4" height="10" rx="1.5" fill="currentColor"/>
        <rect x="10" y="7" width="4" height="6" rx="1.5" fill="currentColor"/>
        <rect x="15" y="7" width="4" height="8" rx="1.5" fill="currentColor"/>
      </svg>
      {{ title }}
    </h1>
    <div class="tableaux">
      <h3
        class="tab-cards"
        v-for="t in tab"
        :key="t.id"
        @click="goToTableau(t)"
        style="cursor:pointer"
      >
      
        {{ t.titre }}
      </h3>
    </div>
  </div>
</template>

