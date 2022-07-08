import { ModuleProperty } from '@/module/domain/ModuleProperty';
import { defineComponent, PropType } from 'vue';

export default defineComponent({
  name: 'ModulePropertiesVue',
  props: {
    properties: {
      type: Array as PropType<Array<ModuleProperty>>,
      required: true,
    },
    moduleSlug: {
      type: String,
      required: true,
    },
    moduleProperties: {
      type: Map<string, string | number | boolean>,
      required: true,
    },
    propertiesType: {
      type: String,
      required: true,
    },
  },
  setup(props) {
    const propertiesStats = (): string => {
      return `${propertiesWithValueCount()} / ${props.properties.length}`;
    };

    const propertiesWithValueCount = (): number => {
      return props.properties.filter(property => notEmpty(props.moduleProperties.get(property.key))).length;
    };

    return {
      propertiesStats,
    };
  },
});

function notEmpty(value: string | number | boolean | undefined): boolean {
  if (value === undefined) {
    return false;
  }

  if (typeof value === 'string') {
    return value.trim().length !== 0;
  }

  return true;
}
