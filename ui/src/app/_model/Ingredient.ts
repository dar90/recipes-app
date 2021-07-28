import {Recipe} from "./Recipe";

export interface Ingredient {
  id: number | null | undefined,
  name: string,
  measureUnit: string,
  recipe: Recipe | null | undefined,
  amount: number
}
