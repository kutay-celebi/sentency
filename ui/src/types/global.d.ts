declare module "~icons/*" {
  import { FunctionalComponent, SVGAttributes } from "vue";
  const component: FunctionalComponent<SVGAttributes>;
  export default component;
}

// export {};
//
// declare module "vue" {
//   export interface ComponentCustomProperties {
//     $filters: Filters;
//   }
//   export interface ComponentCustomOptions {
//     hello: () => void;
//   }
// }
//
// export declare interface Filters {
//   dateLongFormat: (val: any) => string;
// }
