import {Opinion} from "./Opinion";
import {User} from "./User";
import {Ingredient} from "./Ingredient";
import {Category} from "./Category";

export interface Recipe {
  id: number,
  title: string,
  description: string,
  time: number,
  portions: number,
  image: string,
  difficulty: string,
  opinions: Opinion[],
  author: User | null | undefined,
  ingredients: Ingredient[],
  categories: Category[]
}
