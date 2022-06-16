import { Loader } from '@/loader/primary/Loader';
import { Modules } from '@/module/domain/Modules';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { defineComponent, inject, onMounted, reactive } from 'vue';

export default defineComponent({
  name: 'ModulesListVue',
  setup() {
    const modules = inject('modules') as ModulesRepository;

    const modulesContent = reactive({
      content: Loader.loading<Modules>(),
    });

    onMounted(() => {
      modules.list().then(response => modulesContent.content.loaded(response));
    });

    return {
      content: modulesContent.content,
    };
  },
});
