import {Recipe} from "./Recipe";

export interface Category {
  name: string,
  description: string,
  image: string,
  recipes: Recipe[] | null | undefined
}
