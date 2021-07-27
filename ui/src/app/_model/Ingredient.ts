import {Recipe} from "./Recipe";

export interface Ingredient {
  id: number,
  name: string,
  measureUnit: string,
  recipe: Recipe,
  amount: number
}
